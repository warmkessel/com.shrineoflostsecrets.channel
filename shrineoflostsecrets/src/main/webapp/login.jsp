<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.HttpSession,com.shrineoflostsecrets.channel.database.entity.*,com.shrineoflostsecrets.channel.util.*,com.shrineoflostsecrets.channel.constants.*,com.shrineoflostsecrets.channel.database.datastore.*,com.google.cloud.datastore.*"%>
<%
ShrineUser su = new ShrineUser();
String uri = request.getRequestURI();
// Assuming the code is always after the last slash
String auth = uri.substring(uri.lastIndexOf("/a/") + 3);

if (auth.length() > 0) {
    // Store the auth value in the session
    su.loadShrineOTP(auth);
    if(su.isValid()){
        session.setAttribute("auth", su.getKeyString());

    } 
}
%>
<%
    response.sendRedirect(request.getContextPath() + "/");
%>