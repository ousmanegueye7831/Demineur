package sn.esp.dic2.projets.game.demineur.model;

public class Point {
	private static final long serialVersionUID = 1L;
	private int i;
	private int j;
	
	public Point(){}
	
	public Point(int i, int j){
		this.i = i;
		this.j = j;
	}
	
   //***** getteurs /setteurs  ******
	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	
	public boolean equals(Point p){
		return (this.getI() == p.getI() && this.getJ() == p.getJ());
	}

	@Override
	public String toString() {
		return "Point [i=" + i + ", j=" + j + "]";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
