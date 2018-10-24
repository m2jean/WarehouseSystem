package qiChuBLService;


import enums.DataMessage;
import enums.ResultMessage;
import VO.QiChuVO;

public interface QiChuBLService {
	public ResultMessage Initial();
	public DataMessage<QiChuVO> Check(QiChuVO vo);
}
