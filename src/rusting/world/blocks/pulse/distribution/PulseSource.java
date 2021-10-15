package rusting.world.blocks.pulse.distribution;

import mindustry.gen.Building;
import rusting.interfaces.PulseBlockc;

import static mindustry.Vars.world;

public class PulseSource extends PulseNode{
    public PulseSource(String name) {
        super(name);
    }

    public class PulseSourceBuild extends PulseNodeBuild{
        @Override
        public boolean canRecievePulse(float pulse, Building build) {
            return false;
        }

        @Override
        public boolean connectableTo() {
            return false;
        }

        @Override
        public boolean receivePulse(float pulse, Building source) {
            return false;
        }

        @Override
        public void addPulseConnected() {
            super.addPulseConnected();
            proximity().each(b -> {
                //cap out Pulse of all blocks adjacent to the block
                if(b instanceof PulseBlockc) ((PulseBlockc) b).addPulse();
            });
                final int[] index = {0};
                connections.each(l -> {
                    Building j = world.build(l);
                    //need to double check, jic, because I've experienced crashes while a generator was pumping out energy
                    if(!(j instanceof PulseBlockc)){
                        connections.remove(connections.indexOf(l));
                        return;
                    }
                    if(chargef() <= 0 || j == null) return;
                    if(index[0] > connectionsPotential) connections.remove(l);
                    float energyTransmitted = Math.min(pulseModule.pulse, energyTransmission);
                    if(((PulseBlockc)j).receivePulse(energyTransmitted, this)) addPulse();
                    index[0]++;
                });
        }
    }
}
