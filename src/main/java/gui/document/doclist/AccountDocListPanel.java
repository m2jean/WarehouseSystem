package gui.document.doclist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import accountBL.CheckInBL;
import accountBL.CheckOutBL;
import accountBL.CostBL;
import businesslogic.userbl.UserBL;
import VO.CheckInVO;
import VO.CheckOutVO;
import VO.CostVO;
import enums.DataMessage;
import enums.DocumentType;
import enums.ResultMessage;
import gui.MainFrame;
import gui.ResultDialog;
import gui.document.account.CheckInDialog_Add;
import gui.document.account.CheckInDocPanel;
import gui.document.account.CheckOutDialog_Add;
import gui.document.account.CheckOutDocPanel;
import gui.document.cash.CashDialog_Add;
import gui.document.cash.CashDocPanel;
import gui.document.component.DocPanel;


public class AccountDocListPanel extends PersonalDocListPanel {

	private static final long serialVersionUID = 1721216430186557700L;
	/**
	 * Create the panel.
	 */
	public AccountDocListPanel() {
		JButton btn_receipt = new JButton("添加收款单");
		btn_receipt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ResultDialog dia = new CheckInDialog_Add(MainFrame.mf);
				dia.setVisible(true);
				ResultMessage result = ResultDialog.getResultAndDispose(dia);
				checkResult(result, "收款单");
			}			
		});
		btn_receipt.setEnabled(true);
		pnl_func.add(btn_receipt);
		
		JButton btn_cash = new JButton("添加现金费用单");
		btn_cash.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ResultDialog dia = new CashDialog_Add(MainFrame.mf);
				dia.setVisible(true);
				ResultMessage result = ResultDialog.getResultAndDispose(dia);
				checkResult(result, "现金费用单");
			}			
		});
		btn_cash.setEnabled(true);
		pnl_func.add(btn_cash);
		
		JButton btn_payment = new JButton("添加付款单");
		btn_payment.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ResultDialog dia = new CheckOutDialog_Add(MainFrame.mf);
				dia.setVisible(true);
				ResultMessage result = ResultDialog.getResultAndDispose(dia);
				checkResult(result, "付款单");
			}			
		});
		btn_payment.setEnabled(true);
		pnl_func.add(btn_payment);
		
		buildList();
	}

	@Override
	public void buildList() {
		this.setList(getAccountLists());
	}
	
	public ArrayList<DocPanel> getAccountLists(){
		ArrayList<DocPanel> doclist = new ArrayList<DocPanel>();
		
		ArrayList<CheckInVO> checkinlist = getCheckInList();
		for(CheckInVO vo:checkinlist)
			doclist.add(new CheckInDocPanel(vo, DocPanel.PERSONAL, this));
		
		ArrayList<CheckOutVO> checkoutlist = getCheckOutList();
		for(CheckOutVO vo:checkoutlist)
			doclist.add(new CheckOutDocPanel(vo, DocPanel.PERSONAL, this));
		
		ArrayList<CostVO> cashlist = getCashList();
		for(CostVO vo:cashlist)
			doclist.add(new CashDocPanel(vo, DocPanel.PERSONAL, this));
		
		return doclist;
	}
	
	private static ArrayList<CheckInVO> getCheckInList(){
		DataMessage<ArrayList<CheckInVO>> checkinlist = new CheckInBL().getMy(new UserBL().getCurrent());
		switch(checkinlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			return checkinlist.data;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取收款单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取收款单失败，未知错误！");
			break;
		}
		return new ArrayList<CheckInVO>(0);
	}
	private static ArrayList<CheckOutVO> getCheckOutList(){
		DataMessage<ArrayList<CheckOutVO>> checkoutlist = new CheckOutBL().getMy(new UserBL().getCurrent());
		switch(checkoutlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			return checkoutlist.data;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取付款单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取付款单失败，未知错误！");
			break;
		}
		return new ArrayList<CheckOutVO>(0);
	}
	private static ArrayList<CostVO> getCashList(){
		DataMessage<ArrayList<CostVO>> cashlist = new CostBL().getMy(new UserBL().getCurrent());
		switch(cashlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			return cashlist.data;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取现金费用单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取现金费用单失败，未知错误！");
			break;
		}
		return new ArrayList<CostVO>(0);
	}

	@Override
	protected DocumentType[] getDocmentTypes() {
		return new DocumentType[]{DocumentType.ALL,DocumentType.CHECKIN,DocumentType.CHECKOUT,DocumentType.CASH};
	}
}
