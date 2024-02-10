<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*,com.google.cloud.datastore.*,com.google.appengine.api.users.*,com.shrineoflostsecrets.channel.database.datastore.*,com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*"%>
<%
UserService userService = UserServiceFactory.getUserService();
User currentUser = userService.getCurrentUser();

 if (currentUser == null || !userService.isUserAdmin()) {
	response.sendRedirect(JSPConstants.INDEX);
	return;
} 
 
// Process adding a new channel
String id = request.getParameter("id");

String otpPass = request.getParameter("otpPass");
String twitchUserName = request.getParameter("twitchUserName");

long twitchServiceType = 0;

ShrineUser user = new ShrineUser();
if (id != null) {
	user.loadShrineUser(id);
}

if (twitchUserName != null) {
	user.setOtpPass(otpPass);
	user.setTwitchUserName(twitchUserName);
	user.save(); // Save the new channel
}
List<Entity> listChannels = ShrineUserService.listUsers(); // Retrieve list of ShrineChanne
%>

<!DOCTYPE html>
<html>
<head>
<title>Shrine User Management - <%=user.getKeyLong()%> - <%=user.getTwitchUserName()%></title>
</head>
<body>
	<h1>Shrine Channel Management</h1>
	<table>
		<%
		for (Entity entity : listChannels) {
			ShrineUser userList = new ShrineUser();
			userList.loadFromEntity(entity);
		%>
		<tr>
			<td><a href="<%=JSPConstants.ADMINUSERL%>?id=<%=userList.getId()%>"><%=userList.getId()%></a></td>
			<td>
					<%=userList.getTwitchUserName()%>
			</td>
		</tr>
		<%
		}
		%>
	</table>

	<h2>Add New User</h2>
	<form method="post">
		<%
		if (id != null) {
		%>

		<input type="text" name="id" value="<%=user.getKeyLong()%>"
			required><br>
		<%}%>

		Twitch
		Username: <input type="text" name="twitchUserName"
			value=<%=user.getTwitchUserName()%>><br>
		OTP: <input type="text" name="otpPass"
			value=<%=user.getOtpPass()%>><br> 
		<input
			type="submit" value="Add Channel">
	</form>
</body>
</html>