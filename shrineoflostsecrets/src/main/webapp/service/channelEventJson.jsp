<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ page import="java.util.*"%><%@ page import="com.shrineoflostsecrets.channel.util.*"%><%@ page import="org.json.JSONArray"%><%@ page import="org.json.JSONObject"%><%@ page import="com.google.cloud.datastore.*"%><%@ page import="com.shrineoflostsecrets.channel.database.datastore.*"%><%@ page import="com.shrineoflostsecrets.channel.constants.*"%><%@ page import="com.shrineoflostsecrets.channel.database.entity.*"%><%
response.setHeader("Content-Type", "application/json");

    boolean unlimited = Boolean.valueOf(request.getParameter("unlimited"));
    boolean ban = Boolean.valueOf(request.getParameter("ban"));
    String id = request.getParameter("id");
    String requestChannel = request.getParameter("channel");
    String userName = request.getParameter("userName");
	String channel = (requestChannel != null && !requestChannel.isEmpty()) ? requestChannel : "shrineoflostsecrets";

JSONArray jsonEvents = new JSONArray();

try {
    List<Entity> listChannels = null;
    listChannels = ShrineChannelEventList.listChanelEvents(requestChannel, userName, ban, unlimited);
    for (Entity entity : listChannels) {
        ShrineChannelEvent channelEvent = new ShrineChannelEvent();
        channelEvent.loadFromEntity(entity);

        JSONObject jsonEvent = new JSONObject();
        jsonEvent.put("createdDate", DateFormatter.convertToHourAndMin(channelEvent.getCreatedDate()));
        jsonEvent.put("twitchUser", channelEvent.getTwitchUser());
        jsonEvent.put("eventType", channelEvent.getEventType());
        jsonEvent.put("message", channelEvent.getMessage());
        jsonEvents.put(jsonEvent);
    }
} catch (Exception e) {
    // Handle exceptions appropriately, possibly logging the error and returning an error message in JSON
}

JSONObject jsonResponse = new JSONObject();
jsonResponse.put("channel", channel);
jsonResponse.put("events", jsonEvents);

out.print(jsonResponse.toString());
%>