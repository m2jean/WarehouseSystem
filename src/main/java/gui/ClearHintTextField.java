package gui;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ClearHintTextField extends HintTextField {

	private static final long serialVersionUID = 107824081912464916L;

	public ClearHintTextField(String hint) {
		super(hint);
		addFocusListener(new SelectFocusListener());
	}

	public ClearHintTextField(String hint, Color hintc) {
		super(hint, hintc);
		addFocusListener(new SelectFocusListener());
	}

	private class SelectFocusListener extends FocusAdapter{
		@Override
		public void focusGained(FocusEvent fe){
			setText("");
			setForeground(Color.BLACK);
		}
	}
}
