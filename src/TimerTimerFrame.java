import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.text.MaskFormatter;




public class TimerTimerFrame
{
	private JTextField tTimer;
	private JFormattedTextField tTimer1;
	private JPanel  pTimer;
	private Timer timer, timerPreinspekcja;
	private boolean timerStarten, preinspectionStarten, czy2, czyDNF, flagaSposobuWyswietlania;
	private long startTime, elapsed;
	private TimerFrame timerFrame;
	
	
	/*Listenery do timerów*/
	private ActionListener standartowySposobWyswietlaniaTimera = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String display = null;
			elapsed = ((System.currentTimeMillis() - startTime));
			long liczba = (elapsed % 60000) / 1000;
			if(liczba == 59 || !(flagaSposobuWyswietlania))
			{
				display = String.format("%02d:%02d.%02d",(elapsed / 1000)/60, (elapsed % 60000) / 1000, (elapsed % 1000)/10);
				flagaSposobuWyswietlania = false;					
			}
			else if(flagaSposobuWyswietlania)
			{
				display = String.format("%02d.%02d", (elapsed % 60000) / 1000, (elapsed % 1000)/10);
			}
			tTimer.setText(display);		
			timer.setInitialDelay(10);
		}
	};
	
	
	private ActionListener preinspekcjaTimer = new ActionListener()
	{		
		private String czas;
		private int czasInt;
		private int currentTimeMillis;
		@Override
		public void actionPerformed(ActionEvent e)
		{
			long elapsed;
			czasInt = (int) ((startTime % 60000) / 1000);
			currentTimeMillis = (int) ((System.currentTimeMillis() % 60000) / 1000);
			elapsed = (czasInt + 15) - currentTimeMillis;
			if(elapsed > 15)
			{
				elapsed = elapsed - 60;
			}
			if(elapsed != 0 && elapsed != -1 && elapsed != -2)
			{				
				czas = String.valueOf(elapsed);
				tTimer.setText(czas);
			}
			else if(elapsed == 0 || elapsed == -1)
			{
				tTimer.setText("+2");
				czy2 = true;
			}
			else if(elapsed == -2)
			{
				tTimer.setText("DNF");
				czy2 = false;
				czyDNF = true;
				timerPreinspekcja.stop();
			}
		}
	};
	
	private KeyListener lisinerTimera = new KeyListener()
	{	
		@Override
		public void keyTyped(KeyEvent e)
		{
			
		}
		
		@Override
		public void keyReleased(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				if (timerStarten == false)
				{
					startTime = System.currentTimeMillis();
					timer.start();
					timerStarten = true;
				}

				else if (timerStarten == true)
				{
					timer.stop();
					timerStarten = false;
					flagaSposobuWyswietlania = true;
					timerFrame.dodawanieCzasowZTimera(String.valueOf(elapsed));
				}

			}
			
		}
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			
		}
	};		
	
	private KeyListener listenerPreinspection = new KeyListener()
	{		
		@Override
		public void keyTyped(KeyEvent e)
		{
			
		}
		
		@Override
		public void keyReleased(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				if (preinspectionStarten == false)
				{
					startTime = System.currentTimeMillis();
					tTimer.setText("15");
					timerPreinspekcja.start();
					preinspectionStarten = true;
				}

				else if (preinspectionStarten == true && timerStarten == false)
				{
					timerPreinspekcja.stop();
					startTime = System.currentTimeMillis();
					timer.start();
					timerStarten = true;
				}
				else if (timerStarten == true)
				{
					timer.stop();
					timerStarten = false;
					preinspectionStarten = false;
					if(czy2 == true)
					{
						timerFrame.dodawanieCzasowZTimera(String.valueOf(elapsed + 2000));
						czy2 = false;
					}
					else if(czyDNF == true)
					{
						timerFrame.dodawanieCzasowZTimera(String.valueOf(Double.POSITIVE_INFINITY));
						czyDNF = false;
					}
					else
					{
						timerFrame.dodawanieCzasowZTimera(String.valueOf(elapsed));
					}
				}

			}
			
		}
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			// TODO Auto-generated method stub
			
		}
	};
	
	private KeyListener listenerTyping1 = new KeyListener()
	{
		@Override
		public void keyTyped(KeyEvent e)
		{
		}
		
		@Override
		public void keyReleased(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				Double czas = Double.parseDouble(tTimer1.getText());
				czas = czas * 1000;
				timerFrame.dodawanieCzasowZTimera(String.valueOf(czas));
				tTimer1.setText("00.00");
				tTimer1.setCaretPosition(0);
			}
		}			
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			
		}
	};
	
	private KeyListener listenerTyping2 = new KeyListener()
	{
		@Override
		public void keyTyped(KeyEvent e)
		{
		}
		
		@Override
		public void keyReleased(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				int liczbaMin = 0;
				int liczbaS = 0;
				int liczbaSS = 0;
				int koncowyCzas;
				Integer liczby[] = new Integer[6];
				String czas = tTimer1.getText();
				
				liczby[0] = Integer.parseInt(String.valueOf(czas.charAt(0)));
				liczby[1] = Integer.parseInt(String.valueOf(czas.charAt(1)));
				liczby[2] = Integer.parseInt(String.valueOf(czas.charAt(3)));
				liczby[3] = Integer.parseInt(String.valueOf(czas.charAt(4)));
				liczby[4] = Integer.parseInt(String.valueOf(czas.charAt(6)));
				liczby[5] = Integer.parseInt(String.valueOf(czas.charAt(7)));
				
				liczbaMin = ((liczby[0] * 10) + liczby[1]) * 60000;
				liczbaS = ((liczby[2] * 10) + liczby[3]) * 1000;
				liczbaSS = ((liczby[4] * 10) + liczby[5]) * 10;
				koncowyCzas = liczbaMin + liczbaS + liczbaSS;
				timerFrame.dodawanieCzasowZTimera(String.valueOf(koncowyCzas));
				tTimer1.setText("00:00.00");
				tTimer1.setCaretPosition(0);
			}
		}			
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			
		}
	};
	
	public TimerTimerFrame(TimerFrame timerFrame)
	{
		/*Inicjalizacja wskaznika*/
		this.timerFrame = timerFrame;
		flagaSposobuWyswietlania = true;
		
		/*Inicjalizacja panelu*/
		pTimer = new JPanel();
		
		/*Ustawienie layautów*/
		pTimer.setLayout(new BorderLayout());
		
		/*Inicjalizacja komponentow*/
		
		/*tTimer*/
		tTimer = new JTextField();
		tTimer.setHorizontalAlignment(JTextField.CENTER);
		tTimer.setFont(new Font("SeanSeries", Font.BOLD, 100));
		tTimer.setEditable(false);
		tTimer.setText(String.format("%02d.%02d",0,0));
		tTimer.addKeyListener(lisinerTimera);
		
		/*tTimer1*/
		try
		{
			tTimer1 = new JFormattedTextField(new MaskFormatter("##.##"));
		} 
		catch (ParseException e1)
		{
			e1.printStackTrace();
		}
		tTimer1.setHorizontalAlignment(JTextField.CENTER);
		tTimer1.setFont(new Font("SeanSerif", Font.BOLD, 50));
		tTimer1.setText("00.00");
		tTimer1.setCaretPosition(0);
		tTimer1.addKeyListener(listenerTyping1);
		
		/*timer*/
		timer = new Timer(60, standartowySposobWyswietlaniaTimera);
		
		/*timerPreinspekcji*/
		timerPreinspekcja = new Timer(1000, preinspekcjaTimer);
		
		
		/*Dodawanie do panelów*/
		
		/*Dodawanie do pTimer*/
		pTimer.add(tTimer);
	}
	
	/*Metody get*/
	public JPanel getPTimer()
	{
		return pTimer;
	}
	public JTextField getTTimer()
	{
		return tTimer;
	}
	
	public JFormattedTextField getTTyping()
	{
		return tTimer1;
	}
	public void setMaskTimer1(String maska)
	{
		try
		{
			//tTimer1.removeAll();
			if(maska.equals("00.00"))
			{
				tTimer1 = new JFormattedTextField(new MaskFormatter("##.##"));
				tTimer1.setText("00.00");
				tTimer1.addKeyListener(listenerTyping1);
			}
			if(maska.equals("00:00.00"))
			{
				tTimer1 = new JFormattedTextField(new MaskFormatter("##:##.##"));
				tTimer1.setText("00:00.00");
				tTimer1.addKeyListener(listenerTyping2);
			}
			tTimer1.setHorizontalAlignment(JTextField.CENTER);
			tTimer1.setFont(new Font("SeanSerif", Font.BOLD, 50));
			tTimer1.setCaretPosition(0);
			ustawenieTimeraNaTyping();
		} 
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/*Metody ustawienia timera*/
	public void ustawienieWielkosciTimera(int wielkosc)
	{
		tTimer.setFont(new Font("SeanSeries", Font.BOLD, wielkosc));
		tTimer1.setFont(new Font("SeanSeries", Font.BOLD, wielkosc));
	}
	
	/*Metody zmiany timera*/
	public void ustawenieTimeraNaTyping()
	{
		pTimer.removeAll();
		pTimer.add(tTimer1);
		pTimer.revalidate();
		pTimer.repaint();	
	}
	
	public void ustawenieTimeraNaTimer()
	{
		pTimer.removeAll();
		pTimer.add(tTimer);
		pTimer.revalidate();
		pTimer.repaint();	
		tTimer.removeKeyListener(lisinerTimera);
		tTimer.removeKeyListener(listenerPreinspection);
		tTimer.addKeyListener(lisinerTimera);
	}

	public void prinspection()
	{
		pTimer.removeAll();
		pTimer.add(tTimer);
		pTimer.revalidate();
		pTimer.repaint();	
		tTimer.removeKeyListener(lisinerTimera);
		tTimer.removeKeyListener(listenerPreinspection);
		tTimer.addKeyListener(listenerPreinspection);
	}
}
