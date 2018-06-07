import java.util.ArrayList;

public class StationaryEnemy extends Enemy{
	private long timeToShoot;
	private final long DEFAULT_SHOOT_TIME = 500;
	private final long RELOAD_TIME = 3000;
	private int shootCount;
	public StationaryEnemy(int x, int y, int w, int h, AnimationData ani, int hp) {
		super(x, y, w, h, ani, hp);
		timeToShoot = DEFAULT_SHOOT_TIME;
		shootCount =0;
	}
	public ArrayList<Projectile> updateAI(int numFrames, long deltaTimeMS, ArrayList<Projectile> p, AnimationData[] a){
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
			
			//super diagonal bullets
//			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, a.getCurrentImageSize()[0],
//					a.getCurrentImageSize()[1], a, -1, -2, 10));
//			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, a.getCurrentImageSize()[0],
//					a.getCurrentImageSize()[1], a, -2, -1, 10));
//			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, a.getCurrentImageSize()[0],
//					a.getCurrentImageSize()[1], a, 2, -1, 10));
//			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, a.getCurrentImageSize()[0],
//					a.getCurrentImageSize()[1], a, 1, -2, 10));
//			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, a.getCurrentImageSize()[0],
//					a.getCurrentImageSize()[1], a, 2, 1, 10));
//			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, a.getCurrentImageSize()[0],
//					a.getCurrentImageSize()[1], a, 1, 2, 10));
//			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, a.getCurrentImageSize()[0],
//					a.getCurrentImageSize()[1], a, -2, 1, 10));
//			p.add(new Projectile(this.getX() + this.getW()/2, this.getY() + this.getH()/2, a.getCurrentImageSize()[0],
//					a.getCurrentImageSize()[1], a, -1, 2, 10));
		}
		return p;
	}
	
}
