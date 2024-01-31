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
List<Entity> listChanels = TwitchChannelList.listChannels();
    	for(Entity entity: listChanels) {
    		ShrineChannel channel = new ShrineChannel();
    		channel.loadFromEntity(entity);
%>
<tr><td><a href="channelEvent.jsp?channel=<%=channel.getTwitchChannel() %>"><%=channel.getTwitchChannel() %></a></td></tr>
<%}%>
</table>
</body></html>
