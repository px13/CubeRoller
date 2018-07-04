/**
 * Semafór.
 */
public class Zamok {
	private boolean zamknute;
	/**
	 * Konštruktor.
	 */
	public Zamok() {
		zamknute = false;
	}
	/**
	 * @return Vráti èi je zámok zamknutý
	 */
	public boolean isZamknute() {
		return zamknute;
	}
	/**
	 * Odomkne zámok.
	 */
	synchronized public void odomkni() {
		zamknute = false;
		notify();
	}
	/**
	 * Zamkne zámok.
	 */
	synchronized public void zamkni() throws InterruptedException {
		while (zamknute) wait();
		zamknute = true;
	}
}