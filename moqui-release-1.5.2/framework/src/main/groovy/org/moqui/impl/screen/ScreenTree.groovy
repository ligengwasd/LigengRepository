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

import org.moqui.context.ContextStack
import org.moqui.context.ExecutionContext
import org.moqui.impl.StupidUtilities
import org.moqui.impl.actions.XmlAction
import org.moqui.impl.context.ExecutionContextFactoryImpl
import org.moqui.impl.context.ExecutionContextImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ScreenTree {
    protected final static Logger logger = LoggerFactory.getLogger(ScreenTree.class)

    protected ExecutionContextFactoryImpl ecfi
    protected ScreenDefinition sd
    protected Node treeNode
    protected String location

    // protected Map<String, ScreenDefinition.ParameterItem> parameterByName = [:]
    protected Map<String, TreeNode> nodeByName = [:]
    protected List<TreeSubNode> subNodeList = []

    ScreenTree(ExecutionContextFactoryImpl ecfi, ScreenDefinition sd, Node treeNode, String location) {
        this.ecfi = ecfi
        this.sd = sd
        this.treeNode = treeNode
        this.location = location

        // prep tree-node
        for (Node treeNodeNode in treeNode."tree-node")
            nodeByName.put(treeNodeNode."@name", new TreeNode(this, treeNodeNode, location + ".node." + treeNodeNode."@name"))

        // prep tree-sub-node
        for (Node treeSubNodeNode in treeNode."tree-sub-node")
            subNodeList.add(new TreeSubNode(this, treeSubNodeNode, location + ".subnode." + treeSubNodeNode."@node-name"))
    }

    void sendSubNodeJson() {
        // NOTE: This method is very specific to jstree

        ExecutionContextImpl eci = ecfi.getEci()
        ContextStack cs = eci.getContext()

        // logger.warn("========= treeNodeId = ${cs.get("treeNodeId")}")
        // if this is the root node get the main tree sub-nodes, otherwise find the node and use its sub-nodes
        List<TreeSubNode> currentSubNodeList = null
        if (cs.get("treeNodeId") == "#") {
            currentSubNodeList = subNodeList
        } else {
            // logger.warn("======== treeNodeName = ${cs.get("treeNodeName")}")
            if (cs.get("treeNodeName")) currentSubNodeList = nodeByName.get(cs.get("treeNodeName"))?.subNodeList
            if (currentSubNodeList == null) {
                // if no treeNodeName passed through just use the first defined node, though this shouldn't happen
                logger.warn("No treeNodeName passed in request for child nodes for node [${cs.get("treeNodeId")}] in tree [${this.location}], using first node in tree definition.")
                currentSubNodeList = nodeByName.values().first().subNodeList
            }
        }

        List outputNodeList = getChildNodes(currentSubNodeList, eci, cs)

        // logger.warn("========= outputNodeList = ${outputNodeList}")
        eci.getWeb().sendJsonResponse(outputNodeList)
    }

    List<Map> getChildNodes(List<TreeSubNode> currentSubNodeList, ExecutionContextImpl eci, ContextStack cs) {
        List<Map> outputNodeList = []

        for (TreeSubNode tsn in currentSubNodeList) {
            // check condition
            if (tsn.condition != null && !tsn.condition.checkCondition(eci)) continue
            // run actions
            if (tsn.actions != null) tsn.actions.run(eci)

            TreeNode tn = nodeByName.get(tsn.treeSubNodeNode."@node-name")

            // iterate over the list and add a response node for each entry
            String nodeListName = tsn.treeSubNodeNode."@list" ?: "nodeList"
            List nodeList = (List) eci.getResource().evaluateContextField(nodeListName, "")
            // logger.warn("======= nodeList named [${nodeListName}]: ${nodeList}")
            Iterator i = nodeList?.iterator()
            int index = 0
            while (i?.hasNext()) {
                Object nodeListEntry = i.next()

                cs.push()
                try {
                    cs.put("nodeList_entry", nodeListEntry)
                    cs.put("nodeList_index", index)
                    cs.put("nodeList_has_next", i.hasNext())

                    // check condition
                    if (tn.condition != null && !tn.condition.checkCondition(eci)) continue
                    // run actions
                    if (tn.actions != null) tn.actions.run(eci)

                    String id = eci.getResource().evaluateStringExpand((String) tn.linkNode."@id", tn.location + ".id")
                    String text = eci.getResource().evaluateStringExpand((String) tn.linkNode."@text", tn.location + ".text")
                    ScreenUrlInfo.UrlInstance urlInstance = cs.get("sri").makeUrlByTypeGroovyNode(tn.linkNode."@url", tn.linkNode."@url-type" ?: "transition", tn.linkNode, tn.linkNode."@expand-transition-url" ?: "true")

                    // now get children to check if has some, and if in treeOpenPath include them
                    List<Map> childNodeList = null
                    cs.push()
                    try {
                        cs.put("treeNodeId", id)
                        childNodeList = getChildNodes(tn.subNodeList, eci, cs)
                    } finally {
                        cs.pop()
                    }

                    String urlText = urlInstance.getUrlWithParams()
                    if (tn.linkNode."@dynamic-load-id") {
                        String loadId = tn.linkNode."@dynamic-load-id"
                        // NOTE: the void(0) is needed for Firefox and other browsers that render the result of the JS expression
                        urlText = "javascript:{\$('#${loadId}').load('${urlText}'); void(0);}"
                    }

                    Map subNodeMap = [id:id, text:text, a_attr:[href:urlText], li_attr:["treeNodeName":tn.treeNodeNode."@name"]]
                    if (((String) cs.get("treeOpenPath"))?.startsWith(id)) {
                        subNodeMap.state = [opened:true, selected:(cs.get("treeOpenPath") == id)]
                        subNodeMap.children = childNodeList
                    } else {
                        subNodeMap.children = childNodeList as boolean
                    }
                    outputNodeList.add(subNodeMap)
                    /* structure of JSON object from jstree docs:
                        {
                          id          : "string" // will be autogenerated if omitted
                          text        : "string" // node text
                          icon        : "string" // string for custom
                          state       : {
                            opened    : boolean  // is the node open
                            disabled  : boolean  // is the node disabled
                            selected  : boolean  // is the node selected
                          },
                          children    : []  // array of strings or objects
                          li_attr     : {}  // attributes for the generated LI node
                          a_attr      : {}  // attributes for the generated A node
                        }
                     */
                } finally {
                    cs.pop()
                }
            }
        }

        // logger.warn("========= outputNodeList: ${outputNodeList}")
        return outputNodeList
    }

    static class TreeNode {
        protected ScreenTree screenTree
        protected Node treeNodeNode
        protected String location

        protected XmlAction condition = null
        protected XmlAction actions = null
        protected Node linkNode = null
        protected List<TreeSubNode> subNodeList = []

        TreeNode(ScreenTree screenTree, Node treeNodeNode, String location) {
            this.screenTree = screenTree
            this.treeNodeNode = treeNodeNode
            this.location = location
            this.linkNode = treeNodeNode.link[0]

            // prep condition
            if (treeNodeNode.condition && treeNodeNode.condition[0].children()) {
                // the script is effectively the first child of the condition element
                condition = new XmlAction(screenTree.ecfi, (Node) treeNodeNode."condition"[0].children()[0], location + ".condition")
            }
            // prep actions
            if (treeNodeNode.actions) actions = new XmlAction(screenTree.ecfi, (Node) treeNodeNode."actions"[0], location + ".actions")

            // prep tree-sub-node
            for (Node treeSubNodeNode in treeNodeNode."tree-sub-node")
                subNodeList.add(new TreeSubNode(screenTree, treeSubNodeNode, location + ".subnode." + treeSubNodeNode."@node-name"))
        }
    }

    static class TreeSubNode {
        protected ScreenTree screenTree
        protected Node treeSubNodeNode
        protected String location

        protected XmlAction condition = null
        protected XmlAction actions = null

        TreeSubNode(ScreenTree screenTree, Node treeSubNodeNode, String location) {
            this.screenTree = screenTree
            this.treeSubNodeNode = treeSubNodeNode
            this.location = location

            // prep condition
            if (treeSubNodeNode.condition && treeSubNodeNode.condition[0].children()) {
                // the script is effectively the first child of the condition element
                condition = new XmlAction(screenTree.ecfi, (Node) treeSubNodeNode."condition"[0].children()[0], location + ".condition")
            }
            // prep actions
            if (treeSubNodeNode.actions) actions = new XmlAction(screenTree.ecfi, (Node) treeSubNodeNode."actions"[0], location + ".actions")
        }
    }
}
