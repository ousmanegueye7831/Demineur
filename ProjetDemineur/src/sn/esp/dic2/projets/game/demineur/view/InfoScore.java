package sn.esp.dic2.projets.game.demineur.view;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoScore extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JButton emoticon ;
	static JLabel labelTime;
	static JButton boutonNbMine,boutonTime;
	private DemineurMainWindow window;
	private Image bg;
	private JLabel tim ;
	
	public InfoScore(){}
	
	public InfoScore(DemineurMainWindow window){
		this.window = window;
		
		bg = new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/bg2.png" )).getImage();
		JPanel panelTime = new JPanel();
		panelTime.setOpaque(false);
		panelTime.setLayout(new FlowLayout());
		JLabel labelMontre= new JLabel();
		labelMontre.setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/watch.png" )));
		labelMontre.setBorder(null);
		labelMontre.setToolTipText("Le temps écoulé dépuis le début");
		panelTime.add(labelMontre);
		tim = window.getModel().getPartieModel().getTime().getTimerLb();
		tim.setFont(new Font("Timesroman",Font.BOLD,18));
		panelTime.add(tim);
		JPanel panelMine = new JPanel();
		panelMine.setOpaque(false);
		panelMine.setLayout(new FlowLayout());
		JLabel labelMine = new JLabel();
		labelMine.setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/petitmine.png" )));
		labelMine.setBorder(null);
		boutonNbMine = new JButton();
		boutonNbMine.setPreferredSize(new Dimension(30,30));
		boutonNbMine.setText(""+window.getModel().getPartieModel().getGrille().getNbCaseMinees());
		boutonNbMine.setToolTipText("Nombre de mines dans le grille");
		boutonNbMine.setBorder(BorderFactory.createLoweredBevelBorder());
		
		boutonNbMine.setFont(new Font("Timesroman",Font.BOLD,18));
		panelMine.add(boutonNbMine);
		panelMine.add(labelMine);
		emoticon = new JButton();
		emoticon.setIcon(new ImageIcon(DemineurView.class.getResource("/sn/esp/dic2/projets/game/demineur/data/gagne.png" )));
		emoticon.setPreferredSize(new Dimension(40,40));
		emoticon.addActionListener(new MesActionsAbstract("Rejouer la partie",this.window,0));
		emoticon.setToolTipText("Click pour réinitialiser la partie");
		Box box = Box.createHorizontalBox();
		box.add(panelTime);
		box.add(Box.createHorizontalStrut(25));
		box.add(emoticon);
		box.add(Box.createHorizontalStrut(25));
		box.add(panelMine);
		add(box);
	}
	

	@Override
    public void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
	
	
	//**************** getteurs/setteurs******************
	public static JButton getEmoticon() {
		return emoticon;
	}

	public static void setEmoticon(JButton emoticon) {
		InfoScore.emoticon = emoticon;
	}

	public static JLabel getLabelTime() {
		return labelTime;
	}

	public static void setLabelTime(JLabel labelTime) {
		InfoScore.labelTime = labelTime;
	}

	public static JButton getBoutonNbMine() {
		return boutonNbMine;
	}

	public static void setBoutonNbMine(JButton boutonNbMine) {
		InfoScore.boutonNbMine = boutonNbMine;
	}

	public static JButton getBoutonTime() {
		return boutonTime;
	}

	public static void setBoutonTime(JButton boutonTime) {
		InfoScore.boutonTime = boutonTime;
	}

	public DemineurMainWindow getWindow() {
		return window;
	}

	public void setWindow(DemineurMainWindow window) {
		this.window = window;
	}

	public Image getBg() {
		return bg;
	}

	public void setBg(Image bg) {
		this.bg = bg;
	}

	public JLabel getTim() {
		return tim;
	}

	public void setTim(JLabel tim) {
		this.tim = tim;
	}
	
	
}
