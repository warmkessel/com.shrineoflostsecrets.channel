<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*,com.google.cloud.datastore.*,org.json.*,com.shrineoflostsecrets.channel.database.datastore.*,com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.util.*,com.shrineoflostsecrets.channel.constants.*"%>
<%
response.setHeader("Content-Type", "application/json");

String channel = request.getParameter("channel");
boolean safe = Boolean.parseBoolean(request.getParameter("safe"));

// Assume ShrineVoteService is a class that abstracts datastore operations

JSONArray jsonEvents = new JSONArray();
JSONArray jsonVotes = new JSONArray();

	List<Entity> listVotes = ShrineVoteService.listVotes(channel, safe);
	for (Entity entity : listVotes) {
		ShrineVote vote = new ShrineVote();
		vote.loadFromEntity(entity);
		ShrineChannelEvent channelEvent = new ShrineChannelEvent();
		channelEvent.loadEvent(vote.getShrineChannelEventId());

		JSONObject jsonVote = new JSONObject();
		jsonVote.put("amount", vote.getAmount());
		jsonVote.put("message", channelEvent.getMessage());
		jsonVote.put("twitchUser", StringUtil.slice(channelEvent.getTwitchUser(), JSPConstants.MAXUSERNAMELENGTH));
		jsonVote.put("createdDate", channelEvent.getCreatedDate().toString()); // You may need to format this date

		jsonVotes.put(jsonVote);
	}

JSONObject jsonResponse = new JSONObject();
jsonResponse.put("votes", jsonVotes);
out.print(jsonResponse.toString());
%>