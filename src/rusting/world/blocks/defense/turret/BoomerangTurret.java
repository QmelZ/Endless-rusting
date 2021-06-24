package rusting.world.blocks.defense.turret;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.bullet.BulletType;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import rusting.entities.bullet.BoomerangBulletType;

public class BoomerangTurret extends ItemTurret {


    public BoomerangTurret(String name) {
        super(name);
    }

    public class BoomerangTurretBuild extends ItemTurretBuild{
        @Override
        protected void shoot(BulletType type) {

            for (int j = 0; j < shots; j++) {
                final int[] o = new int[]{j};
                Time.run(j * burstSpacing/timeScale, () -> {
                    if(this == null || this.dead || !this.isAdded()) return;;
                    int i = o[0];
                    tr.trns(i * spread + rotation, shootLength);

                    effects();
                    float lifeScl = type.scaleVelocity ? Mathf.clamp(Mathf.dst(x + tr.x, y + tr.y, targetPos.x, targetPos.y) / type.range(), minRange / type.range(), range / type.range()) : 1f;

                    ((BoomerangBulletType) type).createBoomerang(this, team, x + tr.x, y + tr.y, i * spread + rotation, type.damage, 1f + Mathf.range(velocityInaccuracy), lifeScl, this);
                });
            }

            shotCounter++;

            recoil = recoilAmount;
            heat = 1f;
            useAmmo();
        }
    }
};
