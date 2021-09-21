package rusting.ui.dialog.research;

import arc.Core;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.world.Tile;
import rusting.Varsr;
import rusting.content.Palr;
import rusting.ctype.ResearchType;
import rusting.interfaces.ResearchCenter;
import rusting.interfaces.ResearchableObject;
import rusting.ui.dialog.CustomBaseDialog;
import rusting.ui.dialog.Texr;

public class ResearchDialog extends CustomBaseDialog{

    public Seq<ResearchableObject> researchable = new Seq<ResearchableObject>();
    public Seq<String> databaseQuotes = new Seq<String>();
    public Table uiDisplay = new Table();

    public ResearchDialog() {
        super(Core.bundle.get("erui.researchcenter"), Core.scene.getStyle(DialogStyle.class));
        addCloseButton();
    }

    public void makeList(Seq<ResearchType> researchTypes) {
        researchable.clear();
        researchTypes.each(type -> {
            Varsr.research.researchMap.get(type).each(m -> {
                researchable.add(m.item);
            });
        });
    }

    public void refresh(Tile tile) {
        if (isShown()) {
            hide();
            show(tile);
        } else if (tile.build != null && tile.build.block instanceof ResearchCenter)
            makeList(((ResearchCenter) tile.build.block).researchTypes());
    }

    public void show(Tile tile) {
        if (!(tile.build instanceof Building && tile.build.block instanceof ResearchCenter)) return;
        makeList(((ResearchCenter) tile.build.block).researchTypes());
        setup();
        super.show();
    }

    public void setup(){

        cont.clear();
        cont.margin(25);
        cont.left();

        int size = 650;

        cont.table(Texr.button, leftPane -> {

            leftPane.table(t -> {
                t.background(Texr.button);
                t.add("Researchable Items").growX().center().color(Palr.lightstriken);
                t.row();
                t.image().width(210).left().pad(5).padLeft(0).padRight(0).height(4).color(Pal.accent);
            }).top().padTop(0).left();

            leftPane.row();

            leftPane.pane(list -> {

                int cols = 4;
                final int[] count = {0};

                researchable.each(type -> {
                    list.add(new Image(type.researchUIcon())).size(45).pad((64 - 45)/2);

                    if ((++count[0]) % cols == 0) {
                        list.row();
                    }
                });


            }).width(300).padTop(35).left();

        }).size(size).left().top().padTop(35).padLeft(15);

    }
}
