package gui.promotion;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import productBL.ProductItem;
import enums.Status;
import factory.Factory;
import gui.GUIUtility;
import gui.present.PresentTableModel;
import gui.promotion.AddProductInterface;
import gui.promotion.ChangeProductFrame;
import gui.promotion.ChooseProductFrame;
import gui.promotion.ItemExistException;
import businesslogic.userbl.UserBL;
import VO.PresentVO;

public class AddPresentFrame extends JFrame implements AddProductInterface {

	private static final long serialVersionUID = 7746263983165683697L;
	private JTable table;
	private AddPresentFrame thisFrame;
	private PresentTableModel model;
	private PresentVO present;
	private AddPresentInterface parentFrame;
	
	public AddPresentFrame(AddPresentInterface frame, PresentVO present) {
		this.present = present;
		parentFrame = frame;
		thisFrame = this;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 300);
		GUIUtility.setCenter(this);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addActionListener(new ConfirmListener());;
		panel.add(btnNewButton);
		
		JButton button_4 = new JButton("返回");
		button_4.addActionListener(new CloseListener());
		panel.add(button_4);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.SOUTH);
		
		JButton button_1 = new JButton("添加赠品");
		button_1.addActionListener(new AddListener());
		panel_2.add(button_1);
		
		JButton button_2 = new JButton("修改赠品");
		button_2.addActionListener(new UpdateListener());
		panel_2.add(button_2);
		
		JButton button_3 = new JButton("删除赠品");
		button_3.addActionListener(new DeleteListener());
		panel_2.add(button_3);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		
		showInfo();
	}
	
	public void showInfo(){
		ArrayList<ProductItem> list;
		if(present == null){
			JOptionPane.showMessageDialog(null, "暂未添加赠品", "提示", JOptionPane.INFORMATION_MESSAGE);
			list = null;
		}else{
			list = present.getProductList();
		}
		model = new PresentTableModel(list);
		table.setModel(model);
	}
	
	public PresentVO getPresent(){
		ArrayList<ProductItem> list = model.getProductItem();
		if(list.size() == 0){
			return null;
		}else{
			String date = new Factory().getDate();
			String operator = new UserBL().getCurrent().getName();
			return new PresentVO(null, date, list, operator, Status.DRAFT);
		}
	}
	
	class AddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ChooseProductFrame frame = new ChooseProductFrame(thisFrame);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
		}	
	}
	
	class UpdateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(table.getSelectedRowCount() == 0){
				return;
			}
			
			int row = table.getSelectedRow();
			if(row < 0){
				return;
			}
			ProductItem item = model.getItem(row);
			ChangeProductFrame frame = new ChangeProductFrame(thisFrame, item);
			frame.setVisible(true);
		}	
	}
	
	class DeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(table.getSelectedRowCount() == 0){
				return;
			}
			
			int row = table.getSelectedRow();
			if(row < 0){
				return;
			}
			model.deleteItem(row);
			model.fireTableDataChanged();
		}	
	}
	
	class ConfirmListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			parentFrame.setPresent(getPresent());
			dispose();
		}	
	}
	
	class CloseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}	
	}

	public void addProduct(ProductItem item, double total) throws ItemExistException {
		model.addItem(item);
		model.fireTableDataChanged();
	}

	public void updateProduct(ProductItem item, double difference) {
		model.updateItem(item);
		model.fireTableDataChanged();	
	}

}