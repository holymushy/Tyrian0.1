import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.jogamp.opengl.GL2;

public class LevelLoader {
	private Level level1;
	public LevelLoader(GL2 gl){
		BackgroundLoader bgLoader = new BackgroundLoader(gl);
		BackgroundDef bgDef = bgLoader.getBG();
		AABB camera = new AABB(0, bgDef.getH() * bgDef.getTileHeight() - 600, 800, 600);
		AnimationLoader a = new AnimationLoader(gl);
		Player p = new Player(100, camera.getY() + 400, a.getPlayerAni("middle").getCurrentImageSize()[0],
				a.getPlayerAni("middle").getCurrentImageSize()[1], a.getPlayerAni("middle"), 100);
		
		ArrayList<SpawnPoint> points = new ArrayList<SpawnPoint>();
		
		ArrayList<spawner> s = new ArrayList<spawner>();
		s.add(new spawner(89, "carrot", 7));
		//s.add(new spawner(85, "carrot", 7));
		s.add(new spawner(81, "gencore", 2));
		s.add(new spawner(73, "carrot", 5));
		//s.add(new spawner(65, "carrot", 7));
		s.add(new spawner(61, "gencore", 2));
		//s.add(new spawner(57, "carrot", 3));
		s.add(new spawner(49, "carrot", 5));
		s.add(new spawner(40, "gencore", 2));
		//s.add(new spawner(37, "carrot", 5));
		s.add(new spawner(29, "gencore", 3));
		s.add(new spawner(20, "carrot", 5));
		
		for(spawner ss: s){
			ArrayList<Enemy> ee = new ArrayList<Enemy>();
			AnimationData aa = a.getEnemyAni(ss.getEnemy());
			if(ss.getEnemy().equals("carrot")){
				//move in from the right
				for(int i =0; i<ss.getAmount(); i++){
					ee.add(new SideSweeper(camera.getW() + (i * (aa.getCurrentImageSize()[0] + 100)) , 
							ss.getY() * bgDef.getTileHeight() - 10, aa.getCurrentImageSize()[0],
							aa.getCurrentImageSize()[1],aa,10,-3));

					ee.add(new SideSweeper(0 - ((aa.getCurrentImageSize()[0] + 100) * i), 
							ss.getY() * bgDef.getTileHeight() - 100, aa.getCurrentImageSize()[0],
							aa.getCurrentImageSize()[1],aa,10,3));
				}
				points.add(new SpawnPoint(ss.getY()* bgDef.getTileHeight(), ee));
			}
			else if(ss.getEnemy().equals("gencore")){
				for(int i =0; i<ss.getAmount();i++){
					int leftLimit = 0 + aa.getCurrentImageSize()[0];
					int rightLimit = camera.getW() - aa.getCurrentImageSize()[0] * 10;
					ee.add(new StationaryEnemy(((rightLimit-leftLimit)/ss.getAmount() * (i+2)) + leftLimit, ss.getY()*bgDef.getTileHeight()-aa.getCurrentImageSize()[1], 
							aa.getCurrentImageSize()[0], aa.getCurrentImageSize()[1], aa, 40));
				}
				points.add(new SpawnPoint(ss.getY() * bgDef.getTileHeight(), ee));
			}
		}
		
		
		AnimationData bossAni = a.getEnemyAni("bossN");
		Boss b = new Boss(camera.getW()/2 - bossAni.getCurrentImageSize()[0]/2, -bossAni.getCurrentImageSize()[1], 
				bossAni.getCurrentImageSize()[0], bossAni.getCurrentImageSize()[1], 
				new AnimationData[] {bossAni, a.getEnemyAni("bossG"),  a.getEnemyAni("bossA")}, 3000);
		
		level1 = new Level(bgDef, p, camera, points, "level 1", b);
	}
	public Level getLevel(String s){
		Level temp = null;
		switch(s){
		case "Level 1": temp = level1; break;
		}
		return temp;
	}
}

class spawner{
	private int y, amount;
	private String enemy;
	public spawner(int y, String enemy, int amount){
		this.y = y;
		this.amount = amount;
		this.enemy = enemy;
	}
	public int getY(){
		return this.y;
	}
	public int getAmount(){
		return this.amount;
	}
	public String getEnemy(){
		return this.enemy;
	}
}

class SpawnPoint implements Serializable{
	private int y;
	private ArrayList<Enemy> eList;
	public SpawnPoint(int y, ArrayList<Enemy> eList){
		this.y = y;
		this.eList = eList;
	}
	public int getPoint(){
		return this.y;
	}
	public ArrayList<Enemy> getEnemies(){
		return this.eList;
	}
}
