package dev.qweru.mcu.mixin;

import dev.qweru.mcu.mods.ChatHandler;
import dev.qweru.mcu.util.Chat;
import dev.qweru.mcu.util.DataDump;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.s2c.play.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    private static final DataDump packetDump = new DataDump(FabricLoader.getInstance().getConfigDir().toString()+"\\packetDump.txt");

    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void onHandlePacket(Packet<T> packet, PacketListener listener, CallbackInfo ci) {
        if(ChatHandler.chatInfo){
            if(packet instanceof ResourcePackSendS2CPacket res) {
                String logString = "Resource pack received! { data: { URL: "+res.getURL()+", SHA1: "+res.getSHA1()+", PROMPT: "+res.getPrompt()+" } }";
                Chat.sendMessage(logString);
            } else if (packet instanceof WorldBorderCenterChangedS2CPacket wbp) {
                Chat.sendMessage("World border's center changed to "+wbp.getCenterX()+", "+wbp.getCenterZ());
            } else if (packet instanceof WorldBorderSizeChangedS2CPacket wbsp) {
                Chat.sendMessage("World border's size changed to "+wbsp.getSizeLerpTarget());
            } else if(packet instanceof WorldBorderInitializeS2CPacket wbip) {
                Chat.sendMessage("World border initialised at x: "+wbip.getCenterX()+", z: "+wbip.getCenterZ()+", with size of: "+wbip.getSizeLerpTarget());
            } else if (packet instanceof EntitySpawnS2CPacket esp&&ChatHandler.logEntities) {
                Chat.sendMessage("An entity has spawned at "+esp.getX()+", "+esp.getY()+", "+esp.getZ());
            } else if (packet instanceof PlayerSpawnS2CPacket esp&&ChatHandler.logEntities) {
                Chat.sendMessage("A player has spawned at "+esp.getX()+", "+esp.getY()+", "+esp.getZ());
            } else if (packet instanceof EntitiesDestroyS2CPacket edp&&ChatHandler.logEntities) {
                Chat.sendMessage("An entity was removed (id: "+edp.getEntityIds()+")");
            } else if(ChatHandler.doPacketDumps){
                packetDump.addData(packet.toString());
            } else if (packet instanceof EnterCombatS2CPacket ecp) {
                Chat.sendMessage("Entered combat");
            } else if (packet instanceof EndCombatS2CPacket ecp) {
                Chat.sendMessage("Ended combat");
            } else if (packet instanceof PlayerRemoveS2CPacket prp) {
                Chat.sendMessage("Removed players with uuids of "+prp.toString());
            } else if (packet instanceof EntityPositionS2CPacket epp&&ChatHandler.logEntities) {
                Chat.sendMessage("Entity move event (coordinates: "+epp.getX()+", "+epp.getY()+", "+epp.getZ()+")");
            } else if (packet instanceof PlayerRespawnS2CPacket repack) {
                Chat.sendMessage("Respawn packet with info: { dimension: "+repack.getDimension().getValue()+", gamemode: "+repack.getGameMode().getName()+", death location: "+repack.getLastDeathPos().toString()+", previous gamemode: "+ Objects.requireNonNull(repack.getPreviousGameMode()).getName());
            } else if (packet instanceof ExplosionS2CPacket ep) {
                Chat.sendMessage("Explosion at "+ep.getX()+", "+ep.getY()+", "+ep.getZ()+", with a radius of "+ep.getRadius());
            }
        }
        if(ChatHandler.doAnimationCancel){
            if (packet instanceof EntityAnimationS2CPacket) {
                ci.cancel();
            } else if (packet instanceof ItemPickupAnimationS2CPacket) {
                ci.cancel();
            }
        }
    }
}