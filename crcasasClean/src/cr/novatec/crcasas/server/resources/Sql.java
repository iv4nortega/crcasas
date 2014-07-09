package cr.novatec.crcasas.server.resources;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.api.utils.SystemProperty;

import com.googlecode.objectify.Query;

import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.client.database.Person;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;


import java.sql.*;

public class Sql {
	// 173.194.107.56
	public static final String MYSQL_IP = "173.194.107.56:3306";
	public static final String INSERT_CRCASAS = "INSERT IGNORE INTO crcasas ";
	public static final String INSERT_PERSON = "INSERT IGNORE INTO person ";

	public static final String PROJECT_ID = "ticocloud";
	public static final String INSTANCE_NAME = "mash";
	public static final String DATABASE_NAME = "mydb";

	public static final long LIMIT_MILLIS = 1000 * 25; // provide a little leeway

	public static final long LIMIT_COUNT = 500; // provide a little leeway

	
	public Connection conn;
	
	public PreparedStatement updateStatement = null;
	


   
	DataBaseServicesImpl db = new DataBaseServicesImpl();

    
	public Sql() {
		
		connect();
	}
	
	public void connect() {
	    String url = null;
        try {
  	      if (SystemProperty.environment.value() ==
		          SystemProperty.Environment.Value.Production) {
		        // Load the class that provides the new "jdbc:google:mysql://" prefix.
					Class.forName("com.mysql.jdbc.GoogleDriver");
					
			        url = "jdbc:google:mysql://your-project-id:your-instance-name/guestbook?user=root";	

		        url = "jdbc:google:mysql://"+PROJECT_ID+":"+INSTANCE_NAME+"/"+DATABASE_NAME + "?user=root";
		        
		      } else {
		        // Local MySQL instance to use during development.
		        Class.forName("com.mysql.jdbc.Driver");
		        url = "jdbc:mysql://"+MYSQL_IP+"/"+DATABASE_NAME+"?user=root&password=papausenin";
		      }        	
        	
        	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        try {
			conn = DriverManager.getConnection(url);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			
			e.printStackTrace();
		}

        
	}

	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

    public void moveAds(String cursorStr) throws SQLException {
        String updateAd =
    			INSERT_CRCASAS +
    			"(adid, pubDate, updateDate, fbId, phone, phone2)"+
    			" VALUES(?,?,?,?,?,?)";   	
    	try {
    		
    		int count = 0;    		
			updateStatement = conn.prepareStatement(updateAd);
    		Query<Ad> query = db.getActiveAdsQuery();
    		if (cursorStr !=null) query.startCursor(Cursor.fromWebSafeString(cursorStr));
    		QueryResultIterator<Ad> iterator = query.iterator();
    		while (iterator.hasNext()) {
    			Ad ad = iterator.next();
    			if ( (ad!=null) &&  ( ad.getSourceKey() != null ) ) {
    				updateStatement.setString(1, ad.getSourceKey());
    			    java.sql.Date sqlDate = new java.sql.Date(ad.getPubDate().getTime());
    				updateStatement.setDate(2, sqlDate);
    				
    			    sqlDate = new java.sql.Date(ad.getUpdateDate().getTime());
    				updateStatement.setDate(3, sqlDate);    				
    				
    				updateStatement.setString(4, db.getPersonByAd(ad).getFbId());    
    				
    				
    				String phone = "";
    				String phone2 = "";
    				if (ad.getPhone()!= null) {
    					phone = ad.getPhone().replaceAll("[^\\d]", "");
    					if (phone.startsWith("506")) {
    						phone = phone.substring(3);
    					}
    					if (phone.length()>8) {
    						phone2 = phone.substring(8);

    						phone = phone.substring(0, 8);
    						if (phone2.length()>8) {
    							if (phone2.startsWith("506")) {
    								phone2 = phone2.substring(3);
    							}
    							if (phone2.length()>8) {
    								phone2 = phone2.substring(0,8);
    							}
    							
    						}
    					}
    				}

    				updateStatement.setString(5, phone);   
    				updateStatement.setString(6, phone2);   

    				
    				updateStatement.executeUpdate();
    				conn.commit();
    				count++;
    			}
                if (count > LIMIT_COUNT) {
                	Cursor cursor  = iterator.getCursor();
                	Queue queue = QueueFactory.getDefaultQueue();
                	TaskOptions taskOptions = TaskOptions.Builder.withUrl("/tasks/mysql")
                           .method(Method.GET)
                	           .header("Host", BackendServiceFactory.getBackendService().getBackendAddress("backend01"))
                	           .param("table", "crcasas")
                	           .param("cursor", cursor.toWebSafeString());
                	 queue.add(taskOptions);
                	 break;
                }
    		}    		
    		
    	}  catch (SQLException e) {
			db.saveError(e.getMessage(), e.getLocalizedMessage(), "sql.java", "sqlexception");    		
			e.printStackTrace();
		} finally {
	        if (updateStatement != null) {
				updateStatement.close();
	        }			
		}
    	


    	
    };
    
    
    public void movePersons(String cursorStr) throws SQLException   {
       String updatePerson =
			    			INSERT_PERSON +
			    			"(name, member, agent, phone, phone2, email, company, fbId)"+
			    			" VALUES(?,?,?,?,?,?,?,?)";
    	
    	try {
    		updateStatement = conn.prepareStatement(updatePerson);
    		int count = 0;
    		Query<Person> query = db.getPersonQuery();
    		if (cursorStr !=null) query.startCursor(Cursor.fromWebSafeString(cursorStr));
    		QueryResultIterator<Person> iterator = query.iterator();
    		
    		String previousFbId = "";
       	 
    		while (iterator.hasNext()) {
    			Person person = iterator.next();	
    			
    			
    			
    			if ((person.getFbId()!=null) && (!person.getFbId().equals(previousFbId))) {
    				
    				previousFbId = person.getFbId();
             		 
        			if (  db.hasAdsByFbId(person.getFbId())  ) {
        				
        				if ((person.getFirstName() !=null) && (person.getLastName() !=null)) updateStatement.setString(1, person.getFirstName()+" " +person.getLastName());
        				else updateStatement.setString(1, "");
        				if (person.getMember() !=null) updateStatement.setBoolean(2, person.getMember());
        				else updateStatement.setBoolean(2, false);
        				if (person.getAgent() !=null) updateStatement.setBoolean(3, person.getAgent());
        				else updateStatement.setBoolean(3, false);
        				if (person.getPhone() !=null) updateStatement.setString(4, person.getMobile());
        				else updateStatement.setString(4, "");
        				if (person.getPhone() !=null) updateStatement.setString(5, person.getPhone());
        				else updateStatement.setString(5, "");        				
        				if (person.getEmail() !=null) updateStatement.setString(6, person.getEmail());  
        				else updateStatement.setString(6, "");
        				if (person.getOrganization() !=null) updateStatement.setString(7, person.getOrganization());  	
        				else updateStatement.setString(7, ""); 
        				if (person.getFbId() !=null) {
        					updateStatement.setString(8, person.getFbId());  
            				updateStatement.executeUpdate();
            				conn.commit();
            				count++;
        				}


        			}
                    if (count > LIMIT_COUNT) {
 
                    	Cursor cursor  = iterator.getCursor();
                    	Queue queue = QueueFactory.getDefaultQueue();
                    	TaskOptions taskOptions = TaskOptions.Builder.withUrl("/tasks/mysql")
                               .method(Method.GET)
                    	           .header("Host", BackendServiceFactory.getBackendService().getBackendAddress("backend01"))
                    	           .param("table", "person")
                    	           .param("cursor", cursor.toWebSafeString());
                    	 queue.add(taskOptions);
                    	 break;
                    }
    				
    			}
    			
    			
    		}
    		
		} catch (SQLException e) {
			db.saveError(e.getMessage(), e.getLocalizedMessage(), "sql.java", "sqlexception");
			e.printStackTrace();
		} finally {
	        if (updateStatement != null) {
				updateStatement.close();
	        }			
		}
    };
    
	
	public void execStm(String action, String cursorStr) throws SQLException {
		// long startTime = System.currentTimeMillis();
        // if (System.currentTimeMillis()-startTime > LIMIT_MILLIS) {

		if (action.equals("crcasas")) moveAds(cursorStr);
		if (action.equals("person")) movePersons(cursorStr);
	    
	}

}
