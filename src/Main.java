import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Main extends JFrame
{
	public static final int SZEROKOSC = 1200;
	public static final int WYSOKOSC = 600;
	private JMenuBar menuBar;
	private JMenu menuTimer, menuCube, menuWyniki;
	private JMenuItem mTOtworz, mCOtworz, mWOtworz;
	private TimerFrame timer;
	private JFrame frame = this;
	private JPanel p1 = new JPanel();
	private JPanel p2 = new JPanel();
	
	public Main()
	{
		// Tworze okno programu
		setTitle("KofiTimer v1");
		setSize(SZEROKOSC, WYSOKOSC);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Ustawiam styl na Nimbus
		try
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException
				| UnsupportedLookAndFeelException e1)
		{
			e1.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(frame);
		
		//Tworze pasek zadan
		menuBar = new JMenuBar();
		menuTimer = new JMenu("Timer");
			mTOtworz = new JMenuItem("Otworz");
			mTOtworz.addActionListener(new ActionListener()
			{
				
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					//Metoda wywolujaca Timer z klasy Timer
					timer = new TimerFrame();
					p1.add(timer.getPGlowny());
					//System.out.println("Wszedl");
					p1.revalidate();
					p1.repaint();	
					timer.getTimerTimerFrame().getTTimer().requestFocus();
					
				}
			});
		
		menuCube = new JMenu("Cube");
			mCOtworz = new JMenuItem("Otworz");
			mCOtworz.addActionListener(new ActionListener()
			{
				
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					//Metoda wywolujaca Timer z klasy Timer
					Cube cube = new Cube();
					p1.remove(timer.getPGlowny());
					p1.add(cube.getPGlowny());
					//p1.add(test.getPGlowny());
					p1.revalidate();
					p1.repaint();	
				}
			});
			
		
		menuWyniki = new JMenu("Wyniki");
			mWOtworz = new JMenuItem("Otworz");
			mWOtworz.addActionListener(new ActionListener()
			{
				
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					//Metoda wywolujaca Timer z klasy Timer
				}
			});	
			
		//dodaje Itemy
		menuTimer.add(mTOtworz);
		menuCube.add(mCOtworz);
		menuWyniki.add(mWOtworz);
		
		//dodaje do paska zadan
		menuBar.add(menuTimer);	
		menuBar.add(menuCube);
		menuBar.add(menuWyniki);
		
		/*Tworzenie glownego timera*/
		
		//Tworze panele	
		
		//p1
		p1.setLayout(new BorderLayout());
		
		//p2
		p2.setLayout(new BorderLayout());
		p2.add(menuBar,BorderLayout.CENTER);
		
		//dodaje panel p2 do p1 i p1 do glownego okna
		p1.add(p2, BorderLayout.NORTH);
		this.add(p1,BorderLayout.CENTER);
		
		timer = new TimerFrame();
		p1.add(timer.getPGlowny());
		p1.revalidate();
		p1.repaint();	
		timer.getTimerTimerFrame().getTTimer().requestFocus();
	}
	public static void main(String[] args)
	{
			Main kofiTimer = new Main();
			kofiTimer.setVisible(true);
	}
}
