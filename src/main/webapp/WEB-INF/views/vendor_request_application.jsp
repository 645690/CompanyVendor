<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<tags:stylesheet />
<title>Welcome</title>
</head>
<body>
	<tags:vendorNav />

	<div class='container'>
	<div class='table-responsive'>
					<h4 align=center>${message}</h4>
					<table class='table'>
					<tr>
					<th>S/N</th>
					<th>Application No.</th>
					<th>Vendor Name</th>
					<th>Category Type</th>
					<th>Submitted Date</th>
					<th>Status</th>
					</tr>
					<c:forEach var="request" items="${requestList}">
					<tr>
					<td>${request.id}</td>
					<td>${request.applicationNo}</td>
					<td>${request.vendor.name}</td>
					<td>${request.appType.categoryName}</td>
					<td>${request.createdDate}</td>
					<td>${request.appStatus.statusName}</td>
					</tr>
					</c:forEach>
					</table>
					</div>
									</div>
</body>
</html>