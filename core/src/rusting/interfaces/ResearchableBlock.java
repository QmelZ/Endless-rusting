package rusting.interfaces;

import arc.struct.Seq;
import rusting.ctype.ResearchType;

public interface ResearchableBlock {

    //research types for the block
    public Seq<ResearchType> researchTypes = new Seq<ResearchType>();
}
