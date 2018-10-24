package qiChuDataService;


import java.rmi.Remote;
import java.rmi.RemoteException;

import enums.ResultMessage;
import PO.QiChuPO;

public interface QiChuDataService extends Remote{
	public ResultMessage newQiChu(QiChuPO po) throws RemoteException;
	public QiChuPO findQiChu(String id) throws RemoteException;
}
