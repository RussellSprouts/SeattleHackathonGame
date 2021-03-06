package main;

public class N64Controller{
	private double x;
	private double y;
	private boolean button5;
	private boolean button4;
	private boolean trigger;
	private boolean rButton;
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public boolean getButtonJump(){
		return button5;
	}
	
	public boolean getButtonJetpack(){
		return button4;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setR(boolean v){
		rButton = v;
	}
	
	public boolean getR(){
		return rButton;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void setButtonJump(boolean v){
		button5 = v;
	}
	
	public void setButtonJetpack(boolean v){
		button4 = v;
	}
	
	public void trigger(boolean v){
		trigger = true;
	}
	
	public boolean getTrigger(){
		if( trigger ) {
			trigger = false;
			return true;
		}
		return false;
	}
}
