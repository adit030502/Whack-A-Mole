package whack_a_mole;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Creature extends JButton{
        
	private static final int MIN_LIFE = 50;  
	private static final int MAX_LIFE = 300;  
	static Random rand = new Random(); 
	
	private boolean isAlive; 
	private int life_count; 
	private int final_life; 
	
	public Creature() {
		isAlive = false; 
                this.setBackground(Color.RED); 
		life_count = 0; 
		
	}

	public boolean getIsAlive() {
		return isAlive; 
	}

	public void revive() {
		if(!isAlive) {
			final_life = MIN_LIFE + rand.nextInt(MAX_LIFE - MIN_LIFE + 1);  
			isAlive = true;
			this.setBackground(Color.GREEN); 
		}
		
		
	}

	public void kill() {
			isAlive = false;
			this.setBackground(Color.RED); 
			life_count = 0; 
			Game.creaturesAlive--; 
	}

	public void update() {
		if(isAlive) {
			life_count++; 

			if(life_count == final_life) 
				this.kill();
		}
	}
}