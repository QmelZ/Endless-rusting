package rusting.interfaces;

import arc.util.Nullable;
import mindustry.gen.Building;
import mindustry.gen.Buildingc;

public interface PulseBlockc extends Buildingc {

    float pulseEnergy = 0;
    float falloff = 0;
    float xOffset = 0, yOffset = 0, alphaDraw = 0;
    float shake = 0;

    default void setupValues(){}

    default float pulseEfficiency(){
        return 1;
    }

    default void customConsume(){}

    default boolean customConsumeValid(){
        return false;
    }

    default boolean allConsValid(){
        return false;
    }

    default boolean canRecievePulse(float charge){
        return false;
    }

    default boolean connectableTo(){
        return false;
    }

    default void receivePulse(float pulse, Building source){

    }

    default void addPulse(float pulse){
        addPulse(pulse, null);
    }

    default void addPulse(float pulse, @Nullable Building building){}

    default void removePulse(float pulse){
        removePulse(pulse, null);
    }

    default void removePulse(float pulse, @Nullable Building building){}

    default void normalizePulse(){}

    default void overloadEffect(){}

    default boolean overloaded(){
        return false;
    }

    default float overloadChargef(){
        return 0;
    }

    default float chargef(boolean overloadaccount){
        return 0;
    }

    default float chargef(){
        return chargef(true);
    }

}
