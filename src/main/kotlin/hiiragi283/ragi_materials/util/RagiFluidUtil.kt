package hiiragi283.ragi_materials.util

import hiiragi283.ragi_materials.init.RagiItem
import hiiragi283.ragi_materials.material.RagiMaterial
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack

object RagiFluidUtil {

    fun getFluidStack(name: String, amount: Int) = FluidStack(FluidRegistry.getFluid(name), amount)

    fun FluidStack.writeToTag(tag: NBTTagCompound): NBTTagCompound {
        val tagFluid = NBTTagCompound()
        this.writeToNBT(tagFluid)
        tag.setTag("Fluid", tagFluid)
        return tag
    }

    fun getBottle(fluidStack: FluidStack, count: Int = 1) = ItemStack(RagiItem.ItemFullBottle, count, 0).also { it.tagCompound = fluidStack.writeToTag(NBTTagCompound()) }

    fun getBottle(name: String, amount: Int = 1000, count: Int = 1) = getBottle(getFluidStack(name, amount), count)

    fun getBottle(material: RagiMaterial, amount: Int = 1000, count: Int = 1) = getBottle(getFluidStack(material.name, amount), count)

}