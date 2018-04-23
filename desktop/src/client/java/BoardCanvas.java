package client.java;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

public class BoardCanvas extends ResizableCanvas {

	private int currentTile;
	private Pane wrapperPane = (Pane) this.getParent();

	public BoardCanvas() {
		// Redraw canvas when size changes
		currentTile = 0;

		widthProperty().addListener(evt -> {
			try {
				draw();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		});

		heightProperty().addListener(evt -> {
			try {
				draw();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		});
	}

	public void draw() throws IOException, JSONException {
		double width = getWidth();
		double height = getHeight();

		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, width, height);

		Image background = new Image("/client/resources/images/space.jpg");
		gc.drawImage(background, 0, 0, width, height);

		drawInfinityShape(gc);
		drawTiles(gc);
	}

	// Draws infinity curve
	private void drawInfinityShape(GraphicsContext g) {
		double width = getWidth();
		double height = getHeight();

		PixelWriter pw = g.getPixelWriter();

		for (double t = -PI; t < PI; t += 0.02) {
			Point2D point = lemniscate(t);

			int pixel_x = (int) Math.round(point.getX() + width / 2);
			int pixel_y = (int) Math.round(point.getY() + height / 2);

			g.setFill(Color.rgb(45, 88, 158,0.2));
			g.fillOval(pixel_x-37.5,pixel_y-37.5,75,75);
		}
		for (double t = -PI; t < PI; t += 0.0001) {
			Point2D point = lemniscate(t);

			int pixel_x = (int) Math.round(point.getX() + width / 2);
			int pixel_y = (int) Math.round(point.getY() + height / 2);

			pw.setColor(pixel_x, pixel_y, Color.GOLD);
		}
	}

	// Draws all tiles.
	private void drawTiles(GraphicsContext g) throws IOException, JSONException {
		int locIndex = 0;
		for (double t = -PI / 2; t < PI - step; t += step) {
			if (Math.abs(t - PI / 2) > 0.000001) {    // so we don't draw two tiles in the centre.
				Point2D point = lemniscate(t);
				if(Game.locationsSet){
					drawTile(point, g, Game.locations.get(locIndex));
					drawImagedTile(point,g,Game.locations.get(locIndex));
				}
				else{
					drawTile(point, g, Game.locations.get(locIndex));
				}
				locIndex+=1;
			}
		}
		for (double t = -PI; t < -PI / 2 - step; t += step) {
			Point2D point = lemniscate(t);
			if(Game.locationsSet){
				drawTile(point, g, Game.locations.get(locIndex));
				drawImagedTile(point,g,Game.locations.get(locIndex));
			}
			else{
				drawTile(point, g, Game.locations.get(locIndex));
				g.setFill(Color.BLACK);
				g.fillText(Game.locations.get(locIndex).getName(), point.getX() + (getWidth() / 2) - 20, point.getY() + (getHeight() / 2));
			}
			locIndex+=1;
		}
	}

	// Draws individual tiles.
	private void drawTile(Point2D point, GraphicsContext g, Location location) throws IOException, JSONException {

		double width = getWidth();
		double height = getHeight();
		double x = point.getX();
		double y = point.getY();

		g.setFill(Color.rgb(45, 88, 158,0.2));
		g.setStroke(location.getColour());

		g.fillOval(x + (width / 2) - width / 34, y + (height / 2) - width / 34, width / 17, width / 17);
		g.strokeOval(x + (width / 2) - width / 34, y + (height / 2) - width / 34, width / 17, width / 17);

		//g.setFill(Color.WHITE);
		//g.fillText(location.getName(), x + (width / 2) - 20, y + (height / 2));
	}

	private void drawImagedTile(Point2D point, GraphicsContext g, Location location){

		double width = getWidth();
		double height = getHeight();
		double x = point.getX();
		double y = point.getY();

		Image image = location.getImage();
		ColorAdjust ca = new ColorAdjust();

		if(image != null) {
			try {
				ImagePattern imgPattern = new ImagePattern(image);
				g.setEffect(new Glow(.4));
				g.setFill(imgPattern);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		else{
			g.setFill(Color.BLACK);
		}

		g.setStroke(Color.TRANSPARENT);
		g.fillOval(x + (width / 2) - width / 40, y + (height / 2) - width / 40, width / 20, width / 20);
		g.strokeOval(x + (width / 2) - width / 40, y + (height / 2) - width / 40, width / 20, width / 20);

		g.setEffect(null);
		//g.setFill(Color.BLACK);
		//g.fillRect(x + (width / 2) - width / 40, y + (height / 2) - 5, width / 20, 20);

		g.setFill(Color.GOLD);
		g.fillText(location.getName(), x + (width / 2), y + (height / 2) - width / 30);
	}

	private Image getImage(Location location){

		StringBuilder sb = new StringBuilder();
		sb.append("/client/resources/images/worlds/");
		sb.append(location.getName().trim().replace(":","").toString());
		sb.append(".jpg");

		try{
			Image image = new Image( sb.toString() );
			return image;
		}catch(Exception e){
			return null;
		}
	}

	public void getImages(){
		for(Location loc : Game.locations){
			loc.setImage(getImage(loc));
		}
	}

}
