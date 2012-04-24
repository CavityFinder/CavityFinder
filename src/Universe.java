import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

public class Universe {
	private byte[][][] atoms; // probe = 0, empty = -1;
	private double minX, minY, minZ, resolution, probeR;

	public Universe(double resolution, double probeR, double minX, double maxX,
			double minY, double maxY, double minZ, double maxZ) {
		atoms = new byte[(int) ((maxX - minX) / resolution) + 2][(int) ((maxY - minY) / resolution) + 2][(int) ((maxZ - minZ) / resolution) + 2];
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.resolution = resolution;
		this.probeR = probeR;
	}

	private int xToGrid(double x) {
		return (int) (1+(x - minX) / resolution);
	}

	private int yToGrid(double y) {
		return (int) (1+(y - minY) / resolution);
	}

	private int zToGrid(double z) {
		return (int) (1+(z - minZ) / resolution);
	}

	private double gridToX(int x) {
		return (x-1) * resolution + minX;
	}

	private double gridToY(int y) {
		return (y-1) * resolution + minY;
	}

	private double gridToZ(int z) {
		return (z-1) * resolution + minZ;
	}
	/**
	 * For every probe atom within the bounding box of every protein atom,
	 * erase the probe atom if it intersects the protein atom.
	 * @param proteinAtoms
	 */
	public void eraseProbeAtoms(ArrayList<Atom> proteinAtoms) {
		for (Atom atom : proteinAtoms) {
			for (int i = xToGrid(atom.x - atom.r); i <= xToGrid(atom.x + atom.r); i++) {
				for (int j = yToGrid(atom.y - atom.r); j < yToGrid(atom.y
						+ atom.r); j++) {
					for (int k = zToGrid(atom.z - atom.r); k < zToGrid(atom.z
							+ atom.r); k++) {
						if (i >= 0 && j >= 0 && k >= 0 && i < atoms.length
								&& j < atoms[0].length
								&& k < atoms[0][0].length) {
							if (atoms[i][j][k] == 0
									&& (((atom.x - gridToX(i))
											* (atom.x - gridToX(i))
											+ (atom.y - gridToY(j))
											* (atom.y - gridToY(j)) + (atom.z - gridToZ(k))
											* (atom.z - gridToZ(k))) < (probeR + atom.r)
											* (probeR + atom.r)))
								atoms[i][j][k] = -1;
						}
					}
				}
			}
		}
	}

	public void print(PrintWriter p) {
		int count = 1;
		for (int i = 0; i < atoms.length; i++) {
			for (int j = 0; j < atoms[0].length; j++) {
				for (int k = 0; k < atoms[0][0].length; k++) {
					if (atoms[i][j][k] == 0)
						p.format("ATOM  " + (10000 + count) + "  MC  CAV  "
								+ (5000 + count++)
								+ "     %7.3f %7.3f %7.3f  1.00 %5.2f" + "\n",
								gridToX(i), gridToY(j), gridToZ(k), probeR);
				}
			}
		}
	}

	public void eraseNonCavities() {
		Stack<int[]> stack = new Stack<int[]>();
		stack.push(new int[] { 0, 0, 0 });
		while (stack.size() > 0) {
			int[] loc = stack.pop();
			int x = loc[0];
			int y = loc[1];
			int z = loc[2];
			if (atoms[x][y][z] != -1) {
				atoms[x][y][z] = -1;
				if (x + 1 < atoms.length && atoms[x + 1][y][z] == 0)
					stack.push(new int[] { x + 1, y, z });
				if (x > 0 && atoms[x - 1][y][z] == 0)
					stack.push(new int[] { x - 1, y, z });
				if (y + 1 < atoms[0].length && atoms[x][y + 1][z] == 0)
					stack.push(new int[] { x, y + 1, z });
				if (y > 0 && atoms[x][y - 1][z] == 0)
					stack.push(new int[] { x, y - 1, z });
				if (z + 1 < atoms[0][0].length && atoms[x][y][z + 1] == 0)
					stack.push(new int[] { x, y, z + 1 });
				if (z > 0 && atoms[x][y][z - 1] == 0)
					stack.push(new int[] { x, y, z - 1 });
			}
		}
	}
}
