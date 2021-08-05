package rusting.interfaces;

import arc.struct.Seq;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import mindustry.type.ItemStack;
import rusting.Varsr;
import rusting.ctype.ResearchType;
import rusting.world.modules.ResearchModule;

public interface ResearchableObject {
    //research types for the block
    Seq<ResearchType> researchTypes = new Seq<ResearchType>();
    //research module with more specific information
    ResearchModule researchModule = new ResearchModule();
    //whether object is unlocked by default in custom games
    boolean customUnlockable = true;

    default void centerResearchRequirements(ItemStack[] stack){
        centerResearchRequirements(true, stack);
    }

    default void centerResearchRequirements(boolean requiresResearching, ItemStack[] stack){
        researchModule.centerResearchRequirements = stack;
        researchModule.needsResearching = requiresResearching;
        String[] string = {""};
        if(this instanceof UnlockableContent){
            string[0] += ((UnlockableContent) this).localizedName + "'s stack contains: ";
        }
        Varsr.logStack(string[0], stack);
    }

    default boolean researched(Team team){
        return customUnlockable && Vars.state.rules.infiniteResources || Varsr.research.getTeamModule(team, this).researched;
    }
}
