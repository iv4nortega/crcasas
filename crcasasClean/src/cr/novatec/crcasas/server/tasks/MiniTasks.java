package cr.novatec.crcasas.server.tasks;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cr.novatec.crcasas.client.database.SettingsDB;
import cr.novatec.crcasas.server.resources.MyServerLibrary;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")

public class MiniTasks extends HttpServlet  {
	

	MyServerLibrary library = new MyServerLibrary();
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();

	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	 throws IOException {	
		
		SettingsDB settings = new SettingsDB();
		
		settings.setMainAd1(3349063l);
		settings.setMainAd2(3278063l);
		settings.setMainAd3(3293066l);
		settings.setMainAd4(2398085l);

		db.saveSettings(settings);
        
	 }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	 throws ServletException, IOException {
	 doGet(req, resp);
	 }	 
		
}	