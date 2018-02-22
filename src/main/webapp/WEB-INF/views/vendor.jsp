<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<tags:stylesheet />
<title>Vendor</title>
</head>
<body>
  <tags:vendorNav />
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
                href="submitServiceRequestApplication?serviceRequestRegNo=${serviceRequest.regNo}"><button
                    class='btn btn-primary'>Submit Application</button></a></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</body>
</html>