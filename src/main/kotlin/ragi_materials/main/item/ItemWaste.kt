package ragi_materials.main.item

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import ragi_materials.core.RagiMaterials
import ragi_materials.core.item.ItemBase
import ragi_materials.core.material.IMaterialItem
import ragi_materials.core.material.MaterialRegistry
import ragi_materials.core.material.RagiMaterial

object ItemWaste : ItemBase(RagiMaterials.MOD_ID, "waste", 0), IMaterialItem {

    init {
        creativeTab = CreativeTabs.MISC
    }

    //    General    //

    //    Event    //

    override fun onUpdate(stack: ItemStack, world: World, entity: Entity, slot: Int, isSelected: Boolean) {
        //サーバー側の場合，かつ5秒ごと
        if (!world.isRemote && world.worldInfo.worldTime % 100 == 0L) {
            //entityがplayerの場合
            if (entity is EntityPlayer) {
                when (stack.metadata) {
                    //化学廃棄物 -> 毒デバフ
                    0 -> entity.addPotionEffect(PotionEffect(ForgeRegistries.POTIONS.getValue(ResourceLocation("minecraft:poison"))!!, 110, 0))
                    else -> {}
                }
            }
        }
    }

    //    Client    //

    @SideOnly(Side.CLIENT)
    override fun getSubItems(tab: CreativeTabs, subItems: NonNullList<ItemStack>) {
        if (this.isInCreativeTab(tab)) {
            for (i in 0..this.maxMeta) {
                subItems.add(ItemStack(this, 1, i))
            }
        }
    }

    //    IMaterialItem    //

    override fun getMaterial(stack: ItemStack): RagiMaterial = MaterialRegistry.SOUL_SAND

    override fun setMaterial(stack: ItemStack, material: RagiMaterial): ItemStack = stack

}