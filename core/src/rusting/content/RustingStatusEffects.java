package rusting.content;

import arc.Events;
import arc.func.Cons;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.FloatSeq;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.entities.Units;
import mindustry.game.EventType.Trigger;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import rusting.type.statusEffect.*;

//first time I'm doing this
@SuppressWarnings("unchecked")

public class RustingStatusEffects implements ContentList {
    public static StatusEffect
            weather, amberstriken, umbrafliction, macrosis, macotagus, causticBurning, shieldShatter, corruptShield, fragmentaein;
    public static Cons
            corruptShieldCons;

    @Override
    public void load() {

        //sued by weather and to see if unit was alive during a weather effect
        weather = new StatusEffect("weather"){

        };

        amberstriken = new CrystalStatusEffect("amberstriken"){{
            speedMultiplier = 0.35F;
            transitionDamage = 10f;
            effect = Fx.plasticburn;
            init(() -> {
                affinity(StatusEffects.burning, ((unit, time, newTime, result) -> {
                    unit.damagePierce(transitionDamage);
                    unit.apply(umbrafliction, time * 3);
                    Fx.placeBlock.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(amberstriken, 0);
                }));
                affinity(umbrafliction, ((unit, time, newTime, result) -> {
                    unit.damagePierce(transitionDamage/3);
                    result.set(amberstriken, 0);
                }));
            });
            disarm = false;
        }};

        umbrafliction = new CrystalStatusEffect("umbrafliction"){{
            speedMultiplier = 0.15F;
            effect = Fxr.blackened;
            disarm = false;
        }};

        macrosis = new SpreadingStatusEffect("macrosis"){{
            damage = 0.05f;
            speedMultiplier = 0.75f;
            buildSpeedMultiplier = 0.75f;
            dragMultiplier = 0.85f;
            damageMultiplier = 0.88f;
            spreadInterval = 85;
            effect = Fxr.craeWeaversResidue;
            updateCons = new Cons<Unit>() {
                @Override
                public void get(Unit unit) {
                    unit.impulse(Tmp.v1.trns(unit.rotation, unit.type.speed * 6));
                }
            };
        }};

        macotagus = new SpreadingStatusEffect("macotagus"){{
            damage = 0.135f;
            spreadSingle = false;
            speedMultiplier = 0.45f;
            buildSpeedMultiplier = 0.55f;
            dragMultiplier = 0.85f;
            damageMultiplier = 0.82f;
            spreadInterval = 120;
            effect = Fxr.craeWeaverShards;
            effectChance = 0.15f;
            updateCons = new Cons<Unit>() {
                @Override
                public void get(Unit unit) {
                    float rotation = unit.rotation;
                    Unit nearestAlly = null;
                    if(Time.time % 5 < 1){
                        nearestAlly = Units.closest(unit.team, unit.x, unit.y, spreadRadius * 3, u -> true);
                        if(nearestAlly != null) {
                            rotation = Mathf.lerp(unit.angleTo(nearestAlly), unit.rotation, 0.15f  * Time.delta);
                        }
                    }
                    unit.impulse(Tmp.v1.trns(rotation, nearestAlly != null ? unit.type.accel * 32 * (2 - speedMultiplier) : unit.type.accel * 8 * (2 - speedMultiplier)));
                }
            };
        }};

        //does more damage the more damaged the unit is. Strangely heals the unit overtime.
        causticBurning = new ConsStatusEffect("caustic-burning"){{
            speedMultiplier = 0.85f;
            buildSpeedMultiplier = 0.85f;
            dragMultiplier = 0.425f;
            damageMultiplier = 0.92f;
            effect = Fx.plasticburn;
            effectChance = 0.025f;

            updateCons = new Cons<Unit>() {
                @Override
                public void get(Unit unit) {
                    if(Time.time % 2 > 1) {
                        if(unit.damaged() && unit.healthf() > 0.15f && unit.healthf() < 0.85f) unit.damagePierce(Math.min((unit.type.health - unit.health)/60, Math.min(10/60f, unit.health / 2500)) * Time.delta);
                        else unit.heal(0.125f);
                    }
                }
            };

        }};

        shieldShatter = new ConsStatusEffect("shield-shatter"){{
            speedMultiplier = 0.85f;
            effect = Fx.generatespark;

            updateCons = new Cons<Unit>() {

                @Override
                public void get(Unit unit) {
                    if(unit.shield() > 0) unit.damage(Mathf.clamp(unit.shield(), 25/60, unit.shield()));
                }
            };
        }};

        corruptShield = new ConsStatusEffect("corrupt-shield"){{
            speedMultiplier = 1.15f;
            effect = Fxr.blackened;
        }};

        fragmentaein = new ConsStatusEffect("fragmentaein"){{
            transitionDamage = 15;
            effect = Fx.bubble;
            init(() -> {
                affinity(amberstriken, ((unit, time, newTime, result) -> {
                    unit.damagePierce(transitionDamage);
                    unit.damage(amberstriken.transitionDamage);
                    Fx.plasticburn.at(unit.x, unit.y);
                    result.set(fragmentaein, 0);
                    unit.unapply(amberstriken);
                }));
                affinity(umbrafliction, ((unit, time, newTime, result) -> {
                    unit.damagePierce(transitionDamage);
                    unit.damage(umbrafliction.transitionDamage);
                    Fxr.blackened.at(unit.x, unit.y);
                    result.set(fragmentaein, 0);
                    unit.unapply(amberstriken);
                }));
            });
        }};

        //you know I had to
        StatusEffects.corroded.effect = Fxr.corrodedEffect;

        corruptShieldCons = new Cons() {
            @Override
            public void get(Object o) {

                //iterate through all bullets
                Groups.bullet.each(b -> {

                    if(Vars.state.isPaused()) return;
                    if(!b.type.reflectable) return;
                    float range = 64;

                    Seq<Unit> intUnits = Groups.unit.intersect(b.x - range, b.y - range, range * 2, range * 2);
                    FloatSeq forces = new FloatSeq();
                    float[] greatestDistance = {0};

                    intUnits.each(u -> {

                        if(!u.hasEffect(corruptShield) || u.team == b.team) return;

                        float distance = u.dst(b);
                        if(distance > greatestDistance[0]) greatestDistance[0] = distance;
                    });

                    intUnits.each(u -> {

                        if(!u.hasEffect(corruptShield) || u.team == b.team) return;

                        float distance = u.dst(b);
                        float force = Mathf.floor(range - u.dst(b)) * distance/greatestDistance[0];
                        forces.add(force);
                    });

                    int[] index = {0};

                    intUnits.each(u -> {

                        if(!u.hasEffect(corruptShield) || u.team == b.team) return;

                        b.vel.setAngle(Angles.moveToward(b.rotation(), u.angleTo(b), forces.get(index[0]) * Time.delta/30));
                        index[0]++;
                    });
                });

            }
        };

        Events.on(Trigger.update.getClass(), corruptShieldCons);

    }
}
