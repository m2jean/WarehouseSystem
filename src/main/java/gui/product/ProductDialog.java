package gui.product;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import enums.ResultMessage;
import gui.GUIUtility;
import gui.MainFrame;
import gui.ResultDialog;
import VO.CategoryVO;
import VO.ProductVO;
import productBL.ProductBL;
import productBLService.ProductBLService;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class ProductDialog extends ResultDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4637534240805334411L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txf_name;
	private JTextField txf_type;
	private JTextField txf_inprice;
	private JTextField txf_outprice;
	private CategoryVO cate;
	private JLabel lbl_msg;

	/**
	 * Create the dialog.
	 */
	public ProductDialog(MainFrame mf,CategoryVO category) {
		super(mf,true);
		cate = category;
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				close();
			}
		});
		setTitle("添加商品");
		setResizable(false);
		setSize(402, 169);
		GUIUtility.setCenter(this);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(12, 0, 12, 20));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 4, 0, 25));
		{
			JLabel lbl_name = new JLabel("商品名称：");
			lbl_name.setHorizontalAlignment(SwingConstants.TRAILING);
			contentPanel.add(lbl_name);
		}
		{
			txf_name = new JTextField();
			txf_name.addFocusListener(GUIUtility.getSelectFocusListener());
			contentPanel.add(txf_name);
			txf_name.setColumns(10);
			txf_name.addActionListener(new ConfirmListener());
		}
		{
			JLabel lbl_type = new JLabel("商品型号：");
			lbl_type.setHorizontalAlignment(SwingConstants.TRAILING);
			contentPanel.add(lbl_type);
		}
		{
			txf_type = new JTextField();
			txf_type.addFocusListener(GUIUtility.getSelectFocusListener());
			contentPanel.add(txf_type);
			txf_type.setColumns(10);
			txf_type.addActionListener(new ConfirmListener());
		}
		{
			JLabel lbl_inprice = new JLabel("默认进价：");
			lbl_inprice.setHorizontalAlignment(SwingConstants.TRAILING);
			contentPanel.add(lbl_inprice);
		}
		{
			txf_inprice = new JTextField();
			txf_inprice.addFocusListener(GUIUtility.getSelectFocusListener());
			contentPanel.add(txf_inprice);
			txf_inprice.setColumns(10);
			txf_inprice.addActionListener(new ConfirmListener());
		}
		{
			JLabel lbl_outprice = new JLabel("默认售价：");
			lbl_outprice.setHorizontalAlignment(SwingConstants.TRAILING);
			contentPanel.add(lbl_outprice);
		}
		{
			txf_outprice = new JTextField();
			txf_outprice.addFocusListener(GUIUtility.getSelectFocusListener());
			contentPanel.add(txf_outprice);
			txf_outprice.setColumns(10);
			txf_outprice.addActionListener(new ConfirmListener());
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.X_AXIS));
			buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

			buttonPane.add(Box.createHorizontalGlue());
			
			lbl_msg = new JLabel();
			lbl_msg.setForeground(Color.RED);
			buttonPane.add(lbl_msg);
			
			buttonPane.add(Box.createHorizontalStrut(10));
			{
				JButton btn_confirm = new JButton("确定(Enter)");
				btn_confirm.setActionCommand("OK");
				btn_confirm.addActionListener(new ConfirmListener());
				buttonPane.add(btn_confirm);
				getRootPane().setDefaultButton(btn_confirm);
			}
			buttonPane.add(Box.createHorizontalStrut(10));
			{
				JButton btn_cancel = new JButton("取消");
				btn_cancel.setActionCommand("Cancel");
				btn_cancel.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						close();
					}
				});
				buttonPane.add(btn_cancel);
			}
			buttonPane.add(Box.createHorizontalStrut(5));
		}
	}
	
	private class ConfirmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = txf_name.getText().trim();
			String type = txf_type.getText().trim();
			String inprice = txf_inprice.getText().trim();
			String outprice = txf_outprice.getText().trim();
			if(name.equals("")){
				lbl_msg.setText("请输入商品名称！");
				txf_name.requestFocus();
			}
			else if(type.equals("")){
				lbl_msg.setText("请输入商品型号！");
				txf_type.requestFocus();
			}
			else if(inprice.equals("")){
				txf_inprice.requestFocus();
				lbl_msg.setText("请输入默认进价！");
			}
			else if(!inprice.matches("\\d*\\.?\\d+")){
				txf_inprice.requestFocus();
				lbl_msg.setText("默认进价必须为合理金额！");
			}
			else if(outprice.equals("")){
				txf_outprice.requestFocus();
				lbl_msg.setText("请输入默认售价！");
			}
			else if(!outprice.matches("\\d*\\.?\\d+")){
				txf_outprice.requestFocus();
				lbl_msg.setText("默认售价必须为合理金额！");
			}
			else{
				ProductVO prod = new ProductVO(null,name,type,0,Double.parseDouble(inprice),Double.parseDouble(outprice),0,0,cate.getID(),cate.getName());
				ProductBLService prodbl = new ProductBL();
				ResultMessage result = prodbl.addProduct(prod);
				setResult(result);
				switch(result){
				case ITEM_EXIST:
					txf_name.requestFocus();
					lbl_msg.setText("同名的商品已经存在！");
					break;
				case REMOTE_FAIL:
					lbl_msg.setText("网络错误！");
					break;
				case SUCCESS:
					setVisible(false);
					break;
				default:
					lbl_msg.setText("未知错误！");
					break;			
				}
			}
		}
		
	}

}
