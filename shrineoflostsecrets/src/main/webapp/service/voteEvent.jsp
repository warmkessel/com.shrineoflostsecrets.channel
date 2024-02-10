<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page
	import="java.util.*,com.shrineoflostsecrets.channel.util.*,org.json.JSONObject, com.google.cloud.datastore.*, com.shrineoflostsecrets.channel.database.datastore.*, com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.enumerations.*"%>
<%
response.setHeader("Content-Type", "application/json");

String sessionAuth = (String) request.getSession().getAttribute("auth");
if(null == sessionAuth || sessionAuth.isBlank()){
	sessionAuth = request.getSession().getId();
}

long id = Long.valueOf(request.getParameter("id"));
boolean safe = Boolean.valueOf(request.getParameter("safe"));
String channel = request.getParameter("channel");
long amount = Long.valueOf(request.getParameter("amount"));

boolean theReturn = ShrineVote.addVote(channel, id, ShrineVoteCategoryEnum.CATEGORY_ONE, amount, sessionAuth);
%><%=theReturn%>