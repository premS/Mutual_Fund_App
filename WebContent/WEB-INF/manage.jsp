<jsp:include page="template-top.jsp" />
<script src="http://yui.yahooapis.com/3.14.1/build/yui/yui-min.js"></script>
<p style="font-size:medium">
	Research Fund:
</p>

<jsp:include page="error-list.jsp" />

<p>
	<form method="post" action="upload.do" >
		<table>
			<tr>
				<td>Fund Name: </td>
				<td colspan="2"><div id="demo" class="yui3-skin-sam"><input type="text" id="url" name="url"/></div>
				<script>
YUI().use('autocomplete', 'autocomplete-filters', 'autocomplete-highlighters', function (Y) {
  var states = [
<%=request.getAttribute("fund_name")%>
  ];

  Y.one('#url').plug(Y.Plugin.AutoComplete, {
    resultFilters    : 'phraseMatch',
    resultHighlighter: 'phraseMatch',
    source           : states
  });
});
</script></td>
			</tr>
			
			<tr>
				<td colspan="3" align="center">
					<input type="submit" name="button" value="Research"/>
				</td>
			</tr>
		</table>
	</form>
</p>
<hr/>
<jsp:include page="template-bottom.jsp" />

