package rusting.content;

import arc.struct.Seq;
import mindustry.ctype.ContentList;
import rusting.type.Capsule;

public class RustingCapsules implements ContentList {

    public Capsule
    basic
    ;

    @Override
    public void load() {
        basic = new Capsule("endless-rusting-capsule"){{

        }};
    }
}
