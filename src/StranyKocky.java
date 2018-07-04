
import java.io.Serializable;
/**
 * Trieda sl��i na zaznamenanie zmeny rozlo�enia str�n kocky pri ot��an�.
 */
public class StranyKocky implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6575774886343195054L;
	private int horna,dolna,lava,prava,zadna,predna;
	/** @return Vr�ti ��slo prisl�chaj�ce aktu�lnej spodnej strane kocky. */
	public int getDolna() {return dolna;}
	/** Kon�truktor, jednotliv�m premenn�m symbolizuj�cim strany kocky
	 * sa priradia ��sla.
	 */
	public StranyKocky() {
		horna = 3;
		dolna = 5;
		lava = 4;
		prava = 2;
		zadna = 1;
		predna = 0;
	}
	/**
	 * Met�da preusporiada strany kocky pod�a smeru, ktor�m sa ot��a.
	 * @param tr	Posun riadkov.
	 * @param ts	Posun st�pcov.
	 */
	public void otoc(int tr, int ts) {
		
		int staraHorna = horna;
		int staraDolna = dolna;
		int staraLava = lava;
		int staraPrava = prava;
		int staraZadna = zadna;
		int staraPredna = predna;
		if (tr == 1) {
			horna = staraZadna;
			dolna = staraPredna;
			predna = staraHorna;
			zadna = staraDolna;
		}
		else if (tr == -1) {
			dolna = staraZadna;
			horna = staraPredna;
			zadna = staraHorna;
			predna = staraDolna;
		}
		else if (ts == 1) {
			prava = staraHorna;
			lava = staraDolna;
			horna = staraLava;
			dolna = staraPrava;
		}
		else if (ts == -1) {
			lava = staraHorna;
			prava = staraDolna;
			dolna = staraLava;
			horna = staraPrava;
		}
	}
}