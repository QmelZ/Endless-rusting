package rusting.world.research;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.Team;
import rusting.Varsr;
import rusting.ctype.ResearchType;
import rusting.interfaces.ResearchCenterc;
import rusting.interfaces.ResearchableObject;
import rusting.world.modules.ResearchModule;
import rusting.world.modules.TeamResearchModule;

public class RustingResearch {

    private ResearchableObject object;
    private ResearchModule module;

    public ResearchCenterc tmpCenter;
    public TeamResearchModule tmpTeamModule, returnTeamModule = null;
    public ResearchModule tmpResearchModule;

    public ObjectMap<ResearchType, Seq<ResearchModule>> researchMap = new ObjectMap<ResearchType, Seq<ResearchModule>>();

    public class hahano{
        public ObjectMap<ResearchType, ObjectMap<Team, Seq<ResearchableObject>>> webedoinaliltrollin = ObjectMap.of();
    }

    public void setupMap(){
        Varsr.content.researchTypes().each(r -> {
            researchMap.put(r, new Seq());
        });

        Vars.content.each(b -> {
            if(b instanceof ResearchableObject) {
                object = (ResearchableObject) b;
                object.getResearchModule().item = object;
                object.researchTypes().each(researchType -> {
                    researchMap.get(researchType).add(object.getResearchModule());
                });
            }
        });

        Vars.content.each(b -> {
            if(b instanceof ResearchableObject) {
                object = (ResearchableObject) b;
                Log.info(object.getResearchModule().item);
            }
        });
    }

    public void saveGameResearch(){
        //Vars.state.rules.tags.put("tags.er.researchedBlocks", JsonIO.json.toJson(Varsr.formats));
        Log.info("goodbye...");
    };

    public void setupGameResearch(){

        Log.info("hello!");

        //String map = Vars.state.rules.tags.get("tags.er.researchedBlocks", "");

        /*
        ObjectMap<ResearchType, ObjectMap<Team, Seq<ResearchableObject>>> bloinkmapi = null;
        String map = Vars.state.rules.tags.get("tags.er.researchedBlocks", "");

        try {
            bloinkmapi = JsonIO.json.fromJson(ObjectMap.class, ResearchType.class, Seq.class, ResearchableObject.class, map);
        }

        if(bloinkmapi == null)

        Groups.build.each(b -> {
            if(!(b instanceof ResearchCenterc)) return;
            tmpCenter = asResearchCenter(b);
            tmpCenter.setResearchableBlocks();
            tmpCenter.researched.each(object -> {
                getTeamModule(b.team, object).researched = true;
                Log.info(object);
            });
        });

         */
    }

    public ResearchModule getResearchModule(Team team, ResearchableObject object){
        tmpResearchModule = null;
        object.researchTypes().each(r -> {
            researchMap.get(r).each(module -> {
                if(module.item == object) tmpResearchModule = module;
            });
        });
        return tmpResearchModule;
    }

    public TeamResearchModule getTeamModule(Team team, ResearchableObject object){
        returnTeamModule = null;
        object.researchTypes().each(r -> {
            researchMap.get(r).each(module -> {
                tmpTeamModule = module.teamMap.get(team);
                if(module.item == object) returnTeamModule = tmpTeamModule;
                //failsafe
                else if(tmpTeamModule == null) module.teamMap.put(team, new TeamResearchModule());
            });
        });
        return returnTeamModule;
    }

    private ResearchCenterc asResearchCenter(Object o){
        return (ResearchCenterc) o;
    }
}
