import java.util.ArrayList;

public class Level {
	private BackgroundDef bg, oldBG;
	private Player p, oldP;
	private Camera camera, oldCamera;
	private ArrayList<SpawnPoint> spawns, oldSpawns;
	private String name;
	private Boss boss, oldBoss;
	public Level(BackgroundDef bg, Player p, Camera camera, ArrayList<SpawnPoint> points, String name, Boss boss){
		this.bg = bg;
		this.p = p;
		this.oldBG = (BackgroundDef) JavaTemplate.deepClone(bg);
		this.oldP = (Player) JavaTemplate.deepClone(p);
		this.camera = camera;
		this.oldCamera = (Camera) JavaTemplate.deepClone(camera);
		this.spawns = points;
		this.oldSpawns = (ArrayList<SpawnPoint>) JavaTemplate.deepClone(spawns);
		this.name = name;
		this.boss = boss;
		this.oldBoss = (Boss) JavaTemplate.deepClone(this.boss);
	}
	public BackgroundDef getBG(){
		return this.bg;
	}
	public Player getPlayer(){
		return this.p;
	}
	public void reset(){
		this.bg = this.oldBG;
		this.p = this.oldP;
		this.camera = this.oldCamera;
		this.spawns = this.oldSpawns;
		this.boss = this.oldBoss;
		
		this.oldBG = (BackgroundDef) JavaTemplate.deepClone(bg);
		this.oldP = (Player) JavaTemplate.deepClone(p);
		this.camera = (Camera) JavaTemplate.deepClone(this.camera);
		this.oldSpawns = (ArrayList<SpawnPoint>) JavaTemplate.deepClone(spawns);
		this.oldBoss = (Boss) JavaTemplate.deepClone(this.boss);
		
	}
	public Camera getCamera(){
		return this.camera;
	}
	public ArrayList<SpawnPoint> getSpawns(){
		return this.spawns;
	}
	public String getName(){
		return this.name;
	}
	public Boss getBoss(){
		return this.boss;
	}
}