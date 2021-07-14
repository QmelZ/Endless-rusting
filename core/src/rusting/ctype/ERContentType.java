package rusting.ctype;
public class ERContentType {


    public int ordinal = 0;
    private static float nextFreeOrdinal = 0;

    public ERContentType(String name){
        this.name = name;
        this.ordinal = 0;
        nextFreeOrdinal++;
    }

    //how you find the ERContentType
    public String name = "UNUSED";

    public String name(){
        return name;
    }
}
