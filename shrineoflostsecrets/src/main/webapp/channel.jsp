<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page
	import="com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.enumerations.*,com.shrineoflostsecrets.channel.constants.*"%>
<%
boolean unlimited = Boolean.valueOf(request.getParameter("unlimited"));
ShrineServiceTypeEnum serviceType = ShrineServiceTypeEnum.findById(request.getParameter("serviceType"));
String id = request.getParameter("id");
String requestChannel = request.getParameter("channel");
String userName = request.getParameter("userName");
boolean safe = Boolean.valueOf(request.getParameter("safe"));
ShrineChannel channel = new ShrineChannel();
channel.loadShrineChannelName((requestChannel != null && !requestChannel.isEmpty()) ? requestChannel : "shrineoflostsecrets");




// Check if there is a stored auth value in the session
String sessionAuth = (String) request.getSession().getAttribute("auth");
ShrineUser su = new ShrineUser();
if (sessionAuth != null && !su.isValid()) {
	// Use the auth from the session if there is no auth in the URL
	su.loadShrineUser(sessionAuth);
}
%><!DOCTYPE html>
<!--[if lt IE 7]>      <html lang="en" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html lang="en" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html lang="en" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<script>
     // Function to fetch and display vote events
        async function fetchAndDisplayVotes() {
            try {
                console.info('Fetching votes');

                // Fetch events from the server
                const response = await fetch('<%=JSPConstants.SERVICELISTVOTE%>channel=<%=channel.getTwitchChannel()%>&serviceType=<%=serviceType.getName()%>');
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();
                
                

                // Update the status div to indicate new votes have been fetched
                const statusDiv = document.getElementById('status');
                const now = new Date();
                const currentTime = now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds();
                console.info( 'New votes have been fetched! Time: ' + currentTime);

                // Process and display each vote
                const votesContainer = document.getElementById('votes');
                votesContainer.innerHTML = ''; // Clear previous votes to display only new ones
                var first=true;
                data.votes.forEach(vote => { 
    					if(first){
    						first=false;	
    						document.getElementById('headline').innerHTML = 'Total <a href="javascript:void(0);" onclick="vote(\'' + vote.id + '\', \'<%=channel.getTwitchChannel()%>\', \'100\'); return false;" class="vote">Votes</a>: ' + vote.amount + ' - ' + vote.message;
    						document.getElementById('by').innerHTML = 'By: <a href="<%=JSPConstants.CHANNEL%>?channel=<%=channel.getTwitchChannel()%>&userName=' + vote.twitchUser + '">' +  vote.twitchUser + '</a>'+ 
                    		' - <a href="javascript:void(0);" onclick="vote(\'' + vote.id + '\', \'<%=channel.getTwitchChannel()%>\', \'100\'); return false;" class="vote">Vote!</a>';
    					}
    					else{
    	                    const voteElement = document.createElement('div');
							voteElement.className = 'col-md-3 col-sm-6 col-xs-12';                    
                    		voteElement.innerHTML = '<div class="fact-item text-center">' +
                    		'<div class="fact-icon">' +
                        	'<i class="fa fa-trophy fa-lg"></i>' +
                    		'</div>' +
                    		'<span">Total <a href="javascript:void(0);" onclick="vote(\'' + vote.id + '\', \'<%=channel.getTwitchChannel()%>\', \'100\'); return false;" class="vote">Votes</a>: ' + vote.amount + '</span>' +
                    		'<p>' + vote.message + '</p>' +
                    		'<p>By: ' + vote.twitchUser + '</p>' +
                    		'<a href="javascript:void(0);" onclick="vote(\'' + vote.id + '\', \'<%=channel.getTwitchChannel()%>\', \'100\'); return false;" class="vote">Vote!</a>'+
                			'</div>';
                            votesContainer.appendChild(voteElement);
    					}
                });
            } catch (error) {
                console.error('Failed to fetch votes:', error);
            }
        }
     
        async function fetchAndDisplayChannelEvents() {
            try {
                console.info('Fetching Events');

                // Fetch events from the server
                const response = await fetch('<%=JSPConstants.SERVICECHANNELEVENT%>?channel=<%=channel.getTwitchChannel()
                %>&serviceType=<%=serviceType.getName()%>&userName=<%=userName%>');
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();                

                // Update the status div to indicate new events have been fetched
                const statusDiv = document.getElementById('status');
                const now = new Date();
                const currentTime = now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds();
                console.info( 'New events have been fetched! Time: ' + currentTime);

             // Process and display each vote
                const eventContainer = document.getElementById('channelEvent');
                eventContainer.innerHTML = ''; // Clear previous events to display only new ones
                data.events.forEach(event => {
                    const eventElement = document.createElement('div');
                    eventElement.className = 'col-md-3 col-sm-6 col-xs-12';                    
                   var cnt = '<div class="col-md-12 col-sm-12">' +
                	'<div class="media">' +
                    '<a href="<%=JSPConstants.CHANNEL%>?channel=<%=channel.getTwitchChannel()%>&userName=' + event.twitchUser + '">' +
                    '<img src="' + event.logo + '" class="media-object" alt="Monitor">' +
                    '</a>' +
                    '<div class="media-body">' +
                    '<h3><a href="<%=JSPConstants.CHANNEL%>?channel=<%=channel.getTwitchChannel()%>&userName=' + event.twitchUser + '">' + event.twitchUser + '</a></h3>' +
                    '</div>' +
                    '</div>' +
                    '</div>';

                    if("<%=TwitchChannelConstants.ONUSERBAN%>" == event.eventType){
                    	cnt = cnt + '<p class="deleted">User ' + event.twitchUser + ' was Banned!</p>';
                    }
                    else if("<%=TwitchChannelConstants.ONDELETEMESSAGE%>" == event.eventType){
                    	cnt = cnt + '<p class="deleted">' + event.message + '</p>';
                    }
                    else{
                    	cnt = cnt + '<p>' + event.message + '</p>';
                    }
                    
                    cnt = cnt + '<a href="javascript:void(0);" onclick="vote(\'' + event.id + '\', \'<%=channel.getTwitchChannel()%>\', \'100\'); return false;" class="vote">Vote!</a>'+
                    '</div>';
                    eventElement.innerHTML = cnt;

                	eventContainer.appendChild(eventElement);
                });
            } catch (error) {
                console.error('Failed to fetch votes:', error);
            }
        }
        
        async function fetchAndDisplayUser() {
            try {
                console.info('Fetching User');

                // Fetch events from the server
                const response = await fetch('<%=JSPConstants.SERVICEUSER%>');
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();                

                // Update the status div to indicate new events have been fetched
                const statusDiv = document.getElementById('status');
                const now = new Date();
                const currentTime = now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds();
                console.info( 'User have been fetched! Time: ' + currentTime);
                document.getElementById('voteRemaining').innerHTML = data.voteRemaining;
                document.getElementById('voteMulti').innerHTML = data.voteMulti;
            } catch (error) {
                console.error('Failed to fetch votes:', error);
            }
        }
     // Function to periodically fetch and display new votes
        function startUpdates() {
           fetchAndDisplayVotes(); // Initial fetch
           fetchAndDisplayChannelEvents(); // Initial fetch
           <%if (su.isValid()) {%>
           	fetchAndDisplayUser();
           <%}%>
           <%if (channel.isLive()) {%>
           	setInterval(fetchAndDisplayVotes, 60000); // Repeat every 10 seconds
            setInterval(fetchAndDisplayChannelEvents, 10000); // Repeat every 10 seconds
			<%}%>

        }
        // Function to perform a vote
        async function vote(eventId, channel, amount) {
        <%if (su.isValid()) {%>
    	 const url = ('/service/voteEvent.jsp?id='+ eventId + '&channel=' + channel + '&amount=' + amount);
            try {
                const response = await fetch(url, {
                    method: 'GET', // or 'POST' if your server requires
                });
                const data = await response.text(); // Assuming the response is text
                console.info("Vote Recorded " + data);
                fetchAndDisplayVotes();
                fetchAndDisplayUser();
/*                 alert(data); // Show the response in an alert box
 */            } catch (error) {
                console.error('Error during fetch:', error);
            }
            <%} else {%>
            alert("We would love to record your vote, but you must login first");
            <%}%>
        }
     
        </script>
<!-- Mobile Specific Meta -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Always force latest IE rendering engine -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Meta Keyword -->
<meta name="keywords"
	content="one page, business template, single page, onepage, responsive, parallax, creative, business, html5, css3, css3 animation">
<!-- meta character set -->
<meta charset="utf-8">

<!-- Site Title -->
<title>Shrine: <%=channel%></title>

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
<link rel="stylesheet" href="css/font-awesome.min.css">
<!-- Bootstrap -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<!-- Fancybox -->
<link rel="stylesheet" href="css/jquery.fancybox.css">
<!-- owl carousel -->
<link rel="stylesheet" href="css/owl.carousel.css">
<!-- Animate -->
<link rel="stylesheet" href="css/animate.css">
<!-- Main Stylesheet -->
<link rel="stylesheet" href="css/main.css">
<!-- Main Responsive -->
<link rel="stylesheet" href="css/responsive.css">


<!-- Modernizer Script for old Browsers -->
<script src="js/vendor/modernizr-2.6.2.min.js"></script>

</head>

<body onload="startUpdates()">
	<!--
        Fixed Navigation
        ==================================== -->
	<header id="navigation" class="navbar-fixed-top">
		<div class="container">

			<div class="navbar-header">
				<!-- responsive nav button -->
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<!-- /responsive nav button -->

				<!-- logo -->
				<h1 class="navbar-brand call-to-action p">
					<a href="<%=JSPConstants.INDEX%>#body"> <img src="img/logo-sm.jpg" alt="Shrine Logo">
					<%if (su.isValid()) {%>
						Welcome <%=su.getTwitchUserName()%>
						Left: <span id="voteRemaining"></span>
						X:<span id="voteMulti"></span>
					<%}%>
					</a>
				</h1>
				<!-- /logo -->

			</div>

			<!-- main nav -->
			<nav class="collapse navigation navbar-collapse navbar-right"
				role="navigation">
				<ul id="nav" class="nav navbar-nav">
					<li><a href="<%=JSPConstants.INDEX%>#home">Home</a></li>
					<li class="current"><a href="<%=JSPConstants.INDEX%>#channel">Channels</a></li>
					<li><a href="<%=JSPConstants.INDEX%>#voting">Voting</a></li>
					<li><a href="<%=JSPConstants.INDEX%>#contact">Contact</a></li>
					<li><a href="<%=JSPConstants.INDEX%>#login">Login - Register</a></li>
					
				</ul>
			</nav>
			<!-- /main nav -->
		</div>
	</header>
	<!--
        End Fixed Navigation
        ==================================== -->
        <!--
        #quotes
        ========================== -->
	<section id="quotes" style="padding:120px">
		<div class="container">
			<div class="row wow zoomIn">
				<div class="col-lg-12">
					<div class="call-to-action text-center">
						<p id=headline></p>
						<span id=by></span>
						
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--
        End #quotes
        ========================== -->
	<!--
        #count
        ========================== -->

	<section id="count">
		<div class="container">
			<div class="row">
				<div class="counter-section clearfix" id="votes"></div>
			</div>
		</div>
	</section>
	<!--
        End #count
        ========================== -->


	<!--
        #service
        ========================== -->
	<section id="service">
		<div class="section-title text-center wow fadeInDown">
			<h2>Channel: <a href="<%=JSPConstants.CHANNEL%>?channel=<%=channel.getTwitchChannel()%>"><%=channel.getTwitchChannel() %> <%if (channel.isLive()) {%>Live !<%}%>
			</a></h2>
			<p>
			<a href="<%=JSPConstants.CHANNEL%>?channel=<%=channel.getTwitchChannel()%>&serviceType=<%=ShrineServiceTypeEnum.SAFE.getName()%>"><%=ShrineServiceTypeEnum.SAFE.getName()%></a> | 
			<a href="<%=JSPConstants.CHANNEL%>?channel=<%=channel.getTwitchChannel()%>&serviceType=<%=ShrineServiceTypeEnum.LIMITED.getName()%>"><%=ShrineServiceTypeEnum.LIMITED.getName()%></a> | 
			<a href="<%=JSPConstants.CHANNEL%>?channel=<%=channel.getTwitchChannel()%>&serviceType=<%=ShrineServiceTypeEnum.FULL.getName()%>"><%=ShrineServiceTypeEnum.FULL.getName()%></a> | 
			<a href="<%=JSPConstants.CHANNEL%>?channel=<%=channel.getTwitchChannel()%>&serviceType=<%=ShrineServiceTypeEnum.BAN.getName()%>"><%=ShrineServiceTypeEnum.BAN.getName()%></a></p>
			<%if(null != userName && userName.length() > 0){ %>
			<h3>User Filter:<%=userName %></h3>
			<%}%>
			
		</div>
		<div class="container" id="channelEvent"></div>
		
		<!-- end .container -->
	</section>
	<!--
        End #service
        ========================== -->
	
	<!--
        #footer
        ========================== -->
	<footer id="footer" class="text-center">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">

					<div class="footer-logo wow fadeInDown">
						<img src="img/logo-sm.jpg" alt="logo">
					</div>

					<div class="footer-social wow fadeInUp">
						<h3>Social</h3>
						<ul class="text-center list-inline">
							<li><a href="https://www.twitch.tv/shrineoflostsecrets"
								target="_blank"><i class="fa fa-twitch fa-lg"></i></a></li>
						</ul>
					</div>

					<div class="copyright">
						<p>
							ShrineofLostSecrets 2024
						</p>
					</div>

				</div>
			</div>
		</div>
	</footer>
	<!--
        End #footer
        ========================== -->


	<!--
        JavaScripts
        ========================== -->

	<!-- main jQuery -->
	<script src="js/vendor/jquery-1.11.1.min.js"></script>
	<!-- Bootstrap -->
	<script src="js/bootstrap.min.js"></script>
	<!-- jquery.nav -->
	<script src="js/jquery.nav.js"></script>
	<!-- Portfolio Filtering -->
	<script src="js/jquery.mixitup.min.js"></script>
	<!-- Fancybox -->
	<script src="js/jquery.fancybox.pack.js"></script>
	<!-- Parallax sections -->
	<script src="js/jquery.parallax-1.1.3.js"></script>
	<!-- jQuery Appear -->
	<script src="js/jquery.appear.js"></script>
	<!-- countTo -->
	<script src="js/jquery-countTo.js"></script>
	<!-- owl carousel -->
	<script src="js/owl.carousel.min.js"></script>
	<!-- WOW script -->
	<script src="js/wow.min.js"></script>
	<!-- theme custom scripts -->
	<script src="js/main.js"></script>
</body>
</html>
