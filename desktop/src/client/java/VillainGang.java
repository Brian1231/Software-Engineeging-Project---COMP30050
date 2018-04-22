package client.java;

public class VillainGang {

	private int position;
	private boolean isActive;
	
	public VillainGang(){
		this.position = 0;
		this.isActive = false;
	}
	
	public void setPosition(int pos){
		this.position = pos;
	}
	
	public void setState(boolean status){
		this.isActive = status;
	}
	
	public int getPos(){
		return this.position;
	}
	
	public boolean isActive(){
		return this.isActive;
	}
}
