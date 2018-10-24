package gui.product;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import VO.CategoryVO;
import VO.ProductVO;
import enums.DataMessage;
import enums.Operation;
import enums.ResultMessage;
import gui.ClearHintTextField;
import gui.MainFrame;
import gui.ResultDialog;
import productBL.CategoryBL;
import productBL.CategoryLineItem;
import productBL.ProductBL;
import productBLService.CategoryBLService;
import productBLService.ProductBLService;

public class ProductPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7286749578439976602L;
	private static final String CATEGORY_EMPTY = "CATEGORY_EMPTY";
	private static final String NO_CATEGORY_SELECTED = "NO_CATEGORY_SELECTED";
	private static final String NO_MATCH_FOUND = "NO_MATCH_FOUND";
	private static final String PRODUCT_LIST = "PRODUCT_LIST";
	
	private JTable tbl_product;
	private JTree tree_cate;
	private JButton btn_updCate,btn_delCate,btn_addProd,btn_delProd,btn_alarm;
	private JTextField txf_id;
	private JTextField txf_name;
	private CardLayout cardlayout;
	private JPanel pnl_product;
	
	private CategoryBLService catebl = new CategoryBL();
	private ProductBLService prodbl = new ProductBL();
	private JButton btn_addCate;
	private JButton btn_help;

	/**
	 * Create the panel.
	 */
	public ProductPanel() {
		setPreferredSize(new Dimension(800, 640));
		setLayout(new BorderLayout(0, 0));
		
		JPanel pnl_productlist = new JPanel();
		add(pnl_productlist, BorderLayout.CENTER);
		pnl_productlist.setLayout(new BorderLayout(0, 0));
		
		JPanel pnl_prodSearch = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnl_prodSearch.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(pnl_prodSearch, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("编号：");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		pnl_prodSearch.add(lblNewLabel);
		
		txf_id = new ClearHintTextField("默认为任意编号");
		txf_id.addActionListener(new SearchListener());
		pnl_prodSearch.add(txf_id);
		txf_id.setColumns(10);
		
		JLabel lbl_name = new JLabel("名称：");
		lbl_name.setHorizontalAlignment(SwingConstants.TRAILING);
		pnl_prodSearch.add(lbl_name);
		
		txf_name = new ClearHintTextField("默认为任意名称");
		txf_name.addActionListener(new SearchListener());
		pnl_prodSearch.add(txf_name);
		txf_name.setColumns(10);
		
		JButton btn_search = new JButton("搜索商品");
		btn_search.addActionListener(new SearchListener());
		pnl_prodSearch.add(btn_search);
		
		JButton btn_showall = new JButton("所有商品");
		btn_showall.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DataMessage<ArrayList<ProductVO>> result = prodbl.getProduct(null, null);
				if(result.resultMessage == ResultMessage.ITEM_NOT_EXIST || result.data == null){
					cardlayout.show(pnl_product,NO_MATCH_FOUND);
				}
				else if(result.resultMessage == ResultMessage.SUCCESS){
					tree_cate.clearSelection();
					buildTable(result.data);
				}
				else{
					JOptionPane.showMessageDialog(MainFrame.mf,"发生未知错误！");
				}
			}
		});
		pnl_prodSearch.add(btn_showall);
		
		pnl_product = new JPanel();
		pnl_productlist.add(pnl_product, BorderLayout.CENTER);
		cardlayout = new CardLayout();
		pnl_product.setLayout(cardlayout);
		
		JLabel lbl_NoProduct = new JLabel("该分类下没有商品，请添加商品或分类");
		pnl_product.add(lbl_NoProduct, CATEGORY_EMPTY);
		lbl_NoProduct.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lbl_PleaseSelect = new JLabel("请选择一个最低级别的分类");
		pnl_product.add(lbl_PleaseSelect, NO_CATEGORY_SELECTED);
		lbl_PleaseSelect.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lbl_NoMatch = new JLabel("未找到匹配的商品");
		lbl_NoMatch.setHorizontalAlignment(SwingConstants.CENTER);
		pnl_product.add(lbl_NoMatch, NO_MATCH_FOUND);
		
		JScrollPane scrp_product = new JScrollPane();
		pnl_product.add(scrp_product, PRODUCT_LIST);
		
		tbl_product = new JTable();
		tbl_product.getTableHeader().setReorderingAllowed(false);
		tbl_product.setRowHeight(30);
		tbl_product.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		final ListSelectionModel lsmodel = tbl_product.getSelectionModel();
		lsmodel.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(lsmodel.isSelectionEmpty()){
					btn_delProd.setEnabled(false);
					btn_alarm.setEnabled(false);
				}
				else{
					btn_delProd.setEnabled(true);
					btn_alarm.setEnabled(true);
				}
			}			
		});
		scrp_product.setViewportView(tbl_product);
		
		

		JPanel pnl_category = new JPanel();
		pnl_category.setBorder(new EmptyBorder(0, 5, 0, 5));
		add(pnl_category, BorderLayout.WEST);
		pnl_category.setLayout(new BorderLayout(0, 0));

		tree_cate = new JTree();
		DefaultTreeCellRenderer dtcr =  new DefaultTreeCellRenderer();
		dtcr.setLeafIcon(dtcr.getOpenIcon());
		tree_cate.setCellRenderer(dtcr);
		buildTree();
		tree_cate.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				TreePath currentSelection = tree_cate.getSelectionPath();
		        if (currentSelection != null) {
		            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
		                         (currentSelection.getLastPathComponent());
		            if(currentNode.isRoot()){
		            	btn_addCate.setEnabled(true);
		            	btn_delCate.setEnabled(false);
			        	btn_updCate.setEnabled(false);
			        	btn_addProd.setEnabled(false);
		            	btn_delProd.setEnabled(false);
		            	cardlayout.show(pnl_product, NO_CATEGORY_SELECTED);
		            }
		            else if(!currentNode.isLeaf()){
		            	btn_addCate.setEnabled(true);
		            	btn_delCate.setEnabled(false);
		            	btn_updCate.setEnabled(true);
		            	btn_addProd.setEnabled(false);
		            	btn_delProd.setEnabled(false);
		            	cardlayout.show(pnl_product, NO_CATEGORY_SELECTED);
		            }
		            else{
		            	CategoryVO vo = (CategoryVO)currentNode.getUserObject();
		            	
		            	btn_addCate.setEnabled(!vo.hasProduct());
		            	btn_delCate.setEnabled(!vo.hasProduct());
		            	btn_updCate.setEnabled(true);
		            	btn_addProd.setEnabled(true);
		            	btn_delProd.setEnabled(false);
		            	
		            	if(!vo.hasProduct())
		            		cardlayout.show(pnl_product, CATEGORY_EMPTY);
		            	else{
		            		buildTable(vo);
		            	}
		            }
		        }
		        else{
		        	btn_addCate.setEnabled(true);
		        	btn_delCate.setEnabled(false);
		        	btn_updCate.setEnabled(false);
		        	btn_addProd.setEnabled(false);
	            	btn_delProd.setEnabled(false);
		        	cardlayout.show(pnl_product, NO_CATEGORY_SELECTED);
		        }
		        setToolTip();
			}
		});
		tree_cate.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent me){
				if(me.getButton() == MouseEvent.BUTTON3 && me.getClickCount() == 1){
					tree_cate.clearSelection();
					tree_cate.addSelectionRow(tree_cate.getRowForLocation(me.getX(), me.getY()));
					
					JPopupMenu jpm = new JPopupMenu();
					JMenuItem me_addpro = new JMenuItem("添加商品");
					jpm.add(me_addpro);
					me_addpro.addActionListener(btn_addProd.getActionListeners()[0]);
					JMenuItem me_addcate = new JMenuItem("添加分类");
					jpm.add(me_addcate);
					me_addcate.addActionListener(btn_addCate.getActionListeners()[0]);
					JMenuItem me_delcate = new JMenuItem("删除");
					jpm.add(me_delcate);
					me_delcate.addActionListener(btn_delCate.getActionListeners()[0]);
					JMenuItem me_updcate = new JMenuItem("修改名称");
					jpm.add(me_updcate);
					me_updcate.addActionListener(btn_updCate.getActionListeners()[0]);
					
					TreePath currentSelection = tree_cate.getSelectionPath();
					if (currentSelection != null) {
			            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
			                         (currentSelection.getLastPathComponent());
			            if(currentNode.isRoot()){
			            	me_delcate.setEnabled(false);
				        	me_updcate.setEnabled(false);
				        	me_addpro.setEnabled(false);
			            }
			            else if(!currentNode.isLeaf()){
			            	me_delcate.setEnabled(false);
			            	me_addpro.setEnabled(false);
			            }
			            else{
			            	CategoryVO vo = (CategoryVO)currentNode.getUserObject();
			            	if(vo.hasProduct()){
				            	me_addcate.setEnabled(false);
				            	me_delcate.setEnabled(false);
			            	}
			            }
			        }
			        else{
			        	me_delcate.setEnabled(false);
			        	me_updcate.setEnabled(false);
			        	me_addpro.setEnabled(false);
			        }
			        jpm.show(tree_cate, me.getX(), me.getY());
				}
			}
		});
		
		
		JScrollPane scrp_cate = new JScrollPane();
		pnl_category.add(scrp_cate, BorderLayout.CENTER);
		scrp_cate.setViewportView(tree_cate);
   	
    	JPanel pnl_func = new JPanel();
		FlowLayout fl_pnl_prodFunc = (FlowLayout) pnl_func.getLayout();
		fl_pnl_prodFunc.setAlignment(FlowLayout.LEFT);
		add(pnl_func, BorderLayout.SOUTH);
		
		btn_addCate = new JButton("添加分类");
		btn_addCate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String parent = null;
				TreePath currentSelection = tree_cate.getSelectionPath();
		        if (currentSelection != null) {
		            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
		                         (currentSelection.getLastPathComponent());
		            if(!currentNode.isRoot())
		            	parent =((CategoryVO)currentNode.getUserObject()).getName();
		        }
		        ResultDialog dia = new CategoryDialog(MainFrame.mf,Operation.ADD_CATEGORY,
		        		new CategoryVO("", "", parent, false, null, null));	
		        dia.pack();
		        dia.setResizable(false);
		        JComponent jcom = (JComponent)e.getSource();
		        if(jcom.isShowing())
		        	dia.setLocation(jcom.getLocationOnScreen()); 
		        else
		        	dia.setLocation(MouseInfo.getPointerInfo().getLocation()); 
		        dia.setVisible(true);
		        ResultMessage result = ResultDialog.getResultAndDispose(dia);
		        if(result == ResultMessage.SUCCESS){
		        	MainFrame.mf.setMessage("商品分类添加成功！");
		        	buildTree();
		        }
		        else if(result != ResultMessage.CLOSE){
		        	MainFrame.mf.setError("商品分类添加失败");
		        	buildTree();
		        }
			}
		});
		pnl_func.add(btn_addCate);
		
		btn_delCate = new JButton("删除分类");
		btn_delCate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath currentSelection = tree_cate.getSelectionPath();
		        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());
		        CategoryVO vo = (CategoryVO) currentNode.getUserObject();
		        ResultMessage result = catebl.deleteCategory(vo);
		        switch(result){
		        case SUCCESS:
		        	MainFrame.mf.setMessage("商品分类删除成功！");
		        	buildTree();
		        	break;
		        default:
		        	MainFrame.mf.setError("商品分类删除失败");
		        	buildTree();
		        	break;
		        }
			}
		});
		pnl_func.add(btn_delCate);
		
		btn_updCate = new JButton("更改分类名称");
		btn_updCate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath currentSelection = tree_cate.getSelectionPath();
		        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());
		        CategoryVO vo = (CategoryVO) currentNode.getUserObject();
		        ResultDialog dia = new CategoryDialog(MainFrame.mf,Operation.UPD_CATEGORY,vo);JComponent jcom = (JComponent)e.getSource();
		        dia.pack();
		        dia.setResizable(false);
		        if(jcom.isShowing())
		        	dia.setLocation(jcom.getLocationOnScreen()); 
		        else
		        	dia.setLocation(MouseInfo.getPointerInfo().getLocation()); 
		        dia.setVisible(true);		        
		        ResultMessage result = ResultDialog.getResultAndDispose(dia);
		        if(result == ResultMessage.SUCCESS){
		        	MainFrame.mf.setMessage("商品分类修改成功！");
		        	buildTree();
		        }
		        else if(result != ResultMessage.CLOSE){
		        	MainFrame.mf.setError("商品分类修改失败");
		        	buildTree();
		        }
			}
		});
		pnl_func.add(btn_updCate);
		
		btn_delCate.setEnabled(false);
    	btn_updCate.setEnabled(false);
		/*
		JButton btn_refCate = new JButton("刷新");
		btn_refCate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildTree();
			}
		});
		pnl_cateFunc.add(btn_refCate);*/
    	pnl_func.add(Box.createHorizontalStrut(20));
		
		btn_addProd = new JButton("添加商品");
		btn_addProd.setEnabled(false);
		btn_addProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath currentSelection = tree_cate.getSelectionPath();
		        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());
		        ResultDialog dia = new ProductDialog(MainFrame.mf,(CategoryVO)currentNode.getUserObject());
		        dia.setVisible(true);
		        ResultMessage result = ResultDialog.getResultAndDispose(dia);
		        if(result == ResultMessage.SUCCESS){
		        	MainFrame.mf.setMessage("商品添加成功！");
		        	buildTreeTable();
		        }
		        else if(result != ResultMessage.CLOSE){
		        	MainFrame.mf.setError("商品添加失败");
		        	buildTreeTable();
		        }
			}
		});
		pnl_func.add(btn_addProd);
		
		btn_delProd = new JButton("删除商品");
		btn_delProd.setEnabled(false);
		btn_delProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductTableModel ptmodel = (ProductTableModel) tbl_product.getModel();
				int row = tbl_product.getSelectedRow();
				ProductVO product = ptmodel.getProductAt(row);
				if(product.getLastIn() != 0 || product.getLastOut() != 0){
					JOptionPane.showMessageDialog(MainFrame.mf, "商品已存在交易记录！");
					return;
				}
				ResultMessage result = prodbl.deleteProduct(product);
				switch(result){
				case SUCCESS:
					MainFrame.mf.setMessage("商品删除成功！");
					buildTreeTable();
					break;
				default:
					MainFrame.mf.setMessage("商品删除失败");
					buildTreeTable();
					break;
				}
			}
		});
		pnl_func.add(btn_delProd);
		
		btn_alarm = new JButton("设置报警单");
		btn_alarm.setEnabled(false);
		btn_alarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductTableModel ptmodel = (ProductTableModel) tbl_product.getModel();
				int row = tbl_product.getSelectedRow();
				ProductVO product = ptmodel.getProductAt(row);
				new AlarmDialog(MainFrame.mf,product).setVisible(true);
			}
		});
		pnl_func.add(btn_alarm);
		
		pnl_func.add(Box.createHorizontalStrut(20));
		btn_help = new JButton("?");
		pnl_func.add(btn_help);
		ToolTipManager.sharedInstance().setInitialDelay(0);
		
		setToolTip();
	}
	
	private void setToolTip(){
		StringBuilder sb = new StringBuilder("<html><ul>");
		if(!btn_addCate.isEnabled())
			sb.append("<li>不能在有商品的分类中添加分类。</li>");
		if(!btn_delCate.isEnabled())
			sb.append("<li>不能删除非空的分类。</li>");
		if(!btn_addProd.isEnabled())
			sb.append("<li>不能在包含其他分类的分类中添加商品。</li>");
		if(!btn_delProd.isEnabled())
			sb.append("<li>无法删除存在交易记录的商品。</li>");
		if(!btn_alarm.isEnabled())
			sb.append("<li>请选中商品后添加报警单。</li>");
		sb.append("</ul></html>");
		btn_help.setToolTipText(sb.toString());
	}
	
	protected void buildTable() {
		TreePath currentSelection = tree_cate.getSelectionPath();
        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());
        CategoryVO vo = (CategoryVO) currentNode.getUserObject();
        buildTable(vo);
	}

	public void buildTable(CategoryVO category){
		ArrayList<CategoryLineItem> list = category.getProductList();
		ArrayList<ProductVO> products = new ArrayList<ProductVO>();
		for(int i = 0;i < list.size();i++){
			CategoryLineItem item = list.get(i);
			ProductVO product = new ProductVO(item.getProductID(),item.getProductName(),item.getProductModel(), 0, 0,0,0,0,null,null);
			DataMessage<ProductVO> result = prodbl.getProduct(product);
			if(result.resultMessage!= ResultMessage.SUCCESS){
				JOptionPane.showMessageDialog(MainFrame.mf, "获取商品列表失败！");
				return;
			}
			products.add(result.data);
		}
		buildTable(products);
	}
	
	public void buildTable(ArrayList<ProductVO> products) {
		Collections.sort(products);
		tbl_product.setModel(new ProductTableModel(products));		
		cardlayout.show(pnl_product, PRODUCT_LIST);
	}

	public void buildTree(){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("所有分类");
		ResultMessage result = catebl.getCategoryTree(root);
		if(result == ResultMessage.SUCCESS){		
		}
		else{
			JOptionPane.showMessageDialog(this, "获取分类失败!");
		}
		TreeModel model = new DefaultTreeModel(root);
		tree_cate.setModel(model);
		expandTree(tree_cate);
		cardlayout.show(pnl_product, NO_CATEGORY_SELECTED);
	}
	
	private void expandTree(JTree tree){
		int rowc = tree.getRowCount();
		for(int i = rowc-1;i >=0;--i){
			tree.expandRow(i);
		}
		if(rowc != tree.getRowCount())
			expandTree(tree);
	}
	
	private class SearchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = txf_name.getText().trim();
			String id = txf_id.getText().trim();
			//if(name.equals("") && id.equals(""))return;
			if(name.equals(""))name = null;
			if(id.equals(""))id = null;
			DataMessage<ArrayList<ProductVO>> result = prodbl.getProduct(id, name);
			if(result.resultMessage == ResultMessage.ITEM_NOT_EXIST || result.data == null){
				cardlayout.show(pnl_product,NO_MATCH_FOUND);
			}
			else if(result.resultMessage == ResultMessage.SUCCESS){
				buildTable(result.data);
			}
			else{
				JOptionPane.showMessageDialog(MainFrame.mf,"发生未知错误！");
			}
		}		
	}

	private void buildTreeTable() {
		TreePath treepath = tree_cate.getSelectionPath();
		Object[] path = treepath.getPath();
		buildTree();
		DefaultTreeModel model = (DefaultTreeModel) tree_cate.getModel();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) model.getRoot();
		path[0] = node;
		int i = 1;
		while(i < path.length){
			for(int j = 0;j < node.getChildCount();j++){
				DefaultMutableTreeNode n = (DefaultMutableTreeNode) node.getChildAt(j);
				CategoryVO old = (CategoryVO) ((DefaultMutableTreeNode)path[i]).getUserObject();
				CategoryVO newvo = (CategoryVO) n.getUserObject();
				if(old.getID().equals(newvo.getID())){
					path[i] = n;
					node = n;
					i++;
					break;
				}
			}
		}
		tree_cate.setExpandsSelectedPaths(true);
		tree_cate.setSelectionPath(new TreePath(path));
		
		setToolTip();
	}


}
