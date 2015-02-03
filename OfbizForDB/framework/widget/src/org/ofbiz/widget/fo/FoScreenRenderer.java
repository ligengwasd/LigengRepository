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
package org.ofbiz.widget.fo;

import java.io.IOException;
import java.util.Map;

import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.widget.ModelWidget;
import org.ofbiz.widget.html.HtmlWidgetRenderer;
import org.ofbiz.widget.screen.ModelScreenWidget;
import org.ofbiz.widget.screen.ModelScreenWidget.KdGrid;
import org.ofbiz.widget.screen.ModelScreenWidget.KdGridColumn;
import org.ofbiz.widget.screen.ModelScreenWidget.KdGridColumns;
import org.ofbiz.widget.screen.ModelScreenWidget.KdGridCommand;
import org.ofbiz.widget.screen.ModelScreenWidget.KdGridDataSource;
import org.ofbiz.widget.screen.ModelScreenWidget.KdGridField;
import org.ofbiz.widget.screen.ModelScreenWidget.KdGridToolbar;
import org.ofbiz.widget.screen.ModelScreenWidget.KdGridToolbarSub;
import org.ofbiz.widget.screen.ModelScreenWidget.KdLi;
import org.ofbiz.widget.screen.ModelScreenWidget.KdMenu;
import org.ofbiz.widget.screen.ModelScreenWidget.KdTabStrip;
import org.ofbiz.widget.screen.ModelScreenWidget.KdUl;
import org.ofbiz.widget.screen.ModelScreenWidget.KdWindow;
import org.ofbiz.widget.screen.ScreenStringRenderer;
import org.ofbiz.widget.screen.ModelScreenWidget.ColumnContainer;
import org.ofbiz.entity.GenericValue;

/**
 * Widget Library - HTML Form Renderer implementation
 * 
 * @deprecated Use MacroScreenRenderer.
 */
public class FoScreenRenderer extends HtmlWidgetRenderer implements
		ScreenStringRenderer {

	@Override
	public void appendWhitespace(Appendable writer) throws IOException {
		// TODO Auto-generated method stub
		super.appendWhitespace(writer);
	}

	@Override
	public String buildBoundaryComment(String boundaryType, String widgetType,
			String widgetName) {
		// TODO Auto-generated method stub
		return super.buildBoundaryComment(boundaryType, widgetType, widgetName);
	}

	@Override
	public void renderBeginningBoundaryComment(Appendable writer,
			String widgetType, ModelWidget modelWidget) throws IOException {
		// TODO Auto-generated method stub
		super.renderBeginningBoundaryComment(writer, widgetType, modelWidget);
	}

	@Override
	public void renderEndingBoundaryComment(Appendable writer,
			String widgetType, ModelWidget modelWidget) throws IOException {
		// TODO Auto-generated method stub
		super.renderEndingBoundaryComment(writer, widgetType, modelWidget);
	}

	@Override
	public void renderKdTabStripBegin(Appendable writer,
			Map<String, Object> context, KdTabStrip kdTabStrip)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdTabStripEnd(Appendable writer,
			Map<String, Object> context, KdTabStrip kdTabStrip)
			throws IOException {
		// TODO Auto-generated method stub

	}

	public static final String module = FoScreenRenderer.class.getName();

	public FoScreenRenderer() {
	}

	// This is a util method to get the style from a property file
	public static String getFoStyle(String styleName) {
		String value = UtilProperties.getPropertyValue("fo-styles.properties",
				styleName);
		if (value.equals(styleName)) {
			return "";
		}
		return value;
	}

	public String getRendererName() {
		return "xsl-fo";
	}

	public void renderScreenBegin(Appendable writer, Map<String, Object> context)
			throws IOException {
	}

	public void renderScreenEnd(Appendable writer, Map<String, Object> context)
			throws IOException {

	}

	public void renderSectionBegin(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.Section section)
			throws IOException {
		if (section.isMainSection) {
			this.widgetCommentsEnabled = ModelWidget
					.widgetBoundaryCommentsEnabled(context);
		}
		renderBeginningBoundaryComment(writer, section.isMainSection ? "Screen"
				: "Section Widget", section);
	}

	public void renderSectionEnd(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.Section section)
			throws IOException {
		renderEndingBoundaryComment(writer, section.isMainSection ? "Screen"
				: "Section Widget", section);
	}

	public void renderContainerBegin(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.Container container)
			throws IOException {
		writer.append("<fo:block");

		String style = container.getStyle(context);
		if (UtilValidate.isNotEmpty(style)) {
			writer.append(" ");
			writer.append(FoScreenRenderer.getFoStyle(style));
		}
		writer.append(">");
		appendWhitespace(writer);
	}

	public void renderContainerEnd(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.Container container)
			throws IOException {
		writer.append("</fo:block>");
		appendWhitespace(writer);
	}

	public void renderLabel(Appendable writer, Map<String, Object> context,
			ModelScreenWidget.Label label) throws IOException {
		String labelText = label.getText(context);
		if (UtilValidate.isEmpty(labelText)) {
			// nothing to render
			return;
		}
		// open tag
		String style = label.getStyle(context);
		if (UtilValidate.isNotEmpty(style)) {
			writer.append("<fo:inline ");
			writer.append(FoScreenRenderer.getFoStyle(style));
			writer.append(">");
			// the text
			writer.append(labelText);
			// close tag
			writer.append("</fo:inline>");
		} else {
			writer.append(labelText);
		}
		appendWhitespace(writer);
	}

	public void renderHorizontalSeparator(Appendable writer,
			Map<String, Object> context,
			ModelScreenWidget.HorizontalSeparator separator) throws IOException {
		writer.append("<fo:block>");
		appendWhitespace(writer);
		writer.append("<fo:leader leader-length=\"100%\" leader-pattern=\"rule\" rule-style=\"solid\" rule-thickness=\"0.1mm\" color=\"black\"/>");
		appendWhitespace(writer);
		writer.append("</fo:block>");
		appendWhitespace(writer);
	}

	public void renderLink(Appendable writer, Map<String, Object> context,
			ModelScreenWidget.Link link) throws IOException {
		// TODO: not implemented
	}

	public void renderImage(Appendable writer, Map<String, Object> context,
			ModelScreenWidget.Image image) throws IOException {
		// TODO: not implemented
	}

	public void renderContentBegin(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.Content content)
			throws IOException {
		// TODO: not implemented
	}

	public void renderContentBody(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.Content content)
			throws IOException {
		// TODO: not implemented
	}

	public void renderContentEnd(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.Content content)
			throws IOException {
		// TODO: not implemented
	}

	public void renderContentFrame(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.Content content)
			throws IOException {
		// TODO: not implemented
	}

	public void renderSubContentBegin(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.SubContent content)
			throws IOException {
		// TODO: not implemented
	}

	public void renderSubContentBody(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.SubContent content)
			throws IOException {
		// TODO: not implemented
	}

	public void renderSubContentEnd(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.SubContent content)
			throws IOException {
		// TODO: not implemented
	}

	public void renderScreenletBegin(Appendable writer,
			Map<String, Object> context, boolean collapsed,
			ModelScreenWidget.Screenlet screenlet) throws IOException {
		// TODO: not implemented
	}

	public void renderScreenletSubWidget(Appendable writer,
			Map<String, Object> context, ModelScreenWidget subWidget,
			ModelScreenWidget.Screenlet screenlet) throws GeneralException {
		// TODO: not implemented
	}

	public void renderScreenletEnd(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.Screenlet screenlet)
			throws IOException {
		// TODO: not implemented
	}

	public void renderPortalPageBegin(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.PortalPage portalPage)
			throws GeneralException, IOException {
		// TODO: not implemented
	}

	public void renderPortalPageEnd(Appendable writer,
			Map<String, Object> context, ModelScreenWidget.PortalPage portalPage)
			throws GeneralException, IOException {
		// TODO: not implemented
	}

	public void renderPortalPageColumnBegin(Appendable writer,
			Map<String, Object> context,
			ModelScreenWidget.PortalPage portalPage,
			GenericValue portalPageColumn) throws GeneralException, IOException {
		// TODO: not implemented
	}

	public void renderPortalPageColumnEnd(Appendable writer,
			Map<String, Object> context,
			ModelScreenWidget.PortalPage portalPage,
			GenericValue portalPageColumn) throws GeneralException, IOException {
		// TODO: not implemented
	}

	public void renderPortalPagePortletBegin(Appendable writer,
			Map<String, Object> context,
			ModelScreenWidget.PortalPage portalPage, GenericValue portalPortlet)
			throws GeneralException, IOException {
		// TODO: not implemented
	}

	public void renderPortalPagePortletEnd(Appendable writer,
			Map<String, Object> context,
			ModelScreenWidget.PortalPage portalPage, GenericValue portalPortlet)
			throws GeneralException, IOException {
		// TODO: not implemented
	}

	public void renderPortalPagePortletBody(Appendable writer,
			Map<String, Object> context,
			ModelScreenWidget.PortalPage portalPage, GenericValue portalPortlet)
			throws GeneralException, IOException {
		// TODO: not implemented
	}

	@Override
	public void renderColumnContainer(Appendable writer,
			Map<String, Object> context, ColumnContainer columnContainer)
			throws IOException {
		// TODO: not implemented
	}

	@Override
	public void renderKdGridColumn(Appendable writer,
			Map<String, Object> context, KdGridColumn kdGridColumn)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdGridField(Appendable writer,
			Map<String, Object> context, KdGridField kdGridField)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdGridCommand(Appendable writer,
			Map<String, Object> context, KdGridCommand kdGridCommand)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdGridToolbarSub(Appendable writer,
			Map<String, Object> context, KdGridToolbarSub kdGridToolbarSub)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdUlBegin(Appendable writer, Map<String, Object> context,
			KdUl kdUl) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdUlEnd(Appendable writer, Map<String, Object> context,
			KdUl kdUl) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdLiBegin(Appendable writer, Map<String, Object> context,
			KdLi kdLi) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdLiEnd(Appendable writer, Map<String, Object> context,
			KdLi kdLi) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdGridBegin(Appendable writer,
			Map<String, Object> context, KdGrid kdGrid) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdGridEnd(Appendable writer, Map<String, Object> context,
			KdGrid kdGrid) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdGridDataSourceBegin(Appendable writer,
			Map<String, Object> context, KdGridDataSource kdGridDataSource)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdGridDataSourceEnd(Appendable writer,
			Map<String, Object> context, KdGridDataSource kdGridDataSource)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdGridColumnsBegin(Appendable writer,
			Map<String, Object> context, KdGridColumns kdGridColumns)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdGridColumnsEnd(Appendable writer,
			Map<String, Object> context, KdGridColumns kdGridColumns)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdGridToolbarBegin(Appendable writer,
			Map<String, Object> context, KdGridToolbar kdGridToolbar)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdGridToolbarEnd(Appendable writer,
			Map<String, Object> context, KdGridToolbar kdGridToolbar)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdMenuBegin(Appendable writer,
			Map<String, Object> context, KdMenu kdMenu) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdMenuEnd(Appendable writer, Map<String, Object> context,
			KdMenu kdMenu) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderKdWindowBegin(Appendable writer,
			Map<String, Object> context, KdWindow kdWindow) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderKdWindowEnd(Appendable writer,
			Map<String, Object> context, KdWindow kdWindow) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
