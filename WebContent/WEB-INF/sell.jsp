<jsp:include page="template-top.jsp" />
	<style type="text/css" title="currentStyle">
			@import "media/css/demo_page.css";
			@import "media/css/demo_table.css";
		</style>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><html>
					<script type="text/javascript" language="javascript" src="media/js/jquery.js"></script>
		<script type="text/javascript" language="javascript" src="media/js/jquery.dataTables.js"></script>
		
		<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				$('#example').dataTable();
			} );
		</script>
	<div id="dt_example">
	
		<div id="container">
			
			<div id="demo">


		<form action="#" method="POST">	
<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
	
	<tbody>
		<tr>
			<td>Fund</td>
			<td>${fund.fundName}</td>	
		</tr>
		<tr>
			<td>Price</td>
			<td>${fund.fundPrice}</td>	
		</tr>
		
		<tr>
			<td>Your Current Holding:</td>
			<td>${portfolio.currentHolding}</td>	
		</tr>
		<tr>
			<td>Your Buying Price:</td>
			<td>${portfolio.buyingPrice}</td>	
		</tr>
		
			<tr>
			<td>Allocate Shares:</td>
			<td><input id="shares" type="text" size="10"
					name="shares" /></td>	
		</tr>
			<tr>
			
			<td></td>	
			<td><input type="submit" name="action" value="Sell"></td>	
		</tr>
</tbody>
</table>
</form>
			</div>
			<div class="spacer"></div>
						
			
		</div>
	</div>
<jsp:include page="template-bottom.jsp" />