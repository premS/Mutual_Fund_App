<jsp:include page="template-top.jsp" />

<p style="font-size:30px;margin:50px;" align='center'>
	Request Check
</p>

<jsp:include page="error-list.jsp" />

<p align='center'>
	<form method="post" action="requestcheck.do"  align='center'>
		<table class="inputtable"  align='center'>
			<tr>
				<td> I want to withdraw: </td>
				<td>
				$ <input type="text" name="amount" value="${form.amount}" class="inputtext"/>
				</td>
			</tr>

			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="button" value="Send Request" class="button" style="margin:20px;"/>
				</td>
			</tr>
		</table>	
	</form>
</p>

<jsp:include page="template-bottom.jsp" />