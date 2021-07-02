package rusting.entities.bullet;

import arc.math.Angles;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.gen.*;
import rusting.graphics.graphicEffects;

import java.util.Arrays;

public class InstantBounceBulletType extends BounceBulletType implements instantBullet{

    public float length = 100;
    public float trailFadeIn = 5, trailFadeOut = 25;

    public InstantBounceBulletType(int speed, int damage, String sprite) {
        super(speed, damage, sprite);
        this.hitSize = 0;
        this.lifetime = 3600;
        this.speed = 0.1f;
        this.despawnEffect = Fx.none;
    }

    @Override
    public float range() {
        return length;
    }

    @Override
    public void update(Bullet b) {

    }

    @Override
    public void draw(Bullet b){

    }

    @Override
    public void init(Bullet b){
        //no trails
        if(killShooter && b.owner() instanceof Healthc){
            ((Healthc) b.owner()).kill();
        }

        if(instantDisappear){
            b.time = lifetime;
        }

        b.fdata = length;

        hitUnit(b, b.x, b.y, bounceCap, new float[][]{{b.x}, {b.y}});

    }

    public void hitUnit(Bullet b, float ix, float iy, float durability, float[][] points){

        final float[] cords = {ix, iy};

        float x = cords[0];
        float y = cords[1];
        if(points == null) {
            points = new float[][]{{x}, {y}};
        }
        //obsolite for now, untill I make it ricoshae onto nearby blocks including allied ones
        //Posc targ = Geometryr.intersectLine(b.team, b.x, b.y, b.rotation(), b.fdata, homingPower, collidesAir, collidesGround, collidesTiles, collidesTeam, p -> !b.collided.contains(p.id()));
        Posc targ = null;
        if(b != null) targ = Damage.linecast(b, x, y, b.rotation(), b.fdata);
        if((durability > 0 || durability == -1) && targ != null) {

            b.fdata -= b.dst(targ);

            if(targ instanceof Unit) {
                b.vel.setAngle((b.rotation() + b.rotation() - b.angleTo(targ)) * 2);
                b.vel.rotate(180);
                Unit target = (Unit) targ;
                x = target.x + Angles.trnsx(b.rotation(), target.hitSize + 1);
                y = target.y + Angles.trnsy(b.rotation(), target.hitSize + 1);
                b.fdata *= bounciness;

            }
            else if(targ instanceof Building){


                float angle = b.angleTo(targ) - 180;

                b.x = targ.x() + Angles.trnsx(angle, 1);
                b.y = targ.y() + Angles.trnsy(angle, 1);;

                hitTile(b, (Building) targ, ((Building) targ).health, false);
                b.fdata *= bounciness;
                x = b.x;
                y = b.y;
            }
            if(targ instanceof Healthc){
                ((Healthc) targ).damage(damage * (targ instanceof Building ? 1/Vars.state.rules.blockHealthMultiplier : 1) * (b.owner instanceof Unit ? Vars.state.rules.unitDamageMultiplier : Vars.state.rules.blockDamageMultiplier));
            }
            if(targ instanceof Statusc){
                ((Statusc) targ).apply(status, statusDuration);
            }
            if(targ instanceof Velc){
                ((Velc) targ).vel().add(Tmp.v1.trns(b.angleTo(targ), knockback));
            }
            hitEffect.at(x, y);
            Damage.damage(x, y, splashDamageRadius, splashDamage);

            //finds the first array in the array in the array, then adds b.x to it. repeat with b.y. Requires getting an array which is reused twice. preferably clean up code soon.
            points[0] = Arrays.copyOf(points[0], points[0].length + 1);
            points[0][points[0].length - 1] = x;

            points[1] = Arrays.copyOf(points[1], points[1].length + 1);
            points[1][points[1].length - 1] = y;
            b.x = x;
            b.y = y;

            //prevents function being called more than once per second per bullet
            final float[][][] finalPoint = {points};
            if(lifetime - b.time > 5) Time.run(5, () -> {
                hitUnit(b, b.x, b.y, Math.max(durability - 1, -1), finalPoint[0]);
            });
            else{
                despawnEffect.at(x, y);
                graphicEffects.trailEffect(trailColor, x, y, 2.5f, 2, 1, trailFadeIn, trailFadeOut, points);
                b.remove();
            }
        }
        else {

            float ox = b.x + Angles.trnsx(b.rotation(), b.fdata);
            float oy = b.y + Angles.trnsy(b.rotation(), b.fdata);

            points[0] = Arrays.copyOf(points[0], points[0].length + 1);
            points[0][points[0].length - 1] = ox;

            points[1] = Arrays.copyOf(points[1], points[1].length + 1);
            points[1][points[1].length - 1] = oy;

            despawnEffect.at(ox, oy);
            graphicEffects.trailEffect(trailColor, ox, oy, 2.5f, 2, 1, trailFadeIn, trailFadeOut, points);
            b.remove();
        }
    }
}
