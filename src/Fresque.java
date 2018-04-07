import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Area;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 * Classe dessin
 */
public class Fresque extends JPanel{
	// Attributs priv�s
	// P�rim�tre et aire de la fresque
    private int _perimetre;
    private int _aire;
    
    // Liste d'images
    private ArrayList<Image> _images;

    // Dessins et actions
    private boolean _ligne = false;
    private int _polygone = 0;
    private boolean _cercle = false;
    private boolean _ellipse = false;
    private float _homothetie = 0;
    private boolean _translation = false;
    private float _rotation = 0;
    private boolean _symetrie_centrale = false;
    private boolean _symetrie_axiale = false;
    private boolean _copier = false;
    private boolean _fresque = false;
    private boolean _image = false;
    private boolean _forme = false;
    
    // Param�tres des actions
    private int _nbClics = 0;
    private int _nbCotesPolygone = 0;
    private float _tauxHomothetie = 0;
    private float _degreRotation = 0;
    private ArrayList<Integer> _x;
    private ArrayList<Integer> _y;
    JOptionPane _erreur;
    JOptionPane _information;
    
    /**
     * Constructeur de la classe Fresque
     */
    Fresque() {
    	// Initialisation des attributs de la fresque
    	_perimetre = 0;
    	_aire = 0;

    	_images = new ArrayList<Image>(); // Nouvelle liste d'images
        _images.add(new Image()); // Cr�ation de la 1�re image

        _x = new ArrayList<Integer>();
        _y = new ArrayList<Integer>();
        
        setVisible(true);

        // Ecoute du clic
        addMouseListener(new MouseAdapter() {
        	// Ev�nement : clic
            @Override
            public void mouseClicked(MouseEvent clic) {
            	// Cr�ation d'un cercle
                if (_cercle == true) {
                    _nbClics++;

                    // D�finition du centre du cercle
                    if(_nbClics == 1) {
                    	// Enregistrement des coordonn�es du clic
                    	int x = clic.getX();
                    	int y = clic.getY();
                    	// Ajout des coordonn�es du clic
                    	_x.add(x);
                        _y.add(y);
                        // Affichage des traces
                        System.out.println("    Centre : " + x + " / " + y);
                    } else if(_nbClics == 2) { // D�finition du rayon du cercle
                    	// Ajout des coordonn�es du clic
                        _x.add(clic.getX());
                        _y.add(clic.getY());
                        // D�termination de la longueur du rayon
                        int x = _x.get(1) - _x.get(0); // Diff�rences de x
                        int y = _y.get(1) - _y.get(0); // Diff�rences de y
                        double rayon = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

                        // Sauvegarde du cercle
                        Shape cercle = new Ellipse2D.Double(_x.get(0) - (int) rayon, _y.get(0) - (int) rayon, 2 * (int) rayon, 2 * (int) rayon);
                        Area area = new Area(cercle); // Sauvegarde de la forme

                        // Affichage des traces
                        System.out.println("    P�rim�tre : " + 2 * Math.PI * rayon);
                    	System.out.println("    Aire : " + Math.PI * Math.pow(rayon, 2));
                        System.out.println("    Rayon: " + rayon);
                        
                        // Ajout du p�rim�tre et de l'aire du cercle
                        _perimetre +=  2 * Math.PI * rayon; // 2 * PI * R
                        _aire += Math.PI * Math.pow(rayon, 2); // PI * R�
                        // Ajout de la nouvelle forme
                        _images.get(_images.size() - 1).ajouterForme(cercle, area, Math.PI * Math.pow(rayon, 2), 2 * Math.PI * rayon);

                        update(getGraphics());
                    }
                } else if(_ligne == true) { // Cr�ation d'une ligne
                    _nbClics++;

                    // Premier point de la ligne
                    if(_nbClics == 1) {
                    	// Sauvegarde des coordonn�es du clic
                        _x.add(clic.getX());
                        _y.add(clic.getY());
                        System.out.println("    D�finition du premier point : " + _x.get(0) + " / " + _y.get(0));
                    } else if(_nbClics == 2) { // Second point de la ligne
                    	 // Sauvegarde des coordonn�es du clic
                    	 _x.add(clic.getX());
                         _y.add(clic.getY());
                         System.out.println("    D�finition du second point : " + _x.get(1) + " / " + _y.get(1));

                         // Diff�rences de x et y
                         int x = _x.get(1) - _x.get(0);
                         int y = _y.get(1) - _y.get(0);

                         // Sauvegarde de la ligne : rectangle (1 px de hauteur)
                         Shape forme = new Rectangle2D.Double(_x.get(0), _y.get(0),Math.sqrt(x*x + y*y), 1);
                         Area area= new Area(forme);

                         // Orientation de la ligne
                         int c = 1;
                         if (_y.get(1) < _y.get(0))
                        	 c = -1;

                         // Op�ration sur la ligne
                         AffineTransform transformation = new AffineTransform();
                         transformation.rotate(Math.acos(x / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))) * c, _x.get(0), _y.get(0));
                         area.transform(transformation);
                 		_images.get(_images.size() - 1).ajouterForme(forme, area, 0, 2 * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
                         update(getGraphics());
                    }
                } else if(_ellipse == true) { // Cr�ation d'une ellipse
                    _nbClics++;

                    // Premier clic : centre de l'ellipse
                    if (_nbClics == 1) {
                    	// Enregistrement des coordonn�es du clic
                    	int x = clic.getX();
                    	int y = clic.getY();
                    	// Ajout des coordonn�es du clic
                    	_x.add(x);
                        _y.add(y);
                        // Affichage des traces
                        System.out.println("    Centre : " + x + " / " + y);
                    } else if(_nbClics == 2) { // Premier demi-rayon de l'ellipse
                    	// Sauvegarde des coordonn�es du clic
                    	_x.add(clic.getX());
                        _y.add(clic.getY());
                        System.out.println("    D�finition du premier demi-rayon");
                    } else if(_nbClics == 3) {
                    	// Sauvegarde des coordonn�es du clic
                    	_x.add(clic.getX());
                    	_y.add(clic.getY());
                    	System.out.println("    D�finition du second demi-rayon");

                    	// Sauvegarde de l'ellipse
                    	Shape forme = new Ellipse2D.Double(_x.get(0) - Math.abs(_x.get(0) - _x.get(1)), _y.get(0) - Math.abs(_y.get(0) - _y.get(2)), 2 * Math.abs(_x.get(0) - _x.get(1)), 2 * Math.abs(_y.get(0) - _y.get(2)));
                    	Area area= new Area(forme);
                    	double x = (_x.get(0) - _x.get(1)) * (_x.get(0) - _x.get(1));
                    	double y = (_y.get(0) - _y.get(2)) * (_y.get(0) - _y.get(2));

                    	// Calcul du p�rim�tre et de l'aire de l'ellipse
                    	_perimetre += 2 * Math.PI * Math.sqrt(0.5 * (x + y));
                    	_aire += Math.PI * Math.abs(_x.get(0) - _x.get(1)) * Math.abs(_y.get(0) - _y.get(2));
                    	
                    	// Affichage des traces
                    	System.out.println("    P�rim�tre : " + 2 * Math.PI * Math.sqrt(0.5 * (x + y)));
                    	System.out.println("    Aire : " + Math.PI * Math.abs(_x.get(0) - _x.get(1)) * Math.abs(_y.get(0) - _y.get(2)));
                    	System.out.println("    Longueur axe a : " + Math.abs(_x.get(0) - _x.get(1)));
                    	System.out.println("    Longueur axe b : " + Math.abs(_y.get(0) - _y.get(2)));

                    	// Ajout de la forme
                    	_images.get(_images.size() - 1).ajouterForme(forme, area, Math.PI * Math.abs(_x.get(0) - _x.get(1)) * Math.abs(_y.get(0) - _y.get(2)), 2 * Math.PI * Math.sqrt(0.5 * (x + y)));
                    	update(getGraphics());
                    }
                } else if(_polygone != 0) { // Cr�ation d'un polygone
                	_nbClics++;

                	// Affichage des traces
                	System.out.println("    D�finition du point " + _nbClics);

                	// Sauvegarde des coordonn�es du clic
                	_x.add(clic.getX());
                    _y.add(clic.getY());

                    // Si on a atteint le nombre de c�t� souhait�, on affiche
                    if (_nbClics == _polygone)
                    	update(getGraphics());
                } else if(_translation == true) { // D�placement d'un objet (fresque, image ou forme)
                	// S�lection des objets � d�placer
                	ArrayList<Area> listeArea = _images.get(_images.size() - 1).getListArea();
                	if (listeArea.size() == 0) {
                		 System.out.println("Aucun objet � d�placer");
                		 _erreur.showMessageDialog(null, "Aucun objet � d�placer", "Erreur", JOptionPane.ERROR_MESSAGE);
                	} else { // D�placement
	                	Rectangle2D rectangle = null;
	                	AffineTransform transformation = new AffineTransform();

	                	// Si d�placement de l'ensemble de la fresque
	                	if (_fresque == true) {
	                		Area area = new Area();
	                		// Parcours des images
	    	        		for(Image image : _images) {
	                			ArrayList<Area> areas = image.getListArea();
	                			// Parcours des area de l'image
	                			for(Area forme : areas)
	                				area.add(forme);
	                		}

	    	        		// D�placement
	    	        		// R�cup�ration de la r�union des areas
	    	        		rectangle = area.getBounds2D();
	    	        		transformation.translate(clic.getX() - rectangle.getX() - rectangle.getWidth() / 2, clic.getY() - rectangle.getY() - rectangle.getHeight() / 2);
	    	        		for (Image image : _images) {
	                			ArrayList<Area> areas = image.getListArea();
	                			for(Area shape : areas)
	                				shape.transform(transformation);
	                			image.setListArea(areas);
	                		}
	                	} else if (_image == true) { // D�placement d'une image
	                		Area area = new Area();
	                		// Parcours des areas
	                		for (Area forme : listeArea)
	                			area.add(forme);
	                		// R�cup�ration de la r�union des areas
	                		rectangle = area.getBounds2D();
	                		transformation.translate(clic.getX() - rectangle.getX() - rectangle.getWidth() / 2, clic.getY() - rectangle.getY() - rectangle.getHeight() / 2);
	                		for (Area forme : listeArea)
	                			forme.transform(transformation);
	                	} else if (_forme == true) {
	                		// R�cup�ration de la r�union des areas
	                		rectangle = listeArea.get(listeArea.size() - 1).getBounds2D();
	                		transformation.translate(clic.getX() - rectangle.getX() - rectangle.getWidth() / 2, clic.getY() - rectangle.getY() - rectangle.getHeight() / 2);
	                     	listeArea.get(listeArea.size() - 1).transform(transformation);
	                	} else {
	                		System.out.println("Veuillez s�lectionner un objet � d�placer");
	                		_information.showMessageDialog(null, "Veuillez s�lectionner un objet � d�placer", "Information", JOptionPane.INFORMATION_MESSAGE);
	                	}
	                	update(getGraphics());
                	}
                	_images.get(_images.size() - 1).setListArea(listeArea);
                }
            }
        });
    }

	/**
     * R�initialisation des attributs afin de dessiner une ligne
     */
    public void dessinerLigne() {
    	_ligne = true;
        _cercle = false;
        _ellipse= false;
        _polygone = 0;
    }

	/**
     * R�initialisation des attributs afin de dessiner un polygone
     */
	public void dessinerPolygone() {
		_ligne = false;
        _cercle = false;
        _ellipse= false;
        
        try {
        	_nbCotesPolygone = Integer.parseInt(JOptionPane.showInputDialog(null, "Combien de c�t�s comporte votre polygone ?", "Nombre de c�t�s du polygone", JOptionPane.QUESTION_MESSAGE));
        } catch (NumberFormatException e) {
        	_nbCotesPolygone = 0;
        }

        _polygone = _nbCotesPolygone;
	}
	
	/**
     * R�initialisation des attributs afin de dessiner un cercle
     */
	public void dessinerCercle() {
		_cercle = true;
		_ligne = false;
		_ellipse= false;
		_polygone = 0;
    }
	
	/**
     * R�initialisation des attributs afin de dessiner une ellipse
     */
	public void dessinerEllipse() {
		_ligne = false;
		_cercle = false;
		_ellipse= true;
		_polygone = 0;
	}

	/**
	 * Initialisation de l'homoth�tie
	 */
    public void homothetie() {
    	try {
    		_tauxHomothetie = Float.parseFloat(JOptionPane.showInputDialog(null, "Saisissez le taux en pourcentage de l'homoth�tie : ", "Taux de l'homoth�tie", JOptionPane.QUESTION_MESSAGE));
    	} catch (NumberFormatException e) {
    		_tauxHomothetie = 0;
    	}

        _homothetie = _tauxHomothetie / 100;
        update(getGraphics());
    }

    /**
	 * Initialisation du d�placement
	 */
    public void deplacer() {
    	_translation = true;
    }

    /**
	 * Initialisation de la rotation
	 */
    public void rotation() {
    	try {
    		_degreRotation = Float.parseFloat(JOptionPane.showInputDialog(null, "Saisissez l'angle de rotation en degr�s : ","Angle de rotation", JOptionPane.QUESTION_MESSAGE));
    	} catch (NumberFormatException e) {
    		_degreRotation = 0;
    	}

        _rotation = _degreRotation;
    	update(getGraphics());
    }

	/**
	 * Initialisation de la sym�trie axiale
	 */
    public void symetrieAxiale() {
    	_symetrie_axiale = true;
    	update(getGraphics());
    }

    /**
	 * Initialisation de la sym�trie centrale
	 */
    public void symetrieCentrale() {
    	_symetrie_centrale = true;
    	update(getGraphics());
    }

    /**
	 * Initialisation de la nouvelle image
	 */
    public void nouvelleImage() {
    	if (_images.get(_images.size() - 1).getListArea().isEmpty())
    		System.out.println("Une image vide existe d�j�");
    	else
    		_images.add(new Image());
    }
    
    /**
	 * Initialisation 
	 */
    public void copierImage() {
		ArrayList<Area> areas = _images.get(_images.size() - 1).getListArea();
		// Si aucune forme
    	if (areas.size() == 0) {
    		 System.out.println("Aucune forme pour op�rer la copie");
    		 _erreur.showMessageDialog(null, "Aucune forme pour op�rer la copie", "Erreur", JOptionPane.ERROR_MESSAGE);
    	} else { // Sinon transformation
		    // Ajouter l'ensemble des formes de l'image dans la nouvelle
    		_images.add(new Image());
    		_images.get(_images.size() - 1).ajouterImage(_images.get(_images.size() - 2));
    		// Ajouter une petite transformation pour ne pas avoir 2 images identiques
    		areas = _images.get(_images.size() - 1).getListArea();
    		for (Area area : areas) {
    			AffineTransform transformation = new AffineTransform();
    			transformation.scale(1.1, 1.1);
    			area.transform(transformation);
    		}
    	}
    }

    /**
	 * Effacer la derni�re forme
	 */
    public void effacer(){
    	// S'il n'y a aucune image
    	if (_images.isEmpty()) {
    		System.out.println("Aucune forme � effacer");
    	} else { // Sinon
    		if (_images.get(_images.size() - 1).getListArea().isEmpty() && _images.size() != 1) {
    			_images.remove(_images.size() - 1);
    		} else {
    			_images.get(_images.size()-1).effacerForme();
    		}
    		repaint(getGraphics());
    	}
	}

	/**
	 * S�lection du radio fresque
	 */
    public void fresque() {
    	_fresque = true;
    	_image = false;
    	_forme = false;
    	System.out.println("S�lection de la fresque");
    }

    /**
	 * S�lection du radio image
	 */
    public void image() {
    	_fresque = false;
    	_image = true;
    	_forme = false;
    	System.out.println("S�lection de l'image");
    }

    /**
	 * S�lection du radio forme
	 */
	public void forme() {
		_fresque = false;
    	_image = false;
    	_forme = true;
    	System.out.println("S�lection de la forme");
    }

    /**
     * Repaint
     * Rafraichissement de la fen�tre
     * @param g : graphics
     */
    public void repaint(Graphics g){
    	// Initialisation de graphics
    	super.paint(g);
    	Graphics2D g2d = (Graphics2D) g;

     	// Parcours des images
     	for (Image image : _images) {
     		// Initialisation des listes utiles � l'affichage
     		ArrayList<Area> areas = image.getListArea();
     		ArrayList<Shape> formes = image.getListShape();

     		// Aucune area
     		if (areas.isEmpty()) {
	            System.out.println("Area vide");
	        }

	        if (!areas.isEmpty()) {
	        	// Parcours des formes
	            for (int i = 0; i < areas.size(); i++) {
	            	if (formes.get(i).getClass() == Ellipse2D.Double.class) {
	            		// Cercle
	            		if (((Ellipse2D.Double)(formes.get(i))).getHeight() == ((Ellipse2D.Double)(formes.get(i))).getWidth()) {
	            			g.setColor(Color.cyan);
	            		} else { // Ellipse
	            			g.setColor(Color.blue);
	            		}
	            	} else if(formes.get(i).getClass() == Rectangle2D.Double.class) { // Ligne
	            		g.setColor(Color.magenta);
	            	} else if (formes.get(i).getClass() == Polygone.class) { // Polygone
	            		g.setColor(Color.orange);
	            	}
	            	// Affichage
	            	g2d.fill(areas.get(i));
	            	g.setColor(Color.black);
	            	g2d.draw(areas.get(i));
	            }
	        }
     	}
     	g.setColor(Color.black);
    }

    /**
     * Permet de savoir si on doit rafraichir la fen�tre
     */
    public void faireRepaint() {
    	// Si aucune image
        if (_images.isEmpty()) {
            System.out.println("Pas de rafra�chissement de la fen�tre");
            Rectangle rectangle = this.getBounds();
            System.out.println("Fen�tre :" + rectangle.height + " / " + rectangle.width);
        } else {
        	// Sinon, on repaint
            Rectangle rectangle = this.getBounds();
            getGraphics().clearRect((int) rectangle.getX(), 0, (int) rectangle.getWidth(), (int) rectangle.getHeight());
            repaint(getGraphics());
        }
    }

    /**
     * Paint
     * Affichage des formes
     */
    public void paint(Graphics g){
    	// Initialisation
    	super.paint(g);
    	Graphics2D graphic = (Graphics2D) g;

     	this.faireRepaint();

     	// S'il y a un cercle avec le bon nombre de clics
    	if (_cercle == true && _nbClics == 2) {
    		// Affichage du cercle
        	g.setColor(Color.cyan);
        	graphic.draw(_images.get(_images.size() - 1).getLastArea());

        	// Remise � 0
        	_nbClics = 0;
            _x.clear();
            _y.clear();
            _cercle = false;
    	} else if(_ligne == true && _nbClics == 2) { // S'il y a une ligne avec le bon nombre de clics
    		// Affichage de la ligne
        	g.setColor(Color.magenta);
            graphic.draw(_images.get(_images.size() - 1).getLastArea());

            // Remise � 0
        	_nbClics = 0;
            _x.clear();
            _y.clear();
            _ligne = false;
        } else if (_ellipse == true && _nbClics == 3) { // S'il y a une ellipse avec le bon nombre de clics
        	// Affichage de l'ellipse
        	g.setColor(Color.blue);
            graphic.draw(_images.get(_images.size() - 1).getLastArea());

            // Remise � 0
            _nbClics = 0;
            _x.clear();
            _y.clear();
            _ellipse = false;
        } else if (_polygone != 0 && _nbClics ==  _polygone) { // S'il y a un polygone avec le bon nombre de clics
        	// Initialisation
        	int[] x= new int[_x.size()];
        	int[] y= new int[_x.size()];
        	for (int i = 0; i < _x.size(); i++) {
        		x[i] = _x.get(i);
        		y[i] = _y.get(i);
        	}

        	Shape forme = new Polygone(x, y, _x.size());
        	Area area = new Area(forme);
        	
        	// Comparer
        	
        	_images.get(_images.size() - 1).ajouterForme(forme, area, ((Polygone)(forme)).aire(), ((Polygone)(forme)).perimetre());

        	// Affichage du polygone
        	g.setColor(Color.orange);
        	graphic.draw(_images.get(_images.size() - 1).getLastArea());

        	// Remise � 0
        	_nbClics = 0;
            _x.clear();
            _y.clear();
            _polygone = 0;
        }

    	// Actions
        ArrayList<Area> areas = _images.get(_images.size()-1).getListArea();
        if (_symetrie_axiale == true) {
        	// Si aucune forme
        	if (areas.size() == 0) {
        		 System.out.println("Aucune forme pour op�rer la sym�trie axiale");
        		 _erreur.showMessageDialog(null, "Aucune forme pour op�rer la sym�trie axiale", "Erreur", JOptionPane.ERROR_MESSAGE);
        	} else { // Sinon transformation
        		// Sym�trie axiale
        		AffineTransform transformation = new AffineTransform(-1, 0, 0, 1, 0, 0);

        		// Sym�trie axiale de toute la fresque
            	if (_fresque == true) {
            		transformation.translate(-this.getWidth(), 0);
            		// Parcours des images
            		for (Image image :_images) {
            			ArrayList<Area> listeArea = image.getListArea();
            			// Parcours des formes
            			for (Area shape : listeArea)   
                        	shape.transform(transformation);
            			image.setListArea(listeArea);
            		}
            	} else if (_image == true) { // Sym�trie axiale d'une image
            		transformation.translate(-this.getWidth(), 0);
            		for (Area forme : areas)
                    	forme.transform(transformation);
            	} else if (_forme == true) { // Sym�trie axiale d'une forme
                	transformation.translate(-this.getWidth(), 0);
                	areas.get(areas.size() - 1).transform(transformation);
                } else { // Erreur
            		System.out.println("Veuillez s�lectionner l'objet sur lequel effectuer l'op�ration");
            		_information.showMessageDialog(null, "Veuillez s�lectionner l'objet sur lequel effectuer l'op�ration", "Information", JOptionPane.INFORMATION_MESSAGE);
            	}
	        	this.faireRepaint();
        	}
        	// Remise � 0
        	_symetrie_axiale = false;
        } else if (_symetrie_centrale == true) { // Sym�trie centrale
        	// Aucune forme
        	if (areas.size() == 0) {
        		 System.out.println("Veuillez cr�er une forme avant d'effectuer l'op�ration");
        		 _erreur.showMessageDialog(null, "Veuillez cr�er une forme avant d'effectuer l'op�ration", "Erreur", JOptionPane.ERROR_MESSAGE);
        	} else {
        		Rectangle2D rectangle = null;

            	if (_fresque == true) { // Sym�trie centrale sur une fresque
            		for (Image image : _images) {
            			// Liste des images � sym�triser
            			ArrayList<Area> listeArea = image.getListArea();
            			// Parcours des images
            			for (Area forme : listeArea) {   
            				// Application de la sym�trie
            				AffineTransform transformation = new AffineTransform();
                			rectangle = forme.getBounds2D(); // R�cup�ration de la forme totale

                			// Transformation
                			AffineTransform transformation2 = new AffineTransform();
                			transformation.rotate(Math.toRadians(180), rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
                			forme.transform(transformation);

                        	transformation2.translate(this.getWidth() - rectangle.getX() * 2 - rectangle.getWidth(), this.getHeight() - rectangle.getY() * 2 - rectangle.getHeight());
                        	forme.transform(transformation2);
                        }
            			image.setListArea(listeArea);
            		}
            	} else if (_image == true) { // Sym�trie centrale sur une image
            		// Parcours des images
            		for (Area forme : areas) {   
            			// Application de la sym�trie
            			AffineTransform transformation = new AffineTransform();
            			rectangle = forme.getBounds2D();

            			// Transformation
            			AffineTransform transformation2 = new AffineTransform();
            			transformation.rotate(Math.toRadians(180), rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
                    	forme.transform(transformation);
                    	
                    	transformation2.translate(this.getWidth() - rectangle.getX() * 2 - rectangle.getWidth(), this.getHeight() - rectangle.getY() * 2 - rectangle.getHeight());
                    	forme.transform(transformation2);
            		}
            	} else if (_forme == true) { // Sym�trie centrale sur une forme
            		// Application de la sym�trie
            		AffineTransform transformation = new AffineTransform();
            		rectangle = areas.get(areas.size() - 1).getBounds2D();

            		// Transformation
            		AffineTransform transformation2 = new AffineTransform();
            		transformation.rotate(Math.toRadians(180), rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
                	areas.get(areas.size()-1).transform(transformation);

                	transformation2.translate(this.getWidth() - rectangle.getX() * 2 - rectangle.getWidth(), this.getHeight() - rectangle.getY() * 2 - rectangle.getHeight());
                	areas.get(areas.size()-1).transform(transformation2);
            	} else { // Erreur
            		System.out.println("Veuillez s�lection l'objet sur lequel effectuer l'op�ration");
            		_information.showMessageDialog(null, "Veuillez s�lection l'objet sur lequel effectuer l'op�ration", "Information", JOptionPane.INFORMATION_MESSAGE);
            	}
	        	this.faireRepaint();
        	}
        	// Remise � 0
        	_symetrie_centrale = false;
        } else if (_rotation != 0) {
        	// Aucune forme
        	if (areas.size() == 0) {
        		System.out.println("Veuillez cr�er une forme avant d'effectuer l'op�ration");
       		 	_erreur.showMessageDialog(null, "Veuillez cr�er une forme avant d'effectuer l'op�ration", "Erreur", JOptionPane.ERROR_MESSAGE);
        	} else { // Rotation
	        	Rectangle2D rectangle = null;
	        	AffineTransform transformation = new AffineTransform();

	        	if (_fresque == true) { // Rotation sur une fresque
	        		Area area = new Area();
	        		// Parcours des images
	        		for (Image image : _images) {
            			ArrayList<Area> listeArea = image.getListArea();
            			// R�cup�ration de l'area
            			for (Area forme : listeArea)
            				area.add(forme);
            		}

	        		// R�cup�ration de la forme totale
	        		rectangle = area.getBounds2D();
	        		// Transformation
	        		transformation.rotate(Math.toRadians(_rotation), rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
	        		// Parcours des images
	        		for (Image image : _images) {
            			ArrayList<Area> listeArea = image.getListArea();
            			for (Area forme : listeArea)
            				forme.transform(transformation);
            			image.setListArea(listeArea);
            		}
	        	} else if (_image == true) {
	        		Area area = new Area();
	        		// Parcours des images
	        		for (Area forme : areas)
	        			area.add(forme); // R�cup�ration de l'area

	        		// R�cup�ration de la forme totale
	        		rectangle = area.getBounds2D();
	        		// Transformation
	        		transformation.rotate(Math.toRadians(_rotation), rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
	        		// Parcours des images
	        		for(Area forme : areas)
	        			forme.transform(transformation);
	        	} else if (_forme == true) {
	        		// R�cup�ration de la derni�re forme
	        		rectangle = areas.get(areas.size() - 1).getBounds2D();
	        		// Transformation
	        		transformation.rotate(Math.toRadians(_rotation), rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
	        		areas.get(areas.size() - 1).transform(transformation);
	        	} else { // Erreur
	        		System.out.println("Veuillez s�lection l'objet sur lequel effectuer l'op�ration");
            		_information.showMessageDialog(null, "Veuillez s�lection l'objet sur lequel effectuer l'op�ration", "Information", JOptionPane.INFORMATION_MESSAGE);
            	}
	        	this.faireRepaint();
        	}
        	// Remise � 0
        	_rotation = 0;
        } else if (_homothetie != 0) { // Homoth�tie
        	if (areas.size() == 0) {
        		System.out.println("Veuillez cr�er une forme avant d'effectuer l'op�ration");
       		 	_erreur.showMessageDialog(null, "Veuillez cr�er une forme avant d'effectuer l'op�ration", "Erreur", JOptionPane.ERROR_MESSAGE);
        	} else {
        		// D�claration d'outils utiles � la transformation
	        	Rectangle2D rectangle = null;
	        	AffineTransform transformation = new AffineTransform();

	        	if (_fresque == true) { // Homoth�tie sur une fresque
	        		Area area = new Area();
	        		// Parcours des images
	        		for (Image image : _images) {
            			ArrayList<Area> listeArea = image.getListArea();
            			// R�cup�ration des area
            			for (Area forme : listeArea)
            				area.add(forme);
            		}

	        		// R�cup�ration de la forme totale
	        		rectangle = area.getBounds2D();
	        		// Parcours des area
	        		for (Area forme : areas) {
	        			// Application de la transformation
	        			AffineTransform transformation1 = new AffineTransform();
	        			transformation1.translate(-rectangle.getX() - rectangle.getWidth() / 2, -rectangle.getY() - rectangle.getHeight() / 2);
	        			forme.transform(transformation1);

	        			AffineTransform transformation2 = new AffineTransform();
	        			// Nouvelles dimensions
	        			transformation2.scale(_homothetie, _homothetie);
	        			forme.transform(transformation2);

	        			AffineTransform transformation3 = new AffineTransform();
	        			transformation3.translate(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
	        			forme.transform(transformation3);
	        		}
	        	} else if (_image == true) {
	        		Area area = new Area();
	        		// R�cup�rations des area
	        		for (Area forme : areas)
	        			area.add(forme);

	        		// R�cup�ration de la forme totale
	        		rectangle = area.getBounds2D();

	        		// Parcours des area
	        		for (Area forme : areas) {
	        			// Application de la transformation
	        			AffineTransform transformation1 = new AffineTransform();
	        			transformation1.translate(-rectangle.getX() - rectangle.getWidth() / 2, -rectangle.getY() - rectangle.getHeight() / 2);
	        			forme.transform(transformation1);

	        			AffineTransform transformation2 = new AffineTransform();
	        			// Nouvelles dimensions
	        			transformation2.scale(_homothetie, _homothetie);
	        			forme.transform(transformation2);

	        			AffineTransform transformation3 = new AffineTransform();
	        			transformation3.translate(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
	        			forme.transform(transformation3);
	        		}
	            } else if(_forme == true) {
	            	// R�cup�ration de la forme totale
	        		rectangle = areas.get(areas.size() - 1).getBounds2D();
	        		// Nouvelles dimensions
	            	transformation.scale(_homothetie, _homothetie);
	            	areas.get(areas.size() - 1).transform(transformation);

	            	AffineTransform transformation2 = new AffineTransform();
	            	Rectangle2D rectangle2 = areas.get(areas.size() - 1).getBounds2D();

	            	// Positif : zoom
	            	if (_homothetie > 1)
	            		transformation2.translate(rectangle.getX() - rectangle.getWidth() / 2 - rectangle2.getX(), rectangle.getY() - rectangle.getHeight() / 2 - rectangle2.getY());
	            	else // N�gatif : inversion de la forme
	            		transformation2.translate(rectangle.getX() + rectangle2.getWidth() / 2 - rectangle2.getX(), rectangle.getY() + rectangle2.getHeight() / 2 - rectangle2.getY());
	            	areas.get(areas.size()-1).transform(transformation2);
	        	} else { // Erreur
	        		System.out.println("Veuillez s�lection l'objet sur lequel effectuer l'op�ration");
            		_information.showMessageDialog(null, "Veuillez s�lection l'objet sur lequel effectuer l'op�ration", "Information", JOptionPane.INFORMATION_MESSAGE);
            	}
	        	this.faireRepaint();
        	}
        	// Remise � 0
        	_homothetie = 0;
        } else if (_translation == true) {
        	if (areas.size() == 0) { // Erreur
        		System.out.println("Veuillez cr�er une forme avant d'effectuer l'op�ration");
       		 	_erreur.showMessageDialog(null, "Veuillez cr�er une forme avant d'effectuer l'op�ration", "Erreur", JOptionPane.ERROR_MESSAGE);
        	} else {
	        	graphic.draw(areas.get(areas.size() - 1));
	        	// Remise � 0
	        	_translation = false;
        	}
        }
        _images.get(_images.size()-1).setListArea(areas);
    }
    
    // Getters
    /**
     * Retourne le p�rim�tre de la fresque
     * @return _perimetre
     */
    public int getPerimetre() {
        return _perimetre;
    }

    /**
     * Retourne l'aire de la fresque
     * @return _aire
     */
    public int getAire() {
        return _aire;
    }

    // Setters
    /**
     * Ajout du p�rim�tre � la fresque
     * @param perimetre
     */
    public void setPerimetre(int perimetre) {
        _perimetre = perimetre;
    }
    
    /**
     * Ajout d'une aire � la fresque
     * @param aire
     */
    public void setAire(int aire) {
        _aire = aire;
    }
}