package dev.qweru.mcu.mixin;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChatScreen.class)
public interface ChatScreenAccessor {
    @Mutable
    @Accessor("chatField")
    void setChatField(TextFieldWidget chatField);

    @Mutable
    @Accessor("originalChatText")
    String getOriginalChatText();
}
