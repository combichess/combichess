package gui;

//        		"Nytt", "Hämta", "Spara", "Spara som", "Lägesinfo", "Ange svårighetsgrad", "Be om förslag", "Ge upp", "Avsluta"

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
    		return "Öppna";
    	case Save:
    		return "Spara";
    	case SaveAs:
    		return "Spara som";
    	case Info:
    		return "Lägesinfo";
    	case Difficult:
    		return "Ange Svårighetsgrad";
    	case Suggest:
    		return "Be om förslag";
    	case Resign:
    		return "Ge upp";
    	case Exit:
    		return "Avsluta";
    	default:
    		return null;
    	}
    }
}



