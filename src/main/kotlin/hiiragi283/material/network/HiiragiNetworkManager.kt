package hiiragi283.material.network

import hiiragi283.material.RMReference
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.relauncher.Side

val HiiragiNetworkWrapper: SimpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(RMReference.MOD_ID)

object HiiragiNetworkManager {

    fun load() {
        //パケットの登録
        HiiragiNetworkWrapper.registerMessage(
            HiiragiMessageHandler.ToClient::class.java,
            HiiragiMessage.ToClient::class.java,
            0,
            Side.CLIENT
        )
        /*HiiragiNetworkWrapper.registerMessage(
            HiiragiMessageHandler.ToServer::class.java,
            HiiragiMessage.ToClient::class.java,
            1,
            Side.SERVER
        )*/

    }

}