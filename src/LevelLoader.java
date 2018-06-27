import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import com.jogamp.opengl.GL2;

public class LevelLoader {
	public static Level loadLevel1(GL2 gl){
		BackgroundDef bgDef = BackgroundLoader.loadLevel1BG(gl);
		Camera camera = new Camera(0, bgDef.getH() * bgDef.getTileHeight() - 600, 800, 600);
		AnimationLoader a = new AnimationLoader(gl);
		Player p = new PlayerBuilder()
				.setX(camera.getX() + camera.getW()/2)
				.setY(camera.getY() + camera.getH()/2)
				.setW(a.getPlayerAni("middle").getCurrentImageSize()[0])
				.setH(a.getPlayerAni("middle").getCurrentImageSize()[1])
				.setAni(a.getPlayerAni("middle"))
				.setHP(100)
				.build();
		
		ArrayList<SpawnPoint> points = new ArrayList<SpawnPoint>();
		
		ArrayList<spawner> s = new ArrayList<spawner>();
		s.add(new spawner(89, "carrot", 7));
		s.add(new spawner(81, "gencore", 2));
		s.add(new spawner(73, "carrot", 5));
		s.add(new spawner(61, "gencore", 2));
		s.add(new spawner(49, "carrot", 5));
		s.add(new spawner(40, "gencore", 2));
		s.add(new spawner(29, "gencore", 3));
		s.add(new spawner(20, "carrot", 5));
		
		for(spawner ss: s){
			ArrayList<Enemy> ee = new ArrayList<Enemy>();
			AnimationData aa = a.getEnemyAni(ss.getEnemy());
			if(ss.getEnemy().equals("carrot")){
				for(int i =0; i<ss.getAmount(); i++){
					ee.add(new SideSweeperBuilder()
							.setX(camera.getW() + (i * (aa.getCurrentImageSize()[0] + 100)))
							.setY(ss.getY() * bgDef.getTileHeight() - 10)
							.setW(aa.getCurrentImageSize()[0])
							.setH(aa.getCurrentImageSize()[1])
							.setAni(aa)
							.setHP(10)
							.setXDir(-3)
							.build());

					//move in from the left
					ee.add(new SideSweeperBuilder()
							.setX(0 - ((aa.getCurrentImageSize()[0] + 100) * i))
							.setY(ss.getY() * bgDef.getTileHeight() - 100)
							.setW(aa.getCurrentImageSize()[0])
							.setH(aa.getCurrentImageSize()[1])
							.setAni(aa)
							.setHP(10)
							.setXDir(3)
							.build());
				}
				points.add(new SpawnPoint(ss.getY()* bgDef.getTileHeight(), ee));
			}
			else if(ss.getEnemy().equals("gencore")){
				for(int i =0; i<ss.getAmount();i++){
					int leftLimit = 0 + aa.getCurrentImageSize()[0];
					int rightLimit = camera.getW() - aa.getCurrentImageSize()[0] * 10;
					ee.add(new StationaryEnemyBuilder()
							.setX(((rightLimit-leftLimit)/ss.getAmount() * (i+2)) + leftLimit)
							.setY(ss.getY()*bgDef.getTileHeight()-aa.getCurrentImageSize()[1])
							.setW(aa.getCurrentImageSize()[0])
							.setH(aa.getCurrentImageSize()[1])
							.setAni(aa)
							.setHP(40)
							.build());
				}
				points.add(new SpawnPoint(ss.getY() * bgDef.getTileHeight(), ee));
			}
		}
		
		Collections.sort(points);
		
		AnimationData bossAni = a.getEnemyAni("bossN");
		
		Boss b = new BossBuilder()
				.setX(camera.getW()/2 - bossAni.getCurrentImageSize()[0]/2)
				.setY(-bossAni.getCurrentImageSize()[1])
				.setW(bossAni.getCurrentImageSize()[0])
				.setH(bossAni.getCurrentImageSize()[1])
				.setAni(new AnimationData[] {bossAni, a.getEnemyAni("bossG"),  a.getEnemyAni("bossA")})
				.setHP(3000)
				.build();
		
		return new Level(bgDef, p, camera, points, "level 1", b);
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

class SpawnPoint implements Serializable, Comparable<SpawnPoint>{
	private static final long serialVersionUID = 3140001776788805355L;
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
	public int compareTo(SpawnPoint other) {
		if(this.getPoint() > other.getPoint() ) return -1;
		else if(this.getPoint() < other.getPoint()) return 1;
		else return 0;
	}
}
