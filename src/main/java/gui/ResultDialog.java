package gui;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import enums.ResultMessage;

public class ResultDialog extends JDialog {
	private static final long serialVersionUID = -8107087901285000251L;
	private ResultMessage result;

	public ResultDialog(Frame parent, boolean modal) {
		super(parent, modal);
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				result = ResultMessage.CLOSE;
				setVisible(false);
			}
		});
	}

	public ResultDialog(JDialog parent, boolean modal) {
		super(parent, modal);
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				result = ResultMessage.CLOSE;
				setVisible(false);
			}
		});
	}
	
	protected final void close(){
		result = ResultMessage.CLOSE;
		setVisible(false);
	}
	
	protected void setResult(ResultMessage result){
		this.result = result;
	}

	public final ResultMessage getResult(){
		return result;
	}

	public static ResultMessage getResultAndDispose(ResultDialog dia){
		ResultMessage ret = dia.getResult();
		dia.dispose();
		return ret;
	}
}
