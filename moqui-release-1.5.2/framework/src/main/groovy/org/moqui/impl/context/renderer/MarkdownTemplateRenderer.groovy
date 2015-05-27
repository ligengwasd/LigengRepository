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
package org.moqui.impl.context.renderer

import org.markdown4j.Markdown4jProcessor
import org.moqui.context.Cache
import org.moqui.context.ExecutionContextFactory
import org.moqui.context.TemplateRenderer
import org.moqui.impl.context.ExecutionContextFactoryImpl
import org.moqui.impl.screen.ScreenRenderImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MarkdownTemplateRenderer implements TemplateRenderer {
    protected final static Logger logger = LoggerFactory.getLogger(MarkdownTemplateRenderer.class)

    protected ExecutionContextFactoryImpl ecfi
    protected Cache templateMarkdownLocationCache

    MarkdownTemplateRenderer() { }

    TemplateRenderer init(ExecutionContextFactory ecf) {
        this.ecfi = (ExecutionContextFactoryImpl) ecf
        this.templateMarkdownLocationCache = ecfi.cacheFacade.getCache("resource.markdown.location")
        return this
    }

    void render(String location, Writer writer) {
        String mdText = templateMarkdownLocationCache.get(location)
        if (mdText) {
            writer.write(mdText)
            return
        }

        String sourceText = ecfi.resourceFacade.getLocationText(location, false)
        if (!sourceText) {
            logger.warn("In Markdown template render got no text from location ${location}")
            return
        }

        Markdown4jProcessor markdown4jProcessor = new Markdown4jProcessor()
        //ScreenRenderImpl sri = (ScreenRenderImpl) ecfi.getExecutionContext().getContext().get("sri")
        // how to set base URL? if (sri != null) builder.setBase(sri.getBaseLinkUri())

        mdText = markdown4jProcessor.process(sourceText)

        if (mdText) {
            templateMarkdownLocationCache.put(location, mdText)
            writer.write(mdText)
        }
    }

    String stripTemplateExtension(String fileName) {
        if (fileName.contains(".md")) return fileName.replace(".md", "")
        else if (fileName.contains(".markdown")) return fileName.replace(".markdown", "")
        else return fileName
    }

    void destroy() { }
}
