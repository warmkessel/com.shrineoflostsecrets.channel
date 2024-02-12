<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page
	import="java.util.*,com.shrineoflostsecrets.channel.util.*,org.json.JSONArray,org.json.JSONObject,com.shrineoflostsecrets.channel.enumerations.*,com.google.cloud.datastore.*,com.shrineoflostsecrets.channel.database.datastore.*,com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*"%>
<%
response.setHeader("Content-Type", "application/json");

String sessionAuth = (String) request.getSession().getAttribute("auth");
ShrineLog.log(TwitchChannelConstants.SHRINECHANNELEVENT, ShrineDebugEnum.PROUCTION,
		"User " + sessionAuth + " at the channelEventJson Page");

boolean unlimited = Boolean.valueOf(request.getParameter("unlimited"));
ShrineServiceTypeEnum serviceType = ShrineServiceTypeEnum.findById(request.getParameter("serviceType"));
String id = request.getParameter("id");
String requestChannel = request.getParameter("channel");
String userName = request.getParameter("userName");
String channel = (requestChannel != null && !requestChannel.isEmpty()) ? requestChannel : "shrineoflostsecrets";

JSONArray jsonEvents = new JSONArray();

ShrineChannel shrineChannel = new ShrineChannel();
shrineChannel.loadShrineChannelName(channel);
HashMap<String, String> logo = new HashMap();
try {
	List<Entity> listChannels = null;
	listChannels = ShrineChannelEventList.listChanelEvents(requestChannel, userName, serviceType.getBan(), unlimited);
	for (Entity entity : listChannels) {
		ShrineChannelEvent channelEvent = new ShrineChannelEvent();
		channelEvent.loadFromEntity(entity);
		if (
			!Arrays.asList(TwitchChannelConstants.BOTS).contains(channelEvent.getTwitchUser())
			&& !channelEvent.getMessage().isBlank()
			&&(
				(ShrineServiceTypeEnum.LIMITED.equals(serviceType)
				&& channelEvent.getMessage().length() > JSPConstants.MINMESSAGESIZE))
				||(ShrineServiceTypeEnum.FULL.equals(serviceType))
				||(ShrineServiceTypeEnum.SAVE.equals(serviceType))
		){
	if (!logo.containsKey(channelEvent.getTwitchUser())) {
		ShrineChannel foundChannel = new ShrineChannel();
		foundChannel.loadShrineChannelName(channelEvent.getTwitchUser());
		if (foundChannel.isValid()) {
			logo.put(channelEvent.getTwitchUser(), foundChannel.getTwitchLogoImg());
		} else {
			logo.put(channelEvent.getTwitchUser(), shrineChannel.getTwitchLogoImg());

		}
	}
	JSONObject jsonEvent = new JSONObject();
	jsonEvent.put("id", channelEvent.getId());
	jsonEvent.put("logo", logo.get(channelEvent.getTwitchUser()));
	jsonEvent.put("createdDate", DateFormatter.convertToHourAndMin(channelEvent.getCreatedDate()));
	jsonEvent.put("twitchUser", StringUtil.slice(channelEvent.getTwitchUser(), JSPConstants.MAXUSERNAMELENGTH));
	jsonEvent.put("eventType", channelEvent.getEventType());
	jsonEvent.put("message", channelEvent.getMessage());
	jsonEvents.put(jsonEvent);
		}
	}
} catch (Exception e) {
	// Handle exceptions appropriately, possibly logging the error and returning an error message in JSON
}

JSONObject jsonResponse = new JSONObject();
jsonResponse.put("channel", channel);
jsonResponse.put("events", jsonEvents);

out.print(jsonResponse.toString());
%>