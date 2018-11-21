
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Board {
	
	private static ArrayList<Sprite> background; 
	private static Sider sider; 
	private static ArrayList<Sprite> pieces; 
	private static int currentTurn; 
	private static Sprite pieceInHand;
	private static Sprite selected;
	
	
	static int mouseX;
	static int mouseY;
	
	// The edges of the board
	private static int top;
	private static int bottom;
	private static int left;
	private static int right;
	
	// Player moves
	private static int player1Moves = 0;
	private static int player2Moves = 0;
	
	
	
	public static int getCurrentLevel() {
		return 0;
	}

	public static void initVars(int c) {
		Loader.loadSprites("res/"+ c + ".turn");
		background = Loader.getBackground();
		pieces = new ArrayList<Sprite>();
		sider = new Sider();
		currentTurn = c;
		pieceInHand = null;
		selected = null;
		top = 1;
		bottom = 8;
		left = 1;
		right = 8;
		player2Moves = 0;
		player1Moves = 0;
	}

	public Board() throws SlickException {
		initVars(currentTurn);
	}

	public void update(Input input, int delta) {
		
		if (input.isKeyPressed(Input.KEY_R))
			initVars(currentTurn);
		
		mouseX = (int)(Mouse.getEventX()-Loader.getxShift())/App.TILE_SIZE;
		mouseY = (int)(App.SCREEN_HEIGHT- Mouse.getEventY()-Loader.getyShift())/App.TILE_SIZE;
		if(Mouse.isButtonDown(0) && pieceInHand == null) {
			
			if(mouseX==0 && mouseY==3) {	// grabbing piece from the side pool
				pieceInHand = new Sprite("b64", -1, 3);
			}
			else if(mouseX==0 && mouseY<=6) {
				pieceInHand = new Sprite("r64", -1, 6);
			}
		}
		
		if(Mouse.isButtonDown(0) && pieceInHand != null && canPieceBePlaced(mouseX, mouseY)) {	// placing piece on the board
			System.out.println(pieceInHand.getColour());
			pieces.add(new Sprite(pieceInHand));

			process(mouseX, mouseY, pieceInHand.getColour());
			pieceInHand = null;
		}
		
		if(selected!=null) {	// when i have a selected piece
			if(Mouse.isButtonDown(0)) {	// try to place piece on the board
				if(canPieceBePlaced(mouseX, mouseY)) { // valid board placement
					selected.setX(mouseX);
					selected.setY(mouseY);
					process(mouseX, mouseY, selected.getColour());
				}
				selected = null;
			}
		}else{	// when no piece selected
			if(Mouse.isButtonDown(0)) {
				selected = getSelected(mouseX, mouseY);	// try to get a piece in that tile
			}
		}
		
	}

	public void render(Graphics g) {
		
		for (Sprite i : background) {
			i.render(g);
		}
		
		if(sider!=null&&player1Moves<13&&player2Moves<13)
			sider.render(g);

		if(pieceInHand != null) {
			pieceInHand.setX((int)mouseX);
			pieceInHand.setY((int)mouseY);
			pieceInHand.render(g);
		}
		
		for (Sprite i : pieces) {
			i.render(g);
		}
		
	}
	
	public static Sprite getSelected(int x, int y) {
		if(pieces.size()>=1)
			for(Sprite i : pieces) {
				if(i.getX()==x && i.getY()==y) {
					return i;
				}
			}
		return null;
	}
	
	

	public void shrinkBoard() {
		Image image = null;
		try {
			image = new Image("res/cross.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Sprite i:background) {
			if(i.getX()==left||i.getX()==right||i.getY()==top||i.getY()==bottom) {
				i.setImage(image);
			}
			if((i.getX()==left+1|| i.getX()==right-1) && (i.getY()==top+1||i.getY()==bottom-1)) {
				i.setImage(image);
			}
			
		}
		top++;bottom--;left++;right--;
		
	}
	
	
	public boolean canPieceBePlaced(int x, int y) {
		
		if (x>=left && x<=right && y>=top && y<=bottom) {
			if((x==left||x==right)&&(y==top||y==bottom)) {
				return false;
			}
			
			for (Sprite piece : pieces) {
				if (piece.getX() == x && piece.getY() == y) {
					return false;
				}
				
			}
			return true;
		}
		return false;
	}
	
	
	public void process(int x, int y, String colour) {

		if (colour.equals("b64")) {
			player1Moves++;
		}
		else if (colour.equals("r64")) {
			player2Moves++;
		}

		if(player1Moves==12&&player2Moves==12) {
			sider=null;
		}
		
		
		Sprite[][] piecesAr = new Sprite[10][10];
		for (Sprite piece : pieces) {
			piecesAr[piece.getX()][piece.getY()] = piece;
		}
		
		
		
		if (piecesAr[x-1][y] != null && !piecesAr[x-1][y].getColour().equals(colour)) {
			
			if (piecesAr[x-2][y] != null && piecesAr[x-2][y].getColour().equals(colour)) {
				piecesAr[x-1][y] = null;
				System.out.println(1);
			}
			else if ((x-2)==left && (y==top || y==bottom)) {
				piecesAr[x-1][y] = null;
				System.out.println(2);
			}
		}
		if (piecesAr[x+1][y] != null && !piecesAr[x+1][y].getColour().equals(colour)) {
				if (piecesAr[x+2][y] != null && piecesAr[x+2][y].getColour().equals(colour)) {
					piecesAr[x+1][y] = null;
					System.out.println(3);
				}
				else if ((x+2)==right && (y==top || y==bottom)) {
					piecesAr[x+1][y] = null;
					System.out.println(4);
				}
			
		}
		if (piecesAr[x][y+1] != null && !piecesAr[x][y+1].getColour().equals(colour)) {
			if (piecesAr[x][y+2] != null && piecesAr[x][y+2].getColour().equals(colour)) {
				piecesAr[x][y+1] = null;
				System.out.println(5);
			}
			else if ((y+2)==bottom && (x==left || x==right)) {
				piecesAr[x][y+1] = null;
				System.out.println(6);
			}
		}
		if (piecesAr[x][y-1] != null && !piecesAr[x][y-1].getColour().equals(colour)) {
			if (piecesAr[x][y-2] != null && piecesAr[x][y-2].getColour().equals(colour)) {
				piecesAr[x][y-1] = null;
				System.out.println(7);
			}
			else if ((y-2)==top && (x==left || x==right)) {
				piecesAr[x][y-1] = null;
				System.out.println(8);
			}
		}
		
		//-----------------------------
		
		if (piecesAr[x-1][y] != null && !piecesAr[x-1][y].getColour().equals(colour) && piecesAr[x+1][y] != null && !piecesAr[x+1][y].getColour().equals(colour)) {
			piecesAr[x][y] = null;
			System.out.println(9);
		}
		else if (piecesAr[x-1][y] != null && !piecesAr[x-1][y].getColour().equals(colour) && (x+1)==right && (y==top || y==bottom)) {
			piecesAr[x][y] = null;
			System.out.println(10);
		}
		else if (piecesAr[x+1][y] != null && !piecesAr[x+1][y].getColour().equals(colour) && (x-1)==left && (y==top || y==bottom)) {
			piecesAr[x][y] = null;
			System.out.println(11);
		}
		else if (piecesAr[x][y-1] != null && !piecesAr[x][y-1].getColour().equals(colour) && piecesAr[x][y+1] != null && !piecesAr[x][y+1].getColour().equals(colour)) {
			piecesAr[x][y] = null;
			System.out.println(12);
		}
		else if (piecesAr[x][y+1] != null && !piecesAr[x][y+1].getColour().equals(colour) && (y-1)==bottom && (x==right || x==left)) {
			piecesAr[x][y] = null;
			System.out.println(13);
		}
		else if (piecesAr[x][y-1] != null && !piecesAr[x][y-1].getColour().equals(colour) && (y+1)==top && (x==right || x==left)) {
			piecesAr[x][y] = null;
			System.out.println(14);
		}
		// suicide against corner
		// up corner
		else if (piecesAr[x][y+1] != null && !piecesAr[x][y+1].getColour().equals(colour) && (y-1)==top && (x==right || x==left)) {
			piecesAr[x][y] = null;
		}
		// down corner
		else if (piecesAr[x][y-1] != null && !piecesAr[x][y-1].getColour().equals(colour) && (y+1)==bottom && (x==right || x==left)) {
			piecesAr[x][y] = null;
		}
		// left corner
		else if (piecesAr[x+1][y] != null && !piecesAr[x+1][y].getColour().equals(colour) && (x-1)==left && (y==top || y==bottom)) {
			piecesAr[x][y] = null;
		}
		// right corner
		else if (piecesAr[x-1][y] != null && !piecesAr[x-1][y].getColour().equals(colour) && (x+1)==right && (y==top || y==bottom)) {
			piecesAr[x][y] = null;
		}
		
		
		if(player2Moves == 4 && player1Moves == 4) {
			shrinkBoard();
			for (int row = top; row <= bottom+1; row++) {
				for (int col = left; col <= right+1; col++) {
					if (row==top || row==bottom || col==left || col==right) {
						piecesAr[row-1][col-1] = null;
						// Remember to delete the new corners!!!
					}
					
				}
				
			}
			player1Moves=0;
			player2Moves=0;
			
		}
		
		ArrayList<Sprite> piecesAL = new ArrayList<Sprite>();
		for (int row = top; row <= bottom+1; row++) {
			for (int col = left; col <= right+1; col++) {
				if (piecesAr[row-1][col-1] != null) {
					
					piecesAL.add(piecesAr[row-1][col-1]);
				}
			}
		}
		pieces=piecesAL;
		

	}

	
}

