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

class SideSweeperBuilder{
	//int x, int y, int w, int h, AnimationData ani, int hp, int xDir
	private int x, y, w, h, hp, xDir;
	private AnimationData ani;
	public SideSweeperBuilder() {}
	public SideSweeperBuilder setX(int x) {this.x = x; return this;}
	public SideSweeperBuilder setY(int y) {this.y = y; return this;}
	public SideSweeperBuilder setW(int w) {this.w = w; return this;}
	public SideSweeperBuilder setH(int h) {this.h = h; return this;}
	public SideSweeperBuilder setHP(int hp) {this.hp = hp; return this;}
	public SideSweeperBuilder setXDir(int xDir) {this.xDir = xDir; return this;}
	public SideSweeperBuilder setAni(AnimationData ani) {this.ani = ani; return this;}
	public int getX() {return this.x;}
	public int getY() {return this.y;}
	public int getW() {return this.w;}
	public int getH() {return this.h;}
	public int getHP() {return this.hp;}
	public int getXDir() {return this.xDir;}
	public AnimationData getAni() {return this.ani;}
	public SideSweeper build(){
		return new SideSweeper(this);	
	}
}