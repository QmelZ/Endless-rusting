package rusting.content;

import arc.graphics.Color;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.type.Weather;
import mindustry.type.weather.ParticleWeather;
import mindustry.world.meta.Attribute;
import rusting.type.weather.BulletParticleWeather;

public class RustingWeathers implements ContentList{
    public static Weather
            //destructive
            fossilStorm, corrosiveDeluge, chemNullificationStorm;

    @Override
    public void load(){
        fossilStorm = new BulletParticleWeather("fossil-storm"){{
            particleBullet = RustingBullets.fossilShard;
            dynamicSpawning = true;
            chanceSpawn = 4;
            randRange = new Vec2(4, 4);
            color = noiseColor = Color.valueOf("#c4cf6f");
            particleRegion = "particle";
            drawNoise = true;
            useWindVector = true;
            sizeMax = 8;
            sizeMin = 4;
            minAlpha = 0.1f;
            maxAlpha = 0.8f;
            density = 1850;
            baseSpeed = 3.45f;
            opacityMultiplier = 0.45f;
            force = 0.15f;
            sound = Sounds.wind;
            soundVol = 0.7f;
            duration = 2 * Time.toMinutes;
            attrs.set(Attribute.light, -0.4f);
            attrs.set(Attribute.water, -0.2f);
        }};

        corrosiveDeluge = new BulletParticleWeather("corrosive-deluge") {{
            color = noiseColor = regionColour = Color.coral;
            dynamicSpawning = false;
            chanceSpawn = 0;
            attrs.set(Attribute.light, 0.75f);
            attrs.set(Attribute.water, 0.35f);
        }};

        chemNullificationStorm = new ParticleWeather("chem-nullification-storm") {{
            color = noiseColor = Color.cyan;
            particleRegion = "particle";
            useWindVector = true;
            sizeMax = 8;
            sizeMin = 4;
            minAlpha = 0.1f;
            maxAlpha = 0.8f;
            density = 1850;
            baseSpeed = 3.45f;
            status = RustingStatusEffects.causticBurning;
            statusDuration = 500f;
            opacityMultiplier = 0.45f;
            force = 0.035f;
            sound = Sounds.rain;
            soundVol = 0.45f;
            duration = 4.35f * Time.toMinutes;
            attrs.set(Attribute.light, 0.75f);
            attrs.set(Attribute.water, 0.35f);
            attrs.set(Attribute.heat, -0.15f);
            attrs.set(Attribute.oil, -0.25f);
            attrs.set(Attribute.spores, -0.45f);
        }};
    }
}
