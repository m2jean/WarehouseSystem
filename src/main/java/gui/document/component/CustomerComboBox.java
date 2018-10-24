package gui.document.component;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import customerBL.CustomerBL;
import customerBLService.CustomerBLService;
import enums.DataMessage;
import VO.CustomerVO;

public class CustomerComboBox extends JComboBox<CustomerVO> {

	private static final long serialVersionUID = -7349903922057896085L;	
	private CustomerVO[] list;

	protected CustomerComboBox(CustomerVO[] list){
		super(list);
		this.list = list;
		setSelectedIndex(0);
	}
	
	public void setSelected(String id){
		for(CustomerVO cust:list)
			if(cust.getID().equals(id)){
				this.setSelectedItem(cust);
				break;
			}
	}

	public static CustomerComboBox getInstance(JDialog parent,String type){
		CustomerBLService custbl = new CustomerBL();
		DataMessage<ArrayList<CustomerVO>> result = custbl.getAllCustomer();
		switch(result.resultMessage){
		case ITEM_NOT_EXIST:
			JOptionPane.showMessageDialog(parent, "没有客户可以添加，请在客户管理中创建客户！");
			parent.dispose();
			break;
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(parent, "通信错误，无法获得客户列表！");
			parent.dispose();
			break;
		case SUCCESS:
			ArrayList<CustomerVO> filtered = new ArrayList<CustomerVO>();
			for(CustomerVO vo:result.data)
				if(type.isEmpty() || vo.getType().equals(type))
					filtered.add(vo);
			if(filtered.isEmpty()){
				if(type.isEmpty()){
					JOptionPane.showMessageDialog(parent, "没有客户可以添加，请在客户管理中创建客户！");
					parent.dispose();
					break;
				}
				else{
					JOptionPane.showMessageDialog(parent, "没有"+type+"可以添加，请在客户管理中创建！");
					parent.dispose();
					break;
				}
			}
			CustomerVO[] list = new CustomerVO[filtered.size()];
			for(int i = 0;i < list.length;i++)
				list[i] = filtered.get(i);
			return new CustomerComboBox(list);
		default:
			JOptionPane.showMessageDialog(parent, "未知错误！");
			parent.dispose();
			break;
		}
		return null;
	}
	
	public CustomerVO getSelectedCustomer(){
		int s = super.getSelectedIndex();
		if(s==-1)
			return null;
		return super.getItemAt(s);
	}
}
