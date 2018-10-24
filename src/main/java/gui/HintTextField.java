package gui;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

public class HintTextField extends JTextField{

	private static final long serialVersionUID = 5577681559704205192L;
	private String hint;
	private Color c;
	public HintTextField(String hint){
		this(hint,Color.GRAY);
	}
	public HintTextField(String hint, Color hintc){		
		this.hint = hint;
		c = hintc;
		restoreDefault();
		this.addFocusListener(new HintFocusListener());
	}
	
	private void restoreDefault(){
		setText(hint);
		setForeground(c);
	}
	
	@Override
	public void setText(String text){
		super.setText(text);
		this.setForeground(Color.BLACK);
	}
	
	@Override
	public String getText(){
		String text = super.getText();
		if(text.equals(hint) && this.getForeground().equals(c))
			return "";
		return text;
	}
	
	private class HintFocusListener extends FocusAdapter {
		
		@Override
		public void focusLost(FocusEvent fe){
			if(getText().trim().equals("")){
				setForeground(c);
				HintTextField.super.setText(hint);
			}
		}
	}


}
