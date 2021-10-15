package rusting.ui.dialog.research;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.scene.event.ClickListener;
import arc.scene.event.HandCursorListener;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.*;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Scaling;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.ui.Cicon;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock.CoreBuild;
import rusting.Varsr;
import rusting.content.Palr;
import rusting.ctype.ResearchType;
import rusting.interfaces.ResearchCenter;
import rusting.interfaces.ResearchableObject;
import rusting.ui.dialog.CustomBaseDialog;
import rusting.ui.dialog.Texr;

import static mindustry.Vars.mobile;
import static mindustry.Vars.player;

public class ResearchDialog extends CustomBaseDialog{

    private TextField search;
    private Table all = new Table();
    private Table information = new Table();

    public Seq<ResearchType> researchTypes = Seq.with();
    public ObjectMap<String, Seq<ResearchableObject>> categorized = ObjectMap.of();
    public ResearchableObject selected;
    public Seq<ResearchableObject> researchable = new Seq<ResearchableObject>();
    public Seq<String> databaseQuotes = new Seq<String>();
    public Table uiDisplay = new Table();

    public ResearchDialog() {
        super(Core.bundle.get("erui.researchcenter"), Core.scene.getStyle(DialogStyle.class));
        shouldPause = true;
        addCloseButton();

        all.margin(20).marginTop(0f).top().left().setWidth(300);

        information.margin(15).marginTop(0).top().left().setWidth(450);

        setup();
    }

    @Override
    public Dialog show() {
        //split into three different methods to make it easier to tell where something goes wrong
        refresh();
        makeList();
        setup();
        return super.show();
    }

    public void makeList() {
        researchable.clear();

        researchTypes.each(type -> {
            Varsr.research.researchMap.get(type).each(m -> {

                if(researchable.contains(m.item)) return;

                Seq<ResearchType> containing = Seq.with(m.item.researchTypes().toArray());

                researchTypes.each(t -> {
                    if(containing.contains(t)) containing.remove(t);
                });

                if(containing.size <= 0) researchable.add(m.item);
            });
        });
    }

    public void refresh() {
        researchTypes.clear();

        Groups.build.each(b -> {
            if(b.block instanceof ResearchCenter) ((ResearchCenter) b.block).researchTypes().each(r -> {
                if(!researchTypes.contains(r)) researchTypes.add(r);
            });
        });
    }

    public void setup(){

        cont.clear();
        cont.margin(25);
        cont.center();

        rebuildList();

        int size = 650;

        cont.table(s -> {
            s.image(Icon.zoom).padRight(8);
            search = s.field(null, text -> rebuildList()).growX().get();
            search.setMessageText(Core.bundle.get("players.search"));
            search.addInputDialog();
        }).fillX().padTop(4).row();

        //contains the blocks categorized. Rebuild the all table after building this table/updating the text in the searchbar
        cont.table(Texr.button, leftPane -> {

            leftPane.table(t -> {
                t.background(Texr.button);
                t.add("Researchable Items").growX().top().color(Palr.lightstriken);
                t.row();
                t.image().width(210).left().pad(5).padLeft(0).padRight(0).height(4).color(Pal.accent);
            }).top().padTop(0).left();

            leftPane.row();

            leftPane.pane(all).padTop(35).grow().fill();

        }).size(size).left().top().padTop(35).padLeft(15);

        //contains all the extra information for the block/unlocking it
        cont.table(Texr.button, rightPane -> {

            if(selected == null){
                rightPane.add(information);
            }
            else{
                rightPane.pane(information).padTop(35).growX().fillX();
            }

            rebuildInformation();

        }).width(1500 - size - 350).height(size).right().top().padTop(35).padRight(45).padLeft(350);
    }

    public void rebuildList(){

        categorized.clear();

        researchable.each(r -> {

            if(r.hidden() || (search != null && search.getText().length() > 0 && !r.lcoalizedName().toLowerCase().contains(search.getText().toLowerCase()))) return;

            if(!categorized.containsKey(r.categoryName())) categorized.put(r.categoryName(), Seq.with());
            categorized.get(r.categoryName()).add(r);
        });

        all.clear();

        categorized.each((title, types) -> {

            all.table(t -> {
                t.add(title).left().top().color(Pal.accent).left();
                t.row();
                t.image().growX().left().pad(5).padLeft(0).padRight(0).height(4).color(Pal.accent);
            }).top().padTop(0).growX();

            all.row();

            int cols = 9;
            final int[] count = {0};

            all.table(t -> {
                types.each(type -> {


                    Image image = new Image(type.researchUIcon()).setScaling(Scaling.fit);

                    t.add(image).size(45).pad((64 - 45) / 2);
                    ClickListener listener = new ClickListener();
                    image.addListener(listener);

                    image.addListener(new Tooltip(tip -> {
                        tip.background(Texr.button).add((type.researched(Vars.player.team()) ? "The " : "(Locked)\nThe ") + type.lcoalizedName());
                        tip.row();
                    }));

                    if (!mobile) {
                        image.addListener(new HandCursorListener());
                        image.update(() -> image.color.lerp(!listener.isOver() ? (type.researched(Vars.player.team()) ? Color.white : Palr.voidBullet) : Color.lightGray, Mathf.clamp(0.4f * Time.delta)));
                    }

                    image.clicked(() -> {
                        selected = type;
                        rebuildInformation();
                    });

                    if ((++count[0]) % cols == 0) {
                        t.row();
                    }

                });
            });

            all.row();
        });
    }

    public void rebuildInformation(){

        information.clear();

        if(selected == null) {
            information.add("[grey]<select something for more information :D>");
            return;
        }

        information.image(selected.researchUIcon());

        if(!selected.researched(player.team())){

            Tile tile = Varsr.research.getCenter(Seq.with(selected.researchTypes().get(0))).tile;

            ItemStack[] rCost = selected.getResearchModule().centerResearchRequirements;
            Table itemsCost = new Table();

            itemsCost.table(table -> {

                //used for columns.
                int count = 1;
                int cols = Mathf.clamp((Core.graphics.getWidth() - 30) / (32 + 10), 1, 8);

                for(ItemStack costing: rCost) {
                    Image itemImage = new Image(new TextureRegionDrawable().set(costing.item.icon(Cicon.medium))).setScaling(Scaling.fit);

                    table.stack(
                            itemImage,
                            new Table(t -> {
                                t.add(Math.min(tile.build.team.core().items.get(costing.item), costing.amount) + "/" + costing.amount);
                            }).left().margin(1, 3, 2, 0)
                    ).pad(10f);
                    if((count++) % cols == 0) table.row();
                }
            });
            information.pane(table -> {
                table.center();
                table.button("Unlock?", () -> {
                    Building building = tile.build;
                    CoreBuild coreBlock = building.team.core();
                    boolean canResearch = false;

                    //if it's infinite resources or the core has the resources available, continue
                    if(Vars.state.rules.infiniteResources || coreBlock.items.has(rCost, 1)){

                        //remove items from core
                        for(int i = 0; i < selected.getResearchModule().centerResearchRequirements.length; i++){
                            coreBlock.items.remove(selected.getResearchModule().centerResearchRequirements[i]);
                        }

                        //research the block
                        building.configure(selected.name());
                        Sounds.unlock.at(player.x, player.y);
                        rebuildInformation();
                    }
                }).height(75f).width(145);
                table.image(selected.researchUIcon()).size(8 * 12);
                table.row();
                table.add(itemsCost);
            });

        }
    }
}
