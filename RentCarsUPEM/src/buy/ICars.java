package buy;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

public interface ICars extends Remote {
	public ICar[] getCars() throws RemoteException;
	public boolean addCar(String model, int price, int pricelocation) throws RemoteException;
	public boolean rentVehicule(int id,int employee) throws RemoteException, MalformedURLException, NotBoundException, ServiceException;
	public boolean buyCar(int id) throws RemoteException;
	public double sendCarPrice(int id) throws RemoteException;
	public String getSelectedCarsModel(int carID) throws RemoteException;
	public double getSelectedCarsPrice(int carID) throws RemoteException;
	public int getSelectedCarsHBR(int carID) throws RemoteException;
	
}
