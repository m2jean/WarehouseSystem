package businesslogic.messagebl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import dataservice.userdataservice.UserDataService;
import enums.ResultMessage;
import exception.EmptyException;
import exception.RemoteFailException;
import factory.Factory;
import factory.RMIFactory;
import PO.MessagePO;
import VO.MessageVO;
import VO.UserVO;
import businesslogic.userbl.UserBL;
import businesslogicservice.messageblservice.MessageBLService;
import businesslogicservice.userblservice.UserBLService;

public class MessageBL implements MessageBLService {
	
	public ResultMessage send(UserVO user, MessageVO message) {
		try {
			//UserDataService dataService = new UserData();
			UserDataService dataService=new RMIFactory().newUserDataService();
			Factory factory=new Factory();
			String date=factory.getTime();
			message.setDate(date);
			return dataService.addMessage(message.toPO(), user.toPO());
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public ResultMessage update(UserVO user, ArrayList<MessageVO> message) {
		try {
			//UserDataService dataService = new UserData();
			UserDataService dataService=new RMIFactory().newUserDataService();
			ArrayList<MessagePO> list = new ArrayList<MessagePO>(message.size());
			for(MessageVO vo:message)
				list.add(vo.toPO());
			return dataService.updateMessage(user.toPO(),list);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ResultMessage delete(MessageVO message) {
		try {
			//UserDataService dataService = new UserData();
			UserDataService dataService=new RMIFactory().newUserDataService();
			UserBLService service=new UserBL();
			UserVO user=service.getCurrent();
			return dataService.deleteMessage(message.toPO(), user.toPO());
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public ArrayList<MessageVO> get() throws RemoteFailException, EmptyException {
		try {
			//UserDataService dataService = new UserData();
			UserDataService dataService=new RMIFactory().newUserDataService();
			UserBLService service=new UserBL();
			UserVO user=service.getCurrent();
			ArrayList<MessagePO> poList=dataService.getMessage(user.toPO());
			if(poList.isEmpty()) throw new EmptyException("Message of"+user.getUsername());
			ArrayList<MessageVO> voList = new ArrayList<MessageVO>();
			for(MessagePO message:poList)
				voList.add(message.toVO());
			return voList;
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			throw new RemoteFailException(e);
		}
	}

}
