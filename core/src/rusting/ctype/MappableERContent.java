package rusting.ctype;

import rusting.Varsr;

public abstract class MappableERContent extends ERContent{

    public String name;

    public MappableERContent(String name){
        this.name = name;
        Varsr.content.handleContent(this);
    }
}
