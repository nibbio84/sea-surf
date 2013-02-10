<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.nerdammer.it/seasurf" prefix="csrf"%>
<html>
<body>
<h2>Page 3</h2>
<csrf:token var="token" />
<a href="index.jsp">Go home</a>
<a href="${pageContext.request.contextPath}/page2.jsp?seasurf-token=${token}">Goto page 2</a>
</body>
</html>
