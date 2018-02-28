<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		<!--     <div class='panel panel-primary'>
      <div class='panel-heading'>User Dashboard</div>
      <div class='panel-body'>
        <div class='row'>
          <div class='col-md-4'>
            <a>
              <button class='btn btn-primary'>Apply to be
                vendor</button>
            </a>
          </div>
        </div>
      </div>
    </div> -->

		<div class='table-responsive'>
			<table class='table'>
				<thead>
					<tr>
						<th>Reg No</th>
						<th>Name</th>
						<th>Status</th>
						<!--             <th>Action</th> -->
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty vendor}">
						<tr>
							<td>${vendor.regNo}</td>
							<td>${vendor.name}</td>
							<td>${vendor.status}</td>
							<%--               <td style="width: 10%"><a
                href="viewCompany?regNo=${company.regNo}"><button
                    class='btn btn-primary'>View Info</button></a></td> --%>
						</tr>
					</c:if>
					<c:if test="${not empty company}">
						<tr>
							<td>${company.regNo}</td>
							<td>${company.name}</td>
							<td>${company.status}</td>
							<%--               <td style="width: 10%"><a
                href="viewCompany?regNo=${company.regNo}"><button
                    class='btn btn-primary'>View Info</button></a></td> --%>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>