import java.util.ArrayList;

public abstract class Enemy extends DamagableDrawableAABB {
	public Enemy(int x, int y, int w, int h, AnimationData ani, int hp){
		super(x,y,w,h,ani,hp);
	}
	public abstract ArrayList<Projectile> updateAI(int numFrames, long deltaTimeMS, ArrayList<Projectile> p, AnimationData[] a);
}
