<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<tags:stylesheet />
<title>Error Detected</title>
</head>
<body>
  <div class='container'>
    <div class='panel panel-primary'>
      <div class='panel-heading'>An error has occurred</div>
      <div class='panel-body'>
        <div class='row'>
          <div class='col-md-4'>
            <h1>${message}</h1>
          </div>
        </div>
        <div class='row'>
          <div class='col-md-4'>
            <a href="logout">
              <button class='btn btn-primary'>Logout</button>
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>