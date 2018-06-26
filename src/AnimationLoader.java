import com.jogamp.opengl.GL2;

public class AnimationLoader {
	private AnimationData leftData, midData, rightData, playerProjectile, enemyGenCore, bossN, 
		bossG, bossA, enemyCarrot, bossP2Projectile,enemyProjectile;
	public AnimationLoader(GL2 gl){
		/* Animation Data for the player down below*/
		AniTemp[] tempAniTemp = new AniTemp[] {new AniTemp("Player/Left1.tga", 100), new 
				AniTemp("Player/Left2.tga", 1000)};
		leftData = AnimationFactory.createAnimationDataHold(tempAniTemp, 2, gl);
		
		//middle "animation"
		midData = AnimationFactory.createAnimationData(new AniTemp[] {new AniTemp("Player/Middle.tga",1000)}
		, 2, gl);
		
		
		//right animation
		tempAniTemp = new AniTemp[] {new AniTemp("Player/Right1.tga", 100), 
				new AniTemp("Player/Right2.tga",1000)};
		rightData = AnimationFactory.createAnimationDataHold(tempAniTemp, 2, gl);
		
		/* Animation Data for projectiles below*/
		playerProjectile = AnimationFactory.createAnimationData(new AniTemp[] 
				{new AniTemp("Projectiles/bulletss.tga",1000)}, 1, gl);
		
		enemyProjectile = AnimationFactory.createAnimationData(new AniTemp[] 
				{new AniTemp("Projectiles/bullet.tga",1000)}, 1, gl);
		
		//boss phase2 / fireball
		bossP2Projectile = AnimationFactory.createAnimationData(new AniTemp[] 
				{new AniTemp("Projectiles/fireBall.tga",1000)}, 1, gl);
		
		/* Animation Data for enemies below*/
		//Gencore
		enemyGenCore = AnimationFactory.createAnimationData(new AniTemp[] 
				{new AniTemp("Enemies/gencoreMiddle0.tga",1000)}, 2, gl);
		
		//Carrot
		enemyCarrot = AnimationFactory.createAnimationData(new AniTemp[] 
				{new AniTemp("Enemies/superCarrotMiddle0.tga",1000)}, 2, gl);
		
		//boss
		bossN = AnimationFactory.createAnimationData(new AniTemp[] 
				{new AniTemp("Boss/bossNormal.tga",1000)}, 2, gl);
		
		bossG = AnimationFactory.createAnimationData(new AniTemp[] 
				{new AniTemp("Boss/bossGettingAngry.tga",1000)}, 2, gl);
		
		bossA = AnimationFactory.createAnimationData(new AniTemp[] 
				{new AniTemp("Boss/bossAngry.tga",1000)}, 2, gl);
		
	}
	public AnimationData getPlayerAni(String s){
		AnimationData t = null;
		switch(s){
		case "left": t = this.leftData; break;
		case "right": t= this.rightData; break;
		case "middle": t = this.midData; break;
		}
		return t;
	}
	public AnimationData getProjectileAni(String s){
		AnimationData t = null;
		switch(s){
		case "player": t= this.playerProjectile; break;
		case "enemy": t= this.enemyProjectile; break;
		case "boss2": t= this.bossP2Projectile; break;
		}
		return t;
	}
	public AnimationData getEnemyAni(String s){
		AnimationData t = null;
		switch(s){
		case "gencore": t= this.enemyGenCore; break;
		case "carrot": t= this.enemyCarrot; break;
		case "bossN": t= this.bossN; break;
		case "bossG": t= this.bossG; break;
		case "bossA": t= this.bossA; break;
		}
		return t;
	}
}

class AniTemp{
	private String location;
	private float frameTime;
	public AniTemp(String location, float frameTime) {
		this.location = location;
		this.frameTime = frameTime;
	}
	public String getLocation() {
		return this.location;
	}
	public float getFrameTime() {
		return this.frameTime;
	}
}

class AnimationFactory{
	public static AnimationData createAnimationData(AniTemp[] aniArr, double multiplier, GL2 gl) {
		int[] spriteSize = new int[2];
		int[] realSpriteSize = new int[2];
		FrameDef[] frames = new FrameDef[aniArr.length];
		for(int i =0; i<aniArr.length; i++) {
			int spriteTex = JavaTemplate.glTexImageTGAFile(gl, aniArr[i].getLocation(), spriteSize);
			realSpriteSize[0] = (int) (spriteSize[0] * multiplier);
			realSpriteSize[1] = (int) (spriteSize[1] * multiplier);
			frames[i] = new FrameDef(spriteTex, aniArr[i].getFrameTime(), realSpriteSize);
		}
		AnimationDef def = new AnimationDef(frames);
		return new AnimationData(def);
	}
	public static AnimationDataHold createAnimationDataHold(AniTemp[] aniArr, double multiplier, GL2 gl) {
		int[] spriteSize = new int[2];
		int[] realSpriteSize = new int[2];
		FrameDef[] frames = new FrameDef[aniArr.length];
		for(int i =0; i<aniArr.length; i++) {
			int spriteTex = JavaTemplate.glTexImageTGAFile(gl, aniArr[i].getLocation(), spriteSize);
			realSpriteSize[0] = (int) (spriteSize[0] * multiplier);
			realSpriteSize[1] = (int) (spriteSize[1] * multiplier);
			frames[i] = new FrameDef(spriteTex, aniArr[i].getFrameTime(), realSpriteSize);
		}
		AnimationDef def = new AnimationDef(frames);
		return new AnimationDataHold(def);
	}
}
