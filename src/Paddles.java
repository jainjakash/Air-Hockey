//import javax.swing.ImageIcon;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class Paddles{

	private double padRadius = 35;
	private double padDiameter = 2*padRadius;
	
	//Initial position of paddles
	private double pad1XPos = 200-padRadius;
	private double pad1YPos = 200-padRadius;
	private double pad2XPos = 600-padRadius;
	private double pad2YPos = 200-padRadius;

	//Initial velocities of paddles
	private int pad1XVel = 0;
	private int pad1YVel = 0;
	private int pad2XVel = 0;
	private int pad2YVel = 0;

	private Image paddle1 = new Image("paddle1.png");
	private Image paddle2 = new Image("paddle2.png");

	public Image getPaddle1(){
		return paddle1;
	}

	public Image getPaddle2(){
		return paddle2;
	}

	public void pad1Up(){
		pad1YVel = -3;
	}

	public void pad1Down(){
		pad1YVel = 3;
	}

	public void pad1Left(){
		pad1XVel = -3;
	}

	public void pad1Right(){
		pad1XVel = 3;
	}

	public void pad2Up(){
		pad2YVel = -3;
	}

	public void pad2Down(){
		pad2YVel = 3;
	}

	public void pad2Left(){
		pad2XVel = -3;
	}

	public void pad2Right(){
		pad2XVel = 3;
	}
	
	public double getPad1XPos() {
		return pad1XPos;
	}
	
	public double getPad1YPos() {
		return pad1YPos;
	}
	
	public double getPad1XCen() {
		return pad1XPos+padRadius;
	}
	
	public double getPad1YCen() {
		return pad1YPos+padRadius;
	}
	
	public double getPad2XCen() {
		return pad2XPos+padRadius;
	}
	
	public double getPad2YCen() {
		return pad2YPos+padRadius;
	}
	
	public int getPad1XVel() {
		return pad1XVel;
	}
	
	public int getPad1YVel() {
		return pad1YVel;
	}
	
	public int getPad2XVel() {
		return pad2XVel;
	}
	
	public int getPad2YVel() {
		return pad2YVel;
	}
	
	public Rectangle2D getPad1Rect() {
		return new Rectangle2D(pad1XPos, pad1YPos, 70, 70);
	}
	
	public Rectangle2D getPad2Rect() {
		return new Rectangle2D(pad2XPos, pad2YPos, 70, 70);
	}
	
	public void pad1Released(boolean check){
		if (check == true)
			pad1XVel = 0;
		else
			pad1YVel = 0;
	}

	public void pad2Released(boolean check){
		if (check == true)
			pad2XVel = 0;
		else
			pad2YVel = 0;
	}
	
	public void setPad1XVel(int a) {
		pad1XVel = a;
	}
	
	public void setPad1YVel(int b) {
		pad1YVel = b;
	}
	
	public void setPad2XVel(int c) {
		pad2XVel = c;
	}
	
	public void setPad2YVel(int d) {
		pad2YVel = d;
	}
	
	public void setPad1Pos(int xPos, int yPos) {
		pad1XPos = xPos;
		pad1YPos = yPos;
	}
	
	public void setPad2Pos(int xPos, int yPos) {
		pad2XPos = xPos;
		pad2YPos = yPos;
	}
	
	public void reset() {
		pad1XPos = 165;
		pad1YPos = 165;
		pad2XPos = 565;
		pad2YPos = 165;
	}

	public void move(){

		//initially, add speeds to respective positions
		pad1XPos+=pad1XVel;
		pad1YPos+=pad1YVel;
		pad2XPos+=pad2XVel;
		pad2YPos+=pad2YVel;


		//if Pad 1 hits the top wall

		if (pad1YPos<=0)
			pad1YVel = 1;

		//if Pad 1 hits the bottom wall

		if (pad1YPos>=400-padDiameter)
			pad1YVel = -1;

		//if Pad 1 hits the left wall

		if (pad1XPos<=0)
			pad1XVel = 1;

		//if Pad 1 hits the boundary

		if (pad1XPos>=400-padDiameter)
			pad1XVel = -1;

		//if Pad 2 hits the top wall

		if (pad2YPos<=0)
			pad2YVel = 1;

		//if Pad 2 hits the bottom wall

		if (pad2YPos>=400-padDiameter)
			pad2YVel = -1;

		//if Pad 2 hits the boundary

		if (pad2XPos<=400)
			pad2XVel = 1;

		//if Pad 2 hits the right wall

		if (pad2XPos>=800-padDiameter)
			pad2XVel = -1;

	}
	public void paint(GraphicsContext gc){
		gc.drawImage(paddle1, pad1XPos, pad1YPos);
		gc.drawImage(paddle2, pad2XPos, pad2YPos);
	}


}