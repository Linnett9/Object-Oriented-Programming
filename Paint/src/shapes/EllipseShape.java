package shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import javax.json.Json;
import javax.json.JsonObject;

import java.awt.geom.AffineTransform;

public class EllipseShape extends Shape {

    public EllipseShape(int x0, int y0, int x1, int y1) {
        super();
        this.x1 = x0;
        this.y1 = y0;
        this.width = Math.abs(x1 - x0);
        this.height = Math.abs(y1 - y0);
        constructShape();
    }

    @Override
    public void constructShape() {
        // Create an ellipse within the bounding rectangle
        awtShape = new Ellipse2D.Float(x1, y1, width, height);
    }

    @Override
    public void paint(Graphics2D g) {
        // Apply rotation before painting the shape
        AffineTransform old = g.getTransform();
        g.rotate(rotationAngle, getCentroid().x, getCentroid().y);
        super.paint(g); 
        g.setTransform(old); 
    }

    public void setEndPoint(int x2, int y2) {
        // Updating the end point and reconstructing the shape
        this.width = Math.abs(x2 - x1);
        this.height = Math.abs(y2 - y1);
        constructShape();
    }
    //Calculates centroid
    @Override
    public Point getCentroid() {
        return new Point(x1 + width / 2, y1 + height / 2);
    }
    @Override
    public void rotate(double radians, Point centroid) {
        this.rotationAngle = radians;
    }
    @Override
    public void scale(double scaleFactor) {
        Point centroid = getCentroid();

        // Adjust the top-left corner of the ellipse relative to the centroid
        x1 = centroid.x - (int)((centroid.x - x1) * scaleFactor);
        y1 = centroid.y - (int)((centroid.y - y1) * scaleFactor);

        // Update the width and height of the ellipse
        width = (int)(width * scaleFactor);
        height = (int)(height * scaleFactor);

        constructShape(); 
    }

    //Converts to JSON
      public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("type", "ellipse")
            .add("x", x1)
            .add("y", y1)
            .add("properties", Json.createObjectBuilder()
                .add("width", width)
                .add("height", height)
                .add("borderColor", colorToString(borderColor))
                .add("borderWidth", borderWidth)
                .add("fillColor", fill ? colorToString(fillColor) : "none")
                .add("rotation", rotationAngle))
            .build();
    }
}
