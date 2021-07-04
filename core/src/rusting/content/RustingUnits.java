package rusting.content;

import arc.func.Prov;
import arc.graphics.Color;
import arc.struct.ObjectIntMap;
import arc.struct.ObjectMap.Entry;
import mindustry.content.Bullets;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import rusting.ai.types.MultiSupportAI;
import rusting.entities.abilities.*;
import rusting.entities.units.*;

public class RustingUnits implements ContentList{
    //Steal from BetaMindy
    private static Entry<Class<? extends Entityc>, Prov<? extends Entityc>>[] types = new Entry[]{
            prov(CraeUnitEntity.class, CraeUnitEntity::new),
            prov(BaseUnit.class, BaseUnit::new)
    };

    private static ObjectIntMap<Class<? extends Entityc>> idMap = new ObjectIntMap<>();

    /**
     * Internal function to flatmap {@code Class -> Prov} into an {@link Entry}.
     * @author GlennFolker
     */
    private static <T extends Entityc> Entry<Class<T>, Prov<T>> prov(Class<T> type, Prov<T> prov){
        Entry<Class<T>, Prov<T>> entry = new Entry<>();
        entry.key = type;
        entry.value = prov;
        return entry;
    }

    /**
     * Setups all entity IDs and maps them into {@link EntityMapping}.
     * @author GlennFolker
     */

    private static void setupID(){
        for(
                int i = 0,
                j = 0,
                len = EntityMapping.idMap.length;

                i < len;

                i++
        ){
            if(EntityMapping.idMap[i] == null){
                idMap.put(types[j].key, i);
                EntityMapping.idMap[i] = types[j].value;

                if(++j >= types.length) break;
            }
        }
    }

    /**
     * Retrieves the class ID for a certain entity type.
     * @author GlennFolker
     */
    public static <T extends Entityc> int classID(Class<T> type){
        return idMap.get(type, -1);
    }

    public static CraeUnitType
            duono, duoly, duanga,
            fahrenheit;
    public static UnitType
            marrow, metaphys, ribigen;

    @Override
    public void load() {
        setupID();

        EntityMapping.nameMap.put("duono", CraeUnitEntity::new);
        duono = new CraeUnitType("duono"){{

            defaultController = MultiSupportAI::new;

            flying = true;
            hitSize = 7;
            itemCapacity = 50;
            health = 110;
            speed = 1.2f;
            accel = 0.045f;
            drag = 0.025f;
            isCounted = false;

            mineTier = 3;
            mineSpeed = 1.5f;

            pulseStorage = 25;
            repairRange = 40;

            projectileDeathSpawnInterval = 3;

            constructor = CraeUnitEntity::new;

            abilities.add(
                    new UpkeeperFieldAbility(4.35f, 135, 45, 4f)
            );
            weapons.add(
                new Weapon("none") {{
                    x = 0;
                    y = 0;
                    mirror = false;
                    bullet = RustingBullets.paveBolt;
                    reload = 120;
                }}
            );
        }};

        EntityMapping.nameMap.put("duoly", CraeUnitEntity::new);
        duoly = new CraeUnitType("duoly"){{
            defaultController = MultiSupportAI::new;

            flying = true;
            hitSize = 10;
            itemCapacity = 20;
            health = 320;
            speed = 2.3f;
            accel = 0.0225f;
            drag = 0.0185f;
            rotateSpeed = 2.85f;
            isCounted = false;

            mineTier = 3;
            mineSpeed = 2.3f;

            pulseStorage = 65;
            repairRange = 50;


            projectileDeathSpawnInterval = 5;

            constructor = CraeUnitEntity::new;

            abilities.add(
                    new UpkeeperFieldAbility(5.5f, 175, 55, 7f)
            );
            weapons.add(
                new Weapon("none") {{
                    x = 1;
                    y = 3;
                    bullet = RustingBullets.paveWeaver;
                    reload = 22.5f;
                }}
            );
        }};

        EntityMapping.nameMap.put("duanga", CraeUnitEntity::new);
        duanga = new CraeUnitType("duanga"){{
            defaultController = MultiSupportAI::new;

            range = 155;
            flying = true;
            hitSize = 15;
            itemCapacity = 35;
            health = 435;
            armor = 2;
            speed = 1.8f;
            accel = 0.0525f;
            drag = 0.0385f;
            rotateSpeed = 4.85f;
            buildSpeed = 0.75f;

            rotateShooting = false;

            isCounted = false;

            pulseStorage = 85;
            repairRange = 90;
            pulseAmount = 3.25f;
            pulseGenRange = 120;

            projectileDeathSpawnInterval = 8;

            constructor = CraeUnitEntity::new;

            abilities.add(
                new HealthEqualizerAbility(){{
                    health = 7.5f;
                    reload = 22.5f;
                    range = 95;
                    lineThickness = 7;
                    laserOffset = 2.35f;
                    maxWidth = 2;
                    y = -2;
                    x = 3.75f;
                    mirror = true;
                }},
                new PulseGeneratorAbility(){{
                    pulse = 3.25f;
                    reload = 135;
                    range = 120;
                    laserOffset = 2.35f;
                    x = 0;
                    y = -6.25f;
                }},
                new UpkeeperFieldAbility(7.5f, 235, 65, 6),
                new RegenerationAbility(3.5f/60)
            );
        }};


        EntityMapping.nameMap.put("fahrenheit", CraeUnitEntity::new);
        fahrenheit = new CraeUnitType("fahrenheit"){{

            flying = false;
            hitSize = 5;
            health = 110;
            speed = 1.2f;
            accel = 0.045f;
            drag = 0.025f;

            pulseStorage = 25;

            projectileDeathSpawnInterval = 6;

            constructor = CraeUnitEntity::new;

            abilities.add(
                    new UpkeeperFieldAbility(4.35f, 135, 45, 4f)
            );
            weapons.add(
                new Weapon("none") {{
                    x = 0;
                    y = 0;
                    mirror = false;
                    bullet = RustingBullets.paveBolt;
                    reload = 120;
                }}
            );
        }};

        EntityMapping.nameMap.put("marrow", BaseUnit::new);
        marrow = new UnitType("marrow"){{
            hitSize = 8;
            health = 155;
            armor = 1;
            speed = 0.6f;
            accel = 0.5f;
            drag = 0.25f;
            lightRadius = hitSize * 2.5f;
            lightColor = Palr.dustriken;
            lightOpacity = 0.15f;
            itemCapacity = 15;
            commandLimit = 4;
            mechLegColor = Palr.dustriken;
            //v7 compatability
            constructor = BaseUnit::new;
            abilities.add(
                    new RegenerationAbility(0.1f)
            );

            immunities.addAll(
                StatusEffects.wet,
                StatusEffects.burning,
                StatusEffects.sporeSlowed,
                StatusEffects.sapped,
                RustingStatusEffects.shieldShatter
            );

            weapons.add(
                new Weapon("none") {{
                    x = 4;
                    y = 4.25f;
                    shots = 3;
                    spacing = 3;
                    shotDelay = 5;
                    bullet = RustingBullets.horizonInstalt;
                    shootSound = Sounds.bang;
                    reload = 125;
                }}
            );

        }};

        EntityMapping.nameMap.put("metaphys", BaseUnit::new);
        metaphys = new UnitType("metaphys"){{
            hitSize = 10;
            health = 540;
            armor = 4;
            speed = 0.45f;
            accel = 0.15f;
            drag = 0.05f;
            lightRadius = hitSize * 2.5f;
            lightColor = Palr.dustriken;
            lightOpacity = 0.35f;
            itemCapacity = 35;
            commandLimit = 3;
            mechLegColor = Palr.dustriken;
            //v7 compatability
            constructor = BaseUnit::new;
            abilities.add(
                    new RegenerationAbility(0.1f)
            );

            immunities.addAll(
                StatusEffects.wet,
                StatusEffects.burning,
                StatusEffects.sporeSlowed,
                StatusEffects.sapped,
                RustingStatusEffects.shieldShatter
            );

            weapons.addAll(
                new Weapon("endless-rusting-metaphys-sidearms"){{
                    top = false;
                    alternate = true;
                    x = 7.25f;
                    y = 1.25f;
                    recoil = 2;
                    shots = 2;
                    spacing = 5;
                    inaccuracy = 3;
                    bullet = RustingBullets.mhenShard;
                    shootSound = Sounds.flame2;
                    reload = 57.5f;
                }}
            );

        }};

        EntityMapping.nameMap.put("ribigen", BaseUnit::new);
        ribigen = new UnitType("ribigen"){{
            hitSize = 13;
            health = 940;
            armor = 10;
            speed = 0.35f;
            accel = 0.35f;
            drag = 0.15f;
            lightRadius = hitSize * 3.5f;
            lightColor = Palr.dustriken;
            lightOpacity = 0.05f;
            itemCapacity = 75;
            commandLimit = 2;
            mechLegColor = Palr.dustriken;
            //v7 compatability
            constructor = BaseUnit::new;
            abilities.add(
                    new RegenerationAbility(0.3f)
            );

            immunities.addAll(
                    StatusEffects.wet,
                    StatusEffects.burning,
                    StatusEffects.sporeSlowed,
                    StatusEffects.sapped,
                    RustingStatusEffects.shieldShatter,
                    RustingStatusEffects.amberstriken,
                    RustingStatusEffects.umbrafliction
            );

            weapons.addAll(
                new Weapon("endless-rusting-ribigen-instalt-launcher"){{
                    x = 13.25f;
                    y = 6.25f;
                    reload = 120;
                    recoil = 3;
                    shots = 4;
                    spacing = 4;
                    shootSound = Sounds.artillery;
                    bullet = RustingBullets.timelessInstalt;
                    top = false;
                }},
                new Weapon("endless-rusting-ribigen-weapon"){{
                    x = 0;
                    y = 0;
                    shootY = 7.75f;
                    shots = 5;
                    spacing = 1;
                    inaccuracy = 0.25f;
                    shotDelay = 1;
                    recoil = 0;
                    reload = 150;
                    heatColor = new Color(Pal.turretHeat).a(0.54f);
                    bullet = Bullets.heavyOilShot;
                    shootSound = Sounds.release;
                    mirror = false;
                }},
                new Weapon("none"){{
                    x = 0;
                    y = 0;
                    shots = 25;
                    spacing = 2;
                    inaccuracy = 1;
                    shotDelay = 0.35f;
                    reload = 150;
                    bullet = Bullets.oilShot;
                    shootSound = Sounds.none;
                }}
            );
        }};

    }
}
