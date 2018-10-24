package gui;

import java.awt.Button;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JToggleButton;

public class ButtonFactory {

	private Font f;
	
	public ButtonFactory(Font font){
		f = font;
	}

	public JButton getButton() {
		JButton jb = new JButton();
		jb.setFont(f);
		return jb;
	}

	public JButton getButton(String arg0) {
		JButton jb = new JButton(arg0);
		jb.setFont(f);
		return jb;
	}
	
	public JToggleButton getToggleButton(String text){
		JToggleButton jtb = new JToggleButton(text);
		jtb.setFont(f);
		return jtb;
	}
	
	public FixedSizeButton getFixedSizeButton(String[] texts){
		FixedSizeButton fsb = new FixedSizeButton(texts);
		fsb.setFont(f);
		return fsb;
	}

	public void setButtonFont(Button btn){
		btn.setFont(f);
	}

	public Font getFont() {
		return f;
	}
}
