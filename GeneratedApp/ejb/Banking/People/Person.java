package ejb.Banking.People;
import ejb.Banking.People.City;
import ejb.Banking.Accounts.Account;
import framework.*;

import java.text.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Person")
public class Person extends EntityAbstract implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
		@Column(name="JMBG", unique = false, nullable = false)
		private String JMBG;
         
		@Column(name="firstName", unique = false, nullable = false)
		private String firstName;
         
		@Column(name="lastName", unique = false, nullable = false)
		private String lastName;
         
		@Column(name="address", unique = false, nullable = false)
		private String address;
         
		@Column(name="phoneNumber", unique = false, nullable = false)
		private Integer phoneNumber;
         
		@ManyToOne
		@JoinColumn(name="city", referencedColumnName="id", nullable = false)
		private City city;
         
		@ManyToOne
		@JoinColumn(name="account", referencedColumnName="id", nullable = false)
		private Account account;
         

	public Person(){
	}


      public String getJMBG(){
           return JMBG;
      }
      
      public void setJMBG(String JMBG){
           this.JMBG = JMBG;
      }
      
      public String getFirstName(){
           return firstName;
      }
      
      public void setFirstName(String firstName){
           this.firstName = firstName;
      }
      
      public String getLastName(){
           return lastName;
      }
      
      public void setLastName(String lastName){
           this.lastName = lastName;
      }
      
      public String getAddress(){
           return address;
      }
      
      public void setAddress(String address){
           this.address = address;
      }
      
      public Integer getPhoneNumber(){
           return phoneNumber;
      }
      
      public void setPhoneNumber(Integer phoneNumber){
           this.phoneNumber = phoneNumber;
      }
      
      public City getCity(){
           return city;
      }
      
      public void setCity(City city){
           this.city = city;
      }
      
      public Account getAccount(){
           return account;
      }
      
      public void setAccount(Account account){
           this.account = account;
      }
      

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public Object[] getValues() {	
		List<Object> list = new ArrayList<Object>();
		
		list.add(id);		
		list.add(JMBG.toString());
		list.add(firstName.toString());
		list.add(lastName.toString());
		list.add(address.toString());
		list.add(phoneNumber.toString());
		if(city != null){
			list.add(city.toString());
		}else{
			list.add("");
		}
		if(account != null){
			list.add(account.toString());
		}else{
			list.add("");
		}
		 
		 return list.toArray();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(JMBG + " ");
		result.append(firstName + " ");
		result.append(lastName + " ");
		result.append(address + " ");
		result.append(phoneNumber + " ");
		
		return result.toString();
	}

}
