package shapes;

import java.awt.*;
import java.awt.geom.Line2D;

import javax.json.Json;
import javax.json.JsonObject;

public class LineShape extends Shape {

    //Line construction
    public LineShape(int x1, int y1, int x2, int y2) {
        super();
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        constructShape();
    }

    @Override
    public void constructShape() {
        awtShape = new Line2D.Float(x1, y1, x2, y2);
    }
//Paints line
    @Override
    public void paint(Graphics2D g) {
        g.setColor(getBorderColor());
        g.setStroke(new BasicStroke(getBorderWidth()));
        g.draw(awtShape);
    }

    public void setEndPoint(int x2, int y2) {
        this.x2 = x2;
        this.y2 = y2;
        constructShape();
    }
//Is a given point within a 30.0 pixel tolerance of the line segment
    @Override
    public boolean isClicked(int x, int y) {
    final double TOLERANCE = 30.0;
    return ((Line2D) awtShape).ptSegDist(x, y) <= TOLERANCE; // cast the line and calculate difference of a selected point
    }
//Find central point
    @Override
    public Point getCentroid() {
        return new Point((x1 + x2) / 2, (y1 + y2) / 2);
    }
//Rotates given specified angle
    @Override
    public void rotate(double radians, Point centroid) {
        int newX1 = rotatePointX(x1, y1, centroid, radians);
        int newY1 = rotatePointY(x1, y1, centroid, radians);
        int newX2 = rotatePointX(x2, y2, centroid, radians);
        int newY2 = rotatePointY(x2, y2, centroid, radians);

        x1 = newX1;
        y1 = newY1;
        x2 = newX2;
        y2 = newY2;

        constructShape();
    }

    private int rotatePointX(int x, int y, Point centroid, double radians) {
        return centroid.x + (int)((x - centroid.x) * Math.cos(radians) - (y - centroid.y) * Math.sin(radians));
    }

    private int rotatePointY(int x, int y, Point centroid, double radians) {
        return centroid.y + (int)((x - centroid.x) * Math.sin(radians) + (y - centroid.y) * Math.cos(radians));
    }
//Scales from the centre
    @Override
    public void scale(double scaleFactor) {
        Point centroid = getCentroid();

        x1 = centroid.x + (int)((x1 - centroid.x) * scaleFactor);
        y1 = centroid.y + (int)((y1 - centroid.y) * scaleFactor);
        x2 = centroid.x + (int)((x2 - centroid.x) * scaleFactor);
        y2 = centroid.y + (int)((y2 - centroid.y) * scaleFactor);

        constructShape();
    }
    //Converts to a JSON
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("type", "line")
            .add("x", x1)
            .add("y", y1)
            .add("properties", Json.createObjectBuilder()
                .add("x2", x2)
                .add("y2", y2)
                .add("lineColor", colorToString(borderColor))
                .add("lineWidth", borderWidth))
            .build();
    }
}
