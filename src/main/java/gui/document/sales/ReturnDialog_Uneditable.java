package gui.document.sales;

import java.util.ArrayList;

import javax.swing.JPanel;

import gui.MainFrame;
import gui.inventory.InventoryLineItem;
import VO.ReturnVO;

public class ReturnDialog_Uneditable extends ReturnDialog {

	private static final long serialVersionUID = 50371541507420283L;

	public ReturnDialog_Uneditable(MainFrame parent, ReturnVO vo) {
		super(parent, vo);
		setTitle("查看销售退货单");
		
		txf_stock.setEditable(false);
		txa_remarks.setEditable(false);
		txf_clerk.setEditable(false);
		btn_save.setEnabled(false);
		btn_submit.setEnabled(false);
	}

	@Override
	protected ReturnLineItemPanel getItemPanel(ReturnDialog returnDialog,
			ArrayList<InventoryLineItem> list,
			ArrayList<InventoryLineItem> gifts, int[] limit) {
		return new ReturnLineItemPanel_Uneditable(this,list,gifts,limit,vo.getTotal());
	}
	
	@Override
	protected void addButtons(JPanel buttonPane){
	//DO NOTHING	
	}
}
