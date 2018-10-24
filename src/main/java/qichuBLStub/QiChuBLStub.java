package qichuBLStub;


import VO.QiChuVO;
import enums.DataMessage;
import enums.ResultMessage;
import qiChuBLService.QiChuBLService;
import qiChuDataService.QiChuDataService;
import qichuDataStub.QiChuDataStub;

public class QiChuBLStub implements QiChuBLService{
	QiChuDataService qichu=new QiChuDataStub();

	public ResultMessage Initial() {
		return ResultMessage.SUCCESS;
	}

	public DataMessage<QiChuVO> Check(QiChuVO vo) {
		return new DataMessage<QiChuVO>(ResultMessage.SUCCESS);
	}
}
