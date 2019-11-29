package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import regras.*;
 
public class PNBatalhaNaval extends JPanel implements Observer {
	double xIni=40.0,yIni=40.0,xIni2=800.0,larg=30.0,alt=30.0,espLinha=5.0;
	int iClick,jClick;
	int numAcertos = 0;
	Celula tab1[][]=new Celula[15][15];
	Celula tab2[][]=new Celula[15][15];
	
	
	
	JMenuBar mb= new JMenuBar();  
    JMenu menu=new JMenu("Menu");  

    JMenuItem i1=new JMenuItem("Load Game");  
    JMenuItem i2=new JMenuItem("Save Game");  
	
	
	Jogador j1;
	Jogador j2;
	//Jogador jogadores [] ;
	Line2D.Double ln1[]=new Line2D.Double[32];
	Line2D.Double ln2[]=new Line2D.Double[32];
	Fachada ctrl;
	int vez = -1;
	
	JTextField nameTextField = new JTextField(20);
	JTextField nameTextField2 = new JTextField(20);
    JButton buttonInicio = new JButton("Start");
    JButton buttonInicioAtaque = new JButton("Start Attack");
    JButton saveButton = new JButton("Save Game");
    JButton loadButton = new JButton("Load Game");
    JLabel attackLabel = new JLabel("AAAAAAAAAAAAAAAAAAAAAAAAA");

    Battlefield BF1;
    Battlefield BF2;
    JPanel container;
    
    
    
    Peca vPecas1[];
    Peca vPecas2[];
    
    boolean jog1Posicionado = false, jog2Posicionado = false, attackEnded = true;
	

	public PNBatalhaNaval(Fachada f) {
		ctrl = f;
		Fachada.getFachada().register(this);
		BF1 = new Battlefield(xIni, yIni, (short)1, Fachada.getFachada());
		BF2 = new Battlefield(xIni, yIni, (short)2, Fachada.getFachada());
		BF1.addObserver(this);
		BF2.addObserver(this);
		
		j1 = Jogador.criaJogador("stub", 1);
		j2 = Jogador.criaJogador("stub", 2);
		//jogadores = new Jogador [] {j1,j2};
		container = new JPanel(new GridLayout(2,1));
		container.setSize(1400, 700);
		container.setLayout(null);
		container.setLocation(0, 0);

		this.setPecas();
		
		
		buttonInicio.addActionListener(new IntroButton());
        buttonInicio.setBounds(670, 450, 100, 40);
        buttonInicioAtaque.addActionListener(new AtaqueButton());
        buttonInicioAtaque.setBounds(600, 550, 100, 40);
        saveButton.addActionListener(new SaveButton());
        saveButton.setBounds(600, 600, 100, 40);
        loadButton.addActionListener(new LoadButton());
        loadButton.setBounds(670, 500, 100, 40);
        		
        		
		menu.add(i1);
	    
	    menu.add(i2);
	    mb.add(menu);  
	    ((JFrame) SwingUtilities.getWindowAncestor(this)).setJMenuBar(mb);  
        	    
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;
		
		if(j1.getMyName() == "stub" || j2.getMyName() == "stub") {
			
			g2d.drawString("Jogador 1:", 650, 350);
			g2d.drawString("Jogador 2:", 650, 400);
			nameTextField.setBounds(715, 335, 100, 25);
	        nameTextField.setFont(nameTextField.getFont().deriveFont(15f));

			nameTextField2.setBounds(715, 385, 100, 25); 
	        nameTextField2.setFont(nameTextField.getFont().deriveFont(15f));

	        
	        add(nameTextField);
	        add(nameTextField2);
	        add(buttonInicio);
	        add(loadButton);
		}
		else {
			remove(buttonInicio);
			remove(nameTextField);
			remove(nameTextField2);
			remove(loadButton);
			// ADD TABULEIROS
			
			if(!jog1Posicionado) {
				// ADD TABULEIROS
				BF1.setBounds(700,0, 700, 1000);
				if(BF1.getParent() != container) {
					container.add(BF1,15);					
				}
				else {
					container.repaint();
					BF1.repaint();
				}
			}
			
			else if (!jog2Posicionado) {
				container.remove(BF1);
				BF2.setBounds(700,0, 700, 1000);
				setPecasJogador2();
				container.add(BF2,15);
				
			}
			else if(jog2Posicionado == true && jog1Posicionado == true && vez == -1) {
				BF1.setBounds(0,0, 700, 1000);
				container.remove(BF2);
				BF1.setAttackMode();
				BF2.setAttackMode();
				container.add(BF1,0);
				container.add(BF2,1);
				buttonInicioAtaque.setBounds(600, 550, 100, 40);
				add(buttonInicioAtaque);

				
		        add(saveButton);

				attackLabel.setBounds(450, 600, 300,50);
		        container.add(attackLabel,2);
		        
			}
			
			
		    add(container);
		}		
	}
	
	
	
	
	class IntroButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	       ctrl.setJogadores(nameTextField.getText(), nameTextField2.getText());
	       BF1.setJogador(j1);
	       BF2.setJogador(j2);
	    }
	}

	class AtaqueButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			remove(buttonInicioAtaque);
			if(attackEnded == true) {
				attackEnded = false;
				if(vez == 1) {		
					setAttack(j2);
				}
				else {
					setAttack(j1);
				}
			}
	    }
	}
	
	class SaveButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ctrl.saveGame(vez, numAcertos);
		}
	}
	
	class LoadButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ctrl.loadGame();
		}
	}

	
	private void setAttack(Jogador jogador)
	{
		if(jogador.getVez() == 1)
		{
			vez=1;
			BF1.setHidden(false);
			BF2.setHidden(true);
			BF1.isUnderAttack = false;
			BF2.isUnderAttack = true;

			BF1.repaint();
		}
		else
		{
			vez = 2;
			BF1.setHidden(true);
			BF2.setHidden(false);
			BF1.isUnderAttack = true;
			BF2.isUnderAttack = false;
			BF2.repaint();
		}
		//repaint();
	}
	
	public void notify(Observable o) {
		
		Object lob[] =(Object[]) o.get();
		String type = (String) lob[0];
		
		
		 
		if(type.compareTo("regras") == 0) 
		{
			String [] jogadores = (String [])lob[4];	
			j1.setMyName(jogadores[0]);
			j2.setMyName(jogadores[1]);
			repaint();
		}
		else if(type.compareTo("movement-released") == 0)
		{
			if((int)lob[1] > (int)(700 + xIni) && (int)lob[2] > (int)yIni) {
				
				//AJEITAR ISSO:
				if(!jog1Posicionado) {
					if(BF1.setPeca((int[][])lob[5], (int)lob[1] - 700, (int)lob[2],(int)lob[3],(int)lob[4], (int) lob[6])) {

						container.remove(vPecas1[(int)lob[6]]);
					}	
					if(container.getComponentCount() == 1) {
						jog1Posicionado = true;
						repaint();
					}
				
				}
				else if(!jog2Posicionado){					
					if(BF2.setPeca((int[][])lob[5], (int)lob[1] - 700, (int)lob[2],(int)lob[3],(int)lob[4], (int) lob[6])) {
						container.remove(vPecas2[(int)lob[6]]);
					}
					if(container.getComponentCount() == 1) {
						jog2Posicionado = true;
						repaint();
					}
				}
			}
		}
		else if(type.compareTo("attack-executed") == 0) {
			String tipoAcerto = (String)lob[3];
			numAcertos = (int)lob[1];
			if(numAcertos == 3) {
				numAcertos = 0;
				BF1.setAttackMode();
				BF2.setAttackMode();
				BF1.isUnderAttack = false;
				BF2.isUnderAttack = false;
				attackEnded = true;
				add(buttonInicioAtaque);
				repaint();
		}
		

			

			
			
			
			// #TODO CONSERTAR ESSES SETTEXT QUE N FUNCIONAM
			if( tipoAcerto.compareTo("agua") == 0) {
				
//				attackLabel.setText("Água atingida!");
//				attackLabel = "Água atingida!";
			}
			
			else if( tipoAcerto.compareTo("peca") == 0) {
//				attackLabel.setText("Peça atingida!");
//				attackLabel = "Peça atingida!";
			}
			
			else if( tipoAcerto.compareTo("erro") == 0) {
//				attackLabel.setText("Essa casa ja foi atingida!");
//				attackLabel = "Essa casa ja foi atingida!";
			}
			
			
			
			
		}
		else if(type == "remove")
		{
			short numTab = (short)lob[2];
			System.out.println(numTab);
			if(numTab == 1)
			{
				container.add(vPecas1[(int)lob[1]], container.getComponentCount()-1);
			}
			else 
			{
				container.add(vPecas2[(int)lob[1]], container.getComponentCount()-1);				
			}
			repaint();
		}
	}
	
	private void setPecas() {
		
		vPecas1 = j1.getMyPieces();
		vPecas2 = j2.getMyPieces();
		
		for(int i =0 ; i< 15 ; i++)
		{
			vPecas1[i].addObserver(this);
			vPecas2[i].addObserver(this);
		}
		
	    
		vPecas1[0].setBounds(50,100, 100,70);
		vPecas1[1].setBounds(185,100, 100,70);
		vPecas1[2].setBounds(320,100, 100,70);
		vPecas1[3].setBounds(455,100, 100,70);				
		vPecas1[4].setBounds(590,100, 100,70);				
		
		vPecas1[5].setBounds(50,300, 30,30);
		vPecas1[6].setBounds(185,300, 30,30);
		vPecas1[7].setBounds(320,300, 30,30);
		vPecas1[8].setBounds(455,300, 30,30);
		
		vPecas1[9].setBounds(50,400, 60,30);
		vPecas1[10].setBounds(185,400, 60,30);				
		vPecas1[11].setBounds(320,400, 60,30);	
		
		vPecas1[12].setBounds(50,200, 120,30);
		vPecas1[13].setBounds(185,200, 120,30);
		
		vPecas1[14].setBounds(50,500, 150,30);

		
		
		vPecas2[0].setBounds(50,100, 100,70);
		vPecas2[1].setBounds(185,100, 100,70);
		vPecas2[2].setBounds(320,100, 100,70);
		vPecas2[3].setBounds(455,100, 100,70);				
		vPecas2[4].setBounds(590,100, 100,70);				
		
		vPecas2[5].setBounds(50,300, 30,30);
		vPecas2[6].setBounds(185,300, 30,30);
		vPecas2[7].setBounds(320,300, 30,30);
		vPecas2[8].setBounds(455,300, 30,30);
		
		vPecas2[9].setBounds(50,400, 60,30);
		vPecas2[10].setBounds(185,400, 60,30);				
		vPecas2[11].setBounds(320,400, 60,30);	
		
		vPecas2[12].setBounds(50,200, 120,30);
		vPecas2[13].setBounds(185,200, 120,30);
		
		vPecas2[14].setBounds(50,500, 150,30);
		
		
		
		vPecas1[0].setLocation(50,100);
		vPecas1[1].setLocation(185,100);
		vPecas1[2].setLocation(320,100);
		vPecas1[3].setLocation(455,100);				
		vPecas1[4].setLocation(590,100);				
		
		vPecas1[5].setLocation(50,300);
		vPecas1[6].setLocation(185,300);
		vPecas1[7].setLocation(320,300);
		vPecas1[8].setLocation(455,300);
		
		vPecas1[9].setLocation(50,400);	
		vPecas1[10].setLocation(185,400);				
		vPecas1[11].setLocation(320,400);;		
		
		vPecas1[12].setLocation(50,200);
		vPecas1[13].setLocation(185,200);
		
		vPecas1[14].setLocation(50,500);
	    
		
		vPecas2[0].setLocation(50,100);
		vPecas2[1].setLocation(185,100);
		vPecas2[2].setLocation(320,100);
		vPecas2[3].setLocation(455,100);				
		vPecas2[4].setLocation(590,100);				
		
		vPecas2[5].setLocation(50,300);
		vPecas2[6].setLocation(185,300);
		vPecas2[7].setLocation(320,300);
		vPecas2[8].setLocation(455,300);
		
		vPecas2[9].setLocation(50,400);	
		vPecas2[10].setLocation(185,400);				
		vPecas2[11].setLocation(320,400);;		
		
		vPecas2[12].setLocation(50,200);
		vPecas2[13].setLocation(185,200);
		
		vPecas2[14].setLocation(50,500);
	    

		for (int i =0 ; i<15 ; i++)
		{
			container.add(vPecas1[i],i);
			//container.add(vPecas2[i],i);
		}

	}
	
	private void setPecasJogador2() {
		
		vPecas1 = j1.getMyPieces();
		vPecas2 = j2.getMyPieces();
		
		for(int i =0 ; i< 15 ; i++)
		{
			vPecas1[i].addObserver(this);
			vPecas2[i].addObserver(this);
		}
		
	    
		vPecas1[0].setBounds(50,100, 100,70);
		vPecas1[1].setBounds(185,100, 100,70);
		vPecas1[2].setBounds(320,100, 100,70);
		vPecas1[3].setBounds(455,100, 100,70);				
		vPecas1[4].setBounds(590,100, 100,70);				
		
		vPecas1[5].setBounds(50,300, 30,30);
		vPecas1[6].setBounds(185,300, 30,30);
		vPecas1[7].setBounds(320,300, 30,30);
		vPecas1[8].setBounds(455,300, 30,30);
		
		vPecas1[9].setBounds(50,400, 60,30);
		vPecas1[10].setBounds(185,400, 60,30);				
		vPecas1[11].setBounds(320,400, 60,30);	
		
		vPecas1[12].setBounds(50,200, 120,30);
		vPecas1[13].setBounds(185,200, 120,30);
		
		vPecas1[14].setBounds(50,500, 150,30);

		
		
		vPecas2[0].setBounds(50,100, 100,70);
		vPecas2[1].setBounds(185,100, 100,70);
		vPecas2[2].setBounds(320,100, 100,70);
		vPecas2[3].setBounds(455,100, 100,70);				
		vPecas2[4].setBounds(590,100, 100,70);				
		
		vPecas2[5].setBounds(50,300, 30,30);
		vPecas2[6].setBounds(185,300, 30,30);
		vPecas2[7].setBounds(320,300, 30,30);
		vPecas2[8].setBounds(455,300, 30,30);
		
		vPecas2[9].setBounds(50,400, 60,30);
		vPecas2[10].setBounds(185,400, 60,30);				
		vPecas2[11].setBounds(320,400, 60,30);	
		
		vPecas2[12].setBounds(50,200, 120,30);
		vPecas2[13].setBounds(185,200, 120,30);
		
		vPecas2[14].setBounds(50,500, 150,30);
		
		
		
		vPecas1[0].setLocation(50,100);
		vPecas1[1].setLocation(185,100);
		vPecas1[2].setLocation(320,100);
		vPecas1[3].setLocation(455,100);				
		vPecas1[4].setLocation(590,100);				
		
		vPecas1[5].setLocation(50,300);
		vPecas1[6].setLocation(185,300);
		vPecas1[7].setLocation(320,300);
		vPecas1[8].setLocation(455,300);
		
		vPecas1[9].setLocation(50,400);	
		vPecas1[10].setLocation(185,400);				
		vPecas1[11].setLocation(320,400);;		
		
		vPecas1[12].setLocation(50,200);
		vPecas1[13].setLocation(185,200);
		
		vPecas1[14].setLocation(50,500);
	    
		
		vPecas2[0].setLocation(50,100);
		vPecas2[1].setLocation(185,100);
		vPecas2[2].setLocation(320,100);
		vPecas2[3].setLocation(455,100);				
		vPecas2[4].setLocation(590,100);				
		
		vPecas2[5].setLocation(50,300);
		vPecas2[6].setLocation(185,300);
		vPecas2[7].setLocation(320,300);
		vPecas2[8].setLocation(455,300);
		
		vPecas2[9].setLocation(50,400);	
		vPecas2[10].setLocation(185,400);				
		vPecas2[11].setLocation(320,400);;		
		
		vPecas2[12].setLocation(50,200);
		vPecas2[13].setLocation(185,200);
		
		vPecas2[14].setLocation(50,500);
	    

		for (int i =0 ; i<15 ; i++)
		{
			//container.add(vPecas1[i],i);
			container.add(vPecas2[i],i);
		}

	}
}