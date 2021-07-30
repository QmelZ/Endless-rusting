package rusting.ui.dialog.research;

import arc.Core;
import arc.scene.ui.Dialog;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Cell;
import mindustry.Vars;
import rusting.Varsr;
import rusting.ui.dialog.CustomBaseDialog;

public class WelcomingDialog extends CustomBaseDialog {

    private String name = "";
    private Cell button;

    public WelcomingDialog() {
        super(Core.bundle.get("erui.welcomepage"), Core.scene.getStyle(DialogStyle.class), false);
    }

    public void setup(){
        cont.clear();
        cont.pane(table -> {
            table.add(Core.bundle.format("erui.greeting", Vars.player.name().equals("") ? Core.bundle.get("settings.er.username.default") : Vars.player.name()));
            table.row();
            name = Vars.player.name();
            table.table(t -> {
                t.add("@name").padRight(10);
                t.field(Core.settings.getString("name"), text -> {
                    name = text;
                }).pad(8).addInputDialog(Vars.maxNameLength);
            });
            table.row();

            button = table.button(Core.bundle.get("erui.button.comfirm"), () -> {
                Image clearButton = new Image(Core.atlas.find("clear"));
                button.setElement(clearButton);
                Varsr.username = name;
                Core.settings.put("settings.er.username", name);
                table.row();
                String sendOff = Core.bundle.get("erui.defaultsendoff");
                if(name.equals("Sh1penfire")){
                    Core.settings.put("er.debug", true);
                    sendOff = Core.bundle.get("erui.sh1penfire");
                    Varsr.debug = true;
                    Varsr.debug();
                }
                table.add(Core.bundle.format("erui.aftergreeting",name, sendOff));
                addCloseButton();
            }).grow().padTop(60);
        });
    }

    @Override
    public Dialog show() {
        setup();
        return super.show();
    }
}
