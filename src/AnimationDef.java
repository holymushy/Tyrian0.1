import java.io.Serializable;

public class AnimationDef implements Serializable{
	private static final long serialVersionUID = 2302551783622123590L;
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
