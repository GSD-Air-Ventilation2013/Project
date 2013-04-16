<%@ page language="java" contentType="text/html" pageEncoding="ISO-8859-1" trimDirectiveWhitespaces="true"%>
<%@ page import="dk.itu.serverside.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Building Control</title>
<meta charset="utf-8" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/Views/css/style.css" type="text/css" />
</head>
<body id="index" class="home">
<header id="banner" class="body">
  <h1>Building Dashboard</h1>
  <nav>
    <ul>
    <li><a href="<%=request.getContextPath()%>/index.jsp">Main page</a></li>
   	  <li><a href="<%=request.getContextPath()%>/Views/about.jsp">About</a></li>
    </ul>
  </nav>
</header>
<!-- /#banner -->