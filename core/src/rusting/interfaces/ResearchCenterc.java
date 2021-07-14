package rusting.interfaces;

import arc.struct.Seq;
import rusting.ctype.ResearchType;

public interface ResearchCenterc {

    Seq<String> researchedBlocks = new Seq<String>();
    //used in ui
    Seq<ResearchType> researchTypes = new Seq<>();

    default void setResearchedBlocks(){

    }
}
