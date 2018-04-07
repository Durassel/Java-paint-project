import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Classe fenêtre
 */
public class Fenetre extends JFrame implements ActionListener{
	// Menu barre
	private MenuBar _menu = new MenuBar();
	// Onglets
    private Menu _menu_menu = new Menu("Menu");
	    private MenuItem _menu_nouveau = new MenuItem("Nouveau");
	    private MenuItem _menu_quitter = new MenuItem("Quitter");
	private Menu _menu_images = new Menu("Image");
    	private MenuItem _menu_nouvelle_image = new MenuItem("Nouvelle image");
    private Menu _menu_formes = new Menu("Formes");
    	private MenuItem _menu_ligne = new MenuItem("Ligne");
    	private MenuItem _menu_polygone = new MenuItem("Polygone");
    	private MenuItem _menu_cercle = new MenuItem("Cercle");
    	private MenuItem _menu_ellipse = new MenuItem("Ellipse");
    	private MenuItem _menu_effacer = new MenuItem("Effacer");
	private Menu _menu_actions = new Menu("Actions");
		private MenuItem _menu_homothetie = new MenuItem("Homothétie");
		private MenuItem _menu_translation = new MenuItem("Translate");
		private MenuItem _menu_rotation = new MenuItem("Rotation");
		private MenuItem _menu_symetrie_axiale = new MenuItem("Symétrie axiale");
		private MenuItem _menu_symetrie_centrale = new MenuItem("Symétrie centrale");
		private MenuItem _menu_copier = new MenuItem("Copier");
	// Panels :
	private JPanel _panel_bouton = new JPanel();
    // Panel : objet à sélectionner
    private ButtonGroup _radio = new ButtonGroup();
    private JRadioButton _forme = new JRadioButton("Forme");
    private JRadioButton _image = new JRadioButton("Image");
    private JRadioButton _fresque = new JRadioButton("Fresque");

    // Panel : dessin
    private Fresque _panel_dessin = new Fresque();

    /**
     * Constructeur de la classe Fenetre
     * Initialisation des boutons et panels
     */
	public Fenetre() {
		// Initialisation des menus
		this.setTitle("Projet");
		this.setSize(1500, 1000);
		this.setMenuBar(_menu);

		// Ajout des menus
		_menu.add(_menu_menu);
		_menu.add(_menu_images);
		_menu.add(_menu_formes);
		_menu.add(_menu_actions);

		_menu_menu.add(_menu_nouveau);
		_menu_menu.add(_menu_quitter);

		_menu_images.add(_menu_nouvelle_image);

		_menu_formes.add(_menu_ligne);
		_menu_formes.add(_menu_polygone);
		_menu_formes.add(_menu_cercle);
		_menu_formes.add(_menu_ellipse);
		_menu_formes.add(_menu_effacer);

		_menu_actions.add(_menu_symetrie_axiale);
		_menu_actions.add(_menu_symetrie_centrale);
		_menu_actions.add(_menu_rotation);
		_menu_actions.add(_menu_homothetie);
		_menu_actions.add(_menu_translation);
		_menu_actions.add(_menu_copier);

		// Radio
		_radio.add(_fresque);
 		_radio.add(_image);
 		_radio.add(_forme);
 		_fresque.setBackground(new Color(175,175,175));
 		_panel_bouton.add(_fresque);
 		_image.setBackground(new Color(175,175,175));
 		_panel_bouton.add(_image);
 		_forme.setBackground(new Color(175,175,175));
 		_panel_bouton.add(_forme);

 		// Action listener
		_menu_nouveau.addActionListener(this);
		_menu_quitter.addActionListener(this);
		
		_menu_nouvelle_image.addActionListener(this);
		_menu_copier.addActionListener(this);
		
		_menu_ligne.addActionListener(this);
		_menu_polygone.addActionListener(this);
		_menu_cercle.addActionListener(this);
		_menu_ellipse.addActionListener(this);
		_menu_effacer.addActionListener(this);
		
		_menu_homothetie.addActionListener(this);
		_menu_translation.addActionListener(this);
		_menu_rotation.addActionListener(this);
		_menu_symetrie_axiale.addActionListener(this);
		_menu_symetrie_centrale.addActionListener(this);
 			
 		_fresque.addActionListener(new selectionObjet());
 		_image.addActionListener(new selectionObjet());
 		_forme.addActionListener(new selectionObjet());

 		// Positionnement des panels
 		_panel_bouton.setBackground(Color.lightGray);
 		this.add(_panel_bouton, BorderLayout.PAGE_START);
 		this.add(_panel_dessin, BorderLayout.CENTER);
 		
 		this.setLocationRelativeTo(null);
 		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
 		this.setVisible(true);
	}

    /**
     * Sélection de l'objet à opérer (image, forme, fresque)
     */
    class selectionObjet implements ActionListener{
    	public void actionPerformed(ActionEvent evenement) {
            if (_forme.isSelected())
            	_panel_dessin.forme();
            if (_image.isSelected())
            	_panel_dessin.image();
            if (_fresque.isSelected())
            	_panel_dessin.fresque();
        }
    }

    /**
     * Override de actionPerformed
     * Détection des clics sur les boutons
     */
	@Override
	public void actionPerformed(ActionEvent evenement) {
		if (evenement.getSource() == _menu_ligne) {
			System.out.println("Ligne");
			_panel_dessin.dessinerLigne(); 
		} else if (evenement.getSource() == _menu_polygone) {
			System.out.println("Polygone");
			_panel_dessin.dessinerPolygone();
		} else if (evenement.getSource() == _menu_cercle) {
			System.out.println("Cercle");
			_panel_dessin.dessinerCercle();
		} else if (evenement.getSource() == _menu_ellipse) {
			System.out.println("Ellipse");
			_panel_dessin.dessinerEllipse();
		} else if (evenement.getSource() == _menu_homothetie) {
			System.out.println("Homothétie");
			_panel_dessin.homothetie();
		} else if (evenement.getSource() == _menu_translation) {
			System.out.println("Translation");
			_panel_dessin.deplacer();  
		} else if (evenement.getSource() == _menu_rotation) {
			System.out.println("Rotation");
			_panel_dessin.rotation();
		} else if (evenement.getSource() == _menu_symetrie_axiale) {
			System.out.println("Symétrie axiale");
			_panel_dessin.symetrieAxiale();
		} else if (evenement.getSource() == _menu_symetrie_centrale) {
			System.out.println("Symétrie centrale");
			_panel_dessin.symetrieCentrale();
		} else if (evenement.getSource() == _menu_effacer) {
			System.out.println("Effacer");
			_panel_dessin.effacer();
		} else if (evenement.getSource() == _menu_nouvelle_image) {
			_panel_dessin.nouvelleImage();
		} else if (evenement.getSource() == _menu_copier) {
			System.out.println("Copier");
			_panel_dessin.copierImage();
		} else if (evenement.getSource() == _menu_nouveau) {
			this.dispose();
			Fenetre nouveau = new Fenetre();
		} else if (evenement.getSource() == _menu_quitter) {
			System.exit(0);
		}
	}
}