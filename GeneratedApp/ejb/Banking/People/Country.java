package ejb.Banking.People;
import ejb.Banking.People.City;
import ejb.Banking.Money.Currency;
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
@Table(name = "Country")
public class Country extends EntityAbstract implements Serializable {
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
         
    	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "country")
  		private Set<City> city = new HashSet<City>();
         
    	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "country")
  		private Set<Currency> currency = new HashSet<Currency>();
         

	public Country(){
	}

    	public void addCity(City entity) {
    		if(entity != null) {
    			if(!city.contains(entity)) {
    				entity.setCountry(this);
    				city.add(entity);
    			}
    		}
    	}
    	
    	public void removeCity(City entity) {
    		if(entity != null) {
    			if(city.contains(entity)) {
					entity.setCountry(null);
    				city.remove(entity);
    			}
    		}
    	}
    	public void addCurrency(Currency entity) {
    		if(entity != null) {
    			if(!currency.contains(entity)) {
    				entity.setCountry(this);
    				currency.add(entity);
    			}
    		}
    	}
    	
    	public void removeCurrency(Currency entity) {
    		if(entity != null) {
    			if(currency.contains(entity)) {
					entity.setCountry(null);
    				currency.remove(entity);
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
      
      public Set<City> getCity(){
           return city;
      }
      
      public void setCity( Set<City> city){
           this.city = city;
      }
      
      public Set<Currency> getCurrency(){
           return currency;
      }
      
      public void setCurrency( Set<Currency> currency){
           this.currency = currency;
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
