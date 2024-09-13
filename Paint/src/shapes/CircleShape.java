package shapes;

import java.awt.geom.Ellipse2D;

import javax.json.Json;
import javax.json.JsonObject;

import java.awt.Point;

public class CircleShape extends EllipseShape {

    public CircleShape(int x0, int y0, int diameter) {
        super(x0, y0, x0 + diameter, y0 + diameter);
    }

    @Override
    public void setEndPoint(int x2, int y2) {
        // Calculate the diameter based on the distance from the start point to the end point
        // Use the maximum of width or height to maintain the circle
        int diameter = Math.max(Math.abs(x2 - this.x1), Math.abs(y2 - this.y1));
        this.width = diameter;
        this.height = diameter;
        constructShape();
    }

    @Override
    public void constructShape() {
        // The awtShape for a circle is an ellipse with equal width and height (the diameter).
        awtShape = new Ellipse2D.Float(x1, y1, width, height);
    }
    public Point getCentroid() {
        return 
        new Point(x1 + width / 2, y1 + height / 2);
    }
    //Scaled from the centroid
    @Override
    public void scale(double scaleFactor) {
        Point centroid = getCentroid();

        // Adjust the top-left corner of the circle relative to the centroid
        x1 = centroid.x - (int)((centroid.x - x1) * scaleFactor);
        y1 = centroid.y - (int)((centroid.y - y1) * scaleFactor);

        // Update the diameter of the circle
        int diameter = (int)(width * scaleFactor);
        this.width = diameter;
        this.height = diameter; //Equal

        constructShape(); 
    }
    //Converts to a JSON
    @Override
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("type", "circle")
            .add("x", x1)
            .add("y", y1)
            .add("properties", Json.createObjectBuilder()
                .add("diameter", width)
                .add("borderColor", colorToString(borderColor))
                .add("borderWidth", borderWidth)
                .add("fillColor", fill ? colorToString(fillColor) : "none")
                .add("rotation", rotationAngle))
            .build();
    }
}
