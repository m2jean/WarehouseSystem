package enums;

public class DataMessage<T>{
	public ResultMessage resultMessage;
	public T data;
	
	public DataMessage(ResultMessage message){
		resultMessage = message;
		data=null;
	}
	public DataMessage(T data){
		resultMessage = ResultMessage.SUCCESS;
		this.data = data;
	}
}
