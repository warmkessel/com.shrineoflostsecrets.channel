<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.google.cloud.datastore.*, com.google.appengine.api.users.*, com.shrineoflostsecrets.channel.database.datastore.*, com.shrineoflostsecrets.channel.constants.*, com.shrineoflostsecrets.channel.database.entity.*" %>

<%
UserService userService = UserServiceFactory.getUserService();
    User currentUser = userService.getCurrentUser();

    if (currentUser == null || !userService.isUserAdmin()) {
        response.sendRedirect("/index.jsp");
        return;
    }

    // Process adding a new channel
    String id = request.getParameter("id");

    String twitchChannelName = request.getParameter("twitchChannel");
    String discordChannelName = request.getParameter("discordChannel");
    String twitchUserName = request.getParameter("twitchUserName");
    String discordUserName = request.getParameter("discordUserName");
    String twitchServiceTypeStr = request.getParameter("twitchServiceType");
    long twitchServiceType = 0;

    if (twitchServiceTypeStr != null && !twitchServiceTypeStr.isEmpty()) {
        try {
            twitchServiceType = Long.parseLong(twitchServiceTypeStr);
        } catch (NumberFormatException e) {
            // Handle invalid number format
        }
    }
    ShrineChannel channel = new ShrineChannel();
    if (id != null) {
    	channel.loadShrineChannel(id);
    }
    
    
    if (twitchChannelName != null && discordChannelName != null) {
    	channel.setTwitchChannel(twitchChannelName);
        channel.setDiscordChannel(discordChannelName);
        channel.setTwitchUserName(twitchUserName);
        channel.setDiscordUserName(discordUserName);
        channel.setTwitchServiceType(twitchServiceType);
        channel.save(); // Save the new channel
    }
    List<Entity> listChannels = TwitchChannelList.listChannels(); // Retrieve list of ShrineChannels
%>

<!DOCTYPE html>
<html>
<head>
    <title>Shrine Channel Management - <%=channel.getKeyLong() %></title>
</head>
<body>
    <h1>Shrine Channel Management</h1>
    <table>
        <% for (Entity entity : listChannels) {
            ShrineChannel channelList = new ShrineChannel();
            channelList.loadFromEntity(entity);
        %>
        <tr>
            <td>
                <a href="channelEvent.jsp?channel=<%= channelList.getTwitchChannel() %>">
                    <%= channelList.getTwitchChannel() %>
                </a>
            </td>
        </tr>
        <% } %>
    </table>
         
    <h2>Add New Channel</h2>
    <form method="post">
<%if(id != null){%> 

         <input type="text" name="id"  value ="<%=channel.getKeyLong() %>" required><br>
<%}%> 

        Twitch Channel: <input type="text" name="twitchChannel" required value=<%=channel.getTwitchChannel()%>><br>
        Discord Channel: <input type="text" name="discordChannel" required value=<%=channel.getDiscordChannel()%>><br>
        Twitch Username: <input type="text" name="twitchUserName" value=<%=channel.getTwitchUserName()%>><br>
        Discord Username: <input type="text" name="discordUserName" value=<%=channel.getDiscordUserName()%>><br>
        Twitch Service Type: <input type="text" name="twitchServiceType" value="0"><br>
        <input type="submit" value="Add Channel">
    </form>
</body>
</html>