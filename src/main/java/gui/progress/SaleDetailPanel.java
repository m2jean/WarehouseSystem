package gui.progress;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import customerBL.CustomerBL;
import customerBLService.CustomerBLService;
import productBL.ProductBL;
import productBLService.ProductBLService;
import saleBL.SaleBL;
import saleBL.SaleLog;
import saleBLService.SaleBLService;
import VO.CustomerVO;
import VO.ProductVO;
import enums.DataMessage;
import enums.ResultMessage;
import factory.ExportToExcel;
import gui.datecombobox.ChooseDatePanel;
import gui.document.component.DocPanel;

public class SaleDetailPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7022552456188371843L;
	protected DocPanel lastSelected;
	protected boolean hasCommitted;
	protected JPanel pnl_func;
	private JTextField textField_warehouse;
	private JTextField textField_salesman;
	private JTable table_product;
	private ChooseDatePanel pnl_date;
	private JComboBox comboBox_product;
	private JComboBox comboBox_customer;
	private ArrayList<SaleLog> saleList;
	private ArrayList<ProductVO> productList;
	private ArrayList<CustomerVO> customerList;
	private String[] productBoxData;
	private String[] customerBoxData;
	private SaleDetailPanel thisPanel;
	
	/**
	 * Create the panel.
	 */
	public SaleDetailPanel() {
		thisPanel = this;
		getInfo();                 //获取商品、客户列表
		
		setPreferredSize(new Dimension(800, 640));
		setLayout(new BorderLayout(0, 0));
		
		JPanel pnl_north = new JPanel();
		pnl_north.setBorder(new EmptyBorder(0, 5, 5, 5));
		add(pnl_north, BorderLayout.NORTH);
		pnl_north.setLayout(new BorderLayout(0, 0));
		
		JPanel pnl_status = new JPanel();
		pnl_north.add(pnl_status, BorderLayout.WEST);
		pnl_status.setBorder(null);
		pnl_status.setLayout(new BoxLayout(pnl_status, BoxLayout.Y_AXIS));
		
		Component verticalGlue_2 = Box.createVerticalGlue();
		pnl_status.add(verticalGlue_2);
		
		Component verticalGlue = Box.createVerticalGlue();
		pnl_status.add(verticalGlue);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		pnl_status.add(verticalGlue_1);
		
		JPanel pnl_search = new JPanel();
		pnl_search.setBorder(new EmptyBorder(5, 0, 0, 0));
		pnl_north.add(pnl_search, BorderLayout.EAST);
		GridBagLayout gbl_pnl_search = new GridBagLayout();
		gbl_pnl_search.columnWidths = new int[]{46, 127, 71, 31, 0, 0, 43, 0, 0};
		gbl_pnl_search.rowHeights = new int[]{23, 0, 0};
		gbl_pnl_search.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnl_search.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		pnl_search.setLayout(gbl_pnl_search);
		
		pnl_date = new ChooseDatePanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnl_date.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_chooseDatePanel = new GridBagConstraints();
		gbc_chooseDatePanel.anchor = GridBagConstraints.WEST;
		gbc_chooseDatePanel.gridwidth = 4;
		gbc_chooseDatePanel.fill = GridBagConstraints.VERTICAL;
		gbc_chooseDatePanel.insets = new Insets(0, 0, 5, 5);
		gbc_chooseDatePanel.gridx = 0;
		gbc_chooseDatePanel.gridy = 0;
		pnl_search.add(pnl_date, gbc_chooseDatePanel);
		
		JLabel label = new JLabel("仓库：");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 4;
		gbc_label.gridy = 0;
		pnl_search.add(label, gbc_label);
		
		textField_warehouse = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 5;
		gbc_textField.gridy = 0;
		pnl_search.add(textField_warehouse, gbc_textField);
		textField_warehouse.setColumns(10);
		
		JLabel label_product = new JLabel("商品：");
		GridBagConstraints gbc_label_product = new GridBagConstraints();
		gbc_label_product.anchor = GridBagConstraints.EAST;
		gbc_label_product.insets = new Insets(0, 0, 0, 5);
		gbc_label_product.gridx = 0;
		gbc_label_product.gridy = 1;
		pnl_search.add(label_product, gbc_label_product);
		
		comboBox_product = new JComboBox(productBoxData);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		pnl_search.add(comboBox_product, gbc_comboBox);
		
		JLabel label_2 = new JLabel("客户：");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.insets = new Insets(0, 0, 0, 5);
		gbc_label_2.gridx = 2;
		gbc_label_2.gridy = 1;
		pnl_search.add(label_2, gbc_label_2);
		
		comboBox_customer = new JComboBox(customerBoxData);
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 3;
		gbc_comboBox_1.gridy = 1;
		pnl_search.add(comboBox_customer, gbc_comboBox_1);
		
		JLabel label_1 = new JLabel("业务员：");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 0, 5);
		gbc_label_1.gridx = 4;
		gbc_label_1.gridy = 1;
		pnl_search.add(label_1, gbc_label_1);
		
		textField_salesman = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 0, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 5;
		gbc_textField_1.gridy = 1;
		pnl_search.add(textField_salesman, gbc_textField_1);
		textField_salesman.setColumns(10);
		
		JButton btn_search = new JButton("搜索");
		btn_search.addActionListener(new SearchListener());
		GridBagConstraints gbc_btn_search = new GridBagConstraints();
		gbc_btn_search.fill = GridBagConstraints.BOTH;
		gbc_btn_search.gridx = 7;
		gbc_btn_search.gridy = 1;
		pnl_search.add(btn_search, gbc_btn_search);
		
		table_product=new JTable();
		showProductInfo(null);
		JScrollPane scrollPane = new JScrollPane(table_product);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);
		
		pnl_func = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnl_func.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(pnl_func, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("导出");
		btnNewButton.addActionListener(new ExportListener());
		pnl_func.add(btnNewButton);
	}
	
	public void getInfo(){
		ProductBLService productService=new ProductBL();
		CustomerBLService customerService=new CustomerBL();
		
		DataMessage<ArrayList<ProductVO>> pMessage=productService.getProductList();
		DataMessage<ArrayList<CustomerVO>> cMessage=customerService.getAllCustomer();
		
		if(pMessage.resultMessage==ResultMessage.REMOTE_FAIL||cMessage.resultMessage==ResultMessage.REMOTE_FAIL){
			JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}else{
			productList=pMessage.data;
			customerList=cMessage.data;
			int productNumber=productList==null?0:productList.size();
			int customerNumber=customerList==null?0:customerList.size();
			productBoxData=new String[productNumber+1];
			customerBoxData=new String[customerNumber+1];
			
			productBoxData[0]="全部";
			customerBoxData[0]="全部";
			for(int i=0;i<productNumber;i++){
				ProductVO product=productList.get(i);
				productBoxData[i+1]=product.getID()+" "+product.getName()+" "+product.getModel();
			}
			
			for(int i=0;i<customerNumber;i++){
				CustomerVO customer=customerList.get(i);
				customerBoxData[i+1]=customer.getID()+" "+customer.getName();
			}
		}
	}
	
	public void showProductInfo(ArrayList<SaleLog> list){
		ProductInfoTable tableModel=new ProductInfoTable(list);
		table_product.setModel(tableModel);
	}
	
	class SearchListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){		
			SaleBLService service=new SaleBL();
			String startDate=pnl_date.getStartDate();
			String endDate=pnl_date.getEndDate();
			
			String product=(String)comboBox_product.getSelectedItem();
			if(product.equals("全部")){
				product="";
			}else{
				product=product.split(" ")[1];
			}
			
			String customer=(String)comboBox_customer.getSelectedItem();
			if(customer.equals("全部")){
				customer="";
			}else{
				customer=customer.split(" ")[1];
			}
			
			String salesman=textField_salesman.getText();
			String warehouse=textField_warehouse.getText();
			
			DataMessage<ArrayList<SaleLog>> message=service.getMingxi(startDate, endDate, product, customer, salesman, warehouse);
			if(message.resultMessage==ResultMessage.SUCCESS){
				saleList=message.data;
				showProductInfo(saleList);
			}else if(message.resultMessage==ResultMessage.IS_EMPTY){
				JOptionPane.showMessageDialog(null, "没有符合要求的销售数据", "提示", JOptionPane.INFORMATION_MESSAGE);
			}else if(message.resultMessage==ResultMessage.REMOTE_FAIL){
				JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
	}
	
	class ExportListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(saleList==null){
				JOptionPane.showMessageDialog(null, "没有可导出的数据", "错误", JOptionPane.ERROR_MESSAGE);
			}else{
				JFileChooser chooser = new JFileChooser();
				chooser.setSelectedFile(new File("销售明细.xls"));
				int result = chooser.showSaveDialog(thisPanel);
				if(result == JFileChooser.APPROVE_OPTION){
					File file = chooser.getSelectedFile();
					ExportToExcel tool = new ExportToExcel();
					String filepath = getPath(file);
					tool.toExcel(saleList, filepath);
					JOptionPane.showMessageDialog(null, "导出成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		
		private String getPath(File file){
			int count = 0;
			File parent = file.getParentFile();
			String name = file.getName();
			String originalName = name.split("\\.")[0];
			File[] list = parent.listFiles();
			for(int i=0;i<list.length;i++){
				if(list[i].getName().equals(name)){
					count++;
					name = originalName+"("+count+").xls";
					i=-1;
					continue;
				}
			}
			
			return parent.getPath()+"/"+name;
		}
	}
	
	public static void main(String[] args){    	
		JFrame frame=new JFrame();
		SaleDetailPanel panel=new SaleDetailPanel();
		frame.setSize(800, 640);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
	
}

