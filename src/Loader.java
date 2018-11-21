
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Loader {

	private static int xShift; // dx (in pixel) needed to move Sprite(s) to the
								// center
	private static int yShift; // dy (in pixel) needed to move Sprite(s) to the
								// center

	
	private static ArrayList<Sprite> background; // wall, floor, target tiles

	
	private static void init() {
		background = new ArrayList<Sprite>();
	}
	
	public static void loadSprites(String filename) {
		init();
		ArrayList<String> al = new ArrayList<String>(); // stores all the info
														// from .lvl file

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String text;
			while ((text = br.readLine()) != null) {
				al.add(text);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] parts = al.get(0).split(" "); // parsing the first line
		xShift = (App.SCREEN_WIDTH - Integer.parseInt(parts[0]) * App.TILE_SIZE) / 2;
		yShift = (App.SCREEN_HEIGHT - Integer.parseInt(parts[1]) * App.TILE_SIZE) / 2;

		
		for (int i = 0; i < al.size() - 1; i++) {
			parts = al.get(i + 1).split(" ");
			Sprite s = new Sprite(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
			background.add(s);
		}
	}

	public static int getxShift() {
		return xShift;
	}

	public static int getyShift() {
		return yShift;
	}
	
	public static ArrayList<Sprite> getBackground() {
		return background;
	}

	public static void setBackground(ArrayList<Sprite> background) {
		Loader.background = background;
	}

}
