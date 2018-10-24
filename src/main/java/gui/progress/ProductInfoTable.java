package gui.progress;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import saleBL.SaleLog;

public class ProductInfoTable extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8170490832374828085L;
	String[] head;
	String[][] data;
	ArrayList<SaleLog> itemList;
	
	public ProductInfoTable(ArrayList<SaleLog> saleList){
		head=new String[]{"时间","ID","商品名","型号","数量","单价","总额"};
		itemList=saleList;
		
		if(saleList==null){
			data=new String[0][0];
			return;
		}
		
		data=new String[itemList.size()][head.length];
		int i=0;
		for(SaleLog item:saleList){
			data[i][0]=item.getDate();
			data[i][1]=item.getId();
			data[i][2]=item.getName();
			data[i][3]=item.getSize();
			data[i][4]=String.valueOf(item.getNumber());
			data[i][5]=String.valueOf(item.getPrice());
			data[i][6]=String.valueOf(item.getMoney());
			
			i++;
		}
	}
	
	public int getColumnCount(){
		return head.length;
	}
	
	public int getRowCount(){
		return data.length;
	}
	
	public String getColumnName(int col){
		return head[col];
	}
	
	public String getValueAt(int row, int col){
		return data[row][col];
	}
}
