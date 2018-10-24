package gui.document.lineitem;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import VO.ProductVO;
import VO.SaleItemVO;
import enums.DataMessage;
import gui.inventory.InventoryLineItem;
import productBL.ProductBL;
import productBLService.ProductBLService;


public abstract class LineItemPanel_Editable extends LineItemPanel {

	private static final long serialVersionUID = 5694502460547950389L;
	protected JComboBox<SaleItemVO> cbx_product;
	protected JButton btn_delete,btn_add;
	protected ProductVO[] lists;

	public LineItemPanel_Editable(JDialog parent) {// For adding
		super(parent);
		construct();
	}
	
	public LineItemPanel_Editable(JDialog parent,ArrayList<InventoryLineItem> list){// For updating
		super(parent,list);
		construct();
	}
	
	protected abstract void construct();
	
	protected void setProductList() {
		ProductBLService prodbl = new ProductBL();
		DataMessage<ArrayList<ProductVO>> result = prodbl.getProductList();
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
			lists = new ProductVO[result.data.size()];
			for(int i = 0;i < lists.length;i++)
				lists[i] = result.data.get(i);
			cbx_product = new JComboBox<SaleItemVO>(lists);
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
		tbl_list.setModel(new LineItemTableModel_Editable(list,lbl_total,parent));
	}

	public double getTotal(){
		return ((LineItemTableModel_Editable)tbl_list.getModel()).getTotal();
	}
}
