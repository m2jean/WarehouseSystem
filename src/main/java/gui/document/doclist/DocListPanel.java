package gui.document.doclist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;

import enums.DocumentType;
import enums.Status;
import gui.MainFrame;
import gui.document.component.DocPanel;
import gui.document.search.DocSearchPanel;

public abstract class DocListPanel extends JPanel {
	

	private static final long serialVersionUID = -1625736822766000743L;
	protected ArrayList<DocPanel> list;
	protected JPanel pnl_list;
	protected int selectedNum = 0;
	//private JLabel lbl_selected;
	protected DocPanel lastSelected;
	protected boolean hasCommitted = true;
	protected boolean hasExamined = true;
	protected boolean hasRed = true;
	protected JPanel pnl_func;
	protected DocSearchPanel pnl_search;
	private JScrollPane scrollPane;

	public DocListPanel() {
		setPreferredSize(new Dimension(800, 640));
		setLayout(new BorderLayout(0, 0));
		
		JPanel pnl_north = new JPanel();
		pnl_north.setBorder(new EmptyBorder(0, 5, 5, 5));
		add(pnl_north, BorderLayout.NORTH);
		pnl_north.setLayout(new BorderLayout(0, 0));
		
		JPanel pnl_status = new JPanel();
		pnl_north.add(pnl_status, BorderLayout.WEST);
		pnl_status.setBorder(null);
		pnl_status.setLayout(new BoxLayout(pnl_status, BoxLayout.Y_AXIS));
		
		pnl_search = getSearchPanel();
		pnl_search.setDocumentTypes(getDocmentTypes());
		pnl_north.add(pnl_search, BorderLayout.EAST);
		
		pnl_status.add(Box.createVerticalGlue());
		
		JButton btn_clear = new JButton("清除全部");
		btn_clear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clearAll();
			}			
		});
		pnl_status.add(btn_clear);
		
		//lbl_selected = new JLabel("选中了0条单据");
		//pnl_status.add(lbl_selected);
		
		scrollPane = new JScrollPane();
		//scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.CENTER);
		
		pnl_list = new JPanel();
		pnl_list.setLayout(new BoxLayout(pnl_list, BoxLayout.Y_AXIS));
		scrollPane.setViewportView(pnl_list);
		
		pnl_func = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnl_func.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(pnl_func, BorderLayout.SOUTH);
		
		align();
	}
	
	protected abstract DocSearchPanel getSearchPanel();
	protected abstract DocumentType[] getDocmentTypes();
	
	public abstract void buildList();
	public void setList(ArrayList<DocPanel> doclist){
		Collections.sort(doclist);
		this.list = doclist;
		
		pnl_list.removeAll();
		for(int i = doclist.size()-1;i >= 0;i--){
			pnl_list.add(doclist.get(i));
		}
		//pnl_list.add(Box.createVerticalStrut(640-95));
		addItemListenerForAll(list,new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				hasCommitted = false;
				hasExamined = false;
				hasRed = false;
				if(e.getStateChange() == ItemEvent.SELECTED){
					setSelectedNum(++selectedNum);
				}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
				else{
					setSelectedNum(--selectedNum);
				}
				for(DocPanel dp:list){
					if(dp.isSelected()){
						lastSelected = dp;
						if(dp.getStatus()!=Status.DRAFT){
							hasCommitted = true;
							if(dp.getStatus()!=Status.SUBMIT){
								hasExamined = true;
								if(dp.isRed()){
									hasRed = true;
									break;
								}
							}
						}
					}
				}
			}
		});
		
		hasCommitted = false;
		hasExamined = false;
		hasRed = false;
		for(DocPanel dp: list)
			dp.setSelected(false);
		align();
	}
	
	private void align(){
		//scrollPane.getViewport().setViewPosition(new Point(0,0));
		JViewport vp = new JViewport();
		vp.setView(pnl_list);
		scrollPane.setViewport(vp);
		vp.setViewPosition(new Point(0,0));
		//Point p = scrollPane.getViewport().getViewPosition();
		//Dimension d = scrollPane.getViewport().getViewSize();
	}
	
	public static void addItemListenerForAll(ArrayList<DocPanel> list,ItemListener il){
		for(DocPanel dp: list)
			dp.addItemListener(il);
	}
	public void removeAllItemListener(){
		if(this.list != null)
			for(DocPanel dp: list)
				dp.removeItemListeners();
	}
	public ArrayList<DocPanel> getList(){
		return list;
	}
	
	public void clearAll(){
		hasCommitted = false;
		hasExamined = false;
		hasRed = false;
		for(DocPanel dp: list)
			dp.setSelected(false);
		setSelectedNum(0);
	}
	
	public ArrayList<DocPanel> getSelected(){
		ArrayList<DocPanel> selected = new ArrayList<DocPanel>(selectedNum);
		for(DocPanel dp: list)
			if(dp.isSelected())
				selected.add(dp);
		return selected;
	}
	
	protected void setSelectedNum(int num){
		selectedNum = num;
		MainFrame.mf.setMessage("选中了"+num+"条单据");
		//lbl_selected.setText("选中了"+num+"条单据");
	}
	public int getSelectedNum(){
		return selectedNum;
	}


}
