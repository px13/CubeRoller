
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
	 * @return Vr�ti po�et riadkov matice.
	 */
    public int getR() {return r;}
    /**
	 * @return Vr�ti po�et st�pcov matice.
	 */
	public int getS() {return s;}
	/** 
	 * Met�da umo��uje manipulova� priamo s po�om.
	 * @return Vr�ti dvojrozmern� pole, ktor� reprezentuje maticu.
	 */
	public double[][] getPole() {return pole;}
	
	/**
	 * Kon�truktor.
	 * @param r 	Po�et riadkov matice.
	 * @param s 	Po�et st�pcov matice.
	 * @param pole	Pole, ktor� reprezentuje maticu.
	 */
	public Matica(int r,int s,double[][] pole) {
        this.r = r;
        this.s = s;
        this.pole = pole;
	}
    
	/**
	 * Met�da vyn�sob� dve matice.
	 * @param b Matica, ktorou sa ide n�sobi�.
	 * @return Vr�ti vyn�soben� maticu.
	 */
    public Matica nasob(Matica b) {
    	/**
    	 *Met�da vyn�sob� dve matice a vr�ti nov� maticu. 
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