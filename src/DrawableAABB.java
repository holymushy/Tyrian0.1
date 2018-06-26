import com.jogamp.opengl.GL2;

public class DrawableAABB extends AABB{
	private AnimationData ani;
	public DrawableAABB(int x, int y, int w, int h, AnimationData ani){
		super(x,y,w,h);
		this.ani = ani;
	}
	
	public void changeAnimation(AnimationData ani){
		this.ani = ani;
	}
	public void draw(Camera camera, GL2 gl, long deltaTimeMS){
		ani.update(deltaTimeMS);
		if(camera.AABBIntersect(this)){
			JavaTemplate.glDrawSprite(gl, this.ani.getCurrentImage(), this.getX()-camera.getX(), this.getY() - camera.getY(), this.getW(), this.getH());
		}
	}
}
