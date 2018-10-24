package gui.document.cash;

import javax.swing.JDialog;

import VO.ListOutVO;

import javax.swing.JButton;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class CashItemPanel_Editable extends CashItemPanel {

	private static final long serialVersionUID = 5694502460547950389L;
	private JButton btn_delete;

	public CashItemPanel_Editable(JDialog parent) {// For adding
		super(parent);
		construct();
	}
	
	public CashItemPanel_Editable(JDialog parent,ArrayList<ListOutVO> list){// For updating
		super(parent,list);
		construct();
	}
	
	private void construct(){

		JButton btn_add = new JButton("添加");
		btn_add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ListOutVO OutVo = new ListOutVO("",0,"");
				list.add(OutVo);
				tbl_list.setModel(new CashItemTableModel_Editable(list,lbl_total,parent));	
			}			
		});
		btn_add.setEnabled(true);
		pnl_func.add(btn_add);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		pnl_func.add(horizontalStrut_1);
		
		btn_delete = new JButton("删除");
		btn_delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				CashItemTableModel model = (CashItemTableModel) tbl_list.getModel();
				list.remove(model.getItemAt(tbl_list.getSelectedRow()));
				tbl_list.setModel(new CashItemTableModel_Editable(list,lbl_total,parent));
			}			
		});
		btn_delete.setEnabled(false);
		pnl_func.add(btn_delete);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		pnl_func.add(horizontalGlue);
		
		tbl_list.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(tbl_list.getSelectedRow() == -1)
					btn_delete.setEnabled(false);
				else
					btn_delete.setEnabled(true);
			}	
		});
	}

	protected void setTableModel(){
		tbl_list.setModel(new CashItemTableModel_Editable(list,lbl_total,parent));
	}

	public double getTotal(){
		return ((CashItemTableModel_Editable)tbl_list.getModel()).getTotal();
	}
}
