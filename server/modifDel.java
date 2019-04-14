import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;


public class modifDel extends JFrame{
	
	private Connexion connexion;
	
	public modifDel(String selection, Connexion connexion) {
		
		//Crée la fenetre
		super("Suppression");
		this.connexion = connexion;
		setSize(1000,200);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new BorderLayout());
		if(selection == "Groupe") {
			DelGroupe();
		}else if(selection == "Chanson") {
			DelChanson();
		}else if(selection == "Billet") {
			DelBillet();
		}
	}
	
	//
	//Groupe selectionné
	public void DelGroupe() {
		setTitle("Suppression d'un groupe");
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
		//Bouton Validation
		JButton butValide = new JButton("Valider");
		c.gridx = 1;
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

				      //Supprime le groupe
				      stmt = c.createStatement();
				      String sql = "DELETE " +
				                   "FROM Groupe  " + 
				                   "WHERE nom_groupe = '" + (String)CBSelGrp.getSelectedItem() + "';"; 
				      stmt.executeUpdate(sql);
				      
				    //Supprime Chanson  
				      sql = "DELETE " +
				        	"FROM Chanson " + 
			                "WHERE nom_groupe = '" + (String)CBSelGrp.getSelectedItem() + "';"; 
				      stmt.executeUpdate(sql);
				      
				    //Supprime Joue  
				      sql = "DELETE " +
				        	"FROM Joue " + 
			                "WHERE nom_groupe = '" + (String)CBSelGrp.getSelectedItem() + "';"; 
				      stmt.executeUpdate(sql);
				      
				      JOptionPane err = new JOptionPane("SUPPRESSION REUSSI");
				      JOptionPane.showMessageDialog(err, "GROUPE SUPPRIME.");
				      err.setVisible(true);
				      
				      stmt.close();
					  System.out.println("Records created successfully");
				    } catch ( Exception e1 ) {
				    	System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
				    	JOptionPane err = new JOptionPane();
				    	JOptionPane.showMessageDialog(err, "ERREUR DANS LA SUPPRESSION D'UN GROUPE.", "ERREUR", JOptionPane.ERROR_MESSAGE);
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
	public void DelChanson() {
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
		    conn = DriverManager.getConnection("jdbc:sqlite:Database.db");
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
		//Bouton Validation
		JButton butValide = new JButton("Valider");
		c.gridx = 4;
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
				      
				    //Supprime la chanson
				      stmt = c.createStatement();
				      String sql = "DELETE " +
				                   "FROM Chanson  " + 
				                   "WHERE nom_chanson = '" + (String)CBSelCha.getSelectedItem() + "';"; 
				      stmt.executeUpdate(sql);
				      
				      JOptionPane err = new JOptionPane("SUPPRESSION REUSSI");
				      JOptionPane.showMessageDialog(err, "CHANSON SUPPRIMEE.");
				      err.setVisible(true);
				      stmt.close();
					  System.out.println("Records created successfully");
				    } catch ( Exception e1 ) {
				    	System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
				    	JOptionPane err = new JOptionPane();
				    	JOptionPane.showMessageDialog(err, "ERREUR DANS LA SUPPRESSION D'UNE CHANSON.\n", "ERREUR", JOptionPane.ERROR_MESSAGE);
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
	public void DelBillet() {
		setTitle("Suppression d'un billet");
		JPanel panAddGrp = new JPanel();
		panAddGrp.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		panAddGrp.setBackground(Color.CYAN);
		panAddGrp.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		//Categorie
		JLabel labBi = new JLabel("Billet");
		labBi.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 0;
		c.gridy = 0;
		panAddGrp.add(labBi,c);
		JComboBox<String> CBSelBi = new JComboBox<String>();
		Connection conn = null;
		try {
		   	Class.forName("org.sqlite.JDBC");
		    conn = DriverManager.getConnection("jdbc:sqlite:./../Database.db");
		    conn.setAutoCommit(false);
		    System.out.println("Opened database successfully");
		    
		    ResultSet rs = connexion.query("Select lieu from Concert");
		    try {
		    	while(rs.next()) {
		    		CBSelBi.addItem(rs.getString("lieu"));
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
		panAddGrp.add(CBSelBi, c);
		//Nom
		JLabel labNom = new JLabel("Nom");
		labNom.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 1;
		c.gridy = 0;
		panAddGrp.add(labNom,c);
		//TF Nom
		JTextField nom = new JTextField(); 
		nom.setPreferredSize(new Dimension(100, 25));
		c.gridx = 1;
		c.gridy = 1;
		panAddGrp.add(nom,c);
		//Prenom
		JLabel labPrenom = new JLabel("Prenom");
		labPrenom.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 2;
		c.gridy = 0;
		panAddGrp.add(labPrenom,c);
		//TF prenom
		JTextField prenom = new JTextField(); 
		prenom.setPreferredSize(new Dimension(100, 25));
		c.gridx = 2;
		c.gridy = 1;
		panAddGrp.add(prenom,c);
		//num
		JLabel labnum = new JLabel("N°");
		labnum.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 3;
		c.gridy = 0;
		panAddGrp.add(labnum,c);
		//TF numero
		JTextField numero = new JTextField(); 
		numero.setPreferredSize(new Dimension(30, 25));
		c.gridx = 3;
		c.gridy = 1;
		panAddGrp.add(numero,c);
		//num
		JLabel labrue = new JLabel("Rue");
		labrue.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 4;
		c.gridy = 0;
		panAddGrp.add(labrue,c);
		//TF rue
		JTextField rue = new JTextField(); 
		rue.setPreferredSize(new Dimension(100, 25));
		c.gridx = 4;
		c.gridy = 1;
		panAddGrp.add(rue,c);
		//ville
		JLabel labVille = new JLabel("Ville");
		labVille.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 5;
		c.gridy = 0;
		panAddGrp.add(labVille,c);
		//TF ville
		JTextField ville = new JTextField(); 
		ville.setPreferredSize(new Dimension(100, 25));
		c.gridx = 5;
		c.gridy = 1;
		panAddGrp.add(ville,c);
		//pays
		JLabel labPays = new JLabel("Pays");
		labPays.setFont(new Font("Arial",Font.BOLD, 15));
		c.gridx = 6;
		c.gridy = 0;
		panAddGrp.add(labPays,c);
		//TF pays
		JTextField pays = new JTextField(); 
		pays.setPreferredSize(new Dimension(100, 25));
		c.gridx = 6;
		c.gridy = 1;
		panAddGrp.add(pays,c);
		//Bouton Validation
		JButton butValide = new JButton("Valider");
		c.gridx = 7;
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
				      
				      int i = 0;
				      ResultSet rs = connexion.query("Select * from Client natural join Billet where nom = '" + nom.getText() + "';");
					    try {
					    	while(rs.next()) {
								i++;
							}
					    } catch (SQLException e1) {
							e1.printStackTrace();
						}
					    
					    int age = 0;
					      ResultSet rs1 = connexion.query("Select age from Client where nom = '" + nom.getText() + "' and prenom = '" + prenom.getText() + "' ;");
						    try {
						    	while(rs1.next()) {
									age = rs1.getInt("age");
								}
						    } catch (SQLException e1) {
								e1.printStackTrace();
							}
				     if(age < 18) {
					 //Modifie prix Billet
					 stmt = c.createStatement();
					 String sql = "UPDATE Billet  " +
				            	  "SET prix = prix + 20 "+ 
				                  "WHERE id_billet = (SELECT id_billet FROM Billet natural join client natural join Concert WHERE lieu = '" + CBSelBi.getSelectedItem() +  
				                  "' and nom = '" + nom.getText() + "' and prenom = '" + prenom.getText() + "' " +
				                  " and num_rue = '" + rue.getText() + "' and rue = '" + rue.getText() + 
				                  "' and ville = '" + ville.getText() + "' and pays = '" + pays.getText() + "' limit 1);"; 
					  stmt.executeUpdate(sql);
				     }
				    //Supprime Billet
				      stmt = c.createStatement();
				      String sql = "UPDATE Billet  " +
			           	    "SET id_client = 0 "+ 
			                "WHERE id_billet = (SELECT id_billet FROM Billet natural join client natural join Concert WHERE lieu = '" + CBSelBi.getSelectedItem() +  
			                "' and nom = '" + nom.getText() + "' and prenom = '" + prenom.getText() + "' " +
			                " and num_rue = '" + rue.getText() + "' and rue = '" + rue.getText() + 
			                "' and ville = '" + ville.getText() + "' and pays = '" + pays.getText() + "' limit 1);"; 
				      stmt.executeUpdate(sql);
				      
				      if (i == 1) {
				    	  sql = "DELETE  " +
			                   	"FROM client "+ 
			                    "WHERE id_client = (SELECT id_client FROM Client WHERE nom = '" + nom.getText() + "' and prenom = '" + prenom.getText() + "');"; 
				      stmt.executeUpdate(sql);
				      }else if (i == 0) {
				    	  throw new Exception();
				      }
				      JOptionPane err = new JOptionPane("SUPRESSION REUSSI");
				      JOptionPane.showMessageDialog(err, "BILLET SUPPRIME.");
				      err.setVisible(true);
				      
				      stmt.close();
					  System.out.println("Records created successfully");
				    } catch ( Exception e1 ) {
				    	System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
				    	JOptionPane err = new JOptionPane();
				    	JOptionPane.showMessageDialog(err, "ERREUR DANS LA SUPPRESSION D'UN BILLET.\n", "ERREUR", JOptionPane.ERROR_MESSAGE);
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
}
