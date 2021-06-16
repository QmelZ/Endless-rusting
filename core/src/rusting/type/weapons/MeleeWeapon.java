package rusting.type.weapons;

import mindustry.type.Weapon;

public class MeleeWeapon extends Weapon {
    /*
    //damage it does
    public float damage;
    //knockback the weapon has
    public float knockback;
    //angle in which the weapon arcs in
    public float arc;
    public float recoil;
    public float weaponRecoil;

    protected void shoot(Unit unit, WeaponMount mount, float shootX, float shootY, float aimX, float aimY, float mountX, float mountY, float rotation, int side){
        float baseX = unit.x, baseY = unit.y;
        boolean delay = firstShotDelay + shotDelay > 0f;

        (delay ? chargeSound : continuous ? Sounds.none : shootSound).at(shootX, shootY, Mathf.random(soundPitchMin, soundPitchMax));

        BulletType ammo = bullet;
        float lifeScl = ammo.scaleVelocity ? Mathf.clamp(Mathf.dst(shootX, shootY, aimX, aimY) / ammo.range()) : 1f;

        sequenceNum = 0;
        if(delay){
            Angles.shotgun(shots, spacing, rotation, f -> {
                Time.run(sequenceNum * shotDelay + firstShotDelay, () -> {
                    if(!unit.isAdded()) return;
                    mount.bullet = bullet(unit, shootX + unit.x - baseX, shootY + unit.y - baseY, f + Mathf.range(inaccuracy), lifeScl);
                });
                sequenceNum++;
            });
        }else{
            Angles.shotgun(shots, spacing, rotation, f -> mount.bullet = bullet(unit, shootX, shootY, f + Mathf.range(inaccuracy), lifeScl));
        }

        boolean parentize = ammo.keepVelocity;

        if(delay){
            Time.run(firstShotDelay, () -> {
                if(!unit.isAdded()) return;

                unit.vel.add(Tmp.v1.trns(rotation + 180f, ammo.recoil));
                Effect.shake(shake, shake, shootX, shootY);
                mount.heat = 1f;
                if(!continuous){
                    shootSound.at(shootX, shootY, Mathf.random(soundPitchMin, soundPitchMax));
                }
            });
        }else{
            unit.vel.add(Tmp.v1.trns(rotation + 180f, ammo.recoil));
            Effect.shake(shake, shake, shootX, shootY);
            mount.heat = 1f;
        }

        ejectEffect.at(mountX, mountY, rotation * side);
        ammo.shootEffect.at(shootX, shootY, rotation, parentize ? unit : null);
        ammo.smokeEffect.at(shootX, shootY, rotation, parentize ? unit : null);
        unit.apply(shootStatus, shootStatusDuration);
    }


     */
}
