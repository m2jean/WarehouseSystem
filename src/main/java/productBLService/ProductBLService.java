package productBLService;

import java.util.*;

import productBL.CostChange;
import VO.ProductVO;
import VO.SaleItemVO;
import VO.SpecialProductVO;
import enums.DataMessage;
import enums.ResultMessage;

public interface ProductBLService {
	public ResultMessage addProduct(ProductVO vo);
	public ResultMessage deleteProduct(ProductVO vo);
	public ResultMessage updateProduct(ProductVO vo);
	public DataMessage<ProductVO> getProduct(ProductVO vo);
	public DataMessage<ArrayList<ProductVO>> getProduct(String id, String name);// keyword
	public DataMessage<ArrayList<ProductVO>> getProductList();
	public DataMessage<ArrayList<CostChange>> getCostChange();
	public ResultMessage addSpecialProduct(SpecialProductVO vo);
	public ResultMessage deleteSpecialProduct(SpecialProductVO vo);
	public ResultMessage updateSpecialProduct(SpecialProductVO vo);
	public DataMessage<SpecialProductVO> getSpecialProduct(SpecialProductVO vo);
	public DataMessage<ArrayList<SpecialProductVO>> getAllSpecialProduct();
	public DataMessage<ArrayList<SaleItemVO>> getAllSaleItem();
}
