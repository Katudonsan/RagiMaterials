package hiiragi283.material.material

import hiiragi283.material.RagiMaterials
import hiiragi283.material.util.RagiColor

object MaterialRegistry {

    private val REGISTRY: HashMap<String, HiiragiMaterial> = hashMapOf()

    @JvmStatic
    fun getMaterials(): Collection<HiiragiMaterial> = REGISTRY.values

    @JvmStatic
    fun getMaterialFromName(name: String) = REGISTRY.getOrDefault(name, HiiragiMaterial.EMPTY)

    @JvmStatic
    fun registerMaterial(material: HiiragiMaterial) {
        val name = material.getName()
        //同じ名前で登録されていた場合，登録せずに警告を表示する
        REGISTRY.putIfAbsent(name, material)
            ?.let { RagiMaterials.LOGGER.warn("The material: $name has already registered!") }
    }

    //    Materials    //

    // ELEMENTS //
    // 1st Period
    @JvmField
    val HYDROGEN = HiiragiMaterial.Builder("hydrogen", 1)
        .setColor(RagiColor.BLUE)
        .setFormula("H")
        .setMolar(1.0)
        .setTempBoil(20)
        .setTempMelt(14)
        .build()

    @JvmField
    val HELIUM = HiiragiMaterial.Builder("helium", 2)
        .setColor(RagiColor.YELLOW)
        .setFormula("He")
        .setMolar(4.0)
        .build()

    // 2nd Period
    @JvmField
    val LITHIUM = HiiragiMaterial.Builder("lithium", 3)
        .setColor(RagiColor.GRAY)
        .setFormula("Li")
        .setMolar(6.9)
        .build()

    @JvmField
    val BERYLLIUM = HiiragiMaterial.Builder("beryllium", 4)
        .setColor(RagiColor.DARK_GREEN)
        .setFormula("Be")
        .setMolar(9.0)
        .build()

    @JvmField
    val BORON = HiiragiMaterial.Builder("boron", 5)
        .setColor(RagiColor.GRAY)
        .setFormula("B")
        .setMolar(10.8)
        .build()

    @JvmField
    val CARBON = HiiragiMaterial.Builder("carbon", 6)
        .setColor(RagiColor.BLACK)
        .setFormula("C")
        .setMolar(12.0)
        .build()

    @JvmField
    val NITROGEN = HiiragiMaterial.Builder("nitrogen", 7)
        .setColor(RagiColor.DARK_AQUA)
        .setFormula("N")
        .setMolar(14.0)
        .build()

    @JvmField
    val OXYGEN = HiiragiMaterial.Builder("oxygen", 8)
        .setColor(RagiColor.AQUA)
        .setFormula("O")
        .setMolar(16.0)
        .build()

    @JvmField
    val FLUORINE = HiiragiMaterial.Builder("fluorine", 9)
        .setColor(RagiColor.BLUE)
        .setFormula("F")
        .setMolar(19.0)
        .build()

    @JvmField
    val NEON = HiiragiMaterial.Builder("neon", 10)
        .setColor(RagiColor.DARK_AQUA)
        .setFormula("Ne")
        .setMolar(20.2)
        .build()

    // 3rd Period
    @JvmField
    val SODIUM = HiiragiMaterial.Builder("sodium", 11)
        .setColor(RagiColor.BLUE)
        .setFormula("Na")
        .setMolar(23.0)
        .build()

    @JvmField
    val MAGNESIUM = HiiragiMaterial.Builder("magnesium", 12)
        .setColor(RagiColor.GRAY)
        .setFormula("Mg")
        .setMolar(24.3)
        .build()

    @JvmField
    val ALUMINIUM = HiiragiMaterial.Builder("aluminium", 13)
        .setColor(RagiColor.AQUA)
        .setFormula("Al")
        .setMolar(27.0)
        .build()

    @JvmField
    val SILICON = HiiragiMaterial.Builder("silicon", 14)
        .setColor(RagiColor.DARK_GRAY)
        .setFormula("Si")
        .setMolar(28.1)
        .build()

    @JvmField
    val PHOSPHORUS = HiiragiMaterial.Builder("phosphorus", 15)
        .setColor(RagiColor.WHITE)
        .setFormula("P")
        .setMolar(31.0)
        .build()

    @JvmField
    val SULFUR = HiiragiMaterial.Builder("sulfur", 16)
        .setColor(RagiColor.YELLOW)
        .setFormula("S")
        .setMolar(32.1)
        .build()

    @JvmField
    val CHLORINE = HiiragiMaterial.Builder("chlorine", 17)
        .setColor(RagiColor.YELLOW)
        .setFormula("Cl")
        .setMolar(35.5)
        .build()

    @JvmField
    val ARGON = HiiragiMaterial.Builder("argon", 18)
        .setColor(RagiColor.GREEN)
        .setFormula("Ar")
        .setMolar(40.0)
        .build()

    // 4th Period
    @JvmField
    val POTASSIUM = HiiragiMaterial.Builder("potassium", 19)
        .setColor(RagiColor.GRAY)
        .setFormula("K")
        .setMolar(39.1)
        .build()

    @JvmField
    val CALCIUM = HiiragiMaterial.Builder("calcium", 20)
        .setColor(RagiColor.GRAY)
        .setFormula("Ca")
        .setMolar(40.1)
        .build()

    @JvmField
    val SCANDIUM = HiiragiMaterial.Builder("scandium", 21)
        .setColor(RagiColor.GRAY)
        .setFormula("Sc")
        .setMolar(45.0)
        .build()

    @JvmField
    val TITANIUM = HiiragiMaterial.Builder("titanium", 22)
        .setColor(RagiColor.LIGHT_PURPLE)
        .setFormula("Ti")
        .setMolar(47.9)
        .build()

    @JvmField
    val VANADIUM = HiiragiMaterial.Builder("vanadium", 23)
        .setColor(RagiColor.DARK_GRAY)
        .setFormula("V")
        .setMolar(50.9)
        .build()

    @JvmField
    val CHROMIUM = HiiragiMaterial.Builder("chromium", 24)
        .setColor(RagiColor.GRAY)
        .setFormula("Cr")
        .setMolar(52.0)
        .build()

    @JvmField
    val MANGANESE = HiiragiMaterial.Builder("manganese", 25)
        .setColor(RagiColor.GRAY)
        .setFormula("Mn")
        .setMolar(54.9)
        .build()

    @JvmField
    val IRON = HiiragiMaterial.Builder("iron", 26)
        .setColor(RagiColor.GRAY)
        .setFormula("Fe")
        .setMolar(55.8)
        .build()

    @JvmField
    val COBALT = HiiragiMaterial.Builder("cobalt", 27)
        .setColor(RagiColor.BLUE)
        .setFormula("Co")
        .setMolar(58.9)
        .build()

    @JvmField
    val NICKEL = HiiragiMaterial.Builder("nickel", 28)
        .setColor(RagiColor.GRAY)
        .setFormula("Ni")
        .setMolar(58.7)
        .build()

    @JvmField
    val COPPER = HiiragiMaterial.Builder("copper", 29)
        .setColor(RagiColor.GOLD)
        .setFormula("Cu")
        .setMolar(63.5)
        .build()

    @JvmField
    val ZINC = HiiragiMaterial.Builder("zinc", 30)
        .setColor(RagiColor.GRAY)
        .setFormula("Zn")
        .setMolar(65.4)
        .build()

    @JvmField
    val GALLIUM = HiiragiMaterial.Builder("gallium", 31)
        .setColor(RagiColor.GRAY)
        .setFormula("Ga")
        .setMolar(69.7)
        .build()

    @JvmField
    val GERMANIUM = HiiragiMaterial.Builder("germanium", 32)
        .setColor(RagiColor.GRAY)
        .setFormula("Ge")
        .setMolar(72.6)
        .build()

    @JvmField
    val ARSENIC = HiiragiMaterial.Builder("arsenic", 33)
        .setColor(RagiColor.GRAY)
        .setFormula("As")
        .setMolar(74.9)
        .build()

    @JvmField
    val SELENIUM = HiiragiMaterial.Builder("selenium", 34)
        .setColor(RagiColor.BLACK)
        .setFormula("Se")
        .setMolar(74.9)
        .build()

    @JvmField
    val BROMINE = HiiragiMaterial.Builder("bromine", 35)
        .setColor(RagiColor.GOLD)
        .setFormula("Br")
        .setMolar(79.9)
        .build()

    @JvmField
    val KRYPTON = HiiragiMaterial.Builder("krypton", 36)
        .setColor(RagiColor.DARK_GREEN)
        .setFormula("Kr")
        .setMolar(83.8)
        .build()

    // 5th Period
    @JvmField
    val RUBIDIUM = HiiragiMaterial.Builder("rubidium", 37)
        .setColor(RagiColor.GRAY)
        .setFormula("Rb")
        .setMolar(85.5)
        .build()

    @JvmField
    val STRONTIUM = HiiragiMaterial.Builder("strontium", 38)
        .setColor(RagiColor.GRAY)
        .setFormula("Sr")
        .setMolar(87.6)
        .build()

    @JvmField
    val YTTRIUM = HiiragiMaterial.Builder("yttrium", 39)
        .setColor(RagiColor.GRAY)
        .setFormula("Y")
        .setMolar(88.9)
        .build()

    @JvmField
    val ZIRCONIUM = HiiragiMaterial.Builder("zirconium", 40)
        .setColor(RagiColor.GRAY)
        .setFormula("Zr")
        .setMolar(91.2)
        .build()

    @JvmField
    val NIOBIUM = HiiragiMaterial.Builder("niobium", 41)
        .setColor(RagiColor.GRAY)
        .setFormula("Nb")
        .setMolar(92.9)
        .build()

    @JvmField
    val MOLYBDENUM = HiiragiMaterial.Builder("molybdenum", 42)
        .setColor(RagiColor.GRAY)
        .setFormula("Mo")
        .setMolar(96.0)
        .build()

    @JvmField
    val TECHNETIUM = HiiragiMaterial.Builder("technetium", 43)
        .setColor(RagiColor.GRAY)
        .setFormula("Tc")
        //.setMolar(97.0/98.0/99.0)
        .build()

    @JvmField
    val RUTHENIUM = HiiragiMaterial.Builder("ruthenium", 44)
        .setColor(RagiColor.GRAY)
        .setFormula("Ru")
        .setMolar(101.1)
        .build()

    @JvmField
    val RHODIUM = HiiragiMaterial.Builder("rhodium", 45)
        .setColor(RagiColor.GRAY)
        .setFormula("Rh")
        .setMolar(102.9)
        .build()

    @JvmField
    val PALLADIUN = HiiragiMaterial.Builder("palladium", 46)
        .setColor(RagiColor.GRAY)
        .setFormula("Pd")
        .setMolar(106.4)
        .build()

    @JvmField
    val SILVER = HiiragiMaterial.Builder("silver", 47)
        .setColor(RagiColor.GRAY)
        .setFormula("Ag")
        .setMolar(107.9)
        .build()

    @JvmField
    val CADMIUM = HiiragiMaterial.Builder("cadmium", 48)
        .setColor(RagiColor.GRAY)
        .setFormula("Cd")
        .setMolar(112.4)
        .build()

    @JvmField
    val INDIUM = HiiragiMaterial.Builder("indium", 49)
        .setColor(RagiColor.GRAY)
        .setFormula("In")
        .setMolar(114.8)
        .build()

    @JvmField
    val TIN = HiiragiMaterial.Builder("tin", 50)
        .setColor(RagiColor.GRAY)
        .setFormula("Sn")
        .setMolar(118.7)
        .build()

    @JvmField
    val ANTIMONY = HiiragiMaterial.Builder("antimony", 51)
        .setColor(RagiColor.GRAY)
        .setFormula("Sb")
        .setMolar(121.8)
        .build()

    @JvmField
    val TELLURIUM = HiiragiMaterial.Builder("tellurium", 52)
        .setColor(RagiColor.GRAY)
        .setFormula("Te")
        .setMolar(127.6)
        .build()

    @JvmField
    val IODINE = HiiragiMaterial.Builder("iodine", 53)
        .setColor(RagiColor.DARK_PURPLE)
        .setFormula("I")
        .setMolar(126.9)
        .build()

    @JvmField
    val XENON = HiiragiMaterial.Builder("xenon", 54)
        .setColor(RagiColor.AQUA)
        .setFormula("Xe")
        .setMolar(131.3)
        .build()

    fun init() {
        // ELEMENTS //
        // 1st Period
        registerMaterial(HYDROGEN)
        registerMaterial(HELIUM)
        // 2nd Period
        registerMaterial(LITHIUM)
        registerMaterial(BERYLLIUM)
        registerMaterial(BORON)
        registerMaterial(CARBON)
        registerMaterial(NITROGEN)
        registerMaterial(OXYGEN)
        registerMaterial(FLUORINE)
        registerMaterial(NEON)
        // 3rd Period
        registerMaterial(SODIUM)
        registerMaterial(MAGNESIUM)
        registerMaterial(ALUMINIUM)
        registerMaterial(SILICON)
        registerMaterial(PHOSPHORUS)
        registerMaterial(SULFUR)
        registerMaterial(CHLORINE)
        registerMaterial(ARGON)
        // 4th Period
        registerMaterial(POTASSIUM)
        registerMaterial(CALCIUM)
        registerMaterial(SCANDIUM)
        registerMaterial(TITANIUM)
        registerMaterial(VANADIUM)
        registerMaterial(CHROMIUM)
        registerMaterial(MANGANESE)
        registerMaterial(IRON)
        registerMaterial(COBALT)
        registerMaterial(NICKEL)
        registerMaterial(COPPER)
        registerMaterial(ZINC)
        registerMaterial(GALLIUM)
        registerMaterial(GERMANIUM)
        registerMaterial(ARSENIC)
        registerMaterial(SELENIUM)
        registerMaterial(BROMINE)
        registerMaterial(KRYPTON)
        // 5th Period
        registerMaterial(RUBIDIUM)
        registerMaterial(STRONTIUM)
        registerMaterial(YTTRIUM)
        registerMaterial(ZIRCONIUM)
        registerMaterial(NIOBIUM)
        registerMaterial(MOLYBDENUM)
        registerMaterial(TECHNETIUM)
        registerMaterial(RUTHENIUM)
        registerMaterial(RHODIUM)
        registerMaterial(PALLADIUN)
        registerMaterial(SILVER)
        registerMaterial(CADMIUM)
        registerMaterial(INDIUM)
        registerMaterial(TIN)
        registerMaterial(ANTIMONY)
        registerMaterial(TELLURIUM)
        registerMaterial(IODINE)
        registerMaterial(XENON)

    }

}