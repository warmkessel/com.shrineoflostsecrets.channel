<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="com.shrineoflostsecrets.channel.database.entity.*, com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.util.*"%>
<%
ShrineUser su = new ShrineUser();
String uri = request.getRequestURI();
// Assuming the code is always after the last slash
String auth = uri.substring(uri.lastIndexOf("/a/") + 3);

if (auth.length() > 0) {
    // Store the auth value in the session
    su.loadShrineOTP(auth);
    if(su.isValue()){
    	String opt = OTPGenerator.generateOTP();
    	su.setOtpPass(opt);
        request.getSession().setAttribute("auth", opt);
    	su.save();
    } 
} 
// Check if there is a stored auth value in the session
String sessionAuth = (String) request.getSession().getAttribute("auth");
if (sessionAuth != null && !su.isValue()) {
	// Use the auth from the session if there is no auth in the URL
	su.loadShrineOTP(sessionAuth);
	if(su.isValue()){
		String opt = OTPGenerator.generateOTP();
    	su.setOtpPass(opt);
        request.getSession().setAttribute("auth", opt);
    	su.save();
	}
}
%>

<!-- Google tag (gtag.js) -->
<script async="true"
    src="https://www.googletagmanager.com/gtag/js?id=G-N2VTBWYNCJ"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'G-N2VTBWYNCJ');
</script>
<h1><%= su.getTwitchUserName()%></h1>
<a href="./channel.jsp">Channel List</a>