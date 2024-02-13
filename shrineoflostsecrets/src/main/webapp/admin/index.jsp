<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.google.appengine.api.users.*,com.shrineoflostsecrets.channel.constants.*"%>
<%
    UserService userService = UserServiceFactory.getUserService();
    User currentUser = userService.getCurrentUser();
%><%
						if (currentUser != null) {
						%> <a
						href="<%=userService.createLogoutURL(JSPConstants.INDEX)%>"
						class="btn btn-primary btn-sm">Welcome <%=currentUser.getNickname()%></a><br>
						<a href="<%=JSPConstants.ADMINCHANNEL%>?">Channel List</a><br>
						<a href="<%=JSPConstants.ADMINUSERL%>">User List</a><br>
						<a href="<%=JSPConstants.ADMINVOTERESET%>">Vote Amount Reset</a><br><br>
						<a href="<%=JSPConstants.ADMINSHOW%>">Show</a><br>
						<%
						} else {
						%> <a
						href="<%=userService.createLoginURL(JSPConstants.INDEX)%>"
						class="btn btn-primary btn-sm">Login/Register</a><br> <%}%>
