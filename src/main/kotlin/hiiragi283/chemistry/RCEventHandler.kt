package hiiragi283.chemistry

import hiiragi283.api.item.ICrusherItem
import hiiragi283.api.material.MaterialRegistry
import hiiragi283.api.part.HiiragiPart
import hiiragi283.api.recipe.CrucibleRecipe
import hiiragi283.api.recipe.CrushingRecipe
import hiiragi283.api.registry.HiiragiRegistry
import hiiragi283.api.shape.ShapeRegistry
import hiiragi283.api.tileentity.HiiragiProvider
import hiiragi283.core.util.hiiragiLocation
import hiiragi283.material.RMItems
import hiiragi283.material.RMReference
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry
import rechellatek.camelToSnakeCase

object RCEventHandler {

    //    Registration    //

    @SubscribeEvent
    fun createRegistry(event: RegistryEvent.NewRegistry) {
        HiiragiRegistry.init()
    }

    @SubscribeEvent
    fun registerBlock(event: RegistryEvent.Register<Block>) {
        RCBlocks.register(event.registry)
    }

    @SubscribeEvent
    fun registerItem(event: RegistryEvent.Register<Item>) {
        RCItems.register(event.registry)
    }

    @SubscribeEvent
    fun registerCrucibleRecipe(event: RegistryEvent.Register<CrucibleRecipe>) {
        val registry: IForgeRegistry<CrucibleRecipe> = event.registry
        //るつぼレシピの登録
        MaterialRegistry.getMaterials()
            .filter { it.isMetal() }
            .forEach { material ->
                ShapeRegistry.getShapes()
                    .filter { it.hasScale() }
                    .map { HiiragiPart(it, material) }
                    .forEach {
                        registry.register(
                            CrucibleRecipe(it, material.tempMelt, material.name to (144 * it.shape.scale).toInt())
                                .setRegistryName(RMReference.MOD_ID, it.getOreDict().camelToSnakeCase())
                        )
                    }
            }
    }

    @SubscribeEvent
    fun registerCrushingRecipe(event: RegistryEvent.Register<CrushingRecipe>) {
        val registry: IForgeRegistry<CrushingRecipe> = event.registry
        //粉砕レシピの登録
        MaterialRegistry.getMaterials()
            .filter { it.isSolid() }
            .forEach { material ->
                ShapeRegistry.getShapes()
                    .filter { it.hasScale() && it.scale >= 1.0 }
                    .map { HiiragiPart(it, material) }
                    .forEach {
                        registry.register(
                            CrushingRecipe(
                                it,
                                mapOf(
                                    RMItems.MATERIAL_DUST.getItemStack(it) to 100
                                )
                            ).setRegistryName(RMReference.MOD_ID, it.getOreDict().camelToSnakeCase())
                        )
                    }
            }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun registerBlockColorHandler(event: ColorHandlerEvent.Block) {
        RCBlocks.registerColorBlock(event.blockColors)
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun registerItemColorHandler(event: ColorHandlerEvent.Item) {
        RCBlocks.registerColorItem(event.itemColors)
        RCItems.registerColorItem(event.itemColors)
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun registerModel(event: ModelRegistryEvent) {
        RCBlocks.registerModel()
        RCItems.registerModel()
    }

    //    Gameplay    //

    private val keyInventory = hiiragiLocation("inventory")
    private val keyTank = hiiragiLocation("tank")
    private val keyEnergy = hiiragiLocation("energy")

    @SubscribeEvent
    fun attachCapability(event: AttachCapabilitiesEvent<TileEntity>) {
        val tile: TileEntity = event.`object`
        if (tile is HiiragiProvider.Inventory) event.addCapability(keyInventory, tile.createInventory())
        if (tile is HiiragiProvider.Tank) event.addCapability(keyTank, tile.createTank())
        if (tile is HiiragiProvider.Energy) event.addCapability(keyEnergy, tile.createBattery())
    }

    @SubscribeEvent
    fun onHarvestDrop(event: BlockEvent.HarvestDropsEvent) {
        //爆破や機械による採掘ではnullになるので，それを回避する
        val player: EntityPlayer = event.harvester ?: return
        val stack: ItemStack = player.getHeldItem(player.activeHand)
        if (stack.isEmpty || stack.item !is ICrusherItem) return
        val recipe: CrushingRecipe = HiiragiRegistry.CRUSHING.valuesCollection
            .firstOrNull { it.matches(event.state) } ?: return
        val list: MutableList<ItemStack> = event.drops
        list.clear() //既存のドロップ品を一度リセットする
        list.addAll(recipe.getWeightedOutputs())
    }

}