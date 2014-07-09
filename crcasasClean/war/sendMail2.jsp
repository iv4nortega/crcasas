<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<%@ page import="javax.mail.Message" %>
<%@ page import="javax.mail.MessagingException" %>
<%@ page import="javax.mail.Session" %>
<%@ page import="javax.mail.Transport" %>
<%@ page import="javax.mail.internet.AddressException" %>
<%@ page import="javax.mail.internet.InternetAddress" %>
<%@ page import="javax.mail.internet.MimeMessage" %>

<%@ page import="java.util.Properties" %>
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="cr.novatec.crcasas.server.rpc.DAO" %>
<%@ page import="cr.novatec.crcasas.server.rpc.DataBaseServicesImpl" %>
<%@ page import="cr.novatec.crcasas.client.database.Ad" %>

<html>
<head>
<title>sendMail2</title>
<%



request.setCharacterEncoding("UTF-8");




String fromName = request.getParameter("fromName");
String fromEmail = request.getParameter("fromEmail");
String fromPhone = request.getParameter("fromPhone");

String subject = request.getParameter("subject");

if (subject.equals("na")) subject = "Consulta";
if (subject.equals("consulta")) subject = "Consulta sobre la propiedad";
if (subject.equals("llamar")) subject = "Por favor llamar";
if (subject.equals("cita")) subject = "Quiero ir a ver la propiedad";
if (subject.equals("comprar")) subject = "Estoy interesado en comprar";

String message = request.getParameter("message");

String adIdst;
Long adId;


if (request.getParameter("adId") != null) {
	
	adIdst = request.getParameter("adId");
	adId = Long.parseLong(adIdst);

	if (adId != null ) {
		
		DAO dao = new DAO();
		DataBaseServicesImpl db = new DataBaseServicesImpl();
		
		Ad ad = db.getAdBySourceKey(adIdst);
		
		if (ad!= null) {
			
			try {
				
				Properties props = new Properties();
				Session session2 = Session.getDefaultInstance(props, null);
				
				String toEmail = ad.getEmail();
				String toName = ad.getFirstName();
				
			    String msgBody = "Estimado(a) " + toName + ",<br><br>" + 
				         "Una persona solicita informacion con relacion a su anuncio No. "+ adIdst + ".<br><br>"+
				         "Nombre: "+ fromName + "<br><br>"+
				         "Telefono: "+ fromPhone + "<br><br>"+
				         "Correo: <span>"+ fromEmail + "</span><br><br>"+
				         "Asunto: "+ subject + "<br><br>"+
				         "Mensaje: <br>"+ message + "<br><br>"+
				         
				         "Muchas gracias por preferirnos,<br><br>"+
				         "Atentamente,<br><br>"+
				         "crCasas.com";

				
			    Message msg = new MimeMessage(session2);
			    msg.setFrom(new InternetAddress("ventas@crCasas.com", "crCasas"));
			    msg.addRecipient(Message.RecipientType.TO,
			                     new InternetAddress(toEmail, toName));
			    
			    msg.addRecipient(Message.RecipientType.BCC,
			            new InternetAddress("admin@crCasas.com", "admin"));

			    
			    msg.setSubject("Solicitud de informacion sobre su anuncio en crCasas");
			    msg.setContent(msgBody, "text/html");
			    Transport.send(msg);   
			    
			    

			} catch (AddressException e) {
			    // ...
			} catch (MessagingException e) {
			    // ...
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}			
			
	}
}	










%>
<script type="text/javascript">
function load()
{
alert("Su mensaje a sido enviado. ");
}
</script>

</head>

<body onload="load()">
</body>
</html>