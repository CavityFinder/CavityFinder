/* 
I have not received unauthorized aid on this assignment. I understand
the answers that I have submitted. The answers submitted have not
been directly copied from another source, but instead are written in
my own words. 
*/


/**
 * Final Project
 * @authors Bryan Malyn (bmalyn)
 * 			John Mooring (jmooring)
 * 			Derek Chaconas (chaconas)
 */


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
	
	//Returns the information that the atom was constructed with.
	public String toString() {
		return info;
	}
}
