package cr.novatec.crcasas.client.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Indexed;

public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    Long id;
	
	private String firstName;
	private String lastName;
	private String organization;
	@Indexed private String email;
	@Indexed private String email2;
	private String phone;
	private String mobile;
	private String gender; // M = male ; F = Female;
	private Boolean agent; 
	private Boolean member; 
	private String memberStatus; // A = active ; I = Inactive ; R = retired; D = payment due;
	private Boolean premium;
	private Integer category; //  1 = basico  2 etc
	private String fbId;
	private Date  startDate;
	private Date startPayment;  // fecha del primer pago
	private Date nextPayment;  // fecha del siguiente pago
	private Integer paymentSatus; // 0 = pagado 1 = pending 2 = overdue 
	private Integer visits;
	private Date lastVisits;
	
	public Person() {
		this.agent = false;
		this.visits = 0;
		this.member = false;
		this.premium = false;
		this.startDate = new Date();
		this.lastVisits = new Date();
	}
	
	public Person(	String firstName,	String lastName,	String email,
					String email2,	String phone,	String mobile,	String gender) {	

		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email2);
		this.setEmail2(email2);
		this.setPhone(phone);
		this.setMobile(mobile);
		this.setGender(gender);
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getEmail2() {
		return email2;
	}



	public void setEmail2(String email2) {
		this.email2 = email2;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getMobile() {
		return mobile;
	}



	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getAgent() {
		return agent;
	}

	public void setAgent(Boolean agent) {
		this.agent = agent;
	}

	public Boolean getMember() {
		return member;
	}

	public void setMember(Boolean member) {
		this.member = member;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public Boolean getPremium() {
		return premium;
	}

	public void setPremium(Boolean premium) {
		this.premium = premium;
	}



	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@Override
	public String toString() {
		return "Person [email=" + email + ", email2=" + email2 + ", firstName="
				+ firstName + ", gender=" + gender + ", id=" + id
				+ ", lastName=" + lastName + ", mobile=" + mobile + ", phone="
				+ phone + "]";
	}

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	public Date getStartPayment() {
		return startPayment;
	}

	public void setStartPayment(Date startPayment) {
		this.startPayment = startPayment;
	}

	public Date getNextPayment() {
		return nextPayment;
	}

	public void setNextPayment(Date nextPayment) {
		this.nextPayment = nextPayment;
	}

	public Integer getPaymentSatus() {
		return paymentSatus;
	}

	public void setPaymentSatus(Integer paymentSatus) {
		this.paymentSatus = paymentSatus;
	}

	public Integer getVisits() {
		return visits;
	}

	public void setVisits(Integer visits) {
		this.visits = visits;
		
		
	}

	public Date getLastVisits() {
		return lastVisits;
	}

	public void setLastVisits(Date lastVisits) {
		this.lastVisits = lastVisits;
	}


	
}
