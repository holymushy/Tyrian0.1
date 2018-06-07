import java.util.ArrayList;

import com.jogamp.opengl.GL2;

public class Projectile extends DrawableAABB{
	private int xDir, yDir, dmg;
	public Projectile(int x, int y, int w, int h, AnimationData ani, int xDir, int yDir, int dmg){
		super(x,y,w,h,ani);
		this.xDir = xDir;
		this.yDir = yDir;
		this.dmg = dmg;
	}
	public void move(){
		this.addToX(xDir);
		this.addToY(yDir);
	}
	public int getDamage(){
		return this.dmg;
	}
	public int getYDir(){
		return this.yDir;
	}
}


class ProjectileHandler{
	private ArrayList<Projectile> pList;
	private ArrayList<Enemy> eList;
	private Player p;
	private Boss boss;
	public ProjectileHandler(){
		pList = null;
		eList = null;
		p = null;
		boss = null;
	}
	public void projectileVsEnemy(ArrayList<Projectile> pList, ArrayList<Enemy> eList){
		this.pList = pList;
		this.eList = eList;
		for(int i =0; i<this.pList.size(); i++){
			for(int j=0; j<this.eList.size(); j++){
				if(this.pList.get(i).AABBIntersect(this.eList.get(j))){
					this.eList.get(j).damage(this.pList.get(i).getDamage());
					if(this.eList.get(j).getHP() <= 0){
						if(p!=null){
							if(this.eList.get(j) instanceof SideSweeper) p.addToEnemiesKilled(1);
							else if(this.eList.get(j) instanceof StationaryEnemy) p.addToEnemiesKilled(2);
						}
						this.eList.remove(j); 
						j--;
					}
					this.pList.remove(i);
					i--;
					break;
				}
			}
		}
	}
	public void projectileVsPlayer(ArrayList<Projectile> pList, Player p){
		this.pList = pList;
		this.p = p;
		for(int i = 0; i<this.pList.size(); i++){
			if(p.AABBIntersect(this.pList.get(i))){
				this.p.damage(this.pList.get(i).getDamage());
				this.pList.remove(i);
				i--;
			}
		}
	}
	public void projectileVsBoss(ArrayList<Projectile> pList, Boss b){
		this.pList = pList;
		this.boss = b;
		if(boss.activated()){
			for(int i = 0; i<this.pList.size(); i++){
				if(b.AABBIntersect(this.pList.get(i))){
					this.boss.damage(this.pList.get(i).getDamage());
					this.pList.remove(i);
					i--;
				}
			}
		}
	}
	
	public ArrayList<Projectile> drawProjectiles(ArrayList<Projectile> projectiles, AABB camera, GL2 gl, long deltaTimeMS, int cameraScrollSpeed){
		if (camera.getY() == 0) cameraScrollSpeed = 0;
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile pro = projectiles.get(i);
			pro.addToY(cameraScrollSpeed);
			if (camera.AABBIntersect(pro)) {
				pro.draw(camera, gl, deltaTimeMS);
			} else {
				projectiles.remove(i);
				i--;
			}
			pro.addToY(-cameraScrollSpeed);
		}
		return projectiles;
	}
	public Player getNewPlayer(){
		return this.p;
	}
	public ArrayList<Projectile> getNewPList(){
		return this.pList;
	}
	public ArrayList<Enemy> getNewEList(){
		return this.eList;
	}
	public Boss getNewBoss(){
		return this.boss;
	}
}