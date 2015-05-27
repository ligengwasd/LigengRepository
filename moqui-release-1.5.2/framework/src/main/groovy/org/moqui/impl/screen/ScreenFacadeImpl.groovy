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
package org.moqui.impl.screen

import freemarker.template.Template
import groovy.transform.CompileStatic
import org.moqui.context.ResourceReference
import org.moqui.context.ScreenFacade
import org.moqui.context.ScreenRender
import org.moqui.context.Cache
import org.moqui.impl.context.ExecutionContextFactoryImpl
import org.moqui.impl.screen.ScreenDefinition.SubscreensItem
import org.moqui.impl.screen.ScreenDefinition.TransitionItem
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class ScreenFacadeImpl implements ScreenFacade {
    protected final static Logger logger = LoggerFactory.getLogger(ScreenFacadeImpl.class)

    protected final ExecutionContextFactoryImpl ecfi

    protected final Cache screenLocationCache
    protected final Cache screenLocationPermCache
    // used by ScreenUrlInfo
    final Cache screenUrlCache
    protected final Cache screenInfoCache
    protected final Cache screenInfoRefRevCache
    protected final Cache screenTemplateModeCache
    protected final Cache screenTemplateLocationCache
    protected final Cache widgetTemplateLocationCache
    protected final Cache screenFindPathCache
    protected final Cache dbFormNodeByIdCache

    ScreenFacadeImpl(ExecutionContextFactoryImpl ecfi) {
        this.ecfi = ecfi
        this.screenLocationCache = ecfi.cacheFacade.getCache("screen.location")
        this.screenLocationPermCache = ecfi.cacheFacade.getCache("screen.location.perm")
        this.screenUrlCache = ecfi.cacheFacade.getCache("screen.url")
        this.screenInfoCache = ecfi.cacheFacade.getCache("screen.info")
        this.screenInfoRefRevCache = ecfi.cacheFacade.getCache("screen.info.ref.rev")
        this.screenTemplateModeCache = ecfi.cacheFacade.getCache("screen.template.mode")
        this.screenTemplateLocationCache = ecfi.cacheFacade.getCache("screen.template.location")
        this.widgetTemplateLocationCache = ecfi.cacheFacade.getCache("widget.template.location")
        this.screenFindPathCache = ecfi.cacheFacade.getCache("screen.find.path")
        this.dbFormNodeByIdCache = ecfi.cacheFacade.getCache("screen.form.db.node")
    }

    ExecutionContextFactoryImpl getEcfi() { return ecfi }

    void warmCache() {
        long startTime = System.currentTimeMillis()
        int screenCount = 0
        for (String rootLocation in getAllRootScreenLocations()) {
            logger.info("Warming cache for all screens under ${rootLocation}")
            ScreenDefinition rootSd = getScreenDefinition(rootLocation)
            screenCount++
            screenCount += warmCacheScreen(rootSd)
        }
        logger.info("Warmed screen definition cache for ${screenCount} screens in ${(System.currentTimeMillis() - startTime)/1000} seconds")
    }
    protected int warmCacheScreen(ScreenDefinition sd) {
        int screenCount = 0
        for (SubscreensItem ssi in sd.subscreensByName.values()) {
            try {
                ScreenDefinition subSd = getScreenDefinition(ssi.getLocation())
                screenCount++
                if (subSd) screenCount += warmCacheScreen(subSd)
            } catch (Throwable t) {
                logger.error("Error loading screen at [${ssi.getLocation()}] during cache warming", t)
            }
        }
        return screenCount
    }

    List<String> getAllRootScreenLocations() {
        List<String> allLocations = []
        for (Node webappNode in ecfi.confXmlRoot."webapp-list"[0]."webapp") {
            for (Node rootScreenNode in webappNode."root-screen") {
                String rootLocation = rootScreenNode."@location"
                allLocations.add(rootLocation)
            }
        }
        return allLocations
    }

    @CompileStatic
    boolean isScreen(String location) {
        if (!location.endsWith(".xml")) return false
        if (screenLocationCache.containsKey(location)) return true

        try {
            ScreenDefinition checkSd = getScreenDefinition(location)
            return (checkSd != null)
        } catch (Throwable t) {
            // ignore the error, just checking to see if it is a screen
            if (logger.isInfoEnabled()) logger.info("Error when checking to see if [${location}] is a XML Screen: ${t.toString()}")
            return false
        }
    }

    @CompileStatic
    ScreenDefinition getScreenDefinition(String location) {
        if (!location) return null
        ScreenDefinition sd = (ScreenDefinition) screenLocationCache.get(location)
        if (sd) return sd

        return makeScreenDefinition(location)
    }

    @CompileStatic
    protected synchronized ScreenDefinition makeScreenDefinition(String location) {
        ScreenDefinition sd = (ScreenDefinition) screenLocationCache.get(location)
        if (sd != null) return sd

        ResourceReference screenRr = ecfi.getResourceFacade().getLocationReference(location)

        ScreenDefinition permSd = (ScreenDefinition) screenLocationPermCache.get(location)
        if (permSd) {
            // check to see if file has been modified, if we know when it was last modified
            if (permSd.sourceLastModified && screenRr.supportsLastModified() &&
                    screenRr.getLastModified() == permSd.sourceLastModified) {
                //logger.warn("========= screen expired but hasn't changed so reusing: ${location}")

                // call this just in case a new screen was added, note this does slow things down just a bit, but only in dev (not in production)
                permSd.populateSubscreens()

                screenLocationCache.put(location, permSd)
                return permSd
            } else {
                screenLocationPermCache.remove(location)
                logger.info("Screen modified since last loaded, reloading: ${location}")
            }
        }

        Node screenNode = null
        InputStream screenFileIs = null

        try {
            screenFileIs = screenRr.openStream()
            screenNode = new XmlParser().parse(screenFileIs)
        } catch (IOException e) {
            // probably because there is no resource at that location, so do nothing
            throw new IllegalArgumentException("Error finding screen at location ${location}", e)
        } finally {
            if (screenFileIs != null) screenFileIs.close()
        }

        if (screenNode == null) {
            throw new IllegalArgumentException("Cound not find definition for screen at location [${location}]")
        }

        sd = new ScreenDefinition(this, screenNode, location)
        // logger.warn("========= loaded screen [${location}] supports LM ${screenRr.supportsLastModified()}, LM: ${screenRr.getLastModified()}")
        sd.sourceLastModified = screenRr.supportsLastModified() ? screenRr.getLastModified() : null
        screenLocationCache.put(location, sd)
        if (screenRr.supportsLastModified()) screenLocationPermCache.put(location, sd)
        return sd
    }

    @CompileStatic
    Node getFormNode(String location) {
        if (!location) return null
        if (location.contains("#")) {
            String screenLocation = location.substring(0, location.indexOf("#"))
            String formName = location.substring(location.indexOf("#")+1)
            if (screenLocation == "moqui.screen.form.DbForm" || screenLocation == "DbForm") {
                return ScreenForm.getDbFormNode(formName, ecfi)
            } else {
                ScreenDefinition esd = getScreenDefinition(screenLocation)
                ScreenForm esf = esd ? esd.getForm(formName) : null
                return esf?.formNode
            }
        } else {
            throw new IllegalArgumentException("Must use full form location (with #) to get a form node, [${location}] has no hash (#).")
        }
    }

    String getMimeTypeByMode(String renderMode) {
        String mimeType = ecfi.getConfXmlRoot()."screen-facade"[0]
                ."screen-text-output".find({ it.@type == renderMode })?."@mime-type"
        return mimeType
    }

    @CompileStatic
    Template getTemplateByMode(String renderMode) {
        Template template = (Template) screenTemplateModeCache.get(renderMode)
        if (template) return template

        template = makeTemplateByMode(renderMode)
        if (!template) throw new IllegalArgumentException("Could not find screen render template for mode [${renderMode}]")
        return template
    }

    protected synchronized Template makeTemplateByMode(String renderMode) {
        Template template = (Template) screenTemplateModeCache.get(renderMode)
        if (template) return template

        String templateLocation = ecfi.getConfXmlRoot()."screen-facade"[0]
                ."screen-text-output".find({ it.@type == renderMode })?."@macro-template-location"
        if (!templateLocation) throw new IllegalArgumentException("Could not find macro-template-location for render mode (screen-text-output.@type) [${renderMode}]")
        // NOTE: this is a special case where we need something to call #recurse so that all includes can be straight libraries
        String rootTemplate = """<#include "${templateLocation}"/><#visit widgetsNode>"""

        Template newTemplate
        try {
            newTemplate = new Template("moqui.automatic.${renderMode}", new StringReader(rootTemplate),
                    ecfi.resourceFacade.ftlTemplateRenderer.getFtlConfiguration())
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while initializing Screen Widgets template at [${templateLocation}]", e)
        }

        screenTemplateModeCache.put(renderMode, newTemplate)
        return newTemplate
    }

    @CompileStatic
    Template getTemplateByLocation(String templateLocation) {
        Template template = (Template) screenTemplateLocationCache.get(templateLocation)
        if (template) return template
        return makeTemplateByLocation(templateLocation)
    }

    @CompileStatic
    protected synchronized Template makeTemplateByLocation(String templateLocation) {
        Template template = (Template) screenTemplateLocationCache.get(templateLocation)
        if (template) return template

        // NOTE: this is a special case where we need something to call #recurse so that all includes can be straight libraries
        String rootTemplate = """<#include "${templateLocation}"/><#visit widgetsNode>"""


        Template newTemplate
        try {
            // this location needs to look like a filename in the runtime directory, otherwise FTL will look for includes under the directory it looks like instead
            String filename = templateLocation.substring(templateLocation.lastIndexOf("/")+1)
            newTemplate = new Template(filename, new StringReader(rootTemplate),
                    ecfi.resourceFacade.ftlTemplateRenderer.getFtlConfiguration())
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while initializing Screen Widgets template at [${templateLocation}]", e)
        }

        screenTemplateLocationCache.put(templateLocation, newTemplate)
        return newTemplate
    }

    @CompileStatic
    Node getWidgetTemplatesNodeByLocation(String templateLocation) {
        Node templatesNode = (Node) widgetTemplateLocationCache.get(templateLocation)
        if (templatesNode) return templatesNode
        return makeWidgetTemplatesNodeByLocation(templateLocation)
    }

    protected synchronized Node makeWidgetTemplatesNodeByLocation(String templateLocation) {
        Node templatesNode = (Node) widgetTemplateLocationCache.get(templateLocation)
        if (templatesNode) return templatesNode

        templatesNode = new XmlParser().parse(ecfi.resourceFacade.getLocationStream(templateLocation))

        widgetTemplateLocationCache.put(templateLocation, templatesNode)
        return templatesNode
    }

    /* may use these at some point but are really superceded by getScreenInfoList():
    String getScreenDisplayString(String rootLocation, int levels) {
        StringBuilder sb = new StringBuilder()
        List<String> infoList = getScreenDisplayInfo(rootLocation, levels)
        for (String info in infoList) sb.append(info).append("\n")
        return sb.toString()
    }
    List<String> getScreenDisplayInfo(String rootLocation, int levels) {
        ScreenInfo rootInfo = new ScreenInfo(getScreenDefinition(rootLocation), null, null, 0)
        List<String> infoList = []
        addScreenDisplayInfo(infoList, rootInfo, 0, levels)
        return infoList
    }
    void addScreenDisplayInfo(List<String> infoList, ScreenInfo si, int level, int levels) {
        StringBuilder sb = new StringBuilder()
        for (int i = 0; i < level; i++) sb.append("- ")
        sb.append(" ").append(si.name).append(" ")
        sb.append("Subscreens: ").append(si.allSubscreens).append("(").append(si.allSubscreensNonPlaceholder).append("), ")
        sb.append("Transitions: ").append(si.transitions).append(" sub ").append(si.allSubscreensTransitions).append(", ")
        sb.append("Sections: ").append(si.sections).append(" sub ").append(si.allSubscreensSections).append(", ")
        sb.append("Forms: ").append(si.forms).append(" sub ").append(si.allSubscreensForms).append(", ")
        sb.append("Trees: ").append(si.trees).append(" sub ").append(si.allSubscreensTrees).append(" ")
        infoList.add(sb.toString())

        if (level == levels) return
        for (ScreenInfo childSi in si.subscreenInfoByName.values()) {
            addScreenDisplayInfo(infoList, childSi, level+1, levels)
        }
    }
    */

    List<ScreenInfo> getScreenInfoList(String rootLocation, int levels) {
        ScreenInfo rootInfo = new ScreenInfo(getScreenDefinition(rootLocation), null, null, 0)
        List<ScreenInfo> infoList = []
        infoList.add(rootInfo)
        rootInfo.addChildrenToList(infoList, levels)
        return infoList
    }

    class ScreenInfo implements Serializable {
        ScreenDefinition sd
        SubscreensItem ssi
        ScreenInfo parentInfo
        ScreenInfo rootInfo
        Map<String, ScreenInfo> subscreenInfoByName = new TreeMap()
        Map<String, TransitionInfo> transitionInfoByName = new TreeMap()
        int level
        String name
        List<String> screenPath = []

        boolean isNonPlaceholder = false
        int subscreens = 0, allSubscreens = 0, subscreensNonPlaceholder = 0, allSubscreensNonPlaceholder = 0
        int forms = 0, allSubscreensForms = 0
        int trees = 0, allSubscreensTrees = 0
        int sections = 0, allSubscreensSections = 0
        int transitions = 0, allSubscreensTransitions = 0
        int transitionsWithActions = 0, allSubscreensTransitionsWithActions = 0

        ScreenInfo(ScreenDefinition sd, SubscreensItem ssi, ScreenInfo parentInfo, int level) {
            this.sd = sd
            this.ssi = ssi
            this.parentInfo = parentInfo
            this.level = level
            this.name = ssi ? ssi.getName() : sd.getScreenName()
            if (parentInfo != null) this.screenPath.addAll(parentInfo.screenPath)
            this.screenPath.add(name)

            subscreens = sd.subscreensByName.size()

            forms = sd.formByName.size()
            trees = sd.treeByName.size()
            sections = sd.sectionByName.size()
            transitions = sd.transitionByName.size()
            for (TransitionItem ti in sd.transitionByName.values()) if (ti.hasActionsOrSingleService()) transitionsWithActions++
            isNonPlaceholder = forms || sections || transitions

            // trickle up totals
            ScreenInfo curParent = parentInfo
            while (curParent != null) {
                curParent.allSubscreens += 1
                if (isNonPlaceholder) curParent.allSubscreensNonPlaceholder += 1
                curParent.allSubscreensForms += forms
                curParent.allSubscreensTrees += trees
                curParent.allSubscreensSections += sections
                curParent.allSubscreensTransitions += transitions
                curParent.allSubscreensTransitionsWithActions += transitionsWithActions
                if (curParent.parentInfo == null) rootInfo = curParent
                curParent = curParent.parentInfo
            }
            if (rootInfo == null) rootInfo = this

            // get info for all subscreens
            for (Map.Entry<String, SubscreensItem> ssEntry in sd.subscreensByName.entrySet()) {
                SubscreensItem curSsi = ssEntry.getValue()
                List<String> childPath = new ArrayList(screenPath)
                childPath.add(curSsi.getName())
                ScreenInfo existingSi = (ScreenInfo) screenInfoCache.get(screenPathToString(childPath))
                if (existingSi != null) {
                    subscreenInfoByName.put(ssEntry.getKey(), existingSi)
                } else {
                    ScreenDefinition ssSd = getScreenDefinition(curSsi.getLocation())
                    if (ssSd == null) {
                        logger.info("While getting ScreenInfo screen not found for ${curSsi.getName()} at: ${curSsi.getLocation()}")
                        continue
                    }
                    ScreenInfo newSi = new ScreenInfo(ssSd, curSsi, this, level+1)
                    subscreenInfoByName.put(ssEntry.getKey(), newSi)
                }
            }

            // populate transition references
            for (Map.Entry<String, TransitionItem> tiEntry in sd.transitionByName.entrySet()) {
                transitionInfoByName.put(tiEntry.getKey(), new TransitionInfo(this, tiEntry.getValue()))
            }

            // now that subscreen is initialized save in list for location and path
            List curInfoList = (List) screenInfoCache.get(sd.location)
            if (curInfoList == null) { curInfoList = []; screenInfoCache.put(sd.location, curInfoList) }
            curInfoList.add(this)
            screenInfoCache.put(screenPathToString(screenPath), this)
        }

        String getIndentedName() {
            StringBuilder sb = new StringBuilder()
            for (int i = 0; i < level; i++) sb.append("- ")
            sb.append(" ").append(name)
            return sb.toString()
        }

        void addChildrenToList(List<ScreenInfo> infoList, int maxLevel) {
            for (ScreenInfo si in subscreenInfoByName.values()) {
                infoList.add(si)
                if (maxLevel > level) si.addChildrenToList(infoList, maxLevel)
            }
        }
    }

    class TransitionInfo implements Serializable {
        ScreenInfo si
        TransitionItem ti
        Set<String> responseScreenPathSet = new TreeSet()
        List<String> transitionPath

        TransitionInfo(ScreenInfo si, TransitionItem ti) {
            this.si = si
            this.ti = ti
            transitionPath = si.screenPath
            transitionPath.add(ti.getName())

            for (ScreenDefinition.ResponseItem ri in ti.conditionalResponseList) {
                if (ri.urlType && ri.urlType != "transition" && ri.urlType != "screen") continue
                String expandedUrl = ri.url
                if (expandedUrl.contains('${')) expandedUrl = ecfi.getResource().evaluateStringExpand(expandedUrl, "")
                ScreenUrlInfo sui = ScreenUrlInfo.getScreenUrlInfo(ecfi.getScreenFacade(), si.rootInfo.sd,
                        si.sd, si.screenPath, expandedUrl, null)
                if (sui.targetScreen == null) continue
                String targetScreenPath = screenPathToString(sui.getPreTransitionPathNameList())
                responseScreenPathSet.add(targetScreenPath)

                Set<String> refSet = (Set<String>) screenInfoRefRevCache.get(targetScreenPath)
                if (refSet == null) { refSet = new HashSet(); screenInfoRefRevCache.put(targetScreenPath, refSet) }
                refSet.add(screenPathToString(transitionPath))
            }
        }
    }

    @CompileStatic
    static String screenPathToString(List<String> screenPath) {
        StringBuilder sb = new StringBuilder()
        for (String screenName in screenPath) sb.append("/").append(screenName)
        return sb.toString()
    }

    @Override
    ScreenRender makeRender() {
        return new ScreenRenderImpl(this)
    }
}
