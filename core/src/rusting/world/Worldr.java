package rusting.world;

import arc.Events;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.world.Tile;
import rusting.world.blocks.environment.DamagingFloor;

public class Worldr {

    public Worldr(){
        
        corrosiveTiles.clear();
        
        Events.on(EventType.WorldLoadEvent.class, e -> {
            Vars.world.tiles.eachTile(t -> {
                if(t.floor() instanceof DamagingFloor) corrosiveTiles.add(t);
            });
        });
    }

    public Seq<Tile> corrosiveTiles = Seq.with();
    private boolean returnBool = false;

    public boolean onTile(Building building, Seq<Integer> list){
        returnBool = false;
        list.each(i -> {
            if(returnBool) return;
            if(building == Vars.world.build(i)) returnBool = true;
        });
        return returnBool;
    }

}
