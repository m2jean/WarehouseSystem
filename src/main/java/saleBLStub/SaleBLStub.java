package saleBLStub;

import java.util.ArrayList;







import productBL.ProductBLStub;
import customerBL.CustomerBLStub;
import VO.PresentVO;
import VO.SaleVO;
import VO.UserVO;
import enums.DataMessage;
import enums.ResultMessage;
import saleBL.SaleLog;
import saleBLService.SaleBLService;
import saleDataStub.SaleDataStub;

public class SaleBLStub implements SaleBLService{

	public ResultMessage addSale(SaleVO vo,PresentVO present) {
		new SaleDataStub().newSale(null);
		
		return ResultMessage.SUCCESS;
	}

	public DataMessage<ArrayList<SaleVO>> getSales(String stime,String etime, String good, String cus,
			String yewu, String cangku) {
		new SaleDataStub().find(cangku,cangku, cangku, cangku, cangku, cangku);
		return null;
	}

	public ResultMessage updateSales(SaleVO vo) {
		new SaleDataStub().update(null);
		new CustomerBLStub().updateCustomer(null);
		new ProductBLStub().updateProduct(null);
		return ResultMessage.SUCCESS;
	}

	public DataMessage<ArrayList<SaleVO>> AllSale() {
		new SaleDataStub().getAll();
		return null;
	}

	@Override
	public DataMessage<SaleVO> fillSale(SaleVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<SaleVO>> AllDraft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<SaleVO>> AllPass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage delSale(SaleVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<SaleVO>> getMy(UserVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<SaleVO> getSale(SaleVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage shenpi(SaleVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<SaleVO>> AllSubmit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<SaleLog>> getMingxi(String stime, String etime,
			String good, String cus, String yewu, String cangku) {
		// TODO Auto-generated method stub
		return null;
	}

}
