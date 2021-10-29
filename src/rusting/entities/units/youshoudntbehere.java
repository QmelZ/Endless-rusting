package rusting.entities.units;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import rusting.content.Palr;
import rusting.content.RustingUnits;
import rusting.graphics.Drawr;

public class youshoudntbehere extends AntiquimGuardianUnitEntity{
    public TextureRegion ourRegionNow;
    private Seq<TextureRegion> weapons = Seq.with();
    private PixmapRegion manipulationIsKey;
    private Pixmap stencil;
    private int drawingLayer = 0;
    private int endOn = 0;

    @Override
    public void collision(Hitboxc other, float x, float y) {
        super.collision(other, x, y);
        if(other instanceof Bullet){
            updateRegion();
        }
    }

    public void updateRegion(){
        //ourRegionNow = Vars.content.units().random().region;
        endOn += 1;
        if(endOn >= 81) return;
        if(endOn >= 80) {
            manipulationIsKey = Core.atlas.getPixmap("endless-rusting-PLACEHOLDER8");
        }
        else manipulationIsKey = Core.atlas.getPixmap(Seq.with(Vars.content.units()).filter(s -> Core.atlas.isFound(s.region)).random().name);
        Drawr.pigmentae(manipulationIsKey, Palr.voidBullet);

        try{
            stencil = new Pixmap(manipulationIsKey.width, manipulationIsKey.height, manipulationIsKey.pixmap.getFormat());
        }
        catch (Error e){
            return;
        }

        stencil = Drawr.pigmentae(manipulationIsKey, Palr.dustriken);

        for (int y = 0; y < manipulationIsKey.height; y++) {
            for (int x = 0; x < manipulationIsKey.width; x++) {

                //distance from current point to center
                float distance = Tmp.v1.set(manipulationIsKey.width/2, manipulationIsKey.height/2).dst(x, y);

                //get color at the current point
                Color col = new Color(stencil.getPixel(x, y));

                //the closer the point selected is to the center, the more it's lerped into Palr.voidBullet
                col.lerp(Palr.voidBullet, distance/Mathf.dst(manipulationIsKey.width, manipulationIsKey.height, 0, 0));
                if(col.a == 1) stencil.draw(x, y, col);
            }
        }

        ourRegionNow = new TextureRegion(new Texture(stencil));

        for (int i = 0; i < type.weapons.size; i++) {
            weapons.add(Seq.with(Vars.content.units()).filter(s -> Core.atlas.isFound(s.region)  && s.weapons.size > 0).random().weapons.random().region);
        }
    }

    @Override
    public void update() {
        super.update();
        drawingLayer = (int) Mathf.random(Layer.groundUnit, Layer.buildBeam);
        iframes = Mathf.random(1, 100000);
    }

    @Override
    public void draw() {
        if(weapons.size > 0) for (int i = 0; i <type.weapons.size; i++) {
            Tmp.v2.set(type.weapons.get(i).x, type.weapons.get(i).y).rotate(rotation);
            Draw.rect(weapons.random(), x + Tmp.v2.x, y + Tmp.v2.y, rotation);
        }

        if(ourRegionNow == null) ourRegionNow = type.region;
        Draw.z(drawingLayer);
        Draw.rect(ourRegionNow, x, y, rotation);

        Draw.color(Pal.shadow);
        float e = Mathf.lerp(0, 0.5f, Mathf.sin(Time.time));
        Draw.rect(type.shadowRegion, x + UnitType.shadowTX * e, y + UnitType.shadowTY * e, rotation - 90);
        Draw.color();

    }

    @Override
    public void damage(float amount) {
        super.damage(Math.min(amount, 15));
    }

    @Override
    public void damagePierce(float amount) {
        super.damage(Math.min(amount, 15 + armor));
    }

    @Override
    public String toString() {
        return "run.#no.";
    }

    @Override
    public int id() {
        return RustingUnits.classID(youshoudntbehere.class);
    }
}
