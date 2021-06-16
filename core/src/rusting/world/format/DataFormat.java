package rusting.world.format;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import rusting.interfaces.Arrayf;

//A format for information, and is read based on the type of format. Load method used to define actual name, and icon for ui.
public class DataFormat {

    public DataFormat(){}

    public DataFormat(String name){
        this.name = name;
    }

    //default name
    public String name = "defaultformat";
    //name displayed in ui
    public String localizedName;
    //icon for format, used for ui, defined in load()
    public TextureRegion icon;
    //Data formats which you can convert to. Method of preventing converting to an incompatible datatype.
    public Seq<DataFormat> convertibleTo;
    //used for conditional conversion into other data formats
    public Arrayf<double[], DataFormat, double[]> convert;

    public void load(){
        localizedName = Core.bundle.get("primitiveformat." + name);
        icon = Core.atlas.find("format-" + name);
    }

}
