package gui.document.imports;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import enums.ResultMessage;
import gui.GUIUtility;
import gui.MainFrame;
import gui.document.component.DocPanel;
import gui.document.doclist.DocListPanel;
import gui.document.vo.ImportExportVO;
import importBL.ImportLineItem;

public abstract class ImportDocPanel extends DocPanel {

	private static final long serialVersionUID = 1733446746459814090L;
	private JLabel lbl_customer,lbl_stock,lbl_totalPrice;
	private JLabel lbl_sorts,lbl_totalNum;
	private JTextArea txa_remarks;
	protected ImportExportVO vo;
	
	public ImportDocPanel(ImportExportVO vo, int func, DocListPanel pnl_list) {
		super(vo, func, pnl_list);
		construct();
		setVO(vo);
	}

	private void construct(){
		GridBagLayout gbl_pnl_content = new GridBagLayout();
		gbl_pnl_content.columnWidths = new int[]{150, 144, 183, 0};
		gbl_pnl_content.rowHeights = new int[]{21, 15, 0, 0};
		gbl_pnl_content.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_pnl_content.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		pnl_content.setLayout(gbl_pnl_content);
		
		lbl_customer = new JLabel();
		lbl_customer.setFont(new Font("宋体", Font.PLAIN, 14));
		lbl_customer.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_customer = new GridBagConstraints();
		gbc_lbl_customer.anchor = GridBagConstraints.WEST;
		gbc_lbl_customer.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_customer.gridx = 0;
		gbc_lbl_customer.gridy = 0;
		pnl_content.add(lbl_customer, gbc_lbl_customer);
		
		lbl_sorts = new JLabel();
		lbl_sorts.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_lbl_sorts = new GridBagConstraints();
		gbc_lbl_sorts.anchor = GridBagConstraints.WEST;
		gbc_lbl_sorts.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_sorts.gridx = 1;
		gbc_lbl_sorts.gridy = 0;
		pnl_content.add(lbl_sorts, gbc_lbl_sorts);
		
		JLabel lbl_remarks = new JLabel("备注：");
		lbl_remarks.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_lbl_remarks = new GridBagConstraints();
		gbc_lbl_remarks.anchor = GridBagConstraints.WEST;
		gbc_lbl_remarks.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_remarks.gridx = 2;
		gbc_lbl_remarks.gridy = 0;
		pnl_content.add(lbl_remarks, gbc_lbl_remarks);
		
		lbl_stock = new JLabel();
		lbl_stock.setFont(new Font("宋体", Font.PLAIN, 14));
		lbl_stock.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_stock = new GridBagConstraints();
		gbc_lbl_stock.anchor = GridBagConstraints.WEST;
		gbc_lbl_stock.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_stock.gridx = 0;
		gbc_lbl_stock.gridy = 1;
		pnl_content.add(lbl_stock, gbc_lbl_stock);
		
		lbl_totalNum = new JLabel();
		lbl_totalNum.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_lbl_totalNum = new GridBagConstraints();
		gbc_lbl_totalNum.anchor = GridBagConstraints.WEST;
		gbc_lbl_totalNum.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_totalNum.gridx = 1;
		gbc_lbl_totalNum.gridy = 1;
		pnl_content.add(lbl_totalNum, gbc_lbl_totalNum);
		
		lbl_totalPrice = new JLabel();
		lbl_totalPrice.setFont(new Font("宋体", Font.PLAIN, 14));
		lbl_totalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_totalPrice = new GridBagConstraints();
		gbc_lbl_totalPrice.anchor = GridBagConstraints.WEST;
		gbc_lbl_totalPrice.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_totalPrice.gridx = 1;
		gbc_lbl_totalPrice.gridy = 2;
		pnl_content.add(lbl_totalPrice, gbc_lbl_totalPrice);
		
		txa_remarks = new JTextArea();
		txa_remarks.setEditable(false);
		txa_remarks.setWrapStyleWord(true);
		//txa_remarks.setBorder(null);
		txa_remarks.setFocusable(false);
		txa_remarks.setBackground(new Color(UIManager.getColor("Label.background").getRGB()));
		txa_remarks.setLineWrap(true);
		GridBagConstraints gbc_txa_remarks = new GridBagConstraints();
		gbc_txa_remarks.gridheight = 2;
		gbc_txa_remarks.fill = GridBagConstraints.BOTH;
		gbc_txa_remarks.gridx = 2;
		gbc_txa_remarks.gridy = 1;
		pnl_content.add(txa_remarks, gbc_txa_remarks);
	}
	
	public void setVO(ImportExportVO vo){
		this.vo = vo;
		setProperty(vo);
		setCustomer(vo.getCustomer());
		setStock(vo.getWarehouse());
		setTotalPrice(vo.getTotal());
		setSortNumber(vo.getProductList().size());
		setTotalNumber(vo.getProductList());
		if(vo.getRemarks().isEmpty())
			txa_remarks.setText("无");
		else
			txa_remarks.setText(vo.getRemarks());
	}
	private void setCustomer(String cus){
		lbl_customer.setText("客户："+cus);
	}
	private void setStock(String stock){
		lbl_stock.setText("仓库："+stock);
	}
	private void setTotalPrice(double price){
		lbl_totalPrice.setText("总价："+GUIUtility.formatDouble(price));
	}
	private void setSortNumber(int num){
		lbl_sorts.setText("共有"+String.valueOf(num)+"种商品");
	}
	private void setTotalNumber(ArrayList<ImportLineItem> list){
		int num = 0;
		for(ImportLineItem item:list)
			num += item.getNumber();
		lbl_totalNum.setText("共计"+num+"件");
	}

	@Override
	public void check() {
		JDialog dia = new ImportDialog_Uneditable(MainFrame.mf,vo);
		dia.pack();
		dia.setResizable(false);
		GUIUtility.setCenter(dia);
		dia.setVisible(true);
	}

	public abstract ResultMessage getDeletionResult();
	
}
