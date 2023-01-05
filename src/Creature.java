package whack_a_mole;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.ImageIcon;

public class Creature extends JButton{
        
	private static final int MIN_LIFE = 50;  
	private static final int MAX_LIFE = 300;  
	static Random rand = new Random(); 
	
	private boolean isAlive;
	private int life_count; 
	private int final_life; 
	
	public Creature() {
		isAlive = false; 
                this.setIcon(loadImage("/image/mole.jpg")); 
		life_count = 0; 
		
	}

	public boolean getIsAlive() {
		return isAlive; 
	}

	public void revive() {
		if(!isAlive) {
			final_life = MIN_LIFE + rand.nextInt(MAX_LIFE - MIN_LIFE + 1);  
			isAlive = true;
			this.setIcon(loadImage("/image/jerry.jpg")); 
		}
		
		
	}

	public void kill() {
			isAlive = false;
			this.setIcon(loadImage("/image/mole.jpg")); 
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

    private Icon loadImage(String path) {
        Image image = new ImageIcon(this.getClass().getResource(path)).getImage();
        Image scaledImage = image.getScaledInstance(200, 100,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}