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
  

    String channel = "shrineoflostsecrets";
    
    ;
    if (request.getParameter("channel") != null && !request.getParameter("channel").isEmpty()) {
    	channel = request.getParameter("channel");
    }
    %>
  <%= channel %>  
<%
    	List<Entity> listChanels = TwitchChannelEventList.listChanelEvents("channel");
    			
    	for(Entity entity: listChanels) {
    		ShrineChannelEvent channelEvent = new ShrineChannelEvent();
    		channelEvent.loadFromEntity(entity);
%>
<tr><td><a href=""><%=channelEvent.getTwitchUser() %></a></td><td><%=channelEvent.getMessage() %></td></tr>
<%}%>
</table>
</body></html>
