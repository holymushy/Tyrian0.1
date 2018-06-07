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
//	public void drawAndAABBChange(AABB camera, GL2 gl, long deltaTimeMS){
//		this.draw(camera, gl, deltaTimeMS);
//		this.changeAABB();
//	}
	public void draw(AABB camera, GL2 gl, long deltaTimeMS){
		boolean loop = ani.update(deltaTimeMS);
		if(camera.AABBIntersect(this)){
			JavaTemplate.glDrawSprite(gl, this.ani.getCurrentImage(), this.getX()-camera.getX(), this.getY() - camera.getY(), this.getW(), this.getH());
		}
	}
//	public void changeAABB(){
//		this.setW(this.ani.getCurrentImageSize()[0]);
//		this.setH(this.ani.getCurrentImageSize()[1]);
//	}
}
