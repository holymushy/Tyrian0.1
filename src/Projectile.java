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
	public static void projectileVsEnemy(ArrayList<Projectile> pList, ArrayList<Enemy> eList, Player p){
		for(int i =0; i<pList.size(); i++){
			for(int j=0; j<eList.size(); j++){
				if(pList.get(i).AABBIntersect(eList.get(j))){
					eList.get(j).damage(pList.get(i).getDamage());
					if(eList.get(j).getHP() <= 0){
						if(p!=null){
							if(eList.get(j) instanceof SideSweeper) p.addToEnemiesKilled(1);
							else if(eList.get(j) instanceof StationaryEnemy) p.addToEnemiesKilled(2);
						}
						eList.remove(j); 
						j--;
					}
					pList.remove(i);
					i--;
					break;
				}
			}
		}
	}
	public static void projectileVsPlayer(ArrayList<Projectile> pList, Player p){
		for(int i = 0; i<pList.size(); i++){
			if(p.AABBIntersect(pList.get(i))){
				p.damage(pList.get(i).getDamage());
				pList.remove(i);
				i--;
			}
		}
	}
	public static void projectileVsBoss(ArrayList<Projectile> pList, Boss b){
		if(b.activated()){
			for(int i = 0; i<pList.size(); i++){
				if(b.AABBIntersect(pList.get(i))){
					b.damage(pList.get(i).getDamage());
					pList.remove(i);
					i--;
				}
			}
		}
	}
	
	public static void drawProjectiles(ArrayList<Projectile> projectiles, Camera camera, GL2 gl, long deltaTimeMS, int cameraScrollSpeed){
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
	}
}