import java.util.ArrayList;

public abstract class Enemy extends DamagableDrawableAABB {
	private static final long serialVersionUID = -2546906160921793608L;
	public Enemy(int x, int y, int w, int h, AnimationData ani, int hp){
		super(x,y,w,h,ani,hp);
	}
	public abstract void updateAI(int numFrames, long deltaTimeMS, ArrayList<Projectile> p, AnimationData[] a);
}
