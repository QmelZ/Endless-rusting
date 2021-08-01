package rusting.world.blocks.defense.turret;

import arc.util.Time;
import mindustry.world.blocks.defense.turrets.LiquidTurret;

public class PumpLiquidTurret extends LiquidTurret {

    public float pumpSpeed = 0.045f;

    public PumpLiquidTurret(String name) {
        super(name);
    }

    public class PumpLiquidTurretBuild extends LiquidTurretBuild{
        @Override
        public void updateTile() {
            super.updateTile();
            if((liquids().current() == tile.floor().liquidDrop || (tile.floor().liquidDrop != null && ammoTypes.get(tile.floor().liquidDrop) != null)) && liquids().currentAmount() + pumpSpeed * Time.delta < liquidCapacity) liquids.add(tile.floor().liquidDrop, pumpSpeed * Time.delta );
        }
    }
}
