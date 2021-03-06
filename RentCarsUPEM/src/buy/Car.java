package buy;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Car extends UnicastRemoteObject implements ICar{
	int id;
	private final String model;
	private double price;
	private double pricelocation;
	private int globalMark;
	private int haveBeenRented = 0;
	private Optional<Integer> renter;
	private Map <Integer,IObservation> status = new HashMap<Integer,IObservation>();
	private List<Integer> waitingQueue;
	private int gone = 0;

	public Car(int idi,String model, double price, double pricelocation) throws RemoteException {
		id = idi;
		
		this.waitingQueue = new ArrayList<>();
		this.model = model;
		this.price = price;
		this.pricelocation = pricelocation;
		this.globalMark = 10;
		this.renter = Optional.ofNullable(-1);
		
	}
	
	public Map<Integer, IObservation> getStatus() throws RemoteException {
		return status;
	}
	public void setGone(int e) throws RemoteException{
		gone = e;
	}
	public int getGone() throws RemoteException{
		return gone;
	}

	public double getPricelocation() throws RemoteException {
		return pricelocation;
	}

	public void setPricelocation(double pricelocation) throws RemoteException {
		this.pricelocation = pricelocation;
	}

	public int getHaveBeenRented() throws RemoteException {
		return haveBeenRented;
	}

	public void setHaveBeenRented(int haveBeenRented) throws RemoteException {
		this.haveBeenRented = haveBeenRented;
	}

	public Optional<Integer> getRenter() throws RemoteException {
		return renter;
	}

	public void setRenter(Optional<Integer> renter) throws RemoteException {
		this.renter = renter;
	}
	
	public double getPrice() throws RemoteException {
		return price;
	}

	public void setPrice(double price) throws RemoteException {
		this.price = price;
	}
	
	public int getGlobalMark() throws RemoteException {
		return globalMark;
	}
	
	public int getID() throws RemoteException {
		return id;
	}
	public void setGlobalMark() throws RemoteException {
		globalMark = 0;
		int cmp = 0;
	
			for(IObservation observation : status.values()) {
				for (IStatus status : observation.getComponents().values()) {
					globalMark = globalMark + status.getMark();
					cmp ++;
				}
			}
		
		globalMark /= cmp;

	}
	
	public String getModel() throws RemoteException {
		return model;
	}
	
	public void addStatus(int employee, int carroserieMark,String carroserieDescription, int moteurMark,String moteurDescription, int roueMark,String roueDescription) throws RemoteException {
		if(status.containsKey(employee)) {
			status.remove(employee);
			status.put(employee,new Observation(carroserieMark,carroserieDescription,moteurMark,moteurDescription,roueMark,roueDescription));

		}
		else {
			status.put(employee,new Observation(carroserieMark,carroserieDescription,moteurMark,moteurDescription,roueMark,roueDescription));

			
		}
	}

	public boolean freeMe() throws RemoteException, MalformedURLException, NotBoundException{
		this.setGlobalMark();
		if (! waitingQueue.isEmpty()) {
			renter = Optional.ofNullable(waitingQueue.remove(0));
			IEmployees r = (IEmployees) Naming.lookup("rmi://localhost:2020/UPEMCorp");
			r.notifyUser(renter.get(), model);
			return true;

		}else {
			renter = Optional.empty();
		}
		return false;
	}

	public boolean rentMe(int userID) throws RemoteException, MalformedURLException, NotBoundException {
		if (!renter.isPresent()) {
			renter = Optional.ofNullable(userID);
				IEmployees r = (IEmployees) Naming.lookup("rmi://localhost:2020/UPEMCorp");
				r.notifyUser(userID, model);
				return true;
		}else {
			waitingQueue.add(userID);
		}
		return false;
	}
	
	

	


}
