package rusting.world.blocks.power;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.type.Item;
import mindustry.world.Tile;
import mindustry.world.blocks.power.BurnerGenerator;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Stat;

public class AttributeBurnerGenerator extends BurnerGenerator {

    public Effect generateEffect = Fx.none;
    public Attribute attribute = Attribute.heat;

    public AttributeBurnerGenerator(String name) {
        super(name);
        ambientSound = Sounds.hum;
        ambientSoundVolume = 0.06f;
    }

    @Override
    public void init(){
        super.init();
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.tiles, attribute, floating);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        drawPlaceText(Core.bundle.formatFloat("bar.efficiency", sumAttribute(attribute, x, y) * 100, 1), x, y, valid);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team){
        //make sure there's heat at this location
        return tile.getLinkedTilesAs(this, tempTiles).sumf(other -> other.floor().attributes.get(attribute)) > 0.01f;
    }

    public class AttributeBurnerGeneratorBuild extends BurnerGeneratorBuild {
        public float sum;

        @Override
        public void updateTile(){


            super.updateTile();

            float calculationDelta = delta();

            heat = Mathf.lerpDelta(heat, generateTime >= 0.001f && enabled ? 1f : 0f, 0.05f);

            totalTime += heat * Time.delta;
consume();
            super.updateTile();
            if (cons.valid()) {
                productionEfficiency = sum + attribute.env();
                if (cons.optionalValid()) {
                    if (liquids().current() != null) {
                        productionEfficiency += getLiquidEfficiency(liquids().current());
                    }

                    Item item = items.take();
                    if(item != null){
                        productionEfficiency += getItemEfficiency(item);
                    }
                }
            } else {
                productionEfficiency = 0;
            }

            if (productionEfficiency > 0.1 && Mathf.chance(0.05 * delta())) {
                generateEffect.at(x + Mathf.range(3), y + Mathf.range(3));
            }
        }

        @Override
        public void drawLight(){
            Drawf.light(team, x, y, (40f + Mathf.absin(10f, 5f)) * Math.min(productionEfficiency, 2f) * size, Color.scarlet, 0.4f);
        }

        @Override
        public void onProximityAdded(){
            super.onProximityAdded();

            sum = sumAttribute(attribute, tile.x, tile.y);
        }
    }
}
