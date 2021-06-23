package rusting.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.*;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.graphics.Trail;
import rusting.content.Palr;

public class BoomerangBulletType extends BounceBulletType {

    //preferably a copy of this boomerang but only with rotateRight inverted
    public BulletType other = null;
    public boolean rotateRight = true;
    public float revolutions = 10;
    public float rotateTotalAngle = revolutions * 360;
    public float rotateMag = 1f, rotScaling = 1, rotScaleMin = 0.1f, rotScaleMax = 1;

    public BoomerangBulletType(int speed, int damage, String sprite) {
        super(speed, damage, sprite);
        this.trailLength = 10;
        this.trailWidth = width/3;
        bounceCap = 2;
        bounciness = 1;
        shrinkX = 0;
        trailColor = Palr.pulseBullet;
    }

    @Override
    public void draw(Bullet b) {

        float height = this.height * ((1f - shrinkY) + shrinkY * b.fout());
        float width = this.width * ((1f - shrinkX) + shrinkX * b.fout());
        float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f);
        float rotation = b.rotation() + offset + rotateTotalAngle * b.fin() % 360 * (rotateRight ? -1 : 1);

        Color mix = Tmp.c1.set(mixColorFrom).lerp(mixColorTo, b.fin());

        Draw.mixcol(mix, mix.a);

        Draw.color(backColor);
        Draw.rect(backRegion, b.x, b.y, width, height, rotation);
        Draw.color(frontColor);
        Draw.rect(frontRegion, b.x, b.y, width, height, rotation);

        Draw.reset();

        ((Seq<Trail>)b.data).each(t -> t.draw(trailColor, trailWidth * b.fout()));

    }

    public void update(Bullet b) {
        super.update(b);

        if (rotateMag > 0) {
            b.vel.rotate(rotateMag * Mathf.clamp(b.fin() * rotScaling, rotScaleMin, rotScaleMax) * (rotateRight ? -1 : 1) * Time.delta);
        }

    }

    @Override
    public Bullet create(@Nullable Entityc owner, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data) {
        if (other != null && Mathf.chance(0.5) && other instanceof BoomerangBulletType) {
            return ((BoomerangBulletType) other).createBoomerang(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, data);
        } else return super.create(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, data);
    }

    //no chance to spawn other boomerang
    public Bullet createBoomerang(@Nullable Entityc owner, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data) {
        return super.create(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, data);
    }
}
