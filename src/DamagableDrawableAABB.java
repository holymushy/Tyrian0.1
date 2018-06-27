
public abstract class DamagableDrawableAABB extends DrawableAABB{
	private static final long serialVersionUID = -3580073914587239753L;
	private int hp;
	public DamagableDrawableAABB(int x, int y, int w, int h, AnimationData ani, int hp){
		super(x,y,w,h,ani);
		this.hp = hp;
	}
	public int getHP(){
		return this.hp;
	}
	public void damage(int amount){
		this.setHP(this.getHP() - amount);
	}
	public void recover(int amount){
		this.setHP(this.getHP() + amount);
	}
	protected void setHP(int newHP){
		this.hp = newHP;
	}
	public void checkCamera(AABB camera){
		if(this.getX() < camera.getX()){
			this.setX(camera.getX()); // if player is leftwards of camera
		}
		if(this.getY() < camera.getY()){
			this.setY(camera.getY()); //if player is upwards of camera
		}
		if(this.getX() + this.getW() > camera.getX() + camera.getW() ){
			//if player is rightwards of camera
			this.addToX((camera.getX() + camera.getW()) - (this.getX() + this.getW()));
		}	
		if(this.getY() + this.getH() > camera.getY() + camera.getH()){
			//if player is downwards of camera
			this.addToY((camera.getY() + camera.getH()) - (this.getY() + this.getH()));
		}
	}
}
