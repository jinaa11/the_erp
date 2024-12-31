<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="x" 	uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
<table border=1  width=50%>
<tr>
	<th>부모계정</th>
	<th>계정유형</th>
	<th>잔액</th>
</tr>
<c:forEach var="vo" items="${KEY_STATE_FLIST}">
	<tr>
		<td>${vo.parent_type}</td>
		<td>${vo.account_type}</td>
		<td>${vo.diff}</td>
	</tr>	
</c:forEach>
</table>

</table>
<br><br>





<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script>
$( document ).ready(function() {
	//$("#btn").click( function() {  
	//    	$("#input").val();
	//});
});
</script>
</body>
</html>