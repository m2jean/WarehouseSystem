package gui.document.component;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class BoundedCheckBox extends JCheckBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7084886857284081501L;
	private JPanel parentPanel;
	
	public BoundedCheckBox(String content,JPanel panel){
		super(content);
		parentPanel = panel;
	}
	
	public JPanel getParentPanel(){
		return parentPanel;
	}
}
