package rusting.entities.bullet;

import arc.func.Cons;
import arc.util.Nullable;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;

//includes cons for some methods in BulletType
public class ConsBulletType extends BasicBulletType {
    @Nullable
    public Cons<Bullet> consUpdate;

    @Nullable
    public Cons<Bullet> consDespawned;

    @Nullable
    public Cons<Bullet> consHit;

    public ConsBulletType(int speed, int damage, String sprite){
        super(speed, damage, sprite);
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        if(consUpdate != null) consUpdate.get(b);
    }

    @Override
    public void hit(Bullet b, float x, float y) {
        super.hit(b, x, y);
        if(consHit != null) consHit.get(b);
    }

    @Override
    public void despawned(Bullet b) {
        super.despawned(b);
        if(consDespawned != null) consDespawned.get(b);
    }
}
