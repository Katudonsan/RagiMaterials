package hiiragi283.ragi_materials.util

import hiiragi283.ragi_materials.material.MaterialBuilder
import hiiragi283.ragi_materials.material.MaterialRegistry
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

object RagiNBT {

    //stackに名前を設定するメソッド
    fun setName(stack: ItemStack, name: String?): ItemStack {
        val nbtBase = NBTTagCompound()
        val nbtName = NBTTagCompound()
        nbtName.setString("Name", name.toString())
        nbtBase.setTag("display", nbtName)
        stack.tagCompound = nbtBase
        return stack
    }

    //stackに翻訳した名前を設定するメソッド
    fun setLocName(stack: ItemStack, name: String?): ItemStack {
        val nbtBase = NBTTagCompound()
        val nbtName = NBTTagCompound()
        nbtName.setString("LocName", name.toString())
        nbtBase.setTag("display", nbtName)
        stack.tagCompound = nbtBase
        return stack
    }

    //道具用のNBTタグを生成するメソッド
    fun getTagTool(material: MaterialBuilder): NBTTagCompound {
        val tag = NBTTagCompound()
        tag.setString("material", material.name)
        //materialがmapToolMaterialに含まれている場合
        if (MaterialRegistry.mapToolMaterial.contains(material)) {
            //mapToolMaterialから耐久値を取得する
            tag.setInteger("durability", MaterialRegistry.mapToolMaterial[material]!!)
        }
        //materialがmapToolMaterialに含まれていない場合
        else {
            //EnumMaterials.WILDCARDの耐久値を参照
            tag.setInteger("durability", MaterialRegistry.mapToolMaterial[MaterialRegistry.WILDCARD]!!)
        }
        return tag
    }
}