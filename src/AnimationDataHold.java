
public class AnimationDataHold extends AnimationData{
	public AnimationDataHold(AnimationDef def){
		super(def);
	}
	public boolean update(float deltaTimeMS){
		this.setSecsNext(this.getSecsNext() - deltaTimeMS);
		while(this.getSecsNext() < 0){
			this.setCurFrame(getCurFrame() + 1);
			if(this.getCurFrame() >= this.getAniDef().frameArraySize()){
				this.setCurFrame(this.getAniDef().frameArraySize()-1);
			}
			this.setSecsNext(this.getSecsNext() + this.getAniDef().getFrame(getCurFrame()).getFrameTime());
		}
		return false;
	}
}
