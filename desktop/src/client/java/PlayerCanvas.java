package client.java;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class PlayerCanvas extends ResizableCanvas {

	public PlayerCanvas() {
		// Redraw canvas when size changes.
		widthProperty().addListener(evt -> draw());
		heightProperty().addListener(evt -> draw());
	}

	public void draw(){
		double width =  getWidth();
		double height = getHeight();

		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, width, height);

		drawPlayers(gc, width, height);
		drawVillains(gc, width, height);
	}

	// Draws players at their current location.
	public void drawPlayers(GraphicsContext g, double width, double height){

		for(Player player : Game.players){
			int position = player.getPosition();
			if(position>19&&position<39)position++;
			double t = -PI/2 + step*position;

			Point2D point = lemniscate(t);
			Point2D offset = playerOffset(player);

			double playerX = point.getX() + (width/2) -10 + offset.getX();
			double playerY = point.getY() + (height/2)-10 + offset.getY();

			g.setFill(player.getColor());
			g.fillOval(playerX,playerY,20,20);

			// Payer ID numbers for testing
			g.setStroke(Color.RED);
					g.strokeText(Integer.toString(player.getId()),playerX+5,playerY+15);
		}
	}

	public void drawVillains(GraphicsContext g, double width, double height){
		if(Game.villainGang.isActive()){
			int position = Game.villainGang.getPos();
			if(position>19&&position<39)position++;
			double t = -PI/2 + step*position;

			Point2D point = lemniscate(t);

			double playerX = point.getX() + (width/2) -10;
			double playerY = point.getY() + (height/2)-10;

			g.setFill(Color.BLACK);
			g.fillOval(playerX,playerY,20,20);

			// Payer ID numbers for testing
			g.setStroke(Color.RED);
			g.strokeText("V",playerX+5,playerY+15);
		}
	}

	// Calculates each players x,y offset so they don't draw on top of each other.
	public Point2D playerOffset(Player player){
		double baseOffset = getWidth() / 80;
		double offsetX;
		double offsetY;

		switch(player.getId()){
		case 1:     offsetX = baseOffset;
		offsetY = baseOffset;
		break;
		case 2:     offsetX = baseOffset;
		offsetY = -baseOffset;
		break;
		case 3:     offsetX = -baseOffset;
		offsetY = -baseOffset;
		break;
		case 4:     offsetX = -baseOffset;
		offsetY = baseOffset;
		break;
		default:    offsetX = baseOffset;
		offsetY = baseOffset;
		}

		return new Point2D(offsetX,offsetY);
	}

}
