<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.enumerations.*"%>
<%

boolean unlimited = Boolean.valueOf(request.getParameter("unlimited"));
ShrineServiceTypeEnum serviceType = ShrineServiceTypeEnum.findById(request.getParameter("serviceType"));
String id = request.getParameter("id");
String requestChannel = request.getParameter("channel");
String userName = request.getParameter("userName");
boolean safe = Boolean.valueOf(request.getParameter("safe"));
String channel = (requestChannel != null && !requestChannel.isEmpty()) ? requestChannel : "shrineoflostsecrets";




// Check if there is a stored auth value in the session
String sessionAuth = (String) request.getSession().getAttribute("auth");
ShrineUser su = new ShrineUser();
if (sessionAuth != null && !su.isValue()) {
	// Use the auth from the session if there is no auth in the URL
	su.loadShrineUser(sessionAuth);
}

%><!DOCTYPE html>
<!--[if lt IE 7]>      <html lang="en" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html lang="en" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html lang="en" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
    <head>
        <script>
     // Function to fetch and display vote events
        async function fetchAndDisplayVotes() {
            try {
                console.info('Fetching votes');

                // Fetch events from the server
                const response = await fetch('/service/listVotesService.jsp?channel=<%=requestChannel%>&safe=<%=safe%>');
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
                data.votes.forEach(vote => {
                    const voteElement = document.createElement('div');
                    voteElement.className = 'col-md-3 col-sm-6 col-xs-12';                    
                    voteElement.innerHTML = '<div class="fact-item text-center">' +
                    '<div class="fact-icon">' +
                        '<i class="fa fa-trophy fa-lg"></i>' +
                    '</div>' +
                    '<span data-to="' + vote.amount + '">' + vote.amount + '</span>' +
                    '<p>' + vote.message + '</p>' +
                    '<p>By: ' + vote.twitchUser + '</p>' +
                '</div>';
                    votesContainer.appendChild(voteElement);
                });
            } catch (error) {
                console.error('Failed to fetch votes:', error);
            }
        }
     
        async function fetchAndDisplayChannelEvents() {
            try {
                console.info('Fetching Events');

                // Fetch events from the server
                const response = await fetch('/service/channelEventJson.jsp?channel=<%=requestChannel%>&serviceType=<%=serviceType.toString()%>&userName=<%=userName%>&unlimited=<%=unlimited%>');
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
                    eventElement.innerHTML = '<div class="col-md-6 col-sm-12">' +
                '<div class="media">' +
                    '<a href="#" class="pull-left">' +
                        '<img src="img/icons/monitor.png" class="media-object" alt="Monitor">' +
                    '</a>' +
                    '<div class="media-body">' +
                        '<h3>' + event.twitchUser + '</h3>' +
                        '<p>' + event.message + '</p>' +
                    '</div>' +
                '</div>' +
            '</div>';
		
            
                eventContainer.appendChild(eventElement);
                });
            } catch (error) {
                console.error('Failed to fetch votes:', error);
            }
        }
     // Function to periodically fetch and display new votes
        function startUpdates() {
          // fetchAndDisplayVotes(); // Initial fetch
           // setInterval(fetchAndDisplayVotes, 60000); // Repeat every 10 seconds
           fetchAndDisplayChannelEvents(); // Initial fetch

        }
        </script>
        <!-- Mobile Specific Meta -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Always force latest IE rendering engine -->
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- Meta Keyword -->
        <meta name="keywords" content="one page, business template, single page, onepage, responsive, parallax, creative, business, html5, css3, css3 animation">
        <!-- meta character set -->
        <meta charset="utf-8">

        <!-- Site Title -->
        <title>Shrine Channel: <%=channel %></title>
        
        <!--
        Google Fonts
        ============================================= -->
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700" rel="stylesheet" type="text/css">
		
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
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <!-- /responsive nav button -->

                    <!-- logo -->
                    <h1 class="navbar-brand">
                        <a href="#body">
                            <img src="img/logo-sm.jpg" alt="Shrine Logo">
                        </a>
                    </h1>
                    <!-- /logo -->

                    </div>

                   <!-- main nav -->
			<nav class="collapse navigation navbar-collapse navbar-right"
				role="navigation">
				<ul id="nav" class="nav navbar-nav">
					<li class="current"><a href="#home">Home</a></li>
					<li><a href="<%=JSPConstants.INDEX%>#channel">Channels</a></li>
					<li><a href="<%=JSPConstants.INDEX%>#voting">Voting</a></li>
					<li><a href="<%=JSPConstants.INDEX%>#contact">Contact</a></li>
				</ul>
			</nav>
			<!-- /main nav -->
                </div>
        </header>
        <!--
        End Fixed Navigation
        ==================================== -->
 <!--
        #count
        ========================== -->
        
        <section id="count">
            <div class="container">
                <div class="row">
                    <div class="counter-section clearfix" id="votes">
                    </div>
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
            <div class="container" id="channelEvent">
            </div> <!-- end .container -->
        </section>
        <!--
        End #service
        ========================== -->         
        <!--
        #quotes
        ========================== -->
        <section id="quotes">
            <div class="container">
                <div class="row wow zoomIn">
                    <div class="col-lg-12">
                        <div class="call-to-action text-center">
                            <p>âACCUMSAN ID CURABITUR ET PORTITOR MAURIS BLANDIT DOLOR LOREM SOME EST OUR DONEC STIN LORTIOSâ</p>
                            <span>John Doe</span>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!--
        End #quotes
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
                            <h3>We are social</h3>
                            <ul class="text-center list-inline">
                                <li><a href="#"><i class="fa fa-facebook fa-lg"></i></a></li>
                                <li><a href="#"><i class="fa fa-twitter fa-lg"></i></a></li>
                                <li><a href="#"><i class="fa fa-google-plus fa-lg"></i></a></li>
                                <li><a href="#"><i class="fa fa-dribbble fa-lg"></i></a></li>
                            </ul>
                        </div>

                        <div class="copyright">
                            <p>Developed by <a target="_blank" href="http://www.themefisher.com">Themefisher</a></p>
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
    <script></script>
</html>
