package it.nerdammer.seasurf;

import it.nerdammer.seasurf.stuff.BlockDetectionException;
import it.nerdammer.seasurf.stuff.ForwardDetectionException;
import it.nerdammer.seasurf.stuff.HttpServletRequestMock;
import it.nerdammer.seasurf.stuff.HttpServletResponseMock;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

public class StdTest3 extends StdBaseTest {

	@Test
	public void test1() {
		
		RequestFilter filter = init("std-test-3.xml");
		
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
			public Cookie[] getCookies() {
				if(res[0]!=null) {
					Cookie c = new Cookie(TokenManager.DEFAULT_STORE_KEY_NAME, res[0]); 
					return new Cookie[]{c};
				}
				return null;
			}
		};
		
		HttpServletResponse resp = new HttpServletResponseMock() {
			@Override
			public void addCookie(Cookie c) {
				if(c.getName().equals(TokenManager.DEFAULT_STORE_KEY_NAME)) {
					res[0] = c.getValue();
				}
			}
		};
		
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
		
		RequestFilter filter = init("std-test-3.xml");
		
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
			public Cookie[] getCookies() {
				if(res[0]!=null) {
					Cookie c = new Cookie(TokenManager.DEFAULT_STORE_KEY_NAME, res[0]); 
					return new Cookie[]{c};
				}
				return null;
			}
		};
		
		HttpServletResponse resp = new HttpServletResponseMock() {
			@Override
			public void addCookie(Cookie c) {
				if(c.getName().equals(TokenManager.DEFAULT_STORE_KEY_NAME)) {
					res[0] = c.getValue();
				}
			}
		};
		
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
		
		RequestFilter filter = init("std-test-3.xml");
		
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
			public Cookie[] getCookies() {
				if(res[0]!=null) {
					Cookie c = new Cookie(TokenManager.DEFAULT_STORE_KEY_NAME, res[0]); 
					return new Cookie[]{c};
				}
				return null;
			}
		};
		
		HttpServletResponse resp = new HttpServletResponseMock() {
			@Override
			public void addCookie(Cookie c) {
				if(c.getName().equals(TokenManager.DEFAULT_STORE_KEY_NAME)) {
					res[0] = c.getValue();
				}
			}
		};
		
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
	
	@Test
	public void test4() {
		
		RequestFilter filter = init("std-test-3.xml");
		
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
			public Cookie[] getCookies() {
				if(res[0]!=null) {
					Cookie c = new Cookie(TokenManager.DEFAULT_STORE_KEY_NAME, res[0]); 
					return new Cookie[]{c};
				}
				return null;
			}
		};
		
		HttpServletResponse resp = new HttpServletResponseMock() {
			@Override
			public void addCookie(Cookie c) {
				if(c.getName().equals(TokenManager.DEFAULT_STORE_KEY_NAME)) {
					res[0] = c.getValue();
				}
			}
		};
		
		try {
			filter.doFilter(req, resp, getTestChain());
		} catch(BlockDetectionException e) {
			// ok
		} catch(ForwardDetectionException e) {
			throw e;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		final String token = res[0];
		
		HttpServletRequest req2 = new HttpServletRequestMock() {
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
			public String getParameter(String k) {
				if(k.equals(TokenManager.DEFAULT_REQUEST_KEY_NAME)) {
					return token;
				}
				return null;
			}
			@Override
			public Cookie[] getCookies() {
				if(res[0]!=null) {
					Cookie c = new Cookie(TokenManager.DEFAULT_STORE_KEY_NAME, res[0]); 
					return new Cookie[]{c};
				}
				return null;
			}
		};
		
		try {
			filter.doFilter(req2, resp, getTestChain());
		} catch(BlockDetectionException e) {
			throw e;
		} catch(ForwardDetectionException e) {
			// ok
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
