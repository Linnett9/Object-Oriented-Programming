package shapes;

import java.awt.*;
import java.awt.geom.Path2D;

import javax.json.Json;
import javax.json.JsonObject;

import java.awt.geom.AffineTransform;

public class TriangleShape extends Shape {

    private int x3, y3; 
    private double rotationAngle;

    public TriangleShape(int x0, int y0, int x1, int y1) {
        super();
        this.x1 = x0;
        this.y1 = y0;
        this.x2 = x1;
        this.y2 = y1;
        this.rotationAngle = 0;
        calculateThirdVertex();
        constructShape();
    }

    private void calculateThirdVertex() {
        // Calculate the third vertex of the triangle
        this.x3 = x1; 
        this.y3 = y2;
    }

    @Override
    public void constructShape() {
        Path2D.Float path = new Path2D.Float();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.closePath();
        this.awtShape = path;
    }

    @Override
    public void paint(Graphics2D g) {
        AffineTransform old = g.getTransform();
        Point centroid =getCentroid();
        g.rotate(rotationAngle, centroid.x, centroid.y);
 
        super.paint(g); 
        g.setTransform(old);
    }

    public void setEndPoint(int x2, int y2) {
        this.x2 = x2;
        this.y2 = y2;
        calculateThirdVertex();
        constructShape();
    }
    @Override
    public void drag(int dx, int dy) {
        super.drag (dx,dy); 
        x3 += dx;
        y3 += dy;
        constructShape();
    }
    @Override
    public Point getCentroid() {
        int centerX = (x1 + x2 + x3) / 3;
        int centerY = (y1 + y2 + y3) / 3;
        return new Point(centerX, centerY);
    }
    @Override
    public void rotate (double radians, Point centroid) {
        this.rotationAngle = radians;
    }
    @Override
    public void scale(double scaleFactor) {
        Point centroid = getCentroid();

        // Adjust each vertex of the triangle relative to the centroid
        x1 = centroid.x + (int)((x1 - centroid.x) * scaleFactor);
        y1 = centroid.y + (int)((y1 - centroid.y) * scaleFactor);
        x2 = centroid.x + (int)((x2 - centroid.x) * scaleFactor);
        y2 = centroid.y + (int)((y2 - centroid.y) * scaleFactor);
        x3 = centroid.x + (int)((x3 - centroid.x) * scaleFactor);
        y3 = centroid.y + (int)((y3 - centroid.y) * scaleFactor);

        constructShape(); 
    }
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("type", "triangle")
            .add("properties", Json.createObjectBuilder()
                .add("x1", x1)
                .add("y1", y1)
                .add("x2", x2)
                .add("y2", y2)
                .add("x3", x3)
                .add("y3", y3)
                .add("rotation", rotationAngle)
                .add("borderColor", colorToString(borderColor))
                .add("borderWidth", borderWidth)
                .add("fillColor", fill ? colorToString(fillColor) : "none"))
            .build();
    }
    }


