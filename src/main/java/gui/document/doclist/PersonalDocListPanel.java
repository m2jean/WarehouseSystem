package gui.document.doclist;

import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Collections;

import enums.ResultMessage;
import gui.MainFrame;
import gui.document.component.DocPanel;
import gui.document.search.DocSearchPanel;
import gui.document.search.DocSearchPanel_General;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public abstract class PersonalDocListPanel extends DocListPanel {

	
	private static final long serialVersionUID = -5684626102091898401L;


	private JButton btn_delete;

	
	/**
	 * Create the panel.
	 */
	public PersonalDocListPanel() {
		/*btn_check = new JButton("查看");
		btn_check.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lastSelected.check();				
			}			
		});
		pnl_func.add(btn_check);
		btn_check.setEnabled(false);*/

		btn_delete = new JButton("删除");
		btn_delete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(getSelectedNum() == 1){
					lastSelected.delete();
				}
				else if(getSelectedNum() > 1){
					int success = 0;
					for(DocPanel doc:getSelected()){
						if(doc.getDeletionResult() == ResultMessage.SUCCESS){
							success++;
						}
					}
					if(getSelectedNum() == success){
						MainFrame.mf.setMessage("成功删除"+String.valueOf(success)+"条单据");
					}
					else
						MainFrame.mf.setMessage("成功删除"+String.valueOf(success)+"条单据，"
							+"失败"+String.valueOf(getSelectedNum()-success)+"条。");
				}
				
				buildList();
			}			
		});
		pnl_func.add(btn_delete);
		btn_delete.setEnabled(false);
		
		/*btn_update = new JButton("修改");
		btn_update.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lastSelected.update();	
			}			
		});
		pnl_func.add(btn_update);
		btn_update.setEnabled(false);*/
	}

	public void setList(ArrayList<DocPanel> list){
		//btn_check.setEnabled(false);
		btn_delete.setEnabled(false);
		//btn_update.setEnabled(false);
		Collections.sort(list);
		addItemListenerForAll(list,new SelectionListener());
		super.setList(list);
	}
	
	private class SelectionListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(selectedNum <= 0){
				//btn_check.setEnabled(false);
				btn_delete.setEnabled(false);
				//btn_update.setEnabled(false);
			}
			/*else if(selectedNum == 1){
				//btn_check.setEnabled(true);
				btn_delete.setEnabled(true);
				//btn_update.setEnabled(true);
			}*/
			else{
				//btn_check.setEnabled(false);
				btn_delete.setEnabled(true);
				//btn_update.setEnabled(false);
			}
			
			if(hasCommitted){
				btn_delete.setEnabled(false);
				//btn_update.setEnabled(false);
			}
		}		
	}
	
	protected DocSearchPanel getSearchPanel(){	
		return new DocSearchPanel_General(this);
	}
	
	protected void checkResult(ResultMessage result, String docname){
		if(result == ResultMessage.SUBMIT_SUCCESS){
			MainFrame.mf.setMessage(docname+"提交成功！");
			buildList();
		}
		else if(result == ResultMessage.SAVE_SUCCESS){
			MainFrame.mf.setMessage(docname+"保存草稿成功！");
			buildList();
		}
		else if(result == ResultMessage.SUBMIT_FAIL){
			MainFrame.mf.setError(docname+"提交失败");
			buildList();
		}
		else if(result == ResultMessage.SAVE_FAILED){
			MainFrame.mf.setError(docname+"保存草稿失败");
			buildList();
		}
		else if(result != ResultMessage.CLOSE){
				MainFrame.mf.setError("操作失败");
		}
	}
}
