<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.google.cloud.datastore.*,com.google.appengine.api.users.*,com.shrineoflostsecrets.channel.database.datastore.*,com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*,com.google.appengine.api.users.*"%>
<%
UserService userService = UserServiceFactory.getUserService();
User currentUser = userService.getCurrentUser();

if (currentUser == null || !userService.isUserAdmin()) {
    response.sendRedirect(JSPConstants.INDEX);
    return;
}

// Initialize services and retrieve all ShrineUsers
List<Entity> shrineUsers = ShrineUserService.listUsers();
Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

// Map to hold the previous votesRemaining for each user
Map<String, Long> previousVotes = new HashMap<>();

// Prepare to update users with votesRemaining less than 1000
List<ShrineUser> updatedUsers = new ArrayList<>();

for (Entity entity : shrineUsers) {
    ShrineUser user = new ShrineUser();
    user.loadFromEntity(entity);
    // Store the current votesRemaining before updating
    previousVotes.put(user.getTwitchUserName(), user.getVotesRemaining());

    if (user.getVotesRemaining() < 1000) {
        user.setVotesRemaining(1000); // Update votesRemaining to 1000
        user.save(); // Persist the updated user back to the datastore
        updatedUsers.add(user); // Add to the list of updated users
    }
}

// List users again to ensure updates are considered
shrineUsers = ShrineUserService.listUsers();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Shrine Users Vote Remaining</title>
</head>
<body>
<a href="<%=JSPConstants.ADMININDEX%>">Index</a>
    <h1>Shrine Users Vote Remaining</h1>
    <table border="1">
        <tr>
            <th>Twitch User Name</th>
            <th>Previous Votes Remaining</th>
            <th>Updated Votes Remaining</th>
        </tr>
        <% for (Entity entity : shrineUsers) {
            ShrineUser user = new ShrineUser();
            user.loadFromEntity(entity);
        %>
        <tr>
            <td><%= user.getTwitchUserName() %></td>
            <td><%= previousVotes.get(user.getTwitchUserName()) %></td>
            <td><%= user.getVotesRemaining() %></td>
        </tr>
        <% } %>
    </table>
</body>
</html>
