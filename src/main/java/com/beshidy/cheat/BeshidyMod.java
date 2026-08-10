package com.beshidy.cheat;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Mod("beshidycheat")
public class BeshidyMod {
    public BeshidyMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }
    @OnlyIn(Dist.CLIENT)
    private void clientSetup(final net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent event) {
        System.out.println("[BESHIDY] Mod loaded!");
    }
}