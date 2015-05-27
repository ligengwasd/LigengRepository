<div id="footer">
    <ul>
        <li class="first">${nowTimestamp?datetime?string.short} - <a href="<@ofbizUrl>ListTimezones</@ofbizUrl>">${timeZone.getDisplayName(timeZone.useDaylightTime(), Static["java.util.TimeZone"].LONG, locale)}</a></li>
        <li><a href="<@ofbizUrl>ListLocales</@ofbizUrl>">${locale.getDisplayName(locale)}</a></li>
        <li class="last"><a href="<@ofbizUrl>ListVisualThemes</@ofbizUrl>">${uiLabelMap.CommonVisualThemes}</a></li>
    </ul>
  <p>
  ${uiLabelMap.CommonCopyright} (c) 2012-${nowTimestamp?string("yyyy")} <a href="http://www.apache.org" target="_blank">趣付 </a>. ${uiLabelMap.CommonPoweredBy} <a href="http://ofbiz.apache.org" target="_blank">OFBiz.</a> <#include "ofbizhome://runtime/svninfo.ftl" />
  </p>
</div>
</div>
<#if layoutSettings.VT_FTR_JAVASCRIPT?has_content>
  <#list layoutSettings.VT_FTR_JAVASCRIPT as javaScript>
    <script type="text/javascript" src="<@ofbizContentUrl>${StringUtil.wrapString(javaScript)}</@ofbizContentUrl>" type="text/javascript"></script>
  </#list>
</#if>
</body>
</html>
