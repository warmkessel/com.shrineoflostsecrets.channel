<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, com.google.cloud.datastore.*, com.shrineoflostsecrets.channel.database.datastore.*, com.shrineoflostsecrets.channel.constants.*, com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.util.*"%>
<%
ShrineUser su = new ShrineUser();
String sessionAuth = (String) request.getSession().getAttribute("auth");
if (sessionAuth != null && !su.isValue()) {
	// Use the auth from the session if there is no auth in the URL
	su.loadShrineUser(sessionAuth);
}
%>
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
<script>
// Function to perform a vote
async function vote(eventId, channel, amount) {
        <% if(su.isValue()){%>
    	 const url = ('/service/voteEvent.jsp?id='+ eventId + '&channel=' + channel + '&amount=' + amount);
            try {
                const response = await fetch(url, {
                    method: 'GET', // or 'POST' if your server requires
                });
                const data = await response.text(); // Assuming the response is text
                console.info("Vote Recorded " + data);
/*                 alert(data); // Show the response in an alert box
 */            } catch (error) {
                console.error('Error during fetch:', error);
            }
            <%}else{%>
            alert("We would love to record your vote, but you must login first");
            <%}%>
        }
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
		<td><button onclick="vote('<%=channelEvent.getId()%>', '<%=channelEvent.getTwitchChannel()%>', '100')">Vote!</button></td>
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

