import java.io.Serializable;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;

public class Player extends DamagableDrawableAABB implements Serializable{
	private final long SHOOT_TIME = 100;
	private final long INVULN_TIME = 200;
	private long timeUntilShoot;
	private long timeUntilHurt;
	private int enemiesKilled;
	public Player(int x, int y, int w, int h, AnimationData ani, int hp){
		super(x,y,w,h,ani,hp);
		timeUntilShoot = SHOOT_TIME;
		timeUntilHurt = INVULN_TIME;
		enemiesKilled =0;
	}
	public void update(GL2 gl, long deltaTimeMS, Camera camera){
		if(timeUntilShoot>0) timeUntilShoot -= deltaTimeMS;
		if(timeUntilHurt >0) timeUntilHurt -= deltaTimeMS;
		this.draw(camera, gl, deltaTimeMS);
	}
	public void damage(int amount){
		if(timeUntilHurt <= 0){
			this.setHP(this.getHP() - amount);
			timeUntilHurt = INVULN_TIME;
		}
		if(this.getHP() <= 0) {SoundLoader.OHNO.playSound();}; //play OHNO sound
		if(this.getHP() < 0) this.setHP(0);
	}
	public ArrayList<Projectile> shoot(ArrayList<Projectile> p, AnimationData data){
		if(timeUntilShoot <= 0){
			p.add(new Projectile((this.getX() + this.getW()/2) , this.getY(), data.getCurrentImageSize()[0]
					, data.getCurrentImageSize()[1], data, 0, -4, 10));
			timeUntilShoot = SHOOT_TIME;
			SoundLoader.PEW.playSound();
		}
		return p;
	}
	public void moveAndCheck(int xDelta, int yDelta, int numFrames, Camera camera){
		this.addToX((int) (xDelta * numFrames));
		this.addToY((int) (yDelta * numFrames));
		this.checkCamera(camera);
	}
	

	public boolean isDead(){
		return this.getHP() <=0;
	}
	public int getEnemiesKilled(){
		return this.enemiesKilled;
	}
	public void addToEnemiesKilled(int e){
		this.enemiesKilled += e;
	}
	
}
