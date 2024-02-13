<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page
	import="java.util.*,com.shrineoflostsecrets.channel.util.*,org.json.JSONArray,org.json.JSONObject,com.shrineoflostsecrets.channel.enumerations.*,com.google.cloud.datastore.*,com.shrineoflostsecrets.channel.database.datastore.*,com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*"%>
<%
response.setHeader("Content-Type", "application/json");
JSONObject jsonResponse = new JSONObject();

ShrineUser su = new ShrineUser();
//Check if there is a stored auth value in the session
String sessionAuth = (String) request.getSession().getAttribute("auth");
if (sessionAuth != null && !su.isValid()) {
	// Use the auth from the session if there is no auth in the URL
	su.loadShrineUser(sessionAuth);

	if(su.isValid()){
		jsonResponse.put("voteRemaining", su.getVotesRemaining());
		jsonResponse.put("voteMulti", su.getVoteMultiplier());
	}
}
out.print(jsonResponse.toString());
%>