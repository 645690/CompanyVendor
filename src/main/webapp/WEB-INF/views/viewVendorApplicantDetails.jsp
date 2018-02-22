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
    <div class='panel panel-primary'>
      <div class='panel-heading'>Vendor Applicant Details</div>
      <div class='panel-body'>
        <div class='row'>
          <div class='col-md-2'>
            <h4>Reg No:</h4>
          </div>
          <div class='col-md-4'>
            <h4>${vendor.regNo}</h4>
          </div>
        </div>
        <div class='row'>
          <div class='col-md-2'>
            <h4>Name:</h4>
          </div>
          <div class='col-md-4'>
            <h4>${vendor.name}</h4>
          </div>
        </div>
        <div class='row'>
          <div class='col-md-2'>
            <h4>Email:</h4>
          </div>
          <div class='col-md-4'>
            <h4>${vendor.email}</h4>
          </div>
        </div>
        <div class='row'>
          <div class='col-md-2'>
            <a href="/systemadmin/acceptVendorApplicant?regNo=${vendor.regNo}">
              <button class='btn btn-primary'>Accept</button>
            </a>
          </div>
          <div class='col-md-2'>
            <a href="/systemadmin/rejectVendorApplicant?regNo=${vendor.regNo}">
              <button class='btn btn-primary'>Reject</button>
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>