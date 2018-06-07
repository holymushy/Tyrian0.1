import java.util.HashMap;
import java.util.Map;

import com.jogamp.opengl.GL2;

public class BackgroundLoader {
	private Map<Integer, BGTile> intToTile = new HashMap<Integer,BGTile>();
	public BackgroundDef bg;
	public BackgroundLoader(GL2 gl){
		int[] bgArray = BGArrayStore.level1Array;
		double multiplier = 2;
		int[] spriteSize = new int[2];
		int[] realSpriteSize = new int[2];
		int spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Desert.tga", spriteSize);
		realSpriteSize[0] = (int) (spriteSize[0] * multiplier);
		realSpriteSize[1] = (int) (spriteSize[1] * multiplier);
		intToTile.put(0, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/DesertPlant.tga", spriteSize);
		intToTile.put(1, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/SmallJames.tga", spriteSize);
		intToTile.put(2, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Tree.tga", spriteSize);
		intToTile.put(3, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Bunker0.tga", spriteSize);
		intToTile.put(4, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Bunker1.tga", spriteSize);
		intToTile.put(5, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Bunker2.tga", spriteSize);
		intToTile.put(6, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Bunker3.tga", spriteSize);
		intToTile.put(7, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Bunker4.tga", spriteSize);
		intToTile.put(8, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Bunker5.tga", spriteSize);
		intToTile.put(9, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Bunker6.tga", spriteSize);
		intToTile.put(10, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Bunker7.tga", spriteSize);
		intToTile.put(11, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Bunker8.tga", spriteSize);
		intToTile.put(12, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Oasis0.tga", spriteSize);
		intToTile.put(13, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Oasis1.tga", spriteSize);
		intToTile.put(14, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Oasis2.tga", spriteSize);
		intToTile.put(15, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Oasis3.tga", spriteSize);
		intToTile.put(16, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Oasis4.tga", spriteSize);
		intToTile.put(17, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Oasis5.tga", spriteSize);
		intToTile.put(18, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Temple0.tga", spriteSize);
		intToTile.put(19, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Temple1.tga", spriteSize);
		intToTile.put(20, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Temple2.tga", spriteSize);
		intToTile.put(21, new BGTile(spriteTex, realSpriteSize, false));
		
		spriteTex = JavaTemplate.glTexImageTGAFile(gl, "BGTGA/Temple3.tga", spriteSize);
		intToTile.put(22, new BGTile(spriteTex, realSpriteSize, false));
		
		bg = new BackgroundDef(20, 100, this.intArraytoTileArray(bgArray));
	}
	public BGTile[] intArraytoTileArray(int[] bgArray){
		BGTile[] tiles = new BGTile[bgArray.length];
		for(int i =0; i<bgArray.length; i++){
			tiles[i] = intToTile.get(bgArray[i]);
		}
		return tiles;
	}
	public BackgroundDef getBG(){
		return this.bg;
	}
}

class BGArrayStore{
	public static final int[] level1Array = 
		{4,5,6,4,5,6,4,5,6,4,5,6,4,5,6,4,5,6,19,20,
				7,8,9,7,8,9,7,8,9,7,8,9,7,8,9,7,8,9,21,22,
				10,11,12,10,11,12,10,11,12,10,11,12,10,11,12,10,11,12,3,1,
				13,14,15,13,14,15,13,14,15,13,14,15,13,14,15,13,14,15,1,2,
				16,17,18,16,17,18,16,17,18,16,17,18,16,17,18,16,17,18,3,1,

				4,5,6,4,5,6,4,5,6,4,5,6,4,5,6,4,5,6,19,20,
				7,8,9,7,8,9,7,8,9,7,8,9,7,8,9,7,8,9,21,22,
				10,11,12,10,11,12,10,11,12,10,11,12,10,11,12,10,11,12,3,1,
				13,14,15,13,14,15,13,14,15,13,14,15,13,14,15,13,14,15,1,2,
				16,17,18,16,17,18,16,17,18,16,17,18,16,17,18,16,17,18,3,1,

				0,1,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,
				0,0,1,0,0,0,3,2,2,0,0,3,0,1,0,0,1,0,0,0,
				0,2,0,1,0,3,0,2,2,0,0,0,1,1,0,3,1,0,3,0,
				0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,
				0,0,1,0,3,0,1,0,0,0,0,0,0,2,1,0,0,0,0,0,

				1,0,0,0,1,0,0,1,0,2,2,2,2,2,2,2,0,2,0,0,
				0,1,0,1,0,2,0,0,0,0,2,13,14,15,2,0,2,0,0,0,
				0,1,2,0,0,1,0,1,0,0,2,16,17,18,2,0,0,2,0,0,
				0,0,1,0,0,0,0,1,0,0,2,13,14,15,2,1,0,0,2,0,
				0,0,2,0,1,0,0,0,0,0,2,16,17,18,2,0,2,0,0,1,

				0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,
				0,1,3,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,
				0,1,0,3,1,0,4,5,6,0,0,0,0,0,13,14,15,0,0,2,
				0,1,0,0,3,0,7,8,9,0,0,0,0,0,16,17,18,0,19,20,
				0,0,2,2,0,0,10,11,12,0,1,1,1,0,0,0,0,0,21,22,

				0,3,3,3,3,0,0,0,0,0,2,0,3,0,0,0,0,1,19,20,
				1,1,1,2,3,0,1,2,0,2,0,0,3,1,0,0,0,1,21,22,
				4,5,6,2,3,0,1,2,2,0,0,1,3,0,1,0,0,1,1,1,
				7,8,9,2,3,0,1,0,0,0,1,0,3,0,1,0,0,0,0,0,
				10,11,12,19,20,0,3,0,0,0,0,3,3,3,0,0,0,0,0,0,

				1,0,0,21,22,0,0,0,1,0,0,0,19,20,0,0,2,0,0,0,
				0,1,2,2,2,2,2,1,0,2,0,0,21,22,0,2,0,2,0,0,
				0,0,1,0,0,0,1,0,0,0,2,0,0,0,2,0,0,0,2,0,
				0,0,0,1,0,1,3,3,3,3,3,2,0,2,0,0,0,0,0,2,
				0,0,0,0,1,0,0,0,0,0,0,0,2,0,3,3,3,3,3,3,

				0,0,2,3,1,2,3,0,0,0,0,0,0,0,0,19,20,0,0,3,
				0,0,0,0,0,0,0,2,1,1,0,0,0,0,0,21,22,0,0,3,
				0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,3,
				1,1,1,0,0,0,2,2,2,0,0,0,0,0,0,0,0,1,0,2,
				0,0,0,0,0,0,3,3,3,0,2,2,2,0,1,1,1,0,2,2,

				19,20,0,19,20,0,19,20,0,0,0,0,0,0,0,0,0,0,0,0,
				21,22,0,21,22,0,21,22,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,4,5,6,0,2,2,2,2,2,2,0,0,0,
				0,0,0,0,0,0,0,7,8,9,0,2,13,14,15,2,0,0,0,0,
				0,0,0,0,0,0,0,10,11,12,0,2,16,17,18,2,0,0,0,0,

				0,0,0,19,20,0,1,1,1,1,1,1,0,0,0,19,20,0,0,0,
				0,0,0,21,22,0,1,13,14,15,1,0,0,3,3,21,22,0,0,0,
				4,5,6,0,0,0,1,16,17,18,1,0,0,3,3,0,0,4,5,6,
				7,8,9,0,0,2,0,3,1,2,2,2,1,0,0,0,0,7,8,9,
				10,11,12,3,0,1,2,1,1,0,2,0,3,3,3,0,0,10,11,12,

				0,0,0,0,0,1,0,2,0,3,0,0,0,0,0,1,1,13,14,15,
				0,4,5,6,4,5,6,0,0,0,19,20,0,0,0,1,1,16,17,18,
				0,7,8,9,7,8,9,0,0,0,21,22,0,0,0,1,1,1,1,1,
				0,10,11,12,10,11,12,0,0,0,0,0,0,0,0,1,1,1,1,1,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,
				0,0,0,0,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,2,13,14,15,2,0,0,0,0,1,0,0,0,0,0,
				1,1,0,0,0,2,16,17,18,2,0,0,0,0,2,0,0,0,0,0,
				1,0,0,0,0,2,2,2,2,2,2,0,1,0,0,0,0,0,0,0,
				1,0,0,0,0,0,0,0,0,0,0,0,0,1,2,0,0,0,0,0,
				1,0,0,0,0,0,2,2,0,0,0,0,0,1,2,0,0,0,0,0,
				0,0,19,20,19,20,1,0,0,0,0,0,0,1,1,0,0,1,2,1,
				0,0,21,22,21,22,1,0,0,0,0,0,4,5,6,0,0,1,3,1,
				0,0,0,0,0,0,0,0,0,0,1,3,7,8,9,0,0,1,2,1,
				2,2,2,2,2,0,0,0,0,1,1,3,10,11,12,0,0,0,0,0,
				13,14,15,2,0,0,2,1,1,0,0,0,0,0,0,0,0,0,19,20,
				16,17,18,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21,22,
				4,5,6,4,5,6,4,5,6,3,3,2,0,0,0,0,0,0,0,0,
				7,8,9,7,8,9,7,8,9,3,3,2,0,0,0,0,0,0,0,0,
				10,11,12,10,11,12,10,11,12,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,1,1,1,0,0,0,0,0,0,0,0,0,4,5,6,0,0,0,
				0,0,0,0,0,0,0,1,0,0,0,0,0,0,7,8,9,0,0,0,
				0,0,3,3,3,0,1,0,2,0,0,0,0,0,10,11,12,0,0,0,
				0,0,0,0,0,1,0,0,0,3,0,0,0,0,1,1,1,0,0,0,
				0,0,2,2,2,0,0,0,0,0,0,0,0,0,4,5,6,0,0,0,
				0,0,0,0,0,0,0,0,0,3,3,0,0,0,7,8,9,0,0,0,
				0,0,0,0,0,0,0,19,20,3,0,0,0,0,10,11,12,0,0,0,
				0,0,0,0,0,0,0,21,22,3,0,0,0,0,0,0,0,0,0,0,
				0,0,0,2,2,0,0,0,0,3,3,0,0,0,0,0,0,0,0,0,
				0,0,2,3,3,2,0,0,0,0,0,0,0,1,1,1,1,1,0,0,
				0,0,2,3,3,2,0,0,0,0,0,0,0,1,1,1,1,1,0,0,
				0,0,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,3,3,3,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,
				0,3,3,3,2,2,2,1,0,0,0,0,0,0,0,0,0,0,0,0,
				0,3,13,14,15,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,3,16,17,18,2,1,0,0,0,0,0,0,0,0,0,0,0,0,1,
				0,3,3,3,3,3,1,0,0,0,0,0,0,0,0,0,0,0,0,1,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
				0,0,3,2,1,2,2,2,0,0,0,0,0,0,0,19,20,0,19,20,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21,22,0,21,22,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,1,1,3,3,0,3,3,0,0,0,0,0,0,0,0,0,0,0,
				0,0,2,2,2,3,0,3,3,0,0,0,0,0,1,1,1,1,0,0,
				0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,2,2,1,0,0,
				0,1,1,1,1,1,1,0,0,0,0,0,0,0,3,3,2,1,0,0,
				0,1,13,14,15,1,0,0,0,0,0,0,0,3,3,2,1,0,0,0,
				0,1,16,17,18,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0};
	public BGArrayStore(){
		
	}
}
