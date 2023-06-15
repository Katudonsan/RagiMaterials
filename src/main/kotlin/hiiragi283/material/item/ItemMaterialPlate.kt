package hiiragi283.material.item

import hiiragi283.material.init.RMItems
import hiiragi283.material.material.HiiragiMaterial
import hiiragi283.material.material.MaterialRegistry
import hiiragi283.material.part.PartRegistry
import hiiragi283.material.util.CraftingBuilder
import hiiragi283.material.util.RagiIngredient
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary

object ItemMaterialPlate : ItemMaterialBase(PartRegistry.PLATE) {

    override fun isMatch(material: HiiragiMaterial): Boolean = super.isMatch(material) && material.isMetal()

    //    IRMEntry    //

    override fun registerRecipe() {
        MaterialRegistry.getMaterials()
            .filter { isMatch(it) }
            .forEach {
                CraftingBuilder(ItemStack(this, 1, it.index))
                    .addIngredient(RagiIngredient("ingot${it.getOreDictName()}"))
                    .addIngredient(RagiIngredient(ItemStack(RMItems.FORGE_HAMMER, 1, OreDictionary.WILDCARD_VALUE)))
                    .buildShapeless()
            }
    }

}