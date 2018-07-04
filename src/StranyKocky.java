
import java.io.Serializable;
/**
 * Trieda slúi na zaznamenanie zmeny rozloenia strán kocky pri otáèaní.
 */
public class StranyKocky implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6575774886343195054L;
	private int horna,dolna,lava,prava,zadna,predna;
	/** @return Vráti èíslo prislúchajúce aktuálnej spodnej strane kocky. */
	public int getDolna() {return dolna;}
	/** Konštruktor, jednotlivım premennım symbolizujúcim strany kocky
	 * sa priradia èísla.
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
	 * Metóda preusporiada strany kocky pod¾a smeru, ktorım sa otáèa.
	 * @param tr	Posun riadkov.
	 * @param ts	Posun ståpcov.
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