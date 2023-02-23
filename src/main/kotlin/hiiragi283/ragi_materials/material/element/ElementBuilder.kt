package hiiragi283.ragi_materials.material.element

import hiiragi283.ragi_materials.material.MaterialBuilder
import hiiragi283.ragi_materials.material.MaterialType
import java.awt.Color

open class ElementBuilder(
    name: String,
    type: MaterialType.TypeHandler,
    override var color: Color?,
    override var molar: Float?,
    override var formula: String?,
    override var tempMelt: Int?,
    override var tempBoil: Int?
) : MaterialBuilder(-1, name, type) {

    init {
        setTemp()
    }

    private fun setTemp() {
        if (tempMelt == tempBoil) super.tempSubl = tempMelt else {
            super.tempMelt = tempMelt
            super.tempBoil = tempBoil
        }
    }
}