package view;

import java.awt.EventQueue;

public class AnchoredMain {

	public static void main(String[] args) {
		 EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new AnchoredGUI();     
	            }
	        });

	}

}
