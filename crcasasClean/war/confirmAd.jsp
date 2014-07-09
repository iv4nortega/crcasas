<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page import="cr.novatec.crcasas.server.rpc.DataBaseServicesImpl" %>
<%@ page import="cr.novatec.crcasas.server.rpc.DAO" %>
<%@ page import="cr.novatec.crcasas.client.database.Ad" %>

<html>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<head>
    

<title>crCasas</title>

</head>

  <body >

<%
	

	DAO dao = new DAO();
	
	DataBaseServicesImpl db = new DataBaseServicesImpl();
	Ad ad = null;
	
	String adIdst = null;
	Long adId = null;
	
	String keyst = null;
	Integer key = null;
	
	String confirm = null;
	
	Integer confirmKey = null;
	
	
	if (request.getParameter("adId") != null) {
		adIdst = request.getParameter("adId");
		adId = Long.parseLong(adIdst);
		
		ad = db.getAdById(adId);
		
		confirmKey = ad.getConfirmationKey();


	}
	
	if (request.getParameter("key") != null) {
		keyst = request.getParameter("key");
		key = Integer.parseInt(keyst);

	}
	
	if (request.getParameter("confirm") != null) {
		confirm = request.getParameter("confirm");
	}
	
	if (ad!=null) {
		if (key!=null) {
			if (confirmKey.equals(key)) {
				if (confirm!=null) {
					if (confirm.toLowerCase().equals("yes")) {
						%>
						<p>Su anuncio ha sido confirmado!</p>
						<p>Se ha enviado un correo a su cuenta con información para modificar el anuncio.</p>
						<p>Puede ver su nuevo anuncio presionando el siguiente boton</p>						
						<button type="button" onClick="window.location='1.novatec2014.appspot/displayAd.jsp?adId=<%=adId%>'">Ver anuncio</button>
						<% 
					} else {
						%>
						<p>Falto valor en el parametro de confirmacion</p>
						<% 
					
					}
				
				} else {
					%>
					<p>Falto parametro confirmar</p>
					<% 
						
				}
			} else {
				%>
				<p>La llave <%=key%> no es valida para confirmar este anuncio</p>
				<% 
				
			}

		} else {
			%>
			<p>Debe suministrar una llave para poder confirmar el anuncio</p>
			<% 
			
		}

	} else {
		%>
		<p>No se encontro el anuncio <%=adId%>.</p>
		<% 		
	}

	
%>





  
  

   
  </body>
</html>