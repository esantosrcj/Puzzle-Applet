import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.*;

import javax.swing.*;
import java.util.Random;

public class Puzzle extends JFrame implements ActionListener {

	private JPanel centerPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	private JLabel label;
	private JButton shuffle;
	private JButton picture1;
	private JButton picture2;
	private JButton picture3;
	private JButton picture4;
	private JButton reset;
	private JButton upload;
    
	private Image source;
	private Image image;
	int[][] pos;
	boolean shuffleOn = false;
	private int[] victory;
   
	JButton buttArray[];
	int width, height, win_width, win_height, picWidth, picHeight;
	int buttonWidth, buttonHeight;

	Random ranPos = new Random();
	//JFileChooser fc = new JFileChooser();

	public Puzzle(String filename) {
		// Puzzle array
        pos = new int[][] {
                            {0, 1, 2}, 
                            {3, 4, 5}, 
                            {6, 7, 8}, 
                            {9, 10, 11}
                        };
        
		// Winning array
		victory = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

		// Button array
		buttArray = new JButton[11];

		// Panels
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(4, 4, 0, 0));

		westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(2, 2, 0, 0));

		eastPanel = new JPanel();
		eastPanel.setLayout(new GridLayout(2, 2, 0, 0));

		// Buttons
		shuffle = new JButton("Shuffle");
		shuffle.addActionListener(this);

		picture1 = new JButton("Desert");
		picture1.addActionListener(this);

		picture2 = new JButton("Lighthouse");
		picture2.addActionListener(this);

		picture3 = new JButton("Jellyfish");
		picture3.addActionListener(this);

		picture4 = new JButton("Penguins");
		picture4.addActionListener(this);

		reset = new JButton("Reset");
		reset.addActionListener(this);

		upload = new JButton("Upload");
		upload.addActionListener(this);
        
        
		// Image
		ImageIcon pic = new ImageIcon(Puzzle.class.getResource(filename));
		source = pic.getImage();

		// Image size
		width = pic.getIconWidth();
		height = pic.getIconHeight();
        
       
		// Puzzle piece size
		buttonWidth = (width / 3);
		buttonHeight = (height / 4);

		// Add panels to frame
		add(centerPanel, BorderLayout.CENTER);
		add(westPanel, BorderLayout.WEST);
		add(eastPanel, BorderLayout.EAST);

		// Set size of panels
		centerPanel.setPreferredSize(new Dimension(width, height));

		// Add buttons to panels
		eastPanel.add(shuffle);
		eastPanel.add(reset);
		eastPanel.add(upload);
		westPanel.add(picture1);
		westPanel.add(picture2);
		westPanel.add(picture3);
		westPanel.add(picture4);

		// Border between panels
		westPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		eastPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Set size of buttons
		shuffle.setPreferredSize(new Dimension(100, 50));
		picture1.setPreferredSize(new Dimension(100, 50));

        int  buttIndex = 0;
        for ( int i = 0; i < 4; i++) {
            for ( int j = 0; j < 3; j++) {
                if ( j == 2 && i == 3) {
                    label = new JLabel("");
                    centerPanel.add(label);
                } else {
                    buttArray[buttIndex] = new JButton();
                    buttArray[buttIndex].addActionListener(this);
                    centerPanel.add(buttArray[buttIndex]);
                    image = createImage(new FilteredImageSource(source.getSource(),
                    		new CropImageFilter(j*width/3, i*height/4, 
                            (width/3)+1, height/4)));
                    buttArray[buttIndex].setIcon(new ImageIcon(image));
                    buttIndex++;
                   
                }
            }
        }
		// ******************** JFrame ************
		// Frame options
		setSize(width + 400, height + 60);
		setTitle("Pic Puzlr!");
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void shuffle() {
		shuffleOn = true;
		for (int i = 0; i < 75; i++) {
			int index = ranPos.nextInt(10);
			buttArray[index].doClick();
		}
		shuffleOn = false;
	}
	
	//************************** JFrame ****************
    public static void main(String[] args) {
    	new Puzzle("Koala.jpg");

    }
    
    public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();

		if (button == shuffle) {
			shuffle();
		}

		if (button == picture1) {
			shuffleOn = true;
			new Puzzle("Desert.jpg");
			dispose(); // JFrame
		}

		if (button == picture2) {
			shuffleOn = true;
			new Puzzle("Lighthouse.jpg");
			dispose(); // JFrame
		}
		
		if (button == picture3) {
			shuffleOn = true;
			new Puzzle("Jellyfish.jpg");
			dispose(); // JFrame
		}
		
		if (button == picture4) {
			shuffleOn = true;
			new Puzzle("Penguins.jpg");
			dispose(); // JFrame
		}
		
		if (button == reset) {

			new Puzzle("Koala.jpg");
			dispose(); // JFrame
		}

        Dimension size = button.getSize();
        int labelIndex =0;
        int labelX = label.getX();
        int labelY = label.getY();
        int buttonX = button.getX();
        int buttonY = button.getY();
        int buttonPosX = buttonX / size.width;
        int buttonPosY = buttonY / size.height;
        int buttonIndex = pos[buttonPosY][buttonPosX];
        
        int temp = 0;
        
        //Move blank up
        if (labelX == buttonX && (labelY - buttonY) == size.height ) {

             labelIndex = buttonIndex + 3;

             centerPanel.remove(buttonIndex);
             centerPanel.add(label, buttonIndex);
             centerPanel.add(button,labelIndex);
             temp = victory[labelIndex];
             victory[labelIndex] = victory[buttonIndex];
             victory[buttonIndex] = temp;
             centerPanel.validate();
             
             
        }
        //Move blank down
        if (labelX == buttonX && (labelY - buttonY) == -size.height ) {

             labelIndex = buttonIndex - 3;
             centerPanel.remove(labelIndex);
             centerPanel.add(button,labelIndex);
             centerPanel.add(label, buttonIndex);
             temp = victory[labelIndex];
             victory[labelIndex] = victory[buttonIndex];
             victory[buttonIndex] = temp;
             centerPanel.validate();
            
            
        }
        //Move blank left
        if (labelY == buttonY && (labelX - buttonX) == size.width ) {

             labelIndex = buttonIndex + 1;
             centerPanel.remove(buttonIndex);
             centerPanel.add(label, buttonIndex);
             centerPanel.add(button,labelIndex);
             temp = victory[labelIndex];
             victory[labelIndex] = victory[buttonIndex];
             victory[buttonIndex] = temp;
             centerPanel.validate();
            
            
        }
        //Move blank right
        if (labelY == buttonY && (labelX - buttonX) == -size.width ) {

            labelIndex = buttonIndex - 1;
             centerPanel.remove(buttonIndex);
             centerPanel.add(label, labelIndex);
             centerPanel.add(button,labelIndex);
             temp = victory[labelIndex];
             victory[labelIndex] = victory[buttonIndex];
             victory[buttonIndex] = temp;
             centerPanel.validate();
        }
        
		boolean ftw = true;
		for (int i = 0; i < 12; i++) {
			if (victory[i] != i) {
				ftw = false;
			}
		}
		ImageIcon pic = new ImageIcon(Puzzle.class.getResource("youwin.jpg"));
		if (ftw && !shuffleOn) {

			JOptionPane.showMessageDialog(null, "", "You Won!",
					JOptionPane.PLAIN_MESSAGE, pic);
		}
	}
}
