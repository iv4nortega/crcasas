package cr.novatec.crcasas.server.rpc;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;


public class DAO extends DAOBase
{
    static {
        ObjectifyService.register(cr.novatec.crcasas.client.database.Ad.class);
        ObjectifyService.register(cr.novatec.crcasas.client.database.AdWeb.class);

        ObjectifyService.register(cr.novatec.crcasas.client.database.AdHistory.class);
        ObjectifyService.register(cr.novatec.crcasas.client.database.Person.class);
        ObjectifyService.register(cr.novatec.crcasas.client.database.ImageDB.class);
        ObjectifyService.register(cr.novatec.crcasas.client.database.ImageDataBase.class);
        ObjectifyService.register(cr.novatec.crcasas.client.database.Division3.class);
        ObjectifyService.register(cr.novatec.crcasas.client.database.Division2.class);
        ObjectifyService.register(cr.novatec.crcasas.client.database.Division1.class);
        ObjectifyService.register(cr.novatec.crcasas.client.database.CsvFile.class);
        ObjectifyService.register(cr.novatec.crcasas.client.database.Messages.class);      
        ObjectifyService.register(cr.novatec.crcasas.client.database.AdCursor.class);       
        ObjectifyService.register(cr.novatec.crcasas.client.database.NacionDivision3.class);
        ObjectifyService.register(cr.novatec.crcasas.client.database.EncuentraDivision3.class);

        ObjectifyService.register(cr.novatec.crcasas.client.database.Jobs.class);

        ObjectifyService.register(cr.novatec.crcasas.client.database.Nacion3.class);
        ObjectifyService.register(cr.novatec.crcasas.client.database.Encuentra3.class);
        ObjectifyService.register(cr.novatec.crcasas.client.database.Inmobi3.class);

        ObjectifyService.register(cr.novatec.crcasas.client.database.Errors.class);

        ObjectifyService.register(cr.novatec.crcasas.client.database.Global.class);

        ObjectifyService.register(cr.novatec.crcasas.client.database.SettingsDB.class);

        ObjectifyService.register(cr.novatec.crcasas.client.database.Alerts.class);

        ObjectifyService.register(cr.novatec.crcasas.client.database.Likes.class);

    }
    
}
