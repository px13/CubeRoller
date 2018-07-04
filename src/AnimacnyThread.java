
import javafx.application.Platform;

/**
 * Vl�kno sa star� o vykreslenie ot��av�ho pohybu kocky.
 */
public class AnimacnyThread extends Thread {
	
	private Plocha plocha;
	private Objekt kocka;
	private double tx,ty;
	private Zamok zamok;
	
	/**
	 * Kon�truktor.
	 * @param zamok		Premenn� zabezpe�uje, aby sa nespustilo viac pohybov naraz.
	 * @param plocha	Plocha do ktorej sa vykres�uje kocka.
	 * @param kocka		Kocka, ktorou sa bude ot��a�.
	 * @param tx		Ve�kos� posunutia v smere osi x.
	 * @param ty		Ve�kos� posunutia v smere osi y.
	 */
	public AnimacnyThread(Zamok zamok, Plocha plocha, Objekt kocka, double tx, double ty) {
		this.zamok = zamok;
		this.plocha = plocha;
		this.kocka = kocka;
		this.tx = tx;
		this.ty = ty;
	}
	
	/**
	 * Vl�kno po spusten�, zadan� objekt v desiatich krokoch posunie
	 * o zadan� vzdialenos� a oto�� dokopy o 90 stup�ov.
	 * Medzi jednotliv�mi krokmi je 30 ms �akanie.
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