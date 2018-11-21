import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sider {
	private Sprite b;
	private Sprite r;


	public Sprite getB() {
		return b;
	}

	public void setB(Sprite b) {
		this.b = b;
	}

	public Sprite getR() {
		return r;
	}

	public void setR(Sprite r) {
		this.r = r;
	}

	public Sider() {
		this.b = new Sprite("b64", -1, 3);
		this.r = new Sprite("r64", -1, 6);
	}

	public void render(Graphics g) {
		this.b.render(g);
		this.r.render(g);
	}
}
