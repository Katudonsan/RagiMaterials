package hiiragi283.material.api.shape

import hiiragi283.material.api.material.HiiragiMaterial
import hiiragi283.material.common.util.commonId
import hiiragi283.material.common.util.hiiragiId
import hiiragi283.material.common.util.itemModelLayered
import net.devtech.arrp.json.blockstate.JState
import net.devtech.arrp.json.models.JModel
import net.devtech.arrp.json.recipe.JRecipe
import net.devtech.arrp.json.recipe.JResult
import net.devtech.arrp.json.recipe.JStackedResult
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import kotlin.math.roundToInt

fun shapeOf(name: String, prefix: String, scale: Double, init: HiiragiShape.() -> Unit = {}): HiiragiShape {
    val shape = HiiragiShape(name, prefix, scale)
    shape.init()
    return shape
}

class HiiragiShape internal constructor(
    val name: String,
    val prefix: String,
    val scale: Double
) {

    var model: JModel = itemModelLayered { layer0("minecraft:item/iron_ingot") }
    var recipes: (HiiragiMaterial) -> Map<Identifier, JRecipe> = { mapOf() }
    var state: JState = JState()
    var translationKey: String = "shape.$name"
    var type: Type = Type.ITEM

    private val replacedName: (HiiragiMaterial) -> String = { prefix.replace("@", it.name) }

    companion object {
        @JvmField
        val EMPTY = HiiragiShape("empty", "", 0.0)
    }

    override fun toString(): String = "Part:$name"

    fun getName(material: HiiragiMaterial): Text = TranslatableText(translationKey, material.getTranslatedName())

    fun getId(material: HiiragiMaterial): Identifier = hiiragiId(replacedName(material))

    fun getTag(material: HiiragiMaterial): Identifier = commonId(replacedName(material))

    fun getResult(material: HiiragiMaterial, count: Int = 1): JStackedResult =
        JResult.stackedResult(getId(material).toString(), count)

    fun getWeight(material: HiiragiMaterial): Double = (material.molar * scale * 10.0).roundToInt() / 10.0

    fun hasScale(): Boolean = scale > 0.0

    fun isEmpty(): Boolean = this.name == "empty"

    fun isValid(material: HiiragiMaterial): Boolean = name in material.validShapes

    enum class Type {
        BLOCK, ITEM
    }

}