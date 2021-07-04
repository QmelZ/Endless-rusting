package rusting.content;

import arc.struct.Seq;
import mindustry.ctype.ContentList;
import rusting.capsule.Capsule;

public class RustingCapsules implements ContentList {

    //stores all defined capsules, maps them to id
    public static Seq<Capsule> capsules = new Seq<Capsule>();
    public static float nextFreeID = 0;

    public static float nextID(){
        if(capsules.find(c -> c.ID == nextFreeID) == null){
            nextFreeID++;
            return nextFreeID - 1;
        }
        capsules.each(c -> {
            if(c.ID == nextFreeID){
                nextFreeID++;
            }
        });
        return nextFreeID;
    };

    public Capsule
    basic
    ;

    @Override
    public void load() {
        basic = new Capsule("endless-rusting-capsule"){{

        }};
    }
}
