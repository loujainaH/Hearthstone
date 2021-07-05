package controller;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import GUI.GUI;
import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.*;

public class controller implements ActionListener , MouseListener {
	private GUI view1;
	private GUI view2;
	private GUI view3;
	private Game model;
	private Hero hero1;
	private Hero hero2;
	private JTextArea text1;
	private JTextArea text2;
	private ArrayList<JButton> buttons;
	boolean mouseClicked;
	private Minion attackerHand;
	private Spell  spell;
	private Minion attackerField;
	private Minion attackedField;
	private boolean heroAttacked;
	private Spell spellActive;
	private boolean spellOnHero;
	//private boolean spellOnHero2;
	private boolean spellOnField1;
	private boolean spellOnField2;
	private boolean useHeroPower;
	//private boolean handMinion;
	private boolean cont;

public controller() throws FullHandException, CloneNotSupportedException {
		 super();		
		 cont=true;
		 view1=new GUI();
	     view1.getContentPane().setLayout(new GridLayout( 1 , 6 , 10 , 10 ));
		 
	     JButton h=new JButton();
		 h.setText("Hunter");
		 h.setIcon(new ImageIcon("images/hunter.png"));
		 h.setContentAreaFilled(false);
		 
		 JButton m=new JButton();
		 m.setText("Mage");
		 m.setIcon(new ImageIcon("images/mage.png"));
		 m.setContentAreaFilled(false);
		 
		 JButton pa=new JButton();
		 pa.setText("Paladin");
		 pa.setIcon(new ImageIcon("images/paladin.png"));
		 pa.setContentAreaFilled(false);
		 
		 JButton pr=new JButton();
		 pr.setText("Priest");
		 pr.setIcon(new ImageIcon("images/priest.png"));
		 pr.setContentAreaFilled(false);
		 
		 JButton w=new JButton();
		 w.setText("Warlock");
		 w.setIcon(new ImageIcon("images/warlock.png"));
		 w.setContentAreaFilled(false);

		 h.setName("Hunter1");
		 m.setName("Mage1");
		 pa.setName("Paladin1");
		 pr.setName("Priest1");
		 w.setName("Warlock1");
		 
		 view1.add(m);
		 view1.add(h);
		 view1.add(w);
		 view1.add(pa);
		 view1.add(pr);
		 text1 = new JTextArea();
		 text1.setPreferredSize(new Dimension(50,view1.getWidth()));
		 text1.setFont(new Font("Courier",Font.BOLD,view1.getWidth()/45));
		 text1.setBackground(Color.BLACK);
		 text1.setForeground(Color.magenta);
		 text1.setOpaque(true);
		 text1.setEditable(false);
		 view1.add(text1,BorderLayout.NORTH);
		 text1.setText("\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"     Hero 1:");
		 view1.revalidate();
		 view1.repaint();
		 
		 m.addActionListener(this);
		 h.addActionListener(this);
		 w.addActionListener(this);
		 pa.addActionListener(this);
		 pr.addActionListener(this);
		 view1.setVisible(true);
	
		 view2=new GUI();
	     view2.getContentPane().setLayout(new GridLayout(1,6 ,10 ,10));
		
	     JButton h2=new JButton();
		 h2.setText("Hunter");
		 h2.setIcon(new ImageIcon("images/hunter.png"));
		 h2.setContentAreaFilled(false);
		 
		 JButton m2=new JButton();
		 m2.setText("Mage");
		 m2.setIcon(new ImageIcon("images/mage.png"));
		 m2.setContentAreaFilled(false);
		 
		 JButton pa2=new JButton();
		 pa2.setText("Paladin");
		 pa2.setIcon(new ImageIcon("images/paladin.png"));
		 pa2.setContentAreaFilled(false);
		 
		 JButton pr2=new JButton();
		 pr2.setText("Priest");
		 pr2.setIcon(new ImageIcon("images/priest.png"));
		 pr2.setContentAreaFilled(false);
		 
		 JButton w2=new JButton();
		 w2.setText("Warlock");
		 w2.setIcon(new ImageIcon("images/warlock.png"));
		 w2.setContentAreaFilled(false);
		 
		 h2.setName("Hunter2");
		 m2.setName("Mage2");
		 pa2.setName("Paladin2");
		 pr2.setName("Priest2");
		 w2.setName("Warlock2");
		 
		 view2.add(m2);
		 view2.add(h2);
		 view2.add(w2);
		 view2.add(pa2);
		 view2.add(pr2);
		 view2.revalidate();
		 view2.repaint();
		 m2.addActionListener(this);
		 h2.addActionListener(this);
		 w2.addActionListener(this);
		 pa2.addActionListener(this);
		 pr2.addActionListener(this);
		 text2 = new JTextArea();
		 text2.setPreferredSize(new Dimension(50, view2.getWidth()));
		 text2.setFont(new Font("Courier",Font.BOLD,view1.getWidth()/45));
		 text2.setBackground(Color.BLACK);
		 text2.setForeground(Color.magenta);
		 text2.setOpaque(true); 
		 text2.setEditable(false);
		 view2.add(text2 , BorderLayout.NORTH);
		 text2.setText("\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"     Hero 2:");
	     view2.setVisible(false);
	     view3 =new GUI();
	     view3.getContentPane().setLayout(new GridLayout( 4 , 12 , 4 ,8));
	     view3.revalidate();
	     view3.repaint();
	     view3.setVisible(false); 
	 }
		

private void xBox(String s){
		String [] x = s.split(" ");
		if (!x[0].equals("Congrats") && !(model.getCurrentHero().getCurrentHP()<=0|| model.getOpponent().getCurrentHP()<=0)) {
		   ps("sounds/TAN1.wav");
		   JOptionPane.showMessageDialog(null, s);
		   update();
		}
		else
				{JOptionPane.showMessageDialog(null, s);
				System.exit(0);
				view3.setVisible(false);
				}
		}
	


public void actionPerformed(ActionEvent e)  {		
		JButton b=(JButton)e.getSource();
		System.out.println(e.getActionCommand());
		Card c =null;
		
		/*if(b.getName().equals("gameOver")){
			view3.dispose();
		}
		if(b.getName().equals("again")){
			try {
				view3.dispose();				
				controller tany=new controller();
				tany.cont=false;
			} catch (FullHandException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}*/
			
		if(b.getText().equals("End Turn")){
			try {
				 
				model.endTurn();
				update();
			} catch (FullHandException | CloneNotSupportedException e1) {
				update();
				if (e1 instanceof FullHandException) {
			//		xBox(e1.getMessage());//ps("sounds/TAN.wav");
					Card burned=((FullHandException) e1).getBurned();
					if (burned instanceof Spell) {
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
							
					}
				   if (burned instanceof Minion) {
					  xBox(e1.getMessage());//ps("sounds/TAN.wav");
					  
						String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
						 if(((Minion)burned).isDivine()){ 
						    	x = x+ "Minion Has a Divine Shield"+"\n";
						    }
						   
						    if(((Minion)burned).isTaunt()){
						    	x = x+ "Taunt Minion"+"\n";
						    }
						    if(!((Minion)burned).isSleeping()){
						    	x = x+ "Charge Minion"+"\n";
						    }
						    xBox(x);		
				   }}
					if (e1 instanceof CloneNotSupportedException)
					xBox(e1.getMessage());//ps("sounds/TAN.wav");
					e1.printStackTrace();
			}
			
		}
// USE HERO CHECK AFTER TA3DEEEEL !!!
		if(b.getText().equals("Use Hero Power")){
			String hero = model.getCurrentHero().getName();
			 if (attackerField!=null||attackedField!=null||heroAttacked||spellOnField1||spellOnField2) {
				 if (hero.equals("Rexxar")||hero.equals("Uther Lightbringer")||hero.equals("Gul'dan")) {
					 xBox("Your hero power can not pick a target  !!!");
					 update();
				 }
			 }
			 else
			switch(hero) {
			case "Anduin Wrynn": if (attackerField!=null) {
				try {
					((Priest)model.getCurrentHero()).useHeroPower(attackerField);
					 ps("sounds/boom (1).wav");
					 update();
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					update();
					if (e1 instanceof FullHandException) {
						//xBox(e1.getMessage());//ps("sounds/TAN.wav");
						Card burned=((FullHandException) e1).getBurned();
						if (burned instanceof Spell) {
						
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							
							xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
								
						}
					   if (burned instanceof Minion) {
						  xBox(e1.getMessage());//ps("sounds/TAN.wav");
						  
							String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
							 if(((Minion)burned).isDivine()){ 
							    	x = x+ "Minion Has a Divine Shield"+"\n";
							    }
							   
							    if(((Minion)burned).isTaunt()){
							    	x = x+ "Taunt Minion"+"\n";
							    }
							    if(!((Minion)burned).isSleeping()){
							    	x = x+ "Charge Minion"+"\n";
							    }
							    xBox(x);		
					   }}
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
				}
				
				
			}
			else if (attackedField!=null) {
				try {
					((Priest)model.getCurrentHero()).useHeroPower(attackedField);
					ps("sounds/boom (1).wav");
					update();
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					update();
					if (e1 instanceof FullHandException) {
						//xBox(e1.getMessage());//ps("sounds/TAN.wav");
						Card burned=((FullHandException) e1).getBurned();
						if (burned instanceof Spell) {
						
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							
							xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
								
						}
					   if (burned instanceof Minion) {
						  xBox(e1.getMessage());//ps("sounds/TAN.wav");
						  
							String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
							 if(((Minion)burned).isDivine()){ 
							    	x = x+ "Minion Has a Divine Shield"+"\n";
							    }
							   
							    if(((Minion)burned).isTaunt()){
							    	x = x+ "Taunt Minion"+"\n";
							    }
							    if(!((Minion)burned).isSleeping()){
							    	x = x+ "Charge Minion"+"\n";
							    }
							    xBox(x);		
					   }}
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
				}
			}
			else if (heroAttacked) {
				try {
					((Priest)model.getCurrentHero()).useHeroPower(model.getOpponent());
					ps("sounds/boom (1).wav");
					update();
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					update();
					if (e1 instanceof FullHandException) {
						//xBox(e1.getMessage());//ps("sounds/TAN.wav");
						Card burned=((FullHandException) e1).getBurned();
						if (burned instanceof Spell) {
						
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							
							xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
								
						}
					   if (burned instanceof Minion) {
						  xBox(e1.getMessage());//ps("sounds/TAN.wav");
						  
							String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
							 if(((Minion)burned).isDivine()){ 
							    	x = x+ "Minion Has a Divine Shield"+"\n";
							    }
							   
							    if(((Minion)burned).isTaunt()){
							    	x = x+ "Taunt Minion"+"\n";
							    }
							    if(!((Minion)burned).isSleeping()){
							    	x = x+ "Charge Minion"+"\n";
							    }
							    xBox(x);		
					   }}
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
				}
			}
			else if (spellOnHero) {
				try {
					((Priest)model.getCurrentHero()).useHeroPower(model.getCurrentHero());
					 ps("sounds/boom (1).wav");
					 update();				
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					update();
					if (e1 instanceof FullHandException) {
						//xBox(e1.getMessage());//ps("sounds/TAN.wav");
						Card burned=((FullHandException) e1).getBurned();
						if (burned instanceof Spell) {
						
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							
							xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
								
						}
					   if (burned instanceof Minion) {
						  xBox(e1.getMessage());//ps("sounds/TAN.wav");
						  
							String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
							 if(((Minion)burned).isDivine()){ 
							    	x = x+ "Minion Has a Divine Shield"+"\n";
							    }
							   
							    if(((Minion)burned).isTaunt()){
							    	x = x+ "Taunt Minion"+"\n";
							    }
							    if(!((Minion)burned).isSleeping()){
							    	x = x+ "Charge Minion"+"\n";
							    }
							    xBox(x);		
					   }}
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
				}
			}
			else useHeroPower = true;	
			break;
			case "Jaina Proudmoore": if (attackerField!=null) {
				try {
					((Mage)model.getCurrentHero()).useHeroPower(attackerField);
					ps("sounds/boom (1).wav");
					update();
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					update();
					if (e1 instanceof FullHandException) {
					//	xBox(e1.getMessage());//ps("sounds/TAN.wav");
						Card burned=((FullHandException) e1).getBurned();
						if (burned instanceof Spell) {
						
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							
							xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
								
						}
					   if (burned instanceof Minion) {
						  xBox(e1.getMessage());//ps("sounds/TAN.wav");
						  
							String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
							 if(((Minion)burned).isDivine()){ 
							    	x = x+ "Minion Has a Divine Shield"+"\n";
							    }
							   
							    if(((Minion)burned).isTaunt()){
							    	x = x+ "Taunt Minion"+"\n";
							    }
							    if(!((Minion)burned).isSleeping()){
							    	x = x+ "Charge Minion"+"\n";
							    }
							    xBox(x);		
					   }}
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
				}
			}
			else if (attackedField!=null) {
				try {
					((Mage)model.getCurrentHero()).useHeroPower(attackedField);
					ps("sounds/boom (1).wav");
					update();
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					update();
					if (e1 instanceof FullHandException) {
						//xBox(e1.getMessage());//ps("sounds/TAN.wav");
						Card burned=((FullHandException) e1).getBurned();
						if (burned instanceof Spell) {
						
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							
							xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
								
						}
					   if (burned instanceof Minion) {
						  xBox(e1.getMessage());//ps("sounds/TAN.wav");
						  
							String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
							 if(((Minion)burned).isDivine()){ 
							    	x = x+ "Minion Has a Divine Shield"+"\n";
							    }
							   
							    if(((Minion)burned).isTaunt()){
							    	x = x+ "Taunt Minion"+"\n";
							    }
							    if(!((Minion)burned).isSleeping()){
							    	x = x+ "Charge Minion"+"\n";
							    }
							    xBox(x);		
					   }}
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
				}
			}
			else if (heroAttacked) {
				try {
					((Mage)model.getCurrentHero()).useHeroPower(model.getOpponent());
					ps("sounds/boom (1).wav");
					update();
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					update();
					if (e1 instanceof FullHandException) {
						//xBox(e1.getMessage());//ps("sounds/TAN.wav");
						Card burned=((FullHandException) e1).getBurned();
						if (burned instanceof Spell) {
						
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							
							xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
								
						}
					   if (burned instanceof Minion) {
						  xBox(e1.getMessage());//ps("sounds/TAN.wav");
						  
							String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
							 if(((Minion)burned).isDivine()){ 
							    	x = x+ "Minion Has a Divine Shield"+"\n";
							    }
							   
							    if(((Minion)burned).isTaunt()){
							    	x = x+ "Taunt Minion"+"\n";
							    }
							    if(!((Minion)burned).isSleeping()){
							    	x = x+ "Charge Minion"+"\n";
							    }
							    xBox(x);		
					   }}else{
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
				}}
			}
			else if (spellOnHero) {
				try {
					((Mage)model.getCurrentHero()).useHeroPower(model.getCurrentHero());
					ps("sounds/boom (1).wav");
					update();
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					update();
					if (e1 instanceof FullHandException) {
					//	xBox(e1.getMessage());//ps("sounds/TAN.wav");
						Card burned=((FullHandException) e1).getBurned();
						if (burned instanceof Spell) {
						
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							
							xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
								
						}
					   if (burned instanceof Minion) {
						  xBox(e1.getMessage());//ps("sounds/TAN.wav");
						  
							String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
							 if(((Minion)burned).isDivine()){ 
							    	x = x+ "Minion Has a Divine Shield"+"\n";
							    }
							   
							    if(((Minion)burned).isTaunt()){
							    	x = x+ "Taunt Minion"+"\n";
							    }
							    if(!((Minion)burned).isSleeping()){
							    	x = x+ "Charge Minion"+"\n";
							    }
							    xBox(x);		
					   }}else{
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
				}}
				}
			
			else useHeroPower = true;		
			break;
			default:try {
					model.getCurrentHero().useHeroPower();
					ps("sounds/boom (1).wav");
					update();
				} catch (FullHandException | CloneNotSupportedException e1) {
					update();
					if (e1 instanceof FullHandException) {
					//	xBox(e1.getMessage());//ps("sounds/TAN.wav");
						Card burned=((FullHandException) e1).getBurned();
						if (burned instanceof Spell) {
						
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							
							xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
								
						}
					   if (burned instanceof Minion) {
						  xBox(e1.getMessage());//ps("sounds/TAN.wav");
						  
							String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
							 if(((Minion)burned).isDivine()){ 
							    	x = x+ "Minion Has a Divine Shield"+"\n";
							    }
							   
							    if(((Minion)burned).isTaunt()){
							    	x = x+ "Taunt Minion"+"\n";
							    }
							    if(!((Minion)burned).isSleeping()){
							    	x = x+ "Charge Minion"+"\n";
							    }
							    xBox(x);		
					   }}else{
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
				}
				} catch (NotEnoughManaException e1) {
					update();
					xBox(e1.getMessage());//ps("sounds/TAN.wav");
					e1.printStackTrace();
				} catch (HeroPowerAlreadyUsedException e1) {
					update();
					xBox(e1.getMessage());//ps("sounds/TAN.wav");
					e1.printStackTrace();
				} catch (NotYourTurnException e1) {
					update();
					xBox(e1.getMessage());//ps("sounds/TAN.wav");
					e1.printStackTrace();
				} catch (FullFieldException e1) {
					update();
					xBox(e1.getMessage());//ps("sounds/TAN.wav");
					e1.printStackTrace();
				} break;
				
			}
			
		}
		if(b.getText().equals("Hunter")) {
			
			try {
				if(view1.isVisible()) {
				hero1=new Hunter();
				view2.setVisible(true);
				view1.setVisible(false);
				 ps("sounds/wand.wav");}
				else {
					ps("sounds/wand.wav");
					hero2=new Hunter();
					view2.setVisible(false);
					view3.setVisible(true);
					ps("sounds/Background-Music.wav");
					 if(hero1 != null && hero2 != null){
				    	 model=new Game(hero1,hero2);
				     if(model.getCurrentHero().equals(hero1)){
				    	 hero1=model.getCurrentHero();
				    	 hero2=model.getOpponent();
				     }else{
				    	 hero2=model.getCurrentHero();
				    	 hero1=model.getOpponent();
				    	 
				     }
						     hand(model.getOpponent());
						     field(model.getOpponent());
						     //middle();
						     field(model.getCurrentHero());
						     hand(model.getCurrentHero());
				}	 
				}} catch (IOException e1) {
					xBox(e1.getMessage());//ps("sounds/TAN.wav");
				  e1.printStackTrace();
			} catch (CloneNotSupportedException e1) {
				xBox(e1.getMessage());
				e1.printStackTrace();
			} catch (FullHandException e1) {
				//xBox(e1.getMessage());//ps("sounds/TAN.wav");
				Card burned=((FullHandException) e1).getBurned();
				if (burned instanceof Spell) {
				
					xBox(e1.getMessage());//ps("sounds/TAN.wav");
					
					xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
						
				}
			   if (burned instanceof Minion) {
				  xBox(e1.getMessage());//ps("sounds/TAN.wav");
				  
					String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
					 if(((Minion)burned).isDivine()){ 
					    	x = x+ "Minion Has a Divine Shield"+"\n";
					    }
					   
					    if(((Minion)burned).isTaunt()){
					    	x = x+ "Taunt Minion"+"\n";
					    }
					    if(!((Minion)burned).isSleeping()){
					    	x = x+ "Charge Minion"+"\n";
					    }
					    xBox(x);
					    
				
			   }
				e1.printStackTrace();
			}
		}
			if(b.getText().equals("Mage")) {
				
					try {
						if(view1.isVisible()) {
						hero1=new Mage();
						view2.setVisible(true);
						view1.setVisible(false);
						 ps("sounds/wand.wav");
						}
						else{
							ps("sounds/wand.wav");
							hero2=new Mage();
							view2.setVisible(false);
							view3.setVisible(true);
							ps("sounds/Background-Music.wav");
							if(hero1 != null && hero2 != null){
						    	 
						    	 model=new Game(hero1,hero2);
						     if(model.getCurrentHero().equals(hero1)){
						    	 hero1=model.getCurrentHero();
						    	 hero2=model.getOpponent();
						     }else{
						    	 hero2=model.getCurrentHero();
						    	 hero1=model.getOpponent();
						     }  
									     hand(model.getOpponent());
									     field(model.getOpponent());
									     //middle();
									     field(model.getCurrentHero());
									     hand(model.getCurrentHero());}
						}} catch (IOException e1) {
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
					} catch (FullHandException e1) {
						Card burned=((FullHandException) e1).getBurned();
						if (burned instanceof Spell) {
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							
							xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
								
						}
					   if (burned instanceof Minion) {
						  xBox(e1.getMessage());//ps("sounds/TAN.wav");
						  
							String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
							 if(((Minion)burned).isDivine()){ 
							    	x = x+ "Minion Has a Divine Shield"+"\n";
							    }
							   
							    if(((Minion)burned).isTaunt()){
							    	x = x+ "Taunt Minion"+"\n";
							    }
							    if(!((Minion)burned).isSleeping()){
							    	x = x+ "Charge Minion"+"\n";
							    }
							    xBox(x);
							    
						
					   }
						e1.printStackTrace();
					}				
		}
			if(b.getText().equals("Paladin")) {
					try {if(view1.isVisible()) {
								hero1=new Paladin();
								view2.setVisible(true);
								view1.setVisible(false);
								 ps("sounds/wand.wav");
					}else{
								ps("sounds/wand.wav");
								hero2=new Paladin();
								view2.setVisible(false);
								view3.setVisible(true);
								 ps("sounds/Background-Music.wav"); 
								if(hero1 != null && hero2 != null){
							    	 
							    	 model=new Game(hero1,hero2);
							     if(model.getCurrentHero().equals(hero1)){
							    	 hero1=model.getCurrentHero();
							    	 hero2=model.getOpponent();
							     }else{
							    	 hero2=model.getCurrentHero();
							    	 hero1=model.getOpponent();
							     }   
										     hand(model.getOpponent());
										     field(model.getOpponent());
										     //middle();
										     field(model.getCurrentHero());
										     hand(model.getCurrentHero());}
							}
					} catch (IOException e1) {
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
					} catch (FullHandException e1) {
						Card burned=((FullHandException) e1).getBurned();
						if (burned instanceof Spell) {
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							
							xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
								
						}
					   if (burned instanceof Minion) {
						  xBox(e1.getMessage());//ps("sounds/TAN.wav");
						  
							String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
							 if(((Minion)burned).isDivine()){ 
							    	x = x+ "Minion Has a Divine Shield"+"\n";
							    }
							   
							    if(((Minion)burned).isTaunt()){
							    	x = x+ "Taunt Minion"+"\n";
							    }
							    if(!((Minion)burned).isSleeping()){
							    	x = x+ "Charge Minion"+"\n";
							    }
							    xBox(x);
							    
						
					   }
						e1.printStackTrace();
					}				
		}
			if(b.getText().equals("Priest")) {
					try {if(view1.isVisible()) {
								hero1=new Priest();
								view2.setVisible(true);
								view1.setVisible(false);
								 ps("sounds/wand.wav");
							}else{ 
								ps("sounds/wand.wav");
								hero2=new Priest();
								view2.setVisible(false);
								view3.setVisible(true);
								ps("sounds/Background-Music.wav");
								if(hero1 != null && hero2 != null){
							    	 
							    	 model=new Game(hero1,hero2);
							     if(model.getCurrentHero().equals(hero1)){
							    	 hero1=model.getCurrentHero();
							    	 hero2=model.getOpponent();
							     }else{
							    	 hero2=model.getCurrentHero();
							    	 hero1=model.getOpponent();
							     }
							    
										     hand(model.getOpponent());
										     field(model.getOpponent());
										     //middle();
										     field(model.getCurrentHero());
										     hand(model.getCurrentHero());}
							}
					} catch (IOException e1) {
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
					} catch (FullHandException e1) {
						Card burned=((FullHandException) e1).getBurned();
						if (burned instanceof Spell) {
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							
							xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
								
						}
					   if (burned instanceof Minion) {
						  xBox(e1.getMessage());//ps("sounds/TAN.wav");
						  
							String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
							 if(((Minion)burned).isDivine()){ 
							    	x = x+ "Minion Has a Divine Shield"+"\n";
							    }
							   
							    if(((Minion)burned).isTaunt()){
							    	x = x+ "Taunt Minion"+"\n";
							    }
							    if(!((Minion)burned).isSleeping()){
							    	x = x+ "Charge Minion"+"\n";
							    }
							    xBox(x);
							    
						
					   }
						e1.printStackTrace();
					}				
		}
			if(b.getText().equals("Warlock")) {
				
					try {	
							if(view1.isVisible()) {
								hero1=new Warlock();
								view2.setVisible(true);
								view1.setVisible(false);
								 ps("sounds/wand.wav");
							}else{
								    ps("sounds/wand.wav");
									hero2=new Warlock();
									view2.setVisible(false);
									view3.setVisible(true);
									 ps("sounds/Background-Music.wav"); 
									if(hero1 != null && hero2 != null){
								    	 
								    	 model=new Game(hero1,hero2);
								     if(model.getCurrentHero().equals(hero1)){
								    	 hero1=model.getCurrentHero();
								    	 hero2=model.getOpponent();
								     }else{
								    	 hero2=model.getCurrentHero();
								    	 hero1=model.getOpponent();
								     }  
										     hand(model.getOpponent());
										     field(model.getOpponent());
										    // middle();
										     field(model.getCurrentHero());
										     hand(model.getCurrentHero());}
							}
					} catch (IOException e1) {
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
					} catch (FullHandException e1) {
						Card burned=((FullHandException) e1).getBurned();
						if (burned instanceof Spell) {
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							
							xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
								
						}
					   if (burned instanceof Minion) {
						  xBox(e1.getMessage());//ps("sounds/TAN.wav");
						  
							String x="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
							 if(((Minion)burned).isDivine()){ 
							    	x = x+ "Minion Has a Divine Shield"+"\n";
							    }
							   
							    if(((Minion)burned).isTaunt()){
							    	x = x+ "Taunt Minion"+"\n";
							    }
							    if(!((Minion)burned).isSleeping()){
							    	x = x+ "Charge Minion"+"\n";
							    }
							    xBox(x);
							    
						
					   }
						e1.printStackTrace();
					}				
		}
	if(hero1 != null && hero2 != null){
			String x= b.getName();
			
			switch(x){
			// if it was in my hand;	
			case "0CH" :  Card q= model.getCurrentHero().getHand().get(0) ;
						myHand(q);
						//update();
						break;
			case "1CH" :Card q1= model.getCurrentHero().getHand().get(1) ;
			           myHand(q1);
						//update();
						break;
			case "2CH" : Card q2= model.getCurrentHero().getHand().get(2) ;
						myHand(q2);
						//update();
						break;
			case "3CH" : Card q3= model.getCurrentHero().getHand().get(3) ;
						myHand(q3);
						//update();
						break;
			case "4CH" : Card q4= model.getCurrentHero().getHand().get(4) ;
			            myHand(q4);
						//update();
						break;
			case "5CH" : Card q5= model.getCurrentHero().getHand().get(5) ;
						myHand(q5);
						//update();
						break;
			case "6CH" : Card q6= model.getCurrentHero().getHand().get(6) ;
						myHand(q6);
						//update();
						break;
			case "7CH" : Card q7= model.getCurrentHero().getHand().get(7) ;
						myHand(q7);
						//update();
			             break;
			case "8CH" : Card q8= model.getCurrentHero().getHand().get(8) ;
						myHand(q8);
						//update();
						break;
			case "9CH" : Card q9= model.getCurrentHero().getHand().get(9) ;
						myHand(q9);
						//update();
						break;
						
			// if it was a Minion on my field;	
						
						
			case "0CF" : Minion w= model.getCurrentHero().getField().get(0);
			             currentField(w);
						 break;
			case "1CF" :Minion w1= model.getCurrentHero().getField().get(1);
						currentField(w1);						
						 break;
			case "2CF" :Minion w2= model.getCurrentHero().getField().get(2);
						currentField(w2);	 
						 break;
			case "3CF" :Minion w3= model.getCurrentHero().getField().get(3);
						currentField(w3);
						 break;
			case "4CF" :Minion w4= model.getCurrentHero().getField().get(4);
						currentField(w4);	 
						 break;
			case "5CF" :Minion w5= model.getCurrentHero().getField().get(5);
						currentField(w5);
						 break;
			case "6CF" :Minion w6= model.getCurrentHero().getField().get(6);
						currentField(w6);
						 break;
						 
						 
			// if it was a Minion on opponent field;		
			case "0OF" : Minion r= model.getOpponent().getField().get(0);
						opponentField(r);
						break;
			case "1OF" :Minion r1= model.getOpponent().getField().get(1);
						opponentField(r1);
						 break;
			case "2OF" :Minion r2= model.getOpponent().getField().get(2);
						opponentField(r2);
						 break;
			case "3OF" :Minion r3= model.getOpponent().getField().get(3);
						opponentField(r3);
						 break;
			case "4OF" :Minion r4= model.getOpponent().getField().get(4);
						opponentField(r4);
						 break;
			case "5OF" :Minion r5= model.getOpponent().getField().get(5);
						opponentField(r5);
						 break;
			case "6OF" :Minion r6= model.getOpponent().getField().get(6);
			             opponentField(r6);
						 break;	
			// 3ala el ragel el sherer!!!			
			case "Attack Hero": 
				   if (attackerField!=null) {
					try {
						model.getCurrentHero().attackWithMinion(attackerField, model.getOpponent());
						ps("sounds/boom (1).wav");
						attackerField=null;
						heroAttacked=false;
						update();
						
					} catch (CannotAttackException | NotYourTurnException | TauntBypassException | NotSummonedException
							| InvalidTargetException e1) {
						update();
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
					}
					
					}
				   else if (spellActive!=null) {
					   String s = spellActive.getName();
					   switch(s) {
					   case "Kill Command":try {
							model.getCurrentHero().castSpell((HeroTargetSpell)spellActive, model.getOpponent());
							ps("sounds/boom (1).wav");
							update();
						} catch (NotYourTurnException | NotEnoughManaException e1) {
							update();
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							e1.printStackTrace();
						};break;
					   case "Pyroblast" :try {
							model.getCurrentHero().castSpell((HeroTargetSpell)spellActive, model.getOpponent());
							ps("sounds/boom (1).wav");
							update();
						} catch (NotYourTurnException | NotEnoughManaException e1) {
							update();
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							e1.printStackTrace();
						};break;
					   case "Divine Spirit":xBox("This spell can only be casted on a MINION !!");//ps("sounds/TAN.wav");
					   	update();break;
					   case "Polymorph":xBox("This spell can only be casted on a MINION !!");//ps("sounds/TAN.wav");
					   update();break;
					   case "Seal of Champions":xBox("This spell can only be casted on a MINION !!");//ps("sounds/TAN.wav");
					   update();break;
					   case "Shadow Word: Death":xBox("This spell can only be casted on a MINION !!");//ps("sounds/TAN.wav");
					   update();break;
					   case "Siphon Soul":xBox("This spell can only be casted on a MINION !!");//ps("sounds/TAN.wav");
					   update();break;
					   case "Level Up!":xBox("This spell can only be casted on a FIELD!!");//ps("sounds/TAN.wav");
					   update();break;
					   }
					   
				   }
				   else if (useHeroPower) {
						 String name=model.getCurrentHero().getName();
						 if (name.equals("Jaina Proudmoore")) {
							 try {
								((Mage)model.getCurrentHero()).useHeroPower(model.getOpponent());
								ps("sounds/boom (1).wav");
								update();
							} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException | FullHandException
									| FullFieldException | CloneNotSupportedException e1) {
								update();
								if (e1 instanceof FullHandException) {
									//xBox(e1.getMessage());//ps("sounds/TAN.wav");
									Card burned=((FullHandException) e1).getBurned();
									if (burned instanceof Spell) {
									
										xBox(e1.getMessage());//ps("sounds/TAN.wav");
										
										xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
											
									}
								   if (burned instanceof Minion) {
									  xBox(e1.getMessage());//ps("sounds/TAN.wav");
									  
										String x1="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
										 if(((Minion)burned).isDivine()){ 
										    	x1 = x1+ "Minion Has a Divine Shield"+"\n";
										    }
										   
										    if(((Minion)burned).isTaunt()){
										    	x1 = x1+ "Taunt Minion"+"\n";
										    }
										    if(!((Minion)burned).isSleeping()){
										    	x1 = x1+ "Charge Minion"+"\n";
										    }
										    xBox(x1);		
								   }}else{
									xBox(e1.getMessage());//ps("sounds/TAN.wav");
									e1.printStackTrace();
							}
							}
							 }
							 else  if (name.equals("Anduin Wrynn")) {
								 try {
										((Priest)model.getCurrentHero()).useHeroPower(model.getOpponent());
										ps("sounds/boom (1).wav");
										update();
									} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException | FullHandException
											| FullFieldException | CloneNotSupportedException e1) {
										update();
										if (e1 instanceof FullHandException) {
											//xBox(e1.getMessage());//ps("sounds/TAN.wav");
											Card burned=((FullHandException) e1).getBurned();
											if (burned instanceof Spell) {
											
												xBox(e1.getMessage());//ps("sounds/TAN.wav");
												
												xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
													
											}
										   if (burned instanceof Minion) {
											  xBox(e1.getMessage());//ps("sounds/TAN.wav");
											  
												String x1="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
												 if(((Minion)burned).isDivine()){ 
												    	x1 = x1+ "Minion Has a Divine Shield"+"\n";
												    }
												   
												    if(((Minion)burned).isTaunt()){
												    	x1 = x1+ "Taunt Minion"+"\n";
												    }
												    if(!((Minion)burned).isSleeping()){
												    	x1 = x1+ "Charge Minion"+"\n";
												    }
												    xBox(x1);		
										   }}else{
											xBox(e1.getMessage());//ps("sounds/TAN.wav");
											e1.printStackTrace();
									}
									}
					 }
						 
				}
				   
				   else { 
					   heroAttacked=true;
				   }
				 
			break;
			// 3alaya ana 
			case "Spell on hero":
				if (spellActive!=null) {
				   String s = spellActive.getName();
				   switch(s) {
				   case "Kill Command":try {
						model.getCurrentHero().castSpell((HeroTargetSpell)spellActive, model.getCurrentHero());
						ps("sounds/boom (1).wav");
						update();
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						update();
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
					};break;
				   case "Pyroblast" :try {
						model.getCurrentHero().castSpell((HeroTargetSpell)spellActive, model.getCurrentHero());
						ps("sounds/boom (1).wav");
						update();
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						update();
						xBox(e1.getMessage());//ps("sounds/TAN.wav");
						e1.printStackTrace();
					};break;
				   case "Divine Spirit":xBox("This spell can only be casted on a MINION !!");//ps("sounds/TAN.wav");
				   update();break;
				   case "Polymorph":xBox("This spell can only be casted on a MINION !!");//ps("sounds/TAN.wav");
				   update();break;
				   case "Seal of Champions":xBox("This spell can only be casted on a MINION !!");//ps("sounds/TAN.wav");
				   update();break;
				   case "Shadow Word: Death":xBox("This spell can only be casted on a MINION !!");//ps("sounds/TAN.wav");
				   update();break;
				   case "Siphon Soul":xBox("This spell can only be casted on a MINION !!");//ps("sounds/TAN.wav");
				   update();break;
				   case "Level Up!":xBox("This spell can only be casted on a FIELD!!");//ps("sounds/TAN.wav");
				   update();break;
				   
				   }
				   
			   }
				else if (useHeroPower) {
					 String name=model.getCurrentHero().getName();
					 
					 if (name.equals("Jaina Proudmoore")) {
						 try {
							((Mage)model.getCurrentHero()).useHeroPower(model.getCurrentHero());
							ps("sounds/boom (1).wav");
							update();
						} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException | FullHandException
								| FullFieldException | CloneNotSupportedException e1) {
							update();
							if (e1 instanceof FullHandException) {
							//	xBox(e1.getMessage());//ps("sounds/TAN.wav");
								Card burned=((FullHandException) e1).getBurned();
								if (burned instanceof Spell) {
								
									xBox(e1.getMessage());//ps("sounds/TAN.wav");
									
									xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
										
								}
							   if (burned instanceof Minion) {
								  xBox(e1.getMessage());//ps("sounds/TAN.wav");
								  
									String x1="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
									 if(((Minion)burned).isDivine()){ 
									    	x1 = x1+ "Minion Has a Divine Shield"+"\n";
									    }
									   
									    if(((Minion)burned).isTaunt()){
									    	x1 = x1+ "Taunt Minion"+"\n";
									    }
									    if(!((Minion)burned).isSleeping()){
									    	x1 = x1+ "Charge Minion"+"\n";
									    }
									    xBox(x1);		
							   }}else{
								xBox(e1.getMessage());//ps("sounds/TAN.wav");
								e1.printStackTrace();
						}}}
						 else  if (name.equals("Anduin Wrynn")) {
							 try {
									((Priest)model.getCurrentHero()).useHeroPower(model.getCurrentHero());
									ps("sounds/boom (1).wav");
									update();
								} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException | FullHandException
										| FullFieldException | CloneNotSupportedException e1) {
									update();
									if (e1 instanceof FullHandException) {
										//xBox(e1.getMessage());//ps("sounds/TAN.wav");
										Card burned=((FullHandException) e1).getBurned();
										if (burned instanceof Spell) {
										
											xBox(e1.getMessage());//ps("sounds/TAN.wav");
											
											xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
												
										}
									   if (burned instanceof Minion) {
										  xBox(e1.getMessage());//ps("sounds/TAN.wav");
										  
											String x1="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
											 if(((Minion)burned).isDivine()){ 
											    	x1 = x1+ "Minion Has a Divine Shield"+"\n";
											    }
											   
											    if(((Minion)burned).isTaunt()){
											    	x1 = x1+ "Taunt Minion"+"\n";
											    }
											    if(!((Minion)burned).isSleeping()){
											    	x1 = x1+ "Charge Minion"+"\n";
											    }
											    xBox(x1);		
									   }}else{
										xBox(e1.getMessage());//ps("sounds/TAN.wav");
										e1.printStackTrace();
				}}}}
				else if (attackerField!=null||attackedField!=null) {
					//ps("sounds/TAN.wav");
					xBox("A minion can not cast a spell !!!");
					update();
				}
					
			   else { 
				   spellOnHero=true;
			   }
				
			 
		       break;
			case "Spell on OPP. Field": if (spellActive!=null)
				if (!(spellActive instanceof FieldSpell)) {
					//ps("sounds/TAN.wav");
					xBox("This spell Cannot be Casted on a FIELD !!!");
					
					update();}
				else 
				try {
					model.getOpponent().castSpell((FieldSpell)(spellActive));
					ps("sounds/boom (1).wav");
					update();
				} catch (NotYourTurnException | NotEnoughManaException e1) {
					update();
					xBox(e1.getMessage());//ps("sounds/TAN.wav");
					e1.printStackTrace();
				}
			else spellOnField2=true;
				                          break;
			case "Spell on My Field":if (spellActive!=null)
				if (!(spellActive instanceof FieldSpell)) {
					//ps("sounds/TAN.wav");
					xBox("This spell Cannot be Casted on a FIELD !!!");
					update();
					}
				else 
				try {
					model.getCurrentHero().castSpell((FieldSpell)(spellActive));
					ps("sounds/boom (1).wav");
					update();
				} catch (NotYourTurnException | NotEnoughManaException e1) {
					update();
					xBox(e1.getMessage());//ps("sounds/TAN.wav");
					e1.printStackTrace();
				}
			else spellOnField1=true;
			break;
	}}}

public void hand(Hero hero){
	 ArrayList<JButton> handButtons=new ArrayList<JButton>();
   	 ArrayList<Card> h = hero.getHand();
   	 JPanel a= new JPanel();
   	 a.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.5f));
     JLabel heroInfo =new JLabel();
     JLabel heroInfo2 =new JLabel();    
     String text1="<html>";
     String textSpell="<html>";
     heroInfo.setOpaque(true);
     heroInfo2.setOpaque(true);
     heroInfo.setBackground(Color.black);
     heroInfo2.setBackground(Color.black);
     heroInfo.setForeground(Color.magenta);
     heroInfo2.setForeground(Color.magenta);
     
   	 if(hero.equals(model.getCurrentHero())){
   		JButton use=new JButton();
   	 	JButton end=new JButton();
   	 	JButton spell=new JButton();
   	 	textSpell+= "Spell on ME"+"<br/>";
   	 	textSpell+= "     OR"+"<br/>";
   	 	textSpell+= "UseHeroPower On ME"+"<br/>";
   	    textSpell+="</html>";
   	    spell.setText(textSpell);
   	 	spell.setName("Spell on hero");
   	    spell.setOpaque(true);
   	    spell.setBackground(Color.black);
	 	spell.setForeground(Color.MAGENTA);
	 	spell.setFont(new Font("Courier", Font.BOLD,13));
	 	spell.setBorderPainted(false);
   	 	use.setText("Use Hero Power");
   	 	use.setName("Use Hero Power");
   	 	use.setOpaque(true);
   	 	use.setBackground(Color.black);
   	 	use.setForeground(Color.MAGENTA);
   	    use.setBorderPainted(false);
   	 	end.setText("End Turn");
   	    end.setName("End Turn");
   	 	end.setPreferredSize(new Dimension(70,50));
   	 	
   	 	ImageIcon x=new ImageIcon("images/end.png");
   	 	end.setIcon(x);
   	 	use.setFont(new Font("Courier", Font.BOLD,13));
   	 	end.setFont(new Font("Courier", Font.BOLD,13));
   	    spell.addActionListener(this);
   	 	use.addActionListener(this);
   	    end.addActionListener(this);
   	    a.add(spell);
   	    a.add(use);
   	    a.add(end);
   		 
		   		 if(hero1.equals(hero)){
		    			text1 += "CurrentHero(1st Player)"+"<br/>" ;
		    		 }else{
		    			text1 += "CurrentHero(2nd Player)"+"<br/>" ; 
		    		 }
		   		 text1 += "Name:"+ hero.getName()+"<br/>" ;
		   		 text1 += "CurrentHP:" + hero.getCurrentHP() +"<br/>";
		   		 text1 += "Current Manacrystals:" + hero.getCurrentManaCrystals() +"<br/>";
		   		 text1 += "Total Manacrystals:" + hero.getTotalManaCrystals() +"<br/>";
		   		 text1 += "Remaining Cards in Deck:" + hero.getDeck().size() +"<br/>";
		         text1 += "</html>" ;
		         heroInfo.setText(text1);
		        
		         a.add(heroInfo);
		         view3.add(a);
		         
		         heroInfo2.setText("Hand =>");   
		         heroInfo2.setForeground(Color.MAGENTA);
		         a.add(heroInfo2);
		         view3.add(a);
		         
		         
	
   	 for (int i = 0; i < hero.getHand().size(); i++) {
   		        String text2 = "<html>";
				JButton jbh = new JButton();
				jbh.setName(i+"CH");
				JLabel text2label=new JLabel();
				text2label.setFont(new Font("Courier", Font.PLAIN,10));
				if(h.get(i) instanceof Minion ){					
					              		text2 = text2+ "Name:"+ h.get(i).getName()+"<br/>";
									    text2 = text2+ "ManaCost:"+ h.get(i).getManaCost() +"<br/>";
									    text2 = text2+ "Rarity:"+h.get(i).getRarity()+"<br/>"; 
									    text2 = text2+ "Attack:"+((Minion) h.get(i)).getAttack()+" "+"CurrentHP:" + ((Minion) h.get(i)).getCurrentHP()+"<br/>";
									    //text2 = text2+ "CurrentHP:" + ((Minion) h.get(i)).getCurrentHP()+"<br/>";
									    if(((Minion) h.get(i)).isDivine()){ 
									    	text2 = text2+ "Minion Has a Divine Shield"+"<br/>";
									    }
									   
									    if(((Minion) h.get(i)).isTaunt()){
									    	if(!((Minion) h.get(i)).isSleeping()){
										    	text2 = text2+ "Taunt & Charge Minion"+"<br/>";
									    	}else
										    		{text2 = text2+ "Taunt Minion"+"<br/>";
									    }}
									    if( !(((Minion) h.get(i)).isTaunt())  && !((Minion) h.get(i)).isSleeping()){
									    	text2 = text2+ "Charge Minion"+"<br/>";
									    }
									    text2=text2+"</html>";	
				}
				else{
					text2 = text2+ "Name:"+ h.get(i).getName()+"<br/>";
				    text2 = text2+ "ManaCost:"+ h.get(i).getManaCost() +"<br/>";
				    text2 = text2+ "Rarity:"+h.get(i).getRarity()+"<br/>"; 
				    text2=text2+"</html>";	     	
				}
				text2label.setText(text2);
				jbh.add(text2label);
				jbh.addActionListener(this);		
				handButtons.add(jbh);
				a.add(jbh);
			}view3.add(a);
   	 }else
   	 {          JButton ah=new JButton();
			   	textSpell+= "Attack Hero"+"<br/>";
			 	textSpell+= "    OR"+"<br/>";
			 	textSpell+= "Spell on Hero"+"<br/>";
			 	textSpell+="</html>";
			 	ah.setText(textSpell);
			 	ah.setName("Attack Hero");
			 	ah.setOpaque(true);
			 	ah.setBackground(Color.black);
			 	ah.setForeground(Color.MAGENTA);
			 	ah.setFont(new Font("Courier", Font.BOLD,15));
			 	ah.setBorderPainted(false);
			 	JButton spell=new JButton();
			    ah.addActionListener(this);
			    a.add(ah);
   		 
	   		 if(hero1.equals(hero)){
	   			text1 += "Opponent(1st Player)"+"<br/>" ;
	   		 }else{
	   			text1 += "Opponent(2nd Player)"+"<br/>" ; 
	   		 }
	   		 text1 += "Name:"+ hero.getName()+"<br/>" ;
	   		 text1 += "CurrentHP:" + hero.getCurrentHP() +"<br/>";
	   		 text1 += "Current Manacrystals:" + hero.getCurrentManaCrystals() +"<br/>";
	   		 text1 += "Total Manacrystals:" + hero.getTotalManaCrystals() +"<br/>";
	   		 text1 += "Cards in Hand:" + hero.getHand().size() +"<br/>";
	   		 text1 += "Remaining Cards in Deck:" + hero.getDeck().size() +"<br/>";
	         text1 += "</html>" ;
	         heroInfo.setText(text1);
	         a.add(heroInfo);
	         view3.add(a);
	         
	         heroInfo2.setText("Hand =>");   
	         
	         heroInfo2.setForeground(Color.MAGENTA);
	         a.add(heroInfo2);
	         view3.add(a);
	   		 
	   		 for (int i = 0; i < hero.getHand().size(); i++) {
					JLabel opHand = new JLabel();
					opHand.setPreferredSize(new Dimension(100,125));
					    //jbh.setFont(new Font("Courier", Font.PLAIN,13));
					opHand.setName(h.get(i).getName()+"OpponentHand");
					ImageIcon z=new ImageIcon("images/cardback_45.png");
					opHand.setIcon(z);
					a.add(opHand);
				}view3.add(a);
	   	 }
			view3.repaint();
			view3.revalidate();
    }

public void field(Hero hero){
	 ArrayList<JButton> handButtons=new ArrayList<JButton>();
  	 ArrayList<Minion> h = hero.getField();
  	 JPanel a= new JPanel();
  	 a.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.5f));
     JLabel heroInfo2 =new JLabel();  	
     heroInfo2.setOpaque(true);
     heroInfo2.setBackground(Color.black);
     if(hero.equals(model.getCurrentHero())){
				         heroInfo2.setText("Current Hero's Field =>");
				         heroInfo2.setForeground(Color.MAGENTA);
				         JButton spell=new JButton();
					   	 	spell.setText("Spell on My Field");
					   	 	spell.setName("Spell on My Field");
					   	    spell.setOpaque(true);
					   	    spell.setBackground(Color.black);
						 	spell.setForeground(Color.MAGENTA);
						 	spell.setFont(new Font("Courier", Font.BOLD,15));
						 	spell.setBorderPainted(false);
						 	spell.addActionListener(this);
						// 	a.add(spell);
				         a.add(heroInfo2);
				         view3.add(a);         
				         for (int i = 0; i < hero.getField().size(); i++) {
				   		        String text2 = "<html>";
								JButton jbh = new JButton();
								jbh.setName(i+"CF");
								JLabel text2label=new JLabel();
								text2label.setFont(new Font("Courier", Font.PLAIN,12));
													
									     text2 = text2+ "Name:"+ h.get(i).getName()+"<br/>";
										 text2 = text2+ "ManaCost:"+ h.get(i).getManaCost() +"<br/>";
										 text2 = text2+ "Rarity:"+h.get(i).getRarity()+"<br/>"; 
										 text2 = text2+ "Attack:"+ h.get(i).getAttack()+"<br/>";
										 text2 = text2+ "CurrentHP:" +  h.get(i).getCurrentHP()+"<br/>";
										 if( h.get(i).isDivine()){ 
													    	text2 = text2+ "Minion Has a Divine Shield"+"<br/>";
										 }
													   
										 if( h.get(i).isTaunt()){
													    	text2 = text2+ "Taunt Minion"+"<br/>";
										 }
										 if(!((Minion) h.get(i)).isSleeping()){
											 				text2 = text2+ "Charge Minion"+"<br/>";
										    }
										 if(((Minion) h.get(i)).isSleeping()){
								 				text2 = text2+ "Sleeping"+"<br/>";
								 				text2 = text2+ "Cannot Attack"+"<br/>";
							             }if(!((Minion) h.get(i)).isSleeping()){
								 				text2 = text2+ "Not Sleeping"+"<br/>";
								 				if(((Minion) h.get(i)).isAttacked()){
								 				text2 = text2+ "Cannot Attack"+"<br/>";
							                   }else
							                	   text2 = text2+ "Can Attack"+"<br/>";   
								 				}
										 text2=text2+"</html>";	
								text2label.setText(text2);
								jbh.add(text2label);
								jbh.addActionListener(this);		
								handButtons.add(jbh);
								a.add(jbh);
							}
				         view3.add(a);
  	 
     }else 
     {		heroInfo2.setText("Opponent's Field =>");
		  	heroInfo2.setForeground(Color.MAGENTA);
		  	JButton spell=new JButton();
	   	 	spell.setText("Spell on OPP. Field");
	   	 	spell.setName("Spell on OPP. Field");
	   	    spell.setOpaque(true);
	   	    spell.setBackground(Color.black);
		 	spell.setForeground(Color.MAGENTA);
		 	spell.setFont(new Font("Courier", Font.BOLD,15));
		 	spell.setBorderPainted(false);
		 	spell.addActionListener(this);
		 	//a.add(spell);
		    a.add(heroInfo2);
		    view3.add(a);         
		    for (int i = 0; i < hero.getField().size(); i++) {
				    String text2 = "<html>";
					JButton jbh = new JButton();
					jbh.setName(i+"OF");
					JLabel text2label=new JLabel();
					text2label.setFont(new Font("Courier", Font.PLAIN,12));
										
						     text2 = text2+ "Name:"+ h.get(i).getName()+"<br/>";
							 text2 = text2+ "ManaCost:"+ h.get(i).getManaCost() +"<br/>";
							 text2 = text2+ "Rarity:"+ h.get(i).getRarity()+"<br/>"; 
							 text2 = text2+ "Attack:"+ h.get(i).getAttack()+"<br/>";
							 text2 = text2+ "CurrentHP:" +  h.get(i).getCurrentHP()+"<br/>";
							 if( h.get(i).isDivine()){ 
										    	text2 = text2+ "Minion Has a Divine Shield"+"<br/>";
							 }
										   
							 if( h.get(i).isTaunt()){
										    	text2 = text2+ "Taunt Minion"+"<br/>";
							 }
							 
							 if(!((Minion) h.get(i)).isSleeping()){
							    	text2 = text2+ "Charge Minion"+"<br/>";
							    }
							 text2=text2+"</html>";	
							 text2label.setText(text2);
							 jbh.add(text2label);
							 jbh.addActionListener(this);		
							 handButtons.add(jbh);
							 a.add(jbh);
					}view3.add(a);
			  }
     	view3.repaint();
		view3.revalidate();}

public void update(){
	
	 if (model.getCurrentHero().getCurrentHP()<=0||model.getOpponent().getCurrentHP()<=0) {
		 winner();
			}
	 else{
	 attackerField=null;
	 attackedField=null;
	 heroAttacked=false;
	 spellActive=null;
	 spellOnHero=false;
	 useHeroPower=false;
	 spellOnField1=false;
	 spellOnField2=false;
	 view3.getContentPane().removeAll(); 
	 hand(model.getOpponent());
     field(model.getOpponent());
     field(model.getCurrentHero());
     hand(model.getCurrentHero());
     view3.revalidate();
	 view3.repaint();
	 }

	 
}

public void winner(){
     ps("sounds/tada.wav");
	 String x ="";
	 if(model.getCurrentHero().equals(hero1)){
		if( model.getCurrentHero().getCurrentHP()<=0)
		   x ="Congrats "+ "Player2 "+model.getOpponent().getName() + " You Won!!!!";
	else 
			x ="Congrats "+ "Player1 "+model.getCurrentHero().getName() + " You Won!!!!";
			
	 }
	 else{
			if( model.getCurrentHero().getCurrentHP()<=0)
				   x ="Congrats "+ "Player1 "+model.getOpponent().getName() + " You Won!!!!";
				else 
					x ="Congrats "+ "Player2 "+model.getCurrentHero().getName() + " You Won!!!!";
					
			 }
	 
	 view3.getContentPane().removeAll();
	 xBox(x);
	 view3.revalidate();
	 view3.repaint();
	/* JPanel jp=new JPanel();
	 JLabel again=new JLabel();
	 JLabel end=new JLabel();
	 again.setText("Play Again :) ");
	 again.setBackground(Color.black);
	 again.setForeground(Color.magenta);
	 end.setText("Exit :( ");
	 end.setBackground(Color.black);
	 end.setForeground(Color.magenta); 
     JButton a=new JButton();
	 a.add(again);
	 a.setName("again");
	 a.addActionListener(this);
	 JButton B=new JButton();
	 B.addActionListener(this);
	 B.setName("gameOver");
	 B.add(end);
	 jp.add(a);
	 jp.add(B);
	 view3.add(jp);
	 view3.repaint();
	 view3.revalidate();
	 */
}

public void myHand(Card q) {
	if(spellActive!=null) {
		//ps("sounds/TAN.wav");
		xBox("Spells can not be casted on a minion in your hand or another spell !!!");
		update();
	}
	else {
	 if (q instanceof Minion ){
		 
	 
			try {
				model.getCurrentHero().playMinion((Minion)q);
				ps("sounds/wand.wav");
				update();
			} catch (NotYourTurnException | NotEnoughManaException
					| FullFieldException e1) {
				update();
				xBox(e1.getMessage());//ps("sounds/TAN.wav");
				e1.printStackTrace();
			}	
		}
	 else
			if (q instanceof Spell)
			
		 { 
	String s=((Spell)q).getName();
    switch(s) {
    case"Curse of Weakness": if ((Spell)q instanceof AOESpell)
							try {
								model.getCurrentHero().castSpell((AOESpell)q, model.getOpponent().getField());
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException e1) {
								update();
								xBox(e1.getMessage());//ps("sounds/TAN.wav");
								e1.printStackTrace();
							} break;
    case "Divine Spirit":  if ((Spell)q instanceof MinionTargetSpell) {
				    	 if (model.getCurrentHero().getCurrentManaCrystals()<((Spell)q).getManaCost()) {
				    		 xBox("I don't have enough mana !!");
				    		 update();
				    		 //ps("sounds/TAN.wav");
				    		 }
				    	 else if (heroAttacked||spellOnHero||spellOnField1||spellOnField2)
				    		 {//ps("sounds/TAN.wav");
				    		 xBox("This spell is only casted on MINIONS !!!");
				    		 update();
				    		 }
				    	 else {
    						spellActive = (Spell)q;
    						//xBox(spellActive.getName());
   	                      if (attackerField!=null) {
					    	 try {
								model.getCurrentHero().castSpell((MinionTargetSpell)q, attackerField);
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
								update();
								xBox(e1.getMessage());//ps("sounds/TAN.wav");
								e1.printStackTrace();
							}
   	                      }
   	                      else  if (attackedField!=null) {
	                    		  try {
									model.getCurrentHero().castSpell((MinionTargetSpell)q, attackedField);
									ps("sounds/boom (1).wav");
									update();
								} catch (NotYourTurnException | NotEnoughManaException
										| InvalidTargetException e1) {
									update();
									xBox(e1.getMessage());//ps("sounds/TAN.wav");
									e1.printStackTrace();
								}
									
	                    	  }
     	
				    	 } 
					    		 
   	                      }
                           break;
    case "Flamestrike":  if ((Spell)q instanceof AOESpell)
							try {
								model.getCurrentHero().castSpell((AOESpell)q, model.getOpponent().getField());
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException e1) {
								update();
								xBox(e1.getMessage());//ps("sounds/TAN.wav");
								e1.printStackTrace();
							} break;
    case "Holy Nova":  if ((Spell)q instanceof AOESpell)
							try {
								model.getCurrentHero().castSpell((AOESpell)q, model.getOpponent().getField());
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException e1) {
								update();
								xBox(e1.getMessage());//ps("sounds/TAN.wav");
								e1.printStackTrace();
							} 
    						break;
  					
    case "Kill Command": if ((Spell)q instanceof MinionTargetSpell) {
			    	if (model.getCurrentHero().getCurrentManaCrystals()<((Spell)q).getManaCost()) {
			    	  //ps("sounds/TAN.wav");
			   		 xBox("I don't have enough mana !!");
			   		 update();
			    	}
			    	/*else if (spellOnField1||spellOnField2)
		    		 {
			    	//ps("sounds/TAN.wav");
			    	xBox("This spell can not be casted on a FIELD  !!!");
		    		 update();
		    		 }*/
			   	 else {
						spellActive = (Spell)q;
	                      if (attackedField!=null) {
				    	 try {
							model.getCurrentHero().castSpell((MinionTargetSpell)q, attackedField);
							ps("sounds/boom (1).wav");
							update();
						} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
							update();
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							e1.printStackTrace();
						}
				    	 }
	                      else  if (attackerField!=null) {
                   		  try {
								model.getCurrentHero().castSpell((MinionTargetSpell)q, attackerField);
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException
									| InvalidTargetException e1) {
								update();
								xBox(e1.getMessage());//ps("sounds/TAN.wav");
								e1.printStackTrace();
							}
								
                   	  }
	                      
				    	
                       }
			    	}
					     else if ((Spell)q instanceof HeroTargetSpell) {
					    	 if (model.getCurrentHero().getCurrentManaCrystals()<((Spell)q).getManaCost()) {
					    		 //ps("sounds/TAN.wav");
					    		 xBox("I don't have enough mana !!");
					    		 update();
					    		 }
					    	 else if (spellOnField1||spellOnField2)
				    		 {   //ps("sounds/TAN.wav");
					    		 xBox("This spell can not be casted on a FIELD  !!!");
				    		     update();
				    		 }
					    	 else {
	    						spellActive = (Spell)q;
								if (heroAttacked) {
								try {
									model.getCurrentHero().castSpell((HeroTargetSpell)q,model.getOpponent());
									ps("sounds/boom (1).wav");
									update();
								} catch (NotYourTurnException | NotEnoughManaException e) {
									update();
									xBox(e.getMessage());//ps("sounds/TAN.wav");
									e.printStackTrace();
								}
								
								}
								else if(spellOnHero) {
									try {
										model.getCurrentHero().castSpell((HeroTargetSpell)q,model.getCurrentHero());
										ps("sounds/boom (1).wav");
										update();
									} catch (NotYourTurnException | NotEnoughManaException e) {
										update();
										xBox(e.getMessage());//ps("sounds/TAN.wav");
										e.printStackTrace();
									}
									
								}
								
						    	 
						    		 }
							
	                    }
			     
                         break;
                       
    case "Level Up!":  if ((Spell)q instanceof FieldSpell)
					    	if (model.getCurrentHero().getCurrentManaCrystals()<((Spell)q).getManaCost()) {
					    		//ps("sounds/TAN.wav");
							 xBox("I don't have enough mana !!");
							 update();
							 }
					    	else if (heroAttacked||spellOnHero||(attackedField!=null)||(attackerField!=null)) {
					    		//ps("sounds/TAN.wav");
					    		xBox("This spell is casted (automatically) on FIELDS !!!");
					    		update();
					    	}
						 else {
						//	spellActive = (Spell)q;
						//if (spellOnField1) {
							try {
								model.getCurrentHero().castSpell((FieldSpell)q);
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException e) {
								update();
								xBox(e.getMessage());//ps("sounds/TAN.wav");
								e.printStackTrace();
							}
						}
					/*	else if (spellOnField2) {
							try {
								model.getOpponent().castSpell((FieldSpell)q);
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException e) {
								update();
								xBox(e.getMessage());//ps("sounds/TAN.wav");
								e.printStackTrace();
							}
						
						}
					 
				    	 
				    		 }*/
							break;
    case "Multi-Shot": if ((Spell)q instanceof AOESpell)
							try {
								model.getCurrentHero().castSpell((AOESpell)q, model.getOpponent().getField());
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException e1) {
								update();
								xBox(e1.getMessage());//ps("sounds/TAN.wav");
								e1.printStackTrace();
							} 
    						break;
    case "Polymorph":  if ((Spell)q instanceof MinionTargetSpell) {
				    	if (model.getCurrentHero().getCurrentManaCrystals()<((Spell)q).getManaCost()) {
				    		//ps("sounds/TAN.wav");
				   		 xBox("I don't have enough mana !!");
				   		 update();}
				    	else if (heroAttacked||spellOnHero||spellOnField1||spellOnField2) {
				    		 xBox("This spell is only casted on MINIONS !!!");
				    		 //ps("sounds/TAN.wav");
				    		 }
				   	 else {
							spellActive = (Spell)q;
				    	if (attackerField!=null) {
					    	 try {
								model.getCurrentHero().castSpell((MinionTargetSpell)q, attackerField);
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
								update();
								xBox(e1.getMessage());//ps("sounds/TAN.wav");
								e1.printStackTrace();
							}
				            }
				            else  if (attackedField!=null) {
				       		  try {
									model.getCurrentHero().castSpell((MinionTargetSpell)q, attackedField);
									ps("sounds/boom (1).wav");
									update();
								} catch (NotYourTurnException | NotEnoughManaException
										| InvalidTargetException e1) {
									update();
									xBox(e1.getMessage());//ps("sounds/TAN.wav");
									e1.printStackTrace();
								}
									
				       	  }
				
				   	 }
				    	}
				          break;

    case "Pyroblast": if ((Spell)q instanceof MinionTargetSpell) {
				    	if (model.getCurrentHero().getCurrentManaCrystals()<((Spell)q).getManaCost()) {
				   		 xBox("I don't have enough mana !!");
				   		 update();
				   		 //ps("sounds/TAN.wav");
				   		 }
				    	/* else if (spellOnField1||spellOnField2)
			    		 {    //ps("sounds/TAN.wav");
				    		 xBox("This spell can not be casted on a FIELD  !!!");
			    		 update();
			    		 }*/
				   	 else {
							spellActive = (Spell)q;
			    	 if (attackedField!=null) {
				    	 try {
							model.getCurrentHero().castSpell((MinionTargetSpell)q, attackedField);
							ps("sounds/boom (1).wav");
							update();
							//xBox("try");
						} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
							update();
							//xBox("catch");
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							e1.printStackTrace();
						}
				    	 }
			              else  if (attackerField!=null) {
			       		  try {
								model.getCurrentHero().castSpell((MinionTargetSpell)q, attackerField);
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException
									| InvalidTargetException e1) {
								update();
								xBox(e1.getMessage());//ps("sounds/TAN.wav");
								e1.printStackTrace();
							}
								
			       	  }
			              
				    	
				    	 
				    		 }
			           }
					     else {
					    	 if ((Spell)q instanceof HeroTargetSpell) {
					    		 if (model.getCurrentHero().getCurrentManaCrystals()<((Spell)q).getManaCost()) {
						    		 xBox("I don't have enough mana !!");
						    		update();
						    		 //ps("sounds/TAN.wav");
						    		 }
					    		/* else if (spellOnField1||spellOnField2)
					    		 {	//ps("sounds/TAN.wav");
					    			 xBox("This spell can not be casted on a FIELD  !!!");
					    		    update();
					    		 }*/
						    	 else {
		    						spellActive = (Spell)q;
										if (heroAttacked) {
										try {
											model.getCurrentHero().castSpell((HeroTargetSpell)q,model.getOpponent());
											ps("sounds/boom (1).wav");
											update();
										} catch (NotYourTurnException | NotEnoughManaException e) {
											update();
											xBox(e.getMessage());//ps("sounds/TAN.wav");
											e.printStackTrace();
										}
										
										}
										else if(spellOnHero) {
											try {
												model.getCurrentHero().castSpell((HeroTargetSpell)q,model.getCurrentHero());
												ps("sounds/boom (1).wav");
												update();
											} catch (NotYourTurnException | NotEnoughManaException e) {
												update();
												xBox(e.getMessage());//ps("sounds/TAN.wav");
												e.printStackTrace();
											}
											
										}
										
							    		 }
									
			                    }
					     }
						
			             break;
    case "Seal of Champions": if ((Spell)q instanceof MinionTargetSpell) {
					    	if (model.getCurrentHero().getCurrentManaCrystals()<((Spell)q).getManaCost()) {
					    		//ps("sounds/TAN.wav");
					   		 xBox("I don't have enough mana !!");
					   		 update();}
					    	else if (heroAttacked||spellOnHero||spellOnField1||spellOnField2) {
					    		//ps("sounds/TAN.wav");
					    		 xBox("This spell is only casted on MINIONS !!!");
					    		 update();
					    		 }
					   	 else {
					   		 
								spellActive = (Spell)q;
				    	if (attackerField!=null) {
					    	 try {
								model.getCurrentHero().castSpell((MinionTargetSpell)q, attackerField);
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
								update();
								xBox(e1.getMessage());//ps("sounds/TAN.wav");
								e1.printStackTrace();
							}
				           }
				           else  if (attackedField!=null) {
				      		  try {
									model.getCurrentHero().castSpell((MinionTargetSpell)q, attackedField);
									ps("sounds/boom (1).wav");
									update();
								} catch (NotYourTurnException | NotEnoughManaException
										| InvalidTargetException e1) {
									update();
									xBox(e1.getMessage());//ps("sounds/TAN.wav");
									e1.printStackTrace();
								}
									
				      	  }
				 
					    
					    	 
					    		 }
				    	}
				         break;
    case "Shadow Word: Death":  if ((Spell)q instanceof MinionTargetSpell) {
				    	if (model.getCurrentHero().getCurrentManaCrystals()<((Spell)q).getManaCost()) {
				    		//ps("sounds/TAN.wav");
				   		 xBox("I don't have enough mana !!");
				   		 update();}
				    	else if (heroAttacked||spellOnHero||spellOnField1||spellOnField2) {
				    		//ps("sounds/TAN.wav");
				    		 xBox("This spell is only casted on MINIONS !!!");
				    		 update();}
				   	 else {
							spellActive = (Spell)q;
				    	if (attackerField!=null) {
					    	 try {
								model.getCurrentHero().castSpell((MinionTargetSpell)q, attackerField);
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
								update();
								xBox(e1.getMessage());//ps("sounds/TAN.wav");
								e1.printStackTrace();
							}
				           }
				           else  if (attackedField!=null) {
				      		  try {
									model.getCurrentHero().castSpell((MinionTargetSpell)q, attackedField);
									ps("sounds/boom (1).wav");
									update();
								} catch (NotYourTurnException | NotEnoughManaException
										| InvalidTargetException e1) {
									update();
									xBox(e1.getMessage());//ps("sounds/TAN.wav");
									e1.printStackTrace();
								}
									
				      	  }
				
					    	 
					    	 
					    		 }
				    	}
				         break;

    case "Siphon Soul": if ((Spell)q instanceof LeechingSpell) {
					    	if (model.getCurrentHero().getCurrentManaCrystals()<((Spell)q).getManaCost()) {
					    	 //ps("sounds/TAN.wav");
					   		 xBox("I don't have enough mana !!");
					   		 update();}
					    	 else if (spellOnHero||heroAttacked||spellOnField1||spellOnField2)
				    		 {   //ps("sounds/TAN.wav");
					    		 xBox("This spell can only be casted on a MINION !!!");
				    		    update();
				    		 }
					   	 else {
								spellActive = (Spell)q;
	                      if (attackedField!=null) {
							try {
								model.getCurrentHero().castSpell((LeechingSpell)q, attackedField);
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException e1) {
								update();
								xBox(e1.getMessage());//ps("sounds/TAN.wav");
								e1.printStackTrace();
							}
				    	 }
	                      else if (attackerField!=null) {
	                    		try {
									model.getCurrentHero().castSpell((LeechingSpell)q, attackerField);
									ps("sounds/boom (1).wav");
									update();
								} catch (NotYourTurnException | NotEnoughManaException e) {
									update();
									xBox(e.getMessage());//ps("sounds/TAN.wav");
									e.printStackTrace();
								}
	                      } 
				    	
				    	 
				    		 }
                       }
						break;
    case "Twisting Nether":  if ((Spell)q instanceof AOESpell)
							try {
								model.getCurrentHero().castSpell((AOESpell)q, model.getOpponent().getField());
								ps("sounds/boom (1).wav");
								update();
							} catch (NotYourTurnException | NotEnoughManaException e1) {
								update();
								xBox(e1.getMessage());//ps("sounds/TAN.wav");
								e1.printStackTrace();
							} 
    						break;
    }
}
	 }
}
public void currentField(Minion w) {
	if (attackerField!=null) {
		//ps("sounds/TAN.wav");
		xBox("You can not attack a friendly minion");
		update();
		}
	else {
	attackerField = w;	
	//xBox(spellActive.getName());
	 if (attackedField!=null) {
		try {
			model.getCurrentHero().attackWithMinion(attackerField, attackedField);
			ps("sounds/boom (1).wav");
			attackerField=null;
			attackedField=null;
			update();
		} catch (CannotAttackException | NotYourTurnException | TauntBypassException
				| InvalidTargetException | NotSummonedException e2) {
			update();
			xBox(e2.getMessage());//ps("sounds/TAN.wav");
			e2.printStackTrace();
		}}
	 else if (heroAttacked) {
		try {
			model.getCurrentHero().attackWithMinion(attackerField, model.getOpponent());
			ps("sounds/boom (1).wav");
			heroAttacked=false;
			attackerField=null;
			update();
			
			
		} catch (CannotAttackException | NotYourTurnException | TauntBypassException
				| NotSummonedException | InvalidTargetException e3) {
			update();
			xBox(e3.getMessage());//ps("sounds/TAN.wav");
			e3.printStackTrace();
		}}
	 else if(spellActive!=null) {
		 //xBox("I'm working XD ");
		 String s =spellActive.getName(); 
		 switch(s) {
		 case "Divine Spirit": try {
							model.getCurrentHero().castSpell((MinionTargetSpell)spellActive, attackerField);
							ps("sounds/boom (1).wav");
							update();
						} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
							update();
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							e1.printStackTrace();
						}break;
		 case "Seal of Champions": try {
							model.getCurrentHero().castSpell((MinionTargetSpell)spellActive, attackerField);
							ps("sounds/boom (1).wav");
							update();
						} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
							update();
							xBox(e1.getMessage());//ps("sounds/TAN.wav");
							e1.printStackTrace();
						}break;
		 case "Kill Command":try {
							model.getCurrentHero().castSpell((MinionTargetSpell)spellActive, attackerField);
							ps("sounds/boom (1).wav");
							update();
						} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e) {
							update();
							xBox(e.getMessage());//ps("sounds/TAN.wav");
							e.printStackTrace();
						}break;
		 case "Polymorph":try {
							model.getCurrentHero().castSpell((MinionTargetSpell)spellActive, attackerField);
							ps("sounds/boom (1).wav");			
							update();
						} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e) {
							update();
							xBox(e.getMessage());//ps("sounds/TAN.wav");
							e.printStackTrace();
						}break;
		 case "Pyroblast":try {
						model.getCurrentHero().castSpell((MinionTargetSpell)spellActive, attackerField);
						ps("sounds/boom (1).wav");
						update();
					} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e) {
						update();
						xBox(e.getMessage());//ps("sounds/TAN.wav");
						e.printStackTrace();
					}break;
		 case "Shadow Word: Death":try {
						model.getCurrentHero().castSpell((MinionTargetSpell)spellActive, attackerField);
						ps("sounds/boom (1).wav");
						update();
					} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e) {
						update();
						xBox(e.getMessage());//ps("sounds/TAN.wav");
						e.printStackTrace();
					}break;
		 case "Siphon Soul":
			try {
				model.getCurrentHero().castSpell((LeechingSpell)spellActive, attackerField);
				ps("sounds/boom (1).wav");
				update();
			} catch (NotYourTurnException | NotEnoughManaException e) {
				update();
				xBox(e.getMessage());//ps("sounds/TAN.wav");
				e.printStackTrace();
			}
					break;
		 case "Level Up!"://ps("sounds/TAN.wav");xBox("This spell can only be casted on a FIELD !!!");update();break;
		 }
	 }
	 else if (useHeroPower) {
		 
		 String name=model.getCurrentHero().getName();
		
		// else {
		 if (name.equals("Jaina Proudmoore")) {
			 try {
				((Mage)model.getCurrentHero()).useHeroPower(attackerField);
				ps("sounds/boom (1).wav");
				update();
			} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException | FullHandException
					| FullFieldException | CloneNotSupportedException e) {
				update();
				if (e instanceof FullHandException) {
				//	xBox(e.getMessage());//ps("sounds/TAN.wav");
					Card burned=((FullHandException) e).getBurned();
					if (burned instanceof Spell) {
					
						xBox(e.getMessage());//ps("sounds/TAN.wav");
						
						xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
							
					}
				   if (burned instanceof Minion) {
					  xBox(e.getMessage());//ps("sounds/TAN.wav");
					  
						String x1="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
						 if(((Minion)burned).isDivine()){ 
						    	x1 = x1+ "Minion Has a Divine Shield"+"\n";
						    }
						   
						    if(((Minion)burned).isTaunt()){
						    	x1 = x1+ "Taunt Minion"+"\n";
						    }
						    if(!((Minion)burned).isSleeping()){
						    	x1 = x1+ "Charge Minion"+"\n";
						    }
						    xBox(x1);		
				   }}else{
					xBox(e.getMessage());//ps("sounds/TAN.wav");
					e.printStackTrace();
			}
			}
			 }
			 else  if (name.equals("Anduin Wrynn")) {
				 try {
						((Priest)model.getCurrentHero()).useHeroPower(attackerField);
						ps("sounds/boom (1).wav");
						update();
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException | FullHandException
							| FullFieldException | CloneNotSupportedException e) {
						update();
						if (e instanceof FullHandException) {
							//xBox(e.getMessage());//ps("sounds/TAN.wav");
							Card burned=((FullHandException) e).getBurned();
							if (burned instanceof Spell) {
							
								xBox(e.getMessage());//ps("sounds/TAN.wav");
								
								xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
									
							}
						   if (burned instanceof Minion) {
							  xBox(e.getMessage());//ps("sounds/TAN.wav");
							  
								String x1="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
								 if(((Minion)burned).isDivine()){ 
								    	x1 = x1+ "Minion Has a Divine Shield"+"\n";
								    }
								   
								    if(((Minion)burned).isTaunt()){
								    	x1 = x1+ "Taunt Minion"+"\n";
								    }
								    if(!((Minion)burned).isSleeping()){
								    	x1 = x1+ "Charge Minion"+"\n";
								    }
								    xBox(x1);		
						   }}else{
							xBox(e.getMessage());//ps("sounds/TAN.wav");
							e.printStackTrace();
					}
					}
	 }
		 
}//-}
	 else if (spellOnHero) {
		 //ps("sounds/TAN.wav");
			xBox("A minion can not cast a spell !!!");
			update();
		}
		
	 }
	
	}
public void opponentField(Minion r) {

	attackedField = r;
	 try {if (attackerField!=null) {
			model.getCurrentHero().attackWithMinion(attackerField, attackedField);
			ps("sounds/boom (1).wav");
		attackedField=null;
		attackerField=null;
		update();
		}
	} catch (CannotAttackException | NotYourTurnException | TauntBypassException | InvalidTargetException
			| NotSummonedException e1) {
		update();
		xBox(e1.getMessage());//ps("sounds/TAN.wav");
		e1.printStackTrace();
	}
	 if (spellActive!=null) {
		 //xBox("I'm working");
		 String s = spellActive.getName();
		 switch(s) {
		 case "Kill Command":try {
				model.getCurrentHero().castSpell((MinionTargetSpell)spellActive, attackedField);
				ps("sounds/boom (1).wav");
				update();
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
				update();
				xBox(e1.getMessage());//ps("sounds/TAN.wav");
				e1.printStackTrace();
			}
		 break;
		 case "Seal of Champions": try {
				model.getCurrentHero().castSpell((MinionTargetSpell)spellActive, attackedField);
				ps("sounds/boom (1).wav");
				update();
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
				update();
				xBox(e1.getMessage());//ps("sounds/TAN.wav");
				e1.printStackTrace();
			}break;
		 case "Polymorph": try {
				model.getCurrentHero().castSpell((MinionTargetSpell)spellActive, attackedField);
				ps("sounds/boom (1).wav");
				update();
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
				update();
				xBox(e1.getMessage());//ps("sounds/TAN.wav");
				e1.printStackTrace();
			}
		 break;
		 case "Pyroblast":try {
				model.getCurrentHero().castSpell((MinionTargetSpell)spellActive, attackedField);
				ps("sounds/boom (1).wav");
				update();
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
				update();
				xBox(e1.getMessage());//ps("sounds/TAN.wav");
				e1.printStackTrace();
			}
		 break;
		 case "Shadow Word: Death":try {
				model.getCurrentHero().castSpell((MinionTargetSpell)spellActive, attackedField);
				ps("sounds/boom (1).wav");
				update();
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
				update();
				xBox(e1.getMessage());//ps("sounds/TAN.wav");
				e1.printStackTrace();
			}
		 break;
		 case "Siphon Soul":try {
				model.getCurrentHero().castSpell((LeechingSpell)spellActive, attackedField);
				ps("sounds/boom (1).wav");
				update();
			} catch (NotYourTurnException | NotEnoughManaException e1) {
				update();
				xBox(e1.getMessage());//ps("sounds/TAN.wav");
				e1.printStackTrace();
			}break;
		 case "Divine Spirit":
			 
			try {
				model.getCurrentHero().castSpell((MinionTargetSpell)spellActive, attackedField);
				ps("sounds/boom (1).wav");
				update();
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e) {
				update();
				xBox(e.getMessage());//ps("sounds/TAN.wav");
				e.printStackTrace();
			}
					 break;
		 case "Level Up!"://ps("sounds/TAN.wav");xBox("This spell can only be casted on a FIELD !!!");update();break;
		
		 }
		 }
		 

	 else if (useHeroPower) {
		 String name=model.getCurrentHero().getName();
		 if (name.equals("Jaina Proudmoore")) {
			 try {
				((Mage)model.getCurrentHero()).useHeroPower(attackedField);
				ps("sounds/boom (1).wav");
				update();
			} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException | FullHandException
					| FullFieldException | CloneNotSupportedException e) {
				update();
				if (e instanceof FullHandException) {
					//xBox(e.getMessage());//ps("sounds/TAN.wav");
					Card burned=((FullHandException) e).getBurned();
					if (burned instanceof Spell) {
					
						xBox(e.getMessage());//ps("sounds/TAN.wav");
						
						xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
							
					}
				   if (burned instanceof Minion) {
					  xBox(e.getMessage());//ps("sounds/TAN.wav");
					  
						String x1="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
						 if(((Minion)burned).isDivine()){ 
						    	x1 = x1+ "Minion Has a Divine Shield"+"\n";
						    }
						   
						    if(((Minion)burned).isTaunt()){
						    	x1 = x1+ "Taunt Minion"+"\n";
						    }
						    if(!((Minion)burned).isSleeping()){
						    	x1 = x1+ "Charge Minion"+"\n";
						    }
						    xBox(x1);		
				   }}else{
					xBox(e.getMessage());//ps("sounds/TAN.wav");
					e.printStackTrace();
			}
			}
			 }
			 else  if (name.equals("Anduin Wrynn")) {
				 try {
						((Priest)model.getCurrentHero()).useHeroPower(attackedField);
						ps("sounds/boom (1).wav");
						update();
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException | FullHandException
							| FullFieldException | CloneNotSupportedException e) {
						update();
						if (e instanceof FullHandException) {
						//	xBox(e.getMessage());//ps("sounds/TAN.wav");
							Card burned=((FullHandException) e).getBurned();
							if (burned instanceof Spell) {
							
								xBox(e.getMessage());//ps("sounds/TAN.wav");
								
								xBox("Burned Card is : "+"\n"+"name :" +((Spell)burned).getName() +"\n"+"ManaCost: "+((Spell)burned).getManaCost()+"\n"+"Rarity: "+((Spell)burned).getRarity());
									
							}
						   if (burned instanceof Minion) {
							  xBox(e.getMessage());//ps("sounds/TAN.wav");
							  
								String x1="Burned Card is : "+"\n"+"name :" +((Minion)burned).getName() +"\n"+"ManaCost: "+((Minion)burned).getManaCost()+"\n"+"Rarity: "+((Minion)burned).getRarity()+"\n"+"Attack: "+((Minion)burned).getAttack()+"\n"+"CurrentHP:  "+((Minion)burned).getCurrentHP()+"\n";
								 if(((Minion)burned).isDivine()){ 
								    	x1 = x1+ "Minion Has a Divine Shield"+"\n";
								    }
								   
								    if(((Minion)burned).isTaunt()){
								    	x1 = x1+ "Taunt Minion"+"\n";
								    }
								    if(!((Minion)burned).isSleeping()){
								    	x1 = x1+ "Charge Minion"+"\n";
								    }
								    xBox(x1);		
						   }}else{
							xBox(e.getMessage());//ps("sounds/TAN.wav");
							e.printStackTrace();
				}}} 
			 else if (spellOnHero) {
				 //ps("sounds/TAN.wav");
					xBox("A minion can not cast a spell !!!");
					update();
				}
				
		 }
	 }

public void ps(String filepath) {
	try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
		Clip sound = AudioSystem.getClip();
		sound.open(audioInputStream);
		sound.start();
		/*if(filepath.equals("sounds/TAN1.wav")){
			FloatControl floatControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
			double gain = 0.1;
			float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
			floatControl.setValue(dB);
		}*/
		if(filepath.equals("sounds/Background-Music.wav")){
			sound.loop(15);
		}
	} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
		e.printStackTrace();
	}
}


public static void main(String [] args) throws FullHandException, CloneNotSupportedException  {		 
	 new controller();
}
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	//@Override
//	public void onGameOver() {
//		// TODO Auto-generated method stub
//		
//	}
	/*
	public void psbc(String filepath,boolean bc) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
			Clip sound = AudioSystem.getClip();
			sound.open(audioInputStream);
			sound.start();
			sound.loop(2);
	        if(bc){
				sound.stop();
				//sound.loop();
	        }
			}catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}*/
}
