<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  	<link
		href="${pageContext.request.contextPath}/resources/table.css"
		rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Welcome</title>
</head>
<body>
	  <tags:employeeNav />
	
	<div class="modal-body">
					<h4 align=center>${message}</h4>
					<table align=center>
					<tr>
					<th>S/N</th>
					<th>Application No.</th>
					<th>Vendor Name</th>
					<th>Category Type</th>
					<th>Submitted Date</th>
					<th>Status</th>
					<th>Approve</th>
					<th>Reject</th>
					</tr>
					<c:forEach var="request" items="${requestList}">
					<tr>
					<td>${request.id}</td>
					<td>${request.applicationNo}</td>
					<td>${request.vendor.name}</td>
					<td>${request.appType.categoryName}</td>
					<td>${request.createdDate}</td>
					<td>${request.appStatus.statusName}</td>
					<td><button type="button" name="back" 
							onclick="location.href='<c:url value='/request_application?applicationNo=${request.applicationNo}&status=approved'/>'">Approve</button></td>
					<!-- delete with confirmation-->
					<td><button type="button" name="back"
							onclick="location.href='<c:url value='/request_application?applicationNo=${request.applicationNo}&status=rejected'/>'">Reject</button></td>
					<!-- normal delete button 
					<td><a href="<c:url value='/deleteContact/${con.contactId}' />" >Delete</a></td>-->
					</tr>
					</c:forEach>
					</table>
									</div>
	
</body>
</html>