import java.io.Serializable;

public class AnimationData implements Serializable{
	private AnimationDef def;
	private int curFrame = 0;
	private float secsUntilNextFrame;
	private boolean looped;

	public AnimationData(AnimationDef def) {
		this.def = def;
		secsUntilNextFrame = def.getFrame(curFrame).getFrameTime();
	}
	
	public boolean update(float deltaTime) {
		looped = false;
		secsUntilNextFrame -= deltaTime;
		while (secsUntilNextFrame <= 0) {
			curFrame++;
			if (curFrame >= def.frameArraySize()) {
				curFrame = 0;
				looped = true;
			}
			secsUntilNextFrame += def.getFrame(curFrame).getFrameTime();
		}
		return looped;
	}
	public AnimationDef getAniDef(){
		return this.def;
	}
	public float getSecsNext(){
		return this.secsUntilNextFrame;
	}
	public void setSecsNext(float next){
		this.secsUntilNextFrame = next;
	}
	public int getCurrentImage() {
		return def.getFrame(curFrame).getImage();
	}
	public int[] getCurrentImageSize(){
		return def.getFrameSize(curFrame);
	}
	public int getCurFrame(){
		return this.curFrame;
	}
	public void setCurFrame(int frame){
		this.curFrame = frame;
	}
}
