package rusting.content;

import mindustry.ctype.ContentList;
import mindustry.type.SectorPreset;
import rusting.game.ERSectorPreset;

public class RustingSectorPresets implements ContentList {
    public static SectorPreset
    incipiensGrounds, plantaePresevereDomae, volenChannels, paileanCorridors, saltyShoals, overgrownMines, abystrikenCrevasse, crystallineCrags, pulsatingGroves, sulphuricSea, hangout, preservatory;
    ;

    @Override
    public void load() {
        incipiensGrounds = new ERSectorPreset("incipiens-grounds", RustingPlanets.err, 36){{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 23;
            difficulty = 1f;
        }};

        plantaePresevereDomae = new ERSectorPreset("plantae-presevere-domae", RustingPlanets.err, 196){{
            difficulty = 1f;
        }};

        volenChannels = new ERSectorPreset("volen-channels", RustingPlanets.err, 154){{
            difficulty = 4;
        }};

        paileanCorridors = new ERSectorPreset("pailean-corridors", RustingPlanets.err, 187){{
            captureWave = 45;
            difficulty = 3;
        }};

        saltyShoals = new ERSectorPreset("salty-shoals", RustingPlanets.err, 1){{
            captureWave = 40;
            difficulty = 6;
        }};

        overgrownMines = new ERSectorPreset("overgrown-mines", RustingPlanets.err, 97){{
            captureWave = 65;
            difficulty = 8;
        }};

        abystrikenCrevasse = new ERSectorPreset("abystriken-crevasse", RustingPlanets.err, 25){{
            captureWave = 38;
            difficulty = 4;
        }};

        crystallineCrags = new ERSectorPreset("crystalline-crags", RustingPlanets.err, 268){{
            captureWave = 40;
            difficulty = 4;
        }};

        pulsatingGroves = new ERSectorPreset("pulsating-groves", RustingPlanets.err, 56){{
            difficulty = 5;
            useAI = false;
        }};

        sulphuricSea = new ERSectorPreset("sulphur-seas", RustingPlanets.err, 0){{
            difficulty = 8;
            useAI = false;
            rules = (rules) -> {
                rules.tags.put("events.er.stingrayfail", "true");
            };
        }};

        hangout = new ERSectorPreset("pure-past-void-future", RustingPlanets.err, 35){{
            difficulty = 0;
            captureWave = 0;

        }};

        preservatory = new ERSectorPreset("lush-preservatory", RustingPlanets.err, 110){{
            difficulty = 0;
            captureWave = 0;
        }};

    }
}
