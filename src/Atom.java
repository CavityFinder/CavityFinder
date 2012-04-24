public class Atom {
	private String info;
	public double x, y, z, r;

	//Stores all the information of the atom, its location, and its radius. 
	public Atom(String info, double x, double y, double z, double r) {
		this.info = info;
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
	}
	
	//Returns the information that the atom was constucted with.
	public String toString() {
		return info;
	}
}
