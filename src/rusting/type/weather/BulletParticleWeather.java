package rusting.type.weather;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.entities.bullet.*;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.gen.WeatherState;
import mindustry.graphics.MultiPacker;
import mindustry.type.weather.ParticleWeather;
import rusting.EndlessRusting;
import rusting.graphics.Drawr;

public class BulletParticleWeather extends ParticleWeather {
    //Bullet which the weather can create.
    public BulletType particleBullet;
    //whether or not to generate a random number instead of using chanceSpawn. Can be useful for making more dynamic weather.
    public boolean dynamicSpawning = true;
    //chance for a bullet to spawn. Defaults to 1.
    public double chanceSpawn = 1;
    //Range within a bullet can be spawned from on a valid tile.
    public Vec2 randRange = new Vec2(55, 55);
    //Fade time for weather, since WeatherState's fadeTime is private
    public int fadeTime = 60 * 6;
    //Colour of generated region. Only change if PixmapRegion is not found
    public Color regionColour = Color.white;

    public BulletParticleWeather(String name) {
        super(name);
    }

    @Override
    public boolean isHidden(){
        return false;
    }

    @Override public void load(){
        region = Core.atlas.find(name);
        if(region != null && region != Core.atlas.find("error")) return;
        region = Drawr.addTexture(Drawr.pigmentae(Core.atlas.getPixmap(EndlessRusting.modname + "-" + "bullet-particle-weather-template"), regionColour), name);
    }

    @Override
    public void createIcons(MultiPacker packer){
        super.createIcons(packer);
        if(region != null) return;
        Pixmap genRegion = Drawr.pigmentae(Core.atlas.getPixmap("bulletParticleWeatherTemplate"), color);
        packer.add(MultiPacker.PageType.main, name, genRegion);
    }

    @Override
    public void update(WeatherState state){
        double infinity = Double.POSITIVE_INFINITY;
        float speed = force * state.intensity * Time.delta;
        if(speed > 0.001f){
            float windx = state.windVector.x * speed, windy = state.windVector.y * speed;

            for(Unit unit : Groups.unit){
                unit.impulse(windx, windy);
            }
        }
        int rnx = Mathf.random(1, Vars.world.tiles.width - 1);
        int rny = Mathf.random(1, Vars.world.tiles.height - 1);

        double d = state.life != infinity ? 10 * Math.sin(state.life/(100 * 60)) : 10 * Mathf.sin(state.effectTimer/(200 * 60));
        double s = Mathf.clamp(Mathf.sin(state.effectTimer /(510 * 60)) * Mathf.clamp(state.intensity * 10, 0, 1) * 2, 0, 3);
        double chance = state.intensity >= 1.11 ? 1 : Mathf.clamp(s / 10 + Math.abs(Math.sin(d) * s / 10 + Math.sin(d * d * 0.1) * 0.5 - Math.sin(s) * 0.5), s / 4, s * 2 <= 0.5 ? 1 : 0.55);
        if(Vars.world.buildWorld(rnx * 8, rny * 8) == null && Vars.world.tile(rnx, rny) != null && Mathf.chance(chance)){
            rnx = rnx * 8;
            rny = rny * 8;
            if(Vars.world.tile(rnx/8, rny/8).block() == Blocks.air) {
                if (Mathf.chance(dynamicSpawning ? chance * chanceSpawn: chanceSpawn)) {
                    particleBullet.create(null, Team.derelict, (float) rnx + Mathf.random(randRange.x), (float) rny + Mathf.random(randRange.y), state.windVector.angle(), particleBullet.damage, (float) (chance * chance * 2), (float) 1, state);
                    particleBullet.hitEffect.at(rnx, rny);
                }
            }
        }
        if(state.life < fadeTime) state.opacity = (float) Math.min((state.life / fadeTime) * 25 * chance * chance, state.opacity);
        else state.opacity = Mathf.lerpDelta(state.opacity, (float) (25 * chance * chance), (float) 0.004);
    }
}
