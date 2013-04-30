package main;

public class Line {
	MyPoint pointOne;//一条线由两点构成
	MyPoint pointTwo;
	Line(){}
	
	public void setLine(MyPoint p1,MyPoint p2){
		pointOne=p1;
		pointTwo=p2;
	}
	public MyPoint getPointOne(){
		return pointOne;
	}
	public MyPoint getPointTwo(){
		return pointTwo;
	}
}
