package gui.document.account;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import VO.AccountVO;
import VO.ListInVO;

public abstract class BillItemPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8320360264748179480L;
	protected JTable tbl_list;
	protected ArrayList<ListInVO> list = new ArrayList<ListInVO>();
	protected JLabel lbl_total;
	protected JPanel pnl_func;
	protected JDialog parent;
	protected JLabel lbl_hint;
	/**
	 * Create the panel.
	 */
	protected BillItemPanel(JDialog parent) {
		this.parent = parent;
		setLayout(new BorderLayout(0, 0));
		
		Box box_lbl = new Box(BoxLayout.X_AXIS);
		add(box_lbl, BorderLayout.NORTH);
		
		lbl_hint = new JLabel();
		lbl_hint.setForeground(Color.BLUE);
		box_lbl.add(lbl_hint);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);
		
		tbl_list = new JTable();
		tbl_list.setRowHeight(30);
		tbl_list.getTableHeader().setReorderingAllowed(false);
		
		scrollPane.setViewportView(tbl_list);
		
		pnl_func = new JPanel();
		add(pnl_func, BorderLayout.SOUTH);
		pnl_func.setLayout(new BoxLayout(pnl_func, BoxLayout.X_AXIS));
		
		lbl_total = new JLabel("总计：");
		pnl_func.add(lbl_total);
		
		Component horizontalStrut = Box.createHorizontalGlue();
		pnl_func.add(horizontalStrut);
	}
	
	public BillItemPanel(JDialog parent,ArrayList<ListInVO> list){
		this(parent);
		setList(list);
	}
	
	public void setList(ArrayList<ListInVO> list){
		this.list = list;
		setTableModel();
	}
	
	public ArrayList<ListInVO> getList(){
		return list;
	}
	
	public boolean contains(AccountVO vo){
		for(ListInVO in:list)
			if(in.getAccountID().equals(vo.getID()))
				return true;
		return false;
	}
	
	public boolean isEmpty(){
		boolean zero = true;
		for(ListInVO item:list)
			if(item.getMoney() > 0){
				zero = false;
				break;
			}
		return list.isEmpty() || zero;
	}

	protected abstract void setTableModel();
	protected abstract double getTotal();
}
