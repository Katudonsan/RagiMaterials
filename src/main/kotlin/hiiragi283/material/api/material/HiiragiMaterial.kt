package hiiragi283.material.api.material

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import hiiragi283.material.RagiMaterials
import hiiragi283.material.api.fluid.MaterialFluid
import hiiragi283.material.api.machine.MachineProperty
import hiiragi283.material.api.part.HiiragiPart
import hiiragi283.material.api.part.PartConvertible
import hiiragi283.material.api.registry.HiiragiEntry
import hiiragi283.material.api.shape.HiiragiShape
import hiiragi283.material.api.shape.HiiragiShapeType
import hiiragi283.material.init.HiiragiRegistries
import hiiragi283.material.init.HiiragiShapeTypes
import hiiragi283.material.init.HiiragiShapes
import hiiragi283.material.util.HiiragiJsonSerializable
import hiiragi283.material.util.getTileImplemented
import hiiragi283.material.util.itemStack
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import rechellatek.snakeToUpperCamelCase

/**
 * An object which contains several properties of material
 *
 *   === Index Range ===
 *
 *            <= -1 ... Not registered
 *
 *          1 ~ 118 ... Periodic Table
 *
 *        120 ~ 199 ... Isotopes
 *
 *      1000 ~ 1900 ... Integration for other mods
 *
 *    10010 ~ 11899 ... Compounds: 1 + Atomic Number + Index
 *
 *         >= 32768 ... Not registered
 *
 * @param name should be unique
 * @param index follows above format
 * @param color see also [hiiragi283.material.util.HiiragiColor]
 * @param formula empty value will be ignored
 * @param molar 0 or less value will be ignored
 * @param shapeType determine the type of this material
 * @param tempBoil boiling point with kelvin Temperature, 0 or less will be ignored
 * @param tempMelt melting point with kelvin Temperature, 0 or less will be ignored
 * @param translationKey can be overridden
 */
data class HiiragiMaterial(
    val name: String,
    val index: Int,
    var color: Int = 0xFFFFFF,
    var formula: String = "",
    var molar: Double = 0.0,
    var shapeType: HiiragiShapeType = HiiragiShapeTypes.INTERNAL,
    var tempBoil: Int = 0,
    var tempMelt: Int = 0,
    var translationKey: String = "hiiragi_material.$name"
) : HiiragiJsonSerializable {

    val oreDictAlt: MutableList<String> = mutableListOf()
    var fluidBlock: (Fluid) -> Block? = { null }
    var fluidSupplier: () -> Fluid? = { MaterialFluid(this) }
    var machineProperty: MachineProperty? = null

    fun addBracket() = copy(formula = "($formula)")

    fun addTooltip(tooltip: MutableList<String>, shape: HiiragiShape) {
        addTooltip(tooltip, shape.getTranslatedName(this), shape.scale)
    }

    fun addTooltip(tooltip: MutableList<String>, name: String, scale: Int) {
        tooltip.add(I18n.format("tips.ragi_materials.property.name", name))
        tooltip.add("§e=== Property ===")
        if (hasFormula())
            tooltip.add(I18n.format("tips.ragi_materials.property.formula", formula))
        if (hasMolar())
            tooltip.add(I18n.format("tips.ragi_materials.property.mol", molar))
        if (scale > 0)
            tooltip.add(I18n.format("tips.ragi_materials.property.scale", scale))
        if (hasTempMelt())
            tooltip.add(I18n.format("tips.ragi_materials.property.melt", tempMelt))
        if (hasTempBoil())
            tooltip.add(I18n.format("tips.ragi_materials.property.boil", tempBoil))
    }

    fun createFluid() {
        val fluid: Fluid = fluidSupplier() ?: return
        if (FluidRegistry.isFluidRegistered(name)) return
        FluidRegistry.registerFluid(fluid)
        FluidRegistry.addBucketForFluid(fluid)
        fluidBlock(fluid)?.let { block: Block ->
            fluid.block = block
            (block as? HiiragiEntry.BLOCK)?.register()
            FluidRegistry.registerFluid(fluid)
        }
    }

    fun getFluid(): Fluid? = if (FluidRegistry.isFluidRegistered(name)) FluidRegistry.getFluid(name) else null

    fun getFluids(): List<Fluid> {
        val list: MutableList<Fluid> = listOfNotNull(getFluid()).toMutableList()
        list.addAll(oreDictAlt.mapNotNull(FluidRegistry::getFluid))
        return list
    }

    fun getFluidStack(amount: Int = 1000): FluidStack? = FluidRegistry.getFluidStack(name, amount)

    fun getItemStack(shape: HiiragiShape, count: Int = 1): ItemStack? =
        HiiragiRegistries.MATERIAL_ITEM.getValue(shape)?.item()?.itemStack(this, count)

    fun getItemStacks(count: Int = 1): List<ItemStack> = HiiragiRegistries.SHAPE.getValues()
        .map(::getPart)
        .flatMap { it.getItemStacks(count) }

    fun getOreDictName(): String = name.snakeToUpperCamelCase()

    fun getOreDictNameAlt(): List<String> = oreDictAlt.map(String::snakeToUpperCamelCase)

    fun getPart(shape: HiiragiShape): HiiragiPart = HiiragiPart(shape, this)

    fun getTranslatedName(): String = I18n.format(translationKey)

    fun hasOreDictAlt(): Boolean = oreDictAlt.isNotEmpty()

    fun hasFluid(): Boolean = FluidRegistry.isFluidRegistered(name)

    fun hasFormula(): Boolean = formula.isNotEmpty()

    fun hasMolar(): Boolean = molar > 0.0

    fun hasTempBoil(): Boolean = tempBoil > 0

    fun hasTempMelt(): Boolean = tempMelt > 0

    fun isGem(): Boolean = HiiragiShapes.GEM.isValid(this)

    fun isMetal(): Boolean = HiiragiShapes.METAL.isValid(this)

    fun isFluid(): Boolean = isGas() || isLiquid()

    fun isGas(): Boolean = HiiragiShapes.GAS.isValid(this)

    fun isLiquid(): Boolean = HiiragiShapes.LIQUID.isValid(this)

    fun isValidIndex(): Boolean = index >= 0

    fun isSolid(): Boolean = HiiragiShapes.SOLID.isValid(this)

    fun toMaterialStack(amount: Int = 144): MaterialStack = MaterialStack(this, amount)

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HiiragiMaterial -> false
        else -> other.name == this.name
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + index
        result = 31 * result + color
        result = 31 * result + formula.hashCode()
        result = 31 * result + molar.hashCode()
        result = 31 * result + shapeType.hashCode()
        result = 31 * result + tempBoil
        result = 31 * result + tempMelt
        result = 31 * result + translationKey.hashCode()
        return result
    }

    override fun toString(): String = "Material:$name"

    companion object {

        @JvmField
        val UNKNOWN = formulaOf("?")

        @JvmField
        val WILDCARD = materialOf("wildcard", Short.MAX_VALUE.toInt())

        @JvmField
        val RUSSELL = materialOf("russell", 0) {
            color = RagiMaterials.COLOR.rgb
            shapeType = HiiragiShapeTypes.WILDCARD
        }

        @JvmField
        val BLOCK_COLOR: IBlockColor = IBlockColor { _: IBlockState, world: IBlockAccess?, pos: BlockPos?, _: Int ->
            getTileImplemented<PartConvertible.TILE>(world, pos)?.material?.color ?: -1
        }

        @JvmField
        val ITEM_COLOR: IItemColor = IItemColor { stack: ItemStack, _: Int ->
            HiiragiRegistries.MATERIAL_INDEX.getValue(stack.metadata)?.color ?: -1
        }

    }

    //    Registration    //

    fun register() {
        HiiragiRegistries.MATERIAL.register(name, this)
        if (!isValidIndex()) {
            RagiMaterials.LOGGER.error("$this has invalid index: $index !!")
            return
        }
        HiiragiRegistries.MATERIAL_INDEX.register(index, this)
    }

    //    HiiragiJsonSerializable    //

    override fun getJsonElement(): JsonElement {

        val root = JsonObject()

        root.addProperty("name", name)
        root.addProperty("index", index)

        root.addProperty("color", color)
        root.addProperty("formula", formula)

        val fluid: Fluid? = fluidSupplier()
        if (fluid == null) {
            root.addProperty("has_fluid", false)
            root.addProperty("has_fluid_block", false)
        } else {
            root.addProperty("has_fluid", true)
            if (fluidBlock(fluid) != null) {
                root.addProperty("has_fluid_block", true)
            }
        }

        root.add("machineProperty", machineProperty?.getJsonElement())
        root.addProperty("molar", molar)

        val oreDictAltJson = JsonArray()
        oreDictAlt.forEach(oreDictAltJson::add)
        root.add("oreDictAlt", oreDictAltJson)

        root.add("shapeType", shapeType.getJsonElement())
        root.addProperty("tempBoil", tempBoil)
        root.addProperty("tempMelt", tempMelt)

        return root

    }

}