import java.io.Serializable;

public class AnimationDef implements Serializable{
	private FrameDef[] frames;

	public AnimationDef(FrameDef[] frames) {
		this.frames = frames;
	}

	public FrameDef getFrame(int x) {
		return frames[x];
	}
	
	public int[] getFrameSize(int x){
		return frames[x].getSize();
	}

	public int frameArraySize() {
		return frames.length;
	}
}
