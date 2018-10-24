package productDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

import productBL.CostChange;
import PO.ProductPO;
import PO.SpecialProductPO;
import enums.ResultMessage;

public interface ProductDataService extends Remote{
	public String add(ProductPO po) throws RemoteException;
	public ResultMessage delete(ProductPO po) throws RemoteException;
	public ResultMessage update(ProductPO po) throws RemoteException;
	public ProductPO get(ProductPO po) throws RemoteException;                //int id
	public ArrayList<ProductPO> get(String id, String name) throws RemoteException;//keyword 
	public ArrayList<ProductPO> getAllProduct() throws RemoteException;
	public ArrayList<CostChange> getCostChange() throws RemoteException;
	
	public String addSpecial(SpecialProductPO po) throws RemoteException;
	public ResultMessage deleteSpecial(SpecialProductPO po) throws RemoteException;
	public ResultMessage updateSpecial(SpecialProductPO po) throws RemoteException;
	public SpecialProductPO getSpecial(SpecialProductPO po) throws RemoteException;
	public ArrayList<SpecialProductPO> getAllSpecial() throws RemoteException;
}
