<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<tags:stylesheet />
<title>System Admin</title>
</head>
<body>
  <tags:sysadminNav />
  <div class='container'>
    <div class='table-responsive'>
      <table class='table'>
        <thead>
         <tr>
            <th>Reg No</th>
            <th>Name</th>
            <th>Email</th>
            <th>Contact</th>
            <th>Status</th>
            <th>TurnOver</th>
            <th>Vendor Document</th>
            <th colspan="2">Action</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="vendor" items="${vendorList}">
            <tr>
               <td>${vendor.regNo}</td>
              <td>${vendor.name}</td>
              <td>${vendor.email}</td>
              <td>${vendor.contact}</td>
              <td>${vendor.status}</td>
              <td>${vendor.turnover}</td>
              <td><a href="${vendor.docFileUrl}" target="_blank">${vendor.docFileUrl}</a></td>
              <td style="width: 10%"><a
                href="viewVendorApplicantDetails?regNo=${vendor.regNo}"><button
                    class='btn btn-primary'>Details</button></a></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</body>
</html>