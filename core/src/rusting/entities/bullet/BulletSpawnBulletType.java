package rusting.entities.bullet;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.content.Bullets;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;
import mindustry.world.blocks.defense.turrets.Turret.TurretBuild;
import rusting.content.Palr;

public class BulletSpawnBulletType extends ConsBulletType{

    //fired on bullet hit/despawn. Correctly rotated.
    public BulletType finalFragBullet = null;
    public float finalFragBullets = 1;

    public float scaleDrawIn = 4, scaleDrawOut = 4, drawSize = 4;

    public static class BulletSpawner{
        public BulletType bullet = Bullets.standardCopper;
        public float reloadTime = 1;
        public float intervalIn = 0, intervalOut = 0;
        //inaccuracy
        public float inaccuracy = 0;
        //random spray that happens when theres no rotation value set
        public float idleInaccuracy = 360;
        //allows owner to aim for the bullet.
        public boolean manualAiming = true;
        public Sound shootSound = Sounds.none;
    }

    public Seq<BulletSpawner> bullets = new Seq<BulletSpawner>();

    private BulletSpawner spawner;
    private float rotation;
    private Unit unitOwner;

    public BulletSpawnBulletType(float speed, float damage, String sprite) {
        super(speed, damage, sprite);
        this.keepVelocity = false;
        this.collides = false;
        this.frontColor = Palr.lightstriken;
        this.backColor = Palr.dustriken;
        this.trailChance = 0;
    }

    @Override
    public void init(Bullet b) {
        super.init(b);
        if(b != null){
            b.data = new float[bullets.size];
        }
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        for (int i = 0; i < bullets.size; i++) {
            spawner = bullets.get(i);
            //I don't get the logic behind this, interval in and interval out do the opposit of what I made them to do
            if(spawner.intervalIn >= b.fin() * lifetime || spawner.intervalOut >= b.fout() * lifetime) break;
            if(((float[]) b.data)[i] < spawner.reloadTime){
                ((float[]) b.data)[i] += Time.delta;
            }
            else {
                rotation = b.rotation() + Mathf.random(spawner.idleInaccuracy);
                if(spawner.manualAiming){
                    if(b.owner != null && (b.owner instanceof Unit || b.owner instanceof TurretBuild)){
                        if(b.owner instanceof Unit){
                            unitOwner = ((Unit) b.owner);
                            rotation = Mathf.angle(unitOwner.aimX - b.x,unitOwner.aimY - b.y);
                            if(spawner.inaccuracy != 0) rotation += Math.random() * spawner.inaccuracy;
                        }
                        else if(b.owner instanceof TurretBuild){
                            rotation = b.angleTo(((TurretBuild) b.owner).targetPos);
                            if(spawner.inaccuracy != 0) rotation += Math.random() * spawner.inaccuracy;
                        }
                    }
                }
                else{
                    Teamc targ = Units.closestEnemy(b.team, b.x, b.y, spawner.bullet.range(), u -> spawner.bullet.collidesGround && !u.isFlying() || spawner.bullet.collidesAir && u.isFlying());
                    if(targ instanceof Posc){
                        rotation = b.angleTo(targ);
                        if(spawner.inaccuracy != 0) rotation += Math.random() * spawner.inaccuracy;
                    }
                }
                spawner.bullet.create(b.owner, b.team, b.x, b.y, rotation + spawner.inaccuracy);
                if(spawner.shootSound != Sounds.none) spawner.shootSound.at(b.x, b.y);
                ((float[]) b.data)[i] = 0;
                b.fdata = rotation;
            }
        }
    }

    @Override
    public void hit(Bullet b, float x, float y) {
        super.hit(b, x, y);

        if(finalFragBullet != null){
            for(int i = 0; i < finalFragBullets; i++){
                float len = Mathf.random(1f, 7f);
                float a = b.fdata + Mathf.range(fragCone/2) + fragAngle;
                finalFragBullet.create(b, x + Angles.trnsx(a, len), y + Angles.trnsy(a, len), a, Mathf.random(fragVelocityMin, fragVelocityMax), Mathf.random(fragLifeMin, fragLifeMax));
            }
        }

    }

    @Override
    public void draw(Bullet b) {
        Draw.color(frontColor, backColor, b.fin());
        float initialScaling = Math.min(b.fout() * scaleDrawOut, Math.min(b.fin() * scaleDrawIn, 1));
        float scaling = b.fout() * drawSize;
        if(!Core.settings.getBool("bloom")) Draw.alpha(initialScaling * initialScaling);
        else Draw.alpha(initialScaling);
        Fill.circle(b.x, b.y, initialScaling * drawSize);
        float spacing = 2;
        for(float i = 0; i < scaling; i++){
            Lines.stroke((scaling - i) * spacing/5);
            Lines.circle(b.x, b.y, (scaling - i) * spacing + i + drawSize);
        }
    }
}
