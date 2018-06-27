import java.util.ArrayList;

public class SideSweeper extends Enemy{
	private static final long serialVersionUID = -7462458267092842623L;
	private int xDir;
	private long timeToShoot;
	private final long DEFAULT_SHOOT_TIME = 1000;
	public SideSweeper(SideSweeperBuilder s){
		super(s.getX(), s.getY(), s.getW(), s.getH(), s.getAni(), s.getHP());
		this.xDir = s.getXDir();
		timeToShoot = DEFAULT_SHOOT_TIME;
	}
	public void updateAI(int numFrames, long deltaTimeMS, ArrayList<Projectile> p, AnimationData[] a){
		this.addToX(xDir * numFrames);
		this.addToY(-numFrames);
		timeToShoot -= deltaTimeMS;
		AnimationData aa = a[0];
		if(timeToShoot <= 0){
			timeToShoot += DEFAULT_SHOOT_TIME;
			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 0, 2, 10));
		}
	}
}

class SideSweeperBuilder extends DamagableDrawableAABBBuilder{
	//int x, int y, int w, int h, AnimationData ani, int hp, int xDir
	private int xDir;
	public SideSweeperBuilder() {}
	public SideSweeperBuilder setXDir(int xDir) {this.xDir = xDir; return this;}
	public int getXDir() {return this.xDir;}
	public SideSweeper build(){
		return new SideSweeper(this);	
	}
}