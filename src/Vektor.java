/**
 * Trieda reprezentuje Vektor.
 */
public class Vektor {
	private double[] pole;
	/**
	 * Konštruktor.
	 * @param pole Súradnice vektora.
	 */
	public Vektor(double[] pole) {
		
		this.pole = new double[3];
		for (int i = 0 ; i < 3 ; i++) {
			this.pole[i] = pole[i];
		}
	}
	/**
	 * Konštruktor. Odèíta koncový bod od zaèiatoèného bodu a tým sa vytvorí vektor.
	 * @param pole1 Súradnice zaèiatoèného bodu.
	 * @param pole2 Súradnice koncového bodu.
	 */
	public Vektor(double[] pole1, double[] pole2) {
		this.pole = new double[3];
		for (int i = 0 ; i < 3 ; i++) {
			this.pole[i] = pole2[i] - pole1[i];
		}
	}
	/**
	 * @param b Vektor, ktorým sa ide násobi.
	 * @return Vráti skalárny súèin medzi dvoma vektormi.
	 */
	public double skalarnySucin(Vektor b) {
		
		double vysl = 0;
		for (int i = 0 ; i < pole.length ; i++) {
			vysl += pole[i] * b.pole[i];
		}
		return vysl;
	}
	/**
	 * @param b Vektor, ktorým sa ide násobi.
	 * @return Vráti vektorový súèin medzi dvoma vektormi.
	 */
	public Vektor vektorovySucin(Vektor b) {
		double[] vysl = new double[3];
		vysl[0] = pole[1]*b.pole[2]-b.pole[1]*pole[2];
		vysl[1] = pole[2]*b.pole[0]-pole[0]*b.pole[2];
		vysl[2] = pole[0]*b.pole[1]-pole[1]*b.pole[0];
		return new Vektor(vysl);
	}
	/**
	 * @return Vráti ve¾kos aktuálneho vektora.
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
	 * @return Vráti ve¾kos uhla, ktorý zvierajú dva vektory, výsledok je v stupòoch.
	 */
	public double uhol(Vektor b) {
		return Math.toDegrees(Math.acos(skalarnySucin(b)/(velkost()*b.velkost())));
	}
}