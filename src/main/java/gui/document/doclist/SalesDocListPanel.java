package gui.document.doclist;

import gui.GUIUtility;
import gui.MainFrame;
import gui.ResultDialog;
import gui.document.component.DocPanel;
import gui.document.imports.ExportDialog_Add;
import gui.document.imports.ImportDialog_Add;
import gui.document.imports.ImportDocPanel_Export;
import gui.document.imports.ImportDocPanel_Import;
import gui.document.sales.ReturnDialog;
import gui.document.sales.ReturnDocPanel;
import gui.document.sales.SaleDialog;
import gui.document.sales.SaleDocPanel;
import importBL.ExportBL;
import importBL.ImportBL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import saleBL.ReturnBL;
import saleBL.SaleBL;
import VO.ExportVO;
import VO.ImportVO;
import VO.ReturnVO;
import VO.SaleVO;
import businesslogic.userbl.UserBL;
import enums.DataMessage;
import enums.DocumentType;
import enums.ResultMessage;
import enums.Status;


public class SalesDocListPanel extends PersonalDocListPanel {

	private static final long serialVersionUID = 6358938875000153266L;
	private JButton btn_import;
	private JButton btn_export;
	private JButton btn_sale;
	private JButton btn_return;

	/**
	 * Create the panel.
	 */
	public SalesDocListPanel() {
		super();
		
		btn_import = new JButton("添加进货单");
		btn_import.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ResultDialog dia = new ImportDialog_Add(MainFrame.mf);
				dia.pack();
				dia.setResizable(false);
				GUIUtility.setCenter(dia);
				dia.setVisible(true);
				ResultMessage result = ResultDialog.getResultAndDispose(dia);
				checkResult(result,"进货单");
			}		
		});
		pnl_func.add(btn_import);
		
		btn_export = new JButton("添加退货单");
		btn_export.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new ExportDialog_Add(MainFrame.mf,((ImportDocPanel_Import)lastSelected).getImportVO()).setVisible(true);
			}		
		});
		pnl_func.add(btn_export);
		btn_export.setEnabled(false);
		
		btn_sale = new JButton("添加销售单");
		btn_sale.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new SaleDialog(MainFrame.mf).setVisible(true);
			}		
		});
		pnl_func.add(btn_sale);
		
		btn_return = new JButton("添加销售退货单");
		btn_return.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new ReturnDialog(MainFrame.mf,((SaleDocPanel)lastSelected).getSaleVO()).setVisible(true);
			}		
		});
		pnl_func.add(btn_return);
		btn_return.setEnabled(false);
		
		buildList();
	}

	@Override
	public void buildList() {
		setList(getSalesLists());
	}
	
	public ArrayList<DocPanel> getSalesLists(){
		ArrayList<DocPanel> doclist = new ArrayList<DocPanel>();
		
		DataMessage<ArrayList<ImportVO>> importlist = new ImportBL().getImportByCurrentUser();
		switch(importlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(ImportVO vo:importlist.data)
				doclist.add(new ImportDocPanel_Import(vo, DocPanel.PERSONAL, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取进货单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取进货单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<ExportVO>> exportlist = new ExportBL().getExportByCurrentUser();
		switch(exportlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(ExportVO vo:exportlist.data)
				doclist.add(new ImportDocPanel_Export(vo, DocPanel.PERSONAL, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取退货单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取退货单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<SaleVO>> salelist = new SaleBL().getMy(new UserBL().getCurrent());
		switch(salelist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(SaleVO vo:salelist.data)
				doclist.add(new SaleDocPanel(vo, DocPanel.PERSONAL, this));
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取销售单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取销售单失败，未知错误！");
			break;
		}
		
		DataMessage<ArrayList<ReturnVO>> returnlist = new ReturnBL().getMy(new UserBL().getCurrent());
		switch(returnlist.resultMessage){
		case ITEM_NOT_EXIST:
		case IS_EMPTY:
			break;
		case SUCCESS:
			for(ReturnVO vo:returnlist.data)
				doclist.add(new ReturnDocPanel(vo, DocPanel.PERSONAL, this));;
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取销售退货单失败，通信失败！");
			break;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "获取销售退货单失败，未知错误！");
			break;
		}
		
		return doclist;
	}

	public void setList(ArrayList<DocPanel> list){
		addItemListenerForAll(list,new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(getSelectedNum() == 1 && lastSelected.getType() == DocumentType.IMPORT 
						&& lastSelected.getStatus() == Status.PASS){
					btn_export.setEnabled(true);
				}
				else
					btn_export.setEnabled(false);
				
				if(getSelectedNum() == 1 && lastSelected.getType() == DocumentType.SALES 
						&& lastSelected.getStatus() == Status.PASS){
					btn_return.setEnabled(true);
				}
				else
					btn_return.setEnabled(false);
			}
		});
		super.setList(list);
	}
	
	@Override
	protected DocumentType[] getDocmentTypes() {
		return new DocumentType[]{DocumentType.ALL,DocumentType.EXPORT,DocumentType.IMPORT,DocumentType.RETURN,
				DocumentType.SALES};
	}
}
