package rusting.world.blocks.pulse.defense;

import mindustry.gen.Bullet;
import rusting.world.blocks.pulse.PulseBlock;

public class PulseBarrier extends PulseBlock {

    public float pulseAbsorbMulti = 3;

    public PulseBarrier(String name) {
        super(name);
    }

    public class PulseBarrierBuild extends PulseBlockBuild{
        @Override
        public boolean collision(Bullet other) {
            if(other.type.pierceBuilding && other.collided.size < other.type.pierceCap && this.pulseEnergy * pulseAbsorbMulti >= other.type.damage){
                removePulse(other.type.damage/pulseAbsorbMulti);
                return true;
            }
            else return super.collision(other);
        }
    }
}
