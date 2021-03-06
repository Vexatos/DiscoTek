
package net.specialattack.discotek.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLaserRound extends ModelBase {

    public ModelRenderer base;

    public ModelLaserRound() {
        this.base = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);

        this.base.addBox(-6.0F, -8.0F, -6.0F, 12, 5, 12, 0.0F);

        this.base.addBox(-4.0F, -3.0F, -4.0F, 8, 2, 8, 0.0F);
    }

    public void renderAll() {
        this.base.render(0.0625F);
    }

}
