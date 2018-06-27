import com.jogamp.nativewindow.WindowClosingProtocol;
import javax.sound.sampled.*;
import com.jogamp.opengl.*;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JavaTemplate {
	// Set this to true to make the game loop exit.
	private static boolean shouldExit;
	
	// The previous frame's keyboard state.
	private static boolean kbPrevState[] = new boolean[256];

	// The current frame's keyboard state.
	private static boolean kbState[] = new boolean[256];

	private static Map<String, Letter> font = new HashMap<String, Letter>();

	private static Level curLevel;

	public static void main(String[] args) {
		GLProfile gl2Profile;

		try {
			// Make sure we have a recent version of OpenGL
			gl2Profile = GLProfile.get(GLProfile.GL2);
		} catch (GLException ex) {
			System.out.println("OpenGL max supported version is too low.");
			System.exit(1);
			return;
		}

		// Create the window and OpenGL context.
		GLWindow window = GLWindow.create(new GLCapabilities(gl2Profile));
		window.setSize(800, 600);
		window.setTitle("Tyrian 0.1");
		window.setVisible(true);
		window.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
		window.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.isAutoRepeat()) {
					return;
				}
				kbState[keyEvent.getKeyCode()] = true;
			}

			@Override
			public void keyReleased(KeyEvent keyEvent) {
				if (keyEvent.isAutoRepeat()) {
					return;
				}
				kbState[keyEvent.getKeyCode()] = false;
			}
		});

		// Setup OpenGL state.
		window.getContext().makeCurrent();
		GL2 gl = window.getGL().getGL2();
		gl.glViewport(0, 0, 800, 600);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glOrtho(0, 800, 600, 0, 0, 100);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

		// Game initialization goes here.

		for (char i = 'A'; i <= 'Z'; i++) {
			String s = "TGA/" + Character.toString(i) + ".tga";
			int[] Size = new int[2];
			int tex = glTexImageTGAFile(gl, s, Size);
			Letter l = new Letter(Size[0], Size[1], tex);
			font.put(Character.toString(i), l);
		}

		for (int i = 0; i <= 9; i++) {
			String s = "TGA/" + Integer.toString(i) + ".tga";
			int[] Size = new int[2];
			int tex = glTexImageTGAFile(gl, s, Size);
			Letter l = new Letter(Size[0], Size[1], tex);
			font.put(Integer.toString(i), l);
		}
		
		int[] Size = new int[2];
		int tex =  glTexImageTGAFile(gl, "TGA/colon.tga", Size);
		Letter colon = new Letter(Size[0], Size[1], tex);
		font.put(":", colon);
		
		
		curLevel = LevelLoader.loadLevel1(gl);
		BackgroundDef bgDef = curLevel.getBG();
		Camera camera = curLevel.getCamera();
		Clip curClip = SoundLoader.OVERDRIVE.playSoundLoop(); //background sound
		curClip.stop();
		long clipReset = 59000000;
		AnimationLoader a = new AnimationLoader(gl);
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		Player p = curLevel.getPlayer();
		Boss boss = null;
		boolean BossSpawn = false;
		boolean bossDead = false;
		
		int playerSpeed = 5;
		ArrayList<Projectile> playerProjectiles = new ArrayList<Projectile>();
		ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
		ArrayList<SpawnPoint> spawns = curLevel.getSpawns();
		long lastFrameNS;
		long curFrameNS = System.nanoTime();
		int cameraScrollSpeed;

		int physicsDeltaMs = 10;
		int lastPhysicsFrameMs = (int) (System.nanoTime() / 1000000);

		String levelName = "menu";
		
		//ProjectileHandler handler= new ProjectileHandler();

		// The game loop
		while (!shouldExit) {
			System.arraycopy(kbState, 0, kbPrevState, 0, kbState.length);
			lastFrameNS = curFrameNS;
			curFrameNS = System.nanoTime();
			long curFrameMs = curFrameNS / 1000000;
			long deltaTimeMS = (curFrameNS - lastFrameNS) / 1000000;
			int numFrames = (int) (deltaTimeMS / 16);

			// Actually, this runs the entire OS message pump.
			window.display();

			if (!window.isVisible()) {
				shouldExit = true;
				break;
			}
			// Game logic goes here.

			if (levelName.equals("menu")) {
				curClip.stop();
				gl.glClearColor(0, 0, 0, 1);
				gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
				drawText("Press Enter to Play", gl, 150, 200, 0.5);
				drawText("Press i for instructions", gl, 150, 300, 0.5);
				if (kbState[KeyEvent.VK_ENTER]) {
					levelName = "reset";
				}
				if (kbState[KeyEvent.VK_I]) {
					levelName = "inst";
				}
				if (kbState[KeyEvent.VK_ESCAPE] &&  !kbPrevState[KeyEvent.VK_ESCAPE]) {
					shouldExit = true;
				}
			}
			else if(levelName.equals("inst")){
				gl.glClearColor(0, 0, 0, 1);
				gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
				drawText("WASD movement", gl, 20, 100, 0.5);
				drawText("L to shoot", gl, 20, 200, 0.5);
				drawText("ingame: enter to pause and unpause", gl, 20, 300, 0.5);
				drawText("Esc to exit", gl, 20, 400, 0.5);
				if (kbState[KeyEvent.VK_ESCAPE] &&  !kbPrevState[KeyEvent.VK_ESCAPE]) {
					levelName= "menu";
				}
			}
			else if(levelName.equals("reset")){
				curLevel.reset();
				camera = curLevel.getCamera();
				p = curLevel.getPlayer();
				spawns = curLevel.getSpawns();
				enemies.clear();
				playerProjectiles.clear();
				enemyProjectiles.clear();
				curClip.setMicrosecondPosition(clipReset);
				curClip.start();
				levelName = curLevel.getName();
				bossDead = false;
				boss = null;
				BossSpawn = false;
			}
			else if(levelName.equals("pause")){
				curClip.stop();
				deltaTimeMS = 0;
				gl.glClearColor(0, 0, 0, 1);
				gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
				
				bgDef.draw(camera, gl);
				for(Enemy ee: enemies){
					ee.draw(camera, gl, deltaTimeMS);
				}
				p.draw(camera, gl, deltaTimeMS);
				ProjectileHandler.drawProjectiles(playerProjectiles, camera, gl, deltaTimeMS, 0);
				ProjectileHandler.drawProjectiles(enemyProjectiles, camera, gl, deltaTimeMS, 0);
				if(boss != null){
					boss.draw(camera, gl, deltaTimeMS);
				}
				drawText("Player HP: "+ p.getHP(), gl, 0, 0, 0.5);
				drawText("Score: "+ (p.getEnemiesKilled() * 100), gl, 500, 0, 0.5);
				drawText("Game is paused ", gl, 10, 200, 0.5);
				if (kbState[KeyEvent.VK_ENTER] && !kbPrevState[KeyEvent.VK_ENTER]) {
					levelName = curLevel.getName();
					curClip.start();
				}
				if (kbState[KeyEvent.VK_ESCAPE] &&  !kbPrevState[KeyEvent.VK_ESCAPE]) {
					levelName= "menu";
				}
				
			}
			else if(bossDead){
				gl.glClearColor(0, 0, 0, 1);
				gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
				drawText("You beat the game", gl, 100, 100, 0.5);
				drawText("Press Escape to go the menu", gl, 100, 200, 0.5);
				drawText("Or press Enter to play again", gl, 100, 300, 0.5);
				drawText("Your Score: "+ (p.getEnemiesKilled() * 100), gl, 100, 400, 0.5);
				if (kbState[KeyEvent.VK_ESCAPE] &&  !kbPrevState[KeyEvent.VK_ESCAPE]) {
					levelName= "menu";
				}
				if (kbState[KeyEvent.VK_ENTER]) {
					levelName = "reset";
				}
			}
			else if (levelName.contains("level")) {
				if (p.isDead()) {
					curClip.stop();
					deltaTimeMS = 0;
					gl.glClearColor(0, 0, 0, 1);
					gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
					
					bgDef.draw(camera, gl);
					for(Enemy ee: enemies){
						ee.draw(camera, gl, deltaTimeMS);
					}
					p.draw(camera, gl, deltaTimeMS);
					ProjectileHandler.drawProjectiles(playerProjectiles, camera, gl, deltaTimeMS, 0);
					ProjectileHandler.drawProjectiles(enemyProjectiles, camera, gl, deltaTimeMS, 0);
					if(boss != null){
						boss.draw(camera, gl, deltaTimeMS);
					}
					drawText("Player HP: "+ p.getHP(), gl, 0, 0, 0.5);
					drawText("Press Escape to Restart", gl, 150, 200, 0.5);
					drawText("Score: "+ (p.getEnemiesKilled() * 100), gl, 500, 0, 0.5);
					
					if (kbState[KeyEvent.VK_ESCAPE] &&  !kbPrevState[KeyEvent.VK_ESCAPE]) {
						levelName= "reset";
					}
				} else {
					cameraScrollSpeed = -2 * numFrames;
					do {
						for (Projectile pro : playerProjectiles) {
							pro.move();
						}
						for (Projectile pro : enemyProjectiles) {
							pro.move();
						}
						
						ProjectileHandler.projectileVsPlayer(enemyProjectiles, p);
						ProjectileHandler.projectileVsEnemy(playerProjectiles, enemies,p);
						if(boss!= null){
							ProjectileHandler.projectileVsBoss(playerProjectiles, boss);
							if(boss.getHP() <= 0 && bossDead == false){ bossDead = true; p.addToEnemiesKilled(10);}
						}
						lastPhysicsFrameMs += physicsDeltaMs;
					} while (lastPhysicsFrameMs + physicsDeltaMs < curFrameMs);
					//do camera stuff
					if (camera.getY() > 0) {
						camera.addToY(cameraScrollSpeed);
						p.addToY(cameraScrollSpeed);
					}
					
					camera.stayInBG(bgDef);
					if(camera.getY() <=0 && !BossSpawn){
						BossSpawn = true;
						boss = curLevel.getBoss();
						SoundLoader.HAIL2U.playSound();
					}
					
					if(spawns.size() > 0) {
						if(camera.getY() <= spawns.get(0).getPoint()) {
							enemies.addAll(spawns.get(0).getEnemies());
							spawns.remove(0);
						}
					}
					
					//key press stuff
					if (kbState[KeyEvent.VK_W]) {
						p.moveAndCheck(0, -playerSpeed, numFrames, camera);
					}
					if (kbState[KeyEvent.VK_S]) {
						p.moveAndCheck(0, playerSpeed, numFrames, camera);
					}
					if (kbState[KeyEvent.VK_A]) {
						p.moveAndCheck(-playerSpeed, 0, numFrames, camera);
						p.changeAnimation(a.getPlayerAni("left"));
					} else if (kbState[KeyEvent.VK_D]) {
						p.moveAndCheck(playerSpeed, 0, numFrames, camera);
						p.changeAnimation(a.getPlayerAni("right"));
					} else
						p.changeAnimation(a.getPlayerAni("middle"));
					if (kbState[KeyEvent.VK_L]) {
						p.shoot(playerProjectiles, a.getProjectileAni("player"));
					}
					if (kbState[KeyEvent.VK_ESCAPE] &&  !kbPrevState[KeyEvent.VK_ESCAPE]) {
						levelName = "menu";
						curClip.stop();
					}
					if (kbState[KeyEvent.VK_ENTER] && !kbPrevState[KeyEvent.VK_ENTER]) {
						levelName = "pause";
					}
					
					// draw stuffs
					gl.glClearColor(0, 0, 0, 1);
					gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
					
					bgDef.draw(camera, gl);
					for(int i =0; i<enemies.size(); i++){
						if(enemies.get(i).getY() > camera.getY() + camera.getH()){
							enemies.remove(i);
							i--;
						}
						else if(p.AABBIntersect(enemies.get(i))){
							enemies.remove(i);
							i--;
							p.damage(10);
						}
						else{
							Enemy ee = enemies.get(i);
							ee.updateAI(numFrames,deltaTimeMS, enemyProjectiles, 
									new AnimationData[] {a.getProjectileAni("enemy")});
							ee.draw(camera, gl, deltaTimeMS);
						}
					}
					p.update(gl, deltaTimeMS, camera);
					ProjectileHandler.drawProjectiles(playerProjectiles, camera, gl, deltaTimeMS, cameraScrollSpeed);
					ProjectileHandler.drawProjectiles(enemyProjectiles, camera, gl, deltaTimeMS, cameraScrollSpeed);
					if(boss != null){
						if(boss.activated()) boss.moveCheck(deltaTimeMS,camera);
						boss.updateAI(numFrames, deltaTimeMS, enemyProjectiles, 
								new AnimationData[] {a.getProjectileAni("enemy"), a.getProjectileAni("boss2"),
										a.getProjectileAni("enemy")});
						boss.draw(camera, gl, deltaTimeMS);
					}
					drawText("Player HP: "+ p.getHP(), gl, 0, 0, 0.5);
					drawText("Score: "+ (p.getEnemiesKilled() * 100), gl, 500, 0, 0.5);
				}
			}

		}

	}

	// Load a file into an OpenGL texture and return that texture.
	public static int glTexImageTGAFile(GL2 gl, String filename, int[] out_size) {
		final int BPP = 4;

		DataInputStream file = null;
		try {
			// Open the file.
			file = new DataInputStream(new FileInputStream(filename));
		} catch (FileNotFoundException ex) {
			System.err.format("File: %s -- Could not open for reading.", filename);
			return 0;
		}

		try {
			// Skip first two bytes of data we don't need.
			file.skipBytes(2);

			// Read in the image type. For our purposes the image type
			// should be either a 2 or a 3.
			int imageTypeCode = file.readByte();
			if (imageTypeCode != 2 && imageTypeCode != 3) {
				file.close();
				System.err.format("File: %s -- Unsupported TGA type: %d", filename, imageTypeCode);
				return 0;
			}

			// Skip 9 bytes of data we don't need.
			file.skipBytes(9);

			int imageWidth = Short.reverseBytes(file.readShort());
			int imageHeight = Short.reverseBytes(file.readShort());
			int bitCount = file.readByte();
			file.skipBytes(1);

			// Allocate space for the image data and read it in.
			byte[] bytes = new byte[imageWidth * imageHeight * BPP];

			// Read in data.
			if (bitCount == 32) {
				for (int it = 0; it < imageWidth * imageHeight; ++it) {
					bytes[it * BPP + 0] = file.readByte();
					bytes[it * BPP + 1] = file.readByte();
					bytes[it * BPP + 2] = file.readByte();
					bytes[it * BPP + 3] = file.readByte();
				}
			} else {
				for (int it = 0; it < imageWidth * imageHeight; ++it) {
					bytes[it * BPP + 0] = file.readByte();
					bytes[it * BPP + 1] = file.readByte();
					bytes[it * BPP + 2] = file.readByte();
					bytes[it * BPP + 3] = -1;
				}
			}

			file.close();

			// Load into OpenGL
			int[] texArray = new int[1];
			gl.glGenTextures(1, texArray, 0);
			int tex = texArray[0];
			gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
			gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, imageWidth, imageHeight, 0, GL2.GL_BGRA,
					GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(bytes));
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);

			out_size[0] = imageWidth;
			out_size[1] = imageHeight;
			return tex;
		} catch (IOException ex) {
			System.err.format("File: %s -- Unexpected end of file.", filename);
			return 0;
		}
	}

	public static void glDrawSprite(GL2 gl, int tex, int x, int y, int w, int h) {
		gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
		gl.glBegin(GL2.GL_QUADS);
		{
			gl.glColor3ub((byte) -1, (byte) -1, (byte) -1);
			gl.glTexCoord2f(0, 1);
			gl.glVertex2i(x, y);
			gl.glTexCoord2f(1, 1);
			gl.glVertex2i(x + w, y);
			gl.glTexCoord2f(1, 0);
			gl.glVertex2i(x + w, y + h);
			gl.glTexCoord2f(0, 0);
			gl.glVertex2i(x, y + h);
		}
		gl.glEnd();
	}

	public static void drawText(String s, GL2 gl, int x, int y, double size) {
		for (int i = 0; i < s.length(); i++) {
			String key = s.substring(i, i + 1).toUpperCase();
			if (key.equals(" ")) {
				x += (int) (40 * size);
			} else {
				Letter l = font.get(key);
				int trueXSize = (int) (l.xSize * size);
				int trueYSize = (int) (l.ySize * size);
				glDrawSprite(gl, l.texture, x, y, trueXSize, trueYSize);
				x += trueXSize + 5;
			}
		}
	}
	
	public static Object deepClone(Object object) {
		   try {
		     ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     ObjectOutputStream oos = new ObjectOutputStream(baos);
		     oos.writeObject(object);
		     ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		     ObjectInputStream ois = new ObjectInputStream(bais);
		     return ois.readObject();
		   }
		   catch (Exception e) {
		     e.printStackTrace();
		     return null;
		   }
	}
	
	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

}
