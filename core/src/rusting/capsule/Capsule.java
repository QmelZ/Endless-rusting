package rusting.capsule;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import rusting.content.RustingCapsules;

public class Capsule {

    public Capsule(String name){
        this.name = name;
    };

    //used for internal things
    //format: modname-name
    public String name;

    //name that's shown
    public String displayName, description, details;

    public float ID = 0;

    public TextureRegion region;

    public void load(){

        region = Core.atlas.find(name);
        if(!Core.atlas.isFound(region)) region = Core.atlas.find("endless-rusting-default-capsule");
        ID = RustingCapsules.nextID();
        String bundleKey = "capsule." + name;
        if(displayName == null) displayName = Core.bundle.get(bundleKey + ".name", Core.bundle.get(bundleKey + ".displayName", Core.bundle.get("capsule.default.name")));
        if(description == null) description = Core.bundle.get(bundleKey + ".description", "");
        if(details == null) details = Core.bundle.get(bundleKey + ".description", "");
    }

    public String name(){
        return displayName == null ? name : displayName;
    }
}
