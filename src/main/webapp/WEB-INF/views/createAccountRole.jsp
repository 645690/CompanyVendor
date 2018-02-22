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
  <tags:sysadminNav />
  <div class='container'>
    <div class='panel panel-primary'>
      <div class='panel-heading'>Create Account Role</div>
      <div class='panel-body'>
        <form method="post" action="/systemadmin/createAccountRole/submit"
          modelAttribute="accountRole">
          <div class="form-group">
            <label for="name" class="control-label">Account Role Name</label>
            <form:input id="name" path="accountRole.name" name="name"
              required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <button type="submit" class="btn btn-success btn-block">Create
            Account Role</button>
        </form>
      </div>
    </div>
  </div>
</body>
</html>