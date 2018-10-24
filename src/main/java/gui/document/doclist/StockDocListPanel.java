package gui.document.doclist;

import enums.DataMessage;
import enums.DocumentType;
import enums.ResultMessage;
import gui.GUIUtility;
import gui.MainFrame;
import gui.ResultDialog;
import gui.document.component.DocPanel;
import gui.document.overflow.OverflowDialog_Add;
import gui.document.overflow.OverflowDocPanel;
import inventoryBL.InventoryBL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import VO.OverflowVO;


public class StockDocListPanel extends PersonalDocListPanel {

	private static final long serialVersionUID = 7018368833637101876L;

	/**
	 * Create the panel.
	 */
	public StockDocListPanel() {
		/*
		JButton btn_present = new JButton("添加库存赠送单");
		btn_present.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new PresentAddFrame().setVisible(true);;
			}
		});
		pnl_func.add(btn_present);*/
		
		JButton btn_overflow = new JButton("添加报损报溢单");
		btn_overflow.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ResultDialog dia = new OverflowDialog_Add(MainFrame.mf);
				dia.pack();
				dia.setResizable(false);
				GUIUtility.setCenter(dia);
				dia.setVisible(true);
				ResultMessage result = ResultDialog.getResultAndDispose(dia);
				if(result == ResultMessage.SUCCESS){
					MainFrame.mf.setMessage("报损报溢单添加成功");
				}else if(result != ResultMessage.CLOSE){
					MainFrame.mf.setError("报损报溢单添加失败");
				}
				buildList();
			}
		});
		pnl_func.add(btn_overflow);
		
		buildList();
	}

	@Override
	public void buildList() {
		ArrayList<DocPanel> doclist = getStockList();
		super.setList(doclist);
	}

	public ArrayList<DocPanel> getStockList(){
		ArrayList<DocPanel> doclist = new ArrayList<DocPanel>();
		/*
		DataMessage<ArrayList<PresentVO>> presentlist = new InventoryBL().getAllPresentByCurrentUser();
		switch(presentlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(PresentVO vo:presentlist.data)
				doclist.add(new PresentDocPanel(vo));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取赠品单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取赠品单失败，未知错误！");
			break;
		}*/
		
		DataMessage<ArrayList<OverflowVO>> flowlist = new InventoryBL().getAllOverflowByCurrentUser();
		switch(flowlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(OverflowVO vo:flowlist.data)
				doclist.add(new OverflowDocPanel(vo, DocPanel.PERSONAL, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取报损报溢单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取报损报溢单失败，未知错误！");
			break;
		}
		
		return doclist;
	}
	
	@Override
	protected DocumentType[] getDocmentTypes() {
		return new DocumentType[]{DocumentType.ALL,DocumentType.OVERFLOW,DocumentType.PRESENT};
	}
}
