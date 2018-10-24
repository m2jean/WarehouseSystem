package gui.document.sales;

import gui.GUIUtility;
import gui.inventory.InventoryLineItem;
import gui.inventory.InventorySaleItem;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import saleBL.SaleLineItem;
import VO.ProductVO;

public class ReturnLineItemPanel extends JPanel implements SaleLineItemPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8320360264748179480L;
	protected JTable tbl_list;
	protected ArrayList<InventoryLineItem> list = new ArrayList<InventoryLineItem>();
	protected ArrayList<InventoryLineItem> gifts = new ArrayList<InventoryLineItem>();
	private JLabel lbl_total;
	private JPanel pnl_func;
	protected JTextField txf_total;
	protected int[] limit;
	protected JDialog parent;
	/**
	 * Create the panel.
	 */
	private ReturnLineItemPanel(JDialog parent) {
		this.parent = parent;
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);
		
		tbl_list = new JTable();
		tbl_list.getTableHeader().setReorderingAllowed(false);
		
		scrollPane.setViewportView(tbl_list);
		
		pnl_func = new JPanel();
		add(pnl_func, BorderLayout.SOUTH);
		pnl_func.setLayout(new BoxLayout(pnl_func, BoxLayout.X_AXIS));
		
		lbl_total = new JLabel("总计：");
		pnl_func.add(lbl_total);
		
		txf_total = new JTextField(10);
		//txf_total.addFocusListener(GUIUtility.getFocusListenerForDouble(txf_total, parent));
		pnl_func.add(txf_total);
		
		pnl_func.add(Box.createHorizontalGlue());
	}
	
	public ReturnLineItemPanel(JDialog parent,ArrayList<InventoryLineItem> list,ArrayList<InventoryLineItem> gifts,int[] limit){
		this(parent);
		this.list = list;
		this.gifts = gifts;
		this.limit = limit;
		setTableModel();
	}
	
	protected void setTableModel(){
		tbl_list.setModel(new ReturnLineItemTableModel_Editable(parent,list,gifts,limit));
	}
	protected double getTotal(){
		return Double.parseDouble(txf_total.getText());
	}
	public void setTotal(double total){
		txf_total.setText(GUIUtility.formatDouble(total));
	}
	
	protected static InventoryLineItem getLineItem(ProductVO product) {
		return new InventorySaleItem(new SaleLineItem(product.getID(),product.getName(),product.getModel(),0,product.getPriceOut(),0,""));
	}

	@Override
	public ArrayList<SaleLineItem> getItemList() {
		ArrayList<SaleLineItem> itemlist = new ArrayList<SaleLineItem>();
		for(InventoryLineItem item:list)
			itemlist.add(((InventorySaleItem)item).getLineItem());
		return itemlist;
	}
}
