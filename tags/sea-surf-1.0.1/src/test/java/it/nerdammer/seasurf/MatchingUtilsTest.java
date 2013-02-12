package it.nerdammer.seasurf;

import it.nerdammer.seasurf.config.Methods;
import it.nerdammer.seasurf.config.RequestType;
import it.nerdammer.seasurf.stuff.HttpServletRequestMock;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;

public class MatchingUtilsTest {
	
	@Test
	public void testPatternMatch1() {
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getContextPath() {
				return "/test";
			}
			@Override
			public String getRequestURI() {
				return "/test/mypage";
			}
			@Override
			public String getQueryString() {
				return "?ciao=true";
			}
		};
		
		Assert.assertTrue(MatchingUtils.resourceMatchesUrlPattern(req, "/my*"));
		Assert.assertTrue(MatchingUtils.resourceMatchesUrlPattern(req, "/***y*"));
		Assert.assertTrue(MatchingUtils.resourceMatchesUrlPattern(req, "/**m*y*"));
		Assert.assertFalse(MatchingUtils.resourceMatchesUrlPattern(req, "/*e**g*"));
		Assert.assertFalse(MatchingUtils.resourceMatchesUrlPattern(req, "/my"));
		Assert.assertTrue(MatchingUtils.resourceMatchesUrlPattern(req, "/mypage"));
		Assert.assertFalse(MatchingUtils.resourceMatchesUrlPattern(req, "/mypAge"));
	}
	
	@Test
	public void testPatternMatch2() {
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getContextPath() {
				return "";
			}
			@Override
			public String getRequestURI() {
				return "/mypage";
			}
			@Override
			public String getQueryString() {
				return "?ciao=true";
			}
		};
		
		Assert.assertTrue(MatchingUtils.resourceMatchesUrlPattern(req, "/my*"));
		Assert.assertTrue(MatchingUtils.resourceMatchesUrlPattern(req, "/***y*"));
		Assert.assertTrue(MatchingUtils.resourceMatchesUrlPattern(req, "/**m*y*"));
		Assert.assertFalse(MatchingUtils.resourceMatchesUrlPattern(req, "/*e**g*"));
		Assert.assertFalse(MatchingUtils.resourceMatchesUrlPattern(req, "/my"));
		Assert.assertTrue(MatchingUtils.resourceMatchesUrlPattern(req, "/mypage"));
		Assert.assertFalse(MatchingUtils.resourceMatchesUrlPattern(req, "/mypAge"));
	}
	
	@Test
	public void testPatternMatch3() {
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getContextPath() {
				return "/test";
			}
			@Override
			public String getRequestURI() {
				return "/test//mypage";
			}
			@Override
			public String getQueryString() {
				return "?ciao=true";
			}
		};
		
		Assert.assertFalse(MatchingUtils.resourceMatchesUrlPattern(req, "/my*"));
		Assert.assertTrue(MatchingUtils.resourceMatchesUrlPattern(req, "/***y*"));
		Assert.assertTrue(MatchingUtils.resourceMatchesUrlPattern(req, "/**m*y*"));
		Assert.assertFalse(MatchingUtils.resourceMatchesUrlPattern(req, "/*e**g*"));
		Assert.assertFalse(MatchingUtils.resourceMatchesUrlPattern(req, "/my"));
		Assert.assertFalse(MatchingUtils.resourceMatchesUrlPattern(req, "/mypage"));
		Assert.assertFalse(MatchingUtils.resourceMatchesUrlPattern(req, "/mypAge"));
	}
	
	
	@Test
	public void testMethodMatch1() {
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getMethod() {
				return "post";
			}
		};
		
		Methods methods = new Methods();
		methods.getMethod().add("TRACE");
		methods.getMethod().add("POST");
		Assert.assertTrue(MatchingUtils.resourceMatchesMethods(req, methods));
	}
	
	@Test
	public void testMethodMatch2() {
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getMethod() {
				return "get";
			}
		};
		
		Methods methods = new Methods();
		methods.getMethod().add("TRACE");
		methods.getMethod().add("POST");
		Assert.assertFalse(MatchingUtils.resourceMatchesMethods(req, methods));
	}
	
	@Test
	public void testMethodMatch3() {
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getMethod() {
				return " post ";
			}
		};
		
		Methods methods = new Methods();
		methods.getMethod().add("TRACE");
		methods.getMethod().add("POST");
		Assert.assertTrue(MatchingUtils.resourceMatchesMethods(req, methods));
	}
	
	@Test
	public void testRequestMatch1() {
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getContextPath() {
				return "/test";
			}
			@Override
			public String getRequestURI() {
				return "/test/mypage";
			}
			@Override
			public String getMethod() {
				return " post ";
			}
		};
		
		RequestType reqType = new RequestType();
		
		Methods methods = new Methods();
		methods.getMethod().add("TRACE");
		methods.getMethod().add("POST");
		reqType.setMethods(methods);
		
		reqType.setPattern("/mypage");
		
		
		Assert.assertTrue(MatchingUtils.resourceMatches(req, reqType));
	}
	
	@Test
	public void testRequestMatch2() {
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getContextPath() {
				return "/test";
			}
			@Override
			public String getRequestURI() {
				return "/test/mypage";
			}
			@Override
			public String getMethod() {
				return " post ";
			}
		};
		
		RequestType reqType = new RequestType();
		
		reqType.setPattern("/mypage");
		
		
		Assert.assertTrue(MatchingUtils.resourceMatches(req, reqType));
		
		reqType.setPattern("/myp*ge");
		
		
		Assert.assertTrue(MatchingUtils.resourceMatches(req, reqType));
	}
	
	
		
}
