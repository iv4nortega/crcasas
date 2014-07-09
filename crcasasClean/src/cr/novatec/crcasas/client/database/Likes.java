package cr.novatec.crcasas.client.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Unindexed

public class Likes implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    Long id;
	
    private @Indexed Key<Person> member;
    private @Indexed Key<Ad> ad;
    private Date date;
	
	public Likes() {
	}

	

	public Likes(Key<Person> member, Key<Ad> ad, Date date) {
		super();
		this.member = member;
		this.ad = ad;
		this.date = date;
	}



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Key<Person> getMember() {
		return member;
	}


	public void setMember(Key<Person> member) {
		this.member = member;
	}


	public Key<Ad> getAd() {
		return ad;
	}


	public void setAd(Key<Ad> ad) {
		this.ad = ad;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}




}
