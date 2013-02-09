package it.nerdammer.seasurf;

import java.io.IOException;

import it.nerdammer.seasurf.stuff.BlockDetectionException;
import it.nerdammer.seasurf.stuff.ForwardDetectionException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StdBaseTest {

	protected RequestFilter init(final String configFile) {
		ConfigHandler.init(new ConfigLoader() {
			@Override
			public String getConfigFileName() {
				return configFile;
			}
		});
		RequestFilter filter = new RequestFilter() {
			@Override
			protected void block(HttpServletRequest req,
					HttpServletResponse res, String cause) {
				throw new BlockDetectionException();
			}
		};
		
		return filter;
	}
	
	protected FilterChain getTestChain() {
		return new FilterChain() {
			
			public void doFilter(ServletRequest arg0, ServletResponse arg1)
					throws IOException, ServletException {
				throw new ForwardDetectionException();
			}
		};
	}
	
}
