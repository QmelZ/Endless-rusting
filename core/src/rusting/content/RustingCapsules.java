package rusting.content;

import mindustry.ctype.ContentList;
import rusting.EndlessRusting;
import rusting.type.Capsule;

public class RustingCapsules implements ContentList {

    public Capsule
    basic, basicLiquid
    ;

    @Override
    public void load() {
        basic = new Capsule(EndlessRusting.modname + "-basic-capsule"){{

        }};

        basicLiquid = new Capsule(EndlessRusting.modname + "-liquid-capsule"){{

        }};
    }
}
