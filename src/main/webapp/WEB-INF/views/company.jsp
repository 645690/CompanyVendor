<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
  <tags:employeeNav />
  <div class='container'>
    <div class='table-responsive'>
      <table class='table'>
        <thead>
          <tr>
            <th>Reg No</th>
            <th>Name</th>
            <th>Category</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="serviceRequest" items="${serviceRequestList}">
            <tr>
              <td>${serviceRequest.regNo}</td>
              <td>${serviceRequest.name}</td>
              <td>${serviceRequest.category.name}</td>
              <td>${serviceRequest.status.name}</td>
              <td style="width: 10%"><a
                href="viewServiceRequestApplications?serviceRequestRegNo=${serviceRequest.regNo}"><button
                    class='btn btn-primary'>View applications</button></a></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</body>
</html>