package rusting.ctype;

import arc.Core;
import rusting.Varsr;

public abstract class MappableERContent extends ERContent{

    public final String name;
    public String localizedName;

    public MappableERContent(String name){
        this.name = name;
        Varsr.content.handleContent(this);
    }

    @Override
    public void load() {
        super.load();
        localizedName = Core.bundle.get(getContentType().name + "." + name + ".name", name);
    }
}
