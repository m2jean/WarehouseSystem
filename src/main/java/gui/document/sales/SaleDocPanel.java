package gui.document.sales;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import VO.PresentVO;
import VO.SaleVO;
import enums.DataMessage;
import enums.DocumentType;
import enums.ResultMessage;
import enums.Status;
import gui.GUIUtility;
import gui.MainFrame;
import gui.ResultDialog;
import gui.document.component.DocPanel;
import gui.document.doclist.DocListPanel;
import inventoryBL.InventoryBL;
import progressBL.ProgressBL;
import progressBLService.ProgressBLService;
import saleBL.SaleBL;
import saleBL.SaleLineItem;

public class SaleDocPanel extends DocPanel {

	private static final long serialVersionUID = 8377815228318655205L;
	private JLabel lbl_totalPrice;
	private JLabel lbl_totalNum;
	private JLabel lbl_stock;
	private JLabel lbl_sorts;
	private JLabel lbl_customer;
	private JLabel lbl_clerk;
	private JLabel lbl_yeild;
	private JLabel lbl_offPrice;
	private JLabel lbl_actualPrice;
	private JTextArea txa_remarks;
	private SaleVO vo;

	public SaleDocPanel(SaleVO vo, int func, DocListPanel pnl_list) {
		super(vo, func, pnl_list);		
		this.vo = vo;
		construct();
		setVO(vo);
	}
		
	private void construct(){
		GridBagLayout gbl_pnl_content = new GridBagLayout();
		gbl_pnl_content.columnWidths = new int[]{176, 139, 141, 104, 0};
		gbl_pnl_content.rowHeights = new int[]{21, 15, 0, 0};
		gbl_pnl_content.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_pnl_content.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		pnl_content.setLayout(gbl_pnl_content);
		
		lbl_customer = new JLabel();
		lbl_customer.setFont(new Font("宋体", Font.PLAIN, 12));
		lbl_customer.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_customer = new GridBagConstraints();
		gbc_lbl_customer.anchor = GridBagConstraints.WEST;
		gbc_lbl_customer.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_customer.gridx = 0;
		gbc_lbl_customer.gridy = 0;
		pnl_content.add(lbl_customer, gbc_lbl_customer);
		
		lbl_sorts = new JLabel();
		lbl_sorts.setFont(new Font("宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl_sorts = new GridBagConstraints();
		gbc_lbl_sorts.anchor = GridBagConstraints.WEST;
		gbc_lbl_sorts.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_sorts.gridx = 1;
		gbc_lbl_sorts.gridy = 0;
		pnl_content.add(lbl_sorts, gbc_lbl_sorts);
		
		lbl_yeild = new JLabel();
		lbl_yeild.setFont(new Font("宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl_yeild = new GridBagConstraints();
		gbc_lbl_yeild.anchor = GridBagConstraints.WEST;
		gbc_lbl_yeild.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_yeild.gridx = 2;
		gbc_lbl_yeild.gridy = 0;
		pnl_content.add(lbl_yeild, gbc_lbl_yeild);
		
		JLabel lbl_remarks = new JLabel();
		lbl_remarks.setText("备注：");
		lbl_remarks.setFont(new Font("宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl_remarks = new GridBagConstraints();
		gbc_lbl_remarks.anchor = GridBagConstraints.WEST;
		gbc_lbl_remarks.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_remarks.gridx = 3;
		gbc_lbl_remarks.gridy = 0;
		pnl_content.add(lbl_remarks, gbc_lbl_remarks);
		
		lbl_clerk = new JLabel("");
		lbl_clerk.setFont(new Font("宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl_clerk = new GridBagConstraints();
		gbc_lbl_clerk.anchor = GridBagConstraints.WEST;
		gbc_lbl_clerk.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_clerk.gridx = 0;
		gbc_lbl_clerk.gridy = 1;
		pnl_content.add(lbl_clerk, gbc_lbl_clerk);
		
		lbl_totalNum = new JLabel();
		lbl_totalNum.setFont(new Font("宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl_totalNum = new GridBagConstraints();
		gbc_lbl_totalNum.anchor = GridBagConstraints.WEST;
		gbc_lbl_totalNum.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_totalNum.gridx = 1;
		gbc_lbl_totalNum.gridy = 1;
		pnl_content.add(lbl_totalNum, gbc_lbl_totalNum);
		
		lbl_offPrice = new JLabel("");
		lbl_offPrice.setFont(new Font("宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl_offPrice = new GridBagConstraints();
		gbc_lbl_offPrice.anchor = GridBagConstraints.WEST;
		gbc_lbl_offPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_offPrice.gridx = 2;
		gbc_lbl_offPrice.gridy = 1;
		pnl_content.add(lbl_offPrice, gbc_lbl_offPrice);
		
		txa_remarks = new JTextArea();
		txa_remarks.setEditable(false);
		txa_remarks.setWrapStyleWord(true);
		txa_remarks.setBackground(UIManager.getColor("Label.background"));
		txa_remarks.setLineWrap(true);
		GridBagConstraints gbc_txa_remarks = new GridBagConstraints();
		gbc_txa_remarks.gridheight = 2;
		gbc_txa_remarks.fill = GridBagConstraints.BOTH;
		gbc_txa_remarks.gridx = 3;
		gbc_txa_remarks.gridy = 1;
		pnl_content.add(txa_remarks, gbc_txa_remarks);
		
		lbl_stock = new JLabel();
		lbl_stock.setFont(new Font("宋体", Font.PLAIN, 12));
		lbl_stock.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_stock = new GridBagConstraints();
		gbc_lbl_stock.anchor = GridBagConstraints.WEST;
		gbc_lbl_stock.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_stock.gridx = 0;
		gbc_lbl_stock.gridy = 2;
		pnl_content.add(lbl_stock, gbc_lbl_stock);
		
		lbl_totalPrice = new JLabel();
		lbl_totalPrice.setFont(new Font("宋体", Font.PLAIN, 12));
		lbl_totalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_totalPrice = new GridBagConstraints();
		gbc_lbl_totalPrice.anchor = GridBagConstraints.WEST;
		gbc_lbl_totalPrice.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_totalPrice.gridx = 1;
		gbc_lbl_totalPrice.gridy = 2;
		pnl_content.add(lbl_totalPrice, gbc_lbl_totalPrice);
		
		lbl_actualPrice = new JLabel("");
		lbl_actualPrice.setFont(new Font("宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl_actualPrice = new GridBagConstraints();
		gbc_lbl_actualPrice.anchor = GridBagConstraints.WEST;
		gbc_lbl_actualPrice.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_actualPrice.gridx = 2;
		gbc_lbl_actualPrice.gridy = 2;
		pnl_content.add(lbl_actualPrice, gbc_lbl_actualPrice);
	}
	
	public void setVO(SaleVO vo){
		this.vo = vo;
		setProperty(vo);
		setCustomer(vo.getCustomer());
		setClerk(vo.getSalesman());
		setStock(vo.getWarehouse());
		setTotalPrice(vo.getTotal());
		setYeild(vo.getDiscount1());
		setOffPrice(vo.getDiscount2());
		setActualPrice(vo.getPostTotal());
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
	private void setClerk(String clerk){
		lbl_clerk.setText("业务员："+vo.getSalesman());
	}
	private void setStock(String stock){
		lbl_stock.setText("仓库："+stock);
	}
	private void setTotalPrice(double price){
		lbl_totalPrice.setText("总价："+GUIUtility.formatDouble(price));
	}
	private void setActualPrice(double price){
		lbl_actualPrice.setText("折后："+GUIUtility.formatDouble(price));
	}
	private void setSortNumber(int num){
		lbl_sorts.setText("共有"+String.valueOf(num)+"种商品");
	}
	private void setYeild(double amount){
		lbl_yeild.setText("折让："+GUIUtility.formatDouble(amount));
	}
	private void setOffPrice(double percent){
		lbl_yeild.setText("折扣："+GUIUtility.formatDouble(percent));
	}
	private void setTotalNumber(ArrayList<SaleLineItem> list){
		int num = 0;
		for(SaleLineItem item:list)
			num += item.getNumber();
		lbl_totalNum.setText("共计"+num+"件");
	}


	@Override
	public void check() {
		new SaleDialog_Uneditable(MainFrame.mf,vo).setVisible(true);
	}

	@Override
	public ResultMessage update() {
		ResultDialog rd = new SaleDialog(MainFrame.mf,vo);
		rd.setVisible(true);
		ResultMessage ret = ResultDialog.getResultAndDispose(rd);
		return ret;
	}

	@Override
	public ResultMessage getDeletionResult() {
		return new SaleBL().delSale(vo);
	}

	@Override
	public DocumentType getType() {
		return DocumentType.SALES;
	}

	public SaleVO getSaleVO(){
		return vo;
	}
	
	@Override
	public ResultMessage examine() {
		ResultDialog dia = new SaleDialog_Examine(MainFrame.mf,vo);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getExaminationResult(boolean pass) {
		if(pass)
			vo.setStatus(Status.PASS);
		else
			vo.setStatus(Status.FAIL);
		return new SaleBL().shenpi(vo);
	}
	
	@Override
	public ResultMessage redcopy() {
		ResultDialog dia = new SaleDialog_Red(MainFrame.mf,vo);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getRedResult() {
		
		vo.setHong(true);
		DataMessage<PresentVO> present = new InventoryBL().getPresentTable(new PresentVO(vo.getPresent()));
		if(present.resultMessage != ResultMessage.SUCCESS){
			return present.resultMessage;
		}
		present.data.setHong(true);
		
		ProgressBLService progress = new ProgressBL();
		vo = progress.hong(vo);
		return progress.hongcopy(vo,present.data);
	}
}
