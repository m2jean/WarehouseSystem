package inventoryBL;

import VO.InventoryVO;
import VO.OverflowVO;
import VO.PresentVO;
import VO.SnapshotVO;
import enums.DataMessage;
import enums.ResultMessage;
import enums.Status;
import inventoryBLService.InventoryBLService;
import inventoryData.InventoryDataStub;
import PO.PresentPO;
import PO.OverflowPO;

import java.util.*;

public class InventoryBLStub implements InventoryBLService{
	
	
	public DataMessage<SnapshotVO> stocking(){
		return new DataMessage(new SnapshotVO(null, null, 0, null));
	}
	
	public ResultMessage exportStocking(SnapshotVO vo, String filepath){
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage newPresentTable(PresentVO vo){
		new InventoryDataStub().addPresent(new PresentPO(null, null, null, null, null));
		return ResultMessage.SUCCESS;
	}
	
	public DataMessage<PresentVO> getPresentTable(PresentVO vo){
		new InventoryDataStub().getPresent(new PresentPO(null, null, null, null, null));
		return new DataMessage<PresentVO>(ResultMessage.ITEM_NOT_EXIST);
	}
	
	public DataMessage<ArrayList<PresentVO>> getAllPresentTable(){
		new InventoryDataStub().getAllPresent();
		return new DataMessage(new ArrayList<PresentVO>());
	}
	
	public ResultMessage newOverflow(OverflowVO vo){
		new InventoryDataStub().addOverflow(new OverflowPO("id"));
		return ResultMessage.SUCCESS;
	}
	
	public DataMessage<OverflowVO> getOverflow(OverflowVO vo){
		new InventoryDataStub().addOverflow(new OverflowPO("abc"));
		return new DataMessage(new OverflowVO(null));
	}
	
	public DataMessage<ArrayList<OverflowVO>> getAllOverflow(){
		new InventoryDataStub().getAllOverflow();
		return new DataMessage(new ArrayList<OverflowVO>());
	}
	
	public ResultMessage updateOverflow(OverflowVO vo){
		new InventoryDataStub().updateOverflow(new OverflowPO(null, null, null, null, 0, 0, null, Status.DRAFT));
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage updatePresentTable(PresentVO vo){
		new InventoryDataStub().updatePresent(new PresentPO(null, null, null, null, null));
		return ResultMessage.SUCCESS;
	}
	
	public DataMessage<ArrayList<OverflowVO>> getOverflow(String startTime, String endTime, String warehouse){
		return new DataMessage<ArrayList<OverflowVO>>(new ArrayList<OverflowVO>());
	}
	
	public DataMessage<ArrayList<PresentVO>> getPresent(String startTime, String endTime){
		return new DataMessage<ArrayList<PresentVO>>(new ArrayList<PresentVO>());
	}

	@Override
	public DataMessage<InventoryVO> check(String startTime, String endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<PresentVO>> getAllPassedPresent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<OverflowVO>> getAllPassedOverflow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<String> newPresentReturnID(PresentVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<PresentVO>> getPresentDraftByCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<PresentVO>> getAllPresentByCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<OverflowVO>> getOverflowDraftByCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataMessage<ArrayList<OverflowVO>> getAllOverflowByCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage updateOverflowDraft(OverflowVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage deleteOverflowDraft(OverflowVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage updatePresentDraft(PresentVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage deletePresentDraft(PresentVO vo) {
		// TODO Auto-generated method stub
		return null;
	}


}
