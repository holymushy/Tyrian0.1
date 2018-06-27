import java.util.ArrayList;

public class StationaryEnemy extends Enemy{
	private static final long serialVersionUID = 5527634051316328269L;
	private long timeToShoot;
	private final long DEFAULT_SHOOT_TIME = 500;
	private final long RELOAD_TIME = 3000;
	private int shootCount;
	public StationaryEnemy(StationaryEnemyBuilder s) {
		super(s.getX(), s.getY(), s.getW(), s.getH(), s.getAni(), s.getHP());
		timeToShoot = DEFAULT_SHOOT_TIME;
		shootCount =0;
	}
	public void updateAI(int numFrames, long deltaTimeMS, ArrayList<Projectile> p, AnimationData[] a){
		timeToShoot -= deltaTimeMS;
		this.addToY(-numFrames);
		if(timeToShoot <= 0){
			shootCount++;
			if(shootCount >= 3){
				timeToShoot += RELOAD_TIME;
				shootCount = 0;
			}
			else {timeToShoot += DEFAULT_SHOOT_TIME;}
			AnimationData aa = a[0];
			//up, down, left, and right bullets
			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 0, 2, 10));
			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 2, 0, 10));
			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 0, -2, 10));
			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, -2, 0, 10));
			
			//diagonal bullets
			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 2, 2, 10));
			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, -2, 2, 10));
			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 2, -2, 10));
			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, -2, -2, 10));
		}
	}
	
}

class StationaryEnemyBuilder extends DamagableDrawableAABBBuilder{
	//int x, int y, int w, int h, AnimationData ani, int hp
	public StationaryEnemyBuilder() {}
	public StationaryEnemy build() {
		return new StationaryEnemy(this);
	}
}
