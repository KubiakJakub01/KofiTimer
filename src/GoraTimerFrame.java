import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class GoraTimerFrame
{
	private JComboBox comboTypKostki, comboNrSesji;
	private JLabel lScrambleType, lScramble, lScrambleLengh, lSesion;
	private JTextField tScramblLengh;
	private JButton bOpcje, bNastepnyScramble, bPoprzedniScramble;
	private JPanel pGora, pPaskaGornego, pPaskaGornego1, pScrambl, pGoraScrambla;
	private TimerFrame timerFrame;
	
	private KeyListener listenerScrambleLenght = new KeyListener()
	{
		private String cyfry;
		private boolean czyZnakiSieZgadzaj = false;
		private int liczba;
		public boolean czyLiczba(char znak)
		{
			if(znak == '0'|| znak == '1'|| znak == '2'||  znak == '3'||
			znak == '4'|| znak == '5'|| znak == '6'||
			znak == '7'||znak == '8'||znak == '9')
			{
				return true;
			}
			else return false;
			
		}
		@Override
		public void keyTyped(KeyEvent e)
		{

		}
		
		@Override
		public void keyReleased(KeyEvent e)
		{

		}
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				cyfry = tScramblLengh.getText();
				for(int i = 0; i < cyfry.length(); i++)
				{
					if(czyLiczba(cyfry.charAt(i)))
					{
						czyZnakiSieZgadzaj = true;
					}
					else czyZnakiSieZgadzaj = false;
				}
				if(czyZnakiSieZgadzaj)
				{
					liczba = Integer.parseInt(tScramblLengh.getText());
					timerFrame.setDlugoscScrambal(liczba);
					setScramble();
				}
				else tScramblLengh.setText("");
			}
		}
	};
	
	private ActionListener listenerComboNrSesion = new ActionListener()
	{
		private String nrSesion;
		@Override
		public void actionPerformed(ActionEvent e)
		{
			timerFrame.inicjalizacjaTabeliCzsow();
		}
	};
	
	private ActionListener listenerBOpcji = new ActionListener()
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			timerFrame.incjalizajcaPaneluOpcji();		
		}
	};
	
	private ActionListener listenrBackScramble = new ActionListener()
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setScramble(timerFrame.getBackScramble());
		}
	};
	private ActionListener listenerNextScramble = new ActionListener()
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setScramble();
		}
	};
	 
	private ActionListener listenrComboTypKostki = new ActionListener()
	{
		private String rodzajKostki;
		@Override
		public void actionPerformed(ActionEvent e)
		{
			rodzajKostki = comboTypKostki.getSelectedItem().toString();
			timerFrame.inicjalizacjaRodzajuScrambla(rodzajKostki);
			setScramble();
			comboNrSesji.setSelectedItem("1");
			timerFrame.inicjalizacjaTabeliCzsow();
			tScramblLengh.setText(String.valueOf(timerFrame.getDlugoscScrambal()));
		}
	};
	
	public GoraTimerFrame(TimerFrame timerFrame)
	{
		/*Inicjalizacja wskaznikow do innych klas*/
		this.timerFrame = timerFrame;
		
		/*Inicjalizacja paneli*/
		pGoraScrambla = new JPanel();
			pGora = new JPanel();
				pPaskaGornego = new JPanel();
				pPaskaGornego1 = new JPanel();
			pScrambl = new JPanel();
			
		/*Ustawianie layautów*/
		pGoraScrambla.setLayout(new GridLayout(2,0));
		pGora.setLayout(new BorderLayout());
			pPaskaGornego.setLayout(new FlowLayout());
			pPaskaGornego1.setLayout(new FlowLayout());
		pScrambl.setLayout(new BorderLayout());
		
		/*Inicjalizacja komponentów*/
		
		/*dla pPaskaGornego*/
		lScrambleType = new JLabel("Scramble type: ");
		
		comboTypKostki = new JComboBox();
		comboTypKostki.addItem("3x3x3");
		comboTypKostki.addItem("2x2x2");
		comboTypKostki.addItem("4x4x4");
		comboTypKostki.addItem("5x5x5");
		comboTypKostki.addItem("3x3x3OH");
		comboTypKostki.addItem("Pyraminx");
		comboTypKostki.addItem("Skweb");
		comboTypKostki.addActionListener(listenrComboTypKostki);
		
		lScrambleLengh = new JLabel("Scramble length");
		

		tScramblLengh = new JTextField(3);
		tScramblLengh.addKeyListener(listenerScrambleLenght);
		tScramblLengh.setText(String.valueOf(timerFrame.getDlugoscScrambal()));
	
		/*dla pPaskaGornego1*/
		lSesion = new JLabel("Sesion: ");
		
		comboNrSesji = new JComboBox();
		comboNrSesji.addItem("1");
		comboNrSesji.addItem("2");
		comboNrSesji.addItem("3");
		comboNrSesji.addItem("4");
		comboNrSesji.addItem("5");
		comboNrSesji.addActionListener(listenerComboNrSesion);
			
		bOpcje = new JButton("Opcje");
		bOpcje.addActionListener(listenerBOpcji);
		
		bPoprzedniScramble = new JButton("Poprzedni Scrambl");
		bPoprzedniScramble.addActionListener(listenrBackScramble);
		
		bNastepnyScramble = new JButton("Nastepny Scrambl");
		bNastepnyScramble.addActionListener(listenerNextScramble);
		
		/*dla pScrambl*/
		lScramble = new JLabel("Scramble: " + timerFrame.generowanieScrambla());
		lScramble.setFont(new Font("SeanSeries", Font.BOLD, 20));
		
		/*Dodawanie do pPaskaGornego*/
		pPaskaGornego.add(lScrambleType);
		pPaskaGornego.add(comboTypKostki);
		pPaskaGornego.add(lScrambleLengh);
		pPaskaGornego.add(tScramblLengh);
		
		/*Dodawanie do pPaskaGornego1*/
		pPaskaGornego1.add(bPoprzedniScramble);
		pPaskaGornego1.add(bNastepnyScramble);
		pPaskaGornego1.add(bOpcje);
		pPaskaGornego1.add(lSesion);
		pPaskaGornego1.add(comboNrSesji);
		
		/*Dodawanie do pGora*/
		pGora.add(pPaskaGornego, BorderLayout.WEST);
		pGora.add(pPaskaGornego1, BorderLayout.EAST);
		
		/*Dodawanie do pScrambl*/
		pScrambl.add(lScramble, BorderLayout.WEST);
		
		/*Dodawanie do pGoraScrambla*/
		pGoraScrambla.add(pGora);
		pGoraScrambla.add(pScrambl);
	}
	/*Medody klasy*/
	
	/*metody get*/
	public JComboBox getComboNrSesji()
	{
		return comboNrSesji;
	}
	public JPanel getGoraScrambla()
	{
		return pGoraScrambla;
	}
	public JComboBox getComboTypKostki()
	{
		return comboTypKostki;
	}
	
	/*metody set*/
	public void setScramble()
	{
		lScramble.removeAll();
		lScramble = new JLabel("Scramble: " + timerFrame.generowanieScrambla());
		lScramble.setFont(new Font("SeanSeries", Font.BOLD, timerFrame.getTimerOpcje().getWielkoscScrambla()));
		pScrambl.removeAll();
		pScrambl.add(lScramble, BorderLayout.WEST);
		pScrambl.revalidate();
		pScrambl.repaint();
	}
	
	public void setScramble(String scramble)
	{
		lScramble.removeAll();
		lScramble = new JLabel("Scramble: " + scramble);
		lScramble.setFont(new Font("SeanSeries", Font.BOLD, timerFrame.getTimerOpcje().getWielkoscScrambla()));
		pScrambl.removeAll();
		pScrambl.add(lScramble, BorderLayout.WEST);
		pScrambl.revalidate();
		pScrambl.repaint();
	}
	
	public void ustawianieWielkosciScrambla(int wielkosc)
	{
		lScramble.setFont(new Font("SeanSeries", Font.BOLD, wielkosc));
	}
}

