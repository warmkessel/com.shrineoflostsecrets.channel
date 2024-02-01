<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.google.cloud.datastore.*"%>
<%@ page import="com.shrineoflostsecrets.channel.database.datastore.*"%>
<%@ page import="com.shrineoflostsecrets.channel.constants.*"%>
<%@ page import="com.shrineoflostsecrets.channel.database.entity.*"%>

<html>
<body>
	<table>
		<%
		boolean ban = Boolean.valueOf(request.getParameter("ban"));
		String id = request.getParameter("id");
		String requestChannel = request.getParameter("channel");
		String channel = (requestChannel != null && !requestChannel.isEmpty()) ? requestChannel : "shrineoflostsecrets";
		%>
		<%=channel%>
		<%
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
		%>
		<tr>
			<td><%=channelEvent.getCreatedDate().toString()%><td>
			<td><a href="/* URL here! */"><%=channelEvent.getTwitchUser()%></a>
			</td>
			<td>
				<%
				if (TwitchChannelConstants.ONUSERBAN.equals(channelEvent.getEventType())) {
				%>
				User Banned <%
				} else if (TwitchChannelConstants.ONDELETEMESSAAGE.equals(channelEvent.getEventType())) {
				%>
				<b><%=channelEvent.getMessage()%></b> <%
				} else if (TwitchChannelConstants.ONCHANNELMESSAGEELEVATED.equals(channelEvent.getEventType())) {
				%>
				Elevated Sub<i><%=channelEvent.getMessage()%></i> <%
			 	} else {
 				%> <%=channelEvent.getMessage()%>
				<%
				}
				%>
			</td>
		</tr>
		<%
		}
		} catch (Exception e) {
		// Handle exceptions appropriately
		}
		%>
	</table>
</body>
</html>

