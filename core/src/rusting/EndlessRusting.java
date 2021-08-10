package rusting;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.mod.Mod;
import mindustry.type.UnitType;
import rusting.content.*;
import rusting.graphics.Drawr;

public class EndlessRusting extends Mod{

    public static String modname = "endless-rusting";

    public static RustedSettingAdder settingAdder = new RustedSettingAdder();
    private static Seq<UnitType> immunityUnits;

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

        Events.on(EventType.UnitCreateEvent.class,
            e -> {
                if(e.unit.type == RustingUnits.stingray && Vars.state.rules.tags.getBool("events.er.stingrayfail")){
                    for (int j = 0; j < 7; j++) {
                        for (int i = 0; i < 6; i++) {
                            Time.run(i * 65 + Mathf.random(85), () -> {
                                Tmp.v2.trns(e.spawner.rotation * 90, 350).add(e.spawner.x + 64 - Mathf.random(128), e.spawner.y + 128 - Mathf.random(128));
                                Tmp.v1.trns(25, RustingBullets.craeNukestorm.range() * 3).add(5 - Mathf.random(10), 5 - Mathf.random(10));
                                Call.createBullet(RustingBullets.craeNukestorm, Team.blue, Tmp.v2.x + Tmp.v1.x, Tmp.v2.y + Tmp.v1.y, Tmp.v1.add(Tmp.v2).angleTo(Tmp.v2.x, Tmp.v2.y), RustingBullets.craeNukestorm.damage, 1, 3);
                            });
                        }
                        Time.run(j * 145 + Mathf.random(195), () -> {
                            Tmp.v2.trns(e.spawner.rotation * 90, 350).add(e.spawner.x + 64 - Mathf.random(128), e.spawner.y + 128 - Mathf.random(128));
                            Tmp.v1.trns(25, RustingBullets.craeBalistorm.range() * 3).add(5 - Mathf.random(10), 5 - Mathf.random(10));
                            Call.createBullet(RustingBullets.craeBalistorm, Team.blue, Tmp.v2.x + Tmp.v1.x, Tmp.v2.y + Tmp.v1.y, Tmp.v1.add(Tmp.v2).angleTo(Tmp.v2.x, Tmp.v2.y), RustingBullets.craeBalistorm.damage, 1, 3);
                        });
                    }
                }
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
        immunityUnits = Seq.with(RustingUnits.stingray);
        Vars.content.statusEffects().each(s -> {
            //chek for NaN damage
            if(!s.name.contains("endless-rusting") && (s.damage == s.damage && s.damage >= 1 || s.speedMultiplier <= 0.85f)) immunityUnits.each(unit -> unit.immunities.add(s));

        });
        RustingUnits.stingray.immunities.remove(StatusEffects.melting);
        RustingUnits.stingray.immunities.remove(StatusEffects.tarred);


    }

    @Override
    public void loadContent(){
        Drawr.setMethods();
        Varsr.content.createContent();
        Color.cyan.set(Palr.pulseChargeEnd);
        Color.sky.set(Palr.pulseChargeStart);
    }
}
