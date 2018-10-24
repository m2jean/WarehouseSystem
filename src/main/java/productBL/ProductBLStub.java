package productBL;

import java.util.ArrayList;

import VO.ProductVO;
import VO.SaleItemVO;
import VO.SpecialProductVO;
import enums.DataMessage;
import enums.ResultMessage;
import productData.ProductDataStub;
import productBLService.ProductBLService;
import PO.*;

public class ProductBLStub implements ProductBLService{
	public ResultMessage addProduct(ProductVO vo){
		new ProductDataStub().add(new ProductPO(null, null, null, 0, 0, 0, 0, 0, null, null, 0, 0));
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage deleteProduct(ProductVO vo){
		new ProductDataStub().delete(new ProductPO(null, null, null, 0, 0, 0, 0, 0, null, null, 0, 0));
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage updateProduct(ProductVO vo){
		new ProductDataStub().update(new ProductPO(null, null, null, 0, 0, 0, 0, 0, null, null, 0, 0));
		return ResultMessage.SUCCESS;
	}
	
	public DataMessage<ProductVO> getProduct(ProductVO vo){
		new ProductDataStub().get(new ProductPO(null, null, null, 0, 0, 0, 0, 0, null, null, 0, 0));
		return new DataMessage(new ProductVO(null, null, null, 0, 0, 0, 0, 0, null, null, 0, 0));
	}
	
	public DataMessage<ArrayList<ProductVO>> getProduct(String id, String name){
		new ProductDataStub().get(id, name);
		return new DataMessage(new ArrayList<ProductVO>());
	}
	
	public DataMessage<ArrayList<ProductVO>> getProductList(){
		new ProductDataStub().getAllProduct();
		return new DataMessage(new ArrayList<ProductVO>());
	}

	@Override
	public DataMessage<ArrayList<CostChange>> getCostChange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage addSpecialProduct(SpecialProductVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage deleteSpecialProduct(SpecialProductVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage updateSpecialProduct(SpecialProductVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<SpecialProductVO>> getAllSpecialProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<SaleItemVO>> getAllSaleItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<SpecialProductVO> getSpecialProduct(SpecialProductVO vo) {
		// TODO Auto-generated method stub
		return null;
	}


}
