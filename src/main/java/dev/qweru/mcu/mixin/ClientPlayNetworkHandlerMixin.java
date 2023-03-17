package dev.qweru.mcu.mixin;

import dev.qweru.mcu.mods.ChatHandler;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method="sendPacket",at=@At("HEAD"),cancellable = true)
    private void onSendPacket(Packet< ? > p, CallbackInfo ci){

        if(p instanceof ChatMessageC2SPacket cm2s){
            if(ChatHandler.handle(cm2s.chatMessage())) ci.cancel();
        }
    }
}