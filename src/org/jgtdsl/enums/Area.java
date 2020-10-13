package org.jgtdsl.enums;

public enum  Area {
	SYLHET_EAST(0,"Sylhet_East"),
	SYLHET_WEST(1,"Sylhet_West"),
	SYLHET_SOUTH(2,"Sylhet_South"),
	SUNAMGONJ(3,"Sunamgonj"),
	Hobigonj(4,"Hobigonj"),
	SRIMANGAL(5,"Srimangal"),
	VANUGAS(6,"Vanugas"),
	KOMOLGANJ(7,"Komolganj"),
	MAULVIBAZAR(8,"Maulvibazar"),
	//
	CHHATAK(9,"CHHATAK"),
	GOLAPGONJ(10,"Golapgonj"),
	BOROLEKHA(11,"Borolekha"),
	Shayestagonj(12,"Shayestagonj"),
	Shahjibajar(13,"Shahjibajar"),
	Madhabpur(14,"Madhabpur"),	
	FENCHUGONJ(15,"Fenchugonj"),
	KULAURA(16,"Kulaura"),
	Chhatak(17,"DuaraBazar"),
	BEANIBAZAR(18,"Beanibazar"),
	JURI(19,"Juri"),
	Chunarughat(20,"Chunarughat"),
	Bahubol(21,"Bahubol"),
	NOBIGONJ(22,"Nobigonj"),
	SYLHET_NORTH(23,"Sylhet_North"),
	MAULVIBAZAR1(24,"Maulvibazar");
	
	
	

    private String label;
    private int id;

    private Area(int id,String label) {
        this.id=id;
    	this.label = label;
    }

    public String getLabel() {
        return label;
    }
    public int getId() {
        return id+1;
    }

    
}
