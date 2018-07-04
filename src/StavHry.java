
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Trieda popisuje aktu�lny stav hry, zoznam objektov a
 * inform�cia o nato�en� kocky.
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
	 * Inform�cia o rozlo�en� str�n v kocke.
	 */
	public StranyKocky stranyKocky;
	/**
	 * Pole informuj�ce o jednotliv�ch pol��kach hracej plochy.
	 */
	public int[][] pole;
	/**
	 * Riadok kde sa nach�dza kocka.
	 */
	public int posR;
	/**
	 * St�pec kde sa nach�dza kocka.
	 */
	public int posS;
	/**
	 * Kon�truktor.
	 * @param objekty		Zoznam objektov.
	 * @param stranyKocky	Inform�cia o rozlo�en� str�n v kocke.
	 * @param pole			Pole informuj�ce o jednotliv�ch pol��kach hracej plochy.
	 * @param posR			Riadok kde sa nach�dza kocka.
	 * @param posS			St�pec kde sa nach�dza kocka.
	 */
	public StavHry(ArrayList<Objekt> objekty, StranyKocky stranyKocky, int[][] pole, int posR, int posS) {
		this.objekty = objekty;
		this.stranyKocky = stranyKocky;
		this.pole = pole;
		this.posR = posR;
		this.posS = posS;
	}
	/**
	 * Met�da zap�e objekt do zadan�ho s�boru.
 	 * @param subor	S�bor/Absol�tna cesta k s�boru s ulo�enou hrou.
	 */
	public void zapis(String subor) throws Exception
	{	
		ObjectOutputStream w = new ObjectOutputStream(
	    new FileOutputStream(subor));
	    w.writeObject(this);
	    w.close();
	}
	/**
	 * Met�da na��ta objekt zo zadan�ho s�boru.
	 * @param subor	S�bor/Absol�tna cesta k s�boru s ulo�enou hrou.
	 * @return Vr�ti nov� stavHry.
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