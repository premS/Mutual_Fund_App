<jsp:include page="template-top.jsp" />

<p style="font-size:medium">
	Please login below.
</p>


<jsp:include page="error-list.jsp" />

<p>
	<form method="post" action="login.do">
		<table>
			<tr>
				<td> Email Address: </td>
				<td><input type="text" name="userName" value="${form.userName}"/></td>
			</tr>
			<tr>
				<td> Password: </td>
				<td><input type="password" name="password" value=""/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="button" value="Login"/>
				</td>
			</tr>
		</table>
	</form>
</p>

<jsp:include page="template-bottom.jsp" />
