
import java.io.Serializable;

/**
 * Trieda reprezentuje 3D objekt.
 */
public class Objekt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9102474157338648590L;
	private Matica vrcholy;
	private Matica maticaVykreslovania;
	private int[][] polygony;
	private boolean[] skryteHrany;
	private double uholX,uholY,uholZ;
	private double sx = 1,sy = 1,sz = 1;
	private double tx,ty,tz;
	private int[] farba;
	private double posX,posY,posZ;
	
	/**
	 * @return Vráti zoznam farieb jednotlivıch plôšok objektu.
	 */
	public int[] getFarba() {return farba;}
	/** Metóda nastaví farbu plôšky so zadanım indexom.
	 * @param index	Index plôšky, ktorej farba sa ide meni.
	 * @param farba Nová farba. 
	 */
	public void setFarba(int index, int farba) {this.farba[index] = farba;}
	/**
	 * @return Vráti pole, ktoré oznaèuje, ktoré plôšky objektu sú vidite¾né.
	 */
	public boolean[] getSkryteHrany() {return skryteHrany;}
	/** 
	 * @return Vráti pole vrcholov objektu pripravené na vykreslenie.
	 */
	public double[][] getPoleVykreslovania() {return maticaVykreslovania.getPole();}
	
	/**
	 * Konštruktor.
	 * @param vrcholy	Matica, ktorej jednotlivé riadky predstavujú vrcholy objektu.
	 * @param polygony	Dvojrozmerné pole, ktoré informuje o pospájaní vrcholov do polygónov.
	 * @param farba		Farba na ktorú sa nastavia všetky plôšky objektu.
	 */
	public Objekt(Matica vrcholy, int[][] polygony, int farba) {
		this.vrcholy = vrcholy;
		this.polygony = polygony;
		this.farba = new int[polygony.length];
		for (int i = 0 ; i < polygony.length ; i++) this.farba[i] = farba;
		pripravVykreslenie();
	}
	/**
	 * @param uhol	Uhol o ktorı sa má objekt otoèi.
	 * @return		Vráti maticu rotácie o os x o zadanı uhol.
	 */
	public Matica getMaticaRotacieX(double uhol) {
		double sin = Math.sin(Math.toRadians(uhol));
		double cos = Math.cos(Math.toRadians(uhol));
		return new Matica(4,4,new double[][] {{1,0,0,0},{0,cos,-sin,0},{0,sin,cos,0},{0,0,0,1}});
	}
	/**
	 * @param uhol	Uhol o ktorı sa má objekt otoèi.
	 * @return		Vráti maticu rotácie o os y o zadanı uhol.
	 */
	public Matica getMaticaRotacieY(double uhol) {
		double sin = Math.sin(Math.toRadians(uhol));
		double cos = Math.cos(Math.toRadians(uhol));
		return new Matica(4,4,new double[][] {{cos,0,sin,0},{0,1,0,0},{-sin,0,cos,0},{0,0,0,1}});
	}
	/**
	 * @param uhol	Uhol o ktorı sa má objekt otoèi.
	 * @return		Vráti maticu rotácie o os z o zadanı uhol.
	 */
	public Matica getMaticaRotacieZ(double uhol) {
		double sin = Math.sin(Math.toRadians(uhol));
		double cos = Math.cos(Math.toRadians(uhol));
		return new Matica(4,4,new double[][] {{cos,-sin,0,0},{sin,cos,0,0},{0,0,1,0},{0,0,0,1}});
	}
	/**
	 * @param uholX	Uhol o ktorı sa má objekt otoèi okolo osi x.
	 * @param uholY	Uhol o ktorı sa má objekt otoèi okolo osi y.
	 * @param uholZ	Uhol o ktorı sa má objekt otoèi okolo osi z.
	 * @return		Vráti maticu rotácie o zadané uhly.
	 */
	public Matica getMaticaRotacie(double uholX, double uholY, double uholZ) {
		double sinX = Math.sin(Math.toRadians(uholX));
		double cosX = Math.cos(Math.toRadians(uholX));
		double sinY = Math.sin(Math.toRadians(uholY));
		double cosY = Math.cos(Math.toRadians(uholY));
		double sinZ = Math.sin(Math.toRadians(uholZ));
		double cosZ = Math.cos(Math.toRadians(uholZ));
		return new Matica(4,4,new double[][] {
				{cosZ*cosY,-sinZ*cosY,sinY,0},
				{cosZ*sinY*sinX+sinZ*cosX,cosZ*cosX-sinZ*sinY*sinX,-cosY*sinX,0},
				{sinZ*sinX-cosZ*sinY*cosX,sinZ*sinY*cosX+sinX*cosZ,cosY*cosX,0},
				{0,0,0,1}
				});
	}
	/**
	 * @param tx	Posunutie v smere osi x.
	 * @param ty	Posunutie v smere osi y.
	 * @param tz	Posunutie v smere osi z.
	 * @return 		Vráti maticu posunu o zadané súradnice.
	 */
	public Matica getMaticaTranslacie(double tx, double ty, double tz) {
		return new Matica(4,4,new double[][] {{1,0,0,0},{0,1,0,0},{0,0,1,0},{tx,ty,tz,1}});
	}
	/**
	 * @param sx	Škálovanie x súradnice.
	 * @param sy	Škálovanie y súradnice.
	 * @param sz	Škálovanie z súradnice.
	 * @return 		Vráti maticu škálovania o zadané rozmery.
	 */
	public Matica getMaticaSkalovania(double sx, double sy, double sz) {
		return new Matica(4,4,new double[][] {{sx,0,0,0},{0,sy,0,0},{0,0,sz,0},{0,0,0,1}});
	}
	/**
	 * Metóda pripravı objekt na vykreslenie, otoèí objekt o celkové natoèenie,
	 * zistí vidite¾nos hrán a potom naškáluje a posunie objekt o celkovı
	 * posun a zväèšenie. 
	 */
	public void pripravVykreslenie() {
		
		skryteHrany = new boolean[polygony.length];
		
		maticaVykreslovania = vrcholy.nasob(getMaticaRotacie(uholX,uholY,uholZ));
		Vektor kamera = new Vektor(new double[]{0,0,1});
		for (int i = 0 ; i < polygony.length ; i++) {
			Vektor smerovyVektor1 = new Vektor(maticaVykreslovania.getPole()[polygony[i][0]],maticaVykreslovania.getPole()[polygony[i][1]]);
			Vektor smerovyVektor2 = new Vektor(maticaVykreslovania.getPole()[polygony[i][1]],maticaVykreslovania.getPole()[polygony[i][2]]);
			Vektor normalovyVektor = smerovyVektor1.vektorovySucin(smerovyVektor2);
			if (kamera.uhol(normalovyVektor) < 90) {
				skryteHrany[i] = true;
			}
		}
		maticaVykreslovania = maticaVykreslovania.nasob(getMaticaSkalovania(sx,sy,sz));
		maticaVykreslovania = maticaVykreslovania.nasob(getMaticaTranslacie(tx,ty,tz));
		
	}
	/**
	 * Posunie objekt v smere osí x, y, z.
	 * @param x	Posunutie v smere osi x.
	 * @param y	Posunutie v smere osi y.
	 * @param z	Posunutie v smere osi z.
	 */
	public void posun(double x, double y, double z) {
		posX+=x;
		posY+=y;
		posZ+=z;
		vrcholy = vrcholy.nasob(getMaticaTranslacie(x,y,z));
		pripravVykreslenie();
	}
	/**
	 * Zrotuje objekt o zadané uhly.
	 * @param x	Uhol o ktorı sa má objekt otoèi okolo osi x.
	 * @param y	Uhol o ktorı sa má objekt otoèi okolo osi y.
	 * @param z	Uhol o ktorı sa má objekt otoèi okolo osi z.
	 */
	public void rotuj(double x, double y, double z) {
		vrcholy = vrcholy.nasob(getMaticaTranslacie(-posX,-posY,-posZ));
		vrcholy = vrcholy.nasob(getMaticaRotacie(x,y,z));
		vrcholy = vrcholy.nasob(getMaticaTranslacie(posX,posY,posZ));
		pripravVykreslenie();
	}
	/**
	 * Zmení vısledné posunutie objektu.
	 * @param tx	Posunutie v smere osi x.
	 * @param ty	Posunutie v smere osi y.
	 * @param tz	Posunutie v smere osi z.
	 */
	public void zmenPosunutie(double tx, double ty, double tz) {
		this.tx += tx;
		this.ty += ty;
		this.tz += tz;
	}
	/**
	 * Zmení vısledné zväèšenie objektu.
	 * @param sx	Škálovanie x súradnice.
	 * @param sy	Škálovanie y súradnice.
	 * @param sz	Škálovanie z súradnice.
	 */
	public void zmenSkalovanie(double sx, double sy, double sz) {
		this.sx *= sx;
		this.sy *= sy;
		this.sz *= sz;
	}
	/**
	 * Zmení vısledné natoèenie objektu.
	 * @param uholX	Uhol o ktorı sa má objekt otoèi okolo osi x.
	 * @param uholY	Uhol o ktorı sa má objekt otoèi okolo osi y.
	 * @param uholZ	Uhol o ktorı sa má objekt otoèi okolo osi z.
	 */
	public void zmenNatocenie(double uholX, double uholY, double uholZ) {
		this.uholX += uholX;
		this.uholY += uholY;
		this.uholZ += uholZ;
	}
	
}