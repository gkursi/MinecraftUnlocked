package dev.qweru.mcu.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Chat {

    public static void sendRCMessage(String msg){
        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.sendMessage(Text.of(msg));
        }
    }
    public static void sendMessage(String msg){
        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.sendMessage(Text.of("§d[Minecraft Unlocked]§3 "+msg));
        }
    }

    public static void sendErrorMessage(String msg){
        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.sendMessage(Text.of("§d[Minecraft Unlocked] §5[§cERR§5]§3 "+msg));
        }
    }
}
