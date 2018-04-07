import java.awt.Polygon;

/**
 * Classe polygone
 * Hérite de la classe Polygon issue de java.awt (facilité d'utilisation)
 */
public class Polygone extends Polygon {
	/**
	 * Constructeur de la classe Polygone
	 * @param x : coordonnées en x des points du polygone
	 * @param y : coordonnées en y des points du polygone
	 * @param nbPoints : nombre de points du polygone
	 */
	public Polygone(int[] x, int[] y, int nbPoints) {
		super(x, y, nbPoints); // Initialisation des points de la classe Polygon
	}

	/**
	 * Méthode aire
	 * Calcul l'aire du polygone à partir des points
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
	 * Méthode périmètre
	 * Calcul le périmètre du polygone à partir des points
	 * @return Retourne le périmètre du polygone
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
	 * Retourne les coordonnées en x du polygone
	 * @return xpoints
	 */
	public int[] getpX() {
		return xpoints;
	}

	/**
	 * Retourne les coordonnées en y du polygone
	 * @return ypoints
	 */
	public int[] getpY() {
		return ypoints;
	}

	// Setters
	/**
	 * Ajoute les coordonnées en x du polygone
	 * @param x : tableau des coordonnées en x
	 */
	public void setpX(int[] x) {
		xpoints = x;
	}

	/**
	 * Ajoute les coordonnées en y du polygone
	 * @param y : tableau des coordonnées en y
	 */
	public void setpY(int[] y) {
		ypoints = y;
	}
}