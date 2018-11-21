
/**
 * Sprite is a tile in the game, it is also the base class for more interactive objects
 * 
 * @author Peng Cao
 */
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprite {
	private Image image; // image of a tile
	private String imageName = "";
	private boolean isBlocker = false;
	private String colour;
	
	protected int x; // coordinates of the sprite from the level.txt
	protected int y;
	// protected, because its lots of its children change positions, so it makes
	// the modification in their class

	/**
	 * 
	 * @param image_src
	 *            the name of the image
	 * @param x
	 *            the x-coordinate of the sprite
	 * @param y
	 *            the y-coordingate of the sprite
	 */
	public Sprite(String image_src, int x, int y) {
		try {
			switch (image_src) {
				case "0":
					this.imageName = "white"; break;
				case"1":
					this.imageName = "black"; break;
				case"2":
					this.imageName = "cross"; break;
				default:
					this.imageName = image_src;
			
			}
				
			this.image = new Image("res/" + this.imageName + ".png");
			this.x = x;
			this.y = y;
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public Sprite(Sprite another) {
		try {
			this.image = new Image("res/" + another.getImageName() + ".png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.x = another.x;
		this.y = another.y;
		this.imageName = another.getColour();
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	/**
	 * The sprite will be drawn to the center of The Slick game container
	 * object.
	 * 
	 * @param g
	 *            The Slick graphics object, used for drawing.
	 */
	public void render(Graphics g) { // draw the sprite at the
										// shifted-world-center position
		image.draw(x * App.TILE_SIZE + Loader.getxShift(), y * App.TILE_SIZE + Loader.getyShift());
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getImageName() {
		return imageName;
	}

	public boolean isBlocker() {
		return isBlocker;
	}

	public void setBlocker(boolean isBlocker) {
		this.isBlocker = isBlocker;
	}
	
	public String getColour() {
		return this.imageName;
	}
}
