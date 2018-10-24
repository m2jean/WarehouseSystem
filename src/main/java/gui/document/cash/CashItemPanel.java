package gui.document.cash;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import VO.ListOutVO;

public abstract class CashItemPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8320360264748179480L;
	protected JTable tbl_list;
	protected ArrayList<ListOutVO> list = new ArrayList<ListOutVO>();
	protected JLabel lbl_total;
	protected JPanel pnl_func;
	protected JDialog parent;
	/**
	 * Create the panel.
	 */
	protected CashItemPanel(JDialog parent) {
		this.parent = parent;
		setLayout(new BorderLayout(0, 0));
		
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
	
	public CashItemPanel(JDialog parent,ArrayList<ListOutVO> list){
		this(parent);
		setList(list);
	}
	
	public void setList(ArrayList<ListOutVO> list){
		this.list = list;
		setTableModel();
	}
	
	public ArrayList<ListOutVO> getList(){
		return list;
	}
	
	public boolean hasEmpty(){
		if(list.isEmpty())
			return true;
		for(ListOutVO item:list)
			if(item.getName().trim().isEmpty())
				return true;
		return false;
	}

	protected abstract void setTableModel();
	protected abstract double getTotal();
}
