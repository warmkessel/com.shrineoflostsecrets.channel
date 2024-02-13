<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.google.cloud.datastore.*,com.google.appengine.api.users.*,com.shrineoflostsecrets.channel.database.datastore.*,com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*"%>
<%
UserService userService = UserServiceFactory.getUserService();
User currentUser = userService.getCurrentUser();

if (currentUser == null || !userService.isUserAdmin()) {
    response.sendRedirect(JSPConstants.INDEX);
    return;
}

// Process adding or updating a ShrineUser
String id = request.getParameter("id");
String otpPass = request.getParameter("otpPass");
String twitchUserName = request.getParameter("twitchUserName");
String votesRemaining = request.getParameter("votesRemaining");
String voteMultiplier = request.getParameter("voteMultiplier");

ShrineUser user = new ShrineUser();
if (id != null && !id.isEmpty()) {
    user.loadShrineUser(id);
}

if (request.getMethod().equalsIgnoreCase("POST")) {
    if (twitchUserName != null) user.setTwitchUserName(twitchUserName);
    if (otpPass != null) user.setOtpPass(otpPass);
    if (votesRemaining != null) user.setVotesRemaining(Long.parseLong(votesRemaining));
    if (voteMultiplier != null) user.setVoteMultiplier(Long.parseLong(voteMultiplier));
    user.save(); // Save or update the user
}

List<Entity> listChannels = ShrineUserService.listUsers(); // Retrieve list of ShrineUsers
%>

<!DOCTYPE html>
<html>
<head>
<title>Shrine User Management - <%= user.getKeyLong() %> - <%= user.getTwitchUserName() %></title>
</head>
<body>
<a href="<%=JSPConstants.ADMININDEX%>">Index</a>
<h1>Shrine User Management</h1>
<table>
    <% for (Entity entity : listChannels) {
        ShrineUser userList = new ShrineUser();
        userList.loadFromEntity(entity);
    %>
    <tr>
        <td><a href="<%= JSPConstants.ADMINUSERL %>?id=<%= userList.getId() %>"><%= userList.getId() %></a></td>
        <td><%= userList.getTwitchUserName() %></td>
        <td><%= userList.getVoteMultiplier() %></td>
        <td><%= userList.getVotesRemaining() %></td>
    </tr>
    <% } %>
</table>

<h2><%= id != null && !id.isEmpty() ? "Edit User" : "Add New User" %></h2>
<form method="post">
    <% if (id != null && !id.isEmpty()) { %>
    <input type="hidden" name="id" value="<%= user.getKeyLong() %>">
    <% } %>
    Twitch Username: <input type="text" name="twitchUserName" value="<%= user.getTwitchUserName() %>"><br>
    OTP: <input type="text" name="otpPass" value="<%= user.getOtpPass() %>"><br>
    Votes Remaining: <input type="number" name="votesRemaining" value="<%= user.getVotesRemaining() %>"><br>
    Vote Multiplier: <input type="number" name="voteMultiplier" value="<%= user.getVoteMultiplier() %>"><br>
    <input type="submit" value="<%= id != null && !id.isEmpty() ? "Update User" : "Add User" %>">
</form>
</body>
</html>
