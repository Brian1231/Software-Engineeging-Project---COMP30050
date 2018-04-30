package client.java.gameObjects;

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

	public int getPosition(){
		return this.position;
	}


	public void setState(boolean status){
		this.isActive = status;
	}

	public boolean isActive(){
		return this.isActive;
	}
}
