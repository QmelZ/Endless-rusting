package rusting.world.format.holder;

import arc.struct.Seq;
import arc.util.Tmp;
import rusting.interfaces.Arrayf;
import rusting.world.format.DataFormat;

//used to hold multiple data formats
public class FormatHolders {

    static class Seqf extends Seq<DataFormat>{
        public DataFormat getByName(String name){
            DataFormat[] returnObject = {null};
            each(e -> {
                if(e.name.equals(name)) returnObject[0] = e;
            });
            return returnObject[0];
        }
    }

    public Seqf formats = (Seqf) Seqf.with(
        new DataFormat("directional") {{
            convert = new Arrayf<double[], DataFormat, double[]>() {
                @Override
                public double[] get(DataFormat param1, double[] param2) {
                    double[] returnArray = new double[]{0.0, 0.0, 0.0, 0.0};
                    switch (param1.name) {
                        case "rotational":
                            Tmp.v1.trns((float) param2[1], (float) param2[0]);
                            returnArray[0] = Tmp.v1.x;
                            returnArray[1] = Tmp.v1.y;
                            returnArray[2] = param2[2];
                            returnArray[3] = param2[3];
                        default:
                            returnArray = param2;
                    }
                    return returnArray;
                }
            };
        }},
        new DataFormat("rotational") {{

        }}
    );

}
