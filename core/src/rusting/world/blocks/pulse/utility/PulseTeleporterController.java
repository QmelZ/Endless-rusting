package rusting.world.blocks.pulse.utility;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.*;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.world.Tile;
import rusting.interfaces.PrimitiveControlBlock;
import rusting.world.blocks.pulse.distribution.PulseCanal.PulseCanalBuild;
import rusting.world.blocks.pulse.utility.PulseTeleporterCorner.PulseTeleporterCornerBuild;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class PulseTeleporterController extends PulseTeleporterPart {

    //range of the teleporter
    float teleporterRange = 56;
    //How many nodes you can connect to
    public int connectionsPotential = 1;

    public PulseTeleporterController(String name) {
        super(name);
        configurable = true;
        config(Integer.class, (PulseTeleporterControllerBuild entity, Integer i) -> {
            Building other = world.build(i);
            if(!(other instanceof PulseBlockBuild)) return;
            if(entity.connections.contains(i)){
                //unlink
                entity.connections.remove(i);
            }
            else if(other instanceof PrimitiveControlBlock){
                entity.connections.add(i);
            }
        });
    }

    private PulseTeleporterControllerBuild build;
    private Building tmpBuild, tmpBuild2;
    boolean returnBool = false, cornerFound = false;
    Tile tmpTile = null;

    private PulseTeleporterControllerBuild asControllerBuild(Building build){
        return (PulseTeleporterControllerBuild) build;
    }

    public static boolean teleCanConnect(PulseTeleporterControllerBuild build, PulseTeleporterControllerBuild target){
        return (target instanceof PulseTeleporterControllerBuild && !build.connections.contains(target.pos()) && build.connections.size < ((PulseTeleporterController) (build.block)).connectionsPotential && ((PulseTeleporterController) build.block).teleporterRange * 8 >= Mathf.dst(build.x, build.y, target.x, target.y));
    }

    //function findMulti(){let build = Vars.world.buildWorld(Core.input.mouseWorld().x, Core.input.mouseWorld().y); return build.block.multiblockFormed(build.tile)}

    public boolean multiblockFormed(Tile tile) {
        returnBool = false;
        if(!(tile.build instanceof PulseTeleporterControllerBuild)) return false;
        build = asControllerBuild(tile.build);
        build.proximity().each(b -> {
            if(returnBool == true) {
                //Log.info("breaking out of loop");
                return;
            }
            //Log.info("Building is instance of canal: " + (b instanceof PulseCanalBuild));
            //Log.info("build is facing: " + (b.rotation * 90) % 360);
            //Log.info("angle to block is: " + build.angleTo(b.x, b.y));
            if(b instanceof PulseCanalBuild && (b.rotation * 90) % 360 == build.angleTo(b.x, b.y)) {
                returnBool = true;
                tmpBuild2 = b;
            }
        });
        if(returnBool == false) return returnBool;
        //Log.info("found canal");
        for (int i = 0; i < 3; i++) {
            //Log.info("cycle " + (i + 1));
            if(tmpBuild2 instanceof PulseCanalBuild) {
                tmpTile = rusting.world.blocks.pulse.distribution.PulseCanal.asCanal(tmpBuild2).canalEnding;
                if(Mathf.dst(tmpBuild2.tile.x, tmpBuild2.tile.y, tmpTile.x, tmpTile.y) < 8) break;
                if (i == 3 && tmpTile == tile) returnBool = true;
                else if(tmpTile.build != null && tmpTile.build instanceof PulseTeleporterCornerBuild){
                    tmpBuild = tmpTile.build;
                    Tmp.v1.trns(tmpBuild.rotation * 90 - 90, 8);
                    tmpBuild2 = world.buildWorld(tmpBuild.x + Tmp.v1.x, tmpBuild.y + Tmp.v1.y);
                }
            }
        }
        return returnBool;
    }

    public class PulseTeleporterControllerBuild extends PulseBlockBuild {

        /*
        0: idle
        1: teleporting unknown blocks and units in
        2: teleporting blocks and units from  the unknown
         */
        public int state = 0;
        boolean multiblockFormed = false;
        Seq<Integer> connections = new Seq();
        Seq<Building> multiblockParts = new Seq();

        @Override
        public Object config() {
            return super.config();
        }

        @Override
        public void buildConfiguration(Table table) {
            super.buildConfiguration(table);
        }

        @Override
        public void drawConfigure(){

            float scl = Mathf.absin(Time.time, 4f, 1f);

            Drawf.circles(x, y, tile.block().size * tilesize / 2f + 1f + scl);
            Drawf.circles(x, y, (float) (teleporterRange * tilesize));

            connections.each(l -> {
                Building link = world.build(l);
                //prevent crashes that can happen when config is being drawn and a block is destroyed which the node is linekd up to
                if(link == null) return;
                Drawf.square(link.x, link.y, link.block.size * tilesize / 2f + 1f + scl, Pal.place);
            });

            Draw.reset();
        }

        @Override
        public void updateTile() {
            super.updateTile();
            if(chargef() > 0.9f && !multiblockFormed) multiblockFormed = multiblockFormed(tile);
        }
    }
}
