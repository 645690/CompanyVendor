<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
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
  <tags:userNav />
  <div class='container'>
    <div class='panel panel-primary'>
      <div class='panel-heading'>Apply to be Company</div>
      <div class='panel-body'>
        <form method="post" action="/applyToBeCompany/submit"
          modelAttribute="company">
          <div class="form-group">
            <label for="name" class="control-label">Reg No</label>
            <form:input id="name" path="company.regNo" name="name"
              required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <div class="form-group">
            <label for="name" class="control-label">Company Name</label>
            <form:input id="name" path="company.name" name="name"
              required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <button type="submit" class="btn btn-success btn-block">Apply
            to be Company</button>
        </form>
      </div>
    </div>
  </div>
</body>
</html>