import java.io.Serializable;

public class FrameDef implements Serializable{
	private int image;
	private float frameTime;
	private int[] size;

	public FrameDef(int image, float frameTime, int[] size) {
		this.image = image;
		this.frameTime = frameTime;
		this.size = size;
	}

	public int getImage() {
		return this.image;
	}

	public float getFrameTime() {
		return this.frameTime;
	}
	public int[] getSize(){
		return this.size;
	}
}
