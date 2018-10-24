package gui.message;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

public class MessageItemPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4251962147170025720L;
	private JTextArea txf_content;
	private JTextField txf_date;
	@SuppressWarnings("unused")
	private JTextField txf_title;
	private JPanel panel_1;

	/**
	 * Create the panel.
	 */
	
	public MessageItemPanel(String date,String content,boolean isnew) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setSize(270,110);
		this.setPreferredSize(new Dimension(270,110));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panel);
		
		txf_date = new JTextField(date);
		txf_date.setBackground(UIManager.getColor("Button.background"));
		panel.add(txf_date);
		txf_date.setColumns(15);
		txf_date.setEditable(false);
		
		if(isnew){
			JLabel lbl_read = new JLabel("New！");
			lbl_read.setForeground(Color.RED);
			lbl_read.setFont(new Font("宋体", Font.BOLD, 12));
			panel.add(lbl_read);
		}
		/*
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		add(panel_1);
		

		txf_title = new JTextField(title);
		txf_title.setBackground(UIManager.getColor("Button.background"));
		panel_1.add(txf_title);
		txf_title.setColumns(15);
		*/
		
		panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 0, 0, 10));
		add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		txf_content = new JTextArea(content);
		panel_1.add(txf_content);
		txf_content.setEditable(false);
		txf_content.setWrapStyleWord(true);
		txf_content.setLineWrap(true);
		txf_content.setBackground(UIManager.getColor("Button.background"));
		txf_content.setColumns(10);
	}

	
}
