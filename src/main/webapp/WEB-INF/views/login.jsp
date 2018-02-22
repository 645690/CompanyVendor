<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<tags:stylesheet />
<title>Login</title>
</head>
<body>
  <tags:loginNav />
  <div class='container'>
    <div id="login-overlay" class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title" id="myModalLabel">Login to
            Company Manager</h4>
        </div>
        <div class="modal-body">
          <div class="row">
            <div class="col">
              <div class="well">
                <form id="loginForm" method="post" action="loginProcess"
                  modelAttribute="login">
                  <div class="form-group">
                    <label for="username" class="control-label">Username</label>
                    <form:input path="login.username" name="username"
                      id="username" cssClass="form-control"
                      required="true" />
                    <span class="help-block"></span>
                  </div>
                  <div class="form-group">
                    <label for="password" class="control-label">Password</label>
                    <form:password path="login.password" name="password"
                      id="password" cssClass="form-control" />
                    <span class="help-block"></span>
                  </div>
                  <h3>${message}</h3>
                  <button type="submit"
                    class="btn btn-success btn-block">Login</button>
                  <a href="register" class="btn btn-info btn-block">Register
                    now!</a>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
