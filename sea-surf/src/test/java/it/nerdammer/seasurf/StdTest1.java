package it.nerdammer.seasurf;

import it.nerdammer.seasurf.stuff.BlockDetectionException;
import it.nerdammer.seasurf.stuff.ForwardDetectionException;
import it.nerdammer.seasurf.stuff.HttpServletRequestMock;
import it.nerdammer.seasurf.stuff.HttpServletResponseMock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class StdTest1 extends StdBaseTest {

	@Test
	public void test1() {
		
		RequestFilter filter = init("std-test-1.xml");
		
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
			public String getHeader(String header) {
				if(header.equals("Referer")) {
					return "http://www.google.it";
				}
				throw new IllegalArgumentException();
			}
		};
		
		HttpServletResponse res = new HttpServletResponseMock();
		
		try {
			filter.doFilter(req, res, getTestChain());
		} catch(BlockDetectionException e) {
			throw e;
		} catch(ForwardDetectionException e) {
			// ok
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Test
	public void test2() {
		
		RequestFilter filter = init("std-test-1.xml");
		
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
			public String getHeader(String header) {
				if(header.equals("Referer")) {
					return "http://api.google.it";
				}
				throw new IllegalArgumentException();
			}
		};
		
		HttpServletResponse res = new HttpServletResponseMock();
		
		try {
			filter.doFilter(req, res, getTestChain());
		} catch(BlockDetectionException e) {
			// ok
		} catch(ForwardDetectionException e) {
			throw e;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Test
	public void test3() {
		
		RequestFilter filter = init("std-test-1.xml");
		
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getMethod() {
				return "post";
			}
			@Override
			public String getRequestURI() {
				return "/basepath/azionista";
			}
			@Override
			public String getContextPath() {
				return "/basepath";
			}
			@Override
			public String getHeader(String header) {
				if(header.equals("Referer")) {
					return "https://www.nerdammer.it";
				}
				throw new IllegalArgumentException();
			}
		};
		
		HttpServletResponse res = new HttpServletResponseMock();
		
		try {
			filter.doFilter(req, res, getTestChain());
		} catch(BlockDetectionException e) {
			throw e;
		} catch(ForwardDetectionException e) {
			// ok
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Test
	public void test4() {
		
		RequestFilter filter = init("std-test-1.xml");
		
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getMethod() {
				return "post";
			}
			@Override
			public String getRequestURI() {
				return "/basepath/azionista";
			}
			@Override
			public String getContextPath() {
				return "/basepath";
			}
			@Override
			public String getHeader(String header) {
				if(header.equals("Referer")) {
					return "https://www.google.it";
				}
				throw new IllegalArgumentException();
			}
		};
		
		HttpServletResponse res = new HttpServletResponseMock();
		
		try {
			filter.doFilter(req, res, getTestChain());
		} catch(BlockDetectionException e) {
			// ok
		} catch(ForwardDetectionException e) {
			throw e;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Test
	public void test5() {
		
		RequestFilter filter = init("std-test-1.xml");
		
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getMethod() {
				return "get";
			}
			@Override
			public String getRequestURI() {
				return "/basepath/azionista";
			}
			@Override
			public String getContextPath() {
				return "/basepath";
			}
			@Override
			public String getHeader(String header) {
				if(header.equals("Referer")) {
					return "https://www.google.it";
				}
				throw new IllegalArgumentException();
			}
		};
		
		HttpServletResponse res = new HttpServletResponseMock();
		
		try {
			filter.doFilter(req, res, getTestChain());
		} catch(BlockDetectionException e) {
			throw e;
		} catch(ForwardDetectionException e) {
			// ok
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Test
	public void test6() {
		
		RequestFilter filter = init("std-test-1.xml");
		
		HttpServletRequest req = new HttpServletRequestMock() {
			@Override
			public String getMethod() {
				return "post";
			}
			@Override
			public String getRequestURI() {
				return "/basepath/azionista";
			}
			@Override
			public String getContextPath() {
				return "/basepath";
			}
			@Override
			public String getHeader(String header) {
				return null;
			}
		};
		
		HttpServletResponse res = new HttpServletResponseMock();
		
		try {
			filter.doFilter(req, res, getTestChain());
		} catch(BlockDetectionException e) {
			throw e;
		} catch(ForwardDetectionException e) {
			// ok
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
