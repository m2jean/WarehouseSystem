package qiChuData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import PO.QiChuPO;
import enums.ResultMessage;
import qiChuDataService.QiChuDataService;

public class QiChuData extends UnicastRemoteObject implements QiChuDataService{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<QiChuPO> Qichu=new ArrayList<QiChuPO>();
    File file=new File("QiChu.ser");
    public QiChuData() throws RemoteException{
    	if(file.exists()){
		    try{
			    FileInputStream fis=new FileInputStream(file);
			    ObjectInputStream reader=new ObjectInputStream(fis);
			    while(fis.available()>0){
				    QiChuPO po=(QiChuPO)reader.readObject();
				    Qichu.add(po);
			    }
			    reader.close();
		    }catch(Exception e){
			    e.printStackTrace();
		    }
		}
    }

	public ResultMessage newQiChu(QiChuPO po)  throws RemoteException{
		try{
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(file));
			Qichu.add(po);
			for(QiChuPO i:Qichu){
				writer.writeObject(i);
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResultMessage.SUCCESS;
	}

	public QiChuPO findQiChu(String id)  throws RemoteException{
		for(QiChuPO i:Qichu){
			if(i.getID().equals(id)){
				return i;
			}
		}
		return null;
	}
}
