<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*,java.net.*,java.nio.charset.*,com.google.cloud.datastore.*,com.google.appengine.api.users.*,com.shrineoflostsecrets.channel.database.datastore.*,com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.enumerations.*"%>
<%
UserService userService = UserServiceFactory.getUserService();
User currentUser = userService.getCurrentUser();

if (currentUser == null || !userService.isUserAdmin()) {
	response.sendRedirect(JSPConstants.INDEX);
	return;
}
String requestChannel = request.getParameter("channel");
ShrineChannel channel = new ShrineChannel();
channel.loadShrineChannelName(
		(requestChannel != null && !requestChannel.isEmpty()) ? requestChannel : "shrineoflostsecrets");
%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html lang="en" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html lang="en" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html lang="en" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<script>
var votes = [];
var ready = false;

async function fetchAndDisplayVotes() {
    try {
        console.info('Fetching votes');
        // Fetch events from the server
        const response = await fetch('<%=JSPConstants.SERVICELISTVOTE%>channel=<%=channel.getTwitchChannel()%>&serviceType=<%=ShrineServiceTypeEnum.SAFE.getName()%>&size=100');
			if (!response.ok) {
				throw new Error(`HTTP error! status: ${response.status}`);
			}
			const data = await
			response.json();

			// Update the votes array with new data
			votes = data.votes || []; // Fallback to an empty array if no votes are present
			ready = true;

		} catch (error) {
			console.error('Failed to fetch votes:', error);
		}
	}

	// Function to periodically fetch and display new votes
	function startUpdates() {
		fetchAndDisplayVotes(); // Initial fetch
		setInterval(updateDiv, 10000); // Then every 30 seconds

	}
</script>
<!--
        Google Fonts
        ============================================= -->
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700"
	rel="stylesheet" type="text/css">

<!--
        CSS
        ============================================= -->
<!-- Fontawesome -->
<script src="https://kit.fontawesome.com/efcd4206ed.js"
	crossorigin="anonymous"></script>
<!-- Bootstrap -->
<link rel="stylesheet" href="/css/bootstrap.min.css">
<!-- Fancybox -->
<link rel="stylesheet" href="/css/jquery.fancybox.css">
<!-- owl carousel -->
<link rel="stylesheet" href="/css/owl.carousel.css">
<!-- Animate -->
<link rel="stylesheet" href="/css/animate.css">
<!-- Main Stylesheet -->
<link rel="stylesheet" href="/css/main.css">
<!-- Main Responsive -->
<link rel="stylesheet" href="/css/responsive.css">
</head>
<body onload="startUpdates()"
	style="background-color: #000000; background-image: url('/img/banner_bg.png'); background-position: center; background-repeat: no-repeat; background-size: cover;">
	<div
		style="position: absolute; top: 20px; right: 10px; color: white; text-align: right;">
		<img src="/img/logo-sm.jpg" alt="Shrine Of Lost Secrets Logo">
		<h3>Shrine Of Lost Secrets</h3>
		<h4>shrineoflostsecrets.com</h4>
		<h1>
			twitch.tv/<%=channel.getTwitchChannel()%></h1>
	</div>
	<div class="container" style="width: 800px; height: 600px;">
		<div class="row align-items-center justify-content-between">
			<div class="col-lg-6 col-md-8"
				style="position: absolute; left: 100px; top: 75px;">
				<div class="banner_text">
					<div style="color: white">
						<h1 style="padding-bottom: 20px;">
							Score:<span id="score"></span>
						</h1>
						<h2 id="body"></h2>
						<h2 style="padding-top: 15px;">
							By: <span id="by"></span>
						</h2>
					</div>
				</div>
			</div>
			<div class="col-lg-6 col-md-8"
				style="position: absolute; right: 20px; bottom: 5px;">
				<div class="banner_text">
					<div style="color: white">
						<h1 id="message"></h1>
					</div>
				</div>
			</div>
		</div>
	</div>
<body>
	<script>
		// Initialize the votes array
		var messages = [];
				messages[0] = "Welcome to the Shrine Of Lost Secrets",
				messages[1] = "If this channel is not for you, it's ok, you can turn it off",
				messages[2] = "Vote early, Vote Often",
				messages[3] = "Following the Channel will provide you to expanded access, we will whisper you your access",
				messages[4] = "The Shrine is full of secrets that are not fit for the broadcast, Visit: shrineoflostsecrets.com"
				messages[5] = "Follers, Subscriptions will multiply the power of your votes"
				messages[6] = "Use your Channel Points to vote"

		// Function to update the div content
		var currentVote = 0; // Initialize a global index variable.
		var currentMessage = 0; // Initialize a global index variable.

		function updateDiv() {
			console.info("ready " + ready);

			if (ready) {
				console.info("currentMessage " + currentMessage);

				console.info("currentVote " + currentVote + " votes.length "
						+ votes.length);

				var message = document.getElementById("message");
				if (messages && messages.length > 0) {
					message.innerHTML = messages[currentMessage];
					currentMessage = (currentMessage + 1) % messages.length; // Increment or reset index
				} else {
					console
							.error("Element with id 'message' was not found or no message available.");
				}

				var body = document.getElementById("body");
				var by = document.getElementById("by");
				var score = document.getElementById("score");
				console.log(votes[currentVote]);
				console.info("votes[] " + votes);
				body.innerHTML = votes[currentVote].message;
				by.innerHTML = votes[currentVote].twitchUser;
				score.innerHTML = votes[currentVote].amount;
				currentVote = (currentVote + 1); // Increment or reset index
				if (currentVote >= votes.length) {
					currentVote = 0;
					ready = false;
					fetchAndDisplayVotes();
				}
			}

		}
	</script>
</html>