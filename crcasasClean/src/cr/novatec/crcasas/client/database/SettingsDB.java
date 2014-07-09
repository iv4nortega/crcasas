package cr.novatec.crcasas.client.database;

import java.io.Serializable;

import javax.persistence.Id;


public class SettingsDB implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    Long id;
	
	private Long mainAd1;
	private Long mainAd2;
	private Long mainAd3;
	private Long mainAd4;


	
	public SettingsDB() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMainAd1() {
		return mainAd1;
	}

	public void setMainAd1(Long mainAd1) {
		this.mainAd1 = mainAd1;
	}

	public Long getMainAd2() {
		return mainAd2;
	}

	public void setMainAd2(Long mainAd2) {
		this.mainAd2 = mainAd2;
	}

	public Long getMainAd3() {
		return mainAd3;
	}

	public void setMainAd3(Long mainAd3) {
		this.mainAd3 = mainAd3;
	}

	public Long getMainAd4() {
		return mainAd4;
	}

	public void setMainAd4(Long mainAd4) {
		this.mainAd4 = mainAd4;
	}


	
}
