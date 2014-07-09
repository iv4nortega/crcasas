package cr.novatec.crcasas.client.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;

public class ImageDataBase implements Serializable {

	private static final long serialVersionUID = 3L;

	@Id Long id;
	private @Indexed String imageKey;
    private @Indexed Key<Ad> adKey; // PIENSO QUE LO VOY A DEPRECAR POR LA FACILIDAD DD adId
    private @Indexed Long adId; 
   
	String imageUrl;

	Integer size;
	
	Date date;
	
	Date expirationDate;
	
	String contentType;
	
	String fileName;
	
	Integer displayOrder;
	
	String caption;
	
	String description;
	
	Integer width;
	
	Integer height;
	
	public ImageDataBase( )
	{
		
	}

	public String getKey() {
		return imageKey;
	}

	public void setKey(String key) {
		this.imageKey = key;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	
	public String getImageUrl(int width, int height) {
		
		return (isPortrait()) ? imageUrl+"=s"+height : imageUrl+"=s"+width;
	}
	
	public Boolean isPortrait() {
		if (getWidth()<=getHeight()) return true;
		else return false;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageKey() {
		return imageKey;
	}

	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}

	public Integer getSize() {
		if (size == null) setSize(0);
		System.out.println(size);
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Date getDate() {
		if (date == null) setDate(new Date());

		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getExpirationDate() {
		if (expirationDate == null) setExpirationDate(new Date());

		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getContentType() {
		if (contentType == null) setContentType("nd");

		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Integer getDisplayOrder() {
		return (displayOrder==null) ? 99 : displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getCaption() {
		return (caption==null) ? "" : caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Integer getWidth() {
		return (width == null) ? 0 : width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return (height == null) ? 0 : height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return (description !=null) ? description : "";
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
