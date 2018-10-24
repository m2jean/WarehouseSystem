package gui.document.sales;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import productBL.ProductBL;
import productBLService.ProductBLService;
import enums.DataMessage;
import saleBL.SaleLineItem;
import VO.ProductVO;
import VO.SaleItemVO;
import gui.document.lineitem.LineItemPanel_Editable;
import gui.inventory.InventoryLineItem;
import gui.inventory.InventorySaleItem;

public class SaleLineItemPanel_Editable extends LineItemPanel_Editable implements SaleLineItemPanel{

	private static final long serialVersionUID = 5644042077324443574L;
	private ArrayList<Integer> numlist = new ArrayList<Integer>();

	public SaleLineItemPanel_Editable(JDialog parent) {
		super(parent);
		
	}

	public SaleLineItemPanel_Editable(JDialog parent,ArrayList<InventoryLineItem> list) {
		super(parent,list);
		
	}
	
	@Override
	protected void construct(){
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
					if(prod instanceof ProductVO)
						numlist.add(((ProductVO)prod).getNumber());
					else
						numlist.add(Integer.MAX_VALUE);
					setTableModel();
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
				//LineItemTableModel model = (LineItemTableModel) tbl_list.getModel();
				list.remove(tbl_list.getSelectedRow());
				numlist.remove(tbl_list.getSelectedRow());
				setTableModel();
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
	protected void setProductList() {
		ProductBLService prodbl = new ProductBL();
		DataMessage<ArrayList<SaleItemVO>> result = prodbl.getAllSaleItem();
		switch(result.resultMessage){
		case ITEM_NOT_EXIST:
			JOptionPane.showMessageDialog(parent, "没有商品可以添加，请询问库存管理人员！");
			parent.dispose();
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(parent, "通信错误，无法获得商品列表！");
			parent.dispose();
			break;
		case SUCCESS:
			Collections.sort(result.data);
			SaleItemVO[] list = new SaleItemVO[result.data.size()];
			for(int i = 0;i < list.length;i++)
				list[i] = result.data.get(i);
			cbx_product = new JComboBox<SaleItemVO>(list);
			cbx_product.setSelectedIndex(0);
			pnl_func.add(cbx_product);
			break;
		default:
			JOptionPane.showMessageDialog(parent, "未知错误！");
			parent.dispose();
			break;
		}
		
	}
	
	protected void setTableModel(){
		tbl_list.setModel(new SaleLineItemTableModel_Editable(list,numlist,lbl_total,parent));
	}

	@Override
	protected InventoryLineItem getLineItem(SaleItemVO product) {
		return new InventorySaleItem(new SaleLineItem(product.getID(),product.getName(),product.getModel(),1,product.getPrice(),0,product.getPS()));
	}

	@Override
	public ArrayList<SaleLineItem> getItemList() {
		ArrayList<SaleLineItem> itemlist = new ArrayList<SaleLineItem>();
		for(InventoryLineItem item:list)
			itemlist.add(((InventorySaleItem)item).getLineItem());
		return itemlist;
	}
}
