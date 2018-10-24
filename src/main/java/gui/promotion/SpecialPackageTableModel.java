package gui.promotion;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import productBL.ProductItem;
import VO.SpecialProductVO;

public class SpecialPackageTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4963094073282331339L;
	String[] head;
	ArrayList<SpecialProductVO> productList;
	String[][] data;
	
	public SpecialPackageTableModel(ArrayList<SpecialProductVO> list){
		productList = list;
		head = new String[]{"ID", "特价包名", "内容", "原价", "售价", "已售出数量"};
		
		if(list == null || list.size() == 0){
			data = new String[0][0];
		}else{
			data = new String[list.size()][head.length];
			
			for(int i=0;i<list.size();i++){
				SpecialProductVO product = list.get(i);
				data[i][0] = product.getID();
				data[i][1] = product.getName().split("）")[1];
				
				StringBuffer content = new StringBuffer();
				for(ProductItem item:product.getProductList()){
					content.append(item.getProductName()+"*"+item.getNumber()+" ");
				}
				
				data[i][2] = content.toString();
				data[i][3] = String.valueOf(product.getOriginalPrice());
				data[i][4] = String.valueOf(product.getPrice());
				data[i][5] = String.valueOf(product.getNumber());
			}
		}
	}
	
	
	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return head.length;
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	public String getColumnName(int col){
		return head[col];
	}
	
	public SpecialProductVO getProduct(int row){
		return productList.get(row);
	}

}
