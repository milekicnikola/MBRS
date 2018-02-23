package ejb.Banking.Accounts;
import java.util.Date;
import ejb.Banking.Companies.Bank;
import ejb.Banking.People.Person;
import enumerations.PaymentMethod;
import ejb.Banking.Money.Currency;
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
@Table(name = "Account")
public class Account extends EntityAbstract implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
		@Column(name="code", unique = true, nullable = false)
		private Integer code;
         
		@Column(name="est", unique = false, nullable = true)
		private Date est;
         
		@ManyToOne
		@JoinColumn(name="bank", referencedColumnName="id", nullable = false)
		private Bank bank;
         
    	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "account")
  		private Set<Person> person = new HashSet<Person>();
         
		@Column(name="paymentMethod", unique = false, nullable = false)
		private PaymentMethod paymentMethod;
         
		@ManyToOne
		@JoinColumn(name="currency", referencedColumnName="id", nullable = false)
		private Currency currency;
         
		@Column(name="active", unique = false, nullable = false)
		private Boolean active;
         
    	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "account")
  		private Set<Company> company = new HashSet<Company>();
         

	public Account(){
	}

    	public void addPerson(Person entity) {
    		if(entity != null) {
    			if(!person.contains(entity)) {
    				entity.setAccount(this);
    				person.add(entity);
    			}
    		}
    	}
    	
    	public void removePerson(Person entity) {
    		if(entity != null) {
    			if(person.contains(entity)) {
					entity.setAccount(null);
    				person.remove(entity);
    			}
    		}
    	}
    	public void addCompany(Company entity) {
    		if(entity != null) {
    			if(!company.contains(entity)) {
    				entity.setAccount(this);
    				company.add(entity);
    			}
    		}
    	}
    	
    	public void removeCompany(Company entity) {
    		if(entity != null) {
    			if(company.contains(entity)) {
					entity.setAccount(null);
    				company.remove(entity);
    			}
    		}
    	}

      public Integer getCode(){
           return code;
      }
      
      public void setCode(Integer code){
           this.code = code;
      }
      
      public Date getEst(){
           return est;
      }
      
      public void setEst(Date est){
           this.est = est;
      }
      
      public Bank getBank(){
           return bank;
      }
      
      public void setBank(Bank bank){
           this.bank = bank;
      }
      
      public Set<Person> getPerson(){
           return person;
      }
      
      public void setPerson( Set<Person> person){
           this.person = person;
      }
      
      public PaymentMethod getPaymentMethod(){
           return paymentMethod;
      }
      
      public void setPaymentMethod(PaymentMethod paymentMethod){
           this.paymentMethod = paymentMethod;
      }
      
      public Currency getCurrency(){
           return currency;
      }
      
      public void setCurrency(Currency currency){
           this.currency = currency;
      }
      
      public Boolean getActive(){
           return active;
      }
      
      public void setActive(Boolean active){
           this.active = active;
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String formatedDate = dateFormat.format(est);
		list.add(formatedDate);			
		if(bank != null){
			list.add(bank.toString());
		}else{
			list.add("");
		}
		list.add(paymentMethod.toString());
		if(currency != null){
			list.add(currency.toString());
		}else{
			list.add("");
		}
		list.add(active.toString());
		 
		 return list.toArray();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(code + " ");
		result.append(est + " ");
		result.append(paymentMethod + " ");
		result.append(active + " ");
		
		return result.toString();
	}

}
