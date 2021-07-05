package rusting;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import rusting.content.Palr;
import rusting.content.RustedSettingAdder;
import rusting.graphics.Drawr;

public class EndlessRusting extends Mod{

    public static String modname = "endless-rusting";

    public static RustedSettingAdder settingAdder = new RustedSettingAdder();

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
        Varsr.content.load();
        Color.cyan.set(Palr.pulseChargeEnd);
        Color.sky.set(Palr.pulseChargeStart);
    }
}
