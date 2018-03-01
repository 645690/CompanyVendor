<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"  
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Login to Report Server</title>
<tags:stylesheet />
</head>
<body>
  <tags:loginNav />
  <div id="login-overlay" class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="myModalLabel">Login to Company
          Manager</h4>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col">
            <h3 align=center>OTP</h3>
            <div class="well">
              <form id="registerForm" method="post" action="otpProcess"
                modelAttribute="token">
                <div class="form-group">
                  <label for="reg-otp" class="control-label">OTP</label>
                  <!--  <input type="text" class="form-control" id="username" name="username" value="" required="" title="Please enter you username" placeholder="example@gmail.com"> -->
                  <form:input path="token" name="token" id="token"
                    cssClass="form-control" required="true" />
                  <span class="help-block"></span>
                </div>
                <h3 align=center>${message}</h3>
                <button type="submit" class="btn btn-success btn-block">Submit</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
