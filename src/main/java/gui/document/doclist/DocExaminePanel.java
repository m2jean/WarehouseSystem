package gui.document.doclist;

import importBL.ExportBL;
import importBL.ImportBL;
import inventoryBL.InventoryBL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import enums.DataMessage;
import enums.DocumentType;
import enums.ResultMessage;
import gui.MainFrame;
import gui.document.account.CheckInDocPanel;
import gui.document.account.CheckOutDocPanel;
import gui.document.cash.CashDocPanel;
import gui.document.component.DocPanel;
import gui.document.imports.ImportDocPanel_Export;
import gui.document.imports.ImportDocPanel_Import;
import gui.document.overflow.OverflowDocPanel;
import gui.document.sales.ReturnDocPanel;
import gui.document.sales.SaleDocPanel;
import gui.document.search.DocSearchPanel;
import gui.document.search.DocSearchPanel_General;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import accountBL.CheckInBL;
import accountBL.CheckOutBL;
import accountBL.CostBL;
import saleBL.ReturnBL;
import saleBL.SaleBL;
import VO.CheckInVO;
import VO.CheckOutVO;
import VO.CostVO;
import VO.ExportVO;
import VO.ImportVO;
import VO.OverflowVO;
import VO.ReturnVO;
import VO.SaleVO;


public class DocExaminePanel extends DocListPanel {

	private static final long serialVersionUID = -978592272733725795L;
	private JButton btn_check;
	private JButton btn_pass;
	private JButton btn_deny;
	private JButton btn_examine;

	/**
	 * Create the panel.
	 */
	public DocExaminePanel() {
		btn_check = new JButton("查看");
		btn_check.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lastSelected.check();
			}			
		});
		
		btn_examine = new JButton("修改并审批");
		btn_examine.setActionCommand("deny");
		btn_examine.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lastSelected.examine();
			}			
		});
		pnl_func.add(btn_examine);
		btn_examine.setEnabled(false);
		
		
		pnl_func.add(btn_check);
		btn_check.setEnabled(false);
		
		btn_pass = new JButton("通过");
		btn_pass.setActionCommand("pass");
		btn_pass.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int success = 0;
				for(DocPanel doc:getSelected()){
					if(doc.getExaminationResult(true) == ResultMessage.SUCCESS){
						success++;
					}
				}
				if(success == getSelectedNum())
					MainFrame.mf.setMessage("成功审批通过"+String.valueOf(success)+"条单据");
				else
				MainFrame.mf.setMessage("成功审批通过"+String.valueOf(success)+"条单据，"
						+"失败"+String.valueOf(getSelectedNum()-success)+"条。");
				//if(success!=0)
				buildList();
			}			
		});
		pnl_func.add(btn_pass);
		btn_pass.setEnabled(false);
		
		btn_deny = new JButton("否决");
		btn_deny.setActionCommand("deny");
		btn_deny.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int success = 0;
				for(DocPanel doc:getSelected()){
					if(doc.getExaminationResult(false) == ResultMessage.SUCCESS){
						success++;
					}
				}
				if(success == getSelectedNum())
					MainFrame.mf.setMessage("成功否决通过"+String.valueOf(success)+"条单据");
				else
				MainFrame.mf.setMessage("成功否决通过"+String.valueOf(success)+"条单据，"
						+"失败"+String.valueOf(getSelectedNum()-success)+"条。");
				//if(success!=0)
				buildList();
			}			
		});
		pnl_func.add(btn_deny);
		btn_deny.setEnabled(false);
		
		buildList();
	}

	@Override
	public void buildList() {
		ArrayList<DocPanel> doclist = new ArrayList<DocPanel>();
		
		DataMessage<ArrayList<ImportVO>> importlist1 = new ImportBL().getImportList();
		switch(importlist1.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(ImportVO vo:importlist1.data)
				doclist.add(new ImportDocPanel_Import(vo, DocPanel.EXAMINE, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取进货单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取进货单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<ExportVO>> exportlist1 = new ExportBL().getExportList();
		switch(exportlist1.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(ExportVO vo:exportlist1.data)
				doclist.add(new ImportDocPanel_Export(vo, DocPanel.EXAMINE, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取退货单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取退货单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<SaleVO>> salelist = new SaleBL().AllSale();
		switch(salelist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(SaleVO vo:salelist.data)
				doclist.add(new SaleDocPanel(vo, DocPanel.EXAMINE, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取销售单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取销售单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<ReturnVO>> returnlist = new ReturnBL().AllReturn();
		switch(returnlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(ReturnVO vo:returnlist.data)
				doclist.add(new ReturnDocPanel(vo, DocPanel.EXAMINE, this));;
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取销售退货单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取销售退货单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<CheckInVO>> checkinlist = new CheckInBL().getAllReceipt();
		switch(checkinlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(CheckInVO vo:checkinlist.data)
				doclist.add(new CheckInDocPanel(vo, DocPanel.EXAMINE, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取收款单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取收款单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<CheckOutVO>> checkoutlist = new CheckOutBL().getAllPayment();
		switch(checkoutlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(CheckOutVO vo:checkoutlist.data)
				doclist.add(new CheckOutDocPanel(vo, DocPanel.EXAMINE, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取付款单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取付款单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<CostVO>> cashlist = new CostBL().getAllCost();
		switch(cashlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(CostVO vo:cashlist.data)
				doclist.add(new CashDocPanel(vo, DocPanel.EXAMINE, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取现金费用单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取现金费用单失败，未知错误！");
			break;
		}
		
		/*
		DataMessage<ArrayList<PresentVO>> presentlist = new InventoryBL().getAllPresentTable();
		switch(presentlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(PresentVO vo:presentlist.data)
				doclist.add(new PresentDocPanel(vo));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取赠送单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取赠送单失败，未知错误！");
			break;
		}*/
		
		DataMessage<ArrayList<OverflowVO>> overflowlist = new InventoryBL().getAllOverflow();
		switch(overflowlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(OverflowVO vo:overflowlist.data)
				doclist.add(new OverflowDocPanel(vo, DocPanel.EXAMINE, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取报损报溢单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取报损报溢单失败，未知错误！");
			break;
		}
		
		setList(doclist);
	}
	
	@Override
	public void setList(ArrayList<DocPanel> list){
		btn_check.setEnabled(false);
		btn_pass.setEnabled(false);
		btn_deny.setEnabled(false);
		btn_examine.setEnabled(false);
		addItemListenerForAll(list,new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(selectedNum <= 0){
					btn_check.setEnabled(false);
					btn_pass.setEnabled(false);
					btn_deny.setEnabled(false);
					btn_examine.setEnabled(false);
				}
				else if(selectedNum == 1){
					btn_check.setEnabled(true);
					btn_pass.setEnabled(true);
					btn_deny.setEnabled(true);
					btn_examine.setEnabled(true);
				}
				else{
					btn_check.setEnabled(false);
					btn_pass.setEnabled(true);
					btn_deny.setEnabled(true);
					btn_examine.setEnabled(false);
				}
				
				if(hasExamined){
					btn_pass.setEnabled(false);
					btn_deny.setEnabled(false);
					btn_examine.setEnabled(false);
				}
			}		
		});
		super.setList(list);
	}

	@Override
	protected DocSearchPanel getSearchPanel() {
		return new DocSearchPanel_General(this);
	}

	@Override
	protected DocumentType[] getDocmentTypes() {
		return new DocumentType[]{DocumentType.ALL,DocumentType.CASH,DocumentType.CHECKIN,DocumentType.CHECKOUT,
				DocumentType.EXPORT,DocumentType.IMPORT,DocumentType.OVERFLOW,DocumentType.PRESENT,DocumentType.RETURN,
				DocumentType.SALES};
	}

}
