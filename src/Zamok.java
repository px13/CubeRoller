/**
 * Semaf�r.
 */
public class Zamok {
	private boolean zamknute;
	/**
	 * Kon�truktor.
	 */
	public Zamok() {
		zamknute = false;
	}
	/**
	 * @return Vr�ti �i je z�mok zamknut�
	 */
	public boolean isZamknute() {
		return zamknute;
	}
	/**
	 * Odomkne z�mok.
	 */
	synchronized public void odomkni() {
		zamknute = false;
		notify();
	}
	/**
	 * Zamkne z�mok.
	 */
	synchronized public void zamkni() throws InterruptedException {
		while (zamknute) wait();
		zamknute = true;
	}
}