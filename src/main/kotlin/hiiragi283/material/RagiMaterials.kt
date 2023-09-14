package hiiragi283.material

import hiiragi283.material.api.fluid.MaterialFluid
import hiiragi283.material.api.registry.HiiragiRegistries
import hiiragi283.material.compat.RMIntegrationCore
import hiiragi283.material.config.RMConfig
import hiiragi283.material.config.RMJSonHandler
import hiiragi283.material.network.HiiragiNetworkManager
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.awt.Color
import java.util.*

@Mod(
    modid = RMReference.MOD_ID,
    name = RMReference.MOD_NAME,
    version = RMReference.VERSION,
    dependencies = "after-required:forgelin;after:gregtech;after:jei@[4.24.5,)",
    acceptedMinecraftVersions = "[1.12,1.12.2]",
    modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter"
    //modLanguageAdapter = "io.github.chaosunity.forgelin.KotlinAdapter"
)
object RagiMaterials : HiiragiProxy {

    //各種変数の宣言
    internal val CALENDAR: Calendar = Calendar.getInstance()
    internal val COLOR: Color = Color(255, 0, 31)
    internal val LOGGER: Logger = LogManager.getLogger(RMReference.MOD_NAME)

    init {
        if (Loader.isModLoaded("gregtech")) {
            throw RuntimeException(
                "\n" +
                        "=====================================================\n" +
                        "RagiMaterials detected GregTech in this environment!!\n" +
                        "Remove RagiMaterials or GregTech from mods folder\n" +
                        "====================================================="
            )
        }
    }

    @Mod.EventHandler
    override fun onConstruct(event: FMLConstructionEvent) {
        //Universal Bucketを有効化
        FluidRegistry.enableUniversalBucket()
        //Eventを登録
        MinecraftForge.EVENT_BUS.register(HiiragiEventHandler)
        MinecraftForge.EVENT_BUS.register(HiiragiEventHandler.Client)
        //連携の登録
        RMIntegrationCore.onConstruct(event)
    }

    @Mod.EventHandler
    override fun onPreInit(event: FMLPreInitializationEvent) {
        //configから素材を取得
        RMJSonHandler(event).run {
            this.writeJson()
            this.readJson()
        }
        //レジストリへの登録
        HiiragiBlocks.init()
        HiiragiItems.init()
        HiiragiRegistries.registerShape()
        HiiragiRegistries.registerShapeType()
        HiiragiRegistries.registerMaterial()
        MaterialFluid.register()
        //連携の登録
        RMIntegrationCore.onPreInit(event)
    }

    @Mod.EventHandler
    override fun onInit(event: FMLInitializationEvent) {
        //レジストリへの登録
        HiiragiRegistries.registerPart()
        //鉱石辞書の登録
        HiiragiRegistries.BLOCK.registerOreDict()
        HiiragiRegistries.ITEM.registerOreDict()
        //レシピの登録
        HiiragiRegistries.BLOCK.registerRecipe()
        HiiragiRegistries.ITEM.registerRecipe()
        HiiragiRecipes.init()
        //連携の登録
        RMIntegrationCore.onInit(event)
    }

    @Mod.EventHandler
    override fun onPostInit(event: FMLPostInitializationEvent) {
        //連携の登録
        RMIntegrationCore.onPostInit(event)
    }

    @Mod.EventHandler
    override fun onComplete(event: FMLLoadCompleteEvent) {
        //MaterialRegistryからログに出力
        if (RMConfig.MATERIAL.printMaterials) {
            HiiragiRegistries.MATERIAL.getValues().forEach { LOGGER.info(it) }
        }
        //パケット送信の登録
        HiiragiNetworkManager.register()
    }

}