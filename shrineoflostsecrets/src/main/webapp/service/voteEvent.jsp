<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page
	import="java.util.*,com.shrineoflostsecrets.channel.util.*,org.json.JSONObject, com.google.cloud.datastore.*, com.shrineoflostsecrets.channel.database.datastore.*, com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.enumerations.*"%>
<%
response.setHeader("Content-Type", "application/json");

long id = Long.valueOf(request.getParameter("id"));
boolean safe = Boolean.valueOf(request.getParameter("safe"));
String channel = request.getParameter("channel");
String userName = request.getParameter("userName");
long amount = Long.valueOf(request.getParameter("amount"));
ShrineVoteCategoryEnum shrineVoteCategory = ShrineVoteCategoryEnum.findById(request.getParameter("category"));

ShrineVote vote = new ShrineVote();
vote.setShrineChannelEventId(id);
vote.setTwitchChannel(channel);
vote.setTwitchUser(userName);
vote.setShrineVoteCategory(shrineVoteCategory);
vote.setAmount(amount);
vote.setVoteDate();
vote.setSafe(safe);
vote.save();

JSONObject jsonResponse = new JSONObject();
jsonResponse.put("done", true);

out.print(jsonResponse.toString());
%>