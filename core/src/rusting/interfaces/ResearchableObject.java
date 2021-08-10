package rusting.interfaces;

import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import mindustry.type.ItemStack;
import rusting.Varsr;
import rusting.ctype.ResearchType;
import rusting.world.modules.ResearchModule;
import rusting.world.modules.ResearchModuleHolder;

public interface ResearchableObject {
    //research types for the block
    Seq<ResearchType> researchTypes = new Seq<ResearchType>();
    //research module with more specific information
    ResearchModuleHolder researchModule = new ResearchModuleHolder();
    //whether object is unlocked by default in custom games
    boolean customUnlockable = true;

    default void centerResearchRequirements(ItemStack[] stack){
        centerResearchRequirements(true, true, stack);
    }

    default void centerResearchRequirements(boolean requiresResearching, ItemStack[] stack){
        centerResearchRequirements(true, requiresResearching, stack);
    }

    default void resetResearchModule(){
        researchModule.research = new ResearchModule(true, ItemStack.with(), this);
        Log.info("reset module");
    }

    default ResearchModule getResearchModule(){
        return researchModule.research;
    }

    default void centerResearchRequirements(boolean reset, boolean requiresResearching, ItemStack[] stack){
        if(reset) resetResearchModule();
        getResearchModule().centerResearchRequirements = stack;
        getResearchModule().needsResearching = requiresResearching;
        getResearchModule().item = this;
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
