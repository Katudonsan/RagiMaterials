package ragi_materials.main.block

import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import ragi_materials.core.block.BlockContainerBase
import ragi_materials.core.block.property.RagiProperty
import ragi_materials.main.tile.TileStoneMill

object BlockStoneMill : BlockContainerBase<TileStoneMill>("stone_mill", Material.ROCK, TileStoneMill::class.java, 2) {

    init {
        blockHardness = 5.0F
        blockResistance = 5.0F
        defaultState = blockState.baseState.withProperty(RagiProperty.COUNT8, 0)
        setHarvestLevel("pickaxe", 0)
        soundType = SoundType.STONE
    }

    //    General    //

    @Deprecated("Deprecated in Java", ReplaceWith("false"))
    override fun isFullBlock(state: IBlockState) = false

    @Deprecated("Deprecated in Java", ReplaceWith("false"))
    override fun isOpaqueCube(state: IBlockState) = false

    //    BlockState    //

    override fun createBlockState(): BlockStateContainer = BlockStateContainer(this, RagiProperty.COUNT8)

    override fun getMetaFromState(state: IBlockState): Int = state.getValue(RagiProperty.COUNT8)

    @Deprecated("Deprecated in Java", ReplaceWith("defaultState.withProperty(COUNT, meta / 8)", "ragi_materials.main.BlockStoneMill.Companion.COUNT"))
    override fun getStateFromMeta(meta: Int): IBlockState = defaultState.withProperty(RagiProperty.COUNT8, meta / 8)

    fun getCount(state: IBlockState): Int = state.getValue(RagiProperty.COUNT8)

    fun setCount(state: IBlockState, count: Int): IBlockState = state.withProperty(RagiProperty.COUNT8, count % 8)

    fun addCount(state: IBlockState): IBlockState = state.withProperty(RagiProperty.COUNT8, (getCount(state) + 1) % 8)

}