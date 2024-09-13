package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import shapes.Shape;
import shapes.SquareShape;
import shapes.TriangleShape;
import shapes.CircleShape;
import shapes.EllipseShape;
import shapes.LineShape;
import shapes.RectangleShape; 

public class ShapeModelTest {

    private ShapeModel model;

    @Before
    public void setUp() {
        model = new ShapeModel();
    }
//Line Tests
 @Test
    public void testAddLine() {
        Shape shape = new LineShape(0, 0, 10, 10); 
        model.addShape(shape);
        assertEquals(1, model.getShapes().size());
        assertTrue(model.getShapes().contains(shape));
    }

    @Test
    public void testRemoveLine() {
        Shape shape = new LineShape(0, 0, 10, 10); 
        model.addShape(shape);
        model.removeShape(shape);
        assertFalse(model.getShapes().contains(shape));
    }

    @Test
    public void testUndoLine() {
        Shape shape = new LineShape(0, 0, 10, 10); 
        model.addShape(shape);
        model.undo();
        assertFalse(model.getShapes().contains(shape));
    }

    @Test
    public void testRedoLine() {
        Shape shape = new LineShape(0, 0, 10, 10); 
        model.addShape(shape);
        model.undo();
        model.redo();
        assertTrue(model.getShapes().contains(shape));
    }    
// Rectangle Tests
    @Test
    public void testAddShape() {
        Shape shape = new RectangleShape(0, 0, 10, 10);
        model.addShape(shape);
        assertEquals(1, model.getShapes().size());
        assertTrue(model.getShapes().contains(shape));
    }

    @Test
    public void testRemoveShape() {
        Shape shape = new RectangleShape(0, 0, 10, 10); 
        model.addShape(shape);
        model.removeShape(shape);
        assertFalse(model.getShapes().contains(shape));
    }

    @Test
    public void testUndo() {
        Shape shape = new RectangleShape(0, 0, 10, 10); 
        model.addShape(shape);
        model.undo();
        assertFalse(model.getShapes().contains(shape));
    }

    @Test
    public void testRedo() {
        Shape shape = new RectangleShape(0, 0, 10, 10); 
        model.addShape(shape);
        model.undo();
        model.redo();
        assertTrue(model.getShapes().contains(shape));
    }
    //Square Tests
    @Test
    public void testAddSquare() {
        Shape shape = new SquareShape(0, 0, 10);
        model.addShape(shape);
        assertEquals(1, model.getShapes().size());
        assertTrue(model.getShapes().contains(shape));
    }

    @Test
    public void testRemoveSquare() {
        Shape shape = new SquareShape(0, 0, 10);
        model.addShape(shape);
        model.removeShape(shape);
        assertFalse(model.getShapes().contains(shape));
    }

    @Test
    public void testUndoSquare() {
        Shape shape = new SquareShape(0, 0, 10);
        model.addShape(shape);
        model.undo();
        assertFalse(model.getShapes().contains(shape));
    }

    @Test
    public void testRedoSquare() {
        Shape shape = new SquareShape(0, 0, 10);
        model.addShape(shape);
        model.undo();
        model.redo();
        assertTrue(model.getShapes().contains(shape));
    }
    //Ellipse Tests
 @Test
    public void testAddEllipse() {
        Shape shape = new EllipseShape(0, 0, 20, 10);
        model.addShape(shape);
        assertEquals(1, model.getShapes().size());
        assertTrue(model.getShapes().contains(shape));
    }

    @Test
    public void testRemoveEllipse() {
        Shape shape = new EllipseShape(0, 0, 20, 10);
        model.addShape(shape);
        model.removeShape(shape);
        assertFalse(model.getShapes().contains(shape));
    }

    @Test
    public void testUndoEllipse() {
        Shape shape = new EllipseShape(0, 0, 20, 10);
        model.addShape(shape);
        model.undo();
        assertFalse(model.getShapes().contains(shape));
    }

    @Test
    public void testRedoEllipse() {
        Shape shape = new EllipseShape(0, 0, 20, 10);
        model.addShape(shape);
        model.undo();
        model.redo();
        assertTrue(model.getShapes().contains(shape));
    }
    //Circle Tests
    @Test
    public void testAddCircle() {
        Shape shape = new CircleShape(0, 0, 10);
        model.addShape(shape);
        assertEquals(1, model.getShapes().size());
        assertTrue(model.getShapes().contains(shape));
    }

    @Test
    public void testRemoveCircle() {
        Shape shape = new CircleShape(0, 0, 10); 
        model.addShape(shape);
        model.removeShape(shape);
        assertFalse(model.getShapes().contains(shape));
    }

    @Test
    public void testUndoCircle() {
        Shape shape = new CircleShape(0, 0, 10);
        model.addShape(shape);
        model.undo();
        assertFalse(model.getShapes().contains(shape));
    }

    @Test
    public void testRedoCircle() {
        Shape shape = new CircleShape(0, 0, 10); 
        model.addShape(shape);
        model.undo();
        model.redo();
        assertTrue(model.getShapes().contains(shape));
    }
    @Test
    public void testAddTriangle() {
        Shape shape = new TriangleShape(0, 0, 10, 10);
        model.addShape(shape);
        assertEquals(1, model.getShapes().size());
        assertTrue(model.getShapes().contains(shape));
    }

    @Test
    public void testRemoveTriangle() {
        Shape shape = new TriangleShape(0, 0, 10, 10);
        model.addShape(shape);
        model.removeShape(shape);
        assertFalse(model.getShapes().contains(shape));
    }

    @Test
    public void testUndoTriangle() {
        Shape shape = new TriangleShape(0, 0, 10, 10);
        model.addShape(shape);
        model.undo();
        assertFalse(model.getShapes().contains(shape));
    }

    @Test
    public void testRedoTriangle() {
        Shape shape = new TriangleShape(0, 0, 10, 10);
        model.addShape(shape);
        model.undo();
        model.redo();
        assertTrue(model.getShapes().contains(shape));
    }

}
