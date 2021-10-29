package rusting.graphics;

import arc.Core;
import arc.graphics.gl.Shader;
import arc.scene.ui.layout.Scl;
import arc.util.Nullable;
import arc.util.Time;

import static mindustry.Vars.headless;
import static mindustry.Vars.tree;

public class RustedShaders {
    public static @Nullable
    NamedShader sharpeffect;
    protected static boolean loaded;

    public static void load(){
        if(headless) return;
        sharpeffect = new NamedShader("sharpeffect");
        loaded = true;
    }

    public static void dispose(){
        if(!headless && loaded){
            sharpeffect.dispose();
        }
    }

    /** Shaders that the the*/
    public static class NamedShader extends Shader {
        public NamedShader(String name) {
            super(Core.files.internal("shaders/default.vert"),
                    tree.get("shaders/" + name + ".frag"));
        }

        @Override
        public void apply() {
            setUniformf("u_time", Time.time / Scl.scl(1f));
            setUniformf("u_offset",
                    Core.camera.position.x,
                    Core.camera.position.y
            );
        }
    }
}
