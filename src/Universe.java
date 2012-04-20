
public class Universe {
	public void eraseNonCavities(byte[][][] atoms, int cx, int cy, int cz) {
		if (atoms[cx][cy][cz] ==-1)
			return;
		atoms[cx][cy][cz]= -1;
		for (int i = cx - 1; i <= cx + 1; i++) {
			for (int j = cy - 1; j < cy + 1; j++) {
				for (int k = cz - 1; k < cz + 1; k++) {
					if (!(i == cx && j == cy && k == cz) && i >= 0 && j >= 0
							&& k >= 0 && i < atoms.length
							&& j < atoms[0].length && k < atoms[0][0].length) {
						eraseNonCavities(atoms,i,j,k);
					}
				}
			}
		}
	}
}
