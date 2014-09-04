<%@page language="java" 
        contentType="text/html; charset=ISO-8859-1" 
        pageEncoding="ISO-8859-1"
        errorPage="/logout.jsp"%>
         
<%@page import="edu.ncsu.csc.itrust.enums.TransactionType"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>

<%@page import="edu.ncsu.csc.itrust.DBUtil"%>

<%@page import="jspStub.Stub_auth_forwardUser"%>

<%@include file="/global.jsp" %>
<%@include file="/authenticate.jsp" %>
<%Stub_auth_forwardUser saf = new Stub_auth_forwardUser();
  saf.enterForwardUser();
%>
<%
if(request.getUserPrincipal() != null) {
	
	
	long mid = Long.valueOf(request.getUserPrincipal().getName());
	
	if (request.isUserInRole("patient")) {
		response.sendRedirect("patient/home.jsp");
		saf.exitForwardUser();
		return;
	}
	else if (request.isUserInRole("uap")) {
		response.sendRedirect("uap/home.jsp");
		saf.exitForwardUser();
		return;	
	}
	else if (request.isUserInRole("hcp")) {
		response.sendRedirect("hcp/home.jsp");
		saf.exitForwardUser();
		return;	
	}
	else if (request.isUserInRole("er")) {
		response.sendRedirect("er/home.jsp");
		saf.exitForwardUser();
		return;	
	}
	else if (request.isUserInRole("pha")) {
		response.sendRedirect("pha/home.jsp");
		saf.exitForwardUser();
		return;
	}
	else if (request.isUserInRole("admin")) {
		response.sendRedirect("admin/home.jsp");
		saf.exitForwardUser();
		return;
	}
	else if (request.isUserInRole("tester")) {
		response.sendRedirect("tester/home.jsp"); //operationprofile
		saf.exitForwardUser();
		return;
	}
	else if (request.isUserInRole("lt")) {
		response.sendRedirect("lt/home.jsp");
		saf.exitForwardUser();
		return;
	}
	else if(!validSession) {
		session.invalidate();
		response.sendRedirect("/iTrust/");
		saf.exitForwardUser();
	}
	else if (mid == 0)
	{
		session.invalidate();
		saf.exitForwardUser();
	}
	else {
		response.sendRedirect("errors/noaccess.jsp");
		saf.exitForwardUser();
	}
}
%>
