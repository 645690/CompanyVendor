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
      <div class='panel-heading'>Apply to be Vendor</div>
      <div class='panel-body'>
        <form method="post" action="/applyToBeVendor/submit"
          modelAttribute="vendor">
          <div class="form-group">
            <label for="name" class="control-label">Reg No</label>
            <form:input id="name" path="vendor.regNo" name="name"
              required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <div class="form-group">
            <label for="name" class="control-label">Vendor Name</label>
            <form:input id="name" path="vendor.name" name="name"
              required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <div class="form-group">
            <label for="name" class="control-label">Vendor
              Contact</label>
            <form:input id="name" path="vendor.contact" name="name"
              required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <div class="form-group">
            <label for="name" class="control-label">Vendor Email</label>
            <form:input id="name" path="vendor.email" name="name"
              required="true" cssClass="form-control" />
            <span class="help-block"></span>
          </div>
          <div class="form-group">
            <label for="name" class="control-label">Notification
              Prefered Type</label>
            <form:select path="vendor.npt" cssClass="form-control">
              <form:options items="${nptList}" itemValue="name"
                itemLabel="name" />
            </form:select>
            <span class="help-block"></span>
          </div>
          <button type="submit" class="btn btn-success btn-block">Apply
            to be Vendor</button>
        </form>
      </div>
    </div>
  </div>
</body>
</html>