package rusting.world.blocks.defense.turret;

import arc.util.Time;
import mindustry.world.blocks.defense.turrets.ItemTurret;

public class AutoreloadItemTurret extends ItemTurret {
    public float autoreloadThreshold = 0.9f;

    public AutoreloadItemTurret(String name) {
        super(name);
    }

    public class AutoreloadItemTurretBuild extends ItemTurretBuild{
        @Override
        public void update() {
            super.update();
            if(reload <= autoreloadThreshold * reloadTime && !isShooting()) {
                reload = Math.min(reload + Time.delta / reloadTime * baseReloadSpeed() * 60 * 3, autoreloadThreshold * reloadTime);
            }
        }
    }
}
