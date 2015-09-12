package main.control;

public class Message {
	private ProcessType messageTo = null;
	private MessageType messageType = null;
	private Object messageData = null;
	
	public Message(ProcessType messageTo, MessageType messageType, Object message) {
		this.messageData = message;
		this.messageTo = messageTo;
		this.messageType = messageType;
	}
	
	public ProcessType getMessageTo()
	{
		return messageTo;
	}
	
	public MessageType getMessageType()
	{
		return messageType;
	}
	
	public Object getMessageData()
	{
		return messageData;
	}
}
