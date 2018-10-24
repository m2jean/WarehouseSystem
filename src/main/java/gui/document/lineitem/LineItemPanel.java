package gui.document.lineitem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import VO.SaleItemVO;
import gui.inventory.InventoryLineItem;

public abstract class LineItemPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8320360264748179480L;
	protected JTable tbl_list;
	protected ArrayList<InventoryLineItem> list = new ArrayList<InventoryLineItem>();
	protected JLabel lbl_total;
	protected JPanel pnl_func;
	protected JDialog parent;
	/**
	 * Create the panel.
	 */
	protected LineItemPanel(JDialog parent) {
		this.parent = parent;
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Dimension size = new Dimension(0,28+5*30);
		scrollPane.setMinimumSize(size);
		scrollPane.setMaximumSize(size);
		scrollPane.setPreferredSize(size);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.CENTER);
		
		tbl_list = new JTable(new DefaultTableModel(new String[]{"商品编号","名称","型号","数量","单价","金额","备注"}, 0));
		tbl_list.setFillsViewportHeight(true);
		tbl_list.setRowHeight(30);
		tbl_list.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(tbl_list);
		
		pnl_func = new JPanel();
		add(pnl_func, BorderLayout.SOUTH);
		pnl_func.setLayout(new BoxLayout(pnl_func, BoxLayout.X_AXIS));
		
		lbl_total = new JLabel(" 总计：0");
		pnl_func.add(lbl_total);

		
		pnl_func.add(Box.createHorizontalGlue());
	}
	
	public LineItemPanel(JDialog parent,ArrayList<InventoryLineItem> list){
		this(parent);
		setList(list);
	}
	
	public void setList(ArrayList<InventoryLineItem> list){
		this.list = list;
		setTableModel();
	}
	
	public boolean isEmpty(){
		boolean zero = true;
		for(InventoryLineItem item:list)
			if(item.getNumber() > 0){
				zero = false;
				break;
			}
		return list.isEmpty() || zero;
	}
	
	public boolean contains(SaleItemVO prod){
		for(InventoryLineItem item:list)
			if(prod.getID().equals(item.getProductID()))
				return true;
		return false;
	}

	protected abstract InventoryLineItem getLineItem(SaleItemVO product); 
	protected abstract void setTableModel();
	protected abstract double getTotal();
}
