package gui;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SelectHintTextField extends HintTextField {

	private static final long serialVersionUID = 107824081912464916L;

	public SelectHintTextField(String hint) {
		super(hint);
		addFocusListener(new SelectFocusListener());
	}

	public SelectHintTextField(String hint, Color hintc) {
		super(hint, hintc);
		addFocusListener(new SelectFocusListener());
	}

	private class SelectFocusListener extends FocusAdapter{
		@Override
		public void focusGained(FocusEvent fe){
			if(getText().isEmpty())
				setText("");
			else{
				setForeground(Color.BLACK);
				selectAll();
			}
		}
	}
}
