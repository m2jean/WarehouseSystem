package dataservice.promotiondataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import enums.ResultMessage;
import enums.UserPermission;
import enums.VipLevel;
import PO.PromotionPO;

public interface PromotionDataService extends Remote{
	public ArrayList<PromotionPO> get() throws RemoteException;
	public PromotionPO get(String id) throws RemoteException;
	public ArrayList<PromotionPO> getUnexpired() throws RemoteException;
	public ArrayList<PromotionPO> getUnexpired(VipLevel level, double total) throws RemoteException;
	public String add(PromotionPO promotion) throws RemoteException;
	public ResultMessage delete(PromotionPO promotion) throws RemoteException;
	public ResultMessage update(PromotionPO promotion) throws RemoteException;
	public int getDiscountPermission(UserPermission user) throws RemoteException;
	public ResultMessage updateDiscountPermission(int[] array) throws RemoteException; 
}
