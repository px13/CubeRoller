
import javafx.application.Platform;

/**
 * Vl·kno sa star· o vykreslenie ot·ËavÈho pohybu kocky.
 */
public class AnimacnyThread extends Thread {
	
	private Plocha plocha;
	private Objekt kocka;
	private double tx,ty;
	private Zamok zamok;
	
	/**
	 * Konötruktor.
	 * @param zamok		Premenn· zabezpeËuje, aby sa nespustilo viac pohybov naraz.
	 * @param plocha	Plocha do ktorej sa vykresæuje kocka.
	 * @param kocka		Kocka, ktorou sa bude ot·Ëaù.
	 * @param tx		Veækosù posunutia v smere osi x.
	 * @param ty		Veækosù posunutia v smere osi y.
	 */
	public AnimacnyThread(Zamok zamok, Plocha plocha, Objekt kocka, double tx, double ty) {
		this.zamok = zamok;
		this.plocha = plocha;
		this.kocka = kocka;
		this.tx = tx;
		this.ty = ty;
	}
	
	/**
	 * Vl·kno po spustenÌ, zadan˝ objekt v desiatich krokoch posunie
	 * o zadan˙ vzdialenosù a otoËÌ dokopy o 90 stupÚov.
	 * Medzi jednotliv˝mi krokmi je 30 ms Ëakanie.
	 */
	public void run() {
		try {zamok.zamkni();} catch (InterruptedException e) {return;}
		for (int i = 0 ; i < 10 ; i++) {
			
			if (tx > 1) kocka.rotuj(9,0,0);
			else if (tx < -1) kocka.rotuj(-9,0,0);
			else if (ty > 1) kocka.rotuj(0,-9,0);
			else if (ty < -1) kocka.rotuj(0,9,0);
			kocka.posun(ty/10,tx/10,0);
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					plocha.kresli();
				}
			});
			try {sleep(30);} catch (Exception e) {}
		}
		zamok.odomkni();
	}	
}