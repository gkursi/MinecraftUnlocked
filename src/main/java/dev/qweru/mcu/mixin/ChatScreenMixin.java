package dev.qweru.mcu.mixin;

import dev.qweru.mcu.screens.BetterChatScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci){
        MinecraftClient.getInstance().currentScreen.close();
        MinecraftClient.getInstance().setScreen(new BetterChatScreen(""));

    }

    @Inject(method = "init", at = @At("TAIL"), cancellable = true)
    private void init(CallbackInfo ci){

    }
}
