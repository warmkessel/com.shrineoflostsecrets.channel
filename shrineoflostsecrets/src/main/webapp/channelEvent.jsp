<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.google.cloud.datastore.*, com.shrineoflostsecrets.channel.database.datastore.*, com.shrineoflostsecrets.channel.constants.*, com.shrineoflostsecrets.channel.database.entity.*, com.shrineoflostsecrets.channel.util.*"%>
<html>
<!-- Google tag (gtag.js) -->
<script async="true"
	src="https://www.googletagmanager.com/gtag/js?id=G-N2VTBWYNCJ"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'G-N2VTBWYNCJ');
</script>
<body>
	<table>
		<%
		boolean unlimited = Boolean.valueOf(request.getParameter("unlimited"));
		boolean ban = Boolean.valueOf(request.getParameter("ban"));
		String id = request.getParameter("id");
		String requestChannel = request.getParameter("channel");
		String userName = request.getParameter("userName");
		String channel = (requestChannel != null && !requestChannel.isEmpty()) ? requestChannel : "shrineoflostsecrets";
		%>
		<%=channel%>
		<%
				try {
					List<Entity> listChannels = ShrineChannelEventList.listChanelEvents(channel, userName, ban,
							unlimited);
			for (Entity entity : listChannels) {
				ShrineChannelEvent channelEvent = new ShrineChannelEvent();
				channelEvent.loadFromEntity(entity);
		%>
		<tr>
			<td><%=DateFormatter.convertToHourAndMin(channelEvent.getCreatedDate())%><td>
			<td><a href="./channelEventDynamic.jsp?channel=<%=requestChannel%>&userName=<%=channelEvent.getTwitchUser()%>"><%=channelEvent.getTwitchUser()%></a>
			</td>
			<td>
				<%
				if (TwitchChannelConstants.ONUSERBAN.equals(channelEvent.getEventType())) {
				%>
				User Banned <%
				} else if (TwitchChannelConstants.ONDELETEMESSAGE.equals(channelEvent.getEventType())) {
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

