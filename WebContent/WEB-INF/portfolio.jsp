
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
<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
	<thead>
		<tr>
			<th class="html">ID</th>
			<th class="html">Fund Name</th>
			<th class="html">Current Holding</th>
			<th class="html">Buying Price</th>
			<th class="html">Total Value</th>
			<th class="html">Current Price</th>
			<th class="html">Sell</th>
		</tr>
	</thead>
	
	<tbody>
	
	<c:forEach var="row" items="${portfolio}">
			<tr>
			<td>${row.fundId}</td>
			<td>${row.fundName}</td>
			<td>${row.currentHolding}</td>
			<td>${row.buyingPrice}</td>
			<td>20000</td>
			<td>150</td>
			<td><a href="sellFund.do?id=${row.fundId}">sell</a></td>
		</c:forEach>
		
			
		</tr>
		
	

</tbody>

</table>
			</div>
			<div class="spacer"></div>
			
		
			
			
		</div>
	</div>
<jsp:include page="template-bottom.jsp" />