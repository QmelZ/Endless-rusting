package rusting.ui.dialog;

import arc.Core;
import arc.scene.style.Drawable;
import mindustry.core.Version;
import mindustry.ctype.ContentList;
import mindustry.gen.Tex;

public class Texr implements ContentList {
    public static Drawable button;

    @Override
    public void load() {
        button = Version.number >= 7 ? Core.atlas.getDrawable("button") : Tex.button;
    }
}
