package rusting.core;

import arc.struct.Seq;
import mindustry.ctype.ContentList;
import rusting.content.*;

//class to store special content, not found in Vars, and ER's content
public class RustedContentLoader {

    private static final Seq<ContentList> contentLists = Seq.with(
            new RustingStatusEffects(),
            new RustingItems(),
            new RustingBullets(),
            new RustingUnits(),
            new RustingBlocks(),
            new RustingWeathers(),
            new RustingPlanets()
    );

    public void load(){
        contentLists.each(ContentList::load);
    }

}
