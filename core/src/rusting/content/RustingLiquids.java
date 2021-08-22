package rusting.content;

import arc.graphics.Color;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.type.Liquid;

public class RustingLiquids implements ContentList {

    static Liquid
            melomae, cameaint;

    @Override
    public void load() {
        melomae = new Liquid("melomae"){{
            viscosity = 0.7f;
            effect = RustingStatusEffects.macotagus;
            color = Palr.pulseChargeStart;
            barColor = Color.sky;
        }};

        cameaint = new Liquid("cameaint"){{
            viscosity = 0.9f;
            effect = StatusEffects.slow;
            color = Palr.camaintLiquid;
            barColor = Palr.camaintLiquid;
        }};
    }
}
