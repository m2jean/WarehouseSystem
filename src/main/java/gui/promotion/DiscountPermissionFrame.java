package gui.promotion;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import enums.DataMessage;
import enums.ResultMessage;
import enums.UserPermission;
import gui.GUIUtility;
import businesslogic.promotionbl.PromotionBL;
import businesslogicservice.promotionblservice.PromotionBLService;
import javax.swing.SwingConstants;

public class DiscountPermissionFrame extends JFrame {

	private static final long serialVersionUID = 6546389770424874051L;
	private JTextField textField_normal;
	private JTextField textField_manager;
	
	public DiscountPermissionFrame() {
		setSize(300, 190);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GUIUtility.setCenter(this);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton button = new JButton("确认");
		button.addActionListener(new ConfirmListener());
		panel.add(button);
		
		JButton button_1 = new JButton("返回");
		button_1.addActionListener(new CloseListener());
		panel.add(button_1);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		/*panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("29px"),
				ColumnSpec.decode("105px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("86px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("75px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("24px"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("24px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));*/
		
		JLabel label = new JLabel("普通销售人员：");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_1.add(label, "2, 4, left, center");
		
		textField_normal = new JTextField();
		textField_normal.setHorizontalAlignment(SwingConstants.CENTER);
		textField_normal.setFont(new Font("宋体", Font.PLAIN, 15));
		textField_normal.setText("1000");
		panel_1.add(textField_normal, "4, 4, left, top");
		textField_normal.setColumns(10);
		
		JLabel label_1 = new JLabel("销售经理：");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_1.add(label_1, "2, 6");
		
		textField_manager = new JTextField();
		textField_manager.setHorizontalAlignment(SwingConstants.CENTER);
		textField_manager.setFont(new Font("宋体", Font.PLAIN, 15));
		textField_manager.setText("5000");
		panel_1.add(textField_manager, "4, 6, right, top");
		textField_manager.setColumns(10);
		
		showInfo();
	}
	
	public void showInfo(){
		PromotionBLService service = new PromotionBL();
		DataMessage<Integer> m1 = service.getDiscountPermission(UserPermission.SALESMAN);
		DataMessage<Integer> m2 = service.getDiscountPermission(UserPermission.SALES_MANAGER);
		
		if(m1.resultMessage != ResultMessage.SUCCESS || m2.resultMessage != ResultMessage.SUCCESS){
			JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
			dispose();
		}else{
			int normal = m1.data;
			int manager = m2.data;
			textField_normal.setText(String.valueOf(normal));
			textField_manager.setText(String.valueOf(manager));
		}
	}
	
	class ConfirmListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try{
				int normal = Integer.parseInt(textField_normal.getText());
				int manager = Integer.parseInt(textField_manager.getText());
				
				if(normal < 0 || manager < 0){
					JOptionPane.showMessageDialog(null, "折让权限不能小于0", "错误", JOptionPane.ERROR_MESSAGE);
				}else{
					int[] permission = new int[2];
					permission[0] = normal;
					permission[1] = manager;
					PromotionBLService service = new PromotionBL();
					ResultMessage message = service.updateDiscountPermission(permission);
					
					if(message == ResultMessage.SUCCESS){
						JOptionPane.showMessageDialog(null, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}else if(message == ResultMessage.REMOTE_FAIL){
						JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
					}
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, "不要输入数字以外的内容", "错误", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}
	
	class CloseListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	}

}
