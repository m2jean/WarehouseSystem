package businesslogicservice.messageblservice;

import java.util.ArrayList;

import enums.ResultMessage;
import exception.EmptyException;
import exception.RemoteFailException;
import VO.MessageVO;
import VO.UserVO;

public interface MessageBLService {
 public ArrayList<MessageVO> get() throws RemoteFailException, EmptyException;
 public ResultMessage send(UserVO user, MessageVO message);  //user:id
 public ResultMessage delete(MessageVO message);
}
