<jsp:include page="template-top.jsp" />
<jsp:include page="error-list.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script>
  $(function() {
    $( "#d1" ).datepicker({ dateFormat: 'yy-mm-dd' });
  });
  </script>
  <form method="post" action="admin.do">
<p>Choose Ended Trading Date: <input type="text" id="d1" name="d1"></p>
<p>Provide Closing price for the Funds:</p>
<table border="1">
<th>Fund Name</th>
<th>Today's Price</th>
<th>Tomorrow's Price</th>
<c:forEach var="row" items="${fundList}">
			<tr>
			<td>${row.fundName}</td>
			<td>${row.fundPrice}</td>

			<td><input type="text" name="${row.fundId}" id="${row.fundId}"></td>
			
		</tr>
		</c:forEach>
</table>
<input type="submit" value="Set Price"></form>
<jsp:include page="template-bottom.jsp" />