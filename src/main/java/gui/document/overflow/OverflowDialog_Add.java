package gui.document.overflow;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import VO.OverflowVO;
import VO.ProductVO;
import businesslogic.userbl.UserBL;
import enums.DataMessage;
import enums.ResultMessage;
import enums.Status;
import inventoryBL.InventoryBL;
import productBL.ProductBL;
import productBL.ProductLineItem;
import productBLService.ProductBLService;

public class OverflowDialog_Add extends OverflowDialog {

	private static final long serialVersionUID = -7567777392517814988L;
	private JButton btn_submit;
	private JComboBox<ProductVO> cbx_product;

	public OverflowDialog_Add(Frame parent) {
		super(parent);
		setTitle("创建报损报溢单");
		ProductVO prod = cbx_product.getItemAt(cbx_product.getSelectedIndex());
		lbl_record.setText("  "+String.valueOf(prod.getNumber()));
		txf_actual.setDefaultText(String.valueOf(prod.getNumber()));
		cbx_product.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ProductVO prod = cbx_product.getItemAt(cbx_product.getSelectedIndex());
				lbl_record.setText("  "+String.valueOf(prod.getNumber()));
				txf_actual.setDefaultText(String.valueOf(prod.getNumber()));
			}
		});
		vo = new OverflowVO("");
		vo.setOperator(new UserBL().getCurrent().getName());
	}

	@Override
	protected void addButtons(JPanel buttonPane) {
		{
			btn_submit = new JButton("提交");
			btn_submit.setActionCommand("submit");
			btn_submit.addActionListener(new ConfirmListener());
			buttonPane.add(btn_submit);
		}
	}

	@Override
	protected Component getProductComponent() {
		ProductBLService prodbl = new ProductBL();
		DataMessage<ArrayList<ProductVO>> result = prodbl.getProductList();
		switch(result.resultMessage){
		case ITEM_NOT_EXIST:
			JOptionPane.showMessageDialog(this, "没有商品可以添加，请询问库存管理人员！");
			close();
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(this, "通信错误，无法获得商品列表！");
			close();
			break;
		case SUCCESS:
			Collections.sort(result.data);
			ProductVO[] list = new ProductVO[result.data.size()];
			for(int i = 0;i < list.length;i++)
				list[i] = result.data.get(i);
			cbx_product = new JComboBox<ProductVO>(list);
			cbx_product.setSelectedIndex(0);
			return cbx_product;
		default:
			JOptionPane.showMessageDialog(this, "未知错误！");
			close();
			break;
		}
		return null;
	}

	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!txf_actual.getText().trim().matches("\\d+")){
				lbl_msg.setText("请输入合理的商品数量");
				txf_actual.requestFocus();
				return;
			}
			else if(txf_stock.getText().trim().isEmpty()){
				lbl_msg.setText("请输入仓库");
				txf_stock.requestFocus();
				return;
			}
			vo.setWarehouse(txf_stock.getText().trim());
			vo.setNumInWarehouse(Integer.parseInt(txf_actual.getText().trim()));
			vo.setHong(true);
			ProductVO prod = cbx_product.getItemAt(cbx_product.getSelectedIndex());
			vo.setNumInSystem(prod.getNumber());
			vo.setProduct(new ProductLineItem(prod.getID(),prod.getName(),prod.getModel()));
			
			vo.setStatus(Status.SUBMIT);
			ResultMessage result = new InventoryBL().newOverflow(vo);
			setResult(result);
			switch(result){
			case ITEM_EXIST:
				JOptionPane.showMessageDialog(OverflowDialog_Add.this, "单据状态已被修改，创建失败！");
				setVisible(false);
				break;
			case REMOTE_FAIL:
				JOptionPane.showMessageDialog(OverflowDialog_Add.this, "通信错误！");
				break;
			case SUCCESS:
				setVisible(false);
				break;
			default:
				JOptionPane.showMessageDialog(OverflowDialog_Add.this, "未知错误！");
				break;
			
			}
		}
	}
}
