<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ page import="java.util.*,com.shrineoflostsecrets.channel.util.*,org.json.JSONArray,org.json.JSONObject,com.shrineoflostsecrets.channel.enumerations.*,com.google.cloud.datastore.*,com.shrineoflostsecrets.channel.database.datastore.*,com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*"%><%
	response.setHeader("Content-Type", "application/json");

    String sessionAuth = (String) request.getSession().getAttribute("auth");
	ShrineLog.log(TwitchChannelConstants.SHRINECHANNELEVENT, ShrineDebug.PROUCTION, "User " + sessionAuth + " at the channelEventJson Page");

    
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
        jsonEvent.put("id", channelEvent.getId());
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