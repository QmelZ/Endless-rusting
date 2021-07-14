package rusting.content;

import mindustry.ctype.ContentList;
import mindustry.type.SectorPreset;

public class RustingSectorPresets implements ContentList {
    public static SectorPreset
    incipiensGrounds, plantaePresevereDomae, paileanCorridors, abystrikenCrevasse, pulsatingGroves, hangout, preservatory;
    ;

    @Override
    public void load() {
        incipiensGrounds = new SectorPreset("incipiens-grounds", RustingPlanets.err, 36){{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 23;
            difficulty = 1f;
        }};

        plantaePresevereDomae = new SectorPreset("plantae-presevere-domae", RustingPlanets.err, 196){{
            difficulty = 1f;
            useAI = false;
        }};

        paileanCorridors = new SectorPreset("pailean-corridors", RustingPlanets.err, 79){{
            captureWave = 45;
            difficulty = 3;
        }};

        abystrikenCrevasse = new SectorPreset("abystriken-crevasse", RustingPlanets.err, 45){{
            captureWave = 38;
            difficulty = 4;
        }};

        pulsatingGroves = new SectorPreset("pulsating-groves", RustingPlanets.err, 56){{
            difficulty = 5;
        }};

        hangout = new SectorPreset("pure-past-void-future", RustingPlanets.err, 35){{
            difficulty = 0;
            captureWave = 0;
        }};

        preservatory = new SectorPreset("lush-preservatory", RustingPlanets.err, 110){{
            difficulty = 0;
            captureWave = 0;
        }};

    }
}
