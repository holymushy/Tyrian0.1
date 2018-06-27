import java.io.Serializable;

import com.jogamp.opengl.GL2;

public class BackgroundDef implements Serializable {
	private static final long serialVersionUID = -2558482647373421089L;
	private BGTile[] tiles;
	private int w,h;
	public BackgroundDef(int w, int h, BGTile[] tiles){
		this.w = w;
		this.h = h;
		this.tiles = tiles;
	}
	public BGTile getTile(int x, int y){
		return tiles[(y*this.w) + x];
	}
	public int getW(){
		return this.w;
	}
	public int getH(){
		return this.h;
	}
	public int getTileWidth(){
		return tiles[0].getW();
	}
	public int getTileHeight(){
		return tiles[0].getH();
	}
	public void draw(Camera camera, GL2 gl){
		int BGWidth = this.getTileWidth();
		int BGHeight = this.getTileHeight();
		
		int upperIndexX = camera.getX()/BGWidth;
		int upperIndexY = camera.getY()/BGHeight;
		int lowerIndexX = (camera.getX() + camera.getW() - 1)/BGWidth;
		int lowerIndexY = (camera.getY() + camera.getH() - 1)/BGHeight;
		
		for(int i =upperIndexX; i<=lowerIndexX; i++){
			for(int j = upperIndexY; j <= lowerIndexY; j++){
				BGTile temp = this.getTile(i, j);
				AABB tileAABB = new AABB(i * BGWidth, j * BGHeight, BGWidth, BGHeight);
				JavaTemplate.glDrawSprite(gl, temp.getTexture(), tileAABB.getX() - camera.getX(), 
						tileAABB.getY() - camera.getY(), BGWidth, BGHeight);
			}
		}
	}
}
