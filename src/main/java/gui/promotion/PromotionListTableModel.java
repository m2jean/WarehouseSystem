package gui.promotion;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import VO.PromotionVO;

public class PromotionListTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6721778721987452705L;
	private ArrayList<PromotionVO> promotionList;
	private String[] head;
	private String[][] data;
	
	public PromotionListTableModel(ArrayList<PromotionVO> list){
		promotionList = list;
		head = new String[]{"ID","开始时间","结束时间","客户等级要求","总价要求","折让","折扣（%）","赠送代金券","赠品"};
		
		if(list == null || list.size() == 0){
			data = new String[0][0];
		}else{
			data = new String[list.size()][head.length];
			
			for(int i=0;i<list.size();i++){
				PromotionVO vo = list.get(i);
				data[i][0] = vo.getID();
				data[i][1] = vo.getStartDate();
				data[i][2] = vo.getEndDate();
				data[i][3] = vo.getViplvl().toString();
				data[i][4] = String.valueOf(vo.getPriceLine());
				if(Math.abs(vo.getDiscount()) < 0.001){
					data[i][5] = "无";
				}else{
					data[i][5] = String.valueOf(vo.getDiscount());
				}
				
				if(vo.getPercent() == 100){
					data[i][6] = "无";
				}else{
					data[i][6] = String.valueOf(vo.getPercent());
				}
				
				if(Math.abs(vo.getCoupon()) < 0.001){
					data[i][7] = "无";
				}else{
					data[i][7] = String.valueOf(vo.getPercent());
				}
				
				if(vo.getGift() == null){
					data[i][8] = "无";
				}else{
					data[i][8] = vo.getGift().getProductList().get(0).getProductName()+"等";
				}
			}
		}
	}

	@Override
	public int getRowCount() {
		return promotionList==null?0:promotionList.size();
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
	
	public PromotionVO getPromotion(int row){
		return promotionList.get(row);
	}
}
