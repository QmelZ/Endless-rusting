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

        Core.settings.defaults("er.drawtrails", true);
        Core.settings.defaults("er.advancedeffects", true);

        Events.on(EventType.ClientLoadEvent.class,
            e -> {
                setup();
            }
        );

        Events.on(EventType.ContentInitEvent.class, e -> {
            Varsr.content.init();
        });

        Events.on(EventType.WorldLoadEvent.class, e -> {
                    Varsr.research.setupGameResearch();
                }
        );
    }

    @Override
    public void init(){
        Vars.enableConsole = true;
        Varsr.init();
    }

    //called after all content is loaded. can be called again, for debugging.
    public void setup(){
        Varsr.setup();
        settingAdder.init();
        Varsr.content.load();
    }

    @Override
    public void loadContent(){
        Drawr.setMethods();
        Varsr.content.createContent();
        Color.cyan.set(Palr.pulseChargeEnd);
        Color.sky.set(Palr.pulseChargeStart);
    }
}
