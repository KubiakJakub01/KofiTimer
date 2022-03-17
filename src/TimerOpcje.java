import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;




public class TimerOpcje extends JFrame
{
	private JPanel glowny, p1, pNadp1, pCzyTimer, pSuwak, pWielkoscScrambla, pTypPokazywaniaCzasu, pZmienKolor;
	private JComboBox comboTimerCzyWpis, comboTypPokazywaniaCzasu;
	private JButton bZwiekszScramble, bZmiejszScramble, bZmienKolorTla;
	private JLabel lCzyPreinspekcja, lCzyTimer, lSuwak, lWielkoscScrambla, lTypPokazywaniaCzasu;
	private JRadioButton rYes, rNo;
	private ButtonGroup rbGroup;
	private JSlider sSuwakWielkosciTimera;
	private TimerFrame timerFrame;
	private JFrame frame;
	private Color wybranyColor;
	private int wielkoscScrambla;
	
	public TimerOpcje(TimerFrame timerFrame)
	{
		setTitle("KofiTimer");
		setSize(500, 270);
		setLayout(new BorderLayout());

		/*Inicjalizacja wskaznikow do innych klas*/
		this.timerFrame = timerFrame;
		wielkoscScrambla = 20;
		
		/*Inicjalizacja innych zmiennych*/
		frame = this;
		
		/*Tworzenie JPanel*/
		glowny = new JPanel();
		glowny.setLayout(new BoxLayout(glowny, BoxLayout.PAGE_AXIS));
		
		p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		
		pNadp1 = new JPanel();
		pNadp1.setLayout(new BorderLayout());
		
		pCzyTimer = new JPanel();
		pCzyTimer.setLayout(new FlowLayout());
		
		pSuwak = new JPanel();
		pSuwak.setLayout(new FlowLayout());
		
		pWielkoscScrambla = new JPanel();
		pWielkoscScrambla.setLayout(new FlowLayout());
		
		pTypPokazywaniaCzasu = new JPanel();
		pTypPokazywaniaCzasu.setLayout(new FlowLayout());
		
		pZmienKolor = new JPanel();
		pZmienKolor.setLayout(new FlowLayout());
		
		/*Tworzenie komponentow do glowny*/
		lCzyTimer = new JLabel("Jaki ty podawani czasów wybierasz: ");
		
		comboTimerCzyWpis = new JComboBox();
		comboTimerCzyWpis.addItem("Timer");
		comboTimerCzyWpis.addItem("Typing");
		comboTimerCzyWpis.addActionListener(new ActionListener()
		{			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String coWybral = comboTimerCzyWpis.getSelectedItem().toString();
				if(coWybral.equals("Timer"))
				{
					timerFrame.getTimerTimerFrame().ustawenieTimeraNaTimer();
				}
				if(coWybral.equals("Typing"))
				{
					timerFrame.getTimerTimerFrame().ustawenieTimeraNaTyping();
				}
			}
		});
			
		rNo = new JRadioButton("No", true);
		rNo.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				timerFrame.getTimerTimerFrame().ustawenieTimeraNaTimer();
			}
		});
		
		lCzyPreinspekcja = new JLabel("Czy preinspekcja" + " ");
		
		rYes = new JRadioButton("Yes");
		rYes.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				timerFrame.getTimerTimerFrame().prinspection();
			}
		});
		
		rbGroup = new ButtonGroup();
		rbGroup.add(rNo);
		rbGroup.add(rYes);
		
		lSuwak = new JLabel("Jak¹ wielkoœæ timera chcesz: ");
		
		sSuwakWielkosciTimera = new JSlider(75,350,100);
		sSuwakWielkosciTimera.setMajorTickSpacing(50);
		sSuwakWielkosciTimera.setMinorTickSpacing(20);
		sSuwakWielkosciTimera.setPaintTicks(true);
		sSuwakWielkosciTimera.setPaintLabels(true);
		sSuwakWielkosciTimera.addChangeListener(new ChangeListener()
		{		
			@Override
			public void stateChanged(ChangeEvent e)
			{
				int wielkosc = sSuwakWielkosciTimera.getValue();
				timerFrame.getTimerTimerFrame().ustawienieWielkosciTimera(wielkosc);

			}
		});

		
		lWielkoscScrambla = new JLabel("Wielkosc scrambla: ");
		
		bZmiejszScramble = new JButton("Zmniejsz wielkosc scramla");
		bZmiejszScramble.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				wielkoscScrambla = wielkoscScrambla - 5;
				timerFrame.getGoraTimerFrame().ustawianieWielkosciScrambla(wielkoscScrambla);
			}
		});
		
		bZwiekszScramble = new JButton("Zwieksz wielkosc scramla");
		bZwiekszScramble.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				wielkoscScrambla = wielkoscScrambla + 5;
				timerFrame.getGoraTimerFrame().ustawianieWielkosciScrambla(wielkoscScrambla);
			}
		});
		
		lTypPokazywaniaCzasu = new JLabel("Jaki typ wpisywania czasu chcesz stosowaæ: ");
		
		comboTypPokazywaniaCzasu = new JComboBox();
		comboTypPokazywaniaCzasu.addItem("00.00");
		comboTypPokazywaniaCzasu.addItem("00:00.00");
		comboTypPokazywaniaCzasu.addActionListener(new ActionListener()
		{
			String coWybral = comboTypPokazywaniaCzasu.getSelectedItem().toString();
			@Override
			public void actionPerformed(ActionEvent e)
			{
				timerFrame.getTimerTimerFrame().setMaskTimer1(comboTypPokazywaniaCzasu.getSelectedItem().toString());
				
			}
		});
		
		bZmienKolorTla = new JButton("Zmieñ kolor t³a timera");
		bZmienKolorTla.addActionListener(new ActionListener()
		{
			private Color wybranyColor;
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				wybranyColor = JColorChooser.showDialog(frame, "Wybor koloru", Color.GREEN); // Do kolorów
				timerFrame.getTimerTimerFrame().getTTimer().setBackground(wybranyColor);
				timerFrame.getTimerTimerFrame().getTTyping().setBackground(wybranyColor);
			}
		});
		
		/*Dodawanie do JPanel*/
		pCzyTimer.add(lCzyTimer);
		pCzyTimer.add(comboTimerCzyWpis);
		
		p1.add(lCzyPreinspekcja);
		p1.add(rNo);
		p1.add(rYes);
		
		pNadp1.add(p1);
		
		pSuwak.add(lSuwak);
		pSuwak.add(sSuwakWielkosciTimera);
		
		pWielkoscScrambla.add(lWielkoscScrambla);
		pWielkoscScrambla.add(bZwiekszScramble);
		pWielkoscScrambla.add(bZmiejszScramble);
		
		pTypPokazywaniaCzasu.add(lTypPokazywaniaCzasu);
		pTypPokazywaniaCzasu.add(comboTypPokazywaniaCzasu);
		
		pZmienKolor.add(bZmienKolorTla);
		
		glowny.add(pCzyTimer);
		glowny.add(pNadp1);
		glowny.add(pSuwak);
		glowny.add(pWielkoscScrambla);
		glowny.add(pTypPokazywaniaCzasu);
		glowny.add(pZmienKolor);
		
		/*Dodawanie do okna*/
		add(glowny);
	}
	private void closelButtonActionPerformed(java.awt.event.ActionEvent evt) 
	{	
		java.awt.Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new java.awt.event.WindowEvent(this, java.awt.event.WindowEvent.WINDOW_CLOSING));
	}
	
	public Color getColor()
	{
		return wybranyColor;
	}
	public int getWielkoscScrambla()
	{
		return wielkoscScrambla;
	}
	public JSlider getSuwak()
	{
		return sSuwakWielkosciTimera;
	}
}
