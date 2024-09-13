package guiDelegate;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import model.ShapeModel;
import model.ShapeType;
import java.awt.image.BufferedImage;


// Main application window.

public class DrawingApplication extends JFrame {
    private JCheckBox fillCheckBox; 
    private JButton selectionButton; 
    private DrawingPanel drawingPanel;
    
    public DrawingApplication() {

        // Set Title Window
        super("Vector Drawing Application");

        // Create a model instance
        ShapeModel model = new ShapeModel();

        // initalise fill checkbox
        fillCheckBox = new JCheckBox("Fill");

        //Initiaise drawing panel
        drawingPanel = new DrawingPanel(model);

        // Set up the main window layout
        this.setLayout(new BorderLayout());
        this.add(drawingPanel, BorderLayout.CENTER);

        // Add a toolbar
        JToolBar toolBar = createToolBar(model, drawingPanel);
        this.add(toolBar, BorderLayout.NORTH);

        // Add a menu bar
    
        setJMenuBar(createMenuBar(model, drawingPanel));

        // Configure the main window
        configureWindow();
    }

    private void configureWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600); // Set the initial size
        this.setLocationRelativeTo(null); // Center the window
        this.setVisible(true);
    }

    private JMenuBar createMenuBar(ShapeModel model, DrawingPanel drawingPanel) {
        JMenuBar menuBar = new JMenuBar();
    
        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportItem = new JMenuItem("Export");
        exportItem.addActionListener(e -> exportDrawing(drawingPanel));
        fileMenu.add(exportItem);
    
        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem undoItem = new JMenuItem("Undo");
        undoItem.addActionListener(e -> model.undo());
        JMenuItem redoItem = new JMenuItem("Redo");
        redoItem.addActionListener(e -> model.redo());
        editMenu.add(undoItem);
        editMenu.add(redoItem);
    
        // Shape Menu //** NEED TO ADD SHAPE TYPE */
        JMenu shapeMenu = new JMenu("Shapes");
        JMenuItem lineItem = new JMenuItem("Line");
        lineItem.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.LINE));
        JMenuItem rectangleItem = new JMenuItem("Rectangle");
        rectangleItem.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.RECTANGLE));
        JMenuItem ellipseItem = new JMenuItem("Ellipse");
        ellipseItem.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.ELLIPSE));
        JMenuItem triangleItem = new JMenuItem("Triangle");
        triangleItem.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.TRIANGLE));
        shapeMenu.add(lineItem);
        shapeMenu.add(rectangleItem);
        shapeMenu.add(ellipseItem);
        shapeMenu.add(triangleItem);
    
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
    
        return menuBar;
    }
    
    private JToolBar createToolBar(ShapeModel model, DrawingPanel drawingPanel) {
        JToolBar toolBar = new JToolBar();
    
        // Shape buttons
        JButton lineButton = new JButton("Line");
        lineButton.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.LINE));
        JButton rectangleButton = new JButton("Rectangle");
        rectangleButton.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.RECTANGLE));
        JButton ellipseButton = new JButton("Ellipse");
        ellipseButton.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.ELLIPSE));
        JButton triangleButton = new JButton("Triangle");
        triangleButton.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.TRIANGLE));
        JButton squareButton = new JButton ("Square");
        squareButton.addActionListener(e-> drawingPanel.setCurrentShapeType(ShapeType.SQUARE));
        JButton circleButton = new JButton ("Circle");
        circleButton.addActionListener(e-> drawingPanel.setCurrentShapeType(ShapeType.CIRCLE));
    
        // Color button for border color
        JButton borderColorButton = new JButton("Border Color");
        borderColorButton.addActionListener(e -> {
            Color chosenColor = JColorChooser.showDialog(drawingPanel, "Choose a border color", Color.BLACK);
            if (chosenColor != null) {
                drawingPanel.updateSelectedShapeBorderColor(chosenColor);
            }
        });
    
        // Slider for border width
        JSlider borderWidthSlider = new JSlider(1, 10); // Example range
        borderWidthSlider.setMajorTickSpacing(1);
        borderWidthSlider.setPaintTicks(true);
        borderWidthSlider.setPaintLabels(true);
        borderWidthSlider.addChangeListener(e -> {
            float width = borderWidthSlider.getValue();
            drawingPanel.updateSelectedShapeBorderWidth(width);
        });
    
        // Fill color and state
        JButton fillColorButton = new JButton("Fill Color");
        fillColorButton.addActionListener(e -> {
            Color chosenColor = JColorChooser.showDialog(drawingPanel, "Choose a fill color", Color.BLACK);
            if (chosenColor != null) {
                drawingPanel.updateSelectedShapeFill(chosenColor, fillCheckBox.isSelected());
            }
        });
        JCheckBox fillCheckBox = new JCheckBox("Fill"); 
        fillCheckBox.addActionListener(e -> {
            Color currentColor = drawingPanel.getSelectedShapeFillColor();
            drawingPanel.updateSelectedShapeFill(currentColor, fillCheckBox.isSelected());
        });
    
        // Undo/redo buttons
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> model.undo());
        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> model.redo());
    
        // Rotation button
        JButton rotateButton = new JButton("Rotate");
        rotateButton.addActionListener(e -> {
            drawingPanel.toggleRotationMode();
            updateRotateButtonText(rotateButton);
        });
        
        //slider for rotation
        JSlider rotationSlider = new JSlider(0, 360, 0); 
        rotationSlider.setMajorTickSpacing(90);
        rotationSlider.setMinorTickSpacing(30);
        rotationSlider.setPaintTicks(true);
        rotationSlider.setPaintLabels(true);
        rotationSlider.addChangeListener(new ChangeListener() {
  @Override
    public void stateChanged(ChangeEvent e) {
        if (!rotationSlider.getValueIsAdjusting()) {
         int angle = rotationSlider.getValue();
         drawingPanel.rotateSelectedShape(angle);
     }
 }
});
JTextField scaleFactorTextField = new JTextField("1.0", 1); // Default to no scaling
scaleFactorTextField.addActionListener(e -> {
    try {
        double scaleFactor = Double.parseDouble(scaleFactorTextField.getText());
        drawingPanel.scaleSelectedShape(scaleFactor);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(drawingPanel, "Please enter a valid decimal number for scaling.");
    }
});

// Label for the size text field
JLabel scaleFactorLabel = new JLabel("Scale Factor:");


        //Selection button
        JButton selectionButton= new JButton("Select");
        selectionButton.addActionListener(e->{
        drawingPanel.toggleSelectionMode();
        updateSelectionButtonText();
        });
        // Adding buttons to the toolbar
        toolBar.add(lineButton);
        toolBar.add(rectangleButton);
        toolBar.add(ellipseButton);
        toolBar.add(triangleButton);
        toolBar.add(squareButton);
        toolBar.add(circleButton);
        toolBar.addSeparator();
        toolBar.add(borderColorButton);
        toolBar.add(borderWidthSlider);
        toolBar.addSeparator();
        toolBar.add(fillColorButton);
        toolBar.add(fillCheckBox);
        toolBar.addSeparator();
        toolBar.add(undoButton);
        toolBar.add(redoButton);
        toolBar.addSeparator();
        toolBar.add(rotateButton);
        toolBar.add(rotationSlider);
        toolBar.add(scaleFactorLabel);
        toolBar.add(scaleFactorTextField);
        toolBar.add(selectionButton);
    
        return toolBar;
    }


    // Helper methods for actions
   private void exportDrawing(DrawingPanel drawingPanel) {
    // Choose where to save the file
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Specify a file to save");
    int userSelection = fileChooser.showSaveDialog(drawingPanel);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();
        
        // Add a file extension if not present
        if (!filePath.toLowerCase().endsWith(".png")) {
            fileToSave = new File(filePath + ".png");
        }
        
        // Create BufferedImage
        BufferedImage image = new BufferedImage(drawingPanel.getWidth(), drawingPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        // Invoke painting
        drawingPanel.paintComponent(g2d);
        
        // Dispose of the graphics
        g2d.dispose();
        
        try {
            // Save as PNG
            ImageIO.write(image, "png", fileToSave);
            JOptionPane.showMessageDialog(drawingPanel, "Export successful!", "Export", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(drawingPanel, "Error during export: " + ex.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    private void chooseColor(DrawingPanel drawingPanel) {
        Color chosenColor = JColorChooser.showDialog(drawingPanel, "Choose a color", Color.BLACK);
        if (chosenColor != null) {
            drawingPanel.updateSelectedShapeFill(chosenColor, fillCheckBox.isSelected());
        }
    }
    
    
    // Main entry point of the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrawingApplication());
    }
private void updateSelectionButtonText() {
    if (this.drawingPanel != null) {
        if (drawingPanel.isSelectionMode()) {
            selectionButton.setText("Drawing Mode");
        } else {
            selectionButton.setText("Select Mode");
        }
    }
}
private void updateRotateButtonText(JButton rotateButton) {
    if (drawingPanel.isRotationMode()) {
        rotateButton.setText("Move Mode");
    } else {
        rotateButton.setText("Rotate Mode");
    }
}

}
