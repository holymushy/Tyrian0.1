import java.util.ArrayList;

public class Boss extends Enemy {
	private static final long serialVersionUID = -3743638164886441336L;
	boolean phase1, phase2, phase3;
	private AnimationData[] animations;
	private Phase movePhase, shootPhase;
	private final long DEFAULT_SHOOT_ROLL_TIME = 1000;
	private long shootRollTime;
	private final long DEFAULT_SHOOT_TIME_P1 = 500;
	private final long DEFAULT_SHOOT_TIME_P2 = 1100;
	private final long DEFAULT_SHOOT_TIME_P3 = 200;
	private long shootTime;
	private final int xSpeed = 1;
	private boolean activate;
	private int oneThird;
	private int twoThird;
	enum Phase {
		PHASE_R, PHASE_L, PHASE1_P, PHASE2_P, PHASE3_P;
	}

	//int x, int y, int w, int h, AnimationData[] ani, int hp
	public Boss(BossBuilder b) {
		super(b.getX(), b.getY(), b.getW(), b.getH(), b.getAniArr()[0], b.getHP());
		this.animations = b.getAniArr();
		phase1 = true;
		phase2 = false;
		phase3 = false;
		movePhase = Phase.PHASE_L;
		shootPhase = Phase.PHASE1_P;
		shootTime = DEFAULT_SHOOT_TIME_P1;
		shootRollTime = DEFAULT_SHOOT_ROLL_TIME;
		activate = false;
		oneThird = this.getHP()/3;
		twoThird = (this.getHP()/3) *2;
	}

	public void updateAI(int numFrames, long deltaTimeMS, ArrayList<Projectile> p, AnimationData[] a) {
		if (!activate) {
			if(this.getY()== 0) 
				activate = true;
			else{
				this.addToY(numFrames);
				if(this.getY() > 0) this.setY(0);
			}
		} 
		else {
			this.checkHP();
			this.checkPhase(p, a, deltaTimeMS);
		}
	}

	public void setPhase(boolean p1, boolean p2, boolean p3) {
		phase1 = p1;
		phase2 = p2;
		phase3 = p3;
	}

	public boolean[] getPhase() {
		return new boolean[] { phase1, phase2, phase3 };
	}

	public void checkPhase(ArrayList<Projectile> p, AnimationData[] a, long deltaTimeMS) {
		if (phase1)
			phase1Actions(p, a, deltaTimeMS);
		else if (phase2)
			phase2Actions(p, a, deltaTimeMS);
		else
			phase3Actions(p, a, deltaTimeMS);
	}

	public void moveCheck(long deltaTimeMS, Camera cam) {
		if(this.getX() <= cam.getX()) {
			this.setX(cam.getX());
			this.movePhase = Phase.PHASE_R;
		}
		else if(this.getX() + this.getW() >= cam.getX() + cam.getW()) {
			this.setX(cam.getX()+cam.getW()-this.getW());
			this.movePhase = Phase.PHASE_L;
		}
		switch (this.movePhase) {
		case PHASE_L:
			this.addToX((int) (-xSpeed * deltaTimeMS/16));
			if(this.getX()<0) this.setX(0);
			break;
		case PHASE_R:
			this.addToX((int) (xSpeed * deltaTimeMS/16));
			if(this.getX() + this.getW() > 800) this.setX(800-this.getW());
			break;
		default:
			break;
		}
	}

	public void phase1Shoot(ArrayList<Projectile> p, AnimationData[] a, long deltaTimeMS) {
		shootTime -= deltaTimeMS;
		AnimationData aa = a[0];
		if (shootTime <= 0) {
			shootTime += DEFAULT_SHOOT_TIME_P1;
			p.add(new Projectile(this.getX() + this.getW() / 2, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 0, 3, 10));
			p.add(new Projectile(this.getX() + this.getW() / 2, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, -2, 3, 10));
			p.add(new Projectile(this.getX() + this.getW() / 2, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 2, 3, 10));
		}
	}

	public void phase2Shoot(ArrayList<Projectile> p, AnimationData[] a, long deltaTimeMS) {
		shootTime -= deltaTimeMS;
		AnimationData aa = a[1];
		if(shootTime <=0){
			shootTime += DEFAULT_SHOOT_TIME_P2;
			p.add(new Projectile(this.getX() + this.getW() / 2, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 0, 1, 20));
			p.add(new Projectile(this.getX() + this.getW() / 2, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, -3, 1, 20));
			p.add(new Projectile(this.getX() + this.getW() / 2, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 3, 1, 20));
			p.add(new Projectile(this.getX() + this.getW() / 2, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, -2, 1, 20));
			p.add(new Projectile(this.getX() + this.getW() / 2, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 2, 1, 20));
			p.add(new Projectile(this.getX() + this.getW() / 2, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, -1, 1, 20));
			p.add(new Projectile(this.getX() + this.getW() / 2, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 1, 1, 20));
		}
	}

	public void phase3Shoot(ArrayList<Projectile> p, AnimationData[] a, long deltaTimeMS) {
		shootTime -= deltaTimeMS;
		AnimationData aa = a[2];
		if(shootTime <=0){
			shootTime += DEFAULT_SHOOT_TIME_P3;
			p.add(new Projectile(this.getX() + this.getW() / 2, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 0, 3, 5));
			p.add(new Projectile((this.getX() + this.getW() / 2) - aa.getCurrentImageSize()[0] -10, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 0, 3, 5));
			p.add(new Projectile(this.getX() + this.getW() / 2 + aa.getCurrentImageSize()[0] + 10, this.getY() + this.getH(), aa.getCurrentImageSize()[0],
					aa.getCurrentImageSize()[1], aa, 0, 3, 5));
		}
	}

	public void shootCheck(ArrayList<Projectile> p, AnimationData[] a, long deltaTimeMS) {
		shootRollTime -= deltaTimeMS;
		if (shootRollTime <= 0) {
			shootRollTime += DEFAULT_SHOOT_ROLL_TIME;
			if (phase2) {
				int num = JavaTemplate.getRandomNumberInRange(1, 2);
				if (num == 1)
					this.shootPhase = Phase.PHASE1_P;
				else
					this.shootPhase = Phase.PHASE2_P;
			}
			if (phase3) {
				int num = JavaTemplate.getRandomNumberInRange(1, 3);
				if (num == 1)
					this.shootPhase = Phase.PHASE1_P;
				else if (num == 2)
					this.shootPhase = Phase.PHASE2_P;
				else
					this.shootPhase = Phase.PHASE3_P;
			}
		}
		switch (this.shootPhase) {
		case PHASE1_P:
			this.phase1Shoot(p, a, deltaTimeMS);
			break;
		case PHASE2_P:
			this.phase2Shoot(p, a, deltaTimeMS);
			break;
		case PHASE3_P:
			this.phase3Shoot(p, a, deltaTimeMS);
			break;
		default:
			break;
		}
	}

	public void phase1Actions(ArrayList<Projectile> p, AnimationData[] a, long deltaTimeMS) {
		this.phase1Shoot(p, a, deltaTimeMS);
	}

	public void phase2Actions(ArrayList<Projectile> p, AnimationData[] a, long deltaTimeMS) {
		this.shootCheck(p, a, deltaTimeMS);
	}

	public void phase3Actions(ArrayList<Projectile> p, AnimationData[] a, long deltaTimeMS) {
		this.shootCheck(p, a, deltaTimeMS);
	}

	public void checkHP() {
		if (this.getHP() < oneThird) {
			if(!phase3){
				this.setPhase(false, false, true);
				this.changeAnimation(animations[2]);
			}
		} else if (this.getHP() < twoThird) {
			if(!phase2){
				this.setPhase(false, true, false);
				this.changeAnimation(animations[1]);
			}
		}
	}
	public boolean activated(){
		return this.activate;
	}
}

class BossBuilder extends DamagableDrawableAABBBuilder{
	private AnimationData[] ani;
	public BossBuilder() {}
	public BossBuilder setAniArr(AnimationData[] ani) {this.ani = ani; return this;}
	public AnimationData[] getAniArr() {return this.ani;}
	public Boss build() {return new Boss(this);}
}
