/**
 * Trieda reprezentuje Vektor.
 */
public class Vektor {
	private double[] pole;
	/**
	 * Kon�truktor.
	 * @param pole S�radnice vektora.
	 */
	public Vektor(double[] pole) {
		
		this.pole = new double[3];
		for (int i = 0 ; i < 3 ; i++) {
			this.pole[i] = pole[i];
		}
	}
	/**
	 * Kon�truktor. Od��ta koncov� bod od za�iato�n�ho bodu a t�m sa vytvor� vektor.
	 * @param pole1 S�radnice za�iato�n�ho bodu.
	 * @param pole2 S�radnice koncov�ho bodu.
	 */
	public Vektor(double[] pole1, double[] pole2) {
		this.pole = new double[3];
		for (int i = 0 ; i < 3 ; i++) {
			this.pole[i] = pole2[i] - pole1[i];
		}
	}
	/**
	 * @param b Vektor, ktor�m sa ide n�sobi�.
	 * @return Vr�ti skal�rny s��in medzi dvoma vektormi.
	 */
	public double skalarnySucin(Vektor b) {
		
		double vysl = 0;
		for (int i = 0 ; i < pole.length ; i++) {
			vysl += pole[i] * b.pole[i];
		}
		return vysl;
	}
	/**
	 * @param b Vektor, ktor�m sa ide n�sobi�.
	 * @return Vr�ti vektorov� s��in medzi dvoma vektormi.
	 */
	public Vektor vektorovySucin(Vektor b) {
		double[] vysl = new double[3];
		vysl[0] = pole[1]*b.pole[2]-b.pole[1]*pole[2];
		vysl[1] = pole[2]*b.pole[0]-pole[0]*b.pole[2];
		vysl[2] = pole[0]*b.pole[1]-pole[1]*b.pole[0];
		return new Vektor(vysl);
	}
	/**
	 * @return Vr�ti ve�kos� aktu�lneho vektora.
	 */
	public double velkost() {
		double vysl = 0;
		for (int i = 0 ; i < pole.length ; i++) {
			vysl += (pole[i]*pole[i]);
		}
		return Math.sqrt(vysl);
	}
	/**
	 * @param b Vektor,
	 * @return Vr�ti ve�kos� uhla, ktor� zvieraj� dva vektory, v�sledok je v stup�och.
	 */
	public double uhol(Vektor b) {
		return Math.toDegrees(Math.acos(skalarnySucin(b)/(velkost()*b.velkost())));
	}
}