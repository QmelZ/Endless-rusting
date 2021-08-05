package rusting.interfaces;

import arc.struct.Seq;
import mindustry.game.Team;
import rusting.ctype.ResearchType;

public interface Researchablec {

    //set this to current team, else defaults to derelict
    Team researchTeam = Team.derelict;
    //whether object is unlocked by default in custom games
    boolean customUnlockable = true;
    //Centers with same type can research this
    //initialized on creation if necessary, else read off the block instead
    Seq<ResearchType> researchTypes = new Seq<ResearchType>();


}
