package dev.vatuu.tesseract.client.extensions.mixins;

import dev.vatuu.tesseract.client.world.TesseractSkyProperties;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    public void renderTesseractSkybox(MatrixStack stack, float delta, CallbackInfo info) {
        ClientWorld w = this.client.world;
        if(w.getSkyProperties() instanceof TesseractSkyProperties) {
            TesseractSkyProperties sky = (TesseractSkyProperties) w.getSkyProperties();
            sky.getSkybox().renderSky(stack, w, client.gameRenderer.getCamera(), delta);
            info.cancel();
        }
    }
}
