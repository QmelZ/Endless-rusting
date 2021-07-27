package rusting.world.blocks.logic;

import mindustry.gen.Bullet;
import mindustry.world.blocks.logic.MessageBlock;

public class UnbreakableMessageBlock extends MessageBlock {

    public UnbreakableMessageBlock(String name) {
        super(name);
        targetable = false;
    }

    public class UnbreakableMessageBlockBuild extends MessageBuild{

        @Override
        public void damage(float amount, boolean withEffect) {
            return;
        }

        @Override
        public boolean collide(Bullet other) {
            return false;
        }
    }
}
