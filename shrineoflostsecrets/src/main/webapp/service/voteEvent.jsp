<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page
	import="java.util.*,com.shrineoflostsecrets.channel.util.*,org.json.JSONObject, com.google.cloud.datastore.*, com.shrineoflostsecrets.channel.database.datastore.*, com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.enumerations.*"%>
<%
response.setHeader("Content-Type", "application/json");

boolean theReturn = false;
String sessionAuth = (String) request.getSession().getAttribute("auth");
if(sessionAuth != null && !sessionAuth.isBlank()){
	long id = Long.valueOf(request.getParameter("id"));
	boolean safe = Boolean.valueOf(request.getParameter("safe"));
	String channel = request.getParameter("channel");
	long amount = Long.valueOf(request.getParameter("amount"));
	theReturn = ShrineVote.addVote(channel, id, ShrineVoteCategoryEnum.CATEGORY_ONE, amount, sessionAuth);
}
%><%=theReturn%>