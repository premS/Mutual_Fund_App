<jsp:include page="template-top.jsp" />
	<style type="text/css" title="currentStyle">
			@import "media/css/demo_page.css";
			@import "media/css/demo_table.css";
		</style>
		<script type="text/javascript" language="javascript" src="media/js/jquery.js"></script>
		<script type="text/javascript" language="javascript" src="media/js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8">
			/* Data set - can contain whatever information you want */
			var aDataSet = <%=request.getAttribute("accountList")%>;
			var aDataSet1 = <%=request.getAttribute("portfolioList")%>;
			
			$(document).ready(function() {
				$('#dynamic').html( '<table cellpadding="0" style="width:50%" cellspacing="0" border="0" class="display" id="example"></table>' );
				$('#example').dataTable( {
					"aaData": aDataSet,
					"aoColumns": [
                        { "sTitle": "UserID", "sClass": "center" },
						{ "sTitle": "Name", "sClass": "center" },
					    { "sTitle": "Address", "sClass": "center" },
						{ "sTitle": "City", "sClass": "center" },
						{ "sTitle": "State", "sClass": "center" },
						{ "sTitle": "Zip", "sClass": "center" },
						{ "sTitle": "Available Cash", "sClass": "center" },
					]
				} );	
			} );
			$(document).ready(function() {
				$('#dynamic1').html( '<table cellpadding="0" style="width:50%" cellspacing="0" border="0" class="display" id="example1"></table>' );
				$('#example1').dataTable( {
					"aaData": aDataSet1,
					"aoColumns": [
                        { "sTitle": "FundID", "sClass": "center" },
						{ "sTitle": "Fund Name", "sClass": "center" },
					    { "sTitle": "Current Holding", "sClass": "center" },
						{ "sTitle": "Current Price", "sClass": "center" },
						
					]
				} );	
			} );
		</script>
	<div id="dt_example">
		
			<center><div class="full_width big">
Account Details<hr>
			</div>
			
			</center>
			<div id="container" style="width:50%">
			<div id="dynamic"></div></div>
			<div class="spacer"></div>
			
			
	
			</div><br>

<div id="dt_example">
		
			<center><div class="full_width big">
Portfolio Details<hr>
			</div>
			
			</center>
						<div id="container" style="width:50%">
			<div id="dynamic1"></div></div>
			<div class="spacer"></div>
			
			
	
			</div>


<jsp:include page="template-bottom.jsp" />