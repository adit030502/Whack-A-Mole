package whack_a_mole;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.*;
import javax.imageio.ImageIO;


public class Game extends JFrame implements ActionListener{
	private static final int MAX_CREATURES = 4; 
	private static final String SCORE_PREFIX = "Score: "; 
	private static final String TIME_PREFIX = "Time: "; 
	private static final double LENGTH_OF_GAME = .25*60000; 
	private static final int TARGET_SCORE = 200; 
	private static final int MAX_LEVEL = 3;  
	private static final int CREATURES_PER_LEVEL = 8;
	private static Random rand = new Random(); 
	private static  int numOfCreatures = 6;
	private static int level = 1;
	static int score; 
	static JLabel scoreLabel; 
	static JLabel timeLabel; 
	public static int creaturesAlive; 
	
	Creature[] creatures;
	
	
	public static void main(String[] args) {
                Game this_game = new Game();
		JOptionPane.showMessageDialog(this_game, "Instruksi: Klik gambar tikus untuk mendapatkan 10 point.\n  Game ini ada " + MAX_LEVEL + 
												" level. Setiap level memerlukan waktu 15 detik.\n Kamu membutuhkan " + TARGET_SCORE + " point untuk pindah ke level selanjutnya. Good luck.");
		
		
		while(true) {
			
			while(level <= MAX_LEVEL) {
				JOptionPane.showMessageDialog(this_game, "Level " + level + "\n Jumlah lubang " + numOfCreatures + "\n Pencet OK untuk pindah ke level berikutnya.");
				this_game.playGame(); 
				if(score < TARGET_SCORE) {
					JOptionPane.showMessageDialog(this_game, "Level " + level + " Score: " + score + "\n Kamu tidak mencapai target " + TARGET_SCORE + " point.  Game Over");
					break; 
					}

				nextLevel();

				if(level <= MAX_LEVEL) {
					this_game.dispose();
					this_game = new Game();
				}   	
			}

			if(level > MAX_LEVEL)
				JOptionPane.showMessageDialog(this_game, "Selamat, kamu telah memenangkan game ini!");

			int response = JOptionPane.showConfirmDialog(this_game, "Thank you for playing!\n apakah kamu ingin memainkannya lagi?", "Play Again?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) 
				break; 

			resetLevel(); 
			this_game.dispose();
			this_game = new Game();
		
		}

		this_game.dispose(); 
		 

	}

	public static void update_time(double timeRemaining) {
		timeLabel.setText(TIME_PREFIX + NumberFormat.getInstance().format(timeRemaining/1000)); 		
	}

		public static void update_score() {
			score += 10; 
			scoreLabel.setText(SCORE_PREFIX + score); 		
		}
	
	public Game() {
		setSize(500, 500); 
		setLayout(new BorderLayout()); 
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Whack-a-Mole"); 

		score = 0; 
		creaturesAlive = 0; 

		JPanel top = intitialize_top(); 
		add(top, BorderLayout.NORTH);

		JPanel field = intitialize_field(); 
		add(field, BorderLayout.CENTER);

		this.setVisible(true); 
		
		
	}

	private JPanel intitialize_field() { 
		JPanel field = new JPanel(); 
		field.setLayout(new GridLayout(4,3, 5, 5)); 

		creatures = new Creature[numOfCreatures]; 
		for(int x = 0; x < creatures.length; x++) {
			creatures[x] = new Creature();
			creatures[x].addActionListener(this); 
			field.add(creatures[x]); 	
		}
		return field; 
		
	}

	private JPanel intitialize_top() { 
		JPanel top = new JPanel(); 
		top.setLayout(new GridLayout(1,2)); 

		scoreLabel = new JLabel(); 
		timeLabel = new JLabel(); 
		scoreLabel.setText(SCORE_PREFIX);
		timeLabel.setText(TIME_PREFIX);
		top.add(scoreLabel); 
		top.add(timeLabel); 
		
		return top; 
	}

	private void playGame() {
		double startTime = new Date().getTime(); 
		double currentTime = startTime; 
		double timeRemaining = LENGTH_OF_GAME; 

		while(( LENGTH_OF_GAME - timeRemaining) < LENGTH_OF_GAME) {

			long time = System.currentTimeMillis();
			
			reviveCreatures(); 
			updateCreatures(); 

			try{
				long delay = Math.max(0, 32-(System.currentTimeMillis()-time));
				
				Thread.sleep(delay);
				}
			catch(InterruptedException e)
			{
					
			}
 
			currentTime = new Date().getTime(); 

			timeRemaining = LENGTH_OF_GAME - (currentTime - startTime);

			update_time(timeRemaining); 
			
			
		}
		
		
	}
        
	private void updateCreatures() {
		for(int x = 0; x < creatures.length; x++)
			creatures[x].update(); 
	}

	private void reviveCreatures() {
		if (creaturesAlive < MAX_CREATURES) {
			int randomCreature = rand.nextInt(numOfCreatures); 
			if(!creatures[randomCreature].getIsAlive()) {
				creatures[randomCreature].revive(); 
				creaturesAlive++; 
			}
		}
	}

	private static void nextLevel() {
		level++; 
		numOfCreatures = CREATURES_PER_LEVEL * level; 
	}

	private static void resetLevel() {
		level = 1; 
		numOfCreatures = CREATURES_PER_LEVEL; 
	}
	
        @Override
	public void actionPerformed(ActionEvent event) {
		Creature clickedCreature = (Creature) event.getSource();
		if(clickedCreature.getIsAlive()) {
			clickedCreature.kill();
			update_score(); 
		}
		
	}
	
}