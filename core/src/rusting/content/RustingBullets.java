package rusting.content;

import arc.func.Cons;
import arc.graphics.Color;
import arc.math.Angles;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.Fires;
import mindustry.entities.bullet.*;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.world.blocks.defense.turrets.Turret.TurretBuild;
import rusting.entities.bullet.*;
import rusting.math.Mathr;

import static rusting.content.RustingStatusEffects.*;

public class RustingBullets implements ContentList{
    public static BulletType
        //basic bullets
        fossilShard, cloudyShard, craeShard, raehShard, mhemShard, fraeShard, paveShard, darkShard, spawnerGlass, spawnerGlassFrag, spawnerBulat, spawnerBulatFrag,
        //artillery
        mhemQuadStorm,
        //liquid
        melomaeShot,
        //missile/weaving bullets
        craeWeaver, paveWeaver,
        //lightning bullets
        craeBolt,
        //laser bolt bullets
        paveBolt,
        //essentualy small nukes
        craeBalistorm,
        //boomerangs
        craeLightRoundaboutRight, craeLightRoundaboutLeft, saltyLightRoundaboutRight, saltyLightRoundaboutLeft, denseLightRoundaboutLeft, denseLightRoundaboutRight,
        //glaivs
        craeLightGlaive, craeLightGlaiveRight, craeLightGlaiveLeft, saltyLightGlaive,
        //instant bullets
        horizonInstalt, nummingInstalt, timelessInstalt,
        //bullet spawning bullets
        nummingVortex, cloudyVortex, boltingVortex, flamstrikenVortex
        ;

    @Override
    public void load(){

        horizonInstalt = new InstantBounceBulletType(1, 22, "bullet"){{
            width = 7;
            height = 8;
            lifetime = 54;
            length = 132;
            buildingDamageMultiplier = 0.45f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.hitFuse;
            bounceEffect = Fx.blockExplosionSmoke;
            status = shieldShatter;
            statusDuration = 450;
            knockback = 0.1f;
            drag = 0.005f;
            bounciness = 1.6;
            bounceCap = 3;
        }};

        nummingInstalt = new InstantBounceBulletType(1,  7.5f, "bullet"){{
            width = 7;
            height = 8;
            lifetime = 54;
            length = 152;
            buildingDamageMultiplier = 0.15f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.hitFuse;
            bounceEffect = Fx.blockExplosionSmoke;
            trailColor = Palr.lightstriken;
            status = StatusEffects.shocked;
            statusDuration = 250;
            knockback = 0.3f;
            drag = 0.005f;
            bounciness = 1.2;
            bounceCap = 4;
        }};

        timelessInstalt = new InstantBounceBulletType(1, 15, "bullet"){{
            width = 7;
            height = 8;
            lifetime = 192;
            length = 166;
            buildingDamageMultiplier = 0.35f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.hitFuse;
            bounceEffect = Fx.blockExplosionSmoke;
            trailColor = Palr.pulseBullet;
            status = StatusEffects.freezing;
            knockback = 0.3f;
            drag = 0.005f;
            bounciness = 1.2;
            bounceCap = 2;
        }};

        fossilShard = new BounceBulletType(4, 9, "bullet"){{
            width = 7;
            height = 8;
            lifetime = 54;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            bounceEffect = Fx.blockExplosionSmoke;
            status = amberstriken;
            statusDuration = 45;
            knockback = 1;
            drag = 0.005f;
            bounciness = 0.6;
        }};

        cloudyShard = new BasicBulletType(1.5f, 3, "bullet"){{
            splashDamage = 6;
            splashDamageRadius = 22;
            width = 9;
            height = 9;
            lifetime = 54;
            hitEffect = Fx.plasticburn;
            despawnEffect = Fx.plasticburn;
            status = StatusEffects.corroded;
            frontColor = Color.white;
            backColor = Pal.gray;
            knockback = 1.85f;
            homingPower = 0.005f;
        }};

        craeShard = new BounceBulletType(4, 5, "bullet"){{
            width = 7;
            height = 8;
            lifetime = 15;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            bounceEffect = Fx.hitLancer;
            status = macrosis;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseBullet;
            trailColor = frontColor;
            trailEffect = Fx.lightningShoot;
            knockback = 1;
            drag = 0.005f;
            bounciness = 0.6;
        }};

        raehShard = new BounceBulletType(6, 12, "bullet"){{
            width = 9;
            height = 10;
            lifetime = 45;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            bounceEffect = Fx.blockExplosionSmoke;
            trailColor = Pal.darkPyraFlame;
            incendChance = 1;
            incendAmount = 3;
            status = StatusEffects.burning;
            statusDuration = 640;
            trailLength = 10;
            homingPower = 0.25F;
            knockback = 3;
            drag = 0.005f;
            bounciness = 0.85;
        }};



        mhemShard = new BounceBulletType(6, 25, "bullet"){{
            consUpdate = new Cons<Bullet>() {
                @Override
                public void get(Bullet bullet) {
                    Fxr.singingFlame.at(bullet.x, bullet.y, bullet.rotation());
                    if(bullet.fdata() != 1 && bullet.collided.size < 2){
                        if(bullet.owner instanceof TurretBuild) {
                            bullet.vel.setAngle(Angles.moveToward(bullet.rotation(), bullet.angleTo(((TurretBuild) bullet.owner).targetPos.x, ((TurretBuild) bullet.owner).targetPos.y), Time.delta * 261f * bullet.fin()));
                            //stop homing in after reaching cursor
                            if(bullet.within(((TurretBuild) bullet.owner).targetPos.x, ((TurretBuild) bullet.owner).targetPos.y, bullet.hitSize)){
                                bullet.fdata = 1;
                            }
                        }
                    }
                    //essentualy goes to owner aim pos if owner has instance of the Turret Build.

                }
            };
            despawnEffect = Fx.fireSmoke;
            hitEffect = Fx.fire;
            bounceEffect = Fxr.shootMhemFlame;
            incendChance = 1;
            incendAmount = 10;
            status = StatusEffects.burning;
            statusDuration = 3600;
            maxRange = 156;
            width = 6;
            height = 8;
            hitSize = 12;
            lifetime = 35;
            hitEffect = Fx.hitFuse;
            trailLength = 0;
            drag = 0.015f;
            bounciness = 0.95;
            bounceCap = 2;
        }};

        fraeShard = new ConsBulletType(10, 25, "bullet"){{
            consUpdate = new Cons<Bullet>() {
                @Override
                public void get(Bullet bullet) {
                    darkShard.create(bullet.owner, bullet.team, bullet.x, bullet.y, bullet.rotation() - 90, Mathr.helix(7, 1, bullet.fin()));
                    darkShard.create(bullet.owner, bullet.team, bullet.x, bullet.y, bullet.rotation() + 90, Mathr.helix(7, 1, bullet.fin()));
                }
            };
            despawnEffect = Fx.plasticburn;
            hitEffect = Fx.plasticExplosion;
            status = StatusEffects.corroded;
            statusDuration = 7200;
            lightOpacity = 0;
            width = 10;
            height = 12;
            pierce = true;
            pierceBuilding = true;
            lifetime = 20;
            hitEffect = Fx.hitFuse;
        }};

        paveShard = new ConsBulletType(12, 115, "bullet"){{
            consUpdate = new Cons<Bullet>() {
                @Override
                public void get(Bullet bullet){

                    Fxr.singingFlame.at(bullet.x, bullet.y, bullet.rotation() + Mathr.helix(7, 45, bullet.fin()));
                    Fxr.singingFlame.at(bullet.x, bullet.y, bullet.rotation() - Mathr.helix(7, 45, bullet.fin()));

                    if(bullet.collided.size >= 1) {
                        Fires.create(Vars.world.tileWorld(bullet.x, bullet.y));
                        Fxr.paveFlame.at(bullet.x, bullet.y, bullet.rotation());
                    }
                }
            };
            despawnEffect = Fx.fireSmoke;
            hitEffect = Fxr.shootMhemFlame;
            incendChance = 1;
            incendAmount = 10;
            status = StatusEffects.melting;
            statusDuration = 3600;
            width = 8;
            height = 10;
            hitSize = 12;
            pierce = true;
            pierceBuilding = true;
            lifetime = 25;
            hitEffect = Fx.hitFuse;
        }};

        darkShard = new BounceBulletType(4, 2.5f, "bullet"){{
            consUpdate = new Cons<Bullet>() {
                @Override
                public void get(Bullet bullet) {
                    if(bullet.fin() % 0.04 < 0.01) Fxr.blackened.at(bullet.x, bullet.y, bullet.rotation());
                }
            };
            despawnEffect = Fx.fireSmoke;
            hitEffect = Fx.casing3Double;
            bounceEffect = Fx.none;
            shootEffect = Fxr.blackened;
            frontColor = Color.darkGray;
            backColor = Palr.voidBullet;
            status = RustingStatusEffects.umbrafliction;
            statusDuration = 160;
            lightOpacity = 0;
            width = 10;
            height = 12;
            lifetime = 35;
            hitEffect = Fx.hitFuse;
            trailLength = 0;
            homingPower = 0.125f;
            drag = 0.015f;
            bounciness = 0.95;
        }};

        spawnerGlassFrag = new BasicBulletType(2.5f, 5, "bullet"){{
            width = 5f;
            height = 12f;
            shrinkY = 1f;
            lifetime = 55f;
            homingPower = 0.25f;
            backColor = Color.sky;
            frontColor = Color.white;
            despawnEffect = Fx.plasticburn;
            hitEffect = Fx.plasticburn;
            shootEffect = Fx.shootBig;
            pierce = true;
            pierceCap = 2;
            drag = 0.015f;
        }};

        spawnerGlass = new BasicBulletType(1.25f, 13, "bullet"){{
            width = 10f;
            height = 22f;
            shrinkY = 0.8f;
            lifetime = 175;
            backColor = Pal.gray;
            frontColor = Color.white;
            despawnEffect = Fx.blastExplosion;
            despawnEffect = Fx.plasticExplosionFlak;
            hitSound = Sounds.explosion;
            fragBullet = spawnerGlassFrag;
            fragBullets = 11;
            fragVelocityMin = 0.85f;
            fragLifeMin = 0.75f;
            fragLifeMax = 1.15f;
            scaleVelocity = true;
        }};

        spawnerBulatFrag = new BasicBulletType(2.5f, 3, "bullet"){{
            width = 5f;
            height = 12f;
            shrinkY = 1f;
            lifetime = 125f;
            knockback = -1;
            homingPower = 0.25f;
            splashDamage = 5;
            splashDamageRadius = 15;
            frontColor = Palr.lightstriken;
            backColor = Palr.pulseBullet;
            backColor = Color.sky;
            frontColor = Pal.plastaniumBack;
            despawnEffect = Fx.plasticburn;
            hitEffect = Fx.plasticburn;
            pierce = true;
            pierceCap = 2;
            status = StatusEffects.corroded;
            statusDuration = 5;
            drag = 0.015f;
        }};

        spawnerBulat = new BasicBulletType(1.25f, 25, "bullet"){{
            width = 10f;
            height = 22f;
            shrinkY = 0.8f;
            lifetime = 175;
            knockback = -0.75f;
            frontColor = Pal.plastaniumBack;
            despawnEffect = Fx.plasticExplosionFlak;
            hitEffect = Fxr.spawnerBulatExplosion;
            shootEffect = Fx.shootBig;
            hitSound = Sounds.explosion;
            fragBullet = spawnerBulatFrag;
            fragBullets = 9;
            fragVelocityMin = 0.85f;
            fragLifeMin = 0.75f;
            fragLifeMax = 1.15f;
            scaleVelocity = true;
            status = StatusEffects.corroded;
            statusDuration = 360;
        }};

        mhemQuadStorm = new ConsBulletType(2.85f, 3.5f, "shell"){{

            scaleVelocity = true;
            hitShake = 1f;
            frontColor = Palr.lightstriken;
            backColor = Pal.bulletYellowBack;
            hitEffect = Fx.flakExplosion;
            despawnEffect = Fxr.instaltSummoner;
            knockback = 0.8f;
            lifetime = 105f;
            width = height = 11f;
            splashDamageRadius = 35f * 0.75f;
            splashDamage = 33f;
            shootEffect = Fx.shootBig;
            trailEffect = Fx.artilleryTrail;
            trailChance = 0.15f;
            fragBullet = Bullets.fragGlassFrag;
            fragBullets = 13;

            shrinkX = 0.15f;
            shrinkY = 0.63f;

            drag = 0.13f;
        }};

        melomaeShot = new LiquidBulletType(RustingLiquids.melomae){{
            damage = 3;
            homingPower = 0.075f;
            knockback = 0.7f;
            drag = 0.01f;
        }};

        craeWeaver = new BounceBulletType(3, 14, "bullet"){{
            width = 15;
            height = 18;
            lifetime = 45;
            shrinkX = 1;
            shootEffect = Fx.none;
            hitEffect = Fx.hitLancer;
            despawnEffect = Fx.plasticburn;
            bounceEffect = Fx.hitFuse;
            status = RustingStatusEffects.macotagus;
            statusDuration = 1440;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseBullet;
            trailColor = frontColor;
            trailEffect = Fxr.craeWeaversResidue;
            trailChance = 0.15f;
            trailLength = 8;
            trailWidth = 5;
            weaveMag = 2;
            weaveScale = 5;
            homingPower = 0.125f;
            knockback = -0.15f;
            bounciness = 0.8;
        }};

        //duplicated bullet, only to be used for duoplys
        paveWeaver = new BounceBulletType(5, 7, "bullet"){{
            width = 15;
            height = 18;
            lifetime = 35;
            healPercent = 1f;
            shrinkX = 1;
            trailLength = 8;
            collidesTeam = true;
            shootEffect = Fx.heal;
            hitEffect = Fx.plasticburn;
            despawnEffect = Fx.plasticburn;
            backColor = Pal.heal;
            frontColor = Color.white;
            trailColor = frontColor;
            trailEffect = Fx.shootHeal;
            trailChance = 0.15f;
            trailLength = 8;
            trailWidth = 5;
            weaveMag = 2;
            weaveScale = 5;
            homingPower = 0.325f;
            knockback = 0.25f;
            bounciness = 0.8;
        }};

        craeBolt = new LightningBulletType(){{
            damage = 15;
            lightningDamage = 35f;
            lightningLength = 12;
            lightningColor = Palr.pulseChargeStart;
            status = RustingStatusEffects.macrosis;
        }};

        paveBolt = new LaserBoltBulletType(5.2f, 8){{
            recoil = 1.25f;
            lifetime = 15f;
            healPercent = 2f;
            collidesTeam = true;
            backColor = Pal.heal;
            frontColor = Color.white;
        }};

        craeBalistorm = new ConsBulletType(3, 14, "bullet"){{

            consDespawned = new Cons<Bullet>() {
                @Override
                public void get(Bullet b) {
                    Fxr.pulseSmoke.at(b.x, b.y, b.rotation(), new float[]{
                            80,
                            12,
                            0.85f
                    });
                    Fxr.pulseExplosion.at(b.x, b.y, b.rotation(), new Float[]{
                            80f,
                            12f
                    });
                }
            };

            consHit = consDespawned;

            splashDamage = 125;
            splashDamageRadius = 80;

            width = 25;
            height = 25;
            lifetime = 460;
            shrinkX = -0.5f;
            hitEffect = Fx.none;
            despawnEffect = Fx.none;
            status = RustingStatusEffects.macotagus;
            statusDuration = 1440;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseBullet;
            trailColor = frontColor;
            trailChance = 1f;
            weaveMag = 2;
            weaveScale = 5;
            homingPower = 0.125f;
            collidesTiles = false;
            collides = false;
            collidesAir = false;
            scaleVelocity = true;
            hitShake = 1f;
            hitSound = Sounds.explosion;
            knockback = -0.15f;
        }};

        denseLightRoundaboutRight =  new BoomerangBulletType(1, 8, "endless-rusting-boomerang"){{

            other = denseLightRoundaboutLeft;
            width = 16;
            height = 19;
            lifetime = 120;
            homingPower = 0.05f;
            homingRange = 45f;
            trailWidth = 0;
            trailLength = 0;
            rotateMag = 1;
            rotateVisualMag = 0.6f;
            rotScaleMin = 0f;
            rotScaleMax = 0.7f;
            rotateRight = true;
            reverseBoomerangRotScale = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.smeltsmoke;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseChargeEnd;
            trailEffect = Fx.plasticburn;
            status = shieldShatter;
            trailChance = 0.35f;
            drag = -0.001f;
        }};

        saltyLightRoundaboutRight = new BoomerangBulletType(2, 7, "endless-rusting-boomerang"){{

            other = saltyLightRoundaboutLeft;

            reloadMultiplier = 0.85f;
            width = 14;
            height = 13;
            lifetime = 120;
            homingPower = 0.05f;
            homingRange = 45f;
            trailWidth = 0;
            trailLength = 0;
            rotateMag = 1;
            rotateVisualMag = 0.6f;
            rotScaleMin = 0.1f;
            rotScaleMax = 1f;
            rotateRight = true;
            reverseBoomerangRotScale = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.smeltsmoke;
            frontColor = Color.white;
            backColor = Palr.dustriken;
            trailEffect = Fxr.salty;
            status = hailsalilty;
            trailChance = 0.15f;
            drag = 0.008f;
            pierceCap = 1;
            fragBullet = spawnerGlassFrag;
            fragBullets = 2;
        }};

        saltyLightRoundaboutLeft = new BoomerangBulletType(2, 7, "endless-rusting-boomerang"){{

            other = saltyLightRoundaboutRight;

            reloadMultiplier = 0.85f;
            width = 14;
            height = 13;
            lifetime = 120;
            homingPower = 0.05f;
            homingRange = 45f;
            trailWidth = 0;
            trailLength = 0;
            rotateMag = 1;
            rotateVisualMag = 0.6f;
            rotScaleMin = 0.1f;
            rotScaleMax = 1f;
            rotateRight = false;
            reverseBoomerangRotScale = false;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.smeltsmoke;
            frontColor = Color.white;
            backColor = Palr.dustriken;
            trailEffect = Fxr.salty;
            status = hailsalilty;
            trailChance = 0.15f;
            drag = 0.008f;
            pierceCap = 1;
            fragBullet = spawnerGlassFrag;
            fragBullets = 2;
        }};

        denseLightRoundaboutLeft = new BoomerangBulletType(1, 11, "endless-rusting-boomerang"){{

            other = denseLightRoundaboutRight;

            width = 16;
            height = 19;
            lifetime = 120;
            homingPower = 0.05f;
            homingRange = 45f;
            trailWidth = 0;
            trailLength = 0;
            rotateMag = 1;
            rotateVisualMag = 0.6f;
            rotScaleMin = 0f;
            rotScaleMax = 0.7f;
            rotateRight = false;
            reverseBoomerangRotScale = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.smeltsmoke;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseChargeEnd;
            trailEffect = Fx.plasticburn;
            status = shieldShatter;
            trailChance = 0.35f;
            drag = -0.001f;
        }};

        craeLightRoundaboutRight = new BoomerangBulletType(2, 11, "endless-rusting-boomerang"){{

            other = craeLightRoundaboutLeft;

            reloadMultiplier = 1.35f;

            width = 12;
            height = 14;
            lifetime = 150;
            homingPower = 0.05f;
            homingRange = 45f;
            rotateMag = 3;
            rotScaleMin = 0f;
            rotScaleMax = 0.7f;
            rotateRight = true;
            reverseBoomerangRotScale = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseChargeStart;
            trailEffect = Fxr.whoosh;
            status = shieldShatter;
            trailChance = 0.35f;
            drag = -0.001f;
        }};

        craeLightRoundaboutLeft = new BoomerangBulletType(2, 8, "endless-rusting-boomerang"){{

            other = craeLightRoundaboutRight;

            reloadMultiplier = 1.35f;

            width = 12;
            height = 14;
            lifetime = 150;
            homingPower = 0.05f;
            homingRange = 45f;
            rotateMag = 3;
            rotScaleMin = 0f;
            rotScaleMax = 0.7f;
            rotateRight = false;
            reverseBoomerangRotScale = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseChargeEnd;
            trailEffect = Fxr.whoosh;
            status = shieldShatter;
            trailChance = 0.35f;
            drag = -0.001f;
        }};

        craeLightGlaive = new BoomerangBulletType(1, 25, "endless-rusting-glave"){{

            homingPower = 0.025f;

            consUpdate = new Cons<Bullet>() {
                @Override
                public void get(Bullet bullet) {
                    if(bullet.owner instanceof TurretBuild) bullet.vel.setAngle(Angles.moveToward(bullet.rotation(), bullet.angleTo(((TurretBuild) bullet.owner).targetPos.x, ((TurretBuild) bullet.owner).targetPos.y), homingPower * Time.delta * 100f));
                }
            };

            consHit = new Cons<Bullet>() {
                @Override
                public void get(Bullet bullet) {
                    for(int i = 0; i <  5; i++) {
                        ((BoomerangBulletType) craeLightRoundaboutLeft).createBoomerang(bullet.owner, bullet.team, bullet.x, bullet.y, i * 72 + bullet.rotation(), craeLightRoundaboutLeft.damage / 2, 0.85f, 1, 0);
                        ((BoomerangBulletType) craeLightRoundaboutRight).createBoomerang(bullet.owner, bullet.team, bullet.x, bullet.y, i * 72 + bullet.rotation(), craeLightRoundaboutRight.damage / 2, 0.45f, 1, 0);
                    }
                }
            };

            consDespawned = consHit;

            shrinkX = 1.5f;
            shrinkY = 1.5f;
            width = 20;
            height = 20;
            lifetime = 150;
            pierceCap = 1;
            rotateMag = 3;
            rotScaleMin = 0f;
            rotScaleMax = 0f;
            spin = 1;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseChargeEnd;
            status = shieldShatter;
            drag = -0.001f;

        }};

        craeLightGlaiveRight = new BoomerangBulletType(2, 15, "endless-rusting-glave"){{
            other = craeLightGlaiveLeft;

            width = 9;
            height = 9;
            lifetime = 165;
            homingPower = 0.025f;
            pierceCap = -1;
            shrinkX = 0;
            shrinkY = 0;
            bounceCap = 0;
            rotateMag = 5;
            rotScaleMin = 0.2f;
            rotScaleMax = 0.2f;
            rotateRight = true;
            stayInRange = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseBullet;
            status = shieldShatter;
            drag = -0.001f;
        }};

        craeLightGlaiveLeft = new BoomerangBulletType(2, 15, "endless-rusting-glave"){{
            other = craeLightGlaiveRight;

            width = 9;
            height = 9;
            lifetime = 165;
            homingPower = 0.025f;
            pierceCap = -1;
            shrinkX = 0;
            shrinkY = 0;
            bounceCap = 0;
            rotateMag = 5;
            rotScaleMin = 0.2f;
            rotScaleMax = 0.2f;
            rotateRight = false;
            stayInRange = true;
            hitEffect = Fx.hitFuse;
            despawnEffect = Fx.plasticburn;
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseBullet;
            status = shieldShatter;
            statusDuration = 60;
            drag = -0.001f;
        }};

        saltyLightGlaive = new BoomerangBulletType(2.5f, 45, "endless-rusting-glave"){{
            width = 20;
            height = 20;
            lifetime = 72;
            pierceCap = 4;
            shrinkX = 0;
            shrinkY = 0;
            bounceCap = 0;
            rotateMag = 5;
            rotScaleMin = 0.2f;
            rotScaleMax = 0.2f;
            rotScaleMin = 0f;
            rotScaleMax = 0f;
            hitEffect = Fxr.instaltSummonerExplosion;
            despawnEffect = Fxr.instaltSummonerExplosion;
            frontColor = Color.white;
            backColor = Palr.lightstriken;
            status = hailsalilty;
            drag = -0.001f;
            fragBullet = saltyLightRoundaboutRight;
            fragBullets = 3;
            trailColor = Color.white;
            trailEffect = Fxr.salty;
        }};

        nummingVortex = new BulletSpawnBulletType(2f, 250, "none"){{

            bullets = Seq.with(
                new BulletSpawner(){{
                    bullet = nummingInstalt;
                    reloadTime = 5.5f;
                    manualAiming = true;
                    shootSound = Sounds.artillery;
                    intervalIn = 25;
                    intervalOut = 35;
                }},
                new BulletSpawner(){{
                    bullet = darkShard;
                    reloadTime = 7.5f;
                    manualAiming = true;
                    shootSound = Sounds.artillery;
                    intervalIn = 65;
                    intervalOut = 10;
                }}
            );
            hitSound = Sounds.release;
            finalFragBullet = fraeShard;
            finalFragBullets = 1;
            fragCone = 0;
            fragLifeMin = 1;
            fragLifeMax = 1;
            fragVelocityMin = 1;
            fragVelocityMax = 1;
            frontColor = Palr.lightstriken;
            backColor = Pal.bulletYellowBack;
            despawnEffect = Fxr.instaltSummonerExplosion;
            lifetime = 128f;
            width = height = 11f;
            splashDamageRadius = 35f * 0.75f;
            splashDamage = 33f;
            shootEffect = Fx.shootBig;

            shrinkX = 0.15f;
            shrinkY = 0.63f;

            drag = 0.085f;

            scaleDrawIn = 2.5f;
            scaleDrawOut = 7f;
        }};

        cloudyVortex = new BulletSpawnBulletType(0.35f, 250, "none"){{
            bullets = Seq.with(
                new BulletSpawner(){{
                    bullet = cloudyShard;
                    reloadTime = 9.5f;
                    manualAiming = true;
                    inaccuracy = 1;
                    intervalIn = 95;
                    intervalOut = 65;
                }},
                new BulletSpawner(){{
                    bullet = cloudyShard;
                    reloadTime = 3.5f;
                    manualAiming = false;
                    idleInaccuracy = 360;
                    inaccuracy = 5;
                    intervalIn = 95;
                    intervalOut = 65;
                }}
            );
            frontColor = Palr.lightstriken;
            backColor = Palr.dustriken;
            despawnEffect = Fx.sparkShoot;
            lifetime = 405f;
            width = height = 11f;
            splashDamageRadius = 35f * 0.75f;
            splashDamage = 33f;
            shootEffect = Fx.shootBig;

            shrinkX = 0.15f;
            shrinkY = 0.63f;

            scaleDrawIn = 4;
            scaleDrawOut = 9;
            drawSize = 6;

            homingPower = 0.02f;
            drag = 0.005f;

        }};

        //anyone know where this one is from? ;)
        boltingVortex = new BulletSpawnBulletType(0.099f, 250, "none"){{
            bullets = Seq.with(
                new BulletSpawner(){{
                    bullet = craeBolt;
                    reloadTime = 2.5f;
                    shootSound = Sounds.spark;
                    intervalIn = 125;
                    intervalOut = 125;
                }}
            );
            frontColor = Palr.pulseChargeStart;
            backColor = Palr.pulseBullet;
            despawnEffect = Fx.sparkShoot;
            lifetime = 405f;
            width = height = 11f;
            splashDamage = 33f;
            homingPower = 0.02f;
            homingRange = 50;
            splashDamageRadius = 35f * 0.75f;
            shootEffect = Fx.shootBig;

            shrinkX = 0.15f;
            shrinkY = 0.63f;

            scaleDrawIn = 4;
            scaleDrawOut = 9;
            drawSize = 6;
            drag = -0.01f;
        }};

        flamstrikenVortex = new BulletSpawnBulletType(0.15f, 250, "none"){{
            bullets = Seq.with(
                new BulletSpawner(){{
                    bullet = mhemShard;
                    manualAiming = false;
                    reloadTime = 10.5f;
                    shootSound = Sounds.flame2;
                    intervalIn = 65;
                    intervalOut = 65;
                    inaccuracy = 360;
                }},
                new BulletSpawner(){{
                    bullet = mhemShard;
                    manualAiming = false;
                    reloadTime = 16.5f;
                    shootSound = Sounds.flame2;
                    intervalIn = 65;
                    intervalOut = 65;
                    inaccuracy = 360;
                }}
            );
            frontColor = Pal.lightPyraFlame;
            backColor = Palr.darkPyraBloom;
            despawnEffect = Fx.sparkShoot;
            lifetime = 405f;
            width = height = 11f;
            splashDamageRadius = 35f * 0.75f;
            splashDamage = 33f;
            shootEffect = Fx.shootBig;

            shrinkX = 0.15f;
            shrinkY = 0.63f;

            scaleDrawIn = 4;
            scaleDrawOut = 9;
            drawSize = 6;

        }};

    }
}
