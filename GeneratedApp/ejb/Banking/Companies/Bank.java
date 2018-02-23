package ejb.Banking.Companies;
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
@Table(name = "Bank")
public class Bank extends EntityAbstract implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
		@Column(name="swift", unique = true, nullable = false)
		private String swift;
         
		@Column(name="name", unique = false, nullable = false)
		private String name;
         
		@Column(name="address", unique = false, nullable = false)
		private String address;
         
		@Column(name="email", unique = true, nullable = false)
		private String email;
         
		@Column(name="phoneNumber", unique = true, nullable = false)
		private Integer phoneNumber;
         
		@ManyToOne
		@JoinColumn(name="city", referencedColumnName="id", nullable = false)
		private City city;
         
    	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "bank")
  		private Set<Account> account = new HashSet<Account>();
         

	public Bank(){
	}

    	public void addAccount(Account entity) {
    		if(entity != null) {
    			if(!account.contains(entity)) {
    				entity.setBank(this);
    				account.add(entity);
    			}
    		}
    	}
    	
    	public void removeAccount(Account entity) {
    		if(entity != null) {
    			if(account.contains(entity)) {
					entity.setBank(null);
    				account.remove(entity);
    			}
    		}
    	}

      public String getSwift(){
           return swift;
      }
      
      public void setSwift(String swift){
           this.swift = swift;
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
      
      public City getCity(){
           return city;
      }
      
      public void setCity(City city){
           this.city = city;
      }
      
      public Set<Account> getAccount(){
           return account;
      }
      
      public void setAccount( Set<Account> account){
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
		list.add(swift.toString());
		list.add(name.toString());
		list.add(address.toString());
		list.add(email.toString());
		list.add(phoneNumber.toString());
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
		
		result.append(swift + " ");
		result.append(name + " ");
		result.append(address + " ");
		result.append(email + " ");
		result.append(phoneNumber + " ");
		
		return result.toString();
	}

}
