import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class TabelaCzasow
{
	/*Tworzenie tabeli, wierszy i kolumn*/
	private JTable tabelaCzasow;
	private List<Object[]> listTime;
	private List<String> columsName;
	/*Tworzenie scrollPane*/
	private JScrollPane scrollPane;
	Object arrayList[][];
	TableModel tableModel;
	final static int stat1 = 4;
	final static int stat2 = 11;
	private TimerFrame timerFrame;
	private DefaultTableCellRenderer rightRenderer;

	public TabelaCzasow(TimerFrame timerFrame)
	{	
		this.timerFrame = timerFrame;
		/*Torzenie listy tablic Wierszy*/
		listTime = new ArrayList<Object[]>();
		
		
		/*Tworzenie listy Kolumn*/
		columsName = new ArrayList<String>();
		columsName.add("Id");
		columsName.add("Time");
		columsName.add("Avg of 5");
		columsName.add("Avg of 12");
				
		/*Inicjalizacja tabeli*/
		tableModel = new DefaultTableModel(arrayList, columsName.toArray());

				
		tabelaCzasow = new JTable(tableModel) 
		{
	        public boolean isCellEditable(int row, int column) 
	        {                
	                return false;               
	        };        
	    };
	    tabelaCzasow.setToolTipText("Delete - usuniêcie jednego elementy D - DNF 2 - +2 A - czyszczenie ca³ej sesji");
	   
		scrollPane = new JScrollPane(tabelaCzasow);
		tabelaCzasow.setFont(new Font("SeanSeries", Font.PLAIN, 16));
		rebuildAll();
		
		tabelaCzasow.addKeyListener(new KeyListener()
		{
			
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
				int selectedIndex = tabelaCzasow.getSelectionModel().getMinSelectionIndex();
				String[] options = {"Tak", "Nie"};
				if(e.getKeyCode() == KeyEvent.VK_DELETE)
				{				
					if (selectedIndex >= 0)
					{							
						int n = JOptionPane.showOptionDialog(timerFrame,
						        "Czy napewno chcesz usun¹æ element nr " + (selectedIndex + 1) + "?",
						        "",  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
						if (n == JOptionPane.YES_OPTION)
						{
							listTime.remove(selectedIndex);
							timerFrame.zapisywanieListyDoPliku();
							timerFrame.inicjalizacjaTabeliCzsow();
						}
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_D)
				{
					if (selectedIndex >= 0)
					{
						int n = JOptionPane.showOptionDialog(timerFrame,
						        "Czy napewno chcesz zmieniæ element nr " + (selectedIndex + 1) + "na DNF ?",
						        "",  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
						if (n == JOptionPane.YES_OPTION)
						{
							((TableObject)listTime.get(selectedIndex)[1]).setValue(Double.POSITIVE_INFINITY);
							timerFrame.zapisywanieListyDoPliku();
							rebuildAll();
						}
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_2)
				{
					if (selectedIndex >= 0)
					{
						int n = JOptionPane.showOptionDialog(timerFrame,
						        "Czy napewno chcesz dodaæ +2 elementowi nr " + (selectedIndex + 1) + "?",
						        "",  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
						if (n == JOptionPane.YES_OPTION)
						{
							((TableObject)listTime.get(selectedIndex)[1]).setValue(((TableObject)listTime.get(selectedIndex)[1]).getValue() + 2000.0);
							timerFrame.zapisywanieListyDoPliku();
							rebuildAll();
						}
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_A)
				{
					if (selectedIndex >= 0)
					{
						int n = JOptionPane.showOptionDialog(timerFrame,
						        "Czy napewno chcesz wyczyœciæ ca³¹ sesjie?",
						        "",  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
						if (n == JOptionPane.YES_OPTION)
						{
							listTime.clear();
							timerFrame.zapisywanieListyDoPliku();
							rebuildAll();
						}
					}
				}
			}
		});
	}
	
	public JScrollPane getTable()
	{
		return scrollPane;
	}
	
	public List<Object[]> getListTime()
	{
		return listTime;
	}
	
	public void czyszczenieTablicy()
	{
		listTime.clear();
		arrayList = listTime.toArray(new Double[][] {});
		tableModel = new DefaultTableModel(arrayList, columsName.toArray());
		tabelaCzasow.removeAll();
		tabelaCzasow.setModel(tableModel);
	}
	
	public TableObject calculateAvg(List<Double> tempArrayList)
	{
		Integer cnt = 0;
		Double sum = 0.0;
		int i;
		Double[] tempTable = tempArrayList.toArray(new Double[1]);					
		Arrays.sort(tempTable);
		
		for (i = 1;  i < (tempTable.length-1) ; i++ )
			sum += tempTable[i];
		cnt = i - 1;
		
		return new TableObject((Math.floor(100*(sum/cnt)))/100);
	}
	
	public void dodawanieCzasowDoTablicy(double liczba)
	{
		TableObject tableObject = new TableObject(liczba);
		Object[] tab = new Object[4];
		tab[1] = tableObject;
		
		listTime.add(tab);
		rebuildAll();
	}
	
	public void rebuildAll()
	{
		arrayList = listTime.toArray(new Object[][] {});			
		List<Double> stat1ArrayList = new ArrayList<Double>();
		List<Double> stat2ArrayList = new ArrayList<Double>();
		for (int i = 0 ; i < arrayList.length ; i++ )
		{
			arrayList[i][0] = i + 1;
			stat1ArrayList.add(((TableObject)arrayList[i][1]).getValue());
			stat2ArrayList.add(((TableObject)arrayList[i][1]).getValue());
			
			if (i >= stat1)
			{
				arrayList[i][2] = calculateAvg(stat1ArrayList);
				stat1ArrayList.remove(0);
			}
			if (i >= stat2)
			{
				arrayList[i][3] = calculateAvg(stat2ArrayList);
				stat2ArrayList.remove(0);
			}
		}
		
		tableModel = new DefaultTableModel(arrayList, columsName.toArray());
		tabelaCzasow.removeAll();
		tabelaCzasow.setModel(tableModel);
		
		tabelaCzasow.scrollRectToVisible(tabelaCzasow.getCellRect(tabelaCzasow.getRowCount()-1, 0, true));
		tabelaCzasow.getColumnModel().getColumn(0).setPreferredWidth(30);
		
		rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tabelaCzasow.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		tabelaCzasow.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		tabelaCzasow.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tabelaCzasow.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		
	}
}

class TableObject
{
	Double value;
	
	public TableObject()
	{
		value = -1.0;
	}
	
	public TableObject(Double value)
	{
		this.value = value;
	}
	
	public Double getValue()
	{
		return value;
	}
	public void setValue(Double value)
	{
		this.value = value;
	}
	
	@Override
	public String toString()
	{
		if (value.isInfinite()) return new String("DNF");
		
		else if (value == -1) return new String("-");
		else 
		{
			if ((int)((value / 1000.0)/60.0) >= 1) 
				return String.format("%02d:%02d.%02d",(int)((value / 1000.0)/60.0), (int)((value % 60000.0) / 1000.0), (int)((value % 1000.0)/10.0));
			else return String.format("%02d.%02d",(int)((value % 60000.0) / 1000.0), (int)((value % 1000.0)/10.0));
		}
	}
	
}

