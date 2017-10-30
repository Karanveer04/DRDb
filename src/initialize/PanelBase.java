package initialize;
/**
 * @author Karanveer Singh, This class creates an instance of other classes and adds them to a cardLayout
 * All the classes that are instantiated here are panels, they are being added to the PanelBase and are given a keyword
 * so that they can be called to appear later.
 */
import java.awt.CardLayout;
import java.sql.SQLException;

import javax.swing.JPanel;


import movies.AdvSearchPanel;
import movies.BrowsePanel;
import movies.HomePanel;
import movies.MoviePage;
import user.AddMovie;
import user.CreateAccount;
import user.UserProfile;

public class PanelBase extends JPanel {
	private static final long serialVersionUID = 1L;
	private static CardLayout cardLayout = new CardLayout();
	
    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public PanelBase() throws SQLException {
        initialize();
    }

    public void initialize() throws SQLException {
        this.setLayout(cardLayout);
        // creating instances and adding their panels to the PanelBase one by one
        new HomePanel(this);
		this.add(HomePanel.homePanel, "Home");
		
        this.add(new BrowsePanel(this), "Browse");
        
        new AdvSearchPanel(this);
		this.add(AdvSearchPanel.searchResultPanel, "Adv Search");
		
		this.add(new MoviePage(this), "Movie Page");
		
		// Start Elham
		this.add(new CreateAccount(this), "Create Account");
		this.add(new UserProfile(this), "User Profile");
		this.add(new AddMovie(this), "Add Movie");
		//End Elham
		
        cardLayout.show(this, "Home");
    }
}