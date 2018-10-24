package gui.document.cash;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import VO.CostVO;
import accountBL.CostBL;
import accountBLService.CostBLService;
import enums.DocumentType;
import enums.ResultMessage;
import enums.Status;
import gui.MainFrame;
import gui.ResultDialog;
import gui.document.component.DocPanel;
import gui.document.doclist.DocListPanel;
import progressBL.ProgressBL;
import progressBLService.ProgressBLService;

public class CashDocPanel extends DocPanel {

	private static final long serialVersionUID = 8315734829008509504L;
	private CostVO vo;
	private CostBLService costbl;
	/**
	 * Create the panel.
	 */
	public CashDocPanel(CostVO vo, int func, DocListPanel pnl_list) {
		super(vo, func, pnl_list);
		this.vo = vo;
		
		pnl_content.setLayout(new BoxLayout(pnl_content, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		pnl_content.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component verticalGlue_4 = Box.createVerticalGlue();
		panel.add(verticalGlue_4);
		
		
		JLabel lbl_account = new JLabel("银行账户："+vo.getAccountName());
		lbl_account.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lbl_account);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		panel.add(verticalGlue_1);
		
		JLabel lbl_remarks = new JLabel("备注：");
		pnl_content.add(lbl_remarks);
		
		JTextArea txa_remarks = new JTextArea();
		txa_remarks.setEditable(false);
		txa_remarks.setBackground(UIManager.getColor("Panel.background"));
		txa_remarks.setWrapStyleWord(true);
		txa_remarks.setLineWrap(true);
		pnl_content.add(txa_remarks);
		
		JLabel lbl_number = new JLabel("6条转账条目");
		panel.add(lbl_number);
		lbl_number.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_number.setText(vo.getList().size()+"条转账条目");
		
		Component verticalGlue_5 = Box.createVerticalGlue();
		panel.add(verticalGlue_5);
		
		JLabel lbl_total = new JLabel("共计43545.55元");
		panel.add(lbl_total);
		lbl_total.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_total.setText("共计："+vo.getTotal());
		
		Component verticalGlue_2 = Box.createVerticalGlue();
		panel.add(verticalGlue_2);
	}

	@Override
	public DocumentType getType() {
		return DocumentType.CASH;
	}

	@Override
	public void check() {
		new CashDialog_Uneditable(MainFrame.mf,vo).setVisible(true);
	}

	@Override
	public ResultMessage update() {
		ResultDialog dia = new CashDialog_Update(MainFrame.mf,vo);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getDeletionResult() {
		if(costbl==null)
			costbl = new CostBL();
		return costbl.delCost(vo);
	}

	@Override
	public ResultMessage examine() {
		ResultDialog dia = new CashDialog_Examine(MainFrame.mf,vo);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getExaminationResult(boolean pass) {
		if(pass)
			vo.setStatus(Status.PASS);
		else
			vo.setStatus(Status.FAIL);
		return new CostBL().shenpi(vo);
	}

	@Override
	public ResultMessage redcopy() {
		ResultDialog dia = new CashDialog_Red(MainFrame.mf,vo);
		dia.setVisible(true);
		return ResultDialog.getResultAndDispose(dia);
	}

	@Override
	public ResultMessage getRedResult() {
		vo.setHong(true);
		ProgressBLService progress = new ProgressBL();
		vo = progress.hong(vo);
		return progress.hongcopy(vo);
	}
}
