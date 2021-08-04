package rusting.interfaces;

import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.world.Block;
import rusting.ctype.ResearchType;

public interface ResearchCenterc {

    Seq<String> researchableBlocks = new Seq<String>();

    Seq<String> researchedBlocks = new Seq<String>();
    //used in ui
    Seq<ResearchType> researchTypes = new Seq<>();

    default void setResearchableBlocks(){
        Vars.content.blocks().each(c -> {
            Log.info(c);
            //used as a temparary variable and in bug checks
            Researchablec initializedBuild = null;
            //test for block is researchable
            if(researchable(c)){
                Log.info(c.localizedName + " is a researchable block!");
                researchableBlocks.add(c.name);
            }
            else Log.info(c.localizedName + " is not a researchable block!");
        });
    }

    default boolean researchable(Block build){
        return researchable(false, build);
    }

    default boolean researchable(boolean techResearched, Block build){
        boolean[] isFound = {false};
        if(build instanceof ResearchableBlock){
            ResearchableBlock building = (ResearchableBlock) build;
            //if block is already researched, and it's checking whether block is currently not unlocked, return false;
            if(techResearched && researchedBlocks.contains(build.name)) return false;
            //is true if block is compatable with the research types
            researchTypes.each(e -> {
                if(!isFound[0] && building.researchTypes.contains(e)) {
                    isFound[0] = true;
                }
            });
        }
        return isFound[0];
    };
}
