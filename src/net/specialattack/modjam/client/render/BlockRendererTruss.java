
package net.specialattack.modjam.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.specialattack.modjam.block.BlockLight;
import net.specialattack.modjam.block.BlockTruss;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockRendererTruss implements ISimpleBlockRenderingHandler {

    public final int renderId;

    public BlockRendererTruss(int renderId) {
        this.renderId = renderId;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        float off = 0.1875F;

        GL11.glPushMatrix();

        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        // Top-bottom connection
        block.setBlockBounds(0.0F, off, 0.0F, 0.1875F, 1.0F - off, 0.1875F);
        doRender(block, renderer);

        block.setBlockBounds(0.0F, off, 0.8125F, 0.1875F, 1.0F - off, 1.0F);
        doRender(block, renderer);

        block.setBlockBounds(0.8125F, off, 0.8125F, 1.0F, 1.0F - off, 1.0F);
        doRender(block, renderer);

        block.setBlockBounds(0.8125F, off, 0.0F, 1.0F, 1.0F - off, 0.1875F);
        doRender(block, renderer);

        // Bottom bars
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 0.1875F);
        doRender(block, renderer);

        block.setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 0.1875F, 1.0F);
        doRender(block, renderer);

        block.setBlockBounds(0.0F, 0.0F, off, 0.1875F, 0.1875F, 1.0F - off);
        doRender(block, renderer);

        block.setBlockBounds(0.8125F, 0.0F, off, 1.0F, 0.1875F, 1.0F - off);
        doRender(block, renderer);

        // Top bars
        block.setBlockBounds(0.0F, 0.8125F, 0.0F, 1.0F, 1.0F, 0.1875F);
        doRender(block, renderer);

        block.setBlockBounds(0.0F, 0.8125F, 0.8125F, 1.0F, 1.0F, 1.0F);
        doRender(block, renderer);

        block.setBlockBounds(0.0F, 0.8125F, off, 0.1875F, 1.0F, 1.0F - off);
        doRender(block, renderer);

        block.setBlockBounds(0.8125F, 0.8125F, off, 1.0F, 1.0F, 1.0F - off);
        doRender(block, renderer);

        GL11.glPopMatrix();

        // Reset block
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

        boolean connectTop = canConnect(world, x, y + 1, z);
        boolean connectBottom = canConnect(world, x, y - 1, z);
        boolean connectNorth = canConnect(world, x, y, z - 1);
        boolean connectSouth = canConnect(world, x, y, z + 1);
        boolean connectWest = canConnect(world, x - 1, y, z);
        boolean connectEast = canConnect(world, x + 1, y, z);

        if (((connectNorth || connectSouth) && (connectWest || connectEast)) || (!connectNorth && !connectSouth && !connectWest && !connectEast && !connectBottom && !connectTop)) {
            connectTop = true;
            connectBottom = true;
            connectNorth = true;
            connectSouth = true;
            connectWest = true;
            connectEast = true;
        }

        if (connectBottom || connectTop) {
            // Top-bottom connection
            float off = connectNorth || connectEast || connectSouth || connectWest ? 0.1875F : 0.0F;

            boolean flag = connectNorth && connectSouth && connectWest && connectEast;

            if ((!connectNorth && !connectWest) || flag) {
                block.setBlockBounds(0.0F, off, 0.0F, 0.1875F, 1.0F - off, 0.1875F);
                doRender(block, x, y, z, renderer);
            }
            if ((!connectSouth && !connectWest) || flag) {
                block.setBlockBounds(0.0F, off, 0.8125F, 0.1875F, 1.0F - off, 1.0F);
                doRender(block, x, y, z, renderer);
            }
            if ((!connectSouth && !connectEast) || flag) {
                block.setBlockBounds(0.8125F, off, 0.8125F, 1.0F, 1.0F - off, 1.0F);
                doRender(block, x, y, z, renderer);
            }
            if ((!connectNorth && !connectEast) || flag) {
                block.setBlockBounds(0.8125F, off, 0.0F, 1.0F, 1.0F - off, 0.1875F);
                doRender(block, x, y, z, renderer);
            }
        }

        if (connectWest || connectEast) {
            // Bottom bars
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 0.1875F);
            doRender(block, x, y, z, renderer);

            block.setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 0.1875F, 1.0F);
            doRender(block, x, y, z, renderer);

            // Top bars
            block.setBlockBounds(0.0F, 0.8125F, 0.0F, 1.0F, 1.0F, 0.1875F);
            doRender(block, x, y, z, renderer);

            block.setBlockBounds(0.0F, 0.8125F, 0.8125F, 1.0F, 1.0F, 1.0F);
            doRender(block, x, y, z, renderer);
        }

        if (connectNorth || connectSouth) {
            float off = connectWest || connectEast ? 0.1875F : 0.0F;
            // Bottom bars
            block.setBlockBounds(0.0F, 0.0F, off, 0.1875F, 0.1875F, 1.0F - off);
            doRender(block, x, y, z, renderer);

            block.setBlockBounds(0.8125F, 0.0F, off, 1.0F, 0.1875F, 1.0F - off);
            doRender(block, x, y, z, renderer);

            // Top bars
            block.setBlockBounds(0.0F, 0.8125F, off, 0.1875F, 1.0F, 1.0F - off);
            doRender(block, x, y, z, renderer);

            block.setBlockBounds(0.8125F, 0.8125F, off, 1.0F, 1.0F, 1.0F - off);
            doRender(block, x, y, z, renderer);
        }

        // Reset block
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        return true;
    }

    private void doRender(Block block, RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;

        renderer.setRenderBoundsFromBlock(block);
        tess.startDrawingQuads();
        tess.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, 0));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, 0));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, 0));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, 0));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, 0));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, 0));
        tess.draw();
    }

    private void doRender(Block block, int x, int y, int z, RenderBlocks renderer) {
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1.0F, 1.0F, 1.0F);
    }

    public boolean canConnect(IBlockAccess world, int x, int y, int z) {
        Block block = Block.blocksList[world.getBlockId(x, y, z)];

        if (block == null || block.blockID == 0) {
            return false;
        }

        if (block instanceof BlockLight || block instanceof BlockTruss) {
            return true;
        }

        if (!block.renderAsNormalBlock() || !block.isOpaqueCube()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory() {
        return true;
    }

    @Override
    public int getRenderId() {
        return this.renderId;
    }

}