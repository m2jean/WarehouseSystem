package importData;

import importDataService.ExportDataService;

import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.ExportPO;
import PO.UserPO;
import enums.ResultMessage;
import enums.Status;

public class ExportDataStub implements ExportDataService{
	public String add(ExportPO po){
		return "00001";
	}
	
	public ResultMessage delete(ExportPO po){
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage update(ExportPO po){
		return ResultMessage.SUCCESS;
	}
	
	public ExportPO get(ExportPO po){
		return new ExportPO(null, null, null, null, null, null, null, null, 0, null, Status.DRAFT, false);
	}
	
	public ArrayList<ExportPO> get(String startTime, String endTime){
		return new ArrayList<ExportPO>();
	}
	
	public ArrayList<ExportPO> getAllExport(){
		return new ArrayList<ExportPO>();
	}

	@Override
	public ArrayList<ExportPO> getDraft(UserPO user) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ExportPO> getPassed() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ExportPO> getAllExport(UserPO user) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ExportPO> getSubmitted() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
