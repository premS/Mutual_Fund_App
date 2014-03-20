<jsp:include page="template-top.jsp" />
<script src="http://yui.yahooapis.com/3.14.1/build/yui/yui-min.js"></script>
<p style="font-size:medium">
	Research Fund:
</p>
<jsp:include page="error-list.jsp" />
<form method="post" action="deposit1.do" >
		<table>
			<tr>
				<td>User Name: </td>
				<td colspan="2"><div id="demo" class="yui3-skin-sam"><input type="text" id="uname" name="uname"/></div>
				<script>
YUI().use('autocomplete', 'autocomplete-filters', 'autocomplete-highlighters', function (Y) {
  var states = [
<%=request.getAttribute("user_name")%>
  ];

  Y.one('#uname').plug(Y.Plugin.AutoComplete, {
    resultFilters    : 'phraseMatch',
    resultHighlighter: 'phraseMatch',
    source           : states
  });
});
</script></td>
			</tr>
			<tr><td>Maximum Deposit Limit:</td>
			<td>$100,000,000.00</td></tr>
			
			<tr>
			<tr><td>Deposit Amount:</td>
			<td><input type="text" id="amount" name="amount"></td></tr>
			
			<tr>
				<td colspan="3" align="center">
					<input type="submit" name="button" value="Deposit"/>
				</td>
			</tr>
		</table>
	</form>
<jsp:include page="template-bottom.jsp" />
