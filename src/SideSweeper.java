import java.util.ArrayList;

public class SideSweeper extends Enemy{
	private int xDir;
	private long timeToShoot;
	private final long DEFAULT_SHOOT_TIME = 1000;
	public SideSweeper(int x, int y, int w, int h, AnimationData ani, int hp, int xDir){
		super(x,y,w,h,ani,hp);
		this.xDir = xDir;
		timeToShoot = DEFAULT_SHOOT_TIME;
	}
	public ArrayList<Projectile> updateAI(int numFrames, long deltaTimeMS, ArrayList<Projectile> p, AnimationData[] a){
		this.addToX(xDir * numFrames);
		this.addToY(-numFrames);
		timeToShoot -= deltaTimeMS;
		AnimationData aa = a[0];
		if(timeToShoot <= 0){
			timeToShoot += DEFAULT_SHOOT_TIME;
			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 0, 2, 10));
		}
		return p;
	}
}
