package hiiragi283.ragi_materials.tile

import hiiragi283.ragi_materials.RagiMaterials
import hiiragi283.ragi_materials.api.capability.EnumIOType
import hiiragi283.ragi_materials.api.capability.item.RagiItemHandler
import hiiragi283.ragi_materials.api.capability.item.RagiItemHandlerWrapper
import hiiragi283.ragi_materials.container.ContainerLaboTable
import hiiragi283.ragi_materials.proxy.CommonProxy
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class TileLaboBase(type: Int) : TileItemHandlerBase(type) {

    val inputs = object : RagiItemHandler(5) {

        override fun getIOType() = EnumIOType.INPUT

        override fun onContentsChanged(slot: Int) {
            markDirty() //クライアント側にNBTタグを送る
        }
    }
    val catalyst = object : RagiItemHandler(1) {

        override fun getIOType() = EnumIOType.CATALYST

        override fun onContentsChanged(slot: Int) {
            markDirty() //クライアント側にNBTタグを送る
        }
    }
    val inventory = RagiItemHandlerWrapper(inputs, catalyst)

    //    NBT tag    //

    override fun writeToNBT(tag: NBTTagCompound): NBTTagCompound {
        super.writeToNBT(tag)
        tag.setTag(keyInventory, inventory.serializeNBT())
        return tag
    }

    override fun readFromNBT(tag: NBTTagCompound) {
        super.readFromNBT(tag)
        inventory.deserializeNBT(tag.getCompoundTag(keyInventory))
    }

    //    ITileActivatable    //

    override fun onTileActivated(world: World, pos: BlockPos, player: EntityPlayer, hand: EnumHand, facing: EnumFacing): Boolean {
        if (!world.isRemote) player.openGui(RagiMaterials.INSTANCE, CommonProxy.TileID, world, pos.x, pos.y, pos.z)
        return true
    }

    //    TileItemHandlerBase    //

    override fun createContainer(playerInventory: InventoryPlayer, player: EntityPlayer) = ContainerLaboTable(player, this)


}