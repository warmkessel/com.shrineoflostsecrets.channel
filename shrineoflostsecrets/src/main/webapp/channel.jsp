<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.google.cloud.datastore.*"%>

<%@ page import="com.shrineoflostsecrets.channel.database.datastore.*"%>
<%@ page import="com.shrineoflostsecrets.channel.constants.*"%>
<%@ page import="com.shrineoflostsecrets.channel.database.entity.*"%>
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
ni
<table>

<%
List<Entity> listChanels = ShrineChannelService.listChannels();
    	for(Entity entity: listChanels) {
    		ShrineChannel channel = new ShrineChannel();
    		channel.loadFromEntity(entity);
    		boolean live = (channel.getTwitchLastStart().getSeconds() - channel.getTwitchLastEnd().getSeconds() > 0);
%>
<tr>
<td><a href="https://www.twitch.tv/<%=channel.getTwitchChannel() %>">Twitch <%if(live){%>LIVE!<%}%></a></td>
<td><a href="<%if(live){%>channelEventDynamic.jsp<%}else{ %>channelEvent.jsp<%}%>?channel=<%=channel.getTwitchChannel() %>"><%=channel.getTwitchChannel() %></a></td>
<td><a href="channelVotes.jsp?channel=<%=channel.getTwitchChannel()%>">Score Board!</a></td>
</tr>
<%}%>
</table>
</body></html>
