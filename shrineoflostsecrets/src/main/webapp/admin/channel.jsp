<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.google.cloud.datastore.*,com.shrineoflostsecrets.channel.constants.*,com.google.appengine.api.users.*, com.shrineoflostsecrets.channel.database.datastore.*, com.shrineoflostsecrets.channel.constants.*, com.shrineoflostsecrets.channel.database.entity.*"%>

<%
UserService userService = UserServiceFactory.getUserService();
User currentUser = userService.getCurrentUser();

if (currentUser == null || !userService.isUserAdmin()) {
    response.sendRedirect(JSPConstants.INDEX);
    return;
}

// Process adding a new channel
String id = request.getParameter("id");

String twitchChannelName = request.getParameter("twitchChannel");
String discordChannelName = request.getParameter("discordChannel");
String twitchUserName = request.getParameter("twitchUserName");
String discordUserName = request.getParameter("discordUserName");
String twitchLogoImg = request.getParameter("twitchLogoImg"); // Retrieve Twitch Logo Image
String twitchBestQuote = request.getParameter("twitchBestQuote"); // Retrieve Twitch Best Quote
String twitchServiceTypeStr = request.getParameter("twitchServiceType");
String twitchLastStartStr = request.getParameter("twitchLastStart");
String twitchLastEndStr = request.getParameter("twitchLastEnd");
String deletedStr = request.getParameter("deleted"); // Retrieve the deleted parameter

long twitchServiceType = 0;
boolean deleted = false;

if (twitchServiceTypeStr != null && !twitchServiceTypeStr.isEmpty()) {
    try {
        twitchServiceType = Long.parseLong(twitchServiceTypeStr);
    } catch (NumberFormatException e) {
        // Handle invalid number format
    }
}

if (deletedStr != null && deletedStr.equalsIgnoreCase("on")) {
    deleted = true;
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
    channel.setTwitchLogoImg(twitchLogoImg); // Set Twitch Logo Image
    channel.setTwitchBestQuote(twitchBestQuote); // Set Twitch Best Quote
    channel.setTwitchServiceType(twitchServiceType);
    channel.setTwitchLastStart(twitchLastStartStr);
    channel.setTwitchLastEnd(twitchLastEndStr);
    channel.setDeleted(deleted); // Set the deleted status
    channel.save(); // Save the new channel
}
List<Entity> listChannels = ShrineChannelService.listChannels(); // Retrieve list of ShrineChannels
%>

<!DOCTYPE html>
<html>
<head>
<title>Shrine Channel Management - <%=channel.getKeyLong()%></title>
</head>
<body>
    <h1>Shrine Channel Management</h1>
    <table>
        <%
        for (Entity entity : listChannels) {
            ShrineChannel channelList = new ShrineChannel();
            channelList.loadFromEntity(entity);
        %>
        <tr>
            <td><a href="<%=JSPConstants.ADMINCHANNEL%>?id=<%=channelList.getId()%>">
                    <%=channelList.getKeyString()%>
            </a></td>
            <td><a href="https://www.twitch.tv/<%=channelList.getTwitchChannel()%>"><%=channelList.getTwitchChannel()%></a></td>
            <td><img src="<%=channelList.getTwitchLogoImg()%>" alt="Twitch Logo" width="100" height="100"></td>
            <td><%=channelList.getTwitchBestQuote()%></td>
        </tr>
        <%
        }
        %>
    </table>

    <h2>Add New Channel</h2>
    <form method="post">
        <%
        if (id != null) {
        %>

        <input type="text" name="id" value="<%=channel.getKeyLong()%>"
            required><br>
        <%}%>

        Twitch Channel: <input type="text" name="twitchChannel" required
            value=<%=channel.getTwitchChannel()%>><br> Discord
        Channel: <input type="text" name="discordChannel" required
            value=<%=channel.getDiscordChannel()%>><br> Twitch
        Username: <input type="text" name="twitchUserName"
            value=<%=channel.getTwitchUserName()%>><br> Discord
        Username: <input type="text" name="discordUserName"
            value=<%=channel.getDiscordUserName()%>><br> Twitch
        Logo Image: <input type="text" name="twitchLogoImg"
            value=<%=channel.getTwitchLogoImg()%>><br> Twitch
        Best Quote: <input type="text" name="twitchBestQuote"
            value=<%=channel.getTwitchBestQuote()%>><br> Twitch
        Service Type: <input type="text" name="twitchServiceType" value="<%=channel.getTwitchServiceType()%>"><br>
        Twitch Last Start: <input type="datetime-local" name="twitchLastStart"
            value="<%=channel.getTwitchLastStartString()%>"><br>
        Twitch Last End: <input type="datetime-local" name="twitchLastEnd"
            value="<%=channel.getTwitchLastEndString()%>"><br>
        Deleted: <input type="checkbox" name="deleted" <%= channel.getDeleted() ? "checked" : "" %>> <br>
        <input type="submit" value="Add Channel">
    </form>
</body>
</html>
