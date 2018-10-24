package gui.product;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.JLabel;
import javax.swing.JTextField;

import productBL.CategoryBL;
import productBLService.CategoryBLService;
import VO.CategoryVO;
import enums.Operation;
import enums.ResultMessage;
import gui.MainFrame;
import gui.ResultDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CategoryDialog extends ResultDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3137374017867182350L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txf_name;
	private Operation opt;
	private CategoryVO cate;
	private JButton btn_confirm;
	private JLabel lbl_msg;

	/**
	 * Create the dialog.
	 */
	public CategoryDialog(MainFrame mf,Operation opt,CategoryVO cate) {
		super(mf,true);
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				close();
			}
		});
		this.opt = opt;
		this.cate = cate;
		if(opt == Operation.ADD_CATEGORY)
			setTitle("添加分类");
		else if(opt == Operation.UPD_CATEGORY)
			setTitle("删除分类");
		this.setUndecorated(true);
		//setResizable(false);
		//setSize(271, 163);
		//setPreferredSize(new Dimension(271, 163));
		getContentPane().setLayout(new BorderLayout());
		JPanel pnl_contain = new JPanel(new BorderLayout());
		pnl_contain.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED));
		this.setContentPane(pnl_contain);
		
		FlowLayout fl = new FlowLayout();
		//pnl_content.setVgap(32);
		contentPanel.setLayout(fl);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lbl_name = new JLabel("分类名称：");
			contentPanel.add(lbl_name);
		}
		{
			txf_name = new JTextField(cate.getName());
			txf_name.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					txf_name.selectAll();
				}
			});
			txf_name.addActionListener(new ConfirmListener());
			txf_name.addCaretListener(new CaretListener(){
				@Override
				public void caretUpdate(CaretEvent arg0) {
					if(txf_name.getText().trim().isEmpty())
						btn_confirm.setEnabled(false);
					else
						btn_confirm.setEnabled(true);
				}
			});
			contentPanel.add(txf_name);
			txf_name.setColumns(15);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.X_AXIS));
			buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
			
			buttonPane.add(Box.createHorizontalStrut(10));
			lbl_msg = new JLabel();
			lbl_msg.setForeground(Color.RED);
			buttonPane.add(lbl_msg);
			
			buttonPane.add(Box.createHorizontalGlue());
			{
				btn_confirm = new JButton("确定(Enter)");
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
			String cateName = txf_name.getText().trim();
			if(cateName.equals("")){
				lbl_msg.setText("分类名称不能为空！");
				return;
			}
			CategoryBLService catebl = new CategoryBL();
			cate.setName(cateName);
			ResultMessage result = null;
			if(opt == Operation.ADD_CATEGORY){
				result = catebl.addCategory(cate);
			}
			else if(opt == Operation.UPD_CATEGORY)
				result = catebl.updateCategory(cate);
			setResult(result);
			switch(result){
			case ITEM_EXIST:
				lbl_msg.setText("同名的分类已经存在！");
				break;
			case REMOTE_FAIL:
				lbl_msg.setText("网络错误！");
				break;
			case SUCCESS:
				//lbl_msg.setText("操作成功！");
				setVisible(false);
				break;
			default:
				lbl_msg.setText("未知错误！");
				break;			
			}
		}
		
	}

}
