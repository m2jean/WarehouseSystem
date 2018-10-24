package PO;

import java.io.Serializable;
import java.util.ArrayList;

import VO.CostVO;
import VO.ListOutVO;
import enums.Status;

public class CostPO implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 5511340981974934924L;
		private String id;
		private String me;
		private String account;
		private String accountName;
		private ArrayList<ListOutPO> list;
		private double total;
		private String date;
		private Status status;
		private String remark;
		private boolean isHong=false;
		
		public CostPO(String id,String me,String account,String accountName,ArrayList<ListOutPO> list,double total,String date,Status status,String remark,boolean hong){
			this.id=id;
			this.me=me;
			this.account=account;
			this.accountName=accountName;
			this.list=list;
			this.total=total;
			this.date=date;
			this.status=status;
			this.remark=remark;
			this.isHong=hong;
		}
		public CostVO toVO(){
			ArrayList<ListOutVO> d=new ArrayList<ListOutVO>(); 
			for(int i=0;i<list.size();i++){
				ListOutPO ipo=list.get(i);
				ListOutVO ivo=new ListOutVO(ipo.getName(),ipo.getMoney(),ipo.getRemark());
				d.add(ivo);
			}
			CostVO vo=new CostVO(id,me,account,accountName,d,total,date,status,remark,isHong);
			return vo;
		}

		public String getID() {
			return id;
		}

		public void setID(String id) {
			this.id = id;
		}

		public String getMe() {
			return me;
		}

		public void setMe(String me) {
			this.me = me;
		}

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public ArrayList<ListOutPO> getList() {
			return list;
		}

		public void setList(ArrayList<ListOutPO> list) {
			this.list = list;
		}

		public double getTotal() {
			return total;
		}

		public void setTotal(double total) {
			this.total = total;
		}
		public Status getStatus(){
			return status;
		}
		public void setStatus(Status status){
			this.status=status;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getAccountName() {
			return accountName;
		}
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}
		
		public boolean isHong(){
			return isHong;
		}
		public void setHong(boolean hong){
			this.isHong=hong;
		}
}
