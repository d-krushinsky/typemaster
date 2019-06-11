package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

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
		
		g.setColor(Color.YELLOW);
		g.drawString("FPS: "+TypeMaster.getFPS(), 
				TypeMaster.canvas.getWidth()-g.getFontMetrics().stringWidth("FPS: "+TypeMaster.getFPS()),
				g.getFont().getSize());
		g.drawString("UPS: "+TypeMaster.getUPS(), 
				TypeMaster.canvas.getWidth()-g.getFontMetrics().stringWidth("UPS: "+TypeMaster.getUPS()),
				g.getFont().getSize()*2);
	}
}