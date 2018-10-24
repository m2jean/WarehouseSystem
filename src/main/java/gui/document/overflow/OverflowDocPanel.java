package gui.document.overflow;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import VO.OverflowVO;
import enums.DocumentType;
import enums.ResultMessage;
import enums.Status;
import gui.GUIUtility;
import gui.MainFrame;
import gui.ResultDialog;
import gui.document.component.DocPanel;
import gui.document.doclist.DocListPanel;
import inventoryBL.InventoryBL;

public class OverflowDocPanel extends DocPanel {

	private static final long serialVersionUID = 5181412693765919320L;
	private OverflowVO vo;

	public OverflowDocPanel(OverflowVO vo, int func, DocListPanel pnl_list) {
		super(vo, func, pnl_list);
		this.vo = vo;
		
		pnl_content.setLayout(new BoxLayout(pnl_content, BoxLayout.Y_AXIS));		
		JPanel pnl_up = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
		JPanel pnl_down = new JPanel();
		
		pnl_content.add(Box.createVerticalGlue());
		pnl_content.add(pnl_up);
		pnl_content.add(Box.createVerticalGlue());
		pnl_content.add(pnl_down);
		pnl_content.add(Box.createVerticalGlue());
		
		JLabel lbl_stock = new JLabel("仓库：00001 数来宝");
		pnl_up.add(lbl_stock);
		
		JLabel lbl_product = new JLabel("商品：00001 数来宝");
		pnl_up.add(lbl_product);
		
		JLabel lbl_recordNum = new JLabel("记录数量：23444");
		pnl_down.add(lbl_recordNum);
		
		JLabel label_3 = new JLabel("——>");
		pnl_down.add(label_3);
		
		JLabel lbl_delta = new JLabel("+23");
		pnl_down.add(lbl_delta);
		
		JLabel label_4 = new JLabel("——>");
		pnl_down.add(label_4);
		
		JLabel lbl_actualNum = new JLabel("实际数量：23467");
		pnl_down.add(lbl_actualNum);
		
		lbl_stock.setText("仓库："+vo.getWarehouse());
		lbl_product.setText("商品："+vo.getProduct().getID()+" "+vo.getProduct().getName()+" "+vo.getProduct().getModel());
		lbl_recordNum.setText("记录数量："+String.valueOf(vo.getNumInSystem()));
		int delta = vo.getNumInWarehouse() - vo.getNumInSystem();
		lbl_delta.setText((delta<0?"":"+") +String.valueOf(delta));
		lbl_actualNum.setText("实际数量："+String.valueOf(vo.getNumInWarehouse()));
	}

	@Override
	public DocumentType getType() {
		return DocumentType.OVERFLOW;
	}

	@Override
	public void check() {
		new OverflowDialog_Uneditable(MainFrame.mf,vo).setVisible(true);
	}

	@Override
	public ResultMessage update() {
		return null;//do nothing
	}

	@Override
	public ResultMessage getDeletionResult() {
		return null;
	}

	@Override
	public ResultMessage examine() {
		ResultDialog dia = new OverflowDialog_Examine(MainFrame.mf,vo);
		dia.pack();
		dia.setResizable(false);
		GUIUtility.setCenter(dia);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getExaminationResult(boolean pass) {
	if(pass)
		vo.setStatus(Status.PASS);
	else
		vo.setStatus(Status.FAIL);

	return new InventoryBL().updateOverflow((OverflowVO) vo);
	}

	@Override
	public ResultMessage redcopy() {
		ResultDialog dia = new OverflowDialog_Red(MainFrame.mf,vo);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getRedResult() {
		vo.setHong(true);	
		vo.setStatus(Status.SUBMIT);
		return new InventoryBL().newOverflow(vo);
	}

}
