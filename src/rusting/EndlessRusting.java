package rusting;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.ctype.ContentList;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import rusting.content.*;
import rusting.entities.holder.ItemScoreHolder;
import rusting.graphics.Drawr;

public class EndlessRusting extends Mod{

    public static String modname = "endless-rusting";

    public static ItemScoreHolder itemScorer;
    public static RustedSettingAdder settingAdder = new RustedSettingAdder();

    private static final Seq<ContentList> contentLists = Seq.with(
        new RustingStatusEffects(),
        new RustingItems(),
        new RustingBullets(),
        new RustingUnits(),
        new RustingBlocks(),
        new RustingWeathers(),
        new RustingPlanets()
    );

    public EndlessRusting(){

        Core.settings.defaults("drawtrails", true);

        Events.on(EventType.ClientLoadEvent.class,
            e -> {
                setup();
            }
        );
    }

    @Override
    public void init(){
        Vars.enableConsole = true;
        Varsr.init();
        Varsr.ui.init();
    }

    //called after all content is loaded. can be called again, for debugging.
    public void setup(){
        Varsr.setup();
        settingAdder.init();
    }

    @Override
    public void loadContent(){
        Drawr.setMethods();
        contentLists.each(ContentList::load);
        Color.cyan.set(Palr.pulseChargeEnd);
        Color.sky.set(Palr.pulseChargeStart);
    }
}
