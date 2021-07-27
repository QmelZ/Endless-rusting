package rusting.interfaces;

import arc.struct.Seq;
import jdk.nashorn.internal.ir.Block;
import mindustry.game.Team;
import mindustry.gen.Building;
import rusting.ctype.ResearchType;

public interface Researchablec {

    //set this to current team, else defaults to derelict
    Team researchTeam = Team.derelict;

    //Centers with same type can research this
    //initialized on creation if necessary, else read off the block instead
    Seq<ResearchType> researchTypes = new Seq<ResearchType>();

    //overide this to get a valid center
    default ResearchCenterc getResearchedCenter(Team team){
        return null;
    }

    default boolean validCenter(Team team){
        return getResearchedCenter(team) != null;
    }

    default boolean researched(){
        if(!(this instanceof Block)) return false;
        Building building = (Building) this;
        ResearchCenterc center = getResearchedCenter(researchTeam);
        if(center == null || center.researchableBlocks.contains(building.block.name)) return false;
        return true;
    }
}
