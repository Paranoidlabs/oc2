package li.cil.oc2.common.item;

import li.cil.oc2.common.Config;

public final class MemoryItem extends AbstractStorageItem {
    public MemoryItem(final int defaultCapacity) {
        super(defaultCapacity);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected String getOrCreateDescriptionId() {
        return "item.oc2.memory";
    }

    @Override
    public int getMaxCapacity() {
        return Config.maxMemorySize;
    }
}
