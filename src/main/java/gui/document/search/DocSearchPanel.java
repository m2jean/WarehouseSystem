package gui.document.search;

import javax.swing.JPanel;

import enums.DocumentType;
import gui.document.doclist.DocListPanel;

public abstract class DocSearchPanel extends JPanel {

	private static final long serialVersionUID = -942432036854279517L;
	protected DocListPanel docpanel;
	
	public DocSearchPanel(DocListPanel pnl_list){
		docpanel = pnl_list;
	}

	public abstract void setDocumentTypes(DocumentType[] types);
}
