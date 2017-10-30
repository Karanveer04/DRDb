package movies;
/**
 * @author Karanveer Singh, This Class is used to create Objects to initialize Movie icons and titles in the HomePanel.
 */
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class HomeObj {
	private String title;
	private String thumbnailUrl;
	private Image imageIcon = null;
	
	public HomeObj(String title , String thumbnailUrl){
		try{
			URL url = new URL(thumbnailUrl);
			imageIcon = ImageIO.read(url).getScaledInstance(150, 112, 10);
		} catch(IOException ex){
			ex.printStackTrace();
		}
		this.title = title;
		this.thumbnailUrl = thumbnailUrl;	
	}
	
	
	public String getTitle(){
		return this.title;
	}
	public String getThumnailUrl(){
		return this.thumbnailUrl;
	}
	public Image getImageIcon(){
		return this.imageIcon;
	}
	public String toString(){
		return "Title is " +this.title + "  ThumbnailUrl is " + this.thumbnailUrl + "    image is " + this.imageIcon;
	}
}
