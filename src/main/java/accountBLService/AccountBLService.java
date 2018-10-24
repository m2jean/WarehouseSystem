package accountBLService;

import java.util.ArrayList;

import enums.DataMessage;
import enums.ResultMessage;
import VO.AccountVO;

public interface AccountBLService {
	public DataMessage<Double> getBalance(AccountVO vo);
	public ResultMessage addAccount(AccountVO vo);
	public ResultMessage delAccount(AccountVO vo);
	public ResultMessage updAccount(AccountVO vo);
	public DataMessage<ArrayList<AccountVO>> findAccount(String id,String name);
	public DataMessage<AccountVO> findAccount(String id);
	public DataMessage<ArrayList<AccountVO>> getAll();
}
