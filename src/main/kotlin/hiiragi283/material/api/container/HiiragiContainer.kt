package hiiragi283.material.api.container

import hiiragi283.material.api.tile.HiiragiTileEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot

abstract class HiiragiContainer<T : HiiragiTileEntity>(val tile: T, val player: EntityPlayer) : Container() {

    val inventoryPlayer: InventoryPlayer = player.inventory

    protected fun getSlotPositionX(index: Int): Int = 8 + 18 * index

    protected fun getSlotPositionY(index: Int): Int = 18 * (index + 1)

    override fun canInteractWith(playerIn: EntityPlayer): Boolean = true

    protected fun initSlotsPlayer(posY: Int) {
        //プレイヤーのインベントリのスロットを設定
        (0 .. 2).forEach { y: Int ->
            (0 .. 8).forEach { x: Int ->
                addSlotToContainer(Slot(inventoryPlayer, x + y * 9 + 9, 8 + x * 18, y * 18 + posY))
            }
        }
        //プレイヤーのホットバーのスロットを設定
        (0..8).forEach { x: Int ->
            addSlotToContainer(Slot(inventoryPlayer, x, 8 + x * 18, 3 * 18 + (posY + 4)))
        }
    }

}