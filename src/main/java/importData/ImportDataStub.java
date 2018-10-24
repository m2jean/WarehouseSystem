package importData;

import importDataService.ImportDataService;

import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.ImportPO;
import PO.UserPO;
import enums.ResultMessage;
import enums.Status;

public class ImportDataStub implements ImportDataService{
	
	public String add(ImportPO po){
		return "00001";
	}
	
	public ResultMessage delete(ImportPO po){
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage update(ImportPO po){
		return ResultMessage.SUCCESS;
	}
	
	public ImportPO get(ImportPO po){
		return new ImportPO(null, null, null, null, null, null, null, null, Status.DRAFT, false);
	}
	
	public ArrayList<ImportPO> get(String startTime, String endTime){
		return new ArrayList<ImportPO>();
	}
	
	public ArrayList<ImportPO> getAllImport(){
		return new ArrayList<ImportPO>();
	}

	@Override
	public ArrayList<ImportPO> getPassed() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ImportPO> getDraft(UserPO user) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ImportPO> getSubmitted() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ImportPO> getAllImport(UserPO user) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
