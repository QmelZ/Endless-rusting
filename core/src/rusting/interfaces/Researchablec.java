package rusting.interfaces;

import mindustry.game.Team;
import mindustry.gen.Building;

public interface Researchablec {
    default Building getResearchedCenter(Team team){
        return null;
    }

    default boolean validCenter(Team team){
        return getResearchedCenter(team) != null;
    }

    default boolean researched(){
        return false;
    }
}
