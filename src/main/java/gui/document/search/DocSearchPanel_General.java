package gui.document.search;

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

import enums.DocumentType;
import gui.ClearHintTextField;
import gui.datecombobox.ChooseDatePanel;
import gui.document.component.DocPanel;
import gui.document.doclist.DocListPanel;

public class DocSearchPanel_General extends DocSearchPanel{

	private static final long serialVersionUID = 6272730614674015215L;
	private JTextField txf_id;
	private JTextField txf_operator;
	private JComboBox<DocumentType> cbx_type;
	private ChooseDatePanel pnl_date;


	public DocSearchPanel_General(DocListPanel pnl_list) {
		super(pnl_list);

		
		setBorder(new EmptyBorder(5, 0, 0, 0));
		GridBagLayout gbl_pnl_search = new GridBagLayout();
		gbl_pnl_search.columnWidths = new int[]{46, 127, 71, 67, 30, 43, 0, 0};
		gbl_pnl_search.rowHeights = new int[]{23, 0, 0};
		gbl_pnl_search.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		JLabel lbl_type = new JLabel("种类：");
		lbl_type.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lbl_type = new GridBagConstraints();
		gbc_lbl_type.fill = GridBagConstraints.BOTH;
		gbc_lbl_type.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_type.gridx = 5;
		gbc_lbl_type.gridy = 0;
		add(lbl_type, gbc_lbl_type);
		
		cbx_type = new JComboBox<DocumentType>();
		GridBagConstraints gbc_cbx_type = new GridBagConstraints();
		gbc_cbx_type.fill = GridBagConstraints.BOTH;
		gbc_cbx_type.insets = new Insets(0, 0, 5, 0);
		gbc_cbx_type.gridx = 6;
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
		
		txf_id = new ClearHintTextField("默认为任意编号");
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
		
		txf_operator = new ClearHintTextField("默认为任意操作员");
		GridBagConstraints gbc_txf_operator = new GridBagConstraints();
		gbc_txf_operator.fill = GridBagConstraints.BOTH;
		gbc_txf_operator.insets = new Insets(0, 0, 0, 5);
		gbc_txf_operator.gridx = 3;
		gbc_txf_operator.gridy = 1;
		add(txf_operator, gbc_txf_operator);
		txf_operator.setColumns(5);
		
		JButton btn_search = new JButton("搜索");
		GridBagConstraints gbc_btn_search = new GridBagConstraints();
		gbc_btn_search.fill = GridBagConstraints.BOTH;
		gbc_btn_search.gridx = 6;
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
				DocumentType type = cbx_type.getItemAt(cbx_type.getSelectedIndex());
				ArrayList<DocPanel> result = new ArrayList<DocPanel>(20);
				for(DocPanel dp:list){
					if(id.isEmpty() || dp.getID().equals(id))
						if(operator.isEmpty() || dp.getOperator().equals(operator))
							if(dp.getDate().compareTo(start)>=0 && dp.getDate().compareTo(end)<=0)
								if(type==DocumentType.ALL || type==dp.getType())
									result.add(dp);
				}
				docpanel.setList(result);
			}
		});
		add(btn_search, gbc_btn_search);	
	}
	
	public void setDocumentTypes(DocumentType[] types){
		for(int i = 0;i < types.length;i++)
			cbx_type.insertItemAt(types[i],i);
		cbx_type.setSelectedIndex(0);
	}
}
