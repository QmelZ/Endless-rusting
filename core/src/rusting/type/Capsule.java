package rusting.type;

import arc.Core;
import rusting.ctype.UnlockableERContent;

public class Capsule<itemStack, liquidStack> extends UnlockableERContent {

    public float itemPayloadStore;
    public float liquidPayloadStore;
    //durability of the capsule
    public int durability = 100;
    //Insulation of the capsule, used for handling hot liquids 0 means it's heat conductive, and leaks heat everywhere, 1 means that it retains all heat
    public int insulation = 0;
    //Resistance to heat. Used in game, only a stat outside of in game usage.
    public int heatResistance = 0;

    public Capsule(String name){
        super(name);
    }
    public Capsule(String name, float itemPayloadStore, float liquidPayloadStore) {
        super(name);
        this.itemPayloadStore = itemPayloadStore;
        this.liquidPayloadStore = liquidPayloadStore;
    }

    public void load(){

        fullIcon = Core.atlas.find(name);
        if(!Core.atlas.isFound(fullIcon)) fullIcon = Core.atlas.find("endless-rusting-default-capsule");
        String bundleKey = getContentType().name + "." + name;
        if(localizedName == null) localizedName = Core.bundle.get(bundleKey + ".name", Core.bundle.get(bundleKey + ".displayName", name));
        if(description == null) description = Core.bundle.get(bundleKey + ".description", "");
        if(details == null) details = Core.bundle.get(bundleKey + ".description", "");
    }

    public String name(){
        return localizedName == null ? name : localizedName;
    }

    @Override
    public boolean isDisposed() {
        return super.isDisposed();
    }

}