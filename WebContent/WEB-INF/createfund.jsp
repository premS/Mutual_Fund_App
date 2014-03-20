<jsp:include page="template-top.jsp" />
<p style="font-size:medium">
	Research Fund:
</p>
<jsp:include page="error-list.jsp" />
<form method="post" action="createfund1.do" >
		<table>
			<tr>
				<td>Fund Name: </td>
				<td colspan="2"><input type="text" id="fname" name="fname"/></td>
			</tr>
			<tr><td>Fund Tickr:</td>
			<td><input type="text" id="ftick" name="ftick"></td></tr>
			
			<tr>
				<td colspan="3" align="center">
					<input type="submit" name="button" value="Create"/>
				</td>
			</tr>
		</table>
	</form>
<jsp:include page="template-bottom.jsp" />
