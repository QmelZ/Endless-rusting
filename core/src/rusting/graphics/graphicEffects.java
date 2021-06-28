package rusting.graphics;

import arc.graphics.Color;
import arc.util.Log;
import rusting.content.Fxr;

public class graphicEffects {

    private static float[][][] floatArr;

    public static void trailEffect(Color trailColour, float x, float y, float width, float radius, float sclTime, float alpha, float[][] points){
        floatArr = new float[][][]{{{width, radius}, {sclTime, alpha}}, points};
        Log.info(floatArr);
        Fxr.lineCircles.at(x, y, 0, trailColour, floatArr);
    }
}
