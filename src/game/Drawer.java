package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import game.resources.Fonts;
import game.resources.Images;
import nightingale.graph.NDrawer;

public class Drawer implements NDrawer{
	@Override
	public void draw(Graphics g, Graphics2D g2d, AffineTransform at) {
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		
		
		g.drawImage(Images.background, 0, 0,
				(int)TypeMaster.uiCamera.scale(Images.background.getWidth()),
				(int)TypeMaster.uiCamera.scale(Images.background.getHeight()), null);
		TypeMaster.stateHandler.drawCurrentState(g, g2d, at);
		
		Fonts.gameFont.draw("FPS: "+TypeMaster.getFPS(),
				(int)(TypeMaster.uiCamera.unscale(TypeMaster.canvas.getWidth())-Fonts.gameFont.getStringWidth("FPS: "+TypeMaster.getFPS())),
				0, g2d, TypeMaster.uiCamera);
	}
}