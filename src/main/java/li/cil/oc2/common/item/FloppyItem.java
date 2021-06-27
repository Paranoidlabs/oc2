package li.cil.oc2.common.item;

import li.cil.oc2.common.Config;
import net.minecraft.item.IDyeableArmorItem;

public final class FloppyItem extends AbstractStorageItem implements IDyeableArmorItem {
    public FloppyItem(final int capacity) {
        super(capacity);
    }

    @Override
    public int getMaxCapacity() {
        return Config.maxFloppySize;
    }
}
