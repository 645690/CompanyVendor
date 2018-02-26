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
<title>Create Employee</title>
</head>
<body>
  <tags:employeeNav />
  <div class='container'>
    <div class='panel panel-primary'>
      <div class='panel-heading'>Create Employee</div>
      <div class='panel-body'>
        <form method="post" action="createNewEmployee"
          modelAttribute="employee" modelAttribute="permission" modelAttribute="departmentList">
          <div class="form-group">
            <label for="regNo" class="control-label">Register No</label>
            <form:input id="regNo" path="employee.regNo"
              name="regNo" required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <div class="form-group">
            <label for="name" class="control-label">Name</label>
            <form:input id="name" path="employee.name" name="name"
              required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
           <div class="form-group">
            <label for="age" class="control-label">Age</label>
            <form:input id="age" path="employee.age"
              name="age" required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <div class="form-group">
            <label for="contact" class="control-label">Contact</label>
            <form:input id="contact" path="employee.contact"
              name="contact" required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
 
		  <div class="form-group">
            <label for="department" class="control-label">Department</label>
            <form:select path="employee.department.name" cssClass="form-control">
              <form:options items="${departmentList}" itemValue="name" itemLabel="name" />
            </form:select>
            <span class="help-block"></span>
          </div>
          <%-- <div class="form-group">
            <label for="department" class="control-label">Department</label>
            <form:input id="name" path="employee.department.name"
              name="department" required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div> --%>
         <div class="form-group">
            <label for="accountUsername" class="control-label">Account Username</label>
            <form:input id="accountUsername" path="employee.account.username"
              name="accountUsername" required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <div class="form-group">
            <label for="accountPassword" class="control-label">Account Password</label>
            <form:input id="accountPassword" path="employee.account.password"
              name="accountPassword" required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <%-- <div class="form-group">
            <label for="accountRole" class="control-label">Account Role</label>
            <form:input id="accountRole" path="employee.account.accountRole"
              name="accountRole" required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div> --%>
          <%-- <div class="form-group">
            <label for="permission" class="control-label">Permission</label>
            <form:input id="permission" path="permission.name"
              name="permission" cssClass="form-control" />
            <span class="help-block"></span>
          </div> --%>
          <h3>${message}</h3>
          <button type="submit" class="btn btn-success btn-block">Create
           Employee</button>
        </form>
      </div>
    </div>
  </div>
</body>
</html>