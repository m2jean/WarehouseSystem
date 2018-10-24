package gui;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

public class DefaultTextField extends JTextField{

	private static final long serialVersionUID = -1879320271868560538L;
	private String deftxt = "";
	
	public DefaultTextField(){
		this.addFocusListener(new SelectFocusListener());
	}
	public DefaultTextField(String defaultText){
		super(defaultText);
		deftxt = defaultText;
		this.addFocusListener(new SelectFocusListener());
	}
	
	public void setDefaultText(String text){
		super.setText(text);
		deftxt = text;
	}

private class SelectFocusListener extends FocusAdapter {
	@Override
	public void focusGained(FocusEvent e) {
		selectAll();
	}
	
	@Override
	public void focusLost(FocusEvent fe){
		if(getText().trim().equals("")){
			setText(deftxt);
		}
	}
}

}
