package gui.present;

import javax.swing.JFrame;

import inventoryBL.InventoryBL;
import inventoryBLService.InventoryBLService;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import productBL.ProductItem;
import enums.ResultMessage;
import enums.Status;
import gui.GUIUtility;
import gui.MainFrame;
import gui.promotion.AddProductInterface;
import gui.promotion.ChangeProductFrame;
import gui.promotion.ChooseProductFrame;
import gui.promotion.ItemExistException;
import VO.PresentVO;

public class PresentUpdateFrame extends JFrame implements AddProductInterface {

	private static final long serialVersionUID = -5959527359303398419L;
	private JTable table;
	private PresentUpdateFrame thisFrame;
	private JLabel label_name;
	private PresentTableModel model;
	private JLabel label_date;
	private PresentVO present;
	private JLabel label_status;
	private PresentVO vo;
	
	public PresentUpdateFrame(PresentVO present) {
		this.present = present;
		thisFrame = this;
		this.vo = present;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 300);
		GUIUtility.setCenter(this);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton button = new JButton("提交");
		button.addActionListener(new SubmitListener());
		panel.add(button);
		
		JButton btnNewButton = new JButton("保存");
		btnNewButton.addActionListener(new SaveListener());;
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
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.NORTH);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{75, 72, 0, 60, 72, 0, 72, 0};
		gbl_panel_3.rowHeights = new int[]{18, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblId = new JLabel("填写日期：");
		lblId.setFont(new Font("宋体", Font.PLAIN, 15));
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblId.insets = new Insets(0, 0, 0, 5);
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		panel_3.add(lblId, gbc_lblId);
		
		label_date = new JLabel("2014-12-13");
		label_date.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label_date = new GridBagConstraints();
		gbc_label_date.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_date.insets = new Insets(0, 0, 0, 5);
		gbc_label_date.gridx = 1;
		gbc_label_date.gridy = 0;
		panel_3.add(label_date, gbc_label_date);
		
		JLabel label = new JLabel("填写人：");
		label.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 3;
		gbc_label.gridy = 0;
		panel_3.add(label, gbc_label);
		
		label_name = new JLabel("许珂磊");
		label_name.setHorizontalAlignment(SwingConstants.CENTER);
		label_name.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label_name = new GridBagConstraints();
		gbc_label_name.anchor = GridBagConstraints.WEST;
		gbc_label_name.insets = new Insets(0, 0, 0, 5);
		gbc_label_name.gridx = 4;
		gbc_label_name.gridy = 0;
		panel_3.add(label_name, gbc_label_name);
		
		label_status = new JLabel("草稿");
		label_status.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label_status = new GridBagConstraints();
		gbc_label_status.anchor = GridBagConstraints.EAST;
		gbc_label_status.gridx = 6;
		gbc_label_status.gridy = 0;
		panel_3.add(label_status, gbc_label_status);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		
		showInfo();
	}
	
	public void showInfo(){
		if(present == null){
			JOptionPane.showMessageDialog(null, "NULLPOINTER", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
		model = new PresentTableModel(present.getProductList());
		table.setModel(model);
		label_date.setText(present.getDate());
		label_name.setText(present.getOperator());
		label_status.setText(present.getStatus().toString());
	}
	
	public PresentVO getPresent(){
		String date = label_date.getText();
		String operator = label_name.getText();
		ArrayList<ProductItem> list = model.getProductItem();
		PresentVO newPresent = new PresentVO(date, present.getID(), list, operator, Status.DRAFT);
		return newPresent;
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
	
	public void savePresent(Status status, int flag){      //flag = 1 new  flag = 2 updateDraft
		String date = label_date.getText();
		String operator = label_name.getText();
		ArrayList<ProductItem> list = model.getProductItem();
		
		if(list.size() == 0){
			JOptionPane.showMessageDialog(null, "未添加任何商品", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}else{
			PresentVO present = new PresentVO(date, vo.getID(), list, operator, status);
			InventoryBLService inventoryService = new InventoryBL();
			ResultMessage message;
			if(flag == 1){
				message = inventoryService.newPresentTable(present);
			}else if(flag == 2){
				message = inventoryService.updatePresentDraft(present);
			}else{
				return;
			}
			
			if(message == ResultMessage.SUCCESS){
				JOptionPane.showMessageDialog(null, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				dispose();
				MainFrame.mf.pnl_stockDocs.buildList();
			}else if(message == ResultMessage.REMOTE_FAIL){
				JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
			}else if(message == ResultMessage.ITEM_NOT_EXIST){
				JOptionPane.showMessageDialog(null, "该赠品单已被删除", "错误", JOptionPane.ERROR_MESSAGE);
				dispose();
				MainFrame.mf.pnl_stockDocs.buildList();
			}
		}
	}
	
	class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			savePresent(Status.SUBMIT, 1);
		}	
	}
	
	class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			savePresent(Status.DRAFT , 2);
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
	
	public static void main(String[] args){
		InventoryBLService service = new InventoryBL();
		PresentVO present = service.getPresentTable(new PresentVO("ZPD-20141214-00001")).data;
		PresentUpdateFrame frame =new PresentUpdateFrame(present);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

}
