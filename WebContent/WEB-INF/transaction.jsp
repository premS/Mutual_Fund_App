
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
			<th class="html">Transaction Date</th>
			<th class="html">Fund Name</th>
			<th class="html">Operation</th>
			<th class="html">State</th>
			
			<th class="html">Number of shares</th>
			<th class="html">Share Price</th>
			<th class="html">Total Amount</th>
		</tr>
	</thead>
	
	<tbody>
	
	<c:forEach var="row" items="${transaction}">
			<tr>
			<td>${row.date}</td>
			<td>${row.fundName}</td>
			<td>${row.operation}</td>
			<td>${row.state}</td>
			<td>${row.numberOfShares/1000}</td>
			<td>${row.sharePrice/100}</td>
			<td>${row.amount/100}</td>
	</c:forEach>
		
			
		</tr>
		
	

</tbody>

</table>
			</div>
			<div class="spacer"></div>
			
		
			
			
		</div>
	</div>
<jsp:include page="template-bottom.jsp" />