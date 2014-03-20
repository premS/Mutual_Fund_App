<jsp:include page="template-top.jsp" />
	<style type="text/css" title="currentStyle">
			@import "media/css/demo_page.css";
			@import "media/css/demo_table.css";
		</style>
		<script type="text/javascript" language="javascript" src="media/js/jquery.js"></script>
		<script type="text/javascript" language="javascript" src="media/js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8">
			/* Data set - can contain whatever information you want */
			var aDataSet = <%=request.getAttribute("fpdList")%>;
			
			$(document).ready(function() {
				$('#dynamic').html( '<table cellpadding="0" style="width:50%" cellspacing="0" border="0" class="display" id="example"></table>' );
				$('#example').dataTable( {
					"aaData": aDataSet,
					"aoColumns": [
									{ "sTitle": "", "sClass": "center" },
					              { "sTitle": "Date", "sClass": "center" },
						{ "sTitle": "Price", "sClass": "center" },
						
					]
				} );	
			} );
		</script>
	<div id="dt_example">
		
			<center><div class="full_width big">
<%=request.getAttribute("fName")%>
			</div>
			
			<h1>Historic Trend</h1></center>

			<div id="dynamic"></div>
			<div class="spacer"></div>
			
			
	
				<center><h1>Graphical Trend</h1></center>
			</div><br>
<script type="text/javascript">
$(function() {
	
		// Create the chart
		$('#container').highcharts('StockChart', {
			

			rangeSelector : {
				selected : 1
			},

			title : {
				text : '<%=request.getAttribute("fName")%>'
			},
			
			series : [{
				name : '<%=request.getAttribute("fName")%>',
				data : <%=request.getAttribute("fpList")%>,
				marker : {
					enabled : true,
					radius : 3
				},
				shadow : true,
				tooltip : {
					valueDecimals : 2
				}
			}]
		});
	
});

		</script>
			
			<script src="js1/highstock.js"></script>
<script src="js1/modules/exporting.js"></script>

<div id="container" style="height: 500px; min-width: 500px"></div>
		


<jsp:include page="template-bottom.jsp" />