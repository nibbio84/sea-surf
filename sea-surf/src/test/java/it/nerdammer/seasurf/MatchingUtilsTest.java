package it.nerdammer.seasurf;

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
		
		Assert.assertTrue(MatchingUtils.matchesUrlPattern(req, "/my*"));
		Assert.assertTrue(MatchingUtils.matchesUrlPattern(req, "/***y*"));
		Assert.assertTrue(MatchingUtils.matchesUrlPattern(req, "/**m*y*"));
		Assert.assertFalse(MatchingUtils.matchesUrlPattern(req, "/*e**g*"));
		Assert.assertFalse(MatchingUtils.matchesUrlPattern(req, "/my"));
		Assert.assertTrue(MatchingUtils.matchesUrlPattern(req, "/mypage"));
		Assert.assertFalse(MatchingUtils.matchesUrlPattern(req, "/mypAge"));
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
		
		Assert.assertTrue(MatchingUtils.matchesUrlPattern(req, "/my*"));
		Assert.assertTrue(MatchingUtils.matchesUrlPattern(req, "/***y*"));
		Assert.assertTrue(MatchingUtils.matchesUrlPattern(req, "/**m*y*"));
		Assert.assertFalse(MatchingUtils.matchesUrlPattern(req, "/*e**g*"));
		Assert.assertFalse(MatchingUtils.matchesUrlPattern(req, "/my"));
		Assert.assertTrue(MatchingUtils.matchesUrlPattern(req, "/mypage"));
		Assert.assertFalse(MatchingUtils.matchesUrlPattern(req, "/mypAge"));
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
		
		Assert.assertFalse(MatchingUtils.matchesUrlPattern(req, "/my*"));
		Assert.assertTrue(MatchingUtils.matchesUrlPattern(req, "/***y*"));
		Assert.assertTrue(MatchingUtils.matchesUrlPattern(req, "/**m*y*"));
		Assert.assertFalse(MatchingUtils.matchesUrlPattern(req, "/*e**g*"));
		Assert.assertFalse(MatchingUtils.matchesUrlPattern(req, "/my"));
		Assert.assertFalse(MatchingUtils.matchesUrlPattern(req, "/mypage"));
		Assert.assertFalse(MatchingUtils.matchesUrlPattern(req, "/mypAge"));
	}
	
		
}
