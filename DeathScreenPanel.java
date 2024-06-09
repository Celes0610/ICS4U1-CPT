import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class DeathScreenPanel extends JPanel implements ActionListener {
	// Properties
	JLabel Winner = new JLabel("");
	JButton PlayAgain = new JButton("Play Again");
	JButton Exit = new JButton("Exit");

	// Constructor
	public DeathScreenPanel() {
		PlayAgain.setBounds(560,300,60,40);
		add(PlayAgain);
		PlayAgain.addActionListener(this);

		Exit.setBounds(680,300,60,40);
		add(Exit);
		Exit.addActionListener(this);
	}

	public void Winner(String strUsername) {
		Winner.setText(strUsername + " Wins!");
	}   

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == PlayAgain) {

		}else if (evt.getSource() == Exit) {

		}            
	}
}
