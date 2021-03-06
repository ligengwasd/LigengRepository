/*
 * This Work is in the public domain and is provided on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied,
 * including, without limitation, any warranties or conditions of TITLE,
 * NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE.
 * You are solely responsible for determining the appropriateness of using
 * this Work and assume any risks associated with your use of this Work.
 *
 * This Work includes contributions authored by David E. Jones, not as a
 * "work for hire", who hereby disclaims any copyright to the same.
 */
package org.moqui.impl.context.reference

import org.moqui.context.ExecutionContextFactory
import org.moqui.context.ResourceReference
import org.moqui.impl.context.ResourceFacadeImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class BaseResourceReference implements ResourceReference {
    protected final static Logger logger = LoggerFactory.getLogger(BaseResourceReference.class)

    ExecutionContextFactory ecf = null
    protected Map<String, ResourceReference> subContentRefByPath = null

    ResourceReference childOfResource = null

    BaseResourceReference() { }

    @Override
    abstract ResourceReference init(String location, ExecutionContextFactory ecf)

    protected Map<String, ResourceReference> getSubContentRefByPath() {
        if (subContentRefByPath == null) subContentRefByPath = new HashMap<String, ResourceReference>()
        return subContentRefByPath
    }

    @Override
    abstract String getLocation();

    @Override
    URI getUri() {
        String loc = getLocation()
        if (!loc) return null
        if (supportsUrl()) {
            URL locUrl = getUrl()
            // use the multi-argument constructor to have it do character encoding and avoid an exception
            // WARNING: a String from this URI may not equal the String from the URL (ie if characters are encoded)
            // NOTE: this doesn't seem to work on Windows for local files: when protocol is plain "file" and path starts
            //     with a drive letter like "C:\moqui\..." it produces a parse error showing the URI as "file://C:/..."
            return new URI(locUrl.getProtocol(), locUrl.getUserInfo(), locUrl.getHost(),
                    locUrl.getPort(), locUrl.getPath(), locUrl.getQuery(), locUrl.getRef())
        } else {
            // TODO: handle encoding for URI to avoid errors
            return new URI(loc)
        }
    }
    @Override
    String getFileName() {
        String loc = getLocation()
        if (!loc) return null
        return loc.contains("/") ? loc.substring(loc.lastIndexOf("/")+1) : loc
    }

    @Override
    abstract InputStream openStream();

    @Override
    abstract String getText();

    @Override
    String getContentType() {
        String fn = getFileName()
        return fn ? ecf.getResource().getContentType(fn) : null
    }

    @Override
    abstract boolean supportsAll();

    @Override
    abstract boolean supportsUrl();
    @Override
    abstract URL getUrl();

    @Override
    abstract boolean supportsDirectory();
    @Override
    abstract boolean isFile();
    @Override
    abstract boolean isDirectory();
    @Override
    abstract List<ResourceReference> getDirectoryEntries();

    @Override
    ResourceReference getParent() {
        String curLocation = getLocation()
        if (curLocation.endsWith("/")) curLocation = curLocation.substring(0, curLocation.length() - 1)
        String strippedLocation = ResourceFacadeImpl.stripLocationPrefix(curLocation)
        if (!strippedLocation) return null
        if (strippedLocation.startsWith("/")) strippedLocation = strippedLocation.substring(1)
        if (strippedLocation.contains("/")) {
            return ecf.getResource().getLocationReference(curLocation.substring(0, curLocation.lastIndexOf("/")))
        } else {
            String prefix = ResourceFacadeImpl.getLocationPrefix(curLocation)
            if (prefix) return ecf.getResource().getLocationReference(prefix)
            return null
        }
    }

    @Override
    ResourceReference getChild(String childName) {
        ResourceReference directoryRef = findMatchingDirectory()
        StringBuilder fileLoc = new StringBuilder(directoryRef.getLocation())
        if (fileLoc.charAt(fileLoc.length()-1) == '/') fileLoc.deleteCharAt(fileLoc.length()-1)
        if (childName.charAt(0) != '/') fileLoc.append('/')
        fileLoc.append(childName)

        // NOTE: don't really care if it exists or not at this point
        ResourceReference childRef = ecf.resource.getLocationReference(fileLoc.toString())
        return childRef
    }

    @Override
    List<ResourceReference> getChildren() {
        List<ResourceReference> children = []

        ResourceReference directoryRef = findMatchingDirectory()
        if (!directoryRef?.exists) return null

        for (ResourceReference childRef in directoryRef.getDirectoryEntries())
            if (childRef.isFile()) children.add(childRef)

        return children
    }

    @Override
    ResourceReference findChildFile(String relativePath) {
        // no path to child? that means this resource
        if (!relativePath) return this

        if (!supportsAll()) {
            ecf.getExecutionContext().message.addError("Not looking for child file at [${relativePath}] under space root page [${getLocation()}] because exists, isFile, etc are not supported")
            return null
        }
        // logger.warn("============= finding child resource of [${toString()}] path [${relativePath}]")

        // check the cache first
        ResourceReference childRef = getSubContentRefByPath().get(relativePath)
        if (childRef != null && childRef.exists) return childRef

        // this finds a file in a directory with the same name as this resource, unless this resource is a directory
        ResourceReference directoryRef = findMatchingDirectory()

        // logger.warn("============= finding child resource path [${relativePath}] directoryRef [${directoryRef}]")
        if (directoryRef.exists) {
            StringBuilder fileLoc = new StringBuilder(directoryRef.getLocation())
            if (fileLoc.charAt(fileLoc.length()-1) == '/') fileLoc.deleteCharAt(fileLoc.length()-1)
            if (relativePath.charAt(0) != '/') fileLoc.append('/')
            fileLoc.append(relativePath)

            ResourceReference theFile = ecf.resource.getLocationReference(fileLoc.toString())
            if (theFile.exists && theFile.isFile()) childRef = theFile

            // logger.warn("============= finding child resource path [${relativePath}] childRef 1 [${childRef}]")
            /* this approach is no longer needed; the more flexible approach below will handle this and more:
            if (childRef == null) {
                // try adding known extensions
                for (String extToTry in ecf.resource.templateRenderers.keySet()) {
                    if (childRef != null) break
                    theFile = ecf.resource.getLocationReference(fileLoc.toString() + extToTry)
                    if (theFile.exists && theFile.isFile()) childRef = theFile
                    // logger.warn("============= finding child resource path [${relativePath}] fileLoc [${fileLoc}] extToTry [${extToTry}] childRef [${theFile}]")
                }
            }
            */

            // logger.warn("============= finding child resource path [${relativePath}] childRef 2 [${childRef}]")
            if (childRef == null) {
                // didn't find it at a literal path, try searching for it in all subdirectories
                int lastSlashIdx = relativePath.lastIndexOf("/")
                String directoryPath = lastSlashIdx > 0 ? relativePath.substring(0, lastSlashIdx) : ""
                String childFilename = lastSlashIdx >= 0 ? relativePath.substring(lastSlashIdx + 1) : relativePath

                // first find the most matching directory
                ResourceReference childDirectoryRef = directoryRef.findChildDirectory(directoryPath)

                // recursively walk the directory tree and find the childFilename
                childRef = internalFindChildFile(childDirectoryRef, childFilename)
                // logger.warn("============= finding child resource path [${relativePath}] directoryRef [${directoryRef}] childFilename [${childFilename}] childRef [${childRef}]")
            }
            // logger.warn("============= finding child resource path [${relativePath}] childRef 3 [${childRef}]")

            if (childRef != null && childRef instanceof BaseResourceReference) {
                ((BaseResourceReference) childRef).childOfResource = directoryRef
            }
        }

        if (childRef == null) {
            // still nothing? treat the path to the file as a literal and return it (exists will be false)
            if (directoryRef.exists) {
                childRef = ecf.resource.getLocationReference(directoryRef.getLocation() + '/' + relativePath)
                if (childRef instanceof BaseResourceReference) {
                    ((BaseResourceReference) childRef).childOfResource = directoryRef
                }
            } else {
                String newDirectoryLoc = getLocation()
                // pop off the extension, everything past the first dot after the last slash
                int lastSlashLoc = newDirectoryLoc.lastIndexOf("/")
                if (newDirectoryLoc.contains(".")) newDirectoryLoc = newDirectoryLoc.substring(0, newDirectoryLoc.indexOf(".", lastSlashLoc))
                childRef = ecf.resource.getLocationReference(newDirectoryLoc + '/' + relativePath)
            }
        } else {
            // put it in the cache before returning, but don't cache the literal reference
            getSubContentRefByPath().put(relativePath, childRef)
        }

        // logger.warn("============= finding child resource of [${toString()}] path [${relativePath}] got [${childRef}]")
        return childRef
    }

    ResourceReference findChildDirectory(String relativePath) {
        if (!relativePath) return this

        if (!supportsAll()) {
            ecf.getExecutionContext().message.addError("Not looking for child directory at [${relativePath}] under space root page [${getLocation()}] because exists, isFile, etc are not supported")
            return null
        }

        // check the cache first
        ResourceReference childRef = getSubContentRefByPath().get(relativePath)
        if (childRef != null && childRef.exists) return childRef

        List<String> relativePathNameList = relativePath.split("/")

        ResourceReference childDirectoryRef = this
        if (this.isFile()) childDirectoryRef = this.findMatchingDirectory()

        // search remaining relativePathNameList, ie partial directories leading up to filename
        for (String relativePathName in relativePathNameList) {
            childDirectoryRef = internalFindChildDir(childDirectoryRef, relativePathName)
            if (childDirectoryRef == null) break
        }

        if (childDirectoryRef == null) {
            // still nothing? treat the path to the file as a literal and return it (exists will be false)
            String newDirectoryLoc = getLocation()
            if (this.isFile()) {
                // pop off the extension, everything past the first dot after the last slash
                int lastSlashLoc = newDirectoryLoc.lastIndexOf("/")
                if (newDirectoryLoc.contains(".")) newDirectoryLoc = newDirectoryLoc.substring(0, newDirectoryLoc.indexOf(".", lastSlashLoc))
            }
            childDirectoryRef = ecf.resource.getLocationReference(newDirectoryLoc + '/' + relativePath)
        } else {
            // put it in the cache before returning, but don't cache the literal reference
            getSubContentRefByPath().put(relativePath, childRef)
        }
        return childDirectoryRef
    }

    ResourceReference findMatchingDirectory() {
        if (this.isDirectory()) return this
        StringBuilder dirLoc = new StringBuilder(getLocation())
        ResourceReference directoryRef = this
        while (!(directoryRef.exists && directoryRef.isDirectory()) && dirLoc.lastIndexOf(".") > 0) {
            // get rid of one suffix at a time (for screens probably .xml but use .* for other files, etc)
            dirLoc.delete(dirLoc.lastIndexOf("."), dirLoc.length())
            directoryRef = ecf.resource.getLocationReference(dirLoc.toString())
        }
        return directoryRef
    }

    ResourceReference internalFindChildDir(ResourceReference directoryRef, String childDirName) {
        if (directoryRef == null || !directoryRef.exists) return null
        // no child dir name, means this/current dir
        if (!childDirName) return directoryRef

        // try a direct sub-directory, if it is there it's more efficient than a brute-force search
        StringBuilder dirLocation = new StringBuilder(directoryRef.getLocation())
        if (dirLocation.charAt(dirLocation.length()-1) == '/') dirLocation.deleteCharAt(dirLocation.length()-1)
        if (childDirName.charAt(0) != '/') dirLocation.append('/')
        dirLocation.append(childDirName)
        ResourceReference directRef = ecf.resource.getLocationReference(dirLocation.toString())
        if (directRef != null && directRef.exists) return directRef

        // if no direct reference is found, try the more flexible search
        for (ResourceReference childRef in directoryRef.directoryEntries) {
            if (childRef.isDirectory() && (childRef.fileName == childDirName || childRef.fileName.contains(childDirName + '.'))) {
                // matching directory name, use it
                return childRef
            } else if (childRef.isDirectory()) {
                // non-matching directory name, recurse into it
                ResourceReference subRef = internalFindChildDir(childRef, childDirName)
                if (subRef != null) return subRef
            }
        }
        return null
    }

    ResourceReference internalFindChildFile(ResourceReference directoryRef, String childFilename) {
        if (directoryRef == null || !directoryRef.exists) return null

        // find check exact filename first
        ResourceReference exactMatchRef = directoryRef.getChild(childFilename)
        if (exactMatchRef.isFile() && exactMatchRef.getExists()) return exactMatchRef

        List<ResourceReference> childEntries = directoryRef.directoryEntries
        // look through all files first, ie do a breadth-first search
        for (ResourceReference childRef in childEntries) {
            if (childRef.isFile() && (childRef.getFileName() == childFilename || childRef.getFileName().contains(childFilename + '.'))) {
                return childRef
            }
        }
        for (ResourceReference childRef in childEntries) {
            if (childRef.isDirectory()) {
                ResourceReference subRef = internalFindChildFile(childRef, childFilename)
                if (subRef != null) return subRef
            }
        }
        return null
    }

    String getActualChildPath() {
        if (childOfResource == null) return null
        String parentLocation = childOfResource.getLocation()
        String childLocation = getLocation()
        // this should be true, but just in case:
        if (childLocation.startsWith(parentLocation)) {
            String childPath = childLocation.substring(parentLocation.length())
            if (childPath.startsWith("/")) return childPath.substring(1)
            else return childPath
        }
        // if not, what to do?
        return null
    }

    void walkChildTree(List<Map> allChildFileFlatList, List<Map> childResourceList) {
        if (this.isFile()) {
            walkChildFileTree(this, "", allChildFileFlatList, childResourceList)
        }

        if (this.isDirectory()) {
            for (ResourceReference childRef in this.getDirectoryEntries()) {
                ((BaseResourceReference) childRef).walkChildFileTree(this, "", allChildFileFlatList, childResourceList)
            }
        }
    }

    void walkChildFileTree(ResourceReference rootResource, String pathFromRoot,
                       List<Map> allChildFileFlatList, List<Map> childResourceList) {
        // logger.warn("================ walkChildFileTree rootResource=${rootResource} pathFromRoot=${pathFromRoot} curLocation=${getLocation()}")

        String childPathBase = pathFromRoot ? pathFromRoot + '/' : ''

        if (this.isFile()) {
            List<Map> curChildResourceList = []

            String curFileName = this.getFileName()
            if (curFileName.contains(".")) curFileName = curFileName.substring(0, curFileName.indexOf('.'))
            String curPath = childPathBase + curFileName

            if (allChildFileFlatList != null)
                allChildFileFlatList.add([path:curPath, name:curFileName, location:this.getLocation()])
            if (childResourceList != null)
                childResourceList.add([path:curPath, name:curFileName, location:this.getLocation(),
                    childResourceList:curChildResourceList])

            ResourceReference matchingDirReference = this.findMatchingDirectory()
            String childPath = childPathBase + matchingDirReference.fileName
            for (ResourceReference childRef in matchingDirReference.getDirectoryEntries()) {
                ((BaseResourceReference) childRef).walkChildFileTree(rootResource, childPath, allChildFileFlatList, curChildResourceList)
            }
        }

        // TODO: walk child directories somehow or just stick with files with matching directories?
    }

    @Override
    abstract boolean supportsExists()
    @Override
    abstract boolean getExists()

    @Override
    abstract boolean supportsLastModified()
    @Override
    abstract long getLastModified()

    @Override
    abstract boolean supportsSize()
    @Override
    abstract long getSize()

    @Override
    abstract boolean supportsWrite()
    @Override
    abstract void putText(String text)
    @Override
    abstract void putStream(InputStream stream)
    @Override
    abstract void move(String newLocation)
    @Override
    abstract ResourceReference makeDirectory(String name)
    @Override
    abstract ResourceReference makeFile(String name)
    @Override
    abstract boolean delete()

    @Override
    void destroy() { }

    @Override
    String toString() { return getLocation() ?: "[no location (${this.class.getName()})]" }
}
