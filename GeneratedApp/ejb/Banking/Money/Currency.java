package ejb.Banking.Money;
import ejb.Banking.People.Country;
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
@Table(name = "Currency")
public class Currency extends EntityAbstract implements Serializable {
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
         
		@Column(name="name", unique = true, nullable = true)
		private String name;
         
		@Column(name="domicile", unique = false, nullable = false)
		private Boolean domicile;
         
		@ManyToOne
		@JoinColumn(name="country", referencedColumnName="id", nullable = false)
		private Country country;
         
    	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "currency")
  		private Set<Account> account = new HashSet<Account>();
         

	public Currency(){
	}

    	public void addAccount(Account entity) {
    		if(entity != null) {
    			if(!account.contains(entity)) {
    				entity.setCurrency(this);
    				account.add(entity);
    			}
    		}
    	}
    	
    	public void removeAccount(Account entity) {
    		if(entity != null) {
    			if(account.contains(entity)) {
					entity.setCurrency(null);
    				account.remove(entity);
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
      
      public Boolean getDomicile(){
           return domicile;
      }
      
      public void setDomicile(Boolean domicile){
           this.domicile = domicile;
      }
      
      public Country getCountry(){
           return country;
      }
      
      public void setCountry(Country country){
           this.country = country;
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
		list.add(code.toString());
		list.add(name.toString());
		list.add(domicile.toString());
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
		result.append(domicile + " ");
		
		return result.toString();
	}

}
