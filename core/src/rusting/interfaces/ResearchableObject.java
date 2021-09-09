package rusting.interfaces;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import mindustry.type.ItemStack;
import rusting.Varsr;
import rusting.ctype.ResearchType;
import rusting.world.modules.ResearchModule;

public interface ResearchableObject {

    default String name(){
        return "defaukt";
    }

    default Seq<ResearchType> researchTypes(){
        return null;
    }

    default TextureRegion researchUIcon(){
        return Core.atlas.find(name());
    }

    default void centerResearchRequirements(ItemStack[] stack){
        centerResearchRequirements(true, stack);
    }

    default ResearchModule getResearchModule(){
        return null;
    }

    default void centerResearchRequirements(boolean requiresResearching, ItemStack[] stack){
        if(getResearchModule() == null) return;
        getResearchModule().needsResearching = requiresResearching;
        getResearchModule().centerResearchRequirements = stack;
        String[] string = {""};
        if(this instanceof UnlockableContent){
            string[0] += ((UnlockableContent) this).localizedName + "'s stack contains: ";
        }
    }

    default boolean researched(Team team){
        return getResearchModule().needsResearching && Vars.state.rules.infiniteResources || Varsr.research.getTeamModule(team, this).researched;
    }

    default void hideFromUI(){
        getResearchModule().isHidden = true;
    }
}
