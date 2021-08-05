package rusting.world.modules;

import arc.struct.ObjectMap;
import mindustry.content.Items;
import mindustry.game.Team;
import mindustry.type.ItemStack;
import rusting.content.RustingBlocks;
import rusting.interfaces.ResearchableObject;

public class ResearchModule {

    public ResearchableObject item = (ResearchableObject) RustingBlocks.pulseBarrier;
    //self explanatory
    public boolean needsResearching = true;
    //requirements to be researched
    public ItemStack[] centerResearchRequirements = ItemStack.with(Items.copper, 1);
    //map of modules for each team
    public ObjectMap<Team, TeamResearchModule> teamMap = ObjectMap.of();
}
