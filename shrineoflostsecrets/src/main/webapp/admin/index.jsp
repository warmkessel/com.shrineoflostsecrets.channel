<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.shrineoflostsecrets.channel.database.entity.*"%>
<%@ page import="com.google.appengine.api.users.*"%>
<%
    UserService userService = UserServiceFactory.getUserService();
    User currentUser = userService.getCurrentUser();
%><%
						if (currentUser != null) {
						%> <a
						href="<%=userService.createLogoutURL("/index.jsp")%>"
						class="btn btn-primary btn-sm">Welcome <%=currentUser.getNickname()%></a>
						<%
						} else {
						%> <a
						href="<%=userService.createLoginURL("/index.jsp")%>"
						class="btn btn-primary btn-sm">Login/Register</a> <%}%>
<a href="./channel.jsp">Channel List</a>