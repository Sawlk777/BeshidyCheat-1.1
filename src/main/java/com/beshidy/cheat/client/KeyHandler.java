package com.beshidy.cheat.client;
import com.beshidy.cheat.gui.GuiScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "beshidycheat", value = Dist.CLIENT)
public class KeyHandler {
    public static KeyMapping keyMenu = new KeyMapping("key.beshidy.menu", GLFW.GLFW_KEY_M, "key.beshidy.category");
    public static KeyMapping keyKillaura = new KeyMapping("key.beshidy.killaura", GLFW.GLFW_KEY_K, "key.beshidy.category");
    public static KeyMapping keyBypass = new KeyMapping("key.beshidy.bypass", GLFW.GLFW_KEY_R, "key.beshidy.category");

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(keyMenu);
        event.register(keyKillaura);
        event.register(keyBypass);
    }
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (keyMenu.consumeClick()) Minecraft.getInstance().setScreen(new GuiScreen());
        if (keyKillaura.consumeClick()) ClientState.killauraEnabled = !ClientState.killauraEnabled;
        if (keyBypass.consumeClick()) {
            ClientState.bypassMode = (ClientState.bypassMode + 1) % 3;
            String mode = switch (ClientState.bypassMode) {
                case 0 -> "OFF";
                case 1 -> "Funtime";
                case 2 -> "HolyWorld";
                default -> "?";
            };
            Minecraft.getInstance().gui.getChat().addMessage(net.minecraft.network.chat.Component.literal("[BESHIDY] Bypass: " + mode));
        }
    }
}