import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class InterfaceGraph extends JFrame{
	
	private ResultSet rs;
	Connexion connexion = new Connexion("../Database.db");
	
	public InterfaceGraph(String title) {
		
		//Cree la fenetre
		super(title);
		setSize(1200,700);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setLayout(new BorderLayout());
		connexion.connect();
	}
	
	public void ajouteElt() {
		//
		//PANEL SELECT
		//Cree le panel des selects
		JPanel panSel = new JPanel();
		panSel.setPreferredSize(new Dimension(this.getWidth(),200));
		panSel.setBackground(Color.GRAY);
		panSel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3,3,3,3);
		//Cree et place les composants
			//Label selection
		JLabel tabLab = new JLabel("Selections : ");
		tabLab.setFont(new Font("Arial",Font.BOLD, 30));
		c.gridx = 0;
		c.gridy = 0;
		panSel.add(tabLab,c);
			//Liste
		JComboBox<String> type = new JComboBox<String>(new String[] {"Nom des personnes au concert 1",
				  													 "Nom des personnes au concert 2",
				  													 "Nom des personnes au concert 3",
				  													 "Groupes au concert 1",
				  				  									 "Groupes au concert 2",
				  				  									 "Groupes au concert 3",
				  				  									 "Information du concert 1",
				  				  									 "Information du concert 2",
				  				  									 "Information du concert 3",
				  				  									 "Liste des chansons",
				  				  									 "Nombre de billets restants par concerts",
				  				  									 "Nombre de billets vendus et bénéfice généré par concerts",
				  				  									 "Nombres de clients par villes",
				  				  									 "Nombres de clients par pays",
				  				  									 "Nombres de clients mineurs par concerts",
				  				  									 "Nombres de clients majeurs par concerts"});
		String[] liRequete = new String[] {"Select nom from Client natural join Billet natural join Concert where id_concert = 1 and id_client != 0 order by nom",
										   "Select nom from Client natural join Billet natural join Concert where id_concert = 2 and id_client != 0 order by nom",
										   "Select nom from Client natural join Billet natural join Concert where id_concert = 3 and id_client != 0 order by nom",
										   "Select nom_groupe from Groupe natural join Joue natural join Concert where id_concert = 1 order by nom_groupe",
										   "Select nom_groupe from Groupe natural join Joue natural join Concert where id_concert = 2 order by nom_groupe",
										   "Select nom_groupe from Groupe natural join Joue natural join Concert where id_concert = 3 order by nom_groupe",
										   "Select * from Concert where id_concert = 1",
										   "Select * from Concert where id_concert = 2",
										   "Select * from Concert where id_concert = 3",
										   "Select nom_groupe, nom_chanson, duree from Chanson natural join Groupe order by nom_groupe",
										   "Select lieu,date_jour,count(*) as nb_billet_dispo from Billet natural join Concert where id_client = 0 group by lieu,date_jour",
										   "Select lieu,date_jour,count(*) as nb_billet_vendu,sum(prix) as benefice from Billet natural join Concert where id_client > 0 group by lieu,date_jour",
										   "Select ville,pays,count(*) as nb_clients from Client natural join Billet where Billet.id_client > 0 group by ville",
										   "Select pays,count(*) as nb_clients from Client natural join Billet where Billet.id_client > 0 group by pays",
										   "Select lieu,date_jour,count(*) as nb_clients_mineurs from Client natural join Concert natural join Billet where Billet.id_client > 0 and age < 18 group by lieu,date_jour",
										   "Select lieu,date_jour,count(*) as adultes from Client natural join Concert natural join Billet where Billet.id_client > 0 and age >= 18 group by lieu,date_jour"};
		type.setPreferredSize(new Dimension(600,25));
		c.gridx = 0;
		c.gridy = 1;
		panSel.add(type,c);
			//Bouton OK1
		JButton butSelectLi = new JButton("OK");
		c.gridx = 2;
		c.gridy = 1;
		panSel.add(butSelectLi,c);;
		//Ajoute le panel à la fenetre
		this.getContentPane().add(panSel, BorderLayout.NORTH);
		//FIN PANEL SELECT
		//
		
		
		//
		//PANEL MODIF
		JPanel panModif = new JPanel();
		panModif.setPreferredSize(new Dimension(this.getWidth(), 200));
		panModif.setBackground(Color.GREEN);
		panModif.setLayout(new GridBagLayout());
		GridBagConstraints t = new GridBagConstraints();
		t.insets = new Insets(3, 3, 3, 3);
			//Label Modif
		JLabel labModif = new JLabel("Modifications : ");
		labModif.setFont(new Font("Arial",Font.BOLD, 30));
		t.gridx = 0;
		t.gridy = 0;
		panModif.add(labModif, t);
			//Bouton Ajouter
		JButton butModifAdd = new JButton("Ajouter");
		t.gridx = 1;
		t.gridy = 1;
		panModif.add(butModifAdd, t);
			//Bouton Modifier
		JButton butModifMod = new JButton("Modifer");
		t.gridx = 1;
		t.gridy = 2;
		panModif.add(butModifMod, t);
			//Bouton Supprimer
		JButton butModifDel = new JButton("Supprimer");
		t.gridx = 1;
		t.gridy = 3;
		panModif.add(butModifDel, t);
			//ComboBox Modif
		JComboBox<String> CBModif = new JComboBox<String>(new String[] {"Groupe",
					 													"Chanson",
					 													"Billet"});
		CBModif.setPreferredSize(new Dimension(200,25));
		t.gridx = 0;
		t.gridy = 2;
		panModif.add(CBModif,t);
		
		//Ajoute le panel à la fenetre
		this.getContentPane().add(panModif, BorderLayout.CENTER);
		//FIN PANEL MODIF
		//
		
		
		
		//
		//PANEL RESULTAT
		JPanel panRes = new JPanel();
		panRes.setPreferredSize(new Dimension(this.getWidth(),200));
		panRes.setBackground(Color.CYAN);
		panRes.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(3,3,3,3);
			//Label Resultat
		JLabel labRes = new JLabel("Resultats : ");
		labRes.setFont(new Font("Arial",Font.BOLD, 30));
		g.gridx = 0;
		g.gridy = 0;
		panRes.add(labRes, g);
			//ScrollPane
		JTextArea resTA = new JTextArea(8,100);
		resTA.setEditable(false);
		g.gridx = 0;
		g.gridy = 1;
		panRes.add(new JScrollPane(resTA), g);
		//Ajoute le panel à la fenetre
		this.getContentPane().add(panRes, BorderLayout.SOUTH);
		//FIN PANEL RESULTAT
		//
		
		
		
		
		
		//
		//Ajoute un eventListener aux boutons
			//LISTE SELECT
		butSelectLi.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					resTA.setText("");
					switch (type.getSelectedIndex()){
					case 0 :
						rs = connexion.query(liRequete[0]);
						try {
							while(rs.next()) {
								if(rs.getString("nom") != "NAC") {
									resTA.append(" " + rs.getString("nom") + "\n");
								}
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 1 :
						rs = connexion.query(liRequete[1]);
						try {
							while(rs.next()) {
								if(rs.getString("nom") != "NAC") {
									resTA.append(" " + rs.getString("nom") + "\n");
								}
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 2 :
						rs = connexion.query(liRequete[2]);
						try {
							while(rs.next()) {
								if(rs.getString("nom") != "NAC") {
									resTA.append(" " + rs.getString("nom") + "\n");
								}
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 3 :
						rs = connexion.query(liRequete[3]);
						try {
							while(rs.next()) {
								resTA.append(" " + rs.getString("nom_groupe") + "\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 4 :
						rs = connexion.query(liRequete[4]);
						try {
							while(rs.next()) {
								resTA.append(" " + rs.getString("nom_groupe") + "\n");
							}
							break;

						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 5 :
						rs = connexion.query(liRequete[5]);
						try {
							while(rs.next()) {
								resTA.append(" " + rs.getString("nom_groupe") + "\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 6 :
						rs = connexion.query(liRequete[6]);
						try {
							while(rs.next()) {
								resTA.append(" Lieu : " + rs.getString("lieu") + "\n");
								resTA.append(" Date : " + rs.getString("date_jour") + "\n");
								resTA.append(" Interieur : " + Integer.parseInt(rs.getString("interieur")) + "\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 7 :
						rs = connexion.query(liRequete[7]);
						try {
							while(rs.next()) {
								resTA.append(" Lieu : " + rs.getString("lieu") + "\n");
								resTA.append(" Date : " + rs.getString("date_jour") + "\n");
								resTA.append(" Interieur : " + Integer.parseInt(rs.getString("interieur")) + "\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 8 :
						rs = connexion.query(liRequete[8]);
						try {
							while(rs.next()) {
								resTA.append(" Lieu : " + rs.getString("lieu") + "\n");
								resTA.append(" Date : " + rs.getString("date_jour") + "\n");
								resTA.append(" Interieur : " + Integer.parseInt(rs.getString("interieur")) + "\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 9 :
						rs = connexion.query(liRequete[9]);
						try {
							while(rs.next()) {
								resTA.append(" " + rs.getString("nom_groupe") + ", " + rs.getString("nom_chanson") + ", " + rs.getString("duree") + "\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 10 :
						rs = connexion.query(liRequete[10]);
						try {
							while(rs.next()) {
								resTA.append(" " + rs.getString("lieu") + " le " + rs.getString("date_jour") + " : " + rs.getString("nb_billet_dispo") + " billets restants.\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 11 :
						rs = connexion.query(liRequete[11]);
						try {
							while(rs.next()) {
								resTA.append(" " + rs.getString("lieu") + " le " + rs.getString("date_jour") + " : " + rs.getString("nb_billet_vendu") + " billets vendu. " + rs.getString("benefice") + "€ de benefices.\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 12 :
						rs = connexion.query(liRequete[12]);
						try {
							while(rs.next()) {
								resTA.append(" " + rs.getString("pays") + ", " + rs.getString("ville") + " : " + rs.getString("nb_clients") + ".\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 13 :
						rs = connexion.query(liRequete[13]);
						try {
							while(rs.next()) {
								resTA.append(" " + rs.getString("pays") + " : " + rs.getString("nb_clients") + ".\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 14 :
						rs = connexion.query(liRequete[14]);
						try {
							while(rs.next()) {
								resTA.append(" " + rs.getString("lieu") + " le " + rs.getString("date_jour") + " : " + rs.getString("nb_clients_mineurs") + " personnes mineurs.\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 15 :
						rs = connexion.query(liRequete[15]);
						try {
							while(rs.next()) {
								resTA.append(" " + rs.getString("lieu") + " le " + rs.getString("date_jour") + " : " + rs.getString("adultes") + " personnes majeurs.\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					
				}
			});
			//MODIF ADD
		butModifAdd.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					modifAdd fenAdd = new modifAdd((String)CBModif.getSelectedItem(), connexion);
					fenAdd.setVisible(true);
				}
		});
			
			//MODIF MOD
		butModifMod.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						modifMod fenMod = new modifMod((String)CBModif.getSelectedItem(), connexion);
						fenMod.setVisible(true);
				}
		});
		
			//MODIF SUPPR
		butModifDel.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					modifDel fenDel = new modifDel((String)CBModif.getSelectedItem(), connexion);
					fenDel.setVisible(true);
				}
		});
	}

}
