package gui.document.sales;

import gui.GUIUtility;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import productBL.ProductItem;
import VO.PromotionVO;

public class PromotionTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -497419513250528909L;
	private ArrayList<PromotionVO> data;
	private String[] header = new String[]{"结束时间","客户等级要求","总价要求","折让","折扣（%）","赠送代金券","赠品数量"};
	
	public PromotionTableModel(ArrayList<PromotionVO> list){
		data = list;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0:
			return data.get(rowIndex).getEndDate();
		case 1:
			return data.get(rowIndex).getViplvl().toString();
		case 2:
			return GUIUtility.formatDouble(data.get(rowIndex).getPriceLine());
		case 3:
			return GUIUtility.formatDouble(data.get(rowIndex).getDiscount());
		case 4:
			return GUIUtility.formatDouble(data.get(rowIndex).getPercent());
		case 5:
			return GUIUtility.formatDouble(data.get(rowIndex).getCoupon());
		case 6:
			int num = 0;
			if(data.get(rowIndex).getGift()!=null){
				for(ProductItem item:data.get(rowIndex).getGift().getProductList())
					num += item.getNumber();
			}
			return String.valueOf(num);
		}
		return null;
	}
	
	public String getColumnName(int col){
		return header[col];
	}
	

}
