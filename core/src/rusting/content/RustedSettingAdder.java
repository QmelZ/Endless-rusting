package rusting.content;

import arc.Core;
import arc.Events;
import arc.func.Cons;
import arc.input.KeyCode;
import arc.util.Log;
import arc.util.Reflect;
import mindustry.Vars;
import mindustry.game.EventType.Trigger;
import rusting.Varsr;

import java.lang.reflect.Method;

public class RustedSettingAdder {

    //reflection is used as a workaround for fields being merged into the subclass in 7.0
    public void addGraphicSetting(String key){
        set("graphics", "checkPref", new Class[]{String.class, boolean.class}, key, Core.settings.getBool(key));
    }

    public void addGameSetting(String key){
        set("game", "checkPref", new Class[]{String.class, boolean.class}, key, Core.settings.getBool(key));
    }

    public void init(){
        boolean tmp = Core.settings.getBool("uiscalechanged", false);
        Core.settings.put("uiscalechanged", false);

        addGraphicSetting("drawtrails");

        Core.settings.put("uiscalechanged", tmp);

        Cons dialogShow = new Cons() {
            @Override
            public void get(Object o) {
                if(Core.input.keyTap(KeyCode.shiftLeft)) {
                    Varsr.ui.capsuleResearch.show();
                    Varsr.content.capsules().each(c -> {
                        Log.info(c.name + (Core.atlas.isFound(Core.atlas.find(c.name)) && Core.atlas.find(c.name) != null ? " has a ui region" : " does not have a ui icon"));
                    });
                }
            }
        };

        Events.on(Trigger.update.getClass(), dialogShow);
    }

    private static void set(String field, String method, Class[] types, Object... values){
        try{
            Object table = Reflect.get(Vars.ui.settings, field);
            Method m = table.getClass().getDeclaredMethod(method, types);
            m.invoke(table, values);
        }catch(Exception e){
            Log.err(e);
        }
    }
}
