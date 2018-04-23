package game_interfaces;

import java.awt.*;

public interface Colourable {

	void setColour(String colour);
	String getColour();

	void setRGB(int r, int g, int b);
	void setRGB(Color colour);
	Color getRGBColour();
}
