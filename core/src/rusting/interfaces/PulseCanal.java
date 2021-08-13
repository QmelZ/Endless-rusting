package rusting.interfaces;

import arc.struct.Seq;
import mindustry.world.Tile;

public interface PulseCanal extends PulseInstantTransportation{
    //used to update all connected blocks with new endpoints
    Seq<PulseInstantTransportation> connected = new Seq<PulseInstantTransportation>();

    default Tile findCanalEnd(Tile tile, float maxDst){
        return null;
    }
}
