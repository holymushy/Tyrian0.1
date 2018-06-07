import com.jogamp.opengl.GL2;

public class AnimationLoader {
	private AnimationData leftData, midData, rightData, playerProjectile, enemyGenCore, bossN, 
		bossG, bossA, enemyCarrot, bossP2Projectile,enemyProjectile;
	public AnimationLoader(GL2 gl){
		/* Animation Data for the player down below*/
		double multiplier = 2;
		int[] spriteSize = new int[2];
		int[] realSpriteSize = new int[2];
		
		//left animation
		int spriteTex = JavaTemplate.glTexImageTGAFile(gl, "Player/Left2.tga", spriteSize);
		realSpriteSize[0] = (int) (spriteSize[0] * multiplier);
		realSpriteSize[1] = (int) (spriteSize[1] * multiplier);
		FrameDef left2 = new FrameDef(spriteTex, 1000, realSpriteSize);
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "Player/Left1.tga", spriteSize);
		FrameDef left1 = new FrameDef(spriteTex, 100, realSpriteSize);
		
		FrameDef[] leftFrames = {left1, left2};
		AnimationDef left = new AnimationDef(leftFrames);
		leftData = new AnimationDataHold(left);
		
		//middle "animation"
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "Player/Middle.tga", spriteSize);
		FrameDef normal = new FrameDef(spriteTex, 1000, realSpriteSize);
		FrameDef[] normalFrames = {normal};
		AnimationDef mid = new AnimationDef(normalFrames);
		midData = new AnimationData(mid);
		
		
		//right animation
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "Player/Right1.tga", spriteSize);
		FrameDef right1 = new FrameDef(spriteTex, 100, realSpriteSize);
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "Player/Right2.tga", spriteSize);
		FrameDef right2 = new FrameDef(spriteTex, 1000, realSpriteSize);
		
		FrameDef[] rightFrames = {right1,right2};
		AnimationDef right = new AnimationDef(rightFrames);
		rightData = new AnimationDataHold(right);
		
		
		
		/* Animation Data for projectiles below*/
		//main
		multiplier = 1;
		spriteSize = new int[2];
		realSpriteSize = new int[2];
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "Projectiles/bulletss.tga", spriteSize);
		realSpriteSize[0] = (int) (spriteSize[0] * multiplier);
		realSpriteSize[1] = (int) (spriteSize[1] * multiplier);
		FrameDef playerPew = new FrameDef(spriteTex, 1000, realSpriteSize);
		FrameDef[] playerper = {playerPew};
		AnimationDef playerPewPew = new AnimationDef(playerper);
		playerProjectile = new AnimationData(playerPewPew);
		
		multiplier = 1;
		spriteSize = new int[2];
		realSpriteSize = new int[2];
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "Projectiles/bullet.tga", spriteSize);
		realSpriteSize[0] = (int) (spriteSize[0] * multiplier);
		realSpriteSize[1] = (int) (spriteSize[1] * multiplier);
		this.enemyProjectile = new AnimationData(new AnimationDef(new FrameDef[] {new FrameDef(spriteTex, 1000, realSpriteSize)}));
		
		
		
		//boss phase2 / fireball
		multiplier = 1;
		spriteSize = new int[2];
		realSpriteSize = new int[2];
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "Projectiles/fireBall.tga", spriteSize);
		realSpriteSize[0] = (int) (spriteSize[0] * multiplier);
		realSpriteSize[1] = (int) (spriteSize[1] * multiplier);
		this.bossP2Projectile = new AnimationData(new AnimationDef(new FrameDef[] {new FrameDef(spriteTex, 1000, realSpriteSize)}));
		
		/* Animation Data for enemies below*/
		//Gencore
		multiplier = 2;
		spriteSize = new int[2];
		realSpriteSize = new int[2];
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "Enemies/gencoreMiddle0.tga", spriteSize);
		realSpriteSize[0] = (int) (spriteSize[0] * multiplier);
		realSpriteSize[1] = (int) (spriteSize[1] * multiplier);
		FrameDef gencore = new FrameDef(spriteTex, 1000, realSpriteSize);
		FrameDef[] gencoreArray = {gencore};
		AnimationDef gencoreDef = new AnimationDef(gencoreArray);
		enemyGenCore = new AnimationData(gencoreDef);
		
		//Carrot
		multiplier = 2;
		spriteSize = new int[2];
		realSpriteSize = new int[2];
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "Enemies/superCarrotMiddle0.tga", spriteSize);
		realSpriteSize[0] = (int) (spriteSize[0] * multiplier);
		realSpriteSize[1] = (int) (spriteSize[1] * multiplier);
		FrameDef carrot = new FrameDef(spriteTex, 1000, realSpriteSize);
		AnimationDef carrotDef = new AnimationDef(new FrameDef[]{carrot});
		enemyCarrot = new AnimationData(carrotDef);
		
		//boss
		multiplier = 2;
		spriteSize = new int[2];
		realSpriteSize = new int[2];
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "Boss/bossNormal.tga", spriteSize);
		realSpriteSize[0] = (int) (spriteSize[0] * multiplier);
		realSpriteSize[1] = (int) (spriteSize[1] * multiplier);
		FrameDef boss = new FrameDef(spriteTex, 1000, realSpriteSize);
		AnimationDef bossDef = new AnimationDef(new FrameDef[] {boss});
		bossN = new AnimationData(bossDef);
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "Boss/bossGettingAngry.tga", spriteSize);
		realSpriteSize[0] = (int) (spriteSize[0] * multiplier);
		realSpriteSize[1] = (int) (spriteSize[1] * multiplier);
		FrameDef bossGA = new FrameDef(spriteTex, 1000, realSpriteSize);
		AnimationDef bossGADef = new AnimationDef(new FrameDef[] {bossGA});
		bossG = new AnimationData(bossGADef);
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "Boss/bossAngry.tga", spriteSize);
		realSpriteSize[0] = (int) (spriteSize[0] * multiplier);
		realSpriteSize[1] = (int) (spriteSize[1] * multiplier);
		FrameDef bossAF = new FrameDef(spriteTex, 1000, realSpriteSize);
		AnimationDef bossADef = new AnimationDef(new FrameDef[] {bossAF});
		bossA = new AnimationData(bossADef);
		
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
