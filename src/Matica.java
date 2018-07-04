
import java.io.Serializable;

/**
 * Trieda reprezentuje maticu.
 */
public class Matica implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8012039127817861350L;
	private int r,s;
	private double[][] pole;
	
	/**
	 * @return Vráti poèet riadkov matice.
	 */
    public int getR() {return r;}
    /**
	 * @return Vráti poèet ståpcov matice.
	 */
	public int getS() {return s;}
	/** 
	 * Metóda umoòuje manipulova priamo s po¾om.
	 * @return Vráti dvojrozmerné pole, ktoré reprezentuje maticu.
	 */
	public double[][] getPole() {return pole;}
	
	/**
	 * Konštruktor.
	 * @param r 	Poèet riadkov matice.
	 * @param s 	Poèet ståpcov matice.
	 * @param pole	Pole, ktoré reprezentuje maticu.
	 */
	public Matica(int r,int s,double[][] pole) {
        this.r = r;
        this.s = s;
        this.pole = pole;
	}
    
	/**
	 * Metóda vynásobí dve matice.
	 * @param b Matica, ktorou sa ide násobi.
	 * @return Vráti vynásobenú maticu.
	 */
    public Matica nasob(Matica b) {
    	/**
    	 *Metóda vynásobí dve matice a vráti novú maticu. 
    	 */
        Matica vysl = new Matica(r,b.s,new double[r][b.s]);
        for (int i = 0; i < r ; i++) {
            for (int j = 0 ; j < b.s ; j++) {
                for (int k = 0 ; k < s ; k++) {
                    vysl.pole[i][j] += pole[i][k] * b.pole[k][j];
                }
            }
        }
        return vysl;
    }
}