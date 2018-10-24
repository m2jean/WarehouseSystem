package saleBLStub;

import java.util.ArrayList;






import customerBL.CustomerBLStub;
import productBL.ProductBLStub;
import VO.ReturnVO;
import VO.UserVO;
import enums.DataMessage;
import enums.ResultMessage;
import saleBLService.ReturnBLService;
import saleDataStub.ReturnDataStub;

public class ReturnBLStub implements ReturnBLService{

	public ResultMessage addReturn(ReturnVO vo) {
		new ReturnDataStub().newReturn(null);
		return ResultMessage.SUCCESS;
	}

	public DataMessage<ReturnVO> getReturn(ReturnVO vo) {
		new ReturnDataStub().get(null);
		return new DataMessage<ReturnVO>(ResultMessage.SUCCESS);
	}

	public ResultMessage updateReturn(ReturnVO vo) {
		new ReturnDataStub().update(null);
		new CustomerBLStub().updateCustomer(null);
		new ProductBLStub().updateProduct(null);
		return ResultMessage.SUCCESS;
	}

	public DataMessage<ArrayList<ReturnVO>> AllReturn() {
		new ReturnDataStub().getAll();
		return new DataMessage<ArrayList<ReturnVO>>(ResultMessage.SUCCESS);
	}

	public DataMessage<ArrayList<ReturnVO>> getReturns(String stime,String etime, String good, String cus,
			String yewu, String cangku) {
		return new DataMessage<ArrayList<ReturnVO>>(ResultMessage.SUCCESS);
	}

	@Override
	public DataMessage<ArrayList<ReturnVO>> AllDraft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<ReturnVO>> AllPass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage delReturn(ReturnVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<ReturnVO>> getMy(UserVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage shenpi(ReturnVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<ReturnVO>> AllSubmit() {
		// TODO Auto-generated method stub
		return null;
	}

}
