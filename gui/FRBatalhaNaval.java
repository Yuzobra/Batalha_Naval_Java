package gui;

import regras.*;

import java.awt.*;
import javax.swing.*;

public class FRBatalhaNaval extends JFrame {
	final int LARG_DEFAULT=1400;
	final int ALT_DEFAULT=750;
	
	public FRBatalhaNaval(Fachada f) {
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int sl=screenSize.width;
		int sa=screenSize.height;
		int x=sl/2-LARG_DEFAULT/2;
		int y=sa/2-ALT_DEFAULT/2;
		setBounds(x,y,LARG_DEFAULT,ALT_DEFAULT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel container = new JPanel(new GridLayout(1,1));
		PNBatalhaNaval panel = new PNBatalhaNaval(f);
		panel.setLayout(null);

		container.add(panel);
		getContentPane().add(container);
		setTitle("Batalha Naval");
	}
	
	public static void main(String args[]) {
		boolean tabVisible = true;
//		boolean introVisible = true;
//		new FRIntro().setVisible(introVisible);
		(new FRBatalhaNaval(Fachada.getFachada())).setVisible(tabVisible);
	}
}
