package rusting.content;

import arc.func.Cons;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.entities.Units;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import rusting.type.CrystalStatusEffect;
import rusting.type.statusEffect.ConsStatusEffect;
import rusting.type.statusEffect.SpreadingStatusEffect;

public class RustingStatusEffects implements ContentList {
    public static StatusEffect
            amberstriken, umbrafliction, macrosis, macotagus, causticBurning;

    @Override
    public void load() {
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
                    float gotox = unit.x, gotoy = unit.y + 1;
                    Unit nearestAlly = null;
                    if(Time.time % 5 < 1){
                        nearestAlly = Units.closest(unit.team, unit.x, unit.y, spreadRadius * 3, u -> true);
                        if(nearestAlly != null) {
                            Mathf.lerp(unit.angleTo(nearestAlly), unit.rotation, 0.15f  * Time.delta);
                            gotox = nearestAlly.x;
                            gotoy = nearestAlly.y;
                        }
                    }
                    unit.impulse(Tmp.v1.trns(nearestAlly != null ? unit.angleTo(gotox, gotoy) - 90 : unit.rotation, nearestAlly != null ? unit.type.accel * 32 * (2 - speedMultiplier) : unit.type.accel * 8 * (2 - speedMultiplier)));
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

    }
}
