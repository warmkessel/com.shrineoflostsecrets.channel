<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.HttpSession,com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.util.*,com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.datastore.*,com.google.cloud.datastore.*"%>
<%
ShrineUser su = new ShrineUser();
// Check if there is a stored auth value in the session
String sessionAuth = (String) request.getSession().getAttribute("auth");
if (sessionAuth != null && !su.isValue()) {
	// Use the auth from the session if there is no auth in the URL
	su.loadShrineUser(sessionAuth);
}
%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html lang="en" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html lang="en" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html lang="en" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<!-- Google tag (gtag.js) -->
<script async="true"
    src="https://www.googletagmanager.com/gtag/js?id=G-N2VTBWYNCJ"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'G-N2VTBWYNCJ');
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
<title>The Shrine of Lost Secrets</title>
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
<body>

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
				<h1 class="navbar-brand">
					<a href="#body"> <img src="img/logo.png" alt="Shrine Of Lost Secrets Logo">
					</a>
				</h1>
				<!-- /logo -->

			</div>

			<!-- main nav -->
			<nav class="collapse navigation navbar-collapse navbar-right"
				role="navigation">
				<ul id="nav" class="nav navbar-nav">
					<li class="current"><a href="#home">Home</a></li>
					<li><a href="#channel">Channels</a></li>
					<li><a href="#voting">Voting</a></li>
					<li><a href="#contact">Contact</a></li>
				</ul>
			</nav>
			<!-- /main nav -->
		</div>
	</header>
	<!--
        End Fixed Navigation
        ==================================== -->


	<!--
        Home Slider
        ==================================== -->
	<section id="home">
		<div id="home-carousel" class="carousel slide" data-interval="false">
			<ol class="carousel-indicators">
				<li data-target="#home-carousel" data-slide-to="0" class="active"></li>
				<li data-target="#home-carousel" data-slide-to="1"></li>
				<li data-target="#home-carousel" data-slide-to="2"></li>
			</ol>
			<!--/.carousel-indicators-->

			<div class="carousel-inner">

				<div class="item active"
					style="background-image: url('img/slider/bg2.jpg')">
					<div class="carousel-caption">
						<div class="animated bounceInRight">
							<h1>
								Welcome <%= su.getTwitchUserName()%>! <br>The Shrine of Lost Secrets
							</h1>
							<p>The Shrine contains lost secrets, Discover what
								we hold in our cave of wonders.  Come in and see!</p>
						</div>
					</div>
				</div>

				<div class="item"
					style="background-image: url('https://static-cdn.jtvnw.net/jtv_user_pictures/6061ee4d-dae2-4fc8-8910-fdff7a02094f-profile_banner-480.jpeg')">
					<div class="carousel-caption">
						<div class="animated bounceInDown">
							<h2>Sadgirl</h2>
							<p>
								<b>Why are u a sad girl?</b> No dad								
							</p>
						</div>
					</div>
				</div>

				<div class="item"
					style="background-image: url('https://pbs.twimg.com/card_img/1753631827821047808/TKagMHSR?format=png&amp;name=360x360')">
					<div class="carousel-caption">
						<div class="animated bounceInUp">
							<h2>Lazeruss</h2>
							<p>
								<b>Arrive exactly when I intend to and leave when I'm ready.</b>
							</p>
						</div>
					</div>
				</div>
			</div>
			<!--/.carousel-inner-->
			<nav id="nav-arrows"
				class="nav-arrows hidden-xs hidden-sm visible-md visible-lg">
				<a class="sl-prev hidden-xs" href="#home-carousel" data-slide="prev">
					<i class="fa fa-angle-left fa-3x"></i>
				</a> <a class="sl-next" href="#home-carousel" data-slide="next"> <i
					class="fa fa-angle-right fa-3x"></i>
				</a>
			</nav>

		</div>
	</section>
	<!--
        End #home Slider
        ========================== -->


	<!--
        #service
        ========================== -->
	<section id="channel">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="section-title text-center wow fadeInDown">
						<h2>Channel</h2>
						<p>Check out some of our favorite channels.</p>
					</div>
				</div>
			</div>
			<div class="row">

<%
List<Entity> listChanels = ShrineChannelService.listChannels();
    	for(Entity entity: listChanels) {
    		ShrineChannel channel = new ShrineChannel();
    		channel.loadFromEntity(entity);
    		if(!Constants.SHRINEOFLOSTSECRETS.equals(channel.getTwitchChannel())){
    		boolean live = (channel.getTwitchLastStart().getSeconds() - channel.getTwitchLastEnd().getSeconds() > 0);
%>
				<div class="col-md-6 col-sm-12 wow fadeInLeft">
					<div class="media">
						<a href="https://www.twitch.tv/<%=channel.getTwitchChannel() %>" class="pull-left" target="_blank"><img
							class="InjectLayout-sc-1i43xsx-0 cXFDOs tw-image tw-image-avatar"
							alt="<%=channel.getTwitchChannel() %>" alt="Cog"
							src="<%=channel.getTwitchLogoImg()%>">
						</a>
						<div class="media-body">
							<h3>Top Comment</h3>
							<p><%=channel.getTwitchBestQuote()%></p>
						</div>
						<a href="<%if(live){%><%=JSPConstants.CHANNELEVENTDYNAMIC%><%}else{ %><%=JSPConstants.CHANNELEVENT%><%}%>?channel=<%=channel.getTwitchChannel() %>">Enter the Shrine</a> - <a href="<%=JSPConstants.CHANNELVOTE%>?channel=<%=channel.getTwitchChannel()%>">Score Board!</a>
					</div>
				</div>
<%}}%>

			</div>
			<!-- end .row -->
		</div>
		<!-- end .container -->
	</section>
	<!--
        End #service
        ========================== -->
	<!--
        #count
        ========================== -->

	<section id="count">
		<div class="container">
			<div class="row">
				<div class="counter-section clearfix">

					<div class="col-md-3 col-sm-6 col-xs-12 wow fadeInLeft"
						data-wow-delay="0.5s">
						<div class="fact-item text-center">
							<div class="fact-icon">
								<i class="fa fa-check-square fa-lg"></i>
							</div>
							<span data-to="120">0</span>
							<p>Votes Cast</p>
						</div>
					</div>

					<div class="col-md-3 col-sm-6 col-xs-12 wow fadeInLeft"
						data-wow-delay="0.8s">
						<div class="fact-item text-center">
							<div class="fact-icon">
								<i class="fa fa-users fa-lg"></i>
							</div>
							<span data-to="152">0</span>
							<p>Channels</p>
						</div>
					</div>

					<div class="col-md-3 col-sm-6 col-xs-12 wow fadeInLeft"
						data-wow-delay="1.1s">
						<div class="fact-item text-center last">
							<div class="fact-icon">
								<i class="fa fa-clock-o fa-lg"></i>
							</div>
							<span data-to="2500">0</span>
							<p>Follers</p>
						</div>
					</div>

					<div class="col-md-3 col-sm-6 col-xs-12 wow fadeInLeft"
						data-wow-delay="1.3s">
						<div class="fact-item text-center last">
							<div class="fact-icon">
								<i class="fa fa-trophy fa-lg"></i>
							</div>
							<span data-to="150">0</span>
							<p>Subscribers</p>
						</div>
					</div>

				</div>
			</div>
		</div>
	</section>
	<!--
        End #count
	<!--
        #voting
        ========================== -->
	<section id="voting">
		<div class="container">
			<div class="row">

				<div class="section-title text-center wow fadeInUp">
					<h2>Voting Multipliers</h2>
					<p>ALL multipliers count toward ALL votes cast.</p>
				</div>

				<div class="col-md-3 col-sm-6 col-xs-12 wow fadeInUp">
					<div class="pricing-table text-center">
						<div class="price">
							<span class="plan">Follow</span> <span class="value">2X
								Multiplier</span>
						</div>
						<a href="https://www.twitch.tv/shrineoflostsecrets"
							class="btn btn-price">Follow Now</a>
					</div>
				</div>

				<div class="col-md-3 col-sm-6 col-xs-12 wow fadeInUp"
					data-wow-delay="0.3s">
					<div class="pricing-table text-center">
						<div class="price">
							<span class="plan">Tier 1 Subscription</span> <span class="value">4X
								Multiplier</span>
						</div>
						<a href="https://www.twitch.tv/shrineoflostsecrets"
							class="btn btn-price">Subscribe Now</a>
					</div>
				</div>

				<div class="col-md-3 col-sm-6 col-xs-12 wow fadeInUp"
					data-wow-delay="0.6s">
					<div class="pricing-table text-center">
						<div class="price">
							<span class="plan">Tier 2 Subscription</span> <span class="value">8X
								Multiplier</span>
						</div>
						<a href="https://www.twitch.tv/shrineoflostsecrets"
							class="btn btn-price">Subscribe Now</a>
					</div>
				</div>

				<div class="col-md-3 col-sm-6 col-xs-12 wow fadeInUp"
					data-wow-delay="0.9s">
					<div class="pricing-table text-center">
						<div class="price">
							<span class="plan">Tier 3 Subscription</span> <span class="value">16X
								Multiplier</span>
						</div>
						<a href="https://www.twitch.tv/shrineoflostsecrets"
							class="btn btn-price">Subscribe Now</a>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 col-xs-12 wow fadeInUp">
					<div class="pricing-table text-center">
						<div class="price">
							<span class="plan">Founder</span> <span class="value">+ 2X
								Multiplier</span>
						</div>
						<a href="https://www.twitch.tv/shrineoflostsecrets"
							class="btn btn-price">Subscribe Now</a>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 col-xs-12 wow fadeInUp">
					<div class="pricing-table text-center">
						<div class="price">
							<span class="plan">VIP</span> <span class="value">+ 2X
								Multiplier</span>
						</div>
						<a href="https://www.twitch.tv/shrineoflostsecrets"
							class="btn btn-price">Subscribe Now</a>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 col-xs-12 wow fadeInUp">
					<div class="pricing-table text-center">
						<div class="price">
							<span class="plan">BROADCASTER</span> <span class="value">+
								2X Multiplier</span>
						</div>
						<a href="#contact" class="btn btn-price">Subscribe Now</a>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--
        End #voting
        ========================== -->
   	<!--
        #quotes
        ========================== -->
	<section id="quotes">
		<div class="container">
			<div class="row wow zoomIn">
				<div class="col-lg-12">
					<div class="call-to-action text-center">
						<p>"Vote Early, Vote Often"</p>
						<span>Shrine</span>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--
        End #quotes
        ========================== -->
        
        
	<!--
        #contact
        ========================== -->
	<section id="contact">
		<div class="container">
			<div class="row">

				<div class="section-title text-center wow fadeInDown">
					<h2>Contact Us</h2>
					<p>We would love to talk with you. Find us on <a href="https://www.twitch.tv/shrineoflostsecrets">>Twitch</a></p>
				</div>
			</div>
		</div>
	</section>
	<!--
        End #contact
        ========================== -->

	<!--
        #footer
        ========================== -->
	<footer id="footer" class="text-center">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">

					<div class="footer-logo wow fadeInDown">
						<img src="img/logo.png" alt="logo">
					</div>

					<div class="footer-social wow fadeInUp">
						<h3>We are not very social</h3>
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
