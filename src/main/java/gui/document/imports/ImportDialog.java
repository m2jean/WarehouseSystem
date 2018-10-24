package gui.document.imports;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import gui.MainFrame;
import gui.ResultDialog;
import gui.SelectHintTextField;
import gui.document.vo.ImportExportVO;

public abstract class ImportDialog extends ResultDialog {

	private static final long serialVersionUID = -2614159162220167206L;
	private final JPanel contentPanel = new JPanel();
	protected JTextField txf_stock;
	protected JLabel lbl_id;
	protected JLabel lbl_operator;
	protected JTextArea txa_remarks;
	protected ImportExportVO vo;
	protected JLabel lbl_msg;


	/**
	 * Create the dialog.
	 * @param parent
	 */
	public ImportDialog(MainFrame parent) {
		super(parent,true);
		construct();
		setEditable();
	}
	public ImportDialog(MainFrame parent,ImportExportVO imp){
		super(parent,true);
		this.vo = imp;
		construct();
		setEditable();
	}
	
	private void construct(){
		//setResizable(false);
		//setSize(713, 294);
		//GUIUtility.setCenter(this);
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
			
		});
		JPanel cPane = new JPanel(new BorderLayout());
		cPane.setBorder(new EmptyBorder(5, 15, 5, 15));
		this.setContentPane(cPane);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{15, 256, 99, 0, 0};
		//gbl_contentPanel.rowHeights = new int[]{0, 19, 91, 0, 0};
		//gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		//gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		lbl_id = new JLabel("编号：待定");
		GridBagConstraints gbc_lbl_id = new GridBagConstraints();
		gbc_lbl_id.anchor = GridBagConstraints.WEST;
		gbc_lbl_id.gridwidth = 2;
		gbc_lbl_id.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_id.gridx = 0;
		gbc_lbl_id.gridy = 0;
		contentPanel.add(lbl_id, gbc_lbl_id);
		
		JLabel lbl_provider = new JLabel("供应商：");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 0;
		contentPanel.add(lbl_provider, gbc_lblNewLabel_1);
				
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 3;
		gbc_comboBox_1.gridy = 0;
		setCustomerComponent(contentPanel,gbc_comboBox_1);
		
		lbl_operator = new JLabel("操作员：");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPanel.add(lbl_operator, gbc_lblNewLabel);
		
		JLabel lbl_stock = new JLabel("仓库：");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 1;
		contentPanel.add(lbl_stock, gbc_lblNewLabel_2);
		
		txf_stock = new SelectHintTextField("必填");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 1;
		contentPanel.add(txf_stock, gbc_textField_1);
		txf_stock.setColumns(10);
		
		GridBagConstraints gbc_lineItemPanel = new GridBagConstraints();
		gbc_lineItemPanel.gridwidth = 4;
		gbc_lineItemPanel.insets = new Insets(0, 0, 5, 0);
		gbc_lineItemPanel.fill = GridBagConstraints.BOTH;
		gbc_lineItemPanel.gridx = 0;
		gbc_lineItemPanel.gridy = 2;
		setLineItemPanel(contentPanel, gbc_lineItemPanel);
		
		JLabel lbl_remarks = new JLabel("备注：");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 3;
		contentPanel.add(lbl_remarks, gbc_label);
		
		txa_remarks = new JTextArea();
		txa_remarks.setLineWrap(true);
		txa_remarks.setWrapStyleWord(true);
		GridBagConstraints gbc_txa_remarks = new GridBagConstraints();
		gbc_txa_remarks.gridwidth = 3;
		gbc_txa_remarks.fill = GridBagConstraints.BOTH;
		gbc_txa_remarks.gridx = 1;
		gbc_txa_remarks.gridy = 3;
		contentPanel.add(txa_remarks, gbc_txa_remarks);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.X_AXIS));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			
			buttonPane.add(Box.createHorizontalGlue());
			lbl_msg = new JLabel();
			lbl_msg.setForeground(Color.RED);
			buttonPane.add(lbl_msg);

			buttonPane.add(Box.createHorizontalStrut(10));
			addButtons(buttonPane);
			
			buttonPane.add(Box.createHorizontalStrut(5));
		}
	}
	
	protected abstract void addButtons(JPanel buttonPane);
	
	protected void setOperator(String operator){
		lbl_operator.setText("操作员："+operator);
	}

	protected abstract void setEditable();
	protected abstract void setCustomerComponent(JPanel contentPanel,GridBagConstraints gbc);
	protected abstract void setLineItemPanel(JPanel contentPanel,GridBagConstraints gbc);
}
