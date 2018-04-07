import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.AffineTransform;

/**
 * Classe image
 */
public class Image {
	// Attributs priv�s
	private ArrayList<Shape> _formes; // Liste de formes
	private ArrayList<Area> _area; // Liste d'area : facilit� d'op�rations sur les formes
	private double _perimetre; // P�rim�tre total de l'image
	private double _aire; // Aire totale de l'image
 
    /**
     * Constructeur de la classe Image
     */
    Image() {
    	// Initialisation des attributs
    	_formes = new ArrayList<Shape>();
    	_area = new ArrayList<Area>();
    	_perimetre = 0;
    	_aire = 0;
	}

    /**
     * Ajout d'une nouvelle image
     * @param image
     */
	public void ajouterImage(Image image) {
	    Area areaImage = new Area();
	    for (Area areas : _area)
	        areaImage.add(areas);

	    Rectangle2D rectangle = areaImage.getBounds2D();
	    Area copyArea= new Area();
	    for (Area areas : image.getListArea())
	        copyArea.add(areas);

	    Rectangle2D rectangle2 = copyArea.getBounds2D();
	    double x = rectangle.getX() + rectangle.getWidth() / 2 - rectangle2.getX() - rectangle2.getWidth() / 2;
	    double y = rectangle.getY() + rectangle.getHeight() / 2 - rectangle2.getY() - rectangle2.getHeight() / 2;

	    for (Area areas: image.getListArea()) {
	        AffineTransform t3 = new AffineTransform();
	        Area shapeArea = (Area) areas.clone();
	        t3.translate(x, y);
	        shapeArea.transform(t3);
	        _area.add(shapeArea);
	    }

	    // Ajout des formes
	    for (Shape forme : image.getListShape())
	        _formes.add(forme);

	    // Ajout du p�rim�tre et l'aire de la nouvelle image
	    _aire += image.getAire();
	    _perimetre += image.getPerimetre();
	}

    /**
     * Ajout d'une forme
     * @param forme : forme � ajouter
     * @param area : area � ajouter
     * @param aire : aire de la forme � ajouter
     * @param perimetre : p�rim�tre de la forme � ajouter
     */
	public void ajouterForme(Shape forme, Area area, double aire, double perimetre) {
		// Check si la forme n'existe pas d�j� dans l'image
		int cpt = 0;
		for (Shape formes : _formes) {
			if (formes.getClass() ==  forme.getClass()) {
				// Faire la diff�rence entre un cercle et une ellipse
				if (forme.getClass() == Ellipse2D.Double.class) {
					// Si les 2 formes sont des cercles
					Rectangle rectangle = formes.getBounds();
					Rectangle rectangle2 = forme.getBounds();
					if (rectangle.getWidth() / 2 == rectangle.getHeight() / 2 && rectangle2.getWidth() / 2 == rectangle2.getHeight() / 2) {
						cpt++;
					} else if (rectangle.getWidth() / 2 != rectangle.getHeight() / 2 && rectangle2.getWidth() / 2 != rectangle2.getHeight() / 2) {
						cpt++;
					}
				} else {
					cpt++;
				}
			}
		}

		if (cpt == 0) {
			_formes.add(forme);
			_area.add(area);
			_aire += aire;
			_perimetre += perimetre;
	
			System.out.println("");
			System.out.println("    Ajout d'une forme � l'image");
			System.out.println("        Nombre de formes : " + _formes.size());
			System.out.println("        Aire totale : " + _aire + " / p�rim�tre total : " + _perimetre);
			System.out.println("");
		} else {
			System.out.println("    Cette forme existe d�j� dans l'image");
		}
	}

	/**
	 * Suppression de la derni�re forme
	 */
    public void effacerForme() {
    	_area.remove(_formes.size() - 1);
    	_formes.remove(_formes.size() - 1);
	}

	// Getters
    /**
     * Retourne le p�rim�tre
     * @return _perimetre
     */
    public double getPerimetre() {
    	return _perimetre;
    }
    
    /**
     * Retourne l'aire
     * @return _aire
     */
    public double getAire() {
    	return _aire;
    }
    
	/**
	 * Retourne la liste d'area
	 * @return
	 */
	public ArrayList<Area> getListArea() {
		return _area;
	}

	/**
	 * Retourne la liste de formes
	 * @return listForme
	 */
    public ArrayList<Shape> getListShape() {
		return _formes;
	}
    
    /**
	 * Retourne la derni�re area
	 * @return listArea
	 */
	public Area getLastArea() {
		return _area.get(_area.size() - 1);
	}
	
	// Setters
    /**
     * Ajout d'area
     * @param aire
     */
	public void setListArea(ArrayList<Area> aire) {
		_area = aire;
	}
}
