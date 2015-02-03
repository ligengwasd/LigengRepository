/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.ofbiz.widget.screen;

import java.io.IOException;
import java.util.Map;

import org.ofbiz.entity.GenericValue;
import org.ofbiz.widget.screen.ModelScreenWidget.KdTabStrip;
import org.ofbiz.base.util.GeneralException;

/**
 * Widget Library - Screen String Renderer interface.
 */
public interface ScreenStringRenderer {
    public String getRendererName();
    public void renderScreenBegin(Appendable writer, Map<String, Object> context) throws IOException;
    public void renderScreenEnd(Appendable writer, Map<String, Object> context) throws IOException;
    public void renderSectionBegin(Appendable writer, Map<String, Object> context, ModelScreenWidget.Section section) throws IOException;
    public void renderSectionEnd(Appendable writer, Map<String, Object> context, ModelScreenWidget.Section section) throws IOException;
    public void renderColumnContainer(Appendable writer, Map<String, Object> context, ModelScreenWidget.ColumnContainer columnContainer) throws IOException;
    public void renderContainerBegin(Appendable writer, Map<String, Object> context, ModelScreenWidget.Container container) throws IOException;
    public void renderContainerEnd(Appendable writer, Map<String, Object> context, ModelScreenWidget.Container container) throws IOException;
    public void renderContentBegin(Appendable writer, Map<String, Object> context, ModelScreenWidget.Content content) throws IOException;
    public void renderContentBody(Appendable writer, Map<String, Object> context, ModelScreenWidget.Content content) throws IOException;
    public void renderContentEnd(Appendable writer, Map<String, Object> context, ModelScreenWidget.Content content) throws IOException;
    public void renderSubContentBegin(Appendable writer, Map<String, Object> context, ModelScreenWidget.SubContent content) throws IOException;
    public void renderSubContentBody(Appendable writer, Map<String, Object> context, ModelScreenWidget.SubContent content) throws IOException;
    public void renderSubContentEnd(Appendable writer, Map<String, Object> context, ModelScreenWidget.SubContent content) throws IOException;

    public void renderHorizontalSeparator(Appendable writer, Map<String, Object> context, ModelScreenWidget.HorizontalSeparator separator) throws IOException;
    public void renderLabel(Appendable writer, Map<String, Object> context, ModelScreenWidget.Label label) throws IOException;
    public void renderLink(Appendable writer, Map<String, Object> context, ModelScreenWidget.Link link) throws IOException;
    public void renderImage(Appendable writer, Map<String, Object> context, ModelScreenWidget.Image image) throws IOException;
    /**renderKdGridColumn*/
    public void renderKdGridColumn(Appendable writer, Map<String, Object> context,ModelScreenWidget.KdGridColumn kdGridColumn)throws IOException;
    /**renderKdGridField*/
    public void renderKdGridField(Appendable writer, Map<String, Object> context,ModelScreenWidget.KdGridField kdGridField)throws IOException;
    /**renderKdGridCommand*/
    public void renderKdGridCommand(Appendable writer, Map<String, Object> context,ModelScreenWidget.KdGridCommand kdGridCommand)throws IOException;
    /**Kd-Grid-toolbar-sub*/
    public void renderKdGridToolbarSub(Appendable writer, Map<String, Object> context,ModelScreenWidget.KdGridToolbarSub kdGridToolbarSub)throws IOException;
    
    public void renderContentFrame(Appendable writer, Map<String, Object> context, ModelScreenWidget.Content content) throws IOException;
    public void renderScreenletBegin(Appendable writer, Map<String, Object> context, boolean collapsed, ModelScreenWidget.Screenlet screenlet) throws IOException;
    public void renderScreenletSubWidget(Appendable writer, Map<String, Object> context, ModelScreenWidget subWidget, ModelScreenWidget.Screenlet screenlet) throws GeneralException, IOException;
    public void renderScreenletEnd(Appendable writer, Map<String, Object> context, ModelScreenWidget.Screenlet screenlet) throws IOException;

    public void renderPortalPageBegin(Appendable writer, Map<String, Object> context, ModelScreenWidget.PortalPage portalPage) throws GeneralException, IOException;
    public void renderPortalPageEnd(Appendable writer, Map<String, Object> context, ModelScreenWidget.PortalPage portalPage) throws GeneralException, IOException;
    public void renderPortalPageColumnBegin(Appendable writer, Map<String, Object> context, ModelScreenWidget.PortalPage portalPage, GenericValue portalPageColumn) throws GeneralException, IOException;
    public void renderPortalPageColumnEnd(Appendable writer, Map<String, Object> context, ModelScreenWidget.PortalPage portalPage, GenericValue portalPageColumn) throws GeneralException, IOException;
    public void renderPortalPagePortletBegin(Appendable writer, Map<String, Object> context, ModelScreenWidget.PortalPage portalPage, GenericValue portalPortlet) throws GeneralException, IOException;
    public void renderPortalPagePortletBody(Appendable writer, Map<String, Object> context, ModelScreenWidget.PortalPage portalPage, GenericValue portalPortlet) throws GeneralException, IOException;
    public void renderPortalPagePortletEnd(Appendable writer, Map<String, Object> context, ModelScreenWidget.PortalPage portalPage, GenericValue portalPortlet) throws GeneralException, IOException;
    
    /**KdTabStrip*/
    public void renderKdTabStripBegin(Appendable writer, Map<String, Object> context,ModelScreenWidget.KdTabStrip kdTabStrip) throws IOException;
    public void renderKdTabStripEnd(Appendable writer, Map<String, Object> context,ModelScreenWidget.KdTabStrip kdTabStrip) throws IOException;
    /**KdTabStripUl*/
    public void renderKdUlBegin(Appendable writer,Map<String, Object> context, ModelScreenWidget.KdUl kdUl)throws IOException;
    public void renderKdUlEnd(Appendable writer,Map<String, Object> context, ModelScreenWidget.KdUl kdUl)throws IOException;
    /**KdTabStripLi*/
    public void renderKdLiBegin(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdLi kdLi)throws IOException;
    public void renderKdLiEnd(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdLi kdLi)throws IOException;
    /**KdGrid*/
    public void renderKdGridBegin(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdGrid kdGrid)throws IOException;
    public void renderKdGridEnd(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdGrid kdGrid)throws IOException;
    /**KdGridDataSource*/
    public void renderKdGridDataSourceBegin(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdGrid.KdGridDataSource kdGridDataSource)throws IOException;
    public void renderKdGridDataSourceEnd(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdGrid.KdGridDataSource kdGridDataSource)throws IOException;
    /**KdGridColumns*/
    public void renderKdGridColumnsBegin(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdGridColumns kdGridColumns)throws IOException;
    public void renderKdGridColumnsEnd(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdGridColumns kdGridColumns)throws IOException;
    /**KdGridToolbar*/
    public void renderKdGridToolbarBegin(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdGridToolbar kdGridToolbar)throws IOException;
    public void renderKdGridToolbarEnd(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdGridToolbar kdGridToolbar)throws IOException;
    /**KdMenu*/
    public void renderKdMenuBegin(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdMenu kdMenu)throws IOException;
    public void renderKdMenuEnd(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdMenu kdMenu)throws IOException;
    /**kdWindow*/
    public void renderKdWindowBegin(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdWindow kdWindow)throws IOException;
    public void renderKdWindowEnd(Appendable writer,Map<String, Object> context,ModelScreenWidget.KdWindow kdWindow)throws IOException;
    
}



