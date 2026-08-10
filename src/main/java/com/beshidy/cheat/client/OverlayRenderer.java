package com.beshidy.cheat.client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.awt.*;

@Mod.EventBusSubscriber(modid = "beshidycheat", value = Dist.CLIENT)
public class OverlayRenderer {
    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || !ClientState.espEnabled) return;
        GuiGraphics gui = event.getGuiGraphics();
        for (LivingEntity entity : mc.level.getEntitiesOfClass(LivingEntity.class, mc.player.getBoundingBox().inflate(30))) {
            if (entity == mc.player) continue;
            int sx = 400 + (int)(entity.getX() * 10);
            int sy = 300 + (int)(entity.getY() * 10);
            Color color = entity instanceof Player ? Color.GREEN : Color.RED;
            gui.drawBorder(gui.pose(), sx - 15, sy - 25, 30, 50, color.getRGB());
            gui.drawString(mc.font, entity.getDisplayName().getString(), sx - 20, sy - 40, color.getRGB());
            if (ClientState.killauraEnabled && entity == getTarget()) {
                gui.drawBorder(gui.pose(), sx - 15, sy - 20, 30, 40, Color.YELLOW.getRGB());
                gui.drawString(mc.font, "TARGET", sx - 20, sy - 35, Color.YELLOW.getRGB());
            }
        }
    }
    private static LivingEntity getTarget() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return null;
        double minDist = ClientState.reachDistance;
        LivingEntity target = null;
        for (LivingEntity e : mc.level.getEntitiesOfClass(LivingEntity.class, mc.player.getBoundingBox().inflate(ClientState.reachDistance))) {
            if (e == mc.player) continue;
            double dist = e.distanceTo(mc.player);
            if (dist < minDist) { minDist = dist; target = e; }
        }
        return target;
    }
}