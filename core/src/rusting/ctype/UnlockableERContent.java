package rusting.ctype;

import arc.graphics.g2d.TextureRegion;
import arc.util.Nullable;

public abstract class UnlockableERContent extends MappableERContent{

    /** Localized, formal name. Never null. Set to internal name if not found in bundle. */
    public String localizedName;
    /** Localized description & details. May be null. */
    public @Nullable
    String description, details;
    /** Whether this content is always unlocked. */
    public boolean alwaysUnlocked = false;
    /** Whether to show the description in the research dialog preview. */
    public boolean inlineDescription = true;
    /** Special logic icon ID. */
    public int iconId = 0;
    /** Icon of the content to use in UI. */
    public TextureRegion uiIcon;
    /** Icon of the full content. Unscaled.*/
    public TextureRegion fullIcon;
    /** Unlock state. Loaded from settings. Do not modify outside of the constructor. */
    protected boolean unlocked;

    public UnlockableERContent(String name){
        super(name);
    }

}
