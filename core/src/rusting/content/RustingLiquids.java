package rusting.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.Liquid;

public class RustingLiquids implements ContentList {

    static Liquid
            melomae;

    @Override
    public void load() {
        melomae = new Liquid("melomae"){{
            viscosity = 0.7f;
            effect = RustingStatusEffects.macotagus;
            color = Palr.pulseChargeStart;
            barColor = Color.sky;
        }};
    }
}
