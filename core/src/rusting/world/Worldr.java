package rusting.world;

import arc.struct.Seq;
import mindustry.Vars;
import mindustry.gen.Building;

public class Worldr {

    private boolean returnBool = false;

    public boolean onTile(Building building, Seq<Integer> list){
        returnBool = false;
        list.each(i -> {
            if(returnBool) return;
            if(building == Vars.world.build(i)) returnBool = true;
        });
        return returnBool;
    }

}
