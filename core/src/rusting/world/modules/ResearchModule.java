package rusting.world.modules;

import arc.struct.ObjectMap;
import mindustry.content.Items;
import mindustry.game.Team;
import mindustry.type.ItemStack;
import rusting.interfaces.ResearchableObject;

public class ResearchModule {

    public static int nextFreeId = 0;

    public int id = 0;

    public ResearchModule(){

    }

    public ResearchModule(ItemStack[] stacks){
        this(true, ItemStack.with(), null);
    }

    public ResearchModule(ItemStack[] stacks, ResearchableObject item){
        this(true, stacks, item);
    }

    public ResearchModule(boolean needsResearching, ItemStack[] stacks, ResearchableObject item){
        centerResearchRequirements = stacks;
        this.item = item;
        this.needsResearching = needsResearching;
        id = nextFreeId;
        nextFreeId++;
    }

    public ResearchableObject item = null;
    //self explanatory
    public boolean needsResearching = true;
    //requirements to be researched
    public ItemStack[] centerResearchRequirements = ItemStack.with(Items.copper, 1);
    //map of modules for each team
    public ObjectMap<Team, TeamResearchModule> teamMap = ObjectMap.of();
}