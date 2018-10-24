package inventoryBLService;

import java.util.*;

import VO.InventoryVO;
import VO.OverflowVO;
import VO.SnapshotVO;
import VO.PresentVO;
import enums.DataMessage;
import enums.ResultMessage;

public interface InventoryBLService {
	public DataMessage<InventoryVO> check(String startTime, String endTime);      //查看
	public DataMessage<SnapshotVO> stocking();                              //盘点
	public ResultMessage exportStocking(SnapshotVO vo, String filepath);
	
	
	public ResultMessage newPresentTable(PresentVO vo);
	public DataMessage<String> newPresentReturnID(PresentVO vo);
	public ResultMessage updatePresentDraft(PresentVO vo);
	public ResultMessage updatePresentTable(PresentVO vo);
	public ResultMessage deletePresentDraft(PresentVO vo);
	public DataMessage<PresentVO> getPresentTable(PresentVO vo);
	public DataMessage<ArrayList<PresentVO>> getAllPresentTable();
	public DataMessage<ArrayList<PresentVO>> getAllPassedPresent();
	public DataMessage<ArrayList<PresentVO>> getPresentDraftByCurrentUser();
	public DataMessage<ArrayList<PresentVO>> getAllPresentByCurrentUser();
	public DataMessage<ArrayList<PresentVO>> getPresent(String startTime, String endTime);

	
	public ResultMessage newOverflow(OverflowVO vo);
	public ResultMessage updateOverflowDraft(OverflowVO vo);
	public ResultMessage updateOverflow(OverflowVO vo);
	public ResultMessage deleteOverflowDraft(OverflowVO vo);
	public DataMessage<OverflowVO> getOverflow(OverflowVO vo);
	public DataMessage<ArrayList<OverflowVO>> getAllOverflow();
	public DataMessage<ArrayList<OverflowVO>> getAllPassedOverflow();
	public DataMessage<ArrayList<OverflowVO>> getOverflowDraftByCurrentUser();
	public DataMessage<ArrayList<OverflowVO>> getAllOverflowByCurrentUser();
	public DataMessage<ArrayList<OverflowVO>> getOverflow(String startTime, String endTime, String warehouse);

}
