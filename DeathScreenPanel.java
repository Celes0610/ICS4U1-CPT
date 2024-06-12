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
	public DeathScreenPanel(JFrame frame) {
		super();
	
		PlayAgain.setBounds(540,400,80,40);
		PlayAgain.addActionListener(this);

		Exit.setBounds(660,400,80,40);
		Exit.addActionListener(this);

		add(PlayAgain);
		add(Exit);
		add(Winner);

	}

	public void Winner(String strUsername) {
		Winner.setText(strUsername + " Wins!");
	}   

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == PlayAgain) {
			new snakeGame();
		}else if (evt.getSource() == Exit) {
			System.exit(ABORT);
		}            
	}
}
