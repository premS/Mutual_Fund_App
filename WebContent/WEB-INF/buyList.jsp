
<jsp:include page="template-top.jsp" />
	<style type="text/css" title="currentStyle">
			@import "media/css/demo_page.css";
			@import "media/css/demo_table.css";
		</style>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
			<th class="html">Fund</th>
			<th class="html">Price</th>
			<th class="html">buy</th>
		</tr>
	</thead>
	
	<tbody>
	
	<c:forEach var="row" items="${fundList}">
			<tr>
			<td>${row.fundName}</td>
			<td>${row.fundPrice}</td>
			<td><a href="buyFund.do?id=${row.fundId}">Buy</a></td>
			
		</tr>
		</c:forEach>
	

</tbody>

</table>
			</div>
			<div class="spacer"></div>
			
				
		</div>
		</div>
<jsp:include page="template-bottom.jsp" />