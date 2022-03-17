import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;


public class Test extends JFrame
{
	public JPanel p1;
	Test()
	{
		
		setTitle("Timer");
		setSize(400,200);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		
		/* p1 */
		p1.setLayout(new GridLayout(1,2));
		
		/* p2 */
		p2.setLayout(new BorderLayout());
		JTextPane textPane = new JTextPane();
		p2.add(textPane,BorderLayout.CENTER);
		
		/* p3 */
		p3.setLayout(new GridLayout(4,1));
		
		JPanel p31 = new JPanel();
		JButton b1 = new JButton("button 1");
		
		b1.addActionListener(new ActionListener()
		{		
			@Override
			public void actionPerformed(ActionEvent e)
			{
				p1.remove(p2);
				p1.remove(p3);
				p1.add(p3);
				p1.add(p2);
				p1.revalidate();
				p1.repaint();			
			}
		});
		
		p31.setLayout(new FlowLayout());
		p31.add(b1);
		
		
		JPanel p32 = new JPanel();
		JButton b2 = new JButton("button 2");
		p32.setLayout(new FlowLayout());
		p32.add(b2);
		
		JPanel p33 = new JPanel();
		JButton b3 = new JButton("button 3");
		p33.setLayout(new FlowLayout());
		p33.add(b3);
		
		JLabel l34 = new JLabel("Autorstwo Kuba Kubiak");
		
		p3.add(p31);
		p3.add(p32);
		p3.add(p33);
		p3.add(l34);
	
		
		
		/* Dodanie do p1 paneli p2 i p3 */
		p1.add(p2);
		p1.add(p3);
		this.add(p1,BorderLayout.CENTER);
		
		
		setVisible(true);
		
		
	}
	
	public static void main(String[] args)
	{
		Test app = new Test();
	}

}
