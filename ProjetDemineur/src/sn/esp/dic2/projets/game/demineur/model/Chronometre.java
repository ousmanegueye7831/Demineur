package sn.esp.dic2.projets.game.demineur.model;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;


public class Chronometre extends TimerTask{
	private static final long serialVersionUID = 1L;
	private JLabel timerLb;
	private Timer timer ;
	private int i;
	
	public Chronometre(int depart){
		this.i = depart;
		timerLb = new JLabel();
		run();
        timer =  new Timer();
        timer.scheduleAtFixedRate(this, 0, 1000);
	}
	
	@Override
	public void run() {
        	i++;
        	timerLb.setText(""+i);   
	}
	
	//**************** getteurs / setteurs *******
	public JLabel getTimerLb() {
		return timerLb;
	}
	public void setTimerLb(JLabel timerLb) {
		this.timerLb = timerLb;
	}
	
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}

	@Override
	public String toString() {
		return "Chronometre [i=" + i + "]";
	}
	
}

