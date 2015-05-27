package org.ofbiz.widget.screen;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.ofbiz.base.location.FlexibleLocation;
import org.ofbiz.base.util.UtilXml;
import org.ofbiz.base.util.cache.UtilCache;
import org.ofbiz.widget.screen.ModelScreenWidget.KdGrid;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class GridFactory {
	
	public static final String module = GridFactory.class.getName();
	//widget.form.locationResource
	//private static final UtilCache<String, KdGrid> gridLocationCache=  UtilCache.createUtilCache("",0,0,false);
	
    public static KdGrid getKdGridFromLocation(String resourceName, String kdGridName,ModelScreen modelScreen) throws IOException, SAXException, ParserConfigurationException {
    	KdGrid kdGrid = null;
    	//String cacheKey = resourceName + "#" + kdGridName;
    	//KdGrid kdGrid = gridLocationCache.get(cacheKey);
	   	//if (kdGrid == null) {
	   		URL kdGridFileUrl = FlexibleLocation.resolveLocation(resourceName);
	   		Document kdGridFileDoc = UtilXml.readXmlDocument(kdGridFileUrl,true,true);
	   		Element kdGridElement = UtilXml.firstChildElement(kdGridFileDoc.getDocumentElement(),"kd-grid","name",kdGridName);
	   		kdGrid = new KdGrid(modelScreen, kdGridElement);
		//}
	   	return kdGrid;
    }
}
