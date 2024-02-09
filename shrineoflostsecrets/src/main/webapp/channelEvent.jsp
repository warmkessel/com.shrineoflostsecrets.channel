<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, com.google.cloud.datastore.*, com.shrineoflostsecrets.channel.database.datastore.*, com.shrineoflostsecrets.channel.constants.*, com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.util.*"%>
<html>
<!-- Google tag (gtag.js) -->
<script async="true"
	src="https://www.googletagmanager.com/gtag/js?id=G-N2VTBWYNCJ"></script>
<script>
	window.dataLayer = window.dataLayer || [];
	function gtag() {
		dataLayer.push(arguments);
	}
	gtag('js', new Date());
	gtag('config', 'G-N2VTBWYNCJ');
</script>
<body>
		<%
		boolean unlimited = Boolean.valueOf(request.getParameter("unlimited"));
		boolean ban = Boolean.valueOf(request.getParameter("ban"));
		String id = request.getParameter("id");
		String requestChannel = request.getParameter("channel");
		String userName = request.getParameter("userName");
		String channel = (requestChannel != null && !requestChannel.isEmpty()) ? requestChannel : "shrineoflostsecrets";
		%>
		Channel:<%=channel%>
		<br>
		<a href="<%=JSPConstants.CHANNELEVENT%>?channel=<%=channel%>&ban=<%=!ban%>">Toggle Ban</a>
		<a href="<%=JSPConstants.CHANNELEVENTDYNAMIC%>?channel=<%=channel%>&ban=<%=ban%>">Live</a>
		<table>
		<%
		List<Entity> listChannels = ShrineChannelEventList.listChanelEvents(channel, userName, ban, unlimited);
		for (Entity entity : listChannels) {
			ShrineChannelEvent channelEvent = new ShrineChannelEvent();
			channelEvent.loadFromEntity(entity);
			if (!Arrays.asList(TwitchChannelConstants.BOTS)
			.contains(channelEvent.getTwitchUser()) && !channelEvent.getMessage().isBlank()){
		%>
		<tr>
		<td><%=DateFormatter.convertToHourAndMin(channelEvent.getCreatedDate())%></td>
		<td><a href="<%=JSPConstants.CHANNELEVENT%>?channel=<%=requestChannel%>&userName=<%=channelEvent.getTwitchUser()%>"><%=channelEvent.getTwitchUser()%></a></td>
		<td><a
			href="<%=JSPConstants.SERVICEVOTE%>id=<%=channelEvent.getId()%>&channel=<%=channelEvent.getTwitchChannel()%>&userName=<%=channelEvent.getTwitchUser()%>&amount=100">Vote!</a></td>
		<td>
			<%
			if (TwitchChannelConstants.ONUSERBAN.equals(channelEvent.getEventType())) {
				%> User Banned <%
				} else if (TwitchChannelConstants.ONDELETEMESSAGE.equals(channelEvent.getEventType())) {
				%><span style="color:red"><%=channelEvent.getMessage()%></span>
				<%
				} else if (TwitchChannelConstants.ONCHANNELMESSAGEELEVATED.equals(channelEvent.getEventType())) {
				%> Elevated Sub<i><%=channelEvent.getMessage()%></i> <%
				} else {
				 %><%=channelEvent.getMessage()%> <%
	 			}
 			%>
		</td>
		</tr>
	<%}}%>
	</table>
</body>
</html>

