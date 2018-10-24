package gui.document.doclist;


import importBL.ExportBL;
import importBL.ImportBL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

import progressBL.ProgressBL;
import progressBLService.ProgressBLService;
import saleBL.ReturnBL;
import saleBL.SaleBL;
import VO.CheckInVO;
import VO.CheckOutVO;
import VO.CostVO;
import VO.ExportVO;
import VO.ImportVO;
import VO.OverflowVO;
import VO.PresentVO;
import VO.ReturnVO;
import VO.SaleVO;
import accountBL.CheckInBL;
import accountBL.CheckOutBL;
import accountBL.CostBL;
import enums.DataMessage;
import enums.DocumentType;
import enums.ResultMessage;
import gui.MainFrame;
import gui.datecombobox.DateComboBoxManager;
import gui.document.account.CheckInDocPanel;
import gui.document.account.CheckOutDocPanel;
import gui.document.cash.CashDocPanel;
import gui.document.component.DocPanel;
import gui.document.imports.ImportDocPanel_Export;
import gui.document.imports.ImportDocPanel_Import;
import gui.document.sales.ReturnDocPanel;
import gui.document.sales.SaleDocPanel;
import gui.document.search.DocSearchPanel;
import gui.document.search.DocSearchPanel_Progress;

public class ProgressPanel extends DocListPanel {

	private static final long serialVersionUID = 3681142088826519501L;
	private JButton btn_check;
	private JButton btn_red;
	private JButton btn_redcopy;
	/**
	 * Create the panel.
	 */
	public ProgressPanel() {		
		btn_check = new JButton("查看");
		pnl_func.add(btn_check);
		btn_check.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lastSelected.check();
			}			
		});
		btn_check.setEnabled(false);

		btn_red = new JButton("红冲");
		btn_red.setEnabled(false);
		btn_red.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int success = 0;
				for(DocPanel doc:getSelected()){
					if(doc.getRedResult() == ResultMessage.SUCCESS){
						success++;
					}
				}
				JOptionPane.showMessageDialog(MainFrame.mf,"成功红冲"+String.valueOf(success)+"条单据，"
						+"失败"+String.valueOf(getSelectedNum()-success)+"条。");
				if(success!=0)
					buildList();
			}			
		});
		pnl_func.add(btn_red);
		
		btn_redcopy = new JButton("红冲并复制");
		btn_redcopy.setEnabled(false);
		btn_redcopy.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lastSelected.redcopy();
			}			
		});
		pnl_func.add(btn_redcopy);
		
		JButton btnNewButton = new JButton("导出");
		btnNewButton.addActionListener(new ExportListener());
		pnl_func.add(btnNewButton);
		
		buildList();
	}

	@Override
	protected DocSearchPanel getSearchPanel() {
		return new DocSearchPanel_Progress(this);
	}

	@Override
	public void buildList() {
		ArrayList<DocPanel> doclist = new ArrayList<DocPanel>();
		
		DataMessage<ArrayList<ImportVO>> importlist1 = new ImportBL().getAllPassed();
		switch(importlist1.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(ImportVO vo:importlist1.data)
				doclist.add(new ImportDocPanel_Import(vo, DocPanel.RED, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取进货单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取进货单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<ExportVO>> exportlist1 = new ExportBL().getAllPassed();
		switch(exportlist1.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(ExportVO vo:exportlist1.data)
				doclist.add(new ImportDocPanel_Export(vo, DocPanel.RED, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取退货单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取退货单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<SaleVO>> salelist = new SaleBL().AllPass();
		switch(salelist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(SaleVO vo:salelist.data)
				doclist.add(new SaleDocPanel(vo, DocPanel.RED, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取销售单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取销售单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<ReturnVO>> returnlist = new ReturnBL().AllPass();
		switch(returnlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(ReturnVO vo:returnlist.data)
				doclist.add(new ReturnDocPanel(vo, DocPanel.RED, this));;
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取销售退货单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取销售退货单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<CheckInVO>> checkinlist = new CheckInBL().AllPass();
		switch(checkinlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(CheckInVO vo:checkinlist.data)
				doclist.add(new CheckInDocPanel(vo, DocPanel.RED, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取收款单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取收款单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<CheckOutVO>> checkoutlist = new CheckOutBL().AllPass();
		switch(checkoutlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(CheckOutVO vo:checkoutlist.data)
				doclist.add(new CheckOutDocPanel(vo, DocPanel.RED, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取付款单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取付款单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<CostVO>> cashlist = new CostBL().AllPass();
		switch(cashlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(CostVO vo:cashlist.data)
				doclist.add(new CashDocPanel(vo, DocPanel.RED, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取现金费用单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取现金费用单失败，未知错误！");
			break;
		}
		
		/*/
		DataMessage<ArrayList<PresentVO>> presentlist = new InventoryBL().getAllPassedPresent();
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
		
		setList(doclist);
	}

	@Override
	public void setList(ArrayList<DocPanel> list) {
		btn_check.setEnabled(false);
		btn_red.setEnabled(false);
		btn_redcopy.setEnabled(false);
		Collections.sort(list);
		addItemListenerForAll(list,new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(selectedNum <= 0){
					btn_check.setEnabled(false);
					btn_red.setEnabled(false);
					btn_redcopy.setEnabled(false);
				}
				else if(selectedNum == 1){
					btn_check.setEnabled(true);
					btn_red.setEnabled(true);
					btn_redcopy.setEnabled(true);
				}
				else{
					btn_check.setEnabled(false);
					btn_red.setEnabled(true);
					btn_redcopy.setEnabled(false);
				}
				if(hasRed){
					btn_red.setEnabled(false);
					btn_redcopy.setEnabled(false);
				}
			}		
		});
		super.setList(list);
	}
	
	class ExportListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(!list.isEmpty()){
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );
				if(chooser.showSaveDialog(MainFrame.mf) == JFileChooser.APPROVE_OPTION){
					File f = new File(chooser.getSelectedFile().getPath()+"\\"
							+DateComboBoxManager.getFormattedCalendar(DateComboBoxManager.current));
					f.mkdir();
					String path = chooser.getSelectedFile().getPath()+"\\"
								+DateComboBoxManager.getFormattedCalendar(DateComboBoxManager.current)+"\\";
					
					ProgressBLService bl = new ProgressBL();
					ArrayList<CostVO> cost = new ArrayList<CostVO>();
					ArrayList<CheckInVO> checkin = new ArrayList<CheckInVO>();
					ArrayList<CheckOutVO> checkout = new ArrayList<CheckOutVO>();
					ArrayList<ExportVO> export = new ArrayList<ExportVO>();
					ArrayList<ImportVO> imports = new ArrayList<ImportVO>();
					ArrayList<OverflowVO> overflow = new ArrayList<OverflowVO>();
					ArrayList<PresentVO> present = new ArrayList<PresentVO>();
					ArrayList<ReturnVO> returns = new ArrayList<ReturnVO>();
					ArrayList<SaleVO> sale = new ArrayList<SaleVO>();
					for(DocPanel dp:list){
						switch(dp.getType()){
						case CASH:
							cost.add((CostVO)dp.getVO());
							break;
						case CHECKIN:
							checkin.add((CheckInVO)dp.getVO());
							break;
						case CHECKOUT:
							checkout.add((CheckOutVO) dp.getVO());
							break;
						case EXPORT:
							export.add((ExportVO) dp.getVO());
							break;
						case IMPORT:
							imports.add((ImportVO) dp.getVO());
							break;
						case OVERFLOW:
							overflow.add((OverflowVO) dp.getVO());
							break;
						case PRESENT:
							present.add((PresentVO) dp.getVO());
							break;
						case RETURN:
							returns.add((ReturnVO) dp.getVO());
							break;
						case SALES:
							sale.add((SaleVO) dp.getVO());
							break;
						default:
							break;
						}
					}
					
					int fail = 0;
					if(!cost.isEmpty())
						if(bl.CostToExcel(cost, path+"Cost.xls")!=ResultMessage.SUCCESS)
							fail++;
					if(!checkin.isEmpty())
						if(bl.CheckInToExcel(checkin, path+"CheckIn.xls")!=ResultMessage.SUCCESS)
							fail++;
					if(!checkout.isEmpty())
						if(bl.CheckOutToExcel(checkout, path+"CheckOut.xls")!=ResultMessage.SUCCESS)
							fail++;
					if(!export.isEmpty())
						if(bl.ExportToExcel(export, path+"Export.xls")!=ResultMessage.SUCCESS)
							fail++;
					if(!imports.isEmpty())
						if(bl.ImportToExcel(imports, path+"Import.xls")!=ResultMessage.SUCCESS)
							fail++;
					if(!overflow.isEmpty())
						if(bl.OverflowToExcel(overflow, path+"Overflow.xls")!=ResultMessage.SUCCESS)
							fail++;
					if(!present.isEmpty())
						if(bl.PresentToExcel(present, path+"Present.xls")!=ResultMessage.SUCCESS)
							fail++;
					if(!returns.isEmpty())
						if(bl.ReturnToExcel(returns, path+"Return.xls")!=ResultMessage.SUCCESS)
							fail++;
					if(!sale.isEmpty())
						if(bl.SaleToExcel(sale, path+"Sales.xls")!=ResultMessage.SUCCESS)
							fail++;
					if(fail == 0)
						JOptionPane.showMessageDialog(MainFrame.mf,"导出成功！");
					else
						JOptionPane.showMessageDialog(MainFrame.mf,"导出出现异常！");
				}
			}
		}
	}

	@Override
	protected DocumentType[] getDocmentTypes() {
		return new DocumentType[]{DocumentType.ALL,DocumentType.CASH,DocumentType.CHECKIN,DocumentType.CHECKOUT,
				DocumentType.EXPORT,DocumentType.IMPORT,DocumentType.OVERFLOW,DocumentType.PRESENT,DocumentType.RETURN,
				DocumentType.SALES};
	}
}
