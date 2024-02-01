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
<table>

<%
List<Entity> listChanels = TwitchChannelList.listChannels();
    	for(Entity entity: listChanels) {
    		ShrineChannel channel = new ShrineChannel();
    		channel.loadFromEntity(entity);
%>
<tr>
<td><a href="channelEvent.jsp?channel=<%=channel.getTwitchChannel() %>"><%=channel.getTwitchChannel() %></a></td>
<td><a href="channelEventDynamic.jsp?channel=<%=channel.getTwitchChannel() %>"><%=channel.getTwitchChannel() %></a></td>
</tr>
<%}%>
</table>
</body></html>
