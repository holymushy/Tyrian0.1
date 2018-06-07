import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.CountDownLatch;

enum SoundLoader{
	PEW("Sounds/laser pew.wav"),
	OHNO("Sounds/OHNO.wav"),
	OVERDRIVE("Sounds/Overdrive.wav"),
	HAIL2U("Sounds/Hail2You.wav");
	
	private final Sound sound;
	SoundLoader(String s){
		this.sound = Sound.loadFromFile(s);
	}
	public Sound getSound(){
		return this.sound;
	}
	public void playSound(){
		this.sound.play();
	}
	public Clip playSoundLoop(){
		return this.sound.playLooping();
	}

}

public class Sound {
    private byte[] mBytes;
    private DataLine.Info mInfo;
    private AudioFormat mFormat;

    private Sound() {}

    public static Sound loadFromFile(String filename) {
        try {
            Sound s = new Sound();
            File audioFile = new File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            s.mFormat = audioStream.getFormat();
            s.mInfo = new DataLine.Info(Clip.class, audioStream.getFormat());
            s.mBytes = new byte[(int)(s.mFormat.getFrameSize() * audioStream.getFrameLength())];
            audioStream.read(s.mBytes);
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void play() {
        try {
            Clip c = (Clip)AudioSystem.getLine(mInfo);
            c.open(mFormat, mBytes, 0, mBytes.length);
            
            c.addLineListener(new LineListener(){
				public void update(LineEvent event) {
					if (event.getType() == LineEvent.Type.STOP) {
			            event.getLine().close();
			            c.flush();
			            c.close();
			        }
				}
            	
            });
            c.start();
            
        } catch (Exception ex) {
            ;
        }
    }

    public Clip playLooping() {
        try {
            Clip c = (Clip)AudioSystem.getLine(mInfo);
            c.open(mFormat, mBytes, 0, mBytes.length);
            c.loop(Clip.LOOP_CONTINUOUSLY);
            return c;
        } catch (Exception ex) {
            return null;
        }
    }
    
}