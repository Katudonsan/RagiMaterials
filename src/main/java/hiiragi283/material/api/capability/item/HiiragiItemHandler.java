package hiiragi283.material.api.capability.item;

import hiiragi283.material.api.capability.IOControllable;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class HiiragiItemHandler extends ItemStackHandler implements IOControllable {

    @NotNull
    protected final IOControllable.Type ioType;
    @Nullable
    protected final TileEntity tile;

    public HiiragiItemHandler(int size) {
        this(size, Type.GENERAL);
    }

    public HiiragiItemHandler(int size, IOControllable.Type ioType) {
        this(size, ioType, null);
    }

    public HiiragiItemHandler(int size, IOControllable.Type ioType, @Nullable TileEntity tile) {
        super(size);
        this.ioType = ioType;
        this.tile = tile;
    }

    @Override
    protected void onContentsChanged(int slot) {
        if (tile != null) {
            tile.markDirty();
        }
    }

    //    Custom    //

    public void clear() {
        this.stacks = NonNullList.withSize(this.getSlots(), ItemStack.EMPTY);
    }

    public boolean isEmpty() {
        return this.stacks.stream().allMatch(ItemStack::isEmpty);
    }

    //    IOControllable    //

    @NotNull
    @Override
    public Type getIOType() {
        return ioType;
    }

}