import java.awt.Polygon;

/**
 * Classe polygone
 * H�rite de la classe Polygon issue de java.awt (facilit� d'utilisation)
 */
public class Polygone extends Polygon {
	/**
	 * Constructeur de la classe Polygone
	 * @param x : coordonn�es en x des points du polygone
	 * @param y : coordonn�es en y des points du polygone
	 * @param nbPoints : nombre de points du polygone
	 */
	public Polygone(int[] x, int[] y, int nbPoints) {
		super(x, y, nbPoints); // Initialisation des points de la classe Polygon
	}

	/**
	 * M�thode aire
	 * Calcul l'aire du polygone � partir des points
	 * @return Retourne l'aire du polygone
	 */
	double aire() {
		double aire = 0;
		for (int i = 0; i < xpoints.length; i++) { // Parcours des x du polygone
			aire += xpoints[i] * ypoints[(i + 1) % xpoints.length] - ypoints[i] * xpoints[(i + 1) % xpoints.length];
		}
		return Math.abs(aire / 2);
	}

	/**
	 * M�thode p�rim�tre
	 * Calcul le p�rim�tre du polygone � partir des points
	 * @return Retourne le p�rim�tre du polygone
	 */
	double perimetre() {
		double perimetre = 0, x, y;
		for (int i = 0; i < xpoints.length; i++) {
			x = xpoints[(i + 1) % xpoints.length] - xpoints[i];
			y = ypoints[(i + 1) % xpoints.length] - ypoints[i];
			perimetre += Math.sqrt(x * x + y * y);
		}
		return perimetre;
	}
	
	// Getters
	/**
	 * Retourne les coordonn�es en x du polygone
	 * @return xpoints
	 */
	public int[] getpX() {
		return xpoints;
	}

	/**
	 * Retourne les coordonn�es en y du polygone
	 * @return ypoints
	 */
	public int[] getpY() {
		return ypoints;
	}

	// Setters
	/**
	 * Ajoute les coordonn�es en x du polygone
	 * @param x : tableau des coordonn�es en x
	 */
	public void setpX(int[] x) {
		xpoints = x;
	}

	/**
	 * Ajoute les coordonn�es en y du polygone
	 * @param y : tableau des coordonn�es en y
	 */
	public void setpY(int[] y) {
		ypoints = y;
	}
}