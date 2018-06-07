import java.io.Serializable;

public class BGTile implements Serializable{
	private int tex;
	private int[] size;
	private boolean collidable;
	public BGTile(int texture, int[] size, boolean collidable){
		this.tex = texture;
		this.collidable = collidable;
		this.size = size;
	}
	public int getTexture(){
		return this.tex;
	}
	public int getW(){
		return size[0];
	}
	public int getH(){
		return size[1];
	}
	public boolean isCollidable(){
		return this.collidable;
	}
}
