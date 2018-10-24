package gui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class FixedSizeButton extends JButton {

	private static final long serialVersionUID = -3289666496868130319L;
	private int wid;
	//private Font f;
	private String[] txts; 
	
	public FixedSizeButton(String[] texts) {
		super(texts[0]);
		//f = font;
		txts = texts;
		wid = getMaxWid(txts);
		
		//this.setFont(f);
		
	}

	public void setWidth(){
		if(wid != 0){
			setPreferredSize(new Dimension(wid, (int)this.getPreferredSize().getHeight()));
			setMinimumSize(new Dimension(wid, (int)this.getMinimumSize().getHeight()));
			setMaximumSize(new Dimension(wid, (int)this.getMaximumSize().getHeight()));
		}
	}
	
	private static int getMaxWid(String[] txts){
		JButton btn = new JButton();
		
		int btn_wid = 0;
		for(String msg: txts){
			btn.setText(msg);
			double testwid = btn.getPreferredSize().getWidth();
			if(testwid > btn_wid) btn_wid = (int) testwid;
		}
		
		return btn_wid;
	}
	
	@Override
	public void setText(String text){
		super.setText(text);
		setWidth();
	}
	
	@Override
	public Dimension getPreferredSize(){
		Dimension d = new Dimension(wid, (int)super.getPreferredSize().getHeight());
		return d;
	}
	
	@Override
	public Dimension getMinimumSize(){
		Dimension d = new Dimension(wid, (int)super.getMinimumSize().getHeight());
		return d;
	}
	
	@Override
	public Dimension getMaximumSize(){
		Dimension d = new Dimension(wid, (int)super.getMaximumSize().getHeight());
		return d;
	}
	
	@Override
	public void setFont(Font font){
		super.setFont(font);
		if(txts != null)
			wid = getMaxWid(txts);
		setWidth();		
	}
}
