package ejb.Banking.Companies;
import ejb.Banking.Accounts.Account;
import ejb.Banking.People.City;
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
@Table(name = "Company")
public class Company extends EntityAbstract implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
		@Column(name="idNumber", unique = true, nullable = false)
		private Integer idNumber;
         
		@Column(name="name", unique = false, nullable = false)
		private String name;
         
		@Column(name="address", unique = false, nullable = false)
		private String address;
         
		@Column(name="email", unique = false, nullable = false)
		private String email;
         
		@Column(name="phoneNumber", unique = false, nullable = false)
		private Integer phoneNumber;
         
		@Column(name="description", unique = false, nullable = false)
		private String description;
         
		@ManyToOne
		@JoinColumn(name="account", referencedColumnName="id", nullable = false)
		private Account account;
         
		@ManyToOne
		@JoinColumn(name="city", referencedColumnName="id", nullable = false)
		private City city;
         

	public Company(){
	}


      public Integer getIdNumber(){
           return idNumber;
      }
      
      public void setIdNumber(Integer idNumber){
           this.idNumber = idNumber;
      }
      
      public String getName(){
           return name;
      }
      
      public void setName(String name){
           this.name = name;
      }
      
      public String getAddress(){
           return address;
      }
      
      public void setAddress(String address){
           this.address = address;
      }
      
      public String getEmail(){
           return email;
      }
      
      public void setEmail(String email){
           this.email = email;
      }
      
      public Integer getPhoneNumber(){
           return phoneNumber;
      }
      
      public void setPhoneNumber(Integer phoneNumber){
           this.phoneNumber = phoneNumber;
      }
      
      public String getDescription(){
           return description;
      }
      
      public void setDescription(String description){
           this.description = description;
      }
      
      public Account getAccount(){
           return account;
      }
      
      public void setAccount(Account account){
           this.account = account;
      }
      
      public City getCity(){
           return city;
      }
      
      public void setCity(City city){
           this.city = city;
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
		list.add(idNumber.toString());
		list.add(name.toString());
		list.add(address.toString());
		list.add(email.toString());
		list.add(phoneNumber.toString());
		list.add(description.toString());
		if(account != null){
			list.add(account.toString());
		}else{
			list.add("");
		}
		if(city != null){
			list.add(city.toString());
		}else{
			list.add("");
		}
		 
		 return list.toArray();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(idNumber + " ");
		result.append(name + " ");
		result.append(address + " ");
		result.append(email + " ");
		result.append(phoneNumber + " ");
		result.append(description + " ");
		
		return result.toString();
	}

}
