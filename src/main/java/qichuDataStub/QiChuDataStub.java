package qichuDataStub;


import java.rmi.RemoteException;

import PO.QiChuPO;
import enums.ResultMessage;
import qiChuDataService.QiChuDataService;

public class QiChuDataStub implements QiChuDataService{

	public ResultMessage newQiChu(QiChuPO po)  throws RemoteException{
		return ResultMessage.SUCCESS;
	}

	public QiChuPO findQiChu(String id)  throws RemoteException{
		return new QiChuPO();
	}
}
