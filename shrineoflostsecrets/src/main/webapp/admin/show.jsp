<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.net.*,java.nio.charset.*,com.google.cloud.datastore.*,com.google.appengine.api.users.*,com.shrineoflostsecrets.channel.database.datastore.*,com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.enumerations.*"%>
<%
UserService userService = UserServiceFactory.getUserService();
User currentUser = userService.getCurrentUser();

if (currentUser == null || !userService.isUserAdmin()) {
    response.sendRedirect(JSPConstants.INDEX);
    return;
}

String channel = request.getParameter("channel");


if(channel == null || channel.isBlank()){
	channel="lazeruss";
}
List<Entity> listVotes = ShrineVoteService.listVotes(channel, ShrineServiceTypeEnum.FULL); // Retrieve list of Votes
%>

<!DOCTYPE html>
<html>
<head>
<title>Shrine Show</title>
</head>
<body>
	<h1>Shrine of Lost Secrets:</h1>
	<h2>https://shrineoflostsecrets.com/</h2>
	<h3>This content is not suitable for all audiences</h3>
    <div>Channel: <%=channel %></div>  
    <div id="message"></div>  
    <div id="body"></div>  
    <script>
// Initialize the votes array
var messages = [];
messages[0] = "Welcome to the Shrine Of Lost Secrets",
messages[1] = "If this channel is not for you, it's ok, you can turn it off",
messages[2] = "Vote early, Vote Often",
messages[3] = "Following the Channel will provide you to expanded access, we will whisper you your access",
messages[4] = "The Shrine is full of secrets that are not fit for the broadcast, Visit: shrineoflostsecrets.com"

var votes = [];

// Populate the votes array with server-side JSP
<%
int x = 0;
for (Entity entity : listVotes) {
    ShrineVote vote = new ShrineVote();
    vote.loadFromEntity(entity);
    ShrineChannelEvent channelEvent = new ShrineChannelEvent();
    channelEvent.loadEvent(vote.getShrineChannelEventId());
    String message = channelEvent.getMessage() + " - " + channelEvent.getTwitchUser();
    String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
%>
    votes[<%=x%>] = '<%=encodedMessage%>';
<%
    x++;
}
%>

// Function to update the div content
var currentIndex = 0; // Initialize a global index variable.
var currentMessage = 0; // Initialize a global index variable.

function updateDiv() {
	console.info("currentIndex " + currentIndex);
	console.info("currentMessage " + currentMessage);
	
	var message = document.getElementById("message");
	    if (messages && messages.length > 0) {
	    	message.innerHTML = messages[currentMessage];
	    	currentMessage = (currentMessage + 1) % messages.length; // Increment or reset index
	    } else {
	        console.error("Element with id 'message' was not found or no message available.");
	    }
	    
	var div = document.getElementById("body");
    if (div && votes.length > 0) {
        var decodedMessage = decodeURIComponent(votes[currentIndex].replace(/\+/g, ' '));
        console.log(decodedMessage);
        div.innerHTML = decodedMessage;
        currentIndex = (currentIndex + 1) % votes.length; // Increment or reset index
    } else {
        console.error("Element with id 'body' was not found or no votes available.");
    }
}

updateDiv(); // Update immediately on script load
setInterval(updateDiv, 10000); // Then every 30 seconds
</script>
</body>
</html>
