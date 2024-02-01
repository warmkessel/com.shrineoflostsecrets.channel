<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ page import="java.util.*"%><%@ page import="org.json.JSONArray"%><%@ page import="org.json.JSONObject"%><%@ page import="com.google.cloud.datastore.*"%><%@ page import="com.shrineoflostsecrets.channel.database.datastore.*"%><%@ page import="com.shrineoflostsecrets.channel.constants.*"%><%@ page import="com.shrineoflostsecrets.channel.database.entity.*"%><%
response.setHeader("Content-Type", "application/json");

boolean ban = Boolean.valueOf(request.getParameter("ban"));
String id = request.getParameter("id");
String requestChannel = request.getParameter("channel");
String channel = (requestChannel != null && !requestChannel.isEmpty()) ? requestChannel : "shrineoflostsecrets";

JSONArray jsonEvents = new JSONArray();

try {
    List<Entity> listChannels = null;
    if (ban) {
        listChannels = TwitchChannelEventList.listChanelEventsDeleted(channel);
    } else {
        listChannels = TwitchChannelEventList.listChanelEvents(channel);
    }
    for (Entity entity : listChannels) {
        ShrineChannelEvent channelEvent = new ShrineChannelEvent();
        channelEvent.loadFromEntity(entity);

        JSONObject jsonEvent = new JSONObject();
        jsonEvent.put("createdDate", channelEvent.getCreatedDate().toString());
        jsonEvent.put("twitchUser", channelEvent.getTwitchUser());
        String eventType = channelEvent.getEventType();
        String message = channelEvent.getMessage();

        if (TwitchChannelConstants.ONUSERBAN.equals(eventType)) {
            jsonEvent.put("eventType", "User Banned");
        } else if (TwitchChannelConstants.ONDELETEMESSAAGE.equals(eventType)) {
            jsonEvent.put("eventType", "Deleted Message");
            jsonEvent.put("message", message);
        } else if (TwitchChannelConstants.ONCHANNELMESSAGEELEVATED.equals(eventType)) {
            jsonEvent.put("eventType", "Elevated Sub");
            jsonEvent.put("message", message);
        } else {
            jsonEvent.put("message", message);
        }

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
