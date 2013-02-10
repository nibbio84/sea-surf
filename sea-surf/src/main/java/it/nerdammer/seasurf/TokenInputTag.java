package it.nerdammer.seasurf;

import it.nerdammer.seasurf.config.Preferences;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringEscapeUtils;

public class TokenInputTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	@Override
	public int doStartTag() throws JspException {
		
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		
		Preferences prefs = ConfigHandler.getConfig().getPreferences();
		String token = TokenManager.getStoredToken(req, prefs);
		
		if(token!=null) {
			String parameterName = TokenManager.getRequestKeyName(prefs);
			
			try {
				pageContext.getOut().println("<input type=\"hidden\" name=\"" + StringEscapeUtils.escapeHtml(parameterName) + "\" value=\"" + StringEscapeUtils.escapeHtml(token) + "\" />");
			} catch(IOException e) {
				throw new JspException(e);
			}
		}
		
		return SKIP_BODY;
	}
	
}
