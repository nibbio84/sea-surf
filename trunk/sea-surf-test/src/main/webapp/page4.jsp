<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.nerdammer.it/seasurf" prefix="csrf"%>
<html>
<body>
<h2>Page 4</h2>
<a href="index.jsp">Go home</a>
<a href="page2.jsp">Goto page 2</a>
<a href="page3.jsp">Goto page 3</a>
<form action="page2.jsp" method="post">
	<csrf:token-input />
	<input type="submit" value="Go 2" />
</form>
</body>
</html>
