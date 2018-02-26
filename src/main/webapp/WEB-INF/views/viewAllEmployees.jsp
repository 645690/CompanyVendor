<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<tags:stylesheet />
<title>All Employees</title>
</head>
<body>
  <tags:employeeNav />
  <div class='container'>
    <div class='table-responsive'>
      <table class='table'>
        <thead>
          <tr>
            <th>Reg No</th>
            <th>Name</th>
            <th>Age</th>
			<th>Contact</th>
			<th>Department</th>
			<th>Role</th>
          </tr>
        </thead>
		
		<tbody>
				<c:forEach items="${employeeList}" var="employee">
				<tr>
					<td><c:out value="${employee.regNo}" /></td>
					<td><c:out value="${employee.name}" /></td>
					<td><c:out value="${employee.age}" /></td>
					<td><c:out value="${employee.contact}" /></td>
					<td><c:out value="${employee.department.name}" /></td>
					<td><c:out value="${employee.account.accountRole.name}" /></td>
				</tr>
				</c:forEach>
			</tbody>
	  </table>
	</div>
  </div>
</body>
</html>