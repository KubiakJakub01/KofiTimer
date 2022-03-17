import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;


public class TimerFrame extends JPanel 
{
	private JPanel pGlowny, pTimeraCzasuWynikow; 
	private List<String> czasy = new ArrayList<String>();
	private TimerOpcje timerOpcje;
	private TabelaCzasow tabelaCzasow;
	private GoraTimerFrame goraTimerFrame;
	private TimerTimerFrame timerTimerFrame;
	private Scanner odczyt;
	private PrintWriter zapis;
	private String ruchyDoScrambla[], poprzedniScramb, scrambl;
	private Random generator;
	private int dlugoscScrambal, zakresLosowania;
		
	public TimerFrame()
	{
		/*Przypisywanie wartosci typom wbudowanym*/
		ruchyDoScrambla = new String[100];
		
		/*Poczatkowe inicjalizacje*/
		inicjalizacjaRodzajuScrambla("3x3x3");
		generator = new Random();
		scrambl = new String();
		
		/*Inicjalizacja typów wbudowanych*/
		tabelaCzasow = new TabelaCzasow(this);
		timerOpcje = new TimerOpcje(this);
		goraTimerFrame = new GoraTimerFrame(this);
		timerTimerFrame = new TimerTimerFrame(this);
		
		/*Inicjalizacja panele*/
		pGlowny = new JPanel();
		pTimeraCzasuWynikow = new JPanel();
			
		/*Ustawienie layauty dla paneli*/
		pGlowny.setLayout(new BorderLayout());
		pTimeraCzasuWynikow.setLayout(new GridLayout(0,2));
				
		/*odtworzenie stanu po wlaczeniu*/
		inicjalizacjaTabeliCzsow();
		/*Dodawanie komponentów do paneli i tworzynie interfejsu*/
										       
		/*Dodawanie do pTimerTimeWyniki*/		
		pTimeraCzasuWynikow.setLayout(new BorderLayout());
		pTimeraCzasuWynikow.add(tabelaCzasow.getTable(), BorderLayout.WEST);
		pTimeraCzasuWynikow.add(timerTimerFrame.getPTimer());
		tabelaCzasow.getTable().setPreferredSize(new Dimension(300, 250));
		
		/*Dodawanie do pGlowny*/
		pGlowny.add(goraTimerFrame.getGoraScrambla(), BorderLayout.NORTH);
		pGlowny.add(pTimeraCzasuWynikow, BorderLayout.CENTER);	
				
		/*Dodawanie do JFrame*/
		add(pGlowny, BorderLayout.CENTER);
	}
	/*Metody get wskaznikow do innych klas*/
	public TimerTimerFrame getTimerTimerFrame()
	{
		return timerTimerFrame;
	}
	
	public GoraTimerFrame getGoraTimerFrame()
	{
		return goraTimerFrame;
	}
	
	public TimerOpcje getTimerOpcje()
	{
		return timerOpcje;
	}
	
	public TabelaCzasow getTabelaCzasow()
	{
		return tabelaCzasow;
	}
	
	private void zapisDoPliku(String sciezka, String doZapisu) 
	{	
		try
		{
			zapis = new PrintWriter(new BufferedWriter(new FileWriter(sciezka,true)));
			zapis.println(doZapisu);
			zapis.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	
	public void dodawanieCzasowZTimera(String czas) 
	{
		czasy.add(czas);
		String Sciezka = (String) goraTimerFrame.getComboNrSesji().getSelectedItem();		
		Sciezka = "Pliki/" + goraTimerFrame.getComboTypKostki().getSelectedItem().toString() + "/" + "Sesion" + Sciezka + ".txt";
		zapisDoPliku(Sciezka, czas);
		double czasZTimera = Double.valueOf(czas).doubleValue();
		tabelaCzasow.dodawanieCzasowDoTablicy(czasZTimera);
		goraTimerFrame.setScramble();
		pGlowny.revalidate();
		pGlowny.repaint();
	}
	
	public void incjalizajcaPaneluOpcji()
	{
		timerOpcje.setVisible(true);
	}
	
	public void inicjalizacjaTabeliCzsow()
	{
		String sciezka;
		sciezka = "Pliki/"  + goraTimerFrame.getComboTypKostki().getSelectedItem().toString() + "/" + "Sesion" + goraTimerFrame.getComboNrSesji().getSelectedItem() + ".txt";
		tabelaCzasow.czyszczenieTablicy();
		try
		{
			odczyt = new Scanner(new File(sciezka));
			while (odczyt.hasNextLine())
			{
				tabelaCzasow.dodawanieCzasowDoTablicy(Double.parseDouble(odczyt.nextLine())); 
			}
		} 
		catch (FileNotFoundException e)
		{
			System.out.println("Nie dziala");
			e.printStackTrace();
		}
	}
	
	public void inicjalizacjaRodzajuScrambla(String sciezka)
	{
		sciezka = "Pliki/"  + "Scramble" + sciezka + ".txt";		
		try
		{
			Scanner dokument = new Scanner(new File(sciezka));
			int i = 0;
			dlugoscScrambal = Integer.parseInt(dokument.nextLine());
			zakresLosowania = Integer.parseInt(dokument.nextLine());
			while (dokument.hasNextLine())
			{
				ruchyDoScrambla[i] = dokument.nextLine();
				i++;
			}
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
	}
	
	public String generowanieScrambla()
	{
		String poprzednioWylosowane = " ";
		String terazWylosowane;
		int wylosowanaLiczba;
		char znak;
		poprzedniScramb = scrambl;
		scrambl = "";
		for(int i = 0; i < dlugoscScrambal;	i++)
		{
			wylosowanaLiczba = generator.nextInt(zakresLosowania);
			terazWylosowane = ruchyDoScrambla[wylosowanaLiczba];
			znak = terazWylosowane.charAt(0);
			
			if(!(znak == poprzednioWylosowane.charAt(0)))
			{
				scrambl += terazWylosowane + " ";
				poprzednioWylosowane = terazWylosowane;
			}
		}
		return scrambl;
	}
	
	public void zapisywanieListyDoPliku()
	{
		String sciezka = "Pliki/"  + goraTimerFrame.getComboTypKostki().getSelectedItem().toString() + "/" + "Sesion" + goraTimerFrame.getComboNrSesji().getSelectedItem().toString() + ".txt";
		try
		{
			zapis = new PrintWriter(new BufferedWriter(new FileWriter(sciezka)));
			for(int i = 0; i < tabelaCzasow.getListTime().size(); i++)
			{
				zapis.println(((TableObject)tabelaCzasow.getListTime().get(i)[1]).getValue());
			}
			zapis.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/*Metody typu get*/
	public JPanel getPGlowny()
	{
		return pGlowny;
	}
	
	public int getDlugoscScrambal()
	{
		return dlugoscScrambal;
	}
	
	public String getBackScramble()
	{
		return poprzedniScramb;
	}
	
	/*Metody typu set*/
	public void setDlugoscScrambal(int liczba)
	{
		dlugoscScrambal = liczba;
	}

}
