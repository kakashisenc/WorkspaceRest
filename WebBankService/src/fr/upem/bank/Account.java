package fr.upem.bank;



import java.math.BigDecimal;
import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;
import org.tempuri.*;

public class Account {
	private String currency;
	private String mail;
	private String password;
	private String firstName;
	private String lastName;
	private String adress;
	private String phoneNumber;
	private double balance;

	public Account() {
		
	}
	
	public Account(String currency, String mail, String password, String firstName, String lastName, String adress, String phoneNumber, double balance) {
		this.currency = currency;
		this.mail = mail;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.adress = adress;
		this.phoneNumber = phoneNumber;
		this.balance = balance;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getBalance() {
		return balance;
	}
	
	public double convert(String cur1, String cur2, double amount) throws ServiceException, RemoteException {
		ConverterSoap currencyConverter = new ConverterLocator().getConverterSoap();
		BigDecimal taux = currencyConverter.getConversionRate(cur1, cur2, currencyConverter.getLastUpdateDate());
		return Math.round(amount * taux.doubleValue());
	}
	
	public boolean withdraw(double amount) throws RemoteException, ServiceException  {
		double newAmount = convert("EUR", currency, amount);
		if (balance - newAmount < 0) {
			return false;
		}
		balance = balance - newAmount;
		return true;
	}

	public boolean deposit(double amount) throws RemoteException, ServiceException{
		double newAmount = convert("EUR", currency, amount);
		balance = balance + newAmount;
		return true;
	}
}
