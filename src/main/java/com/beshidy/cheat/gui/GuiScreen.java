package com.beshidy.cheat.gui;
import com.beshidy.cheat.client.ClientState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiScreen extends Screen {
    private EditBox reachField;
    private Button bypassButton;
    private Checkbox espCheckbox, killauraCheckbox;
    public GuiScreen() { super(Component.literal("BESHIDY Cheat")); }
    @Override
    protected void init() {
        super.init();
        int x = width/2 - 100, y = 50;
        reachField = new EditBox(font, x, y, 200, 20, Component.literal("Reach"));
        reachField.setValue(String.valueOf(ClientState.reachDistance));
        reachField.setFilter(s -> s.matches("\\d*"));
        addRenderableWidget(reachField);
        bypassButton = Button.builder(Component.literal(getBypassText()), b -> {
            ClientState.bypassMode = (ClientState.bypassMode + 1) % 3;
            b.setMessage(Component.literal(getBypassText()));
        }).bounds(x, y+30, 200, 20).build();
        addRenderableWidget(bypassButton);
        espCheckbox = new Checkbox(x, y+60, 20, 20, Component.literal("ESP"), ClientState.espEnabled);
        killauraCheckbox = new Checkbox(x, y+85, 20, 20, Component.literal("Killaura"), ClientState.killauraEnabled);
        addRenderableWidget(espCheckbox);
        addRenderableWidget(killauraCheckbox);
        addRenderableWidget(Button.builder(Component.literal("Close"), b -> Minecraft.getInstance().setScreen(null)).bounds(x, y+120, 200, 20).build());
    }
    private String getBypassText() {
        return switch (ClientState.bypassMode) {
            case 0 -> "Bypass: OFF";
            case 1 -> "Bypass: Funtime";
            case 2 -> "Bypass: HolyWorld";
            default -> "Bypass: ?";
        };
    }
    @Override
    public void render(GuiGraphics g, int mx, int my, float pt) {
        renderBackground(g, mx, my, pt);
        g.fill(width/2 - 120, 30, width/2 + 120, 180, 0xAA000000);
        g.drawString(font, "BESHIDY CHEAT", width/2 - 50, 40, 0xFFFFFF);
        g.drawString(font, "Reach (1-6)", width/2 - 100, 40, 0xAAAAAA);
        super.render(g, mx, my, pt);
    }
    @Override
    public void onClose() {
        try { int d = Integer.parseInt(reachField.getValue()); if (d>=1 && d<=6) ClientState.reachDistance = d; } catch (Exception ignored) {}
        ClientState.espEnabled = espCheckbox.selected();
        ClientState.killauraEnabled = killauraCheckbox.selected();
        super.onClose();
    }
    @Override public boolean isPauseScreen() { return false; }
}