package rusting.core;

import arc.struct.Seq;
import mindustry.ctype.ContentList;
import rusting.content.*;
import rusting.ctype.*;

//class to store special content, not found in Vars, and ER's content
public class RustedContentLoader {

    private final Seq<ContentList> contentLists = Seq.with(
            new RustingStatusEffects(),
            new RustingItems(),
            new RustingBullets(),
            new RustingUnits(),
            new RustingBlocks(),
            new RustingWeathers(),
            new RustingPlanets(),
            new RustingCapsules()
    );

    public Seq<ERContentType> ContentTypes = Seq.with(
            new ERContentType("capsule"),
            new ERContentType("logicFormat")
    );

    private Seq<ERContent>[] contentMap;

    public ERContentType getContentType(String name){
        return ContentTypes.find(c -> c.name == name);
    }

    public MappableERContent getByName(ERContentType content, String key){
        return null;
    }

        return (Seq<T>)contentMap[type.ordinal];
    }

    public void load(){
        contentMap = new Seq[ContentTypes.size];
        contentLists.each(ContentList::load);
    }

}
