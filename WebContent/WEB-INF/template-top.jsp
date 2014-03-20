<html lang="en" class="no-js">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>Blueprint: Tooltip Menu</title>
		<meta name="description" content="Blueprint: Tooltip Menu" />
		<meta name="keywords" content="Tooltip Menu, navigation, tooltip, menu, css, web development, template" />
		<meta name="author" content="Codrops" />
		<link rel="shortcut icon" href="../favicon.ico">
		<link rel="stylesheet" type="text/css" href="css/default.css" />
		<link rel="stylesheet" type="text/css" href="css/component.css" />
		<link rel="stylesheet" type="text/css" href="css/lily.css">
		<script src="js/modernizr.custom.js"></script>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	
		
	</head>
	<body>
	<%@ page import="databeans.User" %>
		<div class="container">
			<header class="clearfix">
				<span>Carnegie <span class="bp-icon bp-icon-about" data-content="Carnegie Financial Services offers many mutual fund investment options, plus the tools to help you select funds to help meet your investment goals.."></span></span>
				<h1>Financial Services</h1>
				
			</header>	
			
			<ul id="cbp-tm-menu" class="cbp-tm-menu">
				<li>
					
						<%
    User user = (User) session.getAttribute("user");
	if (user != null) {
%>
<a href="#">My Account</a>
					<ul class="cbp-tm-submenu">
						<li> <a class="cbp-tm-icon-archive" href="change-pwd.do">Change Password</a> </li>
						<li> <a class="cbp-tm-icon-archive" href="view.do">View Account</a> </li>
						<li> <a class="cbp-tm-icon-archive" href="logout.do">LogOut</a> </li>
						<%
		}else{
   
%>
<a href="login.do">My Account</a>
					<ul class="cbp-tm-submenu">

				<%} %>		
					</ul>
				</li>
				<li>
					<a href="#">Transact</a>
					<ul class="cbp-tm-submenu">
				<% 	if (user != null) {
%>
						<li> <a class="cbp-tm-icon-archive" href="listFund.do">Buy Fund</a> </li>
						<li> <a class="cbp-tm-icon-archive" href="portfolio.do">Sell Fund</a> </li>
	                    <li> <a class="cbp-tm-icon-archive" href="requestcheck.do">Request Check</a> </li>
	<%
		}
		%>
					</ul>
				</li>
				<li>
					<a href="#">Trade History</a>
					<ul class="cbp-tm-submenu">
								<% 	if (user != null) {
%>
						<li> <a class="cbp-tm-icon-archive" href="transaction.do">Transaction History</a> </li>
	                   
	<%
		}
		%>
					</ul>
				</li>
				<li>
					<a href="#">Research Funds</a>
					<ul class="cbp-tm-submenu">
						<% 	if (user != null) {
%>
						<li><a href="manage.do" class="cbp-tm-icon-archive">Do your Research</a></li>
	<%
		}
		%>
						
					</ul>
				</li>
				<li>
					<a href="#">Contact Us</a>
					<ul class="cbp-tm-submenu">
						<li><a href="transactday.do" class="cbp-tm-icon-screen">Zen</a></li>
						<li><a href="deposit.do" class="cbp-tm-icon-screen">Deposit</a></li>
						<li><a href="createfund.do" class="cbp-tm-icon-screen">Create Fund</a></li>
					</ul>
				</li>
			</ul>
				
		