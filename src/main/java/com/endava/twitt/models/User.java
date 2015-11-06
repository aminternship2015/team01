package com.endava.twitt.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  
public class User {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user", unique = true, nullable = false, insertable = false, updatable = false)
	private int id;	

	@Size(min = 1, max = 254)
	@Column(name = "FirstName")
	private String firstName;

	@Size(min = 1, max = 254)
	@Column(name = "LastName")
	private String lastName;

	@Column(name = "Role", insertable = false)
	private String role;
	
	
	@OneToMany(targetEntity = Tweets.class, mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderBy("publishedDate DESC")
	@JsonBackReference
	private List<Tweets> tweet;

	/*@OneToMany(targetEntity = Follow.class, mappedBy = "userFollowed", fetch = FetchType.EAGER, cascade = CascadeType.ALL)	
	private Set<Follow> followed;*/

	
	@Id
	@Size(min = 4, max = 254)
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	@Column(name = "Email")
	private String email;
	@NotEmpty
	@Size(min = 8, max = 25)
	@Column(name = "Password")
	private String password;

	/*----------------------------------------------------*/
	
/*	public String toString() {
	    return "[" + getEmail() 
	        + ", " + getFirstName()
	        + ", " + getLastName()
	        + ", " + getId()
	        + "]";
	  }*/
	
	
	
	public User(){
        //id=0;
    }
     
    public User(int id, String firstName, String lastName, String email, String role, List<Tweets> tweet){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.tweet = tweet;
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Tweets> getTweet() {
		return tweet;
	}

	public void setTweet(List<Tweets> tweet) {
		this.tweet = tweet;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	 /*@Override
	    public String toString() {
	        return "User [id=" + id + ", name=" + name + ", age=" + age
	                + ", salary=" + salary + "]";
	    }*/

}
