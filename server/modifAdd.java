import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;


public class modifAdd extends JFrame{
	
	private int nbConcert = 3;
	private Connexion connexion;
	
	public modifAdd(String selection, Connexion connexion) {
		
		//Crée la fenetre
		super("Ajout");
		this.connexion = connexion;
		setSize(600,200);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new BorderLayout());
		if(selection == "Groupe") {
			addGroupe();
		}else if(selection == "Chanson") {
			addChanson();
		}else if(selection == "Billet") {
			addBillet();
		}
	}
	
	//
	//Groupe selectionné
	public void addGroupe() {
		setTitle("Ajout d'un groupe");
		JPanel panAddGrp = new JPanel();
		panAddGrp.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		panAddGrp.setBackground(Color.GRAY);
		panAddGrp.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		//ID_concert
		JLabel labIdCon = new JLabel("ID concert");
		labIdCon.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 0;
		c.gridy = 0;
		panAddGrp.add(labIdCon,c);
		//ComboBox select groupe
		JComboBox<String> CBSelID = new JComboBox<String>();
				//Ajoute les groupes
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:./../Database.db");
			conn.setAutoCommit(false);
			System.out.println("Opened database successfully");
					     
			ResultSet rs = connexion.query("Select id_concert , lieu from Concert");
			try {
			   	while(rs.next()) {
					CBSelID.addItem( String.valueOf(rs.getInt("id_concert")) + " : " + rs.getString("lieu"));
				}
				conn.commit();
				conn.close();
			   } catch (SQLException e1) {
				e1.printStackTrace();
			}
		}catch ( Exception e1 ) {
		   	System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
		}
		c.gridx = 0;
		c.gridy = 1;
		panAddGrp.add(CBSelID,c);
		//Nom_Groupe
		JLabel labNomGrp = new JLabel("Nom Groupe");
		labNomGrp.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 1;
		c.gridy = 0;
		panAddGrp.add(labNomGrp,c);
		//TF nom_groupe
		JTextField nom_groupe = new JTextField(); 
		nom_groupe.setPreferredSize(new Dimension(100, 25));
		c.gridx = 1;
		c.gridy = 1;
		panAddGrp.add(nom_groupe,c);
		//Genre
		JLabel labGenre = new JLabel("Genre");
		labGenre.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 2;
		c.gridy = 0;
		panAddGrp.add(labGenre,c);
		//TF genre
		JTextField genre = new JTextField(); 
		genre.setPreferredSize(new Dimension(100, 25));
		c.gridx = 2;
		c.gridy = 1;
		panAddGrp.add(genre,c);
		//Bouton Validation
		JButton butValide = new JButton("Valider");
		c.gridx = 3;
		c.gridy = 1;
		panAddGrp.add(butValide,c);
		butValide.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e){
					Connection c = null;
				    Statement stmt = null;
				    try {
				      Class.forName("org.sqlite.JDBC");
				      c = DriverManager.getConnection("jdbc:sqlite:../Database.db");
				      c.setAutoCommit(false);
				      System.out.println("Opened database successfully");
						System.out.println("2");
					  int i = CBSelID.getSelectedIndex() + 1;
				      //Ajoute le groupe
				      stmt = c.createStatement();
				      String sql = "INSERT INTO Groupe(nom_groupe, genre)" +
				                   "VALUES ('" + nom_groupe.getText() + "','" + genre.getText() + "');"; 
				      stmt.executeUpdate(sql);
						System.out.println("2");
				      //Ajoute Joue  
				      sql = "INSERT INTO Joue(id_concert, nom_groupe)" +
				        	"VALUES (" + i + ",'" + nom_groupe.getText() + "');"; 
				      stmt.executeUpdate(sql);
				      
				      JOptionPane err = new JOptionPane("AJOUT REUSSI");
				      JOptionPane.showMessageDialog(err, "GROUPE AJOUTE.");
				      err.setVisible(true);
				      
				      stmt.close();
					  System.out.println("Records created successfully");
				    } catch ( Exception e1 ) {
				    	System.out.println( e1.getClass().getName() + ": " + e1.getMessage() );
				    	JOptionPane err = new JOptionPane();
				    	JOptionPane.showMessageDialog(err, "ERREUR DANS L'AJOUT D'UN GROUPE.\n(Concert inexistant ou nom groupe existe deja)", "ERREUR", JOptionPane.ERROR_MESSAGE);
				    	err.setVisible(true);
				    }
				    try {
						c.commit();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				    try {
						c.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					dispose();
				}
			});
				
		//Ajoute le panel
		this.getContentPane().add(panAddGrp);
	}
	//
	//
	
	
	//
	//Chanson selectionné
	public void addChanson() {
		setTitle("Ajout d'une chanson");
		JPanel panAddChan = new JPanel();
		panAddChan.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		panAddChan.setBackground(Color.PINK);
		panAddChan.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		//Nom_Groupe
		JLabel labNomGrp = new JLabel("Nom Groupe");
		labNomGrp.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 0;
		c.gridy = 0;
		panAddChan.add(labNomGrp,c);
		//ComboBox select groupe
		JComboBox<String> CBSelGrp = new JComboBox<String>();
				//Ajoute les groupes
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:../Database.db");
			conn.setAutoCommit(false);
			System.out.println("Opened database successfully");
				     
			ResultSet rs = connexion.query("Select nom_groupe from Groupe");
			try {
			   	while(rs.next()) {
					CBSelGrp.addItem(rs.getString("nom_groupe"));
				}
				conn.commit();
				conn.close();
			   } catch (SQLException e1) {
				e1.printStackTrace();
			}
		}catch ( Exception e1 ) {
		   	System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
		}
		c.gridx = 0;
		c.gridy = 1;
		panAddChan.add(CBSelGrp,c);
		//Nom_Chanson
		JLabel labNomChan = new JLabel("Nom Chanson");
		labNomChan.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 1;
		c.gridy = 0;
		panAddChan.add(labNomChan,c);
		//TF nom_Chanson
		JTextField nom_chanson = new JTextField(); 
		nom_chanson.setPreferredSize(new Dimension(100, 25));
		c.gridx = 1;
		c.gridy = 1;
		panAddChan.add(nom_chanson,c);
		//Duree
		JLabel labDuree = new JLabel("Duree HH:MM:SS");
		labDuree.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 2;
		c.gridy = 0;
		panAddChan.add(labDuree,c);
		//TF duree
		JTextField duree = new JTextField(); 
		duree.setPreferredSize(new Dimension(53, 25));
		c.gridx = 2;
		c.gridy = 1;
		panAddChan.add(duree,c);
		//Bouton Validation
		JButton butValide = new JButton("Valider");
		c.gridx = 3;
		c.gridy = 1;
		panAddChan.add(butValide,c);
		butValide.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e){
					Connection c = null;
				    Statement stmt = null;
				    try {
				      Class.forName("org.sqlite.JDBC");
				      c = DriverManager.getConnection("jdbc:sqlite:../Database.db");
				      c.setAutoCommit(false);
				      System.out.println("Opened database successfully");
				      
				      //Ajoute le concert
				      stmt = c.createStatement();
				      String sql = "INSERT INTO Chanson(nom_groupe, nom_chanson, duree)" +
				                   "VALUES ('" +  (String)CBSelGrp.getSelectedItem() + "','" + nom_chanson.getText() + "','" + duree.getText() + "');"; 
				      stmt.executeUpdate(sql);
				      
				      JOptionPane err = new JOptionPane("AJOUT REUSSI");
				      JOptionPane.showMessageDialog(err, "CHANSON AJOUTE.");
				      err.setVisible(true);
				      stmt.close();
					  System.out.println("Records created successfully");
				    } catch ( Exception e1 ) {
				    	System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
				    	JOptionPane err = new JOptionPane();
				    	JOptionPane.showMessageDialog(err, "ERREUR DANS L'AJOUT D'UNE CHANSON.\n(Groupe inexistant)", "ERREUR", JOptionPane.ERROR_MESSAGE);
				    	err.setVisible(true);
				    }
				    try {
						c.commit();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				    try {
						c.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					dispose();
				}
			});
			
		//Ajoute le panel
		this.getContentPane().add(panAddChan);
	}
	//
	//
	
	
	//
	//Billet selectionné
	public void addBillet() {
		setTitle("Modification d'un billet");
		JPanel panAddGrp = new JPanel();
		panAddGrp.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		panAddGrp.setBackground(Color.CYAN);
		panAddGrp.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		//Categorie
		JLabel labCategorie = new JLabel("Impossible d'ajouter un Billet");
		labCategorie.setFont(new Font("Arial",Font.BOLD, 35));
		c.gridx = 0;
		c.gridy = 0;
		panAddGrp.add(labCategorie,c);
		//Bouton Validation
		JButton butValide = new JButton("Valider");
		c.gridx = 0;
		c.gridy = 1;
		panAddGrp.add(butValide,c);
		butValide.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e){
					dispose();
				}
			});
		//Ajoute le panel
		this.getContentPane().add(panAddGrp);
	}
		

}
