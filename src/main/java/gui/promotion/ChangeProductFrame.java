package gui.promotion;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;

import VO.ProductVO;
import gui.GUIUtility;
import productBL.ProductBL;
import productBL.ProductItem;
import productBLService.ProductBLService;

import java.awt.Font;

public class ChangeProductFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -634636355183390266L;
	private AddProductInterface parentFrame;
	private ProductItem item;
	private JComboBox comboBox;
	private JSpinner spinner;
	String[] data;
	
	public ChangeProductFrame(AddProductInterface parentFrame, ProductItem item) {
		this.parentFrame = parentFrame;
		this.item = item;
		
		GUIUtility.setCenter(this);
		setSize(300, 180);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		getProductData();
		
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
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{17, 38, 36, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel label = new JLabel("选择商品：");
		label.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 1;
		panel_1.add(label, gbc_label);
		
		comboBox = new JComboBox(data);
		comboBox.setFont(new Font("宋体", Font.PLAIN, 15));
		//comboBox.setEnabled(false);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		panel_1.add(comboBox, gbc_comboBox);
		
		JLabel label_1 = new JLabel("数量：");
		label_1.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 2;
		panel_1.add(label_1, gbc_label_1);
		
		spinner = new JSpinner();
		spinner.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 2;
		panel_1.add(spinner, gbc_spinner);
		
		showInfo();
	}
	
	public void showInfo(){
		String name = item.getProductID()+" "+item.getProductName()+" "+item.getProductModel();
		comboBox.setSelectedItem(name);
		spinner.setValue(item.getNumber());
	}
	
	public void getProductData(){
		String name = item.getProductID()+" "+item.getProductName()+" "+item.getProductModel();
		data = new String[1];
		data[0] = name;
	}
	
	class ConfirmListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int number = (int)spinner.getValue();
			if(number <= 0){
				JOptionPane.showMessageDialog(null, "数量至少为1", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}else{
				ProductBLService service = new ProductBL();
				ProductVO vo = service.getProduct(new ProductVO(item.getProductID())).data;
				double price = vo.getPriceOut();
				ProductItem pi = new ProductItem(item.getProductID(), item.getProductName(),
						item.getProductModel(), number);
				double difference = price*(number-item.getNumber());
				parentFrame.updateProduct(pi, difference);
				dispose();
			}
		}
		
	}
	
	class CloseListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
		
	}

}
