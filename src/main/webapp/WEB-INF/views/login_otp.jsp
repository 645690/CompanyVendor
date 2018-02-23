<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"  
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
<title>Login to Report Server</title>



<link
	href="${pageContext.request.contextPath}/resources/bootstrap.min.css"
	rel="stylesheet"/>
	<link
		href="${pageContext.request.contextPath}/resources/twenty20.min.css"
		rel="stylesheet"/>
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
			integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
			crossorigin="anonymous"></script>

		<link
			href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
			rel="stylesheet"></link>
</head>

<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
	<body>
		<div id="login-overlay" class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">Login to
						MyCompManager.com</h4>
				</div>
				<h4 align=center>${message}</h4>
				
				<div class="modal-body">

					<div class="row">

						<div class="col-xs-6">
							<h3 align=center>OTP</h3>
							<div class="well">
								<form id="registerForm" method="post" action="otpProcess"
									modelAttribute="token">
									<div class="form-group">
										<label for="reg-otp" class="control-label">OTP</label>
										<!--  <input type="text" class="form-control" id="username" name="username" value="" required="" title="Please enter you username" placeholder="example@gmail.com"> -->
										<form:input path="token" name="token"
											id="token" cssClass="form-control" required="true" />
										<span class="help-block"></span>
									</div>
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
