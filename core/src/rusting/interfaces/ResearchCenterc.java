package rusting.interfaces;

import arc.struct.Seq;
import mindustry.game.Team;
import rusting.Varsr;
import rusting.ctype.ResearchType;
import rusting.world.research.RustingResearch;

public interface ResearchCenterc {

    public RustingResearch research = Varsr.research;

    //used to avoid repeatedyly accesing the research modules in RustingResearch
    Seq<ResearchableObject> researchable = new Seq<ResearchableObject>();

    Seq<ResearchableObject> researched = new Seq<ResearchableObject>();
    //used in ui
    Seq<ResearchType> researchTypes = new Seq<>();

    default void setResearchableBlocks(){
        researched.clear();
        researchTypes.each(r -> {
            research.researchMap.get(r).each(b -> {
                if(researchTypes.size == 1 || !researchable.contains(b.item)) researchable.add(b.item);
            });
        });
    }

    default boolean canResearch(ResearchableObject build){
        return researchable.contains(build);
    }
    default boolean canResearch(boolean techResearched, Team team, ResearchableObject build){
        research.tmpResearchModule = research.getResearchModule(team, build);
        return techResearched && research.tmpResearchModule.needsResearching && !research.tmpResearchModule.teamMap.get(team).researched && canResearch(build);
    };
}
