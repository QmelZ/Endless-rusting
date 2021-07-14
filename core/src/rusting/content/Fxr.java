package rusting.content;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import rusting.entities.units.CraeUnitEntity;
import rusting.entities.units.CraeUnitType;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.angle;
import static arc.math.Angles.randLenVectors;

public class Fxr{
    public static final Effect

        none = new Effect(0, 0f, e -> {}),

        blackened = new Effect(35, 0f, e -> {
            color(Color.black, Color.black, e.fin());
            randLenVectors(e.id, 2, e.finpow() * 3, e.rotation, 360, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, (float) (e.fout() * 1 + Math.sin(e.fin() * 2 * Math.PI)));
                Fill.circle(e.x + x, e.y + y, (float) (e.fout() * 1.2 + Math.sin(e.fin() * 2 * Math.PI)));
            });
            Draw.reset();
            color(Color.valueOf("#9c7ae1"), Color.valueOf("#231841"), e.fin());
            Draw.alpha(0.35F * e.fout());
            randLenVectors(e.id, 2, e.finpow() * 5, e.rotation, 360, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, (float) (e.fout() * 1 + Math.sin(e.fin() * 2 * Math.PI)));
            });
        }),

        singingFlame = new Effect(18, e ->{
            color(Pal.lightPyraFlame, Pal.darkPyraFlame, e.fin() * e.fin());
            float vx = e.x, vy = e.y;
            if(e.data instanceof Position){
                //fidn the offset from the bullet to it's data
                vx += ((Bullet) e.data()).getX() * e.fin() - e.x;
                vy += ((Bullet) e.data()).getY() * e.fin() - e.y;
            }
            float finalVx = vx;
            float finalVy = vy;
            randLenVectors(e.id, 3, 2f + e.fin() * 16f, e.rotation + 180, 15, (x, y) -> {
                Fill.circle(finalVx + x, finalVy + y, 0.2f + e.fout() * 1.5f);
            });
        }),

        paveFlame = new Effect(45, e ->{
            randLenVectors(e.id, 5, 7f + e.fin() * 16f, e.rotation + 180, 15, (x, y) -> {
                color(Pal.lighterOrange, Pal.lightFlame, Math.abs(x * y/4));
                Fill.circle(e.x + x, e.y + y, e.fout() * e.fout() * 2.3f);
            });
        }),

        shootMhemFlame = new Effect(25f, 80f, e -> {
            color(Pal.lightPyraFlame, Pal.darkPyraFlame, Color.gray, e.fin() * e.fin());

            randLenVectors(e.id, 6, e.finpow() * 45f, e.rotation, 10f, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f);
            });
        }),

    launchCraeWeavers = new Effect(85f, 80f, e -> {
        color(Palr.pulseChargeStart, Color.sky, Palr.pulseChargeEnd, e.fin() * e.fin());
        Draw.alpha(e.fout());
        randLenVectors(e.id, 4, e.fin() * 55f, e.rotation, 6, (x, y) -> {
            float rotx =  Angles.trnsx(e.fslope() * e.fslope(), e.fslope() * e.fslope() * 8, e.fin() * 360 - 90), roty = Angles.trnsx(e.fslope() * e.fslope(), e.fslope() * e.fslope() * 9, e.fin() * 360 - 90);
            Fill.circle(e.x + x + rotx, e.y + y + roty, e.fin() * 3f);
        });


        randLenVectors(e.id, 10, e.fin() * 55f, e.rotation, 6, (x, y) -> {
            Lines.stroke(e.fout());
            float rotx =  Angles.trnsx(e.fout() * e.fout(), e.fout() * e.fout() * 8, e.fout() * 360 - 90), roty = Angles.trnsx(e.fout() * e.fout(), e.fout() * e.fout() * 9, e.fin() * 360 - 90);
            lineAngle(e.x + x + rotx, e.y + y + roty, Mathf.angle(x, y), 1f + e.fout() * 3f);
        });

        Fill.light(e.x, e.y, 15,  42 + 3 * e.fout(), Tmp.c1.set(Palr.pulseChargeStart).a(e.fout() * 0.45f), Tmp.c2.set(Palr.pulseChargeEnd).a(e.fout() * 0.25f));
    }),

    craeCorsair = new Effect(135f, 80f, e -> {
        color(Palr.pulseChargeStart, Color.sky, Palr.pulseChargeEnd, e.fin() * e.fin());
        alpha(e.fout() * e.fout() * 0.5f);
        if(e.data instanceof TextureRegion) Draw.rect((TextureRegion) e.data, e.x, e.y, e.rotation);
        alpha(e.fout() * e.fout());
        for(int i = 0; i < 4; i++){
            float tnx = Angles.trnsx(i * 90 + e.fin() * 360, 0, 3), tny = Angles.trnsy(i * 90 + e.fin() * 360, 0, 3);
            randLenVectors(e.id, 3, e.fin() * 6 + 5, i * 90 - 90, 2, (x, y) -> {
                Lines.lineAngle(e.x + tnx + x, e.y + tny + y, Mathf.angle(x, y), e.fout());
            });
        }
    }),

    craeWeaversResidue = new Effect(32f, 80f, e -> {
        color(Palr.pulseChargeStart, Color.sky, Palr.pulseChargeEnd, e.fin() * e.fin());
        Draw.alpha(e.fout() * 0.65f);
        randLenVectors(e.id, 1, e.fin() * 55f, e.rotation, 3, (x, y) -> {
            float rotx =  Angles.trnsx(e.fslope(), e.fslope() * 8, e.fin()), roty = Angles.trnsx(e.fslope() * e.fslope() * 360, e.fslope() * 9, e.fin());
            Fill.circle(e.x + x + rotx, e.y + y + roty, e.fin() * 3f);
        });


        randLenVectors(e.id, 3, e.fin() * 55f, e.rotation, 3, (x, y) -> {
            Lines.stroke(e.fout());
            float rotx =  Angles.trnsx(e.fslope(), e.fslope() * 8, e.fin() * 360 - 90), roty = Angles.trnsx(e.fslope(), e.fslope() * 9, e.fin() * 360 - 90);
            lineAngle(e.x + x + rotx, e.y + y + roty, Mathf.angle(x, y), 1f + e.fout() * 3f);
        });
    }),

    craeWeaverShards = new Effect(125f, e -> {
        color(Palr.pulseChargeStart, Color.sky, Palr.pulseChargeEnd, e.fin() * e.fin());
        Draw.alpha(e.fout() * e.fout());
        for(int i = 0; i < 3; i++){
            float tnx = Angles.trnsx(i * 120 + e.finpow() * 360 + e.rotation - 90, 0, 5), tny = Angles.trnsy(i * 120 + e.finpow() * 360 + e.rotation - 90, 0, 5);
            randLenVectors(e.id, 2, e.fin() * 6 + 5, i * 120 + e.rotation - 90, 2, (x, y) -> {
                Lines.lineAngle(e.x + tnx + x, e.y + tny + y, angle(x, y) - 90, e.fout() * e.fout());
            });
        }
    }),

    whoosh = new Effect(15, e -> {
        Draw.color(e.color, e.color, 1);
        Draw.alpha(e.fout());
        randLenVectors(e.id, 2, e.fin() * 6 + 5, e.rotation - 90, 2, (x, y) -> {
            Lines.lineAngle(e.x, e.y, angle(x, y) - 90, e.fout() * e.fout() * 5);
        });
    }),

    pulseExplosion = new Effect(85f, e -> {
        float nonfinalSplosionRadius = 42 + 3 * e.fout();
        int clouds = 5;
        if(e.data instanceof CraeUnitType) {
            nonfinalSplosionRadius = ((CraeUnitType) e.data).hitSize * 4 + 16 + ((CraeUnitType) e.data).hitSize * 2 * e.fout();
            clouds = (int) ((CraeUnitType) e.data).hitSize/3 + 3;
        }
        else if(e.data instanceof float[]){
            float[] args = (float[]) e.data;
            if(args.length > 0){
                nonfinalSplosionRadius = args[0];
                clouds = (int) args[1];
            }
        }
        final float splosionRadius = nonfinalSplosionRadius;

        color(Palr.pulseChargeStart);

        e.scaled(clouds + 2, i -> {
            stroke(3f * i.fout());
            Lines.circle(e.x, e.y, 3f + i.fin() * splosionRadius);
        });

        randLenVectors(e.id, clouds, 2f + 23f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * splosionRadius/2 + 0.5f);
        });

        color(Palr.pulseChargeEnd);
        stroke(e.fout());

        randLenVectors(e.id + 1, clouds - 1, 1f + 23f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * splosionRadius/2.5f);
        });
    }),

    pulseSmoke = new Effect(315f, e -> {
        float nonfinalSplosionRadius = 42 + 3 * e.fout();
        int clouds = 5;
        float nonfinalAlphaPercent = 1;
        if(e.data instanceof CraeUnitType) {
            nonfinalSplosionRadius = ((CraeUnitType) e.data).hitSize * 4 + 16 + ((CraeUnitType) e.data).hitSize * 2 * e.fout();
            clouds = (int) ((CraeUnitType) e.data).hitSize/3 + 3;
        }
        else if(e.data instanceof float[]){
            float[] args = (float[]) e.data;
            if(args.length > 0){
                nonfinalSplosionRadius = args[0];
                clouds = (int) args[1];
                nonfinalAlphaPercent = args[2];
            }
        }
        final float splosionRadius = nonfinalSplosionRadius;
        final float alphaPercent = Math.min(e.fin() * 315, 60)/60 * nonfinalAlphaPercent;

        Draw.color(Pal.plasticSmoke, Pal.darkestGray, e.fslope() * e.fslope());

        randLenVectors(e.id, clouds * 2, splosionRadius * e.finpow() + 5, e.rotation,  360, (x, y) -> {
            float distance = Mathf.dst(x, y);
            Fill.circle(e.x + x,e.y + y, (1 - distance/(splosionRadius/7 + 5)) * e.fout() * e.fout() * 2);
        });

        if(e.data instanceof CraeUnitEntity) Draw.color(((CraeUnitType) e.data).chargeColourStart, ((CraeUnitType) e.data).chargeColourEnd, e.fin());
        else Draw.color(Palr.pulseChargeStart, Palr.pulseChargeEnd, e.fin());
        Draw.alpha(alphaPercent * e.fout() * 8/10);

        randLenVectors(e.id, clouds * 3, splosionRadius * e.finpow(), e.rotation,  360, (x, y) -> {
            float distance = Mathf.dst(x, y);
            Draw.alpha((1 - distance/(splosionRadius/9.5f - 5)) * e.fout() * e.fout() * 0.15f + 0.85f * e.fout() * alphaPercent);
            Fill.circle(e.x + x,e.y + y, splosionRadius/3.5f);
            Drawf.light(Team.derelict, e.x + x, e.y + y, splosionRadius/3.5f, Palr.pulseChargeStart, Draw.getColor().a);
        });

        randLenVectors(e.id, clouds, splosionRadius/1.5f * e.finpow(), e.rotation,  360, (x, y) -> {
            float distance = Mathf.dst(x, y);
            Draw.alpha((1 - distance/(splosionRadius/7.5f - 5)) * e.fout() * e.fout() * 0.25f + 0.75f * e.fout() * e.fout() * alphaPercent);
            Fill.circle(e.x + x,e.y + y, splosionRadius/7.25f);
            Drawf.light(Team.derelict, e.x + x, e.y + y, splosionRadius/5.25f, Palr.pulseChargeEnd, e.fout() * 0.65f);
        });
    }),

    lineCircles = new Effect(335f, e -> {
        Draw.color(e.color);
        float[][][] arrays = ((float[][][]) e.data);
        float[][] params = arrays[0];
        //first array contains params, second array contains points
        float width = params[0][0];
        float circleRadius = params[0][1];
        float alpha = params[1][0];
        float tickThresholdOut = params[1][1];
        float tickThreshold = params[1][2];
        float[][] points = arrays[1];

        for (int i = 0; i < points[0].length - 1; i++) {
            int ic = i + 1;
            float alphaDraw = Mathf.clamp(e.fin() * 335f/tickThreshold * ic, 0, 1);
            float alphaDrawOut = Mathf.clamp(tickThresholdOut * (points[0].length - ic)/(e.fin() * 335f), 0, 1);
            Draw.alpha(Math.min(alphaDraw, alphaDrawOut) * alpha);
            Lines.stroke(width);
            Lines.line(points[0][i], points[1][i], points[0][i+ 1], points[1][i + 1]);
            Fill.circle(points[0][i + 1], points[1][i + 1], circleRadius);
        }
    }),

    instaltSummoner = new Effect(240, e -> {
        Draw.color(Palr.lightstriken, Palr.dustriken, e.fin());
        float initialScaling = Math.min(e.fout() * 4, Math.min(e.fin() * 4, 1));
        float scaling = e.fout() * 4;
        Draw.alpha(initialScaling);
        Fill.circle(e.x, e.y, initialScaling * 8);
        float spacing = 2;
        for(float i = 0; i < scaling; i++){
            Lines.stroke((scaling - i) * spacing/3);
            Lines.circle(e.x, e.y, (scaling - i) * spacing + i + 8);
        }
    }),

    //modified flak explosion
    instaltSummonerExplosion = new Effect(45, e -> {
        color(Pal.bulletYellowBack);

        e.scaled(15, i -> {
            stroke(3f * i.fout());
            Lines.circle(e.x, e.y, 5f + i.fin() * 25f);

        });

        e.scaled(6, i -> {
            stroke(4f * i.fout());
            Lines.circle(e.x, e.y, 3f + i.fin() * 35f);
        });

        color(Color.gray);

        randLenVectors(e.id, 7, 2f + 33f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 4.5f + 0.55f);
        });

        color(Pal.bulletYellow);
        stroke(e.fout());

        randLenVectors(e.id + 1, 6, 1f + 23f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 5f);
        });
    });

}