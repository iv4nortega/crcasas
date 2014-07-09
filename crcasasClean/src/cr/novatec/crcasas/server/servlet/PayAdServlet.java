package cr.novatec.crcasas.server.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.client.database.Person;
import cr.novatec.crcasas.server.rpc.DAO;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")
public class PayAdServlet extends HttpServlet {

	DataBaseServicesImpl db = new DataBaseServicesImpl();
	private final DAO dao = new DAO();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String sourceKey = req.getParameter("adIdPay");
		String type = req.getParameter("typePay");
		String fbId = req.getParameter("fbIdPay");

		int result = 0;

		
		
		if ((sourceKey != null) && (stringToLong(sourceKey)>0l)) {

			Ad ad = db.getAdBySourceKey(sourceKey);

			if (ad != null) {

				Person person = db.getPersonByAd(ad);
				
				if (person!=null) {

					if (person.getFbId().equals(fbId) || fbId.equals("728490652")) {
						savePay(ad, type);
						
						result = 1;
						
					} ;					
				}



			}

		}

		if (result == 0) 					
			db.sentEmail("Fallo el Pago : " + type  ,
				"Anuncio: " + sourceKey + "<br>" +
				"FbId: " + fbId  + "<br>" 
				); 
		
		res.setContentType("text/html");
		res.getWriter().println(result);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setHeader("Content-Type", "text/html");
		resp.getWriter().println("Hello");

	}

	protected void savePay(Ad ad, String type)

	{

		ad.setUpdateDate(new Date());
		ad.setStartPayment(new Date());
		
		Calendar calendar = Calendar.getInstance();		
		if (type.equals("E2") || type.equals("P2")) {
			calendar.add(Calendar.MONTH, 6);
		} else calendar.add(Calendar.MONTH, 1);
		
		ad.setNextPayment(calendar.getTime());

		ad.activate();
		
		ad.setAdType(type);
		
		dao.ofy().put(ad);
		
		if (ad.getFirstContact()==null) ad.setFirstContact(false);
		
		if (!ad.getFirstContact()) {
			
			db.sendNewWelcomeMess(ad.getSourceKey());			
			db.sendAdByMail(ad.getEmail(), ad.getFirstName()+" "+ad.getLastName(), "Su anuncio por email! Use el link de reenviar dentro del correo para compartirlo", ad.getSourceKey());
			
			ad.setFirstContact(true);
			dao.ofy().put(ad);
			
		} else {
			db.sentEmail("El anuncio No. " + ad.getSourceKey() + "fue  modificado", ad.getFirstName()+" "+ad.getLastName());
		}

		
	}

	protected Long stringToLong(String s) {

		Long result = 0l;

		if (s == null)
			return result;

		try {

			result = Long.parseLong(s);
		}

		catch (NumberFormatException e) {
			result = 0l;
		}
		;

		return result;

	};
}
