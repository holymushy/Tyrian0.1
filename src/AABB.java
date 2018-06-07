import java.io.Serializable;

public class AABB implements Serializable {
	private int x,y,w,h;
	public AABB(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public int getW(){
		return this.w;
	}
	public int getH(){
		return this.h;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public void setW(int w){
		this.w = w;
	}
	public void setH(int h){
		this.h = h;
	}
	public void addToX(int addX){
		this.setX(this.getX() + addX);
	}
	public void addToY(int addY){
		this.setY(this.getY() + addY);
	}
	public boolean AABBIntersect(AABB other){
		// box1 to the right
		if (this.getX() > other.getX() + other.getW()) {
			return false;
		}
		// box1 to the left
		if (this.getX() + this.getW() < other.getX()) {
			return false;
		}
		// box1 below
		if (this.getY() > other.getY() + other.getH()) {
			return false;
		}
		// box1 above
		if (this.getY() + this.getH() < other.getY()) {
			return false;
		}
		return true;
	}
	public void stayInBG(BackgroundDef bg){
		if(this.getX() < 0) this.setX(0);
		if(this.getY() < 0) this.setY(0);
		
		int maxX = (bg.getW() * bg.getTileWidth() )- this.getW();
		int maxY = (bg.getH() * bg.getTileHeight() )- this.getH();
		
		if(this.getX() > maxX) this.setX(maxX);
		if(this.getY() > maxY) this.setY(maxY);
	}

}
