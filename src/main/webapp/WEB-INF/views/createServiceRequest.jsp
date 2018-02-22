<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<tags:stylesheet />
<title>Create Service Request</title>
</head>
<body>
  <tags:employeeNav />
  <div class='container'>
    <div class='panel panel-primary'>
      <div class='panel-heading'>Create Service Request</div>
      <div class='panel-body'>
        <form method="post" action="createNewServiceRequest"
          modelAttribute="serviceRequest">
          <div class="form-group">
            <label for="regNo" class="control-label">Register No</label>
            <form:input id="regNo" path="serviceRequest.regNo"
              name="regNo" required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <div class="form-group">
            <label for="name" class="control-label">Name</label>
            <form:input id="name" path="serviceRequest.name" name="name"
              required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <div class="form-group">
            <label for="name" class="control-label">Department</label>
            <form:input id="name" path="serviceRequest.department.name"
              name="name" required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <div class="form-group">
            <label for="name" class="control-label">Category</label>
            <form:input id="name" path="serviceRequest.category.name"
              name="name" required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <button type="submit" class="btn btn-success btn-block">Create
            Service Request</button>
        </form>
      </div>
    </div>
  </div>
</body>
</html>