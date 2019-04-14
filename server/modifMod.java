import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;


public class modifMod extends JFrame{
	
	private Connexion connexion;
	
	public modifMod(String selection, Connexion connexion) {
		
		//Crée la fenetre
		super("Modification");
		this.connexion = connexion;
		setSize(800,200);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new BorderLayout());
		if(selection == "Groupe") {
			ModGroupe();
		}else if(selection == "Chanson") {
			ModChanson();
		}else if(selection == "Billet") {
			ModBillet();
		}
	}
	
	//
	//Groupe selectionné
	public void ModGroupe() {
		setTitle("Ajout d'un groupe");
		JPanel panAddGrp = new JPanel();
		panAddGrp.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		panAddGrp.setBackground(Color.GRAY);
		panAddGrp.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		//Label select groupe
		JLabel labSelGrp = new JLabel("Selectionner Groupe");
		labSelGrp.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 0;
		c.gridy = 0;
		panAddGrp.add(labSelGrp,c);
		//ComboBox select groupe
		JComboBox<String> CBSelGrp = new JComboBox<String>();
				//Ajoute les groupes
		Connection conn = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      conn = DriverManager.getConnection("jdbc:sqlite:./../Database.db");
	      conn.setAutoCommit(false);
	      System.out.println("Opened database successfully");
	      
	      ResultSet rs = connexion.query("Select nom_groupe from groupe");
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
	    panAddGrp.add(CBSelGrp, c);
		//Nom_Groupe
		JLabel labNomGrp = new JLabel("Nouveau nom Groupe");
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
		JLabel labGenre = new JLabel("Nouveau Genre");
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
		c.gridx = 4;
		c.gridy = 1;
		panAddGrp.add(butValide,c);
		butValide.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e){
					Connection c = null;
				    Statement stmt = null;
				    try {
				      Class.forName("org.sqlite.JDBC");
				      c = DriverManager.getConnection("jdbc:sqlite:./../Database.db");
				      c.setAutoCommit(false);
				      System.out.println("Opened database successfully");

				      //Modifie le groupe
				      stmt = c.createStatement();
				      String sql = "UPDATE Groupe " +
				                   "SET nom_groupe = '" + nom_groupe.getText() + "', genre = '" + genre.getText() + "' " + 
				                   "WHERE nom_groupe = '" + (String)CBSelGrp.getSelectedItem() + "';"; 
				      stmt.executeUpdate(sql);
				      
				    //Modifie Joue  
				      sql = "UPDATE Joue " +
				        	"SET nom_groupe = '" + nom_groupe.getText() + "' " + 
			                "WHERE nom_groupe = '" + (String)CBSelGrp.getSelectedItem() + "';"; 
				      stmt.executeUpdate(sql);
				      
				      JOptionPane err = new JOptionPane("MODIFICATION REUSSI");
				      JOptionPane.showMessageDialog(err, "GROUPE MODIFIE.");
				      err.setVisible(true);
				      
				      stmt.close();
					  System.out.println("Records created successfully");
				    } catch ( Exception e1 ) {
				    	System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
				    	JOptionPane err = new JOptionPane();
				    	JOptionPane.showMessageDialog(err, "ERREUR DANS LA MODIFICATION D'UN GROUPE.", "ERREUR", JOptionPane.ERROR_MESSAGE);
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
	public void ModChanson() {
		setTitle("Modification d'une chanson");
		JPanel panAddChan = new JPanel();
		panAddChan.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		panAddChan.setBackground(Color.PINK);
		panAddChan.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		//Label select groupe
		JLabel labSelGrp = new JLabel("Selectionner Chanson");
		labSelGrp.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 0;
		c.gridy = 0;
		panAddChan.add(labSelGrp,c);
		//ComboBox select groupe
		JComboBox<String> CBSelCha = new JComboBox<String>();
				//Ajoute les groupes
		Connection conn = null;
		try {
		   	Class.forName("org.sqlite.JDBC");
		    conn = DriverManager.getConnection("jdbc:sqlite:./../Database.db");
		    conn.setAutoCommit(false);
		    System.out.println("Opened database successfully");
		     
		    ResultSet rs = connexion.query("Select nom_chanson from Chanson");
		    try {
		    	while(rs.next()) {
					CBSelCha.addItem(rs.getString("nom_chanson"));
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
		panAddChan.add(CBSelCha, c);
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
				      c = DriverManager.getConnection("jdbc:sqlite:./../Database.db");
				      c.setAutoCommit(false);
				      System.out.println("Opened database successfully");
				      
				      //Modifie Chanson
				      stmt = c.createStatement();
				      String sql = "UPDATE Chanson " +
			                   	   "SET nom_chanson = '" + nom_chanson.getText() + "', duree = '" + duree.getText() + "' "+ 
			                       "WHERE nom_chanson = '" + (String)CBSelCha.getSelectedItem() + "';"; 
				      stmt.executeUpdate(sql);
				      
				      JOptionPane err = new JOptionPane("MODIFICATION REUSSI");
				      JOptionPane.showMessageDialog(err, "CHANSON MODIFIE.");
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
	public void ModBillet() {
		setTitle("Modification d'un billet");
		JPanel panAddGrp = new JPanel();
		panAddGrp.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		panAddGrp.setBackground(Color.CYAN);
		panAddGrp.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		//Categorie
		JLabel labCategorie = new JLabel("Impossible de modifier un Billet");
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
