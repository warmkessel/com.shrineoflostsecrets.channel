<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.shrineoflostsecrets.channel.constants.*"%>
<%
boolean unlimited = Boolean.valueOf(request.getParameter("unlimited"));
boolean ban = Boolean.valueOf(request.getParameter("ban"));
String id = request.getParameter("id");
String requestChannel = request.getParameter("channel");
String userName = request.getParameter("userName");
String channel = (requestChannel != null && !requestChannel.isEmpty()) ? requestChannel : "shrineoflostsecrets";
%>
<%=channel%>
<html>
<head>
<script async="true"
	src="https://www.googletagmanager.com/gtag/js?id=G-N2VTBWYNCJ"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'G-N2VTBWYNCJ');
</script>
<title>Channel Events Dynamic Display</title>
<script>
        // Function to fetch and display events
        async function fetchAndDisplayEvents() {
            try {
                console.info('Fetching events');

                // Fetch events from the server
                const response = await fetch('/service/channelEventJson.jsp?channel=<%=requestChannel%>&ban=<%=ban%>&userName=<%=userName%>&unlimited=<%=unlimited%>');
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();

                // Update the status div to indicate new events have been fetched
                const statusDiv = document.getElementById('status');
             // Create a new Date object to get the current date and time
                const now = new Date();

                // Format the current time as a string in a readable format (e.g., HH:MM:SS)
                const currentTime = now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds();

                // Set the textContent of statusDiv to include the message and the current time
                statusDiv.textContent = 'New events have been fetched! Time: ' + currentTime;
                // Process and display each event
                const eventsContainer = document.getElementById('events');
                eventsContainer.innerHTML = ''; // Clear previous events to display only new ones
                data.events.forEach(event => {
                    //console.log(event.message); // Log each event's message to the console
                    const eventElement = document.createElement('div');
                    var eventType = event.eventType; // Assuming eventType holds the type of event

                    switch (eventType) {
                        case "<%=TwitchChannelConstants.ONCHANNELMESSAGE%>":
                            eventType = "Message ";
                            break;
                        case "<%=TwitchChannelConstants.ONDELETEMESSAGE%>":
                            eventType = "Delete ";
                            break;
                        case "<%=TwitchChannelConstants.ONUSERBAN%>":
                            eventType = "Ban ";
                            break;
                        // No default case needed since eventType is initialized with the event's type
                    }
                    
                    eventElement.innerHTML = eventType + ' Event: ' + event.createdDate + ' <a href="https://www.twitch.tv/' + event.twitchUser +'">' + event.twitchUser + '</a>: ' + event.message;
                    if(event.eventType === "<%=TwitchChannelConstants.ONUSERBAN%>" || event.eventType === "<%=TwitchChannelConstants.ONDELETEMESSAGE%>") {
                        eventElement.style.color = "red";
                    }
                    eventsContainer.appendChild(eventElement);
                });
            } catch (error) {
                console.error('Failed to fetch events:', error);
            }
        }

        // Function to periodically fetch and display new events
        function startEventUpdates() {
            fetchAndDisplayEvents(); // Initial fetch
            setInterval(fetchAndDisplayEvents, 10000); // Repeat every 10 seconds
        }
    </script>
</head>
<body onload="startEventUpdates()">
	<div id="status">Waiting for events...</div>
	<!-- Status div to be updated on new events -->

	<%
	if (ban) {
	%>Deleted Messages<%
	} else {
	%>All Messages<%
	}
	%>
	<h1>Channel Events</h1>
	<div id="events"></div>
	<!-- Container for events -->
</body>
</html>
