package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;

import javax.swing.text.JTextComponent;

public class GUIUtility {

	public static void setCenter(Component comp){
		Dimension dim = comp.getSize();
		int width = (int) dim.getWidth();
		int height = (int) dim.getHeight();
		int sWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	    int sHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	    comp.setLocation(sWidth/2 - width/2, sHeight/2 - height/2);
	}
	
	public static FocusListener getSelectFocusListener(){
		return new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(e.getSource() instanceof JTextComponent)
					((JTextComponent)e.getSource()).selectAll();
			}
		};
	}
	
	/*public static FocusListener getFocusListenerForInt(JTextField txf,Component parent){
		return new IntegerInputListener(txf,parent);
	}
	public static FocusListener getFocusListenerForDouble(JTextField txf,Component parent){
		return new DoubleInputListener(txf,parent);
	}
	
	private static class IntegerInputListener implements FocusListener{
		private String temp = "";
		private JTextField txf;
		private Component parent;
		
		IntegerInputListener(JTextField txf,Component parent){
			this.txf = txf;
			this.parent = parent;
		}
		
		@Override
		public void focusGained(FocusEvent arg0) {
			txf.selectAll();
			temp = txf.getText();
		}
		@Override
		public void focusLost(FocusEvent e) {
			if(!txf.getText().trim().matches("\\d+")){
				JOptionPane.showMessageDialog(parent, "请输入合法的整数！");
				txf.setText(temp);
			}			
		}		
	}
	
	private static class DoubleInputListener implements FocusListener{
		private String temp = "";
		private JTextField txf;
		private Component parent;
		
		DoubleInputListener(JTextField txf,Component parent){
			this.txf = txf;
			this.parent = parent;
		}
		
		@Override
		public void focusGained(FocusEvent arg0) {
			txf.selectAll();
			temp = txf.getText();
		}
		@Override
		public void focusLost(FocusEvent e) {
			if(!txf.getText().trim().matches("\\d*\\.?\\d+")){
				JOptionPane.showMessageDialog(parent, "请输入合法的小数！");
				txf.setText(temp);
			}			
		}		
	}*/
	
	public static String formatDouble(double d){
		DecimalFormat nf = new DecimalFormat("##0.00");
		nf.setDecimalSeparatorAlwaysShown(false);
		nf.setMaximumFractionDigits(2);
		return nf.format(d);
	}
}
