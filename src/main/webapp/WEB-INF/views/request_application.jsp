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
<title>Welcome</title>
</head>
<body>
	<tags:vendorNav />

	<div class="modal-body">
		<h4 align=center>${error}</h4>
		<div class="row">
			<div class="col-xs-6">
				<h3 align=center>Application to Company</h3>
				<div class="well">
					<form id="requestForm" method="post" action="r_application"
						modelAttribute="request">
						<div class="form-group">
							<label for="requestComp" class="control-label">Company</label>
							<!--  <input type="text" class="form-control" id="username" name="username" value="" required="" title="Please enter you username" placeholder="example@gmail.com"> -->
						<%-- 	<form:input path="request.company" name="miscType" id="miscType"
								cssClass="form-control" required="true" /> --%>
							<form:select path="request.name">
								<form:options items="${compList}" itemValue="regNo" itemLabel="name"/>
							</form:select>
							<span class="help-block"></span>
						</div>
						<div class="form-group">
							<label for="requestType" class="control-label">Type of Service</label>
							<!--  <input type="text" class="form-control" id="username" name="username" value="" required="" title="Please enter you username" placeholder="example@gmail.com"> -->
							<%-- <form:input path="request.appType" name="miscType" id="miscType"
								cssClass="form-control" required="true" /> --%>
							<form:select path="request.type">
								<form:options items="${categoryList}"  itemValue="categoryName" itemLabel="categoryName" />
							</form:select>
							<span class="help-block"></span>
						</div>
						<button type="submit" class="btn btn-success btn-block">Submit</button>

					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>