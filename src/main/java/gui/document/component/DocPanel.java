package gui.document.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import enums.DocumentType;
import enums.ResultMessage;
import enums.Status;
import gui.MainFrame;
import gui.document.doclist.DocListPanel;
import gui.document.vo.DocumentVO;

public abstract class DocPanel extends JPanel implements Comparable<DocPanel>{

	private static final long serialVersionUID = 5035883320161478573L;
	public static int PERSONAL = 1;
	public static int EXAMINE = 2;
	public static int RED = 3;
	
	private static Image img_check;
	private static Image img_edit;
	private static Image img_remove;
	private static Image img_pass;
	private static Image img_deny;
	protected JPanel pnl_content;
	private BoundedCheckBox ckbx;
	private JLabel lbl_date,lbl_id,lbl_type,lbl_operator,lbl_status;
	private DocumentVO vo;
	private DocListPanel pnllist;

	static{
		try {
			img_check = ImageIO.read(new File("icons/eye-24-128.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			img_edit = ImageIO.read(new File("icons/new-24-128.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			img_remove = ImageIO.read(new File("icons/cross-24-128.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			img_pass = ImageIO.read(new File("icons/onebit_34.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			img_deny = ImageIO.read(new File("icons/onebit_33.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*public DocPanel(){
		construct();
		lbl_date.setText("XJFYD-yyyyMMdd-xxxxx");
	}*/
	
	public DocPanel(DocumentVO vo, int func, DocListPanel pnl_list) {
		this.vo = vo;
		this.pnllist = pnl_list;
		construct(func);
		setProperty(vo);
	}
	
	private void construct(int func){
		setBorder(new LineBorder(Color.BLACK));
		setPreferredSize(new Dimension(745, 95));
		setMaximumSize(new Dimension(745, 95));
		setMinimumSize(new Dimension(745, 95));
		setSize(new Dimension(740, 95));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		//Component horizontalStrut = Box.createHorizontalStrut(8);
		//add(horizontalStrut);
		
		Box box_ckbx = new Box(BoxLayout.X_AXIS);
		box_ckbx.setOpaque(true);
		add(box_ckbx);
		ckbx = new BoundedCheckBox("",this);
		box_ckbx.add(ckbx);
		ckbx.setSize(20, 95);
		ckbx.setPreferredSize(new Dimension(20, 95));
		ckbx.setMinimumSize(new Dimension(20, 95));
		ckbx.setMaximumSize(new Dimension(20, 95));
		if(	(func == PERSONAL && vo.getStatus() == Status.DRAFT) ||
			(func == EXAMINE && vo.getStatus() == Status.SUBMIT)){
			
			box_ckbx.setBackground(new Color(230,230,230));
			ckbx.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseEntered(MouseEvent me){
					ckbx.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				@Override
				public void mouseExited(MouseEvent me){
					ckbx.setCursor(Cursor.getDefaultCursor());
				}
			});
		}
		else{
			ckbx.setEnabled(false);
		}
		
		Box box_btn = new Box(BoxLayout.Y_AXIS);
		add(box_btn);
		
		if(func == PERSONAL)
			switch(vo.getStatus()){
			case DRAFT:
				JButton btn_edit = new JButton(new ImageIcon(img_edit));
				btn_edit.setToolTipText("查看并修改");
				btn_edit.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						ResultMessage result = update();
						String docname = getType().toString();
						if(result == ResultMessage.SUBMIT_SUCCESS){
							MainFrame.mf.setMessage(docname+"提交成功！");
						}
						else if(result == ResultMessage.SAVE_SUCCESS){
							MainFrame.mf.setMessage(docname+"保存草稿成功！");
						}
						else if(result == ResultMessage.SUBMIT_FAIL){
							MainFrame.mf.setError(docname+"提交失败");
						}
						else if(result == ResultMessage.SAVE_FAILED){
							MainFrame.mf.setError(docname+"保存草稿失败");
						}
						else if(result != ResultMessage.CLOSE){
								MainFrame.mf.setError(docname+"操作失败");
						}
						
						if(result != ResultMessage.CLOSE)
							pnllist.buildList();
					}
				});
				box_btn.add(btn_edit);
				JButton btn_remove = new JButton(new ImageIcon(img_remove));
				btn_remove.setToolTipText("删除");
				btn_remove.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						delete();
					}
				});
				box_btn.add(btn_remove);
				break;
			case SUBMIT:	
			case FAIL:
			case PASS:
				JButton btn_check = new JButton(new ImageIcon(img_check));
				btn_check.setToolTipText("查看");
				btn_check.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						check();
					}
				});
				box_btn.add(btn_check);
				break;
			default:
				throw new IllegalArgumentException();
			}
		else if(func == EXAMINE){
			switch(vo.getStatus()){
			case SUBMIT:
				JButton btn_edit = new JButton(new ImageIcon(img_edit));
				box_btn.add(btn_edit);
				btn_edit.setToolTipText("查看并修改");
				btn_edit.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						ResultMessage result = examine();
						
						String docname = getType().toString();
						if(result == ResultMessage.SUCCESS){
							MainFrame.mf.setMessage(docname+"审批成功！");
						}
						else if(result != ResultMessage.CLOSE){
								MainFrame.mf.setError(docname+"审批失败");
						}
						
						if(result != ResultMessage.CLOSE)
							pnllist.buildList();
					}
				});
				JButton btn_pass = new JButton(new ImageIcon(img_pass));
				box_btn.add(btn_pass);
				btn_pass.setToolTipText("通过");
				btn_pass.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						ResultMessage result = getExaminationResult(true);
						if(result == ResultMessage.SUCCESS){
							MainFrame.mf.setMessage("单据审批成功");
						}
						else
							MainFrame.mf.setError("单据审批失败");
						pnllist.buildList();
					}
				});
				JButton btn_deny = new JButton(new ImageIcon(img_deny));
				btn_deny.setToolTipText("否决");
				btn_deny.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						ResultMessage result = getExaminationResult(false);
						if(result == ResultMessage.SUCCESS){
							MainFrame.mf.setMessage("单据否决成功");
						}
						else
							MainFrame.mf.setError("单据否决失败");
						pnllist.buildList();
					}
				});
				box_btn.add(btn_deny);
				break;	
			case FAIL:
			case PASS:
				JButton btn_check = new JButton(new ImageIcon(img_check));
				btn_check.setToolTipText("查看");
				btn_check.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						check();
					}
				});
				box_btn.add(btn_check);
				break;
			default:
				throw new IllegalArgumentException();
			}
		}
		
		//add(Box.createHorizontalGlue());
		add(Box.createHorizontalStrut(5));
		
		JPanel panel = new JPanel();
		panel.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY),new EmptyBorder(0,0,0,10)));
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		lbl_type = new JLabel();
		//lbl_type.setFont(new Font("宋体", Font.PLAIN, 12));
		lbl_type.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lbl_type);
		
		lbl_status = new JLabel();
		//lbl_status.setFont(new Font("宋体", Font.PLAIN, 12));
		lbl_status.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lbl_status);
		
		lbl_date = new JLabel();
		lbl_date.setFont(new Font("Courier New", Font.PLAIN, lbl_date.getFont().getSize()));
		lbl_date.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lbl_date);	
		
		lbl_operator = new JLabel();
		//lbl_operator.setFont(new Font("宋体", Font.PLAIN, 12));
		lbl_operator.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lbl_operator);
		
		lbl_id = new JLabel();
		lbl_id.setFont(new Font("Courier New", Font.PLAIN, lbl_date.getFont().getSize()));
		//lbl_id.setFont(new Font("宋体", Font.PLAIN, 12));
		lbl_id.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lbl_id);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(8);
		add(horizontalStrut_2);
		
		pnl_content = new JPanel();
		pnl_content.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(pnl_content);		
		
		Component horizontalGlue = Box.createHorizontalGlue();
		add(horizontalGlue);
		
	}
	
	protected void setProperty(DocumentVO vo){
		this.vo = vo;
		setDate(getDate());
		setID(getID());
		setType(getType());
		setOperator(getOperator());
		setStatus(getStatus());
		
	}
	
	public String getDate() {
		return vo.getDate();
	}

	private void setDate(String date) {
		lbl_date.setText(date);;
	}
	
	public String getID(){
		return vo.getID();
	}
	
	private void setID(String id){
		//if(vo.getStatus() != Status.DRAFT)
			lbl_id.setText(id);
	}
	
	public abstract DocumentType getType();
	public DocumentVO getVO(){
		return vo;
	}
	
	private void setType(DocumentType dt){
		if(isRed())
			lbl_type.setText("红冲"+dt.toString());
		else
			lbl_type.setText(dt.toString());
	}
	
	public String getOperator(){
		return vo.getOperator();
	}
	
	private void setOperator(String operator){
		lbl_operator.setText("操作员："+operator);
	}
	
	public Status getStatus(){
		return vo.getStatus();
	}
	
	private void setStatus(Status s){
		lbl_status.setText(s.toString());
		switch(s){
		case DRAFT:
			lbl_status.setForeground(Color.BLUE);
			break;
		case FAIL:
			lbl_status.setForeground(Color.RED);
			break;
		case PASS:
			lbl_status.setForeground(new Color(200, 104, 68));
			break;
		case SUBMIT:
		default:
			lbl_status.setForeground(Color.BLACK);
		}
	}
	
	public boolean isSelected(){
		return ckbx.isSelected();
	}
	
	public void setSelected(boolean flag){
		ckbx.setSelected(flag);
	}
	
	public boolean isRed(){
		return vo.isHong();
	}

	public void addItemListener(ItemListener il){
		ckbx.addItemListener(il);
	}

	public void removeItemListeners() {
		for(ItemListener il:ckbx.getItemListeners())
			ckbx.removeItemListener(il);
	}
	
	@Override
	public int compareTo(DocPanel dp){
		if(dp.getStatus().equals(this.getStatus())){
			if(dp.getDate()==null)
				return 0;
			return dp.getDate().compareTo(this.getDate());
		}
		else
			return dp.getStatus().compareTo(this.getStatus());
	}

	public abstract void check();
	public abstract ResultMessage update();
	public void delete(){
		if(JOptionPane.showConfirmDialog(MainFrame.mf, "确认删除"+getType().toString(), "删除单据", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			if(getDeletionResult() == ResultMessage.SUCCESS){
				MainFrame.mf.setMessage("单据删除成功");
			}
			else
				MainFrame.mf.setError("单据删除失败");
			pnllist.buildList();
		}
	}
	public abstract ResultMessage getDeletionResult();
	public abstract ResultMessage examine();
	public abstract ResultMessage getExaminationResult(boolean pass);
	public abstract ResultMessage redcopy();
	public abstract ResultMessage getRedResult();
}
