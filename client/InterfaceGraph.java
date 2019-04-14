import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class InterfaceGraph extends JFrame implements ActionListener{
    private JComboBox<String> concert;
    private JTextField nom;
    private JTextField prenom;
    private JTextField age;
    private JTextField num_rue;
    private JTextField rue;
    private JTextField ville;
    private JTextField pays;
    private JComboBox<String> place;
    private JLabel lnom;
    private JLabel lprenom;
    private JLabel lage;
    private JLabel lnum_rue;
    private JLabel lrue;
    private JLabel lville;
    private JLabel lpays;
    private JLabel lplace;
    private JLabel lconcert;
    private JButton btn;
    private JLabel jlabel;
    private JLabel titleres;
    private ResultSet rs;
    private Connexion connexion = new Connexion("../Database.db");
    //private Connection co2 = DriverManager.getConnection("Database.db");
	
	public InterfaceGraph() {
		super("Reservation de billet");
		setSize(1200,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setLayout(new BorderLayout());
		connexion.connect();
		JPanel panSel = new JPanel();
		panSel.setPreferredSize(new Dimension(this.getWidth(),200));
		panSel.setBackground(Color.GRAY);
		panSel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(4,4,4,4);
		titleres = new JLabel("Reservez votre billet");
		titleres.setFont(new Font("Arial",Font.BOLD, 30));
		c.gridx = 5;
		c.gridy = 0;
		panSel.add(titleres,c);
			//Liste
		
		//GridBagConstraints q = new GridBagConstraints();
		//c.insets = new Insets(10, 10, 10, 10);
		concert = new JComboBox<String>();
		Connection conn = null;
		try {
		   	Class.forName("org.sqlite.JDBC");
		    conn = DriverManager.getConnection("jdbc:sqlite:../Database.db");
		    conn.setAutoCommit(false);
		    System.out.println("Opened database successfully");
		     
		    ResultSet rs = connexion.query("Select lieu, date_jour from Concert");
		    try {
		    	while(rs.next()) {
					concert.addItem(rs.getString("lieu") + ", " + rs.getString("date_jour"));
				}
				conn.commit();
				conn.close();
		    } catch (SQLException e1) {
				e1.printStackTrace();
			}
		}catch ( Exception e1 ) {
		   	System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
		}
		c.gridx = 1;
		c.gridy = 1;
		lnom = label("Nom:");
		nom = entree("",7);
		lprenom = label("Prenom:");
        prenom = entree("",7);
        lage = label("Age:");
        age = entree("0",7);
        lnum_rue = label("N° de rue:");
        num_rue = entree("0",7);
        lrue = label("Rue:");
        rue = entree("",7);
        lville = label("Ville:");
        ville = entree("",7);
        lpays = label("Pays:");
        pays = entree("",7);
        lplace = label("Type de place:");
        lconcert = label("Concert:");
        panSel.add(lnom,c);
        c.gridx++;
        panSel.add(nom,c);
        c.gridx++;
        panSel.add(lprenom,c);
        c.gridx++;
        panSel.add(prenom,c);
        c.gridx++;
        panSel.add(lage,c);
        c.gridx++;
        panSel.add(age,c);
        c.gridx = 1;
        c.gridy++;
        panSel.add(lnum_rue,c);
        c.gridx++;
        panSel.add(num_rue,c);
        c.gridx++;
        panSel.add(lrue,c);
        c.gridx++;
        panSel.add(rue,c);
        c.gridx++;
        panSel.add(lville,c);
        c.gridx++;
        panSel.add(ville,c);
        c.gridx = 1;
        c.gridy++;
        panSel.add(lpays,c);
        c.gridx++;
        panSel.add(pays,c);
        String[] c3 = {"Fosse", "Standard", "VIP"};
        place = new JComboBox<String>(c3);
        c.gridx++;
        panSel.add(lplace,c);
        c.gridx++;
        panSel.add(place,c);
        c.gridx++;
        panSel.add(lconcert,c);
        c.gridx++;
        panSel.add(concert,c);
        btn = new JButton("Reserver un billet");
        btn.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e){
						Connection conn2 = null;
				    	Statement stmt = null;
				        if(infosValides()) {
				        	if(!clientExiste()){
								try {
								   	Class.forName("org.sqlite.JDBC");
									conn2 = DriverManager.getConnection("jdbc:sqlite:../Database.db");
									conn2.setAutoCommit(false);
						  			int idClient = 0;;
						  			ResultSet rs = connexion.query("Select id_client from Client order by id_client desc limit 1;");
						  			stmt = conn2.createStatement();
						  			while(rs.next()) {
							  			idClient = rs.getInt("id_client")+1;
						  			}
						    		String res = "INSERT INTO Client VALUES("+idClient+",'"+nom.getText()+"','"+prenom.getText()+"',"+(int) Integer.parseInt(age.getText())+","+(int) Integer.parseInt(num_rue.getText())+",'"+rue.getText()+"','"+ville.getText()+"','"+pays.getText()+"')";
						    		int col = stmt.executeUpdate(res);
						    		//System.out.println(res);
						    		stmt.close();
						    		conn2.commit();
						    		conn2.close();
						  			JOptionPane.showMessageDialog(panSel, "Votre compte a été crée.");
						    	}catch ( Exception e1 ){
			   						System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
								}
							}
							if(billetDispo()){
								try {
								  Class.forName("org.sqlite.JDBC");
								  conn2 = DriverManager.getConnection("jdbc:sqlite:../Database.db");
								  conn2.setAutoCommit(false);
								  System.out.println("Opened database successfully");

								  //Modifie le billet
								  stmt = conn2.createStatement();
								  String sql = "UPDATE Billet " +
										       "SET id_client = (select id_client from Client where nom = '"+nom.getText()+"')" +
										       "where id_billet = (select id_billet from Billet where id_concert = "+(concert.getSelectedIndex()+1)+" and id_client = 0 and categorie = '"+place.getSelectedItem()+"' limit 1);"; 
								  stmt.executeUpdate(sql);
								  if((int) Integer.parseInt(age.getText()) < 18){
								  	sql = "UPDATE Billet " +
										       "SET prix = prix * 0.75 " +
										       "where id_client = (select id_client from Client where nom = '"+nom.getText()+"' limit 1)";
									stmt.executeUpdate(sql);
									JOptionPane.showMessageDialog(panSel, "La réduction spécial Mineur a été ajouté à votre billet. Le prix du billet a été réduit de 20€.");
								  }
								  stmt.close();
								  conn2.commit();
								  conn2.close();
								  JOptionPane.showMessageDialog(panSel, "Un billet a été réservé.");
								} catch ( Exception e1 ) {
									JOptionPane.showMessageDialog(panSel, "Il n'y a plus de billet "+place.getSelectedItem()+" au concert se déroulant à "+concert.getSelectedItem()+". Désolé pour ce désagrément.");
									e1.printStackTrace();
								}
								/*try {
									conn2.commit();
								} catch (SQLException e1) {
									e1.printStackTrace();
									
								}
								try {
									conn2.close();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}*/
							}
							else JOptionPane.showMessageDialog(panSel, "Il n'y a plus de billet "+place.getSelectedItem()+" au concert se déroulant à "+concert.getSelectedItem()+". Désolé pour ce désagrément.");
						}
						else{
		    				JOptionPane.showMessageDialog(panSel, "Au moins une des informations entrées est incorrecte. Veuillez réessayer.");
		    			}
	    				try {
	    				conn2 = DriverManager.getConnection("jdbc:sqlite:../Database.db");
						conn2.setAutoCommit(false);
						conn2.commit();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						try {
							conn2.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
				}
			}
		);
		c.gridx++;     
        panSel.add(btn,c);
        c.gridx = 5;
        c.gridy++;
        JLabel jlabel = new JLabel("Si vous êtes mineur, votre billet reçoit une réduction de 20€.");
		jlabel.setFont(new Font("Arial",Font.BOLD, 10));
        panSel.add(jlabel,c);
		this.getContentPane().add(panSel, BorderLayout.NORTH);
		c.weightx=1.;
    	c.fill=GridBagConstraints.HORIZONTAL;
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
		JLabel labModif = new JLabel("Rechercher des informations : ");
		labModif.setFont(new Font("Arial",Font.BOLD, 30));
		t.gridx = 0;
		t.gridy = 0;
		panModif.add(labModif, t);
			//Bouton Modifier
		JButton butModifMod = new JButton("Rechercher");
		t.gridx = 1;
		t.gridy = 2;
		panModif.add(butModifMod, t);
			//ComboBox Modif
		JComboBox<String> CBModif = new JComboBox<String>();
		CBModif.setPrototypeDisplayValue("Madison Square Garden, New York, 2018-07-22");
		Connection conn3 = null;
		try {
		   	Class.forName("org.sqlite.JDBC");
		    conn3 = DriverManager.getConnection("jdbc:sqlite:../Database.db");
		    conn3.setAutoCommit(false);
		    System.out.println("Opened database successfully");
		     
		    ResultSet rs = connexion.query("Select lieu, date_jour from Concert");
		    try {
		    	while(rs.next()) {
					CBModif.addItem(rs.getString("lieu") + ", " + rs.getString("date_jour"));
				}
				conn3.commit();
				conn3.close();
		    } catch (SQLException e1) {
				e1.printStackTrace();
			}
		}catch ( Exception e1 ) {
		   	System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
		}
		t.gridx = 0;
		t.gridy = 2;
		panModif.add(CBModif,t);
		
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
		resTA.setFont(new Font("Arial",Font.BOLD, 12));
		g.gridx = 0;
		g.gridy = 1;
		panRes.add(new JScrollPane(resTA), g);
		
		this.getContentPane().add(panRes, BorderLayout.SOUTH);
		//FIN PANEL RESULTAT
		//
		butModifMod.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					resTA.setText("");
					resTA.append("Voici les informations de ce concert :\n");
					switch (CBModif.getSelectedIndex()){
					case 0 :
						ResultSet rs = connexion.query("Select lieu, date_jour, horaire, interieur from Concert natural join Joue natural join Groupe where id_concert = 1 order by id_concert");
						try {
							resTA.append("Lieu: " + rs.getString("lieu") + "\nDate: " + rs.getString("date_jour") + "\nDébute à " + rs.getString("horaire") +"\n"+ rs.getString("interieur") + " (1 = intérieur, 0 = extérieur)\nTarifs:\nFosse: 30€\nStandard: 50€\nVIP: 100€\n");
							ResultSet rg = connexion.query("Select nom_chanson, nom_groupe from Groupe natural join Joue natural join Chanson where id_concert = 1 order by id_concert");
							while(rg.next()){
							 resTA.append("'"+rs.getString("nom_chanson") + "', par " + rs.getString("nom_groupe") + "\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 1 :
						rs = connexion.query("Select lieu, date_jour, horaire, interieur from Concert natural join Joue natural join Groupe where id_concert = 2 order by id_concert");
						try {
							resTA.append("Lieu: " + rs.getString("lieu") + "\nDate: " + rs.getString("date_jour") + "\nDébute à " + rs.getString("horaire") +"\n"+ rs.getString("interieur") + " (1 = intérieur, 0 = extérieur)\nTarifs:\nFosse: 35€\nStandard: 60€\nVIP: 125€\n");
							ResultSet rg = connexion.query("Select nom_chanson, nom_groupe from Groupe natural join Joue natural join Chanson where id_concert = 2 order by id_concert");
							while(rg.next()){
							 resTA.append("'"+rs.getString("nom_chanson") + "', par " + rs.getString("nom_groupe") + "\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					case 2 :
						rs = connexion.query("Select lieu, date_jour, horaire, interieur from Concert natural join Joue natural join Groupe where id_concert = 3 order by id_concert");
						try {
							resTA.append("Lieu: " + rs.getString("lieu") + "\nDate: " + rs.getString("date_jour") + "\nDébute à " + rs.getString("horaire") +"\n"+ rs.getString("interieur") + " (1 = intérieur, 0 = extérieur)\nTarifs:\nFosse: 40€\nStandard: 75€\nVIP: 150€\n");
							ResultSet rg = connexion.query("Select nom_chanson, nom_groupe from Groupe natural join Joue natural join Chanson where id_concert = 3 order by id_concert");
							while(rg.next()){
							 resTA.append("'"+rs.getString("nom_chanson") + "', par " + rs.getString("nom_groupe") + "\n");
							}
							break;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		);
        setExtendedState(JFrame.MAXIMIZED_BOTH); //mettre la fenetre en fullscreen
        setVisible(true);
	}
	
	public boolean clientExiste(){
		int nb = 0; 
		ResultSet rs = connexion.query("Select id_client from Client where nom = '"+nom.getText()+"' and prenom = '"+prenom.getText()+"' and age = "+(int) Integer.parseInt(age.getText())+" and num_rue = "+(int) Integer.parseInt(num_rue.getText())+" and rue = '"+rue.getText()+"' and ville = '"+ville.getText()+"' and pays = '"+pays.getText()+"'");
		try{while(rs.next()) nb++;}
		catch(SQLException e1) {System.out.println("Il y a une erreur");}
		return nb != 0;
	}
	
	public boolean billetDispo(){
		int nb = 0; 
		String str = "Select id_billet from Billet where id_concert = "+(concert.getSelectedIndex()+1)+" and id_client = 0 and categorie = '"+place.getSelectedItem()+"';";
		ResultSet rs = connexion.query(str);
		//System.out.println(str);
		try{while(rs.next()) nb++;}
		catch(SQLException e1) {System.out.println("Il y a une erreur");}
		return nb != 0;
	}
	
	public JTextField entree(String nom, int size){ 
    	JTextField e = new JTextField(nom,size);
    	e.setFont(new Font("Arial", Font.BOLD, 14));
    	e.setMinimumSize(e.getPreferredSize());
    	return e;
    }
    
    public JLabel label(String nom){
    	JLabel l = new JLabel(nom);
		titleres.setFont(new Font("Arial",Font.BOLD, 10));
		return l;
    }
    
    public boolean infosValides(){
        return (!nom.getText().equals("") &&
                !prenom.getText().equals("") &&
                0 < (int) Integer.parseInt(age.getText()) && 
                (int) Integer.parseInt(age.getText()) < 100 &&
                0 < (int) Integer.parseInt(num_rue.getText()) && 
                (int) Integer.parseInt(num_rue.getText()) < 1000 &&
                !rue.getText().equals("") &&
                !ville.getText().equals("") &&
                !pays.getText().equals(""));
    }
    
    public void actionPerformed(ActionEvent e){
        if(infosValides()) titleres.setText("Bon");
        else titleres.setText("Pas bon");
    }
}
