package factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Factory {
	static String currentDate;
	static private int productNum;
	static private int specialProductNum;
	static private int categoryNum;
	static private int importNum;
	static private int exportNum;
	static private int customerNum;
	static private int presentNum;
	static private int overflowNum;
	static private int promotionNum;
	static private int snapshotNum;
	static private int messageNum;
	static private int saleNum;
	static private int returnNum;
	static private int checkinNum;
	static private int checkoutNum;
	static private int costNum;
	static private int draftNum;
	static private int accountNum;
	static public String maxID="maxID";
	
	public Factory(){
		File file=new File("datafactory.ser");
		if(file.exists()){
			try{
				FileInputStream fis=new FileInputStream(file);
				ObjectInputStream ois=new ObjectInputStream(fis);
				currentDate=(String)ois.readObject();
				productNum=(Integer)ois.readObject();
				specialProductNum=(Integer)ois.readObject();
				categoryNum=(Integer)ois.readObject();
				importNum=(Integer)ois.readObject();
				exportNum=(Integer)ois.readObject();
				customerNum=(Integer)ois.readObject();
				presentNum=(Integer)ois.readObject();
				overflowNum=(Integer)ois.readObject();
				promotionNum=(Integer)ois.readObject();
				snapshotNum=(Integer)ois.readObject();
				messageNum=(Integer)ois.readObject();
				saleNum=(Integer)ois.readObject();
				returnNum=(Integer)ois.readObject();
				checkinNum=(Integer)ois.readObject();
				checkoutNum=(Integer)ois.readObject();
				costNum=(Integer)ois.readObject();
				draftNum=(Integer)ois.readObject();
				accountNum = (Integer)ois.readObject();
				ois.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			currentDate="0000-00-00";
			productNum=0;
			specialProductNum=0;
			categoryNum=0;
			importNum=0;
			exportNum=0;
			customerNum=0;
			presentNum=0;
			overflowNum=0;
			promotionNum=0;
			snapshotNum=0;
			messageNum=0;
			saleNum=0;
			returnNum=0;
			checkinNum=0;
			checkoutNum=0;
			costNum=0;
			draftNum=0;
			accountNum = 0;
		}
	}
	
	private void toZero(){                  //所有数量设为0
		importNum=0;
		exportNum=0;
		presentNum=0;
		overflowNum=0;
		snapshotNum=0;
		messageNum=0;
		saleNum=0;
		returnNum=0;
		checkinNum=0;
		checkoutNum=0;
		costNum=0;
		draftNum=0;
	}
	
	private void saveData(){
		File file=new File("datafactory.ser");
		try{
			FileOutputStream fos=new FileOutputStream(file);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(currentDate);
			oos.writeObject(productNum);
			oos.writeObject(specialProductNum);
			oos.writeObject(categoryNum);
			oos.writeObject(importNum);
			oos.writeObject(exportNum);
			oos.writeObject(customerNum);
			oos.writeObject(presentNum);
			oos.writeObject(overflowNum);
			oos.writeObject(promotionNum);
			oos.writeObject(snapshotNum);
			oos.writeObject(messageNum);
			oos.writeObject(saleNum);
			oos.writeObject(returnNum);
			oos.writeObject(checkinNum);
			oos.writeObject(checkoutNum);
			oos.writeObject(costNum);
			oos.writeObject(draftNum);
			oos.writeObject(accountNum);
			oos.close();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public String messagePass(String id){
		StringBuffer sb=new StringBuffer(id);
		sb.append("审批通过。");
		return sb.toString();
	}
	
	public String messageFail(String id){
		StringBuffer sb=new StringBuffer(id);
		sb.append("审批未通过。");
		return sb.toString();
	}
	
	public String getDate(){
		Date current=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String date=format.format(current);
		if(!date.equals(currentDate)){
			currentDate=new String(date);
			toZero();
			saveData();
		}
		return date;
	}
	
	public String getTime(){
		Date current=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String date=format.format(current);
		return date;
	}
	
	private String getDateInner(){
		getDate();
		Date current=new Date();		
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String date=format.format(current);
		return date;
	}
	
	private String countFormat(int count){         //将计数统一成长度为5位的字符串
		DecimalFormat format=new DecimalFormat("00000");
		String c=format.format(count);
		return c;	
	}
	
	public String getDraftID(){
		draftNum++;
		String date=getDateInner();
		StringBuffer id=new StringBuffer();
		id.append("CGD-"+date+"-"+countFormat(draftNum));
		saveData();
		return id.toString();
	}
	
	public String getProductID(){
		productNum++;
		saveData();
		return countFormat(productNum);
	}
	
	public String getSpecialProductID(){
		specialProductNum++;
		saveData();
		return "TJB"+countFormat(specialProductNum);
	}
	
	public String getCategoryID(){
		categoryNum++;
		saveData();
		return countFormat(categoryNum);
	}
	
	public String getImportID(){
		if(importNum>=99999){
			return maxID;
		}
		importNum++;
		String date=getDateInner();
		StringBuffer id=new StringBuffer();
		id.append("JHD-"+date+"-"+countFormat(importNum));
		saveData();
		return id.toString();
	}
	
	public String getExportID(){
		if(importNum>=99999){
			return maxID;
		}
		exportNum++;
		String date=getDateInner();
		StringBuffer id=new StringBuffer();
		id.append("JHTHD-"+date+"-"+countFormat(exportNum));
		saveData();
		return id.toString();
	}
	
	public String getCustomerID(){
		customerNum++;
		saveData();
		return countFormat(customerNum);
	}
	
	public String getPresentID(){
		presentNum++;
		String date=getDateInner();
		StringBuffer id=new StringBuffer();
		id.append("ZPD-"+date+"-"+countFormat(presentNum));
		System.out.println(presentNum);
		saveData();
		return id.toString();
	}
	
	public String getOverflowID(){
		overflowNum++;
		String date=getDateInner();
		StringBuffer id=new StringBuffer();
		id.append("BSBYD-"+date+"-"+countFormat(overflowNum));
		saveData();
		return id.toString();
	}
	
	public String getSnapshotID(){
		snapshotNum++;
		String date=getDateInner();
		StringBuffer id=new StringBuffer();
		id.append("KCKZ-"+date+"-"+countFormat(snapshotNum));
		saveData();
		return id.toString();
	}
	
	public String getPromotionID(){
		promotionNum++;
		saveData();
		return countFormat(promotionNum);
	}
	
	public String getMessageID(){
		messageNum++;
		saveData();
		return countFormat(messageNum);
	}
	
	public String getCheckInID(){
		checkinNum++;
		String date=getDateInner();
		StringBuffer id=new StringBuffer();
		id.append("SKD-"+date+"-"+countFormat(checkinNum));
		saveData();
		return id.toString();
	}
	public String getCheckOutID(){
		checkoutNum++;
		String date=getDateInner();
		StringBuffer id=new StringBuffer();
		id.append("FKD-"+date+"-"+countFormat(checkoutNum));
		saveData();
		return id.toString();
	}
	
	public String getCostID(){
		costNum++;
		String date=getDateInner();
		StringBuffer id=new StringBuffer();
		id.append("XJFYD-"+date+"-"+countFormat(costNum));
		saveData();
		return id.toString();
	}
	
	public String getReturnID(){
		returnNum++;
		String date=getDateInner();
		StringBuffer id=new StringBuffer();
		id.append("XSTHD-"+date+"-"+countFormat(returnNum));
		saveData();
		return id.toString();
	}
	
	public String getSaleID(){
		saleNum++;
		String date=getDateInner();
		StringBuffer id=new StringBuffer();
		id.append("XSD-"+date+"-"+countFormat(saleNum));
		saveData();
		return id.toString();
	}
	
	public int getSnapshotLot(){
		return snapshotNum+1;
	}
	
	public String getAccountID(){
		accountNum++;
		saveData();
		return countFormat(accountNum);
	}
	
}
