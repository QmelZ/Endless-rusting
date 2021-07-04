package rusting.content;

import arc.graphics.Color;
import mindustry.graphics.Pal;

public class Palr {
    public static Color
        pulseChargeStart = new Color(Color.sky).lerp(Pal.lightTrail, 0.35f),
        pulseChargeEnd = new Color(Color.sky).lerp(Pal.lightTrail, 0.25f).lerp(Color.valueOf("#a4ddf2"), 0.05f),
        pulseBullet = new Color(Pal.lancerLaser).lerp(Pal.lightPyraFlame, 0.05f).lerp(pulseChargeEnd, 0.1f),
        pulseShieldStart = Color.valueOf("#5c79d0"),
        pulseShieldEnd = new Color(Color.blue).lerp(Color.valueOf("#6e8adf"), 0.75f).lerp(Color.black, 0.25f),
        voidBullet = new Color(Color.purple).lerp(Color.black, 0.75f),
        dustriken = Color.valueOf("#70696c"),
        lightstriken = Color.valueOf("#b1adac");
    ;
}
