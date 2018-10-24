package gui.document.imports;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import importBL.ImportLineItem;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import VO.ProductVO;
import VO.SaleItemVO;
import gui.document.lineitem.LineItemPanel_Editable;
import gui.document.lineitem.LineItemTableModel;
import gui.inventory.InventoryImportItem;
import gui.inventory.InventoryLineItem;

public class ImportLineItemPanel_Editable extends LineItemPanel_Editable implements ImportLineItemPanel{

	private static final long serialVersionUID = 5644042077324443574L;
	private LinkedList<SaleItemVO>  itemlist = new LinkedList<SaleItemVO>();

	public ImportLineItemPanel_Editable(JDialog parent) {
		super(parent);
	}

	public ImportLineItemPanel_Editable(JDialog parent,ArrayList<InventoryLineItem> list) {
		super(parent,list);
		for(InventoryLineItem item: list){
			for(int i = 0;i < cbx_product.getItemCount();++i){
				SaleItemVO prod = cbx_product.getItemAt(i);
				if(item.getId().equals(prod.getID())){
					cbx_product.removeItemAt(i);
					itemlist.add(prod);
					break;
				}
			}
		}
		if(cbx_product.getItemCount() == 0){
			cbx_product.setEnabled(false);
			btn_add.setEnabled(false);
		}		
	}
	
	@Override
	protected void construct(){		
		JLabel lbl_msg = new JLabel(" 双击表格编辑数量、单价和备注");
		lbl_msg.setForeground(Color.BLUE);
		add(lbl_msg, BorderLayout.NORTH);
		
		setProductList();
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		pnl_func.add(horizontalStrut);
		
		btn_add = new JButton("添加");
		btn_add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SaleItemVO prod = cbx_product.getItemAt(cbx_product.getSelectedIndex());
				if(!contains(prod)){
					list.add(getLineItem(prod));
					setTableModel();
					itemlist.add(prod);

					cbx_product.removeItem(prod);
					if(cbx_product.getItemCount() == 0){
						cbx_product.setEnabled(false);
						btn_add.setEnabled(false);
					}
				}
			}			
		});
		btn_add.setEnabled(true);
		pnl_func.add(btn_add);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		pnl_func.add(horizontalStrut_1);
		
		btn_delete = new JButton("删除");
		btn_delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				LineItemTableModel model = (LineItemTableModel) tbl_list.getModel();
				InventoryLineItem item = model.getItemAt(tbl_list.getSelectedRow());				
				list.remove(item);
				setTableModel();
				SaleItemVO toremove = null;
				for(SaleItemVO sitem: itemlist){
					if(item.getId().equals(sitem.getID())){
						cbx_product.addItem(sitem);
						toremove = sitem;
						break;
					}
				}
				itemlist.remove(toremove);
				cbx_product.setEnabled(true);
				btn_add.setEnabled(true);
			}			
		});
		btn_delete.setEnabled(false);
		pnl_func.add(btn_delete);
		
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

	@Override
	protected InventoryLineItem getLineItem(SaleItemVO saleitem) {
		ProductVO product = (ProductVO)saleitem;
		return new InventoryImportItem(new ImportLineItem(product.getID(),product.getName(),product.getModel(),1,product.getPriceIn(),""));
	}

	@Override
	public ArrayList<ImportLineItem> getItemList() {
		ArrayList<ImportLineItem> itemlist = new ArrayList<ImportLineItem>();
		for(InventoryLineItem item:list)
			itemlist.add(((InventoryImportItem)item).getLineItem());
		return itemlist;
	}
	
}
