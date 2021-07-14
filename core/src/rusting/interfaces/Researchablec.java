package rusting.interfaces;

import arc.struct.Seq;
import mindustry.game.Team;
import rusting.ctype.ResearchType;

public interface Researchablec {

    //Centers with same type can research this
    Seq<ResearchType> researchTypes = new Seq<ResearchType>();

    default ResearchCenterc getResearchedCenter(Team team){
        return null;
    }

    default boolean validCenter(Team team){
        return getResearchedCenter(team) != null;
    }

    default boolean researched(){
        return false;
    }
}
