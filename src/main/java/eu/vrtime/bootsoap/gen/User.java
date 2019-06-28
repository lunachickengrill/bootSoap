package eu.vrtime.bootsoap.gen;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@Column
	private String customerId;

	@Column
	private String firstname;

	@Column
	private String lastname;

	public User(String customerId, String firstname, String lastname) {
		this.customerId = customerId;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Long getId() {
		return Id;
	}

	@Override
	public String toString() {
		return "User [customerId=" + customerId + ", firstname=" + firstname + ", lastname=" + lastname + "]";
	}

	User() {

	}

}
