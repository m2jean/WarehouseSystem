package productData;

import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.ProductPO;
import PO.SpecialProductPO;
import enums.ResultMessage;
import productBL.CostChange;
import productData.ProductDataStub;
import productDataService.ProductDataService;

public class ProductDataStub implements ProductDataService{
	public String add(ProductPO po){
		return "00001";
	}
	
	public ResultMessage delete(ProductPO po){
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage update(ProductPO po){
		return ResultMessage.SUCCESS;
	}
	
	public ProductPO get(ProductPO po){
		return new ProductPO(null, null, null, 0, 0, 0, 0, 0, null, null, 0, 0);
		
	}
	
	public ArrayList<ProductPO> get(String id, String name){
		return new ArrayList<ProductPO>();
	}
	
	public ArrayList<ProductPO> getAllProduct(){
		return new ArrayList<ProductPO>();
	}

	@Override
	public ArrayList<CostChange> getCostChange() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addSpecial(SpecialProductPO po) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage deleteSpecial(SpecialProductPO po)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage updateSpecial(SpecialProductPO po)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SpecialProductPO> getAllSpecial() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpecialProductPO getSpecial(SpecialProductPO po)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
