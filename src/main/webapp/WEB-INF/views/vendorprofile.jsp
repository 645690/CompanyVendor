<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
    <div class='panel panel-primary'>
      <div class='panel-heading'>Vendor Profile</div>
      <div class='panel-body'>
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
            <h4>Contact:</h4>
          </div>
          <div class='col-md-4'>
            <h4>${vendor.contact}</h4>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>