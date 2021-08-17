package rusting.content;

import mindustry.ctype.ContentList;
import rusting.ctype.UnlockableAchievement;

import static rusting.EndlessRusting.modname;

public class RustingAchievements implements ContentList {

    public static UnlockableAchievement
    //planet 1
    shardlingSteps, theBoatmansCursedBoatman, pulseTeleporterConstructed, planet1Clear,
    //msc
    youMonster, giganticQuestionMark, GTFO;

    @Override
    public void load() {
        shardlingSteps = new UnlockableAchievement( modname + "-shardling-steps") {{

        }};

        theBoatmansCursedBoatman = new UnlockableAchievement(modname + "-the-boatmans-cursed-boatman") {{

        }};

        pulseTeleporterConstructed = new UnlockableAchievement(modname + "-pulse-teleporter-constructed") {{

        }};

        planet1Clear = new UnlockableAchievement(modname + "-planet-1-clear") {{

        }};

        youMonster = new UnlockableAchievement(modname + "-you-monster"){{

        }};
    }
}
