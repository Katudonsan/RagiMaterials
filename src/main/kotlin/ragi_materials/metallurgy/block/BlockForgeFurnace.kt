package ragi_materials.metallurgy.block

import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import ragi_materials.core.block.BlockContainerHoldable
import ragi_materials.core.block.property.RagiProperty
import ragi_materials.core.util.RagiFacing
import ragi_materials.metallurgy.tile.TileForgeFurnace

object BlockForgeFurnace : BlockContainerHoldable<TileForgeFurnace>("forge_furnace", Material.ROCK, TileForgeFurnace::class.java, 3) {

    init {
        blockHardness = 5.0F
        blockResistance = 5.0F
        defaultState = blockState.baseState.withProperty(RagiProperty.HORIZONTAL, EnumFacing.NORTH)
        setHarvestLevel("pickaxe", 0)
        soundType = SoundType.STONE
    }

    //    General    //

    @Deprecated("Deprecated in Java")
    override fun addCollisionBoxToList(state: IBlockState, world: World, pos: BlockPos, entityBox: AxisAlignedBB, collidingBoxes: MutableList<AxisAlignedBB>, entity: Entity?, isActualState: Boolean) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0 / 8, 1.0)) //底面
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0 / 8)) //北
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AxisAlignedBB(0.0, 0.0, 1.0 - (1.0 / 8), 1.0, 1.0, 1.0)) //南
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AxisAlignedBB(1.0 - (1.0 / 8), 0.0, 0.0, 1.0, 1.0, 1.0)) //東
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AxisAlignedBB(0.0, 0.0, 0.0, 1.0 / 8, 1.0, 1.0)) //西
    }

    //    BlockState    //

    override fun createBlockState(): BlockStateContainer = BlockStateContainer(this, RagiProperty.HORIZONTAL)

    override fun getMetaFromState(state: IBlockState): Int = RagiFacing.getMeta(state)

    override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase, hand: EnumHand): IBlockState = this.defaultState.withProperty(RagiProperty.HORIZONTAL, placer.horizontalFacing.opposite)

    @Deprecated("Deprecated in Java", ReplaceWith("blockState.baseState.withProperty(RagiProperty.HORIZONTAL, RagiFacing.getState(meta))", "hiiragi283.ragi_materials.util.RagiFacing", "hiiragi283.ragi_materials.util.RagiFacing"))
    override fun getStateFromMeta(meta: Int): IBlockState = blockState.baseState.withProperty(RagiProperty.HORIZONTAL, RagiFacing.getValue(meta))

}