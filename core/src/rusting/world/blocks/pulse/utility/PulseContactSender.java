package rusting.world.blocks.pulse.utility;

import arc.struct.Seq;
import arc.util.io.Reads;

//receives information from adjacent blocks, then sends it to connected blocks. Averages out multiple sources of information.
public class PulseContactSender extends PulseControlModule{

    public PulseContactSender(String name) {
        super(name);
    }

    public class PulseContactReceiverBuild extends PulseControlModuleBuild{

        Seq<PulseControlModuleBuild> adjacent = new Seq<PulseControlModuleBuild>();

        Seq<Double>[] informationStore = new Seq[]{new Seq(), new Seq(), new Seq(), new Seq()};

        public void updateProx(){
            proximity().each(l -> {
                if(l instanceof PulseControlModuleBuild) adjacent.add((PulseControlModuleBuild) l);
            });
        }

        @Override
        public void created() {
            updateProx();
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            updateProx();
        }

        @Override
        public void update() {
            super.update();
            adjacent.each(l -> {
                l.exportInformationDefault(this);
            });
        }

        @Override
        public void rawControl(double p1, double p2, double p3, double p4) {

        }
    }

}
