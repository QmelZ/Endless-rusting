package rusting.world.blocks.pulse.defense;

import arc.math.Mathf;
import arc.scene.ui.ButtonGroup;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import rusting.world.blocks.pulse.PulseBlock;

public class PulseParticleSpawner extends PulseBlock {

    public Effect[] effects = {Fx.smoke};
    public float effectFrequency = 1f, consumeFrequency = 360;

    public PulseParticleSpawner(String name) {
        super(name);
        baseEfficiency = 0.15f;
        configurable = true;
        saveConfig = true;

        config(Integer.class, (PulseParticleSpawnerBuild entity, Integer i) -> {
            Log.info(i);
            entity.state = i;
            entity.clampState();
        });
    }

    public class PulseParticleSpawnerBuild extends PulseBlockBuild{

        int state = 0;
        float particleSpawnInterval = 0, consumeTimer = 0;

        @Override
        public void update() {
            super.update();
            if(effects.length > 0 && allConsValid()){
                particleSpawnInterval += pulseEfficiency() * effectFrequency;
            }
            if(particleSpawnInterval >= 1){
                effects[state].at(x, y);
                particleSpawnInterval = 0;
            }

            if(consumeTimer >= consumeFrequency){
                consume();
                customConsume();
                consumeTimer = 0;
            }
        }

        @Override
        public Object config() {
            return state;
        }

        public void clampState(){
            state = Mathf.clamp(state, 0, effects.length);
        }

        @Override
        public void buildConfiguration(Table table) {
            ButtonGroup<ImageButton> group = new ButtonGroup<>();
            Table buttons = new Table();

            for(int l = 0; l < effects.length; l++){
                final int i = l;
                buttons.button(String.valueOf(i), () -> {
                    configure(i);
                }).size(44);
            }
            table.add(buttons);
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public void write(Writes w) {
            super.write(w);
            w.i(state);
            w.f(particleSpawnInterval);
            w.f(consumeTimer);
        }

        @Override
        public void read(Reads r, byte revision) {
            super.read(r, revision);
            state = r.i();
            particleSpawnInterval = r.f();
            consumeTimer = r.f();
        }
    }
}