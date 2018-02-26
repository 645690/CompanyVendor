<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<tags:stylesheet />
<title>Company Admin</title>
</head>
<body>
  <tags:employeeNav />
  <div class='container'>
    <div class='panel panel-primary'>
      <div class='panel-heading'>Create Employee</div>
      <div class='panel-body'>
        <form method="post" action="updateEmployee" modelAttribute="employee" modelAttribute="updateEmployee" modelAttribute="departmentList" modelAttribute="accountRoleList">
          <div class="form-group">
            <label for="regNo" class="control-label">Register No</label>
            <form:input id="regNo" path="updateEmployee.regNo" name="regNo" required="true" value="${employee.regNo}" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <div class="form-group">
            <label for="name" class="control-label">Name</label>
            <form:input id="name" path="updateEmployee.name" name="name" required="true" value="${employee.name}" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
           <div class="form-group">
            <label for="age" class="control-label">Age</label>
            <form:input id="age" path="updateEmployee.age" name="age" required="true" value="${employee.age}" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <div class="form-group">
            <label for="contact" class="control-label">Contact</label>
            <form:input id="contact" path="updateEmployee.contact" name="contact" required="true" value="${employee.contact}" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
		  <div class="form-group">
            <label for="department" class="control-label">Department</label>
            <form:select path="updateEmployee.department.name" cssClass="form-control">
<%-- 		  <option value="${employee.department.name}" selected disabled hidden>${employee.department.name}</option>	--%>
              <option selected="selected">${employee.department.name}</option>
			  <form:options items="${departmentList}" itemValue="name" itemLabel="name" />
            </form:select>
            <span class="help-block"></span>
          </div>
		  <div class="form-group">
            <label for="accountRole" class="control-label">Role</label>
            <form:select path="updateEmployee.account.accountRole.name" cssClass="form-control">
<%-- 		<option value="${employee.account.accountRole.name}" selected disabled hidden>${employee.account.accountRole.name}</option> --%>
              <option selected="selected">${employee.account.accountRole.name}</option>
			  <form:options items="${accountRoleList}" itemValue="name" itemLabel="name" />
            </form:select>
            <span class="help-block"></span>
          </div>
          <button type="submit" class="btn btn-success btn-block">Update Employee</button>
	  </form>
      </div>
    </div>
  </div>
</body>
</html>