
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Trieda popisuje aktuálny stav hry, zoznam objektov a
 * informácia o natoèení kocky.
 */
public class StavHry implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 441103986773203663L;
	/**
	 * Zoznam objektov.
	 */
	public ArrayList<Objekt> objekty;
	/**
	 * Informácia o rozložení strán v kocke.
	 */
	public StranyKocky stranyKocky;
	/**
	 * Pole informujúce o jednotlivých políèkach hracej plochy.
	 */
	public int[][] pole;
	/**
	 * Riadok kde sa nachádza kocka.
	 */
	public int posR;
	/**
	 * Ståpec kde sa nachádza kocka.
	 */
	public int posS;
	/**
	 * Konštruktor.
	 * @param objekty		Zoznam objektov.
	 * @param stranyKocky	Informácia o rozložení strán v kocke.
	 * @param pole			Pole informujúce o jednotlivých políèkach hracej plochy.
	 * @param posR			Riadok kde sa nachádza kocka.
	 * @param posS			Ståpec kde sa nachádza kocka.
	 */
	public StavHry(ArrayList<Objekt> objekty, StranyKocky stranyKocky, int[][] pole, int posR, int posS) {
		this.objekty = objekty;
		this.stranyKocky = stranyKocky;
		this.pole = pole;
		this.posR = posR;
		this.posS = posS;
	}
	/**
	 * Metóda zapíše objekt do zadaného súboru.
 	 * @param subor	Súbor/Absolútna cesta k súboru s uloženou hrou.
	 */
	public void zapis(String subor) throws Exception
	{	
		ObjectOutputStream w = new ObjectOutputStream(
	    new FileOutputStream(subor));
	    w.writeObject(this);
	    w.close();
	}
	/**
	 * Metóda naèíta objekt zo zadaného súboru.
	 * @param subor	Súbor/Absolútna cesta k súboru s uloženou hrou.
	 * @return Vráti nový stavHry.
	 */ 
	public static StavHry precitaj(String subor) throws Exception
	{
		ObjectInputStream r = new ObjectInputStream(
	    new FileInputStream(subor));
	    StavHry hra = (StavHry)r.readObject();
	    r.close();
	    return hra;
	} 
}