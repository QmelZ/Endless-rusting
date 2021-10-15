package rusting.content;

import arc.Events;
import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.game.EventType;
import rusting.Varsr;
import rusting.ctype.SectorBasedAchievement;
import rusting.ctype.UnlockableAchievement;
import rusting.game.RustingEvents.AchievementUnlockEvent;

import static mindustry.Vars.state;
import static rusting.EndlessRusting.modname;

public class RustingAchievements implements ContentList {

    public static UnlockableAchievement
    //planet 1
    shardlingSteps, theBoatmansCursedBoatman, powerfulLight, pulseTeleporterConstructed, planet1Clear,
    //msc
    youMonster, giganticQuestionMark, GTFO;

    @Override
    public void load() {
        shardlingSteps = new SectorBasedAchievement( modname + "-shardling-steps", 36, RustingPlanets.err) {{

        }};

        theBoatmansCursedBoatman = new UnlockableAchievement(modname + "-the-boatmans-cursed-boatman") {{

            runUnlock = (object) -> {
                if((Vars.state.isCampaign() && state.rules.sector.id == 268) &&
                        Vars.player.unit().hasEffect(RustingStatusEffects.macrosis) &&
                        Vars.player.unit().hasEffect(RustingStatusEffects.macotagus) &&
                        Vars.player.unit().hasEffect(RustingStatusEffects.balancedPulsation) &&
                        Vars.player.unit().hasEffect(StatusEffects.wet) &&
                        Vars.player.unit().hasEffect(StatusEffects.freezing) &&
                        Vars.player.unit().hasEffect(StatusEffects.tarred)
                ){
                    RustingAchievements.theBoatmansCursedBoatman.unlock();
                    Events.fire(new AchievementUnlockEvent(RustingAchievements.theBoatmansCursedBoatman));
                }
            };
        }};

        powerfulLight = new UnlockableAchievement(modname + "-powerful-light"){{

        }};

        pulseTeleporterConstructed = new UnlockableAchievement(modname + "-pulse-teleporter-constructed") {{

        }};

        planet1Clear = new UnlockableAchievement(modname + "-planet-1-clear") {{

        }};

        youMonster = new UnlockableAchievement(modname + "-you-monster"){{

        }};

        GTFO = new UnlockableAchievement(modname + "-GTFO"){{
            triggerClass = EventType.WorldLoadEvent.class;
            runUnlock = e -> {
                if(Varsr.debug) {
                    RustingAchievements.GTFO.unlock();
                    Events.fire(new AchievementUnlockEvent(RustingAchievements.GTFO));
                }
            };
        }};
    }
}
