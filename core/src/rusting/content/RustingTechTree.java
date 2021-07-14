package rusting.content;

import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.content.TechTree;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.ContentList;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives.*;
import mindustry.type.ItemStack;

import static mindustry.content.Blocks.*;
import static mindustry.content.Blocks.steamGenerator;
import static mindustry.content.SectorPresets.groundZero;
import static mindustry.game.Objectives.SectorComplete;
import static rusting.content.RustingBlocks.*;
import static rusting.content.RustingSectorPresets.*;

public class RustingTechTree implements ContentList {
    static TechTree.TechNode context = null;

    @Override
    public void load(){

        extendNode(coreShard, () -> {
            node(pulseResearchCenter, Seq.with(new SectorComplete(paileanCorridors)), () -> {

            });
        });

        extendNode(pulseResearchCenter, () -> {
            node(pulseCollector, () -> {

                node(pulseNode, () -> {
                    node(pulseResonator, () -> {
                        node(pulseSiphon, () -> {

                        });
                    });


                    node(pulseUpkeeper, Seq.with(new SectorComplete(pulsatingGroves)), () -> {
                        node(smallParticleSpawner, () -> {

                        });
                    });

                    node(pulseTesla, Seq.with(new SectorComplete(pulsatingGroves)), () -> {

                    });
                });
                node(pulseGenerator, Seq.with(new SectorComplete(abystrikenCrevasse), new Produce(RustingItems.melonaleum)), () -> {

                });
            });

            node(archangel, Seq.with(new SectorComplete(abystrikenCrevasse)), () -> {

            });

            node(pulseFactory, Seq.with(new SectorComplete(pulsatingGroves)), () -> {

                node(RustingUnits.duono, () -> {
                    node(RustingUnits.duoly, () -> {
                        node(RustingUnits.duanga, () -> {

                        });
                    });
                });

                node(pulseDistributor, () -> {

                });

                node(enlightenmentReconstructor, () -> {
                    node(ascendanceReconstructor, () -> {

                    });
                });
            });

            node(pulseLandmine, () -> {

            });

            node(pulseBarrier, () -> {
                node(pulseBarrierLarge, () -> {});
            });

            node(RustingItems.melonaleum, Seq.with(new Produce(RustingItems.melonaleum)), () -> {

            });
        });

        extendNode(duo, () -> {
            node(prikend, Seq.with(new SectorComplete(pulsatingGroves)), () -> {
                node(prsimdeome, () -> {
                    node(prefraecon, Seq.with(new SectorComplete(pulsatingGroves)), () -> {

                    });

                    node(rangi, () -> {

                    });
                });
            });

            node(refract, Seq.with(new SectorComplete(plantaePresevereDomae)), () -> {
                node(diffract, () -> {
                    node(refract, () -> {

                    });
                });
            });
        });

        extendNode(mender, () -> {
            node(thrum, Seq.with(new SectorComplete(plantaePresevereDomae)), () -> {
                node(spikent, Seq.with(new SectorComplete(pulsatingGroves)), () -> {

                });
            });
        });

        extendNode(groundZero,  () -> {
            node(incipiensGrounds, () -> {
                node(hangout, Seq.with(new SectorComplete(incipiensGrounds), new Produce(Items.titanium)), () -> {
                    node(preservatory, Seq.with(new SectorComplete(paileanCorridors), new Research(terraConveyor)), () -> {});
                });

                node(plantaePresevereDomae, Seq.with(new SectorComplete(incipiensGrounds), new Research(navalFactory)), () -> {

                });

                node(paileanCorridors, Seq.with(new SectorComplete(incipiensGrounds), new Research(pneumaticDrill), new Research(itemBridge), new Produce(Items.graphite), new Produce(Items.silicon)), () -> {
                    node(abystrikenCrevasse, Seq.with(new SectorComplete(paileanCorridors), new Research(pulseResearchCenter), new Research(ripple), new Research(titaniumConveyor)), () -> {});
                    node(pulsatingGroves, Seq.with(new SectorComplete(paileanCorridors), new Research(prsimdeome), new Research(steamGenerator)), () -> {

                    });
                });
            });
        });

    }

    //sets context to the node from the UnlockableContent
    private static void extendNode(UnlockableContent parent, Runnable children){
        TechNode parnode = TechTree.all.find(t -> t.content == parent);
        context = parnode;
        children.run();
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives, Runnable children){
        TechNode node = new TechNode(context, content, requirements);
        if(objectives != null) node.objectives = objectives;

        TechNode prev = context;
        context = node;
        children.run();
        context = prev;
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Runnable children){
        node(content, requirements, null, children);
    }

    private static void node(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives, children);
    }

    private static void node(UnlockableContent content, Runnable children){
        node(content, content.researchRequirements(), children);
    }

    private static void node(UnlockableContent block){
        node(block, () -> {});
    }

    private static void nodeProduce(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives.and(new Produce(content)), children);
    }

    private static void nodeProduce(UnlockableContent content, Runnable children){
        nodeProduce(content, Seq.with(), children);
    }

    private static void nodeProduce(UnlockableContent content){
        nodeProduce(content, Seq.with(), () -> {});
    }
}
