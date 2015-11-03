package main.control;

public enum ProcessType {
	Main(0),
	Gui_1(1),
	Board_1(2);
	
	int value;
	
	private ProcessType(int value)
	{
		this.value = value;
	}
	
	@Override
	public String toString()
	{
		switch(value)
		{
		case 0:
			return "Main";
		case 1:
			return "Gui";
		case 2:
			return "Board";
		default:
			return null;
			
		}
	}
}
