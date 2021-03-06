package buy;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;


public class Observation extends UnicastRemoteObject implements IObservation{
	private Map<String, IStatus> components;

	public Observation(int carroserieMark,String carroserieDescription, int moteurMark,String moteurDescription, int roueMark,String roueDescription) throws RemoteException {
		this.components = new HashMap<String, IStatus>();
		components.put("Carroserie", new Status(carroserieMark,carroserieDescription));
		components.put("Moteur", new Status(moteurMark,moteurDescription));
		components.put("Roue", new Status(roueMark,roueDescription));
	}

	public Map<String, IStatus> getComponents() throws RemoteException {
		return components;
	}

	public void setComponents(Map<String, IStatus> components) throws RemoteException {
		this.components = components;
	}
	
	public int getCarroserieMark() throws RemoteException {
		return components.get("Carroserie").getMark();
	}
	public String getCarroserieDescription() throws RemoteException {
		return components.get("Carroserie").getDescription();
	}
	public int getMoteurMark() throws RemoteException {
		return components.get("Moteur").getMark();
	}
	public String getMoteurDescription() throws RemoteException {
		return components.get("Moteur").getDescription();
	}
	public int getRoueMark() throws RemoteException {
		return components.get("Roue").getMark();
	}
	public String getRoueDescription() throws RemoteException {
		return components.get("Roue").getDescription();
	}

}
