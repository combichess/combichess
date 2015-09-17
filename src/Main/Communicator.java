package main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import main.control.Message;
import main.control.MessageType;
import main.control.ProcessType;

public class Communicator {
	
	private static List<Message> messages = new LinkedList<Message>();
	
	public static synchronized void addMessage(Message mess) {
		messages.add(mess);
	}
	
	public static synchronized Message getMessage(MessageType messType, ProcessType messTo)
	{		
		Iterator<Message> iterator = messages.iterator();
		
		while(iterator.hasNext())
		{
			Message mess = iterator.next();
			if (mess.getMessageTo() == messTo && mess.getMessageType() == messType)
			{
				messages.remove(iterator);
				return mess;
			}
		}
		
		return null;
	}
	
	public static synchronized Message getMessage(ProcessType messTo)
	{
		Iterator<Message> iterator = messages.iterator();
		
		while(iterator.hasNext())
		{
			Message mess = iterator.next();
			if (mess.getMessageTo() == messTo)
			{
				iterator.remove();
				return mess;
			}
		}
		
		return null;
	}
	
	public static synchronized String staticToString()
	{
		return null;
	}
}
