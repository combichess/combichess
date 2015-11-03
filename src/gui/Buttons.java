package gui;

//        		"Nytt", "H�mta", "Spara", "Spara som", "L�gesinfo", "Ange sv�righetsgrad", "Be om f�rslag", "Ge upp", "Avsluta"

public enum Buttons {
    Nytt(101),
    Get(102),
    Save(103),
    SaveAs(104),
    Info(105),
    Difficult(106),
    Suggest(107),
    Resign(108),
    Exit(109);
    
    private int value;
    
    Buttons(int value)
    {
    	this.value = value;
    }
        
    public void setValue(int value)
    {
    	this.value = value;
    }
    
    public int getValue()
    {
    	return value;
    }
    
    @Override
	public String toString()
    {
    	switch(this)
    	{
    	case Nytt:
    		return "Nytt";
    	case Get:
    		return "�ppna";
    	case Save:
    		return "Spara";
    	case SaveAs:
    		return "Spara som";
    	case Info:
    		return "L�gesinfo";
    	case Difficult:
    		return "Ange Sv�righetsgrad";
    	case Suggest:
    		return "Be om f�rslag";
    	case Resign:
    		return "Ge upp";
    	case Exit:
    		return "Avsluta";
    	default:
    		return null;
    	}
    }
}



