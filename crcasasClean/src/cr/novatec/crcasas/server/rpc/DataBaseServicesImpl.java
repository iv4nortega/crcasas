package cr.novatec.crcasas.server.rpc;



import it.sauronsoftware.base64.Base64;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;




import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.*;
import com.google.appengine.labs.repackaged.com.google.common.io.Resources;

import cr.novatec.crcasas.client.Parameters;
import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.client.database.AdCursor;
import cr.novatec.crcasas.client.database.AdHistory;
import cr.novatec.crcasas.client.database.AdWeb;
import cr.novatec.crcasas.client.database.Division1;
import cr.novatec.crcasas.client.database.Division2;
import cr.novatec.crcasas.client.database.Division3;
import cr.novatec.crcasas.client.database.Encuentra3;
import cr.novatec.crcasas.client.database.EncuentraDivision3;
import cr.novatec.crcasas.client.database.Errors;
import cr.novatec.crcasas.client.database.Global;
import cr.novatec.crcasas.client.database.ImageDataBase;
import cr.novatec.crcasas.client.database.Inmobi3;
import cr.novatec.crcasas.client.database.Jobs;
import cr.novatec.crcasas.client.database.Messages;
import cr.novatec.crcasas.client.database.SettingsDB;


import cr.novatec.crcasas.client.database.Nacion3;
import cr.novatec.crcasas.client.database.NacionDivision3;
import cr.novatec.crcasas.client.database.Person;
import cr.novatec.crcasas.server.resources.MyServerLibrary;

@SuppressWarnings("serial")


public class DataBaseServicesImpl   {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	

	private final DAO dao = new DAO();
	
	MyServerLibrary library = new MyServerLibrary();

	public String sanitizeString(String string) {
		String returnString = "";
		if (string == null) returnString = "EMPTY";
		else  if (string.trim().length() == 0) returnString = "EMPTY";
		else returnString = string.trim().toUpperCase().replace("�","A").replace("�","E").replace("�","I").replace("�","O").replace("�","U").replace("�","N");		
		return returnString;		
	}

	
	public void saveSettings(SettingsDB settings) throws IllegalArgumentException {
		dao.ofy().put(settings);
		assert settings.getId() != null; 
	}
	
	public void saveGlobal(Global global) throws IllegalArgumentException {
		dao.ofy().put(global);
		assert global.getId() != null; 
	}
	
	public void saveAd(Ad ad) throws IllegalArgumentException {
		dao.ofy().put(ad);
		assert ad.getId() != null; 
	}
	
	public void saveAdImageUrl(Long adId, String imageUrl, Boolean isPortrait) throws IllegalArgumentException {
		
		if (adId != null) {
			if (adId.compareTo(0l) != 0) {
				Ad ad = dao.ofy().get(Ad.class, adId);
				if (ad != null) {
					ad.setImageUrl(imageUrl);
					ad.setIsPortrait(isPortrait);
					dao.ofy().put(ad);
				}

			}

		};
		

	}
	
	
	public void saveJob(Jobs jobs) throws IllegalArgumentException {
		dao.ofy().put(jobs);
		assert jobs.getId() != null; 
	}
	
	public void savePerson(Person person) throws IllegalArgumentException {
		dao.ofy().put(person);
		assert person.getId() != null; 
	}
	
	public void saveError(Errors error) throws IllegalArgumentException {
		dao.ofy().put(error);
		assert error.getId() != null; 
	}
	
	public void saveDivision2(Division2 division2) throws IllegalArgumentException {
		dao.ofy().put(division2);
		assert division2.getId() != null; 
	}

	public void saveDivision1(Division1 division1) throws IllegalArgumentException {
		dao.ofy().put(division1);
		assert division1.getId() != null; 
	}
	
	public void saveDivision3(Division3 division3) throws IllegalArgumentException {
		dao.ofy().put(division3);
		assert division3.getId() != null; 
	}
	
	
	public boolean deleteAllDivision3(){
		dao.ofy().delete(dao.ofy().query(Division3.class).fetchKeys());
		return true;
	}
	
	public boolean deleteAllDivision2(){
		dao.ofy().delete(dao.ofy().query(Division2.class).fetchKeys());
		return true;
	}
	
	public boolean deleteAllPerson(){
		dao.ofy().delete(dao.ofy().query(Person.class).fetchKeys());
		return true;
	}
	
	public boolean deleteAllImageDB(){
		dao.ofy().delete(dao.ofy().query(ImageDataBase.class).fetchKeys());
		return true;
	}
	
	public boolean deleteAllGlobal(){
		dao.ofy().delete(dao.ofy().query(Global.class).fetchKeys());
		return true;
	}
	
	public boolean deleteAllDivision1(){
		dao.ofy().delete(dao.ofy().query(Division1.class).fetchKeys());
		return true;
	}

	public boolean deleteAllSettings(){
		dao.ofy().delete(dao.ofy().query(SettingsDB.class).fetchKeys());
		return true;
	}
	

	public boolean deleteAllAd(){
		dao.ofy().delete(dao.ofy().query(Ad.class).fetchKeys());
		return true;
	}

	public List<Ad> getAllAdsBySourceKey(String sourceKey, Integer source, String country)
			throws IllegalArgumentException {
		Query<Ad> q = dao.ofy().query(Ad.class).filter("countryId", country).filter("source", sourceKey).filter("sourceKey", source);
        Iterable<Ad> listingItbl = q.list();
        List<Ad> list = new ArrayList<Ad>();
        for (Ad l : listingItbl)
        {
         list.add(l); 
        }
        return list;
	}
	
	public Long getAdWebIdBySourceKey(String sourceKey, Integer source, String country) {
		if (sourceKey==null || country==null || source==null) return null;
		AdWeb ad = dao.ofy().query(AdWeb.class).filter("countryId", country).filter("source", source).filter("sourceKey", sourceKey).get();
		if (ad==null) return null;
		return ad.getId();
	}

	public Long findSourceKey(String sourceKey, String sourceUrl, Integer source, String country) {
		
		List<Ad> adList = getAllAdsBySourceKey(sourceKey, source, country);
		if (adList==null) return null;
		else {
			
			for (Ad a : adList) {
				if (a.getSourceUrl().equals(sourceUrl)) return a.getId();
			}
			
			return null;
		}
		
	}

	public void deleteAd(Long id) throws IllegalArgumentException {
		Ad ad =  dao.ofy().get(Ad.class, id);	
		
		if (ad!=null) {

			deleteImagesByAdId(ad.getId());
			dao.ofy().delete(ad);	

		}
		
		
		
	}



	public Ad updateAd(Ad from) {	
		
		Ad to = getAdById(from.getId());
		
		if (to!=null) {
			to.copyFromDS(from);

		}
		else {
			to = from;
			to.setConfirmationSent(false);	

		}
		
		to.setUpdateDate(new Date());

		dao.ofy().put(to);	

		if (to.getStatus().equals("P") && !to.getConfirmationSent()) {
			to.setConfirmationSent(true);	
			Random rand = new Random(); 			
			to.setConfirmationKey(rand.nextInt(10000));			
			to.setConfirmationSentDate(new Date());
			sentConfirmation(to.getEmail(), to.getId(), 
							 to.getFirstName(),  to.getConfirmationKey());
			
			dao.ofy().put(to);
		}

		return to;
	}
	
	public String createNewAd(String email, 
				String firstName, 
				String lastName, 				 
				String fbId) {
		
		Ad ad = new Ad();

		Person person = getFBUser(fbId);
			
		if (person!=null) {
			
			ad = getAdLastByFbId(fbId);
			
			if (ad==null) {
				ad = new Ad();
				
				ad.init(email, firstName, lastName, person.getId());
									
				ad.setSourceKey(nextAdId().toString());
											
			} else ad.updatePerson(email, firstName, lastName, person.getId());

			ad.setUpdateDate(new Date());
			
			saveAd(ad);

		}
			 											
		return ad.getSourceKey();		
		
	};
	
	public Ad newAdFromCr(Ad from) {	

		Ad to = from;
		
		to.setUpdateDate(new Date());
		dao.ofy().put(to);
		assert to.getId() != null;	
		
		to.setSourceKey(nextAdId().toString());
		to.setSourceUrl(Parameters.HOST_DOMAIN +"#!anuncio=" + to.getSourceKey());
		
		to.setShowAddress(true);
		to.setCountryId(Parameters.AD_COUNTRY_CRC);
		to.setSource(Parameters.AD_SOURCE_CRCASAS);
		
		to.setPubDate(new Date());
		to.setCreationDate(new Date());
		
		dao.ofy().put(to);				
		assert to.getId() != null;			

		return to;
	}
	
	
	public Ad updateAdFromCr(Ad from) {	
		
		Boolean newAd = true;
		
		Global global = getGlobal();
		
		Ad to = getAdById(from.getId());
		
		if (to!=null) {
			to.copyFromDS(from);
			newAd = false;
		}
		else {
			to = from;

		}
		
		to.setUpdateDate(new Date());
		to.activate();
		
		
		if (to.getLocalCurrency()) {
			to.setDolPrice(global.convertToInter(to.getLocalPrice()));
			to.setDolRentPrice(global.convertToInter(to.getLocalRentPrice()));
			
		} else {
			
			to.setLocalPrice(global.convertToLocal(to.getDolPrice()));
			to.setLocalRentPrice(global.convertToLocal(to.getDolRentPrice()));
		};

		if (newAd) 	{

			dao.ofy().put(to);
			assert to.getId() != null;	
			
			to.setSourceUrl("http://www.crcasas.com#!anuncio="+to.getSourceKey());
			//to.setSourceKey(to.getId().toString());
			to.setShowAddress(true);
			to.setCountryId(Parameters.AD_COUNTRY_CRC);
			to.setSource(Parameters.AD_SOURCE_CRCASAS);
			
			to.setPubDate(new Date());
			to.setCreationDate(new Date());

			//sentAdToCM(to.getId());
			
		};
		
		dao.ofy().put(to);				
		assert to.getId() != null;			
		if (newAd) {
			sentEmail("Un nuevo anuncio " + to.getAdTypeName() + " No. " + to.getSourceKey() ,"Publicado por "+ to.getFirstName()+" "+ to.getLastName() + "  " +  to.getEmail());
			sendNewWelcomeMess(to.getSourceKey());			
			sendAdByMail(to.getEmail(), to.getFirstName()+" "+to.getLastName(), "Su anuncio por email! Use link reenviar dentro del correo para compartirlo", to.getSourceKey());
			
		}


		return to;
	}
	

	
	public void sentConfirmation(String email, Long adId, String name,  Integer key) {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        String heads  = "http://1.novatec2014.appspot.com/confirmAd.jsp";
        String params = "?adId="+adId.toString()+"&key="+key.toString()+"&confirm=yes";

        String msgBody = "Hola "+  name + ",<br><br>" + 
        		         "Su anuncio en crCasas.com esta listo para ser publicado.<br>"+
        		         "Para confirmar su publicacion simplemente siga el vinculo siguiente:<br><br>"+
        		         
						  "<a href="+ heads + params + ">"+
						  heads+params+"</a><br><br>"+
        		         
        		         "Muchas gracias por preferirnos,<br><br>"+
        		         "Atentamente,<br><br>"+
        		         "crCasas.com";

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("ventas@crCasas.com", "crCasas.com"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(email, "Estimado Cliente"));
            msg.setSubject("Nuevo anuncio creado en crCasas.com");
            //msg.setText(msgBody);
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
	
	public void sentEmail(String subject, String msgBody ) {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
 
  

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("admin@crCasas.com", "crCasas.com"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("ventas@crcasas.com", "Ventas"));
            msg.setSubject(subject);
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
	
	public void sentPublish(String email, Long adId, String name,  Integer key) {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        String heads  = "http://www.crcasas.com";
        
        // 29 dic 2012 String params2 = "/displayAd.jsp?adId="+adId.toString();

        String params2 = "/anuncio/"+adId.toString();

        String msgBody = "Hola "+  name + ",<br><br>" + 
        		         "Su nuevo anuncio en crCasas.com ha sido publicado y lo puede ver en el v&iacute;nculo siguiente: <br><br>"+
        		
						  "<a href="+ heads + params2 + ">"+
						  heads+params2+"</a><br><br>"+

      		         	  "El n&uacute;mero de identificaci&oacute;n de su anuncio es " + adId.toString() + "<br><br>"+

      		         	  "Tambien puede ver su anuncio ingresando a crCasas en la opci&oacute;n Ver."  + "<br><br>"+

      		         	  "Le recomendamos compartir este v&iacute;nculo para que sus amigos vean su anuncio y este tenga una mayor exposici&oacute;n.<br><br>"+
        		
        		         "Si desea modificar, activar, pausar o eliminar su anuncio simplemente ingrese a crCasas y "+
          		         "y utilice la opci&oacute;n anunciar. <br><br>"+

     		         	  "Su anuncio vencer&aacute;  en 30 d&iacute;as y si desea continuar public&aacute;ndolo deber&aacute; activarlo.<br><br>"+
     		         	 
        		         "Muchas gracias por preferirnos,<br><br>"+
        		         "Atentamente,<br><br><br>"+
        		         "ventas@crCasas.com";

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("ventas@crCasas.com", "crCasas.com"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(email, "Estimado Cliente"));
            msg.setSubject("Su anuncio en crCasas.com fue publicado");
            //msg.setText(msgBody);
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

	public void sendMail(String to, String from, String name,  String phone, String subject) {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("ventas@crCasas.com", "crCasas.com"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(to, name));
            msg.setSubject(subject);
            msg.setContent(subject, "text/html");
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
	
	public String createRowContent(String label, String value) {
		//  <td valign="top" align="right" style="vertical-align: top; display: table-cell; border-collapse: collapse; border-bottom-style: solid; border-bottom-width: 1px; border-bottom-color: #dedede; font-family: 'Helvetica','Arial',sans-serif; font-size: 14px; color: #666; width: 50%; text-align: left; padding: 10px;">Oscar Valverde</td>&#13;
 
		String row = "<tr><td valign='top' align='left' style='vertical-align: top; display: table-cell; border-collapse: collapse; border-bottom-style: solid; border-bottom-width: 1px; border-bottom-color: #dedede; font-family: 'Helvetica','Arial',sans-serif; font-size: 14px; color: #666; width: 50%; text-align: left; padding: 10px;'>"+label+"&#13;</td>&#13;<td valign='top' align='right' style='vertical-align: top; display: table-cell; border-collapse: collapse; border-bottom-style: solid; border-bottom-width: 1px; border-bottom-color: #dedede; font-family: 'Helvetica','Arial',sans-serif; font-size: 14px; color: #666; width: 50%; text-align: left; padding: 10px;'>"+value+"</td></tr>";
		
		return row;
	};
	
	public String createRowAltContent(String label, String value) {
		String row = "<tr><td style='text-align: right !important; width:40%; font-weight: bold !important; color: #00496B; border-left-width: 1px; border-left-style: none; border-left-color: #E1EEF4; font-size: 12px; border-bottom-style: none; border-collapse: collapse; background-color: #E1EEF4; padding: 3px 10px;' align='right !important' bgcolor='#E1EEF4'>"+label+"</td><td style='text-align: left !important; color: #00496B; border-left-width: 1px; border-left-style: solid; border-left-color: #E1EEF4; font-size: 12px; font-weight: normal; border-bottom-style: none; border-collapse: collapse; background-color: #E1EEF4; padding: 3px 10px;' align='left !important' bgcolor='#E1EEF4'>"+value+"</td></tr>";
		
		return row;
	};
	
	public String createContactContent(Ad ad) {
		String row = " ";
		
		if (ad.getShowName() ) row = createRowContent("Vendedor:", ad.getFirstName()+ " " + ad.getLastName());;
		if (ad.getShowPhone() ) row = row + createRowContent("Tel&eacute;fono:", ad.getPhone());
		if (ad.getShowEmail() ) row = row + createRowContent("Correo:", ad.getEmail());
		
		return row;
	};

	
	public String createPropertyContent(Ad ad) {
		String row = createRowContent("Modalidad:", ad.getSaleTypeName());
		row = row + createRowContent("Tipo:", ad.getPropertySubTypeName());
		row = row + createRowContent("Precio:", getPrice(ad));
		row = row + createRowContent("Condici&oacute;n:", ad.getListingTypeName());
		return row;
	};
	
	public String createAddressContent(Ad ad) {
		String row = createRowContent("Provincia:", library.capitalizeFirst(getDivision1ById(ad.getStateId())));
		row = row + createRowContent("Cant&oacute;n:", library.capitalizeFirst(getDivision2ById(ad.getCountyId())));
		row = row + createRowContent("Ciudad:", ad.getCity());
		row = row + createRowContent("Residencial:", ad.getResidential());
		row = row + createRowContent("Direcci&oacute;n:", ad.getAddress());

		return row;
	};
	
	public String createDetailsContent(Ad ad) {
		String row = "";
		if (ad.getPropertyType().equals("L") ) {
			row = row + createRowContent("Tama&ntilde;o:", ad.getLotSize()+" mts2");
			row = row + createRowContent("Frente:", ad.getFrontSize() +" mts2");			
		} else {
			row = row + createRowContent("Tama&ntilde;o:", ad.getLotSize()+" mts2");
			row = row + createRowContent("Frente:", ad.getFrontSize() +" mts2");	
			row = row + createRowContent("Construcci&oacute;n:", ad.getConstructionSize().toString());
			row = row + createRowContent("Habitaciones:", ad.getBedRooms().toString());
			row = row + createRowContent("Ba&ntilde;os:", ad.getBathRooms().toString());
			row = row + createRowContent("Medios Ba&ntilde;os:", ad.getPartialBaths().toString());
			row = row + createRowContent("Pisos:", ad.getFloors().toString());		
			row = row + createRowContent("Cocheras:", ad.getGarages().toString());			
			row = row + createRowContent("A&ntilde;o de construcci&oacute;n:", ad.getYearBuilt().toString());			

		}


		return row;
	};
	
	public String getPrice(Ad ad) {
		DecimalFormat formatter = new DecimalFormat("###,###,###");

		String price = ad.getCurrencyFmt()+formatter.format(ad.getPrice().doubleValue());		
		String rentPrice = ad.getCurrencyFmt()+formatter.format(ad.getRentPrice().doubleValue());		
		
	
		if (ad.getSale()) return price;
		else  return rentPrice;		
	}

	public String getAddress(Ad ad) {
		return library.capitalizeFirst(getDivision2ById(ad.getCountyId())+ ", " + getDivision1ById(ad.getStateId()));		
	}

	
	public String setMailContent(String source, Ad ad) {
		
		String target = source.replaceAll("\t", "");  
		target = target.replaceAll("\n", "");
		
		target =  target.replace("[TITLE]", ad.getTitle());
		target = target.replace("[DETAILS]", ad.getDescription());
		// target = target.replace("[IMAGEURL]", ad.getImageUrl()+"=s280");

		target = target.replace("[IMAGEURL]", ad.getImageUrl());

		
		target = target.replace("[PRICE]", getPrice(ad));

		target = target.replace("[ADDRESS]", getAddress(ad));
		
		
		if (ad.getShowName() ) target = target.replace("[NAME]", ad.getFirstName()+ " " + ad.getLastName());
		if (ad.getShowPhone() ) target = target.replace("[PHONE]", ad.getPhone());
		if (ad.getShowEmail() ) target = target.replace("[EMAIL]", ad.getEmail());
		target = target.replace("[ADID]", ad.getSourceKey());

		String contactContent = createContactContent(ad) ;

		String propertyContent = createPropertyContent(ad) ;
		String addressContent = createAddressContent(ad) ;
		String detailsContent = createDetailsContent(ad) ;
		
		target = target.replace("[CONTACTTABLE]", contactContent);
		target = target.replace("[DETAILSTABLE]", propertyContent+detailsContent);
		target = target.replace("[ADDRESSTABLE]", addressContent);

		return target;
	};
	
	public String setWelcomeContent(String source, Ad ad) {
		
		String  target =  source.replace("[TITLE]", ad.getTitle());
		target = target.replace("[DETAILS]", ad.getDescription());
		target = target.replace("[IMAGEURL]", ad.getImageUrl());
		
		
		target = target.replace("[PRICE]", getPrice(ad));

		target = target.replace("[ADDRESS]", getAddress(ad));
		
		
		target = target.replace("[NAME]", ad.getFirstName());
		target = target.replace("[ADID]", ad.getSourceKey());

		
		target = target.replace("[ADTYPE]", ad.getAdTypeName());
		target = target.replace("[SALETYPE]", ad.getSaleTypeName());
		
		return target;
	};

	public void sendNewWelcomeMess(String adId)  {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        String htmlTemplate = "origen";
        String plainTemplate = "origen";

        String htmlContent = "origen";
        String plainContent = "origen";
        
        String toEmail = "ovalverde@gmail.com";
        String toName = "Oscar Valverde";

        try {
        	        	
        	URL url = this.getClass().getResource("/cr/novatec/crcasas/server/resources/welcomeHTMLTemp.html");
        	URL url2 = this.getClass().getResource("/cr/novatec/crcasas/server/resources/welcomePlainTemp.txt");
       	
        	try {
		       	if (url==null) htmlTemplate = "No se encontro la plantilla para poder enviar el correo";
		       	else htmlTemplate = Resources.toString(url, Charset.forName("UTF-8"));
		       	
		       	if (url2==null) plainTemplate = "No se encontro la plantilla para poder enviar el correo";
		       	else plainTemplate = Resources.toString(url2, Charset.forName("UTF-8"));

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
	        	htmlTemplate = "No se encontro la plantilla para poder enviar el correo";
	        	plainTemplate = "No se encontro la plantilla para poder enviar el correo";
			}
        	
        	
            if (adId!=null) {
            	
            	Ad ad = getAdBySourceKey(adId);
            	
            	if (ad!=null) {
            		
            		htmlContent = setWelcomeContent(htmlTemplate, ad);
            		plainContent = setWelcomeContent(plainTemplate, ad);
            		//htmlContent = htmlTemplate;
            		//plainContent = plainTemplate;
            		
            		toEmail = ad.getEmail();
            		toName = ad.getFirstName() + " " + ad.getLastName();
            		            		
            	} else {
            		htmlContent = "ad Not Found" + adId;
            		plainContent = "ad Not Found" + adId;
            	}
            	
            } else {
            	
           		htmlContent = "ad Not Found" + adId;
        		plainContent = "ad Not Found" + adId;
            }
            

        	
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("ventas@crCasas.com", "crCasas.com"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(toEmail, toName));
            msg.addRecipient(Message.RecipientType.BCC,
                    new InternetAddress("admin@crcasas.com", "Admin"));
           
            msg.setSubject("Informacion sobre su nuevo anuncio");
                        
            // Unformatted text version
            final MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(plainContent);
            // HTML version
            final MimeBodyPart htmlPart = new MimeBodyPart();
            

            
            htmlPart.setContent(htmlContent, "text/html");
            // Create the Multipart.  Add BodyParts to it.
            final Multipart mp = new MimeMultipart();
            mp.addBodyPart(textPart);
            mp.addBodyPart(htmlPart);
            // Set Multipart as the message's content
            msg.setContent(mp);
            
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
	

	
	
	
	
	public void sendAdByMail(String to,  String name, String subject, String adId)  {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        String text = "origen";
        String plainText = "origen";

        String newText = "origen";
        String newPlainText = "origen";

        try {
        	
        	
        	
        	URL url = this.getClass().getResource("/cr/novatec/crcasas/server/resources/htmlTemplate.txt");
        	URL url2 = this.getClass().getResource("/cr/novatec/crcasas/server/resources/plainTemplate.txt");
       	
        	try {
				//text = Resources.toString(url, Charset.forName("UTF-8"));								
		       	if (url==null) text = "trytooo";
		       	else text = Resources.toString(url, Charset.forName("UTF-8"));
		       	
		       	if (url2==null) text = "trytooo";
		       	else plainText = Resources.toString(url2, Charset.forName("UTF-8"));

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
	        	text = "catchooo.";
	        	plainText = "catchooo.";
			}
        	
        	
            if (adId!=null) {
            	
            	Ad ad = getAdBySourceKey(adId);
            	
            	if (ad!=null) {
            		
            		newText = setMailContent(text, ad);
            		newPlainText = setMailContent(plainText, ad);
            		
            	} else {
            		newPlainText = "ad Not Found" + adId;
            	}
            	
            } else {
            	
            	newPlainText = "ad Not Found" + adId;
            }
            

        	
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("ventas@crCasas.com", "crCasas.com"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(to, name));
            
            
            msg.addRecipient(Message.RecipientType.BCC,
                    new InternetAddress("admin@crcasas.com", "Admin"));

            
            msg.setSubject(subject);
                        
            // Unformatted text version
            final MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(newPlainText);
            // HTML version
            final MimeBodyPart htmlPart = new MimeBodyPart();
            

            
            htmlPart.setContent(newText, "text/html");
            // Create the Multipart.  Add BodyParts to it.
            final Multipart mp = new MimeMultipart();
            mp.addBodyPart(textPart);
            mp.addBodyPart(htmlPart);
            // Set Multipart as the message's content
            msg.setContent(mp);
            
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
	


	
	public List<Ad> getThreeAdsList() {
		
		List<SettingsDB> records =  dao.ofy().query(SettingsDB.class).list();
		
	    List<Ad> adList = new ArrayList<Ad>();
	    
	    Ad ad = null;

		
		if ( ! records.isEmpty() ) {
			
			int size = records.size();
			
			if (size>0) {
				
				SettingsDB settings  = records.get(size - 1);
				
				if (settings!=null) {
					
					if (settings.getMainAd1()!=null) {
						ad = getAdById(settings.getMainAd1());
						if (ad!=null) adList.add(ad);
					}

					if (settings.getMainAd2()!=null) {
						ad = getAdById(settings.getMainAd2());
						if (ad!=null) adList.add(ad);
					}
					if (settings.getMainAd3()!=null) {
						ad = getAdById(settings.getMainAd3());
						if (ad!=null) adList.add(ad);
					}					
					
				}
			}
		}

		return adList;
		
	}
	
	
	
	
	public List<Ad> getMainAdList() {
		
		List<SettingsDB> records =  dao.ofy().query(SettingsDB.class).list();
		
	    List<Ad> adList = new ArrayList<Ad>();
	    
	    Ad ad = null;

		
		if ( ! records.isEmpty() ) {
			
			int size = records.size();
			
			if (size>0) {
				
				SettingsDB settings  = records.get(size - 1);
				
				if (settings!=null) {
					
					if (settings.getMainAd1()!=null) {
						ad = getAdById(settings.getMainAd1());
						if (ad!=null) adList.add(ad);
					}

					if (settings.getMainAd2()!=null) {
						ad = getAdById(settings.getMainAd2());
						if (ad!=null) adList.add(ad);
					}
					if (settings.getMainAd3()!=null) {
						ad = getAdById(settings.getMainAd3());
						if (ad!=null) adList.add(ad);
					}
					if (settings.getMainAd4()!=null) {
						ad = getAdById(settings.getMainAd4());
						if (ad!=null) adList.add(ad);
					}
					
					
				}
			}
		}

		return adList;
		
	}
	
	
	
	public List<Ad> getAdList() {
		
		List<Ad> records =  dao.ofy().query(Ad.class).filter("status", "A").order("-creationDate").list();	

		return records;
		
	}
	
	public List<Ad> getAdPremiumList() {
		
		List<Ad> records =  dao.ofy().query(Ad.class).filter("adType", "P").filter("status", "A").order("-creationDate").list();	
		List<Ad> records2 =  dao.ofy().query(Ad.class).filter("adType", "P2").filter("status", "A").order("-creationDate").list();	
		List<Ad> records3 =  dao.ofy().query(Ad.class).filter("adType", "E").filter("status", "A").order("-creationDate").list();	
		List<Ad> records4 =  dao.ofy().query(Ad.class).filter("adType", "E2").filter("status", "A").order("-creationDate").list();	

		records.addAll(records2);		
		records.addAll(records3);
		records.addAll(records4);
		
		return records;
		
	}	
	
	public void loadGeos() {
		
		//System.out.println("Geocode started");
		List<Ad> records =  dao.ofy().query(Ad.class).list();
		Division3 d3 = null;
		Division2 d2 = null;
		int key = 0;
		
		GeoPt geoPt = null;
		
		for (Ad ad: records) {
			
			//System.out.println("a new ad");

			if ((ad!=null) && (ad.getGeoPoint()==null)) {
		
				//System.out.println("paso el iff");
				
				// 10101
				
				if (  ( ad.getStateId()!=null) &&  (ad.getCountyId()!=null) && (ad.getDistrictId()!=null) )
					key = ad.getStateId()*10000 + ad.getCountyId()*100 + ad.getDistrictId();	
				
				d3 = getDivision3ByKey(key);
				if (d3!=null) geoPt = d3.getGeoPoint();
				ad.setGeoPoint(geoPt);	
				
				if (geoPt==null) {
					// 101
					
					if (  ( ad.getStateId()!=null) &&  (ad.getCountyId()!=null)  )
						key = ad.getStateId()*100 + ad.getCountyId();
					d2 = getDivision2ByKey(key);					
					if (d2!=null) geoPt = d2.getGeoPoint();
					ad.setGeoPoint(geoPt);
				}
				
				if (geoPt != null) {
					//System.out.println("A new ad was geocoded");
					ad.setGeoCode("G");
				}
				
				dao.ofy().put(ad);
				//System.out.println("Ad was saved");
			
			}
		}
		
	}
	
	
	public List<Division1> getDivision1List() {
		
		List<Division1> records =  dao.ofy().query(Division1.class).order("key").list();
				
		return records;
		
	}
	

	public String getDivision1ById(Integer id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Division1 div1 = dao.ofy().query(Division1.class).filter("key", id).get();
		if (div1==null || div1.getKey()== null ) return "ND";
		return div1.getNameFmt();
	}

	
	
	public List<Division2> getDivision2List() {
		
		List<Division2> records =  dao.ofy().query(Division2.class).order("name").list();
				
		return records;
		
	}
	

	public List<Division2> getDivision2ListByDiv1(int d1) {
		
		List<Division2> records =  dao.ofy().query(Division2.class).filter("key >=", d1*100).filter("key <",(d1+1)*100).list();
				
		return records;
		
	}

	
	public List<Division3> getDivision3List() {
		
		List<Division3> records =  dao.ofy().query(Division3.class).order("name").list();
		

				
		return records;
		
	}
	
	
	public void loadDivision3Names() {
		
		List<Division3> records =  dao.ofy().query(Division3.class).list();
		
		Division2 d2 = null;
		
		int d2Key = 0;
				
		for (Division3 d3: records) {
			
			d2Key = d3.getKey()/100;
			
			//System.out.println("D3 Key"+ d3.getKey()+" D2 Key=" + d2Key);
						
			d2 = dao.ofy().query(Division2.class).filter("key", d2Key).get();
			
			if (d2!=null)  {
				d3.setD2Name(d2.getName());				
				saveDivision3(d3);
			}

		}
	}
	
	
	
	
	
	
	protected void doUnexpectedFailure(Throwable e) {
		System.out.println("unpexpected failure: "+e.getMessage());
		
		
	}

	public Integer getDivision3Code(String d3) {
		
		List<Division3> records = dao.ofy().query(Division3.class).filter("name", d3).list();
		
		if (records==null) return 0;
		
		return records.get(0).getKey();

	}
	
	public Integer getDivision2Code(String d2) {
		
		List<Division2> records = dao.ofy().query(Division2.class).filter("name", d2).list();
		
		if (records==null) return 0;
		
		return records.get(0).getKey();
	}	
	
	public GeoPt getDivision2Geo(Integer d2) {
		
		List<Division2> records = dao.ofy().query(Division2.class).filter("key", d2).list();
		
		if (records==null) return null;
		else {
			
			for (Division2 r : records) {
				if (r.getGeoPoint()!=null) return r.getGeoPoint();
			}
			
			return null;
		}

	}	
	
	
	public GeoPt getDivision3Geo(Integer d3) {
		
		List<Division3> records = dao.ofy().query(Division3.class).filter("key", d3).list();
		
		if (records==null) return null;
		else {
			
			for (Division3 r : records) {
				if (r.getGeoPoint()!=null) return r.getGeoPoint();
			}
			
			return null;
		}

	}	
	
	public Integer searchDivision3(String d3, Integer d1) {

		List<Division3> records;
		
		if ( (d1 != null) && (d1 > 0) && (d1 <8) ) {			
			records = dao.ofy().query(Division3.class).filter("key >=",d1*10000).filter("key <",(d1+1)*10000).list();			
		} else {
			records = dao.ofy().query(Division3.class).list();						
		}
			
		
		for (Division3 d: records) {
			//System.out.println();
			if (d.getName().contains(d3)) {
							//if (d3.contains(d.getName())) {
				return d.getKey();				
			}
		}
		return null;
	}
	
	public Integer searchEncuentra(String d3, Integer d1) {

		List<Encuentra3> records;
		
		Integer key;
		
		records = dao.ofy().query(Encuentra3.class).filter("name",d3).list();			
		
		if ( records.isEmpty() ) return null;

		if ( d1==null || d1 < 1 || d1 >7 )  return records.get(0).getKey();

		
		for (Encuentra3 d: records) {
				key = d.getKey();
				if (  (key >= d1*10000) && (key < (d1+1)*10000)) return key;
		}				
		
		return records.get(0).getKey();
	}
	
	
	public Integer searchNacion(String d3, Integer d1) {

		List<Nacion3> records;
		
		Integer key;
		
		records = dao.ofy().query(Nacion3.class).filter("name",d3).list();			
		
		if ( records.isEmpty() ) return null;

		if ( d1==null || d1 < 1 || d1 >7 )  return records.get(0).getKey();

		
		for (Nacion3 d: records) {
				key = d.getKey();
				if (  (key >= d1*10000) && (key < (d1+1)*10000)) return key;
		}				
		
		return records.get(0).getKey();
	}
	
	public Integer searchInmobi(String d3, Integer d1) {

		List<Inmobi3> records;
		
		Integer key;
		
		records = dao.ofy().query(Inmobi3.class).filter("name",d3).list();			
		
		if ( records.isEmpty() ) return null;

		if ( d1==null || d1 < 1 || d1 >7 )  return records.get(0).getKey();

		
		for (Inmobi3 d: records) {
				key = d.getKey();
				if (  (key >= d1*10000) && (key < (d1+1)*10000)) return key;
		}				
		
		return records.get(0).getKey();
	}
	
	public Integer searchDivision2(String d2, Integer d3, Integer d1) {
		
		List<Division2> records;
		
		if ( (d1 != null) && (d1 > 0) && (d1 <8) ) {			
			records = dao.ofy().query(Division2.class).filter("key >=",d1*100).filter("key <",(d1+1)*100).list();			
		} else {
			
			if (d3 != null) return d3/100;
			records = dao.ofy().query(Division2.class).list();						
		}
				
		for (Division2 d: records) {
			if (d.getName().contains(d2)) {
			//if (d2.contains(d.getName())) {
				return d.getKey();
			}
		}
		return null;
	}
	
	public Integer searchDivision1(String d1, Integer d2) {
		
		if (d1 != null) {
			if (d1.contains("SAN JOSE")) return 1;
			if (d1.contains("SAN-JOSE")) return 1;
			if (d1.contains("ALAJUELA")) return 2;
			if (d1.contains("CARTAGO")) return 3;
			if (d1.contains("HEREDIA")) return 4;
			if (d1.contains("GUANACASTE")) return 5;
			if (d1.contains("PUNTARENAS")) return 6;
			if (d1.contains("LIMON")) return 7;			
		}
				
		if (d2 != null) {
			if (d2 >= 0 && d2 < 200) return 1;
			if (d2 >= 200 && d2 < 300) return 2;
			if (d2 >= 300 && d2 < 400) return 3;
			if (d2 >= 400 && d2 < 500) return 4;
			if (d2 >= 500 && d2 < 600) return 5;
			if (d2 >= 600 && d2 < 700) return 6;
			if (d2 >= 700 && d2 < 800) return 7;

		}
		
		return null;
	}
	
	
	public String getUploadURL() throws IllegalArgumentException {
		BlobstoreService service =
			BlobstoreServiceFactory.getBlobstoreService();
			           return  service.createUploadUrl("/crcasas/blobstoreservlet");
			           
	}

	
	public Person saveFBUser(String fbId, String email, String firstName,
			 String lastName, String organization, String phone) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Boolean newPerson = false;
		Person person = dao.ofy().query(Person.class).filter("fbId", fbId).get();
		
		if (person==null) {
			person = new Person();
			newPerson = true;
			person.setFbId(fbId);

		} 
		
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setLastVisits(new Date());

		if (person.getVisits()==null) person.setVisits(1);
		else person.setVisits(person.getVisits()+1);
		
		// person.setOrganization(organization); NO LO FACTURA QUEDA PARA UN FUTURO
		person.setEmail(email);

		// person.setPhone(phone); NO LO CAPTURA QUEDA PARA UN FUTURO
		
		dao.ofy().put(person);
		assert person.getId() != null; 
		
		if (newPerson) {
			
       	sentEmail("Nuevo registro de usuario", 
       			"Nombre: " + firstName + "<br>" + 
               	"Apellido: " + lastName + "<br>" + 
               	"Correo: " + email + "<br>" + 
               	"User Id: " + person.getId().toString() + "<br>" + 
       			"fbId: " + fbId + "<br>"   
       			);
			
		}
		
		return person;
		
	}


	
	public Person getFBUser(String fbId) throws IllegalArgumentException {
		// TODO Auto-generated method stub

		return dao.ofy().query(Person.class).filter("fbId", fbId).get();
	}

	
	
	public void saveFBUser(String fbId, String organization, String phone)
			throws IllegalArgumentException {
		
		Person person = dao.ofy().query(Person.class).filter("fbId", fbId).get();
		
		if (person!=null) {
			person.setOrganization(organization);
			person.setPhone(phone);
			
			
			dao.ofy().put(person);
			assert person.getId() != null; 
			
		} 
				
		
	}
	
	public Person getPersonById(Long id) throws IllegalArgumentException {

		return dao.ofy().get(Person.class, id);
	}
	
	public Person getPersonByAd(Ad ad) throws IllegalArgumentException {

		return dao.ofy().get(ad.getAgentKey());
	}
	
	public Ad getAdBySourceKey(String sourceKey) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Ad ad = dao.ofy().query(Ad.class).filter("sourceKey", sourceKey).get();
		return ad;
	}

	
	public Ad getAdById(Long id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (id==null) return null;
		
		Ad ad = new Ad();
		
		try {
				ad = dao.ofy().get(Ad.class, id);
			}catch (Exception e) {
				
			}
		

		return ad;
	}
	
	public AdWeb getAdWebById(Long id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (id==null) return null;
		AdWeb ad = dao.ofy().get(AdWeb.class, id);
		return ad;
	}
	
	public Boolean adExpired(Long id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (id==null) return true;
		AdWeb ad = dao.ofy().get(AdWeb.class, id);
		if (ad.getExpDate()==null) return true;
		if (ad.getExpDate().before(new Date()));
		return true;
	}
	
	public void addNewVisit(Long id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (id!=null) {
			Ad ad = dao.ofy().get(Ad.class, id);
			Integer viewCount = ad.getViewCount();
			if (viewCount==null) viewCount = 1;
			else viewCount++;			
			ad.setViewCount(viewCount);			
			dao.ofy().put(ad);
		}
	}

	
	public String getDivision2ById(Integer id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Division2 div2 = dao.ofy().query(Division2.class).filter("key", id).get();
		if (div2==null || div2.getKey()== null ) return "ND";
		return div2.getNameFmt();
	}

	
	public String getDivision3ById(Integer id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Division3 div3 = dao.ofy().query(Division3.class).filter("key", id).get();
		if (div3==null || div3.getKey()== null ) return "ND";
		return div3.getName();
	}
	
	public Division3 getDivision3ByKey(Integer key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Division3 div3 = dao.ofy().query(Division3.class).filter("key", key).get();
		return div3;
	}

	public Division2 getDivision2ByKey(Integer key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Division2 div2 = dao.ofy().query(Division2.class).filter("key", key).get();
		return div2;
	}
	
	// aqui habia un override
	public AdCursor getAdsCursor(String startCursor, String propertyType, Boolean sale, 
								 Integer div1Key, Integer div2Key, Integer div3Key,
								 Integer from, Integer until) throws IllegalArgumentException {
			
		   
			String price = "";
			
		
			String endCursor = null;
		   Query<AdWeb> query;
		   
		   Integer condition = 1;
		   
		   if (from == null ) from = 0;
		   if (until == null ) until = 0;
		   
		   if (from == 0 && until == 0) condition = 1; // todos
		   if (from == 0 && until > 0)  condition = 2;  // hasta
		   if (from > 0 && until == 0)  condition = 3;  // desde 
		   if (from > 0 && until > 0)   condition = 4;  // desde hasta
		   		   
			Calendar calendar = Calendar.getInstance();
			
			calendar.add(Calendar.DAY_OF_MONTH, -15);
			
			Date date = calendar.getTime();

			if (div2Key<=0) div2Key=null;
		   
		   if (div1Key != null && div2Key != null) {
			   if (div3Key != null ) {				   
				   switch (condition) {
				   	case 1: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter("districtId", div3Key); break;
				   	case 2: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter("districtId", div3Key).filter(price+"<=", until); break;
				   	case 3: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter("districtId", div3Key).filter(price+">", from); break;
				   	case 4: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter("districtId", div3Key).filter(price+">", from).filter(price+"<=", until); break;
				   	default: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter("districtId", div3Key); break;
				   }				   				   
			   }
			   else {
				   switch (condition) {
				   	case 1: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key); break;
				   	case 2: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter(price+"<= ", until); break;
				   	case 3: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter(price+">", from); break;
				   	case 4: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter(price+">", from).filter(price+"<=", until); break;
				   	default: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key); break;
				   }				   				   
			   }
		   }
		   else  {
			   if (div1Key!=null) {
				   switch (condition) {
				   	case 1: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key); break;
				   	case 2: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter(price+"<=", until); break;
				   	case 3: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter(price+">", from); break;
				   	case 4: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter(price+">", from).filter(price+"<=", until); break;
				   	default: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key); break;
				   }				   				   
			   }
			   else {
				   switch (condition) {
				   	case 1: query = dao.ofy().query(AdWeb.class); break;
				   	case 2: query = dao.ofy().query(AdWeb.class).filter(price+"<=", until); break;
				   	case 3: query = dao.ofy().query(AdWeb.class).filter(price+">", from); break;
				   	case 4: query = dao.ofy().query(AdWeb.class).filter(price+">", from).filter(price+"<=", until); break;
				   	default: query = dao.ofy().query(AdWeb.class); break;
				   }
			   }
		   }
	
		   if (startCursor != null )
		        query.startCursor(Cursor.fromWebSafeString(startCursor));
		   		
		  

		    QueryResultIterator<AdWeb> iterator = query.iterator();
		    
		    Integer countAll = query.count();
		    

		    
		    int counter = 0;
		    
		    List<AdWeb> listAds = new ArrayList<AdWeb>();
		    
		    while (iterator.hasNext()) {
		    	counter++;
		    	
				listAds.add(iterator.next());
		    	if (counter==30) {		    		
		    		break;
		    	}
		    };
		    
		    endCursor = iterator.getCursor().toWebSafeString();
		    
		return new AdCursor(listAds, endCursor, countAll, counter);
	}


	public List<AdWeb> getMyAds(String propertyType, Boolean sale, 
			 String countryId,
			 Integer div1Key, Integer div2Key, Integer div3Key,
			 Integer from, Integer until, Boolean localCurrency) throws IllegalArgumentException {

		
		Query<Ad> query = dao.ofy().query(Ad.class); 
				
		QueryResultIterator<Ad> iterator = query.iterator();
		
		
		List<AdWeb> listAds = new ArrayList<AdWeb>();
		
		AdWeb adWeb;
		
		Long adWebId;
		
		Ad ad;
		
		while (iterator.hasNext()) {
			
			ad = iterator.next();
			
			adWebId = getAdWebIdBySourceKey(ad.getSourceKey(), ad.getSource(), ad.getCountryId());
			
			adWeb = getAdWebById(adWebId);
			
			if (adWeb!= null) listAds.add(adWeb);
		};
		
		
		return listAds;
}


	
	
	public AdCursor getAdsCursor(String startCursor, String propertyType, Boolean sale, 
			 String countryId,
			 Integer div1Key, Integer div2Key, Integer div3Key,
			 Integer from, Integer until, Boolean localCurrency) throws IllegalArgumentException {


		String price = "";
		
		if (localCurrency) {
			if (sale) price="localPrice ";
			else price = "localRentPrice ";

		} else {
			if (sale) price="dolPrice ";
			else price = "dolRentPrice ";
			
		}
		
		
		String endCursor = null;
		Query<AdWeb> query;
		
		Integer condition = 1;
		
		if (from == null ) from = 0;
		if (until == null ) until = 0;
		
		if (from == 0 && until == 0) condition = 1; // todos
		if (from == 0 && until > 0)  condition = 2;  // hasta
		if (from > 0 && until == 0)  condition = 3;  // desde 
		if (from > 0 && until > 0)   condition = 4;  // desde hasta
		 
		
		if (div2Key<=0) div2Key=null;
		
		if (div1Key != null && div2Key != null) {
		if (div3Key != null ) {				   
		switch (condition) {
			case 1: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter("districtId", div3Key); break;
			case 2: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter("districtId", div3Key).filter(price+"<=", until); break;
			case 3: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter("districtId", div3Key).filter(price+">", from); break;
			case 4: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter("districtId", div3Key).filter(price+">", from).filter(price+"<=", until); break;
			default: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter("districtId", div3Key); break;
		}				   				   
		}
		else {
		switch (condition) {
			case 1: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key); break;
			case 2: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter(price+"<=", until); break;
			case 3: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter(price+">", from); break;
			case 4: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key).filter(price+">", from).filter(price+"<=", until); break;
			default: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter("countyId", div2Key); break;
		}				   				   
		}
		}
		else  {
		if (div1Key!=null) {
		switch (condition) {
			case 1: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key); break;
			case 2: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter(price+"<=", until); break;
			case 3: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter(price+">", from); break;
			case 4: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key).filter(price+">", from).filter(price+"<=", until); break;
			default: query = dao.ofy().query(AdWeb.class).filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1Key); break;
		}				   				   
		}
		else {
		switch (condition) {
			case 1: query = dao.ofy().query(AdWeb.class); break;
			case 2: query = dao.ofy().query(AdWeb.class).filter(price+"<=", until); break;
			case 3: query = dao.ofy().query(AdWeb.class).filter(price+">", from); break;
			case 4: query = dao.ofy().query(AdWeb.class).filter(price+">", from).filter(price+"<=", until); break;
			default: query = dao.ofy().query(AdWeb.class); break;
		}
		}
		}
		
		List<AdWeb> listAds = new ArrayList<AdWeb>();

		if (startCursor != null )
		query.startCursor(Cursor.fromWebSafeString(startCursor));
		else {
			//listAds = getMyAds(propertyType, 
			//					sale, 
			//					countryId,
			//					div1Key, 
			//					div2Key, 
			//					div3Key,
			//					from, 
			//					until, 
			//					localCurrency);
			//if (listAds==null) listAds = new ArrayList<AdWeb>();
		}

		
		QueryResultIterator<AdWeb> iterator = query.iterator();
		
		Integer countAll = query.count();
		
		int counter = 0;
				
		while (iterator.hasNext()) {
		counter++;
		
		listAds.add(iterator.next());
		if (counter==30) {		    		
		break;
		}
		};
		
		endCursor = iterator.getCursor().toWebSafeString();
		
		return new AdCursor(listAds, endCursor, countAll, counter);
}


	public void loadPrice(){
		
		Query<Ad> query =  dao.ofy().query(Ad.class).filter("currencyCode1", Parameters.AD_CURRENCY_US);
		
		Ad ad;

		QueryResultIterator<Ad> iterator = query.iterator();
		
		
		while (iterator.hasNext() ) {

			ad = iterator.next();	
			
			// mover el precio de colones a dolares
			
			ad.setDolPrice(ad.getLocalPrice());
			ad.setDolRentPrice(ad.getLocalRentPrice());
			
			ad.setLocalPrice(ad.getDolPrice()*Parameters.EXCHANGE_RATE);
			ad.setLocalRentPrice(ad.getDolRentPrice()*Parameters.EXCHANGE_RATE);
		
			ad.setLocalCurrency(false);
			
			dao.ofy().put(ad);
			assert ad.getId() != null; 
			
		}	
		
		query =  dao.ofy().query(Ad.class).filter("currencyCode1", Parameters.AD_CURRENCY_CRC);
		
	   iterator = query.iterator();
		
	   Float f = 0F;
		
		while (iterator.hasNext() ) {

			ad = iterator.next();	
			
			f = ad.getLocalPrice()*Parameters.EXCHANGE_RATE_US;
			ad.setDolPrice(f.longValue());
			f = ad.getLocalRentPrice()*Parameters.EXCHANGE_RATE_US;
			ad.setDolRentPrice(f.longValue());
		
			ad.setLocalCurrency(true);
			
			dao.ofy().put(ad);
			assert ad.getId() != null; 
			
		}	
		
		 
	}
	
	
	public void fixAddress() {
		
		int count = 0;

		Ad a;
		
		QueryResultIterable<Key<Ad>> keys = dao.ofy().query(Ad.class).filter("geoCode","G").fetchKeys();
		//QueryResultIterable<Key<Ad>> keys = dao.ofy().query(Ad.class).filter("countyId",111).fetchKeys();

		
		Integer d3 = null;
		
		
		 for(int i=1; i<3; i++){
			 
				for (Key<Ad> key : keys) {

					a = dao.ofy().get(key);

					if (a.getSource() == 4)
						d3 = searchEncuentra(a.getAddress(), a.getStateId());
					if (a.getSource() == 2)
						d3 = searchNacion(a.getAddress(), a.getStateId());

					if (d3 != null) {

						a.updateDistrict(d3);
						a.updateGeo(getDivision3Geo(d3));

						dao.ofy().put(a);
						count++;

					}

				}
				
				keys = dao.ofy().query(Ad.class).filter("geoCode","N").fetchKeys();
        }
		

	}
	
	public void archive() {
		
		//log.info("Archive Task started at " + new Date());

		
		int count = 0;

		AdHistory h;
		
		Ad a;

		Calendar calendar = Calendar.getInstance();
		
		calendar.add(Calendar.DAY_OF_MONTH, -3);
		
		Date date = calendar.getTime();
		
		QueryResultIterable<Key<Ad>> keys = dao.ofy().query(Ad.class).filter("updateDate <=",date).fetchKeys();
		
		for ( Key<Ad> key: keys) {
			
			a = dao.ofy().get(key);
			
			h = new AdHistory(a.getListingType(), a.getPropertyType(), a.getSale(),
					a.getRent(), a.getAgentKey(), a.getOwnerKey(),
					a.getStatus(), a.getPubDate(), a.getStartDate(), a.getEndDate(),
					a.getExpDate(), a.getSource(), a.getSourceKey(), a.getSourceUrl(),
					a.getImageUrl(), a.getTitle(), a.getDescription(), a.getLocalPrice(),
					a.getDolPrice(), a.getLocalRentPrice(), a.getDolRentPrice(),
					a.getShowName(), a.getShowPhone(), a.getShowEmail(),
					a.getShowAddress(), a.getCurrencyCode1(), a.getCurrencyCode2(),
					a.getViewCount(), a.getPropertySubType(), a.getDetails(),
					a.getLotSize(), a.getConstructionSize(), a.getBedRooms(),
					a.getBathRooms(), a.getPartialBaths(), a.getYearBuilt(),
					a.getFloors(), a.getStreetAddress(), a.getResidential(),
					a.getCity(), a.getDistrictId(), a.getCountyId(), a.getStateId(),
					a.getZipCode(), a.getCountryId(), a.getGeoPoint(), a.getGeoCode());
			
			
		    h.setAddress(a.getAddress());
		    h.setUpdateDate(a.getUpdateDate());

		    
			dao.ofy().put(h);
			assert h.getId() != null;			
			dao.ofy().delete(key);
			
			count++;
			
		}
		
		//log.info("Archive Task archive "+count+" source 2 and 4 records and ended at " + new Date());
		
        Jobs jobs = new Jobs(new Date(), 2, "archive", count, "", "", true, 0);
        
		saveJob(jobs);		
		
		
	}

	
	public void clean() {
		
		
		int count = 0;
		
		Ad a;

		Calendar calendar = Calendar.getInstance();
		
		calendar.add(Calendar.DAY_OF_MONTH, -5);
		
		Date date = calendar.getTime();
		
		QueryResultIterable<Key<Ad>> keys = dao.ofy().query(Ad.class).filter("updateDate <=",date).fetchKeys();
		
		dao.ofy().delete(keys);
	
		
		
        Jobs jobs = new Jobs(new Date(), 2, "clean", 50, "", "", true, 0);
        
		saveJob(jobs);		
		
		
	}

	
	public void deleteInactive() {		
		
		
		   
	Calendar calendar = Calendar.getInstance();
	
	calendar.add(Calendar.MONTH, -1);
	
	
	Date date = calendar.getTime();
		
	sentEmail("delete inactive started", "");

		//dao.ofy().delete(dao.ofy().query(Ad.class).filter("source", 2).fetchKeys());
		//dao.ofy().delete(dao.ofy().query(Ad.class).filter("source", 4).fetchKeys());
	
		sentEmail("delete inactive finish", "");
		
		
	}
	
	
	
	
	public void count() {
		
		QueryResultIterable<Key<Division3>> keys = dao.ofy().query(Division3.class).fetchKeys();		
		
		Division3 div3;
		
		Integer houses;
		Integer rents;
		Integer lots;
		
		
		for ( Key<Division3> key: keys) {
			

			
			div3 = dao.ofy().get(key);


			houses = dao.ofy().query(Ad.class).filter("districtId", div3.getKey()).filter("propertyType", "H").filter("sale", true).count();
			rents = dao.ofy().query(Ad.class).filter("districtId", div3.getKey()).filter("propertyType", "H").filter("sale", false).count();
			lots = dao.ofy().query(Ad.class).filter("districtId", div3.getKey()).filter("propertyType", "L").filter("sale", true).count();

			div3.setHouses(houses);
			div3.setRents(rents);
			div3.setLots(lots);
			
			dao.ofy().put(div3);
			
		}


		
	}
	
	public void countDiv2() {
		
		QueryResultIterable<Key<Division2>> keys = dao.ofy().query(Division2.class).fetchKeys();		
		
		Division2 div2;
		Division3 div3;

		
		Integer houses = 0;
		Integer rents = 0;
		Integer lots = 0;
		

		QueryResultIterable<Key<Division3>> div3Keys;
		
		for ( Key<Division2> key: keys) {
			
			div2 = dao.ofy().get(key);

			div3Keys =  dao.ofy().query(Division3.class).filter("key >=", div2.getKey()*100).filter("key <", (div2.getKey()+1)*100).fetchKeys();
			
			for ( Key<Division3> div3Key: div3Keys) {
				div3 = dao.ofy().get(div3Key);
				houses = houses + div3.getHouses();
				rents = rents + div3.getRents();
				lots = lots + div3.getLots();
			}	
			
			div2.setHouses(houses);
			div2.setRents(rents);
			div2.setLots(lots);
			
			houses = 0;
			rents = 0;
			lots = 0;
			
			dao.ofy().put(div2);
			
		}
		
	}
	
	public void countDiv1() {
		
		QueryResultIterable<Key<Division1>> keys = dao.ofy().query(Division1.class).fetchKeys();		
		
		Division1 div1;
		Division2 div2;

		
		Integer houses = 0;
		Integer rents = 0;
		Integer lots = 0;
		

		QueryResultIterable<Key<Division2>> div2Keys;
		
		for ( Key<Division1> key: keys) {
			
			div1 = dao.ofy().get(key);

			div2Keys =  dao.ofy().query(Division2.class).filter("key >=", div1.getKey()*100).filter("key <", (div1.getKey()+1)*100).fetchKeys();
			
			for ( Key<Division2> div2Key: div2Keys) {
				div2 = dao.ofy().get(div2Key);
				houses = houses + div2.getHouses();
				rents = rents + div2.getRents();
				lots = lots + div2.getLots();
			}	
			
			div1.setHouses(houses);
			div1.setRents(rents);
			div1.setLots(lots);
			
			houses = 0;
			rents = 0;
			lots = 0;
			
			dao.ofy().put(div1);
			
		}


		
	}

	
	public void transformTablesONETIMEUSEONLY() {
		
		Query<NacionDivision3> query = dao.ofy().query(NacionDivision3.class);		
		NacionDivision3 s;
		Nacion3 t;
		QueryResultIterator<NacionDivision3> iterator = query.iterator();
		
		while (iterator.hasNext()) {
			
			s = iterator.next();
			
			t = new Nacion3(Integer.valueOf(s.getKey()), s.getName());
			
			dao.ofy().put(t);
			assert t.getId() != null; 
		}
		
		
		Query<EncuentraDivision3> query2 = dao.ofy().query(EncuentraDivision3.class);		
		EncuentraDivision3 s2;
		Encuentra3 t2;
		QueryResultIterator<EncuentraDivision3> iterator2 = query2.iterator();
		
		while (iterator2.hasNext()) {
			
			s2 = iterator2.next();
			
			t2 = new Encuentra3(Integer.valueOf(s2.getKey()), s2.getName());
			
			dao.ofy().put(t2);
			assert t2.getId() != null; 
		}
		
		
	}
	
	public void fixCoronadoONETIMEUSEONLY() {
		
		Division2 d2 = getDivision2ByKey(111);					
		if (d2!=null) {
			d2.setName("VAZQUEZ DE CORONADO");
			dao.ofy().put(d2);
		}
		
	}

	
	public void updateImageId(Long id, Long adId, String caption) throws IllegalArgumentException {
		ImageDataBase image = getImageById(id);
		image.setAdId(adId);
		image.setCaption(caption);

		image.setCaption("this shit is calling updateimageid");

		dao.ofy().put(image);		

	}

	
	public ImageDataBase getImageById(Long id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return dao.ofy().get(ImageDataBase.class, id);
	}
	
	public ImageDataBase getFirstImage(Long adId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return dao.ofy().query(ImageDataBase.class).filter("adId", adId).filter("displayOrder", 1).get();
	}	

	public ImageDataBase getImageByUrl(String url) throws IllegalArgumentException {
		
	if (url!=null) {
			
			ImageDataBase image = dao.ofy().query(ImageDataBase.class).filter("imageUrl", url).get();
			
			return image;
		}
		
		return null;
	}

	
	public ImageDataBase updateImage(ImageDataBase from) throws IllegalArgumentException {
		
		ImageDataBase to = getImageById(from.getId());
		
		to.setCaption(from.getCaption());
		to.setDisplayOrder(from.getDisplayOrder());
		
		dao.ofy().put(to);		
		return to;
		
		
	}
	
	public ImageDataBase updateImageDB(ImageDataBase from) throws IllegalArgumentException {
		
		//ImageDataBase to = getImageById(from.getId());
		
		//to.setCaption(from.getCaption()+" ..GOTCHA");
		//to.setDisplayOrder(from.getDisplayOrder());
		
		
		//dao.ofy().put(to);		
		//return to;
		return null;
	}
	
	public void saveImage(ImageDataBase from) throws IllegalArgumentException {
		
		ImageDataBase to =  dao.ofy().get(ImageDataBase.class, from.getId());			
		
		if (from.getCaption() != null) to.setCaption(from.getCaption());
		if (from.getDisplayOrder() != null) to.setDisplayOrder(from.getDisplayOrder());
		if (from.getAdId() != null) to.setAdId(from.getAdId());
		
		dao.ofy().put(to);
		
		
	}
	
	
	
	public List<ImageDataBase> getImagesByAdId(Long adId)
			throws IllegalArgumentException {
		
		Query<ImageDataBase> query;
		query = dao.ofy().query(ImageDataBase.class).filter("adId", adId).order("displayOrder");
	    QueryResultIterator<ImageDataBase> iterator = query.iterator();
	    
	   if (!iterator.hasNext()) return null;

	    List<ImageDataBase> records = new ArrayList<ImageDataBase>();
	    	    
	    while (iterator.hasNext()) {
	    	
			records.add(iterator.next());
	
	    };

        return records;
	}

	
	public List<String> getImagesKeysByAdId(Long adId)
			throws IllegalArgumentException {
		
		Query<ImageDataBase> query;
		query = dao.ofy().query(ImageDataBase.class).filter("adId", adId).order("displayOrder");
	    QueryResultIterator<ImageDataBase> iterator = query.iterator();
	    
	   if (!iterator.hasNext()) return null;

	    List<String> records = new ArrayList<String>();
	    	    
	    while (iterator.hasNext()) {
	    	
			records.add(iterator.next().getImageKey());
	
	    };

        return records;
	}
	
	public int getImagesLastNumber(Long adId) {

		
		List<ImageDataBase> imageList = dao.ofy().query(ImageDataBase.class).filter("adId", adId).order("displayOrder").list();
		
		return (imageList!=null) ? imageList.size() + 1: 1;
		
	}
	
	public void deleteImagesByAdId(Long adId)
			throws IllegalArgumentException {
		
		List<ImageDataBase> records =  dao.ofy().query(ImageDataBase.class).filter("adId", adId).list();
		
		for (ImageDataBase r: records) {
			deleteImage(r.getId());
		}
		

	}
	

	
	public void deleteImage(Long id) throws IllegalArgumentException {
		ImageDataBase image =  dao.ofy().get(ImageDataBase.class, id);	
		
		blobstoreService.delete(new BlobKey(image.getKey()));		

		dao.ofy().delete(image);			

	}

	
	public Boolean confirm(Long adId, Integer key)
			throws IllegalArgumentException {
		
		Ad ad = getAdById(adId);
		if (ad==null) return false;
		//if (ad.getConfirmationKey().intValue() == key.intValue()) {


		if (ad.getConfirmationKey().compareTo(key.intValue()) == 0) {		
			ad.setStatus("A");
			dao.ofy().put(ad);	
			sentPublish(ad.getEmail(), ad.getId(), 
					 ad.getFirstName(),  ad.getConfirmationKey());

			return true;
			}
		
		return false;

	}
	


	
	public Boolean edit(Long adId, Integer key) throws IllegalArgumentException {
		
		Ad ad = getAdById(adId);
		if (ad==null) return false;
		

		if (ad.getConfirmationKey().compareTo(key.intValue()) == 0) return true;		
		
		
		return false;			
		}

	
	public List<Ad> getAdList(String email) throws IllegalArgumentException {
		List<Ad> records =  dao.ofy().query(Ad.class).filter("email", email).list();
		
		return records;
	}

	
	
	public Ad getAdLastByFbId(String fbId) throws IllegalArgumentException {
		
		if (fbId!=null) {
			
			Person person = getFBUser(fbId);
			
			if (person!=null) {
							
				Ad ad = dao.ofy().query(Ad.class).filter("agentKey", new Key<Person>(Person.class, person.getId())).filter("status", "Z").get();

				return ad;
			}
		}
		//List<Ad> records =  dao.ofy().query(Ad.class).filter("email", email).list();
		
		return null;
	}	
	
	public Boolean hasAdsByFbId(String fbId) throws IllegalArgumentException {
		
		if (fbId!=null) {
			
			Person person = getFBUser(fbId);
			
			if (person!=null) {
							
				Ad ad = dao.ofy().query(Ad.class).filter("agentKey", new Key<Person>(Person.class, person.getId())).filter("status !=", "Z").get();

				if ( (ad!=null) && (!ad.getStatus().equals("Z"))) return true;
			}
		}
		
		return false;
	}
	
	public Query<Ad> getActiveAdsQuery() {
		return dao.ofy().query(Ad.class).filter("status", "A");
	}
	
	public Query<Person> getPersonQuery() {
		return dao.ofy().query(Person.class).order("fbId");
	}
	
	public List<Ad> getAdsByFbId(String fbId) throws IllegalArgumentException {
		
		if (fbId!=null) {
			
			Person person = getFBUser(fbId);
			
			if (person!=null) {
				
				List<Ad> records =  dao.ofy().query(Ad.class).filter("agentKey", new Key<Person>(Person.class, person.getId())).filter("status !=", "Z").list();
			
				return records;
			}
		}
		//List<Ad> records =  dao.ofy().query(Ad.class).filter("email", email).list();
		
		return null;
	}

	public Person getPersonByEmail(String email) throws IllegalArgumentException {
		
		if (email!=null) {
			
			Person person = dao.ofy().query(Person.class).filter("email", email).get();
			
			return person;
		}
		//List<Ad> records =  dao.ofy().query(Ad.class).filter("email", email).list();
		
		return null;
	}
	
	public List<Ad> getAdsByPersonId(Long personId) throws IllegalArgumentException {
		
		if (personId!=null) {
			
				Person person = dao.ofy().get(Person.class, personId);
					
				List<Ad> records =  dao.ofy().query(Ad.class).filter("agentKey", person).list();
				
				return records;
			
		}
		
		//List<Ad> records =  dao.ofy().query(Ad.class).filter("email", email).list();
		
		return null;
	}


	
	public String getImageUrl(Long adId) throws IllegalArgumentException {
		List<ImageDataBase> records =  dao.ofy().query(ImageDataBase.class).filter("adId", adId).list();
		if ( ! records.isEmpty() ) {
			return records.get(0).getImageUrl();
		}
		return null;
	}

	public String getImageUrlByImageKey(String imageKey) throws IllegalArgumentException {
		List<ImageDataBase> records =  dao.ofy().query(ImageDataBase.class).filter("imageKey", imageKey).list();
		if ( ! records.isEmpty() ) {
			return records.get(0).getImageUrl();
		}
		return null;
	}
	
	public Long getImageIdByImageKey(String imageKey) throws IllegalArgumentException {
		List<ImageDataBase> records =  dao.ofy().query(ImageDataBase.class).filter("imageKey", imageKey).list();
		if ( ! records.isEmpty() ) {
			return records.get(0).getId();
		}
		return null;
	}
	
	
	public List<String> getImageUrlList(Long adId)
			throws IllegalArgumentException {
		List<ImageDataBase> records =  dao.ofy().query(ImageDataBase.class).filter("adId", adId).list();
		
	       List<String> list = new ArrayList<String>();
	        for (ImageDataBase l : records)
	        {
	         list.add(l.getImageUrl()); 
	        }
	        return list;

	}

	

	

	
	public void saveImageCaption(Long id, String caption, String description) throws IllegalArgumentException {
		
		if (id!=null) {
			ImageDataBase image = dao.ofy().get(ImageDataBase.class, id);
			if (image!= null) {
				image.setCaption(caption);
				image.setDescription(description);
				dao.ofy().put(image);
			}
			
		}
		
	}

	
	public void saveImageOrder(List<Long> idList)
			throws IllegalArgumentException {
		
		ImageDataBase image;
		
		Long adId;
		
		int order = 1;
		for (Long id: idList) {
			if (id!=null) {
				image = dao.ofy().get(ImageDataBase.class, id);
				image.setDisplayOrder(order);
				dao.ofy().put(image);				
				if (order==1) {
					saveAdImageUrl(image.getAdId(), image.getImageUrl(), image.isPortrait());				
				}
				order++;


			}
		}
		
	}

	
	public void saveImageAdId(Long id, Long adId)
			throws IllegalArgumentException {
		
		if (id!=null) {
			ImageDataBase image = dao.ofy().get(ImageDataBase.class, id);
			if (image!= null) {
				
				image.setAdId(adId);				
				int displayOrder = getImagesLastNumber(adId);				
				image.setDisplayOrder(displayOrder);							
				dao.ofy().put(image);
				
				if (displayOrder == 1) {
					saveAdImageUrl(adId, image.getImageUrl(), image.isPortrait());
				}
				
			}
			
			}	
		};
		
		public void saveImageAdId(Long id, Long adId, int order)
				throws IllegalArgumentException {
			
			if (id!=null) {
				ImageDataBase image = dao.ofy().get(ImageDataBase.class, id);
				if (image!= null) {
					
					image.setAdId(adId);				
					image.setDisplayOrder(order);							
					dao.ofy().put(image);
					
					if (order == 1) {
						saveAdImageUrl(adId, image.getImageUrl(), image.isPortrait());
					}
					
				}
				
				}	
			};		

	
	public void activateAd(Long id) throws IllegalArgumentException {
		Ad ad =  dao.ofy().get(Ad.class, id);	
		if (ad!=null) {
			ad.activate();
			dao.ofy().put(ad);						
		}
	}

	
	public void stopAd(Long id) throws IllegalArgumentException {
		Ad ad =  dao.ofy().get(Ad.class, id);	
		if (ad!=null) {
			ad.stop();
			dao.ofy().put(ad);						
		}		
	}


    public void setGlobals() throws IllegalArgumentException{
    	
    	Global global = new Global();
    	
    	global.setLastUpdate(new Date());
    	global.setExchangeToLocal(503f);
    	global.setExchangeToInter(0.0019880f);
    	
    	global.setCountryId(Parameters.AD_COUNTRY_CRC);
    	
		dao.ofy().put(global);
		assert global.getId() != null; 

    }
    
   public Global getGlobal() throws IllegalArgumentException{
	   
	   Global global = dao.ofy().query(Global.class).get();

	   	   
	   if (global==null) {
		   global = new Global();
		   
		   global.setCountryId("CRC");
		   global.setExchangeToInter(1/506f);
		   global.setExchangeToLocal(506f);
		   
		   saveGlobal(global);
		   
		   return global;
		   
	   } 

	   
	   return global;
   };
   
   
	public int generateNextId () {
		
		Random r = new Random();
		
		int id = r.nextInt(Parameters.MAX_AD_ID);
		
		id = id + Parameters.MIN_AD_ID;			
				
		return id;
	};
	
	public Integer nextAdId() {
		Integer n = generateNextId();	
		Ad ad = getAdBySourceKey(n.toString());		
		if ( ad==null) return n;
		else {
			n = generateNextId();	
			ad = getAdBySourceKey(n.toString());		
			if ( ad==null) return n;
			else {
				n = generateNextId();	
				ad = getAdBySourceKey(n.toString());		
				if ( ad==null) return n;				
			}
		}
		sentEmail("NextAdId failure", "Id= "+ad.getId().toString());
		
		return null;
	};
	
   
   public void updatePrice() throws IllegalArgumentException {
	   
	   Global global = getGlobal();
		Query<Ad> q = dao.ofy().query(Ad.class);
  
		for (Ad ad: q) {
			if(ad.getLocalCurrency()) {
				ad.setDolPrice(global.convertToInter(ad.getLocalPrice()));
				ad.setDolRentPrice(global.convertToInter(ad.getLocalRentPrice()));

			} else {
				ad.setLocalPrice(global.convertToLocal(ad.getDolPrice()));
				ad.setLocalRentPrice(global.convertToLocal(ad.getDolRentPrice()));
				
			}
			
			dao.ofy().put(ad);
		}
 
   }
   
   
   public void deleteDuplicates() throws IllegalArgumentException {
	   
		Query<Ad> q = dao.ofy().query(Ad.class).order("source").order("sourceKey");
  
		Integer previousSource = 0;
		String previousSourceKey = "empty";
		
		int k = 1;
		int j = 1;
				
		sentEmail("delete duplicates stared", "" );
		
		for (Ad ad: q) {
			
			
			
			if (ad.getSourceKey()!=null) {

				if (ad.getSourceKey().equals(previousSourceKey) && ad.getSource()==previousSource) {
							if (ad.getSource()!=1) {
								ad.setMarkToDelete(true);
								dao.ofy().put(ad);
								k++;
								
							}

				} else {
					previousSource = ad.getSource();
					previousSourceKey = ad.getSourceKey();
					
				}

				
			}
			
			j++;
			if (k==1000) break;
		}
 
		dao.ofy().delete(dao.ofy().query(Ad.class).filter("markToDelete",true).fetchKeys());
		
		sentEmail("delete duplicates: " + k, "J=" + j );
   }

public void saveError(String message, String localized, String module, String type) throws IllegalArgumentException {
	Errors error = new Errors();
	
	error.setMessage(message);
	error.setLocalizedMessage(localized);
	error.setModule(module);
	error.setType(type);
	error.setDate(new Date());
	
	
	
	dao.ofy().put(error);
	assert error.getId() != null; 
}

public void saveMessage(String mess1, String mess2) throws IllegalArgumentException {
	Messages mess = new Messages();
	
	mess.setField1(mess1);
	mess.setField2(mess2);
	
	dao.ofy().put(mess);
	assert mess.getId() != null; 
}


public void sentToCM(Long adId) {
	
	Ad ad = getAdById(adId);
	if (ad!= null) {
		
		Person person = dao.ofy().get(ad.getAgentKey());
		
		if (person!=null) {
			
			URL url;
			try {
				
				url = new URL("http://api.createsend.com/api/v3/subscribers/"+Parameters.APINEWSALES+".xml");
				
			

				String credentials = Parameters.APICMKEY +":password";
				
						
				HttpURLConnection con;
				int responseCode = HttpURLConnection.HTTP_UNAVAILABLE; 
				con = (HttpURLConnection) url.openConnection(); 
				con.setRequestMethod("POST"); 
				con.setDoOutput(true);
				con.setDoInput(true);
				
				String result1 = Base64.encode( credentials);
				
				
				con.setRequestProperty("Authorization",
						"Basic "+ result1);

				con.setRequestProperty("Content-Type",
						"text/html; charset=utf-8");


				
				
				String body = createXMLSubscriber(person, adId, ad.getPropertyTypeName());
				
				if (body!=null) {
					OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), "UTF8");
			        writer.write(body);
			        writer.close();					
				}


		        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
		            // OK
		        } else {
		        	Integer code = con.getResponseCode();
		        	
		            saveError("Error Response Code", code.toString(), "sentToCM", con.getResponseMessage());
		        }
				

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			
		}
	}
	
	
	


	
	}

public String createXMLSubscriber(Person person, Long adId, String propertyType) {
	
	
	String body = null;
	
	if (person!=null) {
		
		 body =
				"<Subscriber>"+
				    "<EmailAddress>"+person.getEmail()+"</EmailAddress>"+
				    "<Name>"+person.getLastName()+"</Name>"+
				    "<CustomFields>"+
				        "<CustomField>"+
				            "<Key>AdId</Key>"+
				            "<Value>"+adId+"</Value>"+
				        "</CustomField>"+
				        "<CustomField>"+
				            "<Key>FirstName1</Key>"+
				            "<Value>"+person.getFirstName()+"</Value>"+
				        "</CustomField>"+
				        "<CustomField>"+
				            "<Key>PropertyType</Key>"+
				            "<Value>"+propertyType+"</Value>"+
				        "</CustomField>"+
				    "</CustomFields>"+
				    "<Resubscribe>true</Resubscribe>"+
				    "<RestartSubscriptionBasedAutoresponders>true</RestartSubscriptionBasedAutoresponders>"+
				"</Subscriber>";


		 
	}

	
	return body;
}


public void sentAdToCM(Long adId) {
	
	Ad ad = getAdById(adId);
	if (ad!= null) {
		
		Person person = dao.ofy().get(ad.getAgentKey());
		
		if (person!=null) {
			
			unsubscribeCM(adId);
			
			URL url;
			try {
				
				url = new URL("http://api.createsend.com/api/v3/subscribers/"+Parameters.NEWADSID+".xml");

				String credentials = Parameters.APICMKEY +":password";
				
						
				HttpURLConnection con;
				int responseCode = HttpURLConnection.HTTP_UNAVAILABLE; 
				con = (HttpURLConnection) url.openConnection(); 
				
				
				con.setRequestMethod("POST"); 
				con.setDoOutput(true);
				con.setDoInput(true);
				
				
				String result1 = Base64.encode( credentials);
				
				
				con.setRequestProperty("Authorization",
						"Basic "+ result1);

				con.setRequestProperty("Content-Type",
						"text/xml");

				
				String body = createXMLAd(ad, person);
				
				if (body!=null) {
					
					
					OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), "UTF8");
					
					
					
			        writer.write(body);
			        writer.close();					
				}


		        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
		            // OK
		        } else {
		        	Integer code = con.getResponseCode();
		        	
		            saveError("Error Response Code", code.toString(), "sentToCM", con.getResponseMessage());
		        }
				

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			
		}
	}
	
	
	


	
	}

public void sentAdToFaceBookCM(Long adId) {
	
	Ad ad = getAdById(adId);
	if (ad!= null) {
		
		Person person = dao.ofy().get(ad.getAgentKey());
		
		if (person!=null) {
			
			
			URL url;
			try {
				
				url = new URL("http://api.createsend.com/api/v3/subscribers/"+Parameters.CMFACEBOOKID+".xml");

				String credentials = Parameters.APICMKEY +":password";
				
						
				HttpURLConnection con;
				int responseCode = HttpURLConnection.HTTP_UNAVAILABLE; 
				con = (HttpURLConnection) url.openConnection(); 
				
				
				con.setRequestMethod("POST"); 
				con.setDoOutput(true);
				con.setDoInput(true);
				
				
				String result1 = Base64.encode( credentials);
				
				
				con.setRequestProperty("Authorization",
						"Basic "+ result1);

				con.setRequestProperty("Content-Type",
						"text/xml");

				
				String body = createXMLShort(ad, person);
				
				if (body!=null) {
					
					
					OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), "UTF8");
					
					
					
			        writer.write(body);
			        writer.close();					
				}


		        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
		            // OK
		        } else {
		        	Integer code = con.getResponseCode();
		        	
		            saveError("Error Response Code", code.toString(), "sentToFaceBookCM", con.getResponseMessage());
		        }
				

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			
		}
	}
	
	
	


	
	}


public void unsubscribeCM(Long adId) {
	
	Ad ad = getAdById(adId);
	if (ad!= null) {
		
		Person person = dao.ofy().get(ad.getAgentKey());
		
		if (person!=null) {
			
			URL url;
			try {
				
				//http://api.createsend.com/api/v3/subscribers/{listid}/unsubscribe.{xml|json}
				
				url = new URL("http://api.createsend.com/api/v3/subscribers/"+Parameters.NEWADSID+"/unsubscribe.xml");

				String credentials = Parameters.APICMKEY +":password";
				
						
				HttpURLConnection con;
				int responseCode = HttpURLConnection.HTTP_UNAVAILABLE; 
				con = (HttpURLConnection) url.openConnection(); 
				
				
				con.setRequestMethod("POST"); 
				con.setDoOutput(true);
				con.setDoInput(true);
				
				
				String result1 = Base64.encode( credentials);
				
				
				con.setRequestProperty("Authorization",
						"Basic "+ result1);

				con.setRequestProperty("Content-Type",
						"text/xml");

				
				String body = createXMLEmail(person.getEmail());
				
				if (body!=null) {
					
					
					OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), "UTF8");
					
					
					
			        writer.write(body);
			        writer.close();					
				}


		        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
		            // OK
		        } else {
		        	Integer code = con.getResponseCode();
		        	
		            saveError("Error Response Code", code.toString(), "sentToCM", con.getResponseMessage());
		        }
				

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			
		}
	}
	
	
	


	
	}

public String createXMLAd(Ad ad, Person person) {
	
	String body = null;
	
	if (ad!= null) {
		
		
		if (person!=null) {
			
			Calendar calendar = Calendar.getInstance();		
		    SimpleDateFormat df = new SimpleDateFormat();
		    
		    df.applyPattern("yyyy/MM/dd");
			if (ad.getCreationDate()!=null)	calendar.setTime(ad.getCreationDate());
			else calendar.setTime(new Date());
			
			String creationDate = df.format(calendar.getTime());
			
			if (ad.getUpdateDate()!=null)	calendar.setTime(ad.getUpdateDate());
			else calendar.setTime(new Date());
			
			String updateDate = df.format(calendar.getTime());
			
			String div1Name = getDivision1ById(ad.getStateId()).toUpperCase();
			String div2Name = getDivision2ById(ad.getCountyId()).toUpperCase();
			
			String imageUrl = ad.getImageUrl();
			
			
			//String test = "para poner las\u00f1 \u00e1";
			
			if (imageUrl==null || imageUrl.trim().isEmpty()) imageUrl = "https://www.dropbox.com/s/5voc9sihl9ykdgh/image-not-found.gif";
			
			
			 body =
						"<Subscriber>"+
						    "<EmailAddress>"+person.getEmail()+"</EmailAddress>"+
						    "<Name>"+person.getFirstName()+" "+person.getLastName()+"</Name>"+
						    "<CustomFields>"+
						    
						        "<CustomField>"+
					            	"<Key>saleType</Key>"+
					            	"<Value>"+ad.getSaleTypeName()+"</Value>"+
					            "</CustomField>"+
					            	
						        "<CustomField>"+
					            	"<Key>propertyType</Key>"+
					            	"<Value>"+ad.getPropertyTypeName()+"</Value>"+
					            "</CustomField>"+					            
						    
						        "<CustomField>"+
						        	"<Key>price</Key>"+
						        	"<Value>"+ad.getPriceFmtServer()+"</Value>"+
						        "</CustomField>"+

						        "<CustomField>"+
						        	"<Key>imageUrl</Key>"+
						        	"<Value>"+ad.getImageUrl()+"</Value>"+
						        "</CustomField>"+

						        "<CustomField>"+
						        	"<Key>creationDate</Key>"+
						        	"<Value>"+creationDate+"</Value>"+
						        "</CustomField>"+

						        "<CustomField>"+
					        		"<Key>updateDate</Key>"+
					        		"<Value>"+updateDate+"</Value>"+
					        	"</CustomField>"+

						        "<CustomField>"+
					        		"<Key>address</Key>"+
					        		"<Value>"+div2Name+", "+div1Name+"</Value>"+
					        	"</CustomField>"+

						        "<CustomField>"+
						        	"<Key>title</Key>"+
						        	"<Value>"+ad.getTitle()+"</Value>"+
						        "</CustomField>"+
					        	
						        "<CustomField>"+
						        	"<Key>details</Key>"+
						        	"<Value>"+ad.getDescription()+"</Value>"+
						        	//"<Value>"+test+"</Value>"+

						        "</CustomField>"+

						        "<CustomField>"+
						            "<Key>adId</Key>"+
						            "<Value>"+ad.getId()+"</Value>"+
						        "</CustomField>"+
						            
						    "</CustomFields>"+
						    "<Resubscribe>true</Resubscribe>"+
						    "<RestartSubscriptionBasedAutoresponders>true</RestartSubscriptionBasedAutoresponders>"+
						"</Subscriber>";


		}
			
		}
	

	
	return body ;
}

public String createXMLEmail(String email) {
	
	String body = null;
	
			
			
			 body =
						"<Subscriber>"+
						    "<EmailAddress>"+email+"</EmailAddress>"+
						"</Subscriber>";


	
	return body ;
}

public String createXMLShort(Ad ad, Person person) {
	
	String body = null;
	
	if (ad!= null) {
		
		
		if (person!=null) {
			
			Calendar calendar = Calendar.getInstance();		
		    SimpleDateFormat df = new SimpleDateFormat();
		    
		    df.applyPattern("yyyy/MM/dd");
			if (ad.getCreationDate()!=null)	calendar.setTime(ad.getCreationDate());
			else calendar.setTime(new Date());
			
			String creationDate = df.format(calendar.getTime());						
			
			 body =
						"<Subscriber>"+
						    "<EmailAddress>"+person.getEmail()+"</EmailAddress>"+
						    "<Name>"+person.getFirstName()+" "+person.getLastName()+"</Name>"+
						    "<CustomFields>"+

						        "<CustomField>"+
						        	"<Key>creationDate</Key>"+
						        	"<Value>"+creationDate+"</Value>"+
						        "</CustomField>"+

						        "<CustomField>"+
						            "<Key>adId</Key>"+
						            "<Value>"+ad.getId()+"</Value>"+
						        "</CustomField>"+
						            
						    "</CustomFields>"+
						    "<Resubscribe>true</Resubscribe>"+
						    "<RestartSubscriptionBasedAutoresponders>true</RestartSubscriptionBasedAutoresponders>"+
						"</Subscriber>";


		}
			
		}
	

	
	return body ;
}


public List<Ad> get15Ad() throws IllegalArgumentException {
	List<Ad> records =  dao.ofy().query(Ad.class).filter("hasImage", true).order("-creationDate").limit(15).list();	
	return records;
}

public List<Ad> getPremiumAds() throws IllegalArgumentException {
	List<Ad> records =  dao.ofy().query(Ad.class).filter("status", "A").filter("hasImage", true).filter("searchPriority", 1).limit(50).list();	

	return records;
}

public List<Ad> getAllAds(int cursor) throws IllegalArgumentException {
	List<Ad> records =  dao.ofy().query(Ad.class).filter("status", "A").filter("hasImage", true).order("searchPriority").offset(cursor).limit(15).list();	
	return records;
}

public List<Ad> getAdsByDiv1(int div1) throws IllegalArgumentException {
	List<Ad> records =  dao.ofy().query(Ad.class).filter("stateId", div1).list();	
	return records;
}

public List<Ad> getAdsByFilter(String propertyType, boolean sale, int div1) throws IllegalArgumentException {
	List<Ad> records = null;

	if (sale) 	 records =  dao.ofy().query(Ad.class).filter("status", "A").filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1).order("searchPriority").list();	
	else records =  dao.ofy().query(Ad.class).filter("status", "A").filter("propertyType", propertyType).filter("rent", !sale).filter("stateId", div1).order("searchPriority").list();

	return records;
}

public List<Ad> getAdsByFilter(String propertyType, boolean sale, int div1, int div2) throws IllegalArgumentException {
	List<Ad> records = null;

	if (sale) 	 records =  dao.ofy().query(Ad.class).filter("status", "A").filter("propertyType", propertyType).filter("sale", sale).filter("stateId", div1).filter("countyId", div2).order("searchPriority").list();	
	else records =  dao.ofy().query(Ad.class).filter("status", "A").filter("propertyType", propertyType).filter("rent", !sale).filter("stateId", div1).filter("countyId", div2).order("searchPriority").list();

	return records;
}


}
