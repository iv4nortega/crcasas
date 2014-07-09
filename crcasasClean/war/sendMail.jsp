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

<html>
<head>
<title>sendMail </title>
<%

request.setCharacterEncoding("UTF-8");


String to = request.getParameter("to");
String from = request.getParameter("from");
String subject = request.getParameter("subject");
String phone = request.getParameter("phone");
String name = request.getParameter("name");
String adId = request.getParameter("adId");

Properties props = new Properties();
Session session2 = Session.getDefaultInstance(props, null);

try {
	
    String msgBody = "Hola,<br><br>" + 
	         "Una persona solicita informacion con relacion a su anuncio No. "+ adId + ".<br><br>"+
	         "Nombre:"+ name + "<br><br>"+
	         "Telefono:"+ phone + "<br><br>"+
	         "Correo:"+ from + "<br><br>"+
	         "Mensaje:"+ subject + "<br><br>"+
	         
	         "Muchas gracias por preferirnos,<br><br>"+
	         "Atentamente,<br><br>"+
	         "crCasas.com";

	
    Message msg = new MimeMessage(session2);
    msg.setFrom(new InternetAddress("ventas@crCasas.com", name));
    msg.addRecipient(Message.RecipientType.TO,
                     new InternetAddress(to, name));
    
    msg.addRecipient(Message.RecipientType.BCC,
            new InternetAddress("admin@crCasas.com", "admin"));

    
    msg.setSubject("Un Cliente potencial solicita informacion sobre su anuncio en crCasas");
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

%>
<script type="text/javascript">
function load()
{
alert("Su mensaje a sido enviado. <%=subject%>.");
}
</script>

</head>

<body onload="load()">
</body>
</html>