<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page
	import="java.util.*,com.shrineoflostsecrets.channel.util.*,org.json.JSONObject, com.google.cloud.datastore.*, com.shrineoflostsecrets.channel.database.datastore.*, com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.enumerations.*"%>
<%

response.setHeader("Content-Type", "application/json");


boolean theReturn = false;
String sessionAuth = (String) request.getSession().getAttribute("auth");
if(sessionAuth != null && !sessionAuth.isBlank()){
	long id = Long.valueOf(request.getParameter("id"));
	String channel = request.getParameter("channel");
	long amount = Long.valueOf(request.getParameter("amount"));
	ShrineUser su = new ShrineUser();
	su.loadShrineUser(sessionAuth);
	
	if(su.isValid() && su.canVote()){
		ShrineChannelEvent sce = new ShrineChannelEvent();
		sce.loadEvent(id);
		if(sce.isValid()){
			boolean safe = TwitchChannelConstants.ONCHANNELMESSAGE.equals(sce.getEventType());
			amount = VoteCaculator.caculateVote(su.vote(amount), su.getVoteMultiplier());		
			theReturn = ShrineVote.addVote(channel, id, ShrineVoteCategoryEnum.CATEGORY_ONE, amount, sessionAuth);
			ShrineLog.log(TwitchChannelConstants.SHRINEVOTE, ShrineDebugEnum.PROUCTION, "User " + sessionAuth + " Channel " + channel + " id " + id + " safe " + safe+ " amount " + amount);
		}
	}
}
%><%=theReturn%>