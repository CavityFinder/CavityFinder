public class Atom {
	private String info;
	private double x;
	private double y;
	private double z;
	private double r;

	public Atom(String info, double x, double y, double z, double r){
		this.info = info;
		this.x=x;
		this.y=y;
		this.z=z;
		this.r=r;
	}
	
	public String toString(){
	  return info;
	}
}
