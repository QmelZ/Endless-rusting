package rusting.entities.units;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.UnitEntity;

public class AntiquimGuardianUnitEntity extends UnitEntity {
    public float iframes = 0;

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void write(Writes w) {
        super.write(w);
        w.f(iframes);
    }

    @Override
    public void read(Reads r) {
        super.read(r);
        iframes = r.f();
    }

    @Override
    public String toString() {
        return "NativeGuardianUnitEntity#" + id;
    }
}
