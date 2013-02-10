package it.nerdammer.seasurf;

import it.nerdammer.seasurf.stuff.BlockDetectionException;
import it.nerdammer.seasurf.stuff.ForwardDetectionException;
import it.nerdammer.seasurf.stuff.HttpServletRequestMock;
import it.nerdammer.seasurf.stuff.HttpServletResponseMock;
import it.nerdammer.seasurf.stuff.HttpSessionMock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;

public class StdTest2 extends StdBaseTest {

	@Test
	public void test1() {
		
		RequestFilter filter = init("std-test-2.xml");
		
		final String[] res = new String[1];
		
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getMethod() {
				return "post";
			}
			@Override
			public String getRequestURI() {
				return "/basepath/action";
			}
			@Override
			public String getContextPath() {
				return "/basepath";
			}
			@Override
			public HttpSession getSession(boolean create) {
				return new HttpSessionMock() {
					@Override
					public void setAttribute(String k, Object v) {
						if(k.equals(TokenManager.DEFAULT_STORE_KEY_NAME)) {
							res[0] = (String)v;
						}
					}
				};
			}
		};
		
		HttpServletResponse resp = new HttpServletResponseMock();
		
		try {
			filter.doFilter(req, resp, getTestChain());
		} catch(BlockDetectionException e) {
			// ok
		} catch(ForwardDetectionException e) {
			throw e;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		
		Assert.assertEquals(TokenManager.DEFAULT_TOKEN_LENGTH, res[0].length());
	}
	
	@Test
	public void test2() {
		
		RequestFilter filter = init("std-test-2.xml");
		
		final String[] res = new String[1];
		
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getMethod() {
				return "post";
			}
			@Override
			public String getRequestURI() {
				return "/basepath/ciccia";
			}
			@Override
			public String getContextPath() {
				return "/basepath";
			}
			@Override
			public HttpSession getSession(boolean create) {
				return new HttpSessionMock() {
					@Override
					public void setAttribute(String k, Object v) {
						if(k.equals(TokenManager.DEFAULT_STORE_KEY_NAME)) {
							res[0] = (String)v;
						}
					}
				};
			}
		};
		
		HttpServletResponse resp = new HttpServletResponseMock();
		
		try {
			filter.doFilter(req, resp, getTestChain());
		} catch(BlockDetectionException e) {
			throw e;
		} catch(ForwardDetectionException e) {
			// ok
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		
		Assert.assertEquals(TokenManager.DEFAULT_TOKEN_LENGTH, res[0].length());
	}
	
	@Test
	public void test3() {
		
		RequestFilter filter = init("std-test-2.xml");
		
		final String[] res = new String[1];
		
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getMethod() {
				return "post";
			}
			@Override
			public String getRequestURI() {
				return "/basepath/aaa";
			}
			@Override
			public String getContextPath() {
				return "/basepath";
			}
			@Override
			public HttpSession getSession(boolean create) {
				return new HttpSessionMock() {
					@Override
					public void setAttribute(String k, Object v) {
						if(k.equals(TokenManager.DEFAULT_STORE_KEY_NAME)) {
							res[0] = (String)v;
						}
					}
					@Override
					public Object getAttribute(String k) {
						if(k.equals(TokenManager.DEFAULT_STORE_KEY_NAME)) {
							return res[0];
						}
						return null;
					}
				};
			}
		};
		
		HttpServletResponse resp = new HttpServletResponseMock();
		
		try {
			filter.doFilter(req, resp, getTestChain());
		} catch(BlockDetectionException e) {
			// ok
		} catch(ForwardDetectionException e) {
			throw e;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		String firstToken = res[0];
		
		try {
			filter.doFilter(req, resp, getTestChain());
		} catch(BlockDetectionException e) {
			// ok
		} catch(ForwardDetectionException e) {
			throw e;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		
		Assert.assertEquals(TokenManager.DEFAULT_TOKEN_LENGTH, res[0].length());
		Assert.assertEquals(firstToken, res[0]);
	}

	
}
