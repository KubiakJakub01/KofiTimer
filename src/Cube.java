import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Cube extends JPanel
{
	//Tworznienie komponentów//
	private JButton dodaj;
	private BufferedImage image;
	private JPanel pGlowny;
	private JLabel picLabel, napis; 

    public Cube() 
    {
       try {
          image = ImageIO.read(new File("gans356.jpg"));
       } catch (IOException ex) {
            // handle exception...
       }
       picLabel = new JLabel(new ImageIcon(image));
       napis = new JLabel();
       napis.setText("Napis");
       System.out.println("wszedl");
       
       //Inicjalizacja panelu//
       pGlowny = new JPanel();
       
       pGlowny.add(picLabel, BorderLayout.EAST);
       pGlowny.add(napis, BorderLayout.SOUTH);
    }
    
     JPanel getPGlowny()
    {
    	return pGlowny;
    }
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }
}
