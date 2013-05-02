package main;

public  class MyPoint{
	private double pointX;
	private double pointY;
	public MyPoint(){	
	}
	public void setpoint(double x,double y){
		//System.out.println(x+","+y);
		this.pointX=x;
		this.pointY=y;	
	}
	public double getpointX(){
		return pointX;
	}
	public double getpointY(){
		return pointY;
	}
}
