package ejb.Banking.People;
import ejb.Banking.People.Country;
import ejb.Banking.People.Person;
import ejb.Banking.Companies.Bank;
import ejb.Banking.Companies.Company;
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
@Table(name = "City")
public class City extends EntityAbstract implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
		@Column(name="code", unique = true, nullable = false)
		private String code;
         
		@Column(name="name", unique = true, nullable = false)
		private String name;
         
		@ManyToOne
		@JoinColumn(name="country", referencedColumnName="id", nullable = false)
		private Country country;
         
    	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "city")
  		private Set<Person> person = new HashSet<Person>();
         
    	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "city")
  		private Set<Bank> bank = new HashSet<Bank>();
         
    	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "city")
  		private Set<Company> company = new HashSet<Company>();
         

	public City(){
	}

    	public void addPerson(Person entity) {
    		if(entity != null) {
    			if(!person.contains(entity)) {
    				entity.setCity(this);
    				person.add(entity);
    			}
    		}
    	}
    	
    	public void removePerson(Person entity) {
    		if(entity != null) {
    			if(person.contains(entity)) {
					entity.setCity(null);
    				person.remove(entity);
    			}
    		}
    	}
    	public void addBank(Bank entity) {
    		if(entity != null) {
    			if(!bank.contains(entity)) {
    				entity.setCity(this);
    				bank.add(entity);
    			}
    		}
    	}
    	
    	public void removeBank(Bank entity) {
    		if(entity != null) {
    			if(bank.contains(entity)) {
					entity.setCity(null);
    				bank.remove(entity);
    			}
    		}
    	}
    	public void addCompany(Company entity) {
    		if(entity != null) {
    			if(!company.contains(entity)) {
    				entity.setCity(this);
    				company.add(entity);
    			}
    		}
    	}
    	
    	public void removeCompany(Company entity) {
    		if(entity != null) {
    			if(company.contains(entity)) {
					entity.setCity(null);
    				company.remove(entity);
    			}
    		}
    	}

      public String getCode(){
           return code;
      }
      
      public void setCode(String code){
           this.code = code;
      }
      
      public String getName(){
           return name;
      }
      
      public void setName(String name){
           this.name = name;
      }
      
      public Country getCountry(){
           return country;
      }
      
      public void setCountry(Country country){
           this.country = country;
      }
      
      public Set<Person> getPerson(){
           return person;
      }
      
      public void setPerson( Set<Person> person){
           this.person = person;
      }
      
      public Set<Bank> getBank(){
           return bank;
      }
      
      public void setBank( Set<Bank> bank){
           this.bank = bank;
      }
      
      public Set<Company> getCompany(){
           return company;
      }
      
      public void setCompany( Set<Company> company){
           this.company = company;
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
		list.add(code.toString());
		list.add(name.toString());
		if(country != null){
			list.add(country.toString());
		}else{
			list.add("");
		}
		 
		 return list.toArray();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(code + " ");
		result.append(name + " ");
		
		return result.toString();
	}

}
