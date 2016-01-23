package engine.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;

import engine.Game;
import engine.graphics.Color;
import engine.graphics.Texture;
import engine.objects.Sprite;
import engine.objects.shapes.CollisionShape;
import engine.objects.shapes.CollisionShapePoly;
import engine.objects.shapes.RenderShape;
import engine.objects.shapes.RenderShapeRect;

public class SpriteLoader {
	
	public static Sprite loadSprite(String path, Vector2f pos, Vector2f scale) {
		try {
			Sprite result = null;
			
			String info = FileUtils.loadAsString(path + ".txt");
			String[] infoLines = info.split("\n");
			String name = infoLines[0].split("=")[1];
			int polyNumber = Integer.parseInt(infoLines[1].split("=")[1]);
			
			BufferedImage image = null;
			
			try {
				image = ImageIO.read(new File(path + "/" + name + ".png"));
			} catch (IOException e) {
				System.out.println("Could not load image for " + name);
				System.exit(-1);
			}
			
			String[] polyData = new String[polyNumber];
			
			for(int i = 0;i < polyData.length;i++) {
				polyData[i] = FileUtils.loadAsString(path + "/PolyData" + i + ".txt");
			}
			
			Vector2f[][] vects = new Vector2f[polyNumber][0];
			
			for(int i = 0;i < polyNumber;i++) {
				String[] data = polyData[i].split("\n");
				vects[i] = new Vector2f[data.length];
				
				for(int j = 0;j < vects[i].length;j++) {
					String[] line = data[j].split("-");
					vects[i][j] = new Vector2f(Float.parseFloat(line[0]),Float.parseFloat(line[1]));
				}
			}
			
			RenderShape renderShape = new RenderShapeRect(pos, scale, new Color(1,1,1));
			renderShape.ID = Texture.loadFromImageStatic(image);
			CollisionShape[] col = new CollisionShape[polyNumber];
			
			for(int i = 0;i < col.length;i++) {
				CollisionShapePoly temp = new CollisionShapePoly(pos, 0, vects[i]);
				temp.setScaleX(scale.x);
				temp.setScaleY(scale.y);
				col[i] = temp;
			}
			
			result = new Sprite(renderShape,col);
			
			return result;
		} catch (Exception e) {
			System.err.println("Could not load sprite " + path);
			e.printStackTrace();
			Game.close();
			return null;
		}		
	}
}
