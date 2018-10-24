package gui.product;

import enums.ResultMessage;
import gui.GUIUtility;
import gui.MainFrame;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import productBL.ProductBL;
import productBLService.ProductBLService;
import VO.ProductVO;

public class ProductTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3324452709974957470L;
	private ArrayList<ProductVO> data;
	private String[] header = {"编号","名称","型号","库存数量","进价","零售价","最近进价","最近零售价"};
	private ProductBLService prodbl = new ProductBL();
	
	public ProductTableModel(ArrayList<ProductVO> list){
		data = list;
	}
	
	
	public int getColumnCount() {
		return header.length;
	}

	public int getRowCount() {
		return data.size();
	}
	
	@Override
	public String getColumnName(int col){
		return header[col];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0:
			return data.get(rowIndex).getID();
		case 1:
			return data.get(rowIndex).getName();
		case 2:
			return data.get(rowIndex).getModel();
		case 3:
			return data.get(rowIndex).getNumber();
		case 4:
			return GUIUtility.formatDouble(data.get(rowIndex).getPriceIn());
		case 5:
			return GUIUtility.formatDouble(data.get(rowIndex).getPriceOut());
		case 6:
			return GUIUtility.formatDouble(data.get(rowIndex).getLastIn());
		case 7:
			return GUIUtility.formatDouble(data.get(rowIndex).getLastOut());
		}
		return null;
	}

	
	public void setValueAt(Object value,int rowIndex,int columnIndex){
		String str = (String) value;
		try{
			switch(columnIndex){
			case 4:
				data.get(rowIndex).setPriceIn(Double.parseDouble(str));
				break;
			case 5:
				data.get(rowIndex).setPriceOut(Double.parseDouble(str));
				break;
			}
			
			ResultMessage result = prodbl.updateProduct(getProductAt(rowIndex));
			switch(result){
			case SUCCESS:
				fireTableCellUpdated(rowIndex,columnIndex);
				break;
			default:
				JOptionPane.showMessageDialog(MainFrame.mf, "修改商品失败！");
				break;
			}
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(MainFrame.mf, "请输入合法小数！");
		}
	}
	
	public boolean isCellEditable(int rowIndex,int columnIndex){
		if(columnIndex == 4 || columnIndex == 5)
			return true;
		else
			return false;
	}
	
	public ProductVO getProductAt(int rowIndex){
		return data.get(rowIndex);
	}
}
