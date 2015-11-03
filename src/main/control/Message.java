package main.control;

public class Message {
	private ProcessType messageFrom = null;
	private ProcessType messageTo = null;
	private MessageType messageType = null;
	private String messageData = null;
	
	public Message(ProcessType messageFrom, ProcessType messageTo, MessageType messageType, String message) {
		this.messageData = message;
		this.messageFrom = messageFrom;
		this.messageTo = messageTo;
		this.messageType = messageType;
	}
	
	public ProcessType getMessageFrom()
	{
		return messageFrom;
	}
	
	public ProcessType getMessageTo()
	{
		return messageTo;
	}
	
	public MessageType getMessageType()
	{
		return messageType;
	}
	
	public String getMessageData()
	{
		return messageData;
	}
	
	@Override
	public String toString()
	{
		String str = "From: \"" + messageFrom.toString();
		str += "\"\tTo: \"" + messageTo.toString();
		str += "\tMessageType: \"" + messageType.toString() + "\"";
		str += "\tData: \"" + messageData + "\"";
		return str;
	}
}
