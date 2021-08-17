package rusting.ui.dialog.ingame;

import arc.Core;
import arc.scene.ui.Dialog;
import arc.scene.ui.layout.Table;
import rusting.Varsr;
import rusting.ctype.UnlockableAchievement;
import rusting.ui.dialog.CustomBaseDialog;

public class ERAchievementsDialog extends CustomBaseDialog {

    private float achievementOffset = 10;

    public ERAchievementsDialog() {
        super(Core.bundle.get("erui.ahievements"), Core.scene.getStyle(DialogStyle.class), false);
        addCloseListener();
    }

    public void rebuild(){
        cont.clear();
        addCloseListener();
        cont.pane(p -> {
            Varsr.content.achievements().each(a -> {
                p.table(t -> {
                    addAchievement(t, a);
                    achievementOffset++;
                }).padLeft(achievementOffset);
            });
        });
    }

    public void addAchievement(Table table, UnlockableAchievement achievement){
        table.table(t -> {
            t.add(achievement.uiImage).size(64, 64).top().padTop(15);
            t.row();
            t.add(achievement.localizedName);
        }).top();
        table.row();
        table.add(achievement.description);
        table.row();
        table.add(achievement.details);
    }

    @Override
    public Dialog show() {
        rebuild();
        return super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }
}
