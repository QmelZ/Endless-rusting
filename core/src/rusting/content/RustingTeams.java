package rusting.content;

import mindustry.Vars;
import mindustry.ctype.ContentList;
import mindustry.game.Team;

public class RustingTeams implements ContentList {
    public static Team
    antiquumNatives, acrillimyl, pulseInfected, voidInfected;
    @Override
    public void load() {
        Vars.mods.getScripts().runConsole(
        "importPackage(java.util);" +
            "let natives = extend(Team, 113, \"Antiquum Natives\", Color.valueOf(\"#70696c\"), {});" +
            "let acrillimyl = extend(Team, 114, \"Acrillimyl\", Color.valueOf(\"#b6cad6\"), {});" +
            "let pulseInfected = extend(Team, 115, \"Pulse Infected\", Color.valueOf(\"#5c79d0\"), Color.valueOf(\"#646eb2\"), Color.valueOf(\"#6977d6\"), Color.valueOf(\"#8199e1\"), {});" +
            "Object.asign(Team.all[116], {name: \"Void Infected\", color: Color.valueOf(\"#33355b\"), palette: [Color.valueOf(\"#6a5c88\"), Color.valueOf(\"#ac8ac1\"), Color.valueOf(\"#33355b\"))]);"
        );
        antiquumNatives = Team.get(113);
        acrillimyl = Team.get(114);

    }
}
