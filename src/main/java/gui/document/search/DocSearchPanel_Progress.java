package gui.document.search;

import enums.DocumentType;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import gui.datecombobox.ChooseDatePanel;
import gui.document.component.DocPanel;
import gui.document.doclist.DocListPanel;
import gui.document.vo.TradeVO;

public class DocSearchPanel_Progress extends DocSearchPanel {

	private static final long serialVersionUID = -8597229096674677537L;
	private JTextField txf_id;
	private JTextField txf_operator;
	private JComboBox<DocumentType> cbx_type;
	private JTextField txf_customer;
	private JTextField txf_stock;
	private ChooseDatePanel pnl_date;

	public DocSearchPanel_Progress(DocListPanel pnl_list) {
		super(pnl_list);
		setBorder(new EmptyBorder(5, 0, 0, 0));

		GridBagLayout gbl_pnl_search = new GridBagLayout();
		gbl_pnl_search.columnWidths = new int[]{46, 127, 71, 31, 0, 0, 43, 0, 0};
		gbl_pnl_search.rowHeights = new int[]{23, 0, 0};
		gbl_pnl_search.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnl_search.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_pnl_search);
		
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
		add(pnl_date, gbc_chooseDatePanel);
		
		JLabel label = new JLabel("客户：");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 4;
		gbc_label.gridy = 0;
		add(label, gbc_label);
		
		txf_customer = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 5;
		gbc_textField.gridy = 0;
		add(txf_customer, gbc_textField);
		txf_customer.setColumns(10);
		
		JLabel lbl_type = new JLabel("种类：");
		lbl_type.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lbl_type = new GridBagConstraints();
		gbc_lbl_type.fill = GridBagConstraints.BOTH;
		gbc_lbl_type.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_type.gridx = 6;
		gbc_lbl_type.gridy = 0;
		add(lbl_type, gbc_lbl_type);
		
		cbx_type = new JComboBox<DocumentType>();
		GridBagConstraints gbc_cbx_type = new GridBagConstraints();
		gbc_cbx_type.fill = GridBagConstraints.BOTH;
		gbc_cbx_type.insets = new Insets(0, 0, 5, 0);
		gbc_cbx_type.gridx = 7;
		gbc_cbx_type.gridy = 0;
		add(cbx_type, gbc_cbx_type);
		
		JLabel lbl_id = new JLabel("编号：");
		GridBagConstraints gbc_lbl_id = new GridBagConstraints();
		gbc_lbl_id.anchor = GridBagConstraints.EAST;
		gbc_lbl_id.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_id.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_id.gridx = 0;
		gbc_lbl_id.gridy = 1;
		add(lbl_id, gbc_lbl_id);
		lbl_id.setHorizontalAlignment(SwingConstants.TRAILING);
		
		txf_id = new JTextField();
		GridBagConstraints gbc_txf_id = new GridBagConstraints();
		gbc_txf_id.fill = GridBagConstraints.BOTH;
		gbc_txf_id.insets = new Insets(0, 0, 0, 5);
		gbc_txf_id.gridx = 1;
		gbc_txf_id.gridy = 1;
		add(txf_id, gbc_txf_id);
		txf_id.setColumns(10);
		
		JLabel lbl_operator = new JLabel("操作员：");
		GridBagConstraints gbc_lbl_operator = new GridBagConstraints();
		gbc_lbl_operator.fill = GridBagConstraints.BOTH;
		gbc_lbl_operator.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_operator.gridx = 2;
		gbc_lbl_operator.gridy = 1;
		add(lbl_operator, gbc_lbl_operator);
		lbl_operator.setHorizontalAlignment(SwingConstants.TRAILING);
		
		txf_operator = new JTextField();
		GridBagConstraints gbc_txf_operator = new GridBagConstraints();
		gbc_txf_operator.fill = GridBagConstraints.BOTH;
		gbc_txf_operator.insets = new Insets(0, 0, 0, 5);
		gbc_txf_operator.gridx = 3;
		gbc_txf_operator.gridy = 1;
		add(txf_operator, gbc_txf_operator);
		txf_operator.setColumns(5);
		
		JLabel label_1 = new JLabel("仓库：");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 0, 5);
		gbc_label_1.gridx = 4;
		gbc_label_1.gridy = 1;
		add(label_1, gbc_label_1);
		
		txf_stock = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 0, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 5;
		gbc_textField_1.gridy = 1;
		add(txf_stock, gbc_textField_1);
		txf_stock.setColumns(10);
		
		JButton btn_search = new JButton("搜索");
		GridBagConstraints gbc_btn_search = new GridBagConstraints();
		gbc_btn_search.fill = GridBagConstraints.BOTH;
		gbc_btn_search.gridx = 7;
		gbc_btn_search.gridy = 1;
		btn_search.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				docpanel.buildList();
				docpanel.removeAllItemListener();
				ArrayList<DocPanel> list = docpanel.getList();
				String id = txf_id.getText().trim();
				String operator = txf_operator.getText().trim();
				String start = pnl_date.getStartDate();
				String end = pnl_date.getEndDate();
				String customer = txf_customer.getText().trim();
				String stock = txf_stock.getText().trim();
				DocumentType type = cbx_type.getItemAt(cbx_type.getSelectedIndex());
				ArrayList<DocPanel> result = new ArrayList<DocPanel>(20);
				for(DocPanel dp:list){
					if(id.isEmpty() || dp.getID().equals(id))
						if(operator.isEmpty() || dp.getOperator().equals(operator))
							if(dp.getDate().compareTo(start)>=0 && dp.getDate().compareTo(end)<=0)
								if(type==DocumentType.ALL || type==dp.getType())
									if(!(dp instanceof TradeVO)){
										result.add(dp);
									}
									else if(customer.isEmpty() || ((TradeVO)dp).getCustomer().contains(customer))
										if(stock.isEmpty() || ((TradeVO)dp).getWarehouse().contains(stock))
											result.add(dp);
									
				docpanel.setList(result);
				}
			}
		});
		add(btn_search, gbc_btn_search);
	}

	@Override
	public void setDocumentTypes(DocumentType[] types) {
		for(int i = 0;i < types.length;i++)
			cbx_type.insertItemAt(types[i],i);
		cbx_type.setSelectedIndex(0);
	}

}
