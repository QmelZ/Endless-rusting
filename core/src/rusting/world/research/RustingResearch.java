package rusting.world.research;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Groups;
import rusting.Varsr;
import rusting.ctype.ResearchType;
import rusting.interfaces.*;
import rusting.world.modules.ResearchModule;
import rusting.world.modules.TeamResearchModule;

public class RustingResearch {

    public ResearchCenterc tmpCenter;
    public TeamResearchModule tmpTeamModule, returnTeamModule = null;
    public ResearchModule tmpResearchModule;

    public ObjectMap<ResearchType, Seq<ResearchModule>> researchMap = new ObjectMap<ResearchType, Seq<ResearchModule>>();

    public void setupMap(){
        Varsr.content.researchTypes().each(r -> {
            researchMap.put(r, new Seq());
            Log.info(r.name);
        });

        Vars.content.each(b -> {
            if(b instanceof ResearchableObject) {
                ((ResearchableObject) b).researchTypes.each(researchType -> {
                    researchMap.get(researchType).add(new ResearchModule(){{
                        item = (ResearchableObject)b;
                    }});
                    Log.info(b);
                });
            }
        });
    }

    public void setupGameResearch(){
        Groups.build.each(b -> {
            if(!(b instanceof ResearchCenterc)) return;
            tmpCenter = asResearchCenter(b);
            tmpCenter.setResearchableBlocks();
            tmpCenter.researched.each(object -> {
                getTeamModule(b.team, object).researched = true;
                Log.info(object);
            });
        });
    }

    public ResearchModule getResearchModule(Team team, ResearchableObject object){
        tmpResearchModule = null;
        object.researchTypes.each(r -> {
            researchMap.get(r).each(module -> {
                if(module.item == object) tmpResearchModule = module;
            });
        });
        return tmpResearchModule;
    }

    public TeamResearchModule getTeamModule(Team team, ResearchableObject object){
        returnTeamModule = null;
        object.researchTypes.each(r -> {
            researchMap.get(r).each(module -> {
                tmpTeamModule = module.teamMap.get(team);
                if(module.item == object) returnTeamModule = tmpTeamModule;
            });
        });
        return returnTeamModule;
    }

    private ResearchCenterc asResearchCenter(Object o){
        return (ResearchCenterc) o;
    }
}
