package enums;

import javax.swing.JPanel;

import gui.account.AccountPanel;
import gui.customer.CustomerPanel;
import gui.document.doclist.AccountDocListPanel;
import gui.document.doclist.DocExaminePanel;
import gui.document.doclist.ProgressPanel;
import gui.document.doclist.SalesDocListPanel;
import gui.document.doclist.StockDocListPanel;
import gui.inventory.InventoryCheckPanel;
import gui.inventory.InventoryPanel;
import gui.log.LogPanel;
import gui.product.ProductPanel;
import gui.progress.CircumstancePanelNew;
import gui.progress.SaleDetailPanel;
import gui.promotion.PromotionPanel;
import gui.user.UserPanel;

public enum Subsystem {
PRODUCT_CATEGORY("商品管理"),INVENTORY("库存查看"),STOCK_SNAP("库存快照"),STOCK_DOCUMENT("单据管理"),
USER_ADMIN("用户管理"),LOG("日志查看"),
SALE_DOCUMENT("单据管理"),CUSTOMER("客户管理"),SALE_DETAIL("销售明细"),
PROMOTION("促销策略"),CIRCUMSTANCE("经营情况"),DOCS_ADMIN("单据审批"),
ACCOUNT("账户管理"),PROGRESS("经营历程"),ACCOUNT_DOCUMENT("单据管理");
	
private String disname;

private Subsystem(String displayname){
	disname = displayname;
}

public String getDisplayName(){
	return disname;
}

public JPanel getPanel(){
	switch(this){
	case ACCOUNT:
		return new AccountPanel();
	case ACCOUNT_DOCUMENT:
		return new AccountDocListPanel();
	case CIRCUMSTANCE:
		return new CircumstancePanelNew();
	case CUSTOMER:
		return new CustomerPanel();
	case DOCS_ADMIN:
		return new DocExaminePanel();
	case INVENTORY:
		return new InventoryPanel();
	case LOG:
		return new LogPanel();
	case PRODUCT_CATEGORY:
		return new ProductPanel();
	case PROGRESS:
		return new ProgressPanel();
	case PROMOTION:
		return new PromotionPanel();
	case SALE_DETAIL:
		return new SaleDetailPanel();
	case SALE_DOCUMENT:
		return new SalesDocListPanel();
	case STOCK_DOCUMENT:
		return new StockDocListPanel();
	case STOCK_SNAP:
		return new InventoryCheckPanel();
	case USER_ADMIN:
		return new UserPanel();	
	}
	throw new NullPointerException("Invalid Subsystem Enum!");
}

public static Subsystem[] getMenu(UserPermission perm){
	switch(perm){
	case ACCOUNT:
	case ACCOUNT_MANAGER:
		return new Subsystem[]{ACCOUNT_DOCUMENT,ACCOUNT};
	case ADMINISTOR:
		return new Subsystem[]{USER_ADMIN};
	case GENERAL_MANAGER:
		return new Subsystem[]{DOCS_ADMIN,LOG};
	case SALESMAN:
	case SALES_MANAGER:
		return new Subsystem[]{SALE_DOCUMENT,CUSTOMER};
	case STOCK_MANAGER:
		return new Subsystem[]{STOCK_DOCUMENT,PRODUCT_CATEGORY};
	}
	throw new NullPointerException("Invalid UserPermission Enum!");
}
}
