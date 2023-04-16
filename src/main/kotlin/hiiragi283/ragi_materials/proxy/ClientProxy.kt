package hiiragi283.ragi_materials.proxy

import hiiragi283.ragi_materials.client.render.RenderBlazingForge
import hiiragi283.ragi_materials.client.render.RenderLaboratoryTable
import hiiragi283.ragi_materials.event.ClientRegistryEvent
import hiiragi283.ragi_materials.tile.TileBlazingForge
import hiiragi283.ragi_materials.tile.TileLaboTable
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.event.FMLConstructionEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

class ClientProxy : CommonProxy() {

    override fun onConstruct(event: FMLConstructionEvent) {
        super.onConstruct(event)
        MinecraftForge.EVENT_BUS.register(ClientRegistryEvent)
    }

    override fun onPreInit(event: FMLPreInitializationEvent) {
        super.onPreInit(event)
        //TESRの登録
        ClientRegistry.bindTileEntitySpecialRenderer(TileBlazingForge::class.java, RenderBlazingForge)
        ClientRegistry.bindTileEntitySpecialRenderer(TileLaboTable::class.java, RenderLaboratoryTable)
        //TEISRの登録
        //RagiRegistries.ItemBlockIndustrialLabo.tileEntityItemStackRenderer = RagiTEISR
    }
}