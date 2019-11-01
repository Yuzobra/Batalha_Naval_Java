package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import regras.*;
 
public class PNBatalhaNaval extends JPanel implements Observer {
	double xIni=40.0,yIni=40.0,xIni2=800.0,larg=30.0,alt=30.0,espLinha=5.0;
	int iClick,jClick;
	Celula tab1[][]=new Celula[15][15];
	Celula tab2[][]=new Celula[15][15];
	String[] jogadores;
	
	Line2D.Double ln1[]=new Line2D.Double[32];
	Line2D.Double ln2[]=new Line2D.Double[32];
	Fachada ctrl;
	
	
	JTextField nameTextField = new JTextField(20);
	JTextField nameTextField2 = new JTextField(20);
    JButton button = new JButton("Start");

    Battlefield BF1;
    Battlefield BF2;
    JPanel container;
    
    Peca vPecas[];


	public PNBatalhaNaval(Fachada f) {
		ctrl = f;
		Fachada.getFachada().register(this);
		BF1 = new Battlefield(xIni, yIni, (short)1, Fachada.getFachada());
		BF2 = new Battlefield(xIni, yIni, (short)2, Fachada.getFachada());
		jogadores = new String[2];
		jogadores[0] = jogadores[1] = "";
		container = new JPanel(new GridLayout(2,1));
		container.setSize(1400, 700);
		container.setLayout(null);
		container.setLocation(0, 0);
		
		vPecas = new Peca[10]; // VERIFICAR ESSA QUANTIDADE
		this.CriaPecas();
	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;
		
		if(jogadores[0].compareTo("") == 0 || jogadores[1].compareTo("") == 0) {
			g2d.drawString("Jogador 1:", 650, 350);
			g2d.drawString("Jogador 2:", 650, 400);
			nameTextField.setBounds(715, 335, 100, 25);
	        nameTextField.setFont(nameTextField.getFont().deriveFont(15f));

			nameTextField2.setBounds(715, 385, 100, 25); 
	        nameTextField2.setFont(nameTextField.getFont().deriveFont(15f));

	        button.addActionListener(new IntroButton());
	        button.setBounds(670, 450, 100, 40);
	        add(nameTextField);
	        add(nameTextField2);
	        add(button);
		}
		else {
			remove(button);
			remove(nameTextField);
			remove(nameTextField2);
			
			// ADD TABULEIROS
			BF1.setBounds(0,0, 700, 1000);
			BF2.setBounds(700,0, 700, 1000);
			vPecas[0].setBounds(100,100, 105,70);
//		    container.add(BF1);
		    container.add(vPecas[0],0);
			container.add(BF2,1);

		    
		    add(container);
		    
		}		
	}
	
	
	
	
	class IntroButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	       ctrl.setJogadores(nameTextField.getText(), nameTextField2.getText());
	       BF1.setJogador(jogadores[0]);
	       BF2.setJogador(jogadores[1]);
	    }
	}


	public void notify(Observable o) {
		Object lob[] =(Object[]) o.get();
		String type = (String) lob[0];
		if(type.compareTo("regras") == 0) {
			jogadores = (String[])lob[4];	
			repaint();
		}
		else if(type.compareTo("movement-released") == 0) {
			if((int)lob[1] > (int)(700 + xIni) && (int)lob[2] > (int)yIni) {
				System.out.println("soltei dentro");
				
				
				//AJEITAR ISSO:
				BF2.setPeca((int[][])lob[5], (int)lob[1] - 700, (int)lob[2],(int)lob[3],(int)lob[4]);
				
				
			}
		}
	}
	
	
	private void CriaPecas(){
		int[][] matriz = {{0,1,0},{1,0,1}};
		this.vPecas[0] = new Peca(matriz, Color.cyan);

	    Movement mv = new Movement(vPecas[0]);
	    mv.addObserver(this);
	}
}