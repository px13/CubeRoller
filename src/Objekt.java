
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
	 * @return Vr�ti zoznam farieb jednotliv�ch pl��ok objektu.
	 */
	public int[] getFarba() {return farba;}
	/** Met�da nastav� farbu pl��ky so zadan�m indexom.
	 * @param index	Index pl��ky, ktorej farba sa ide meni�.
	 * @param farba Nov� farba. 
	 */
	public void setFarba(int index, int farba) {this.farba[index] = farba;}
	/**
	 * @return Vr�ti pole, ktor� ozna�uje, ktor� pl��ky objektu s� vidite�n�.
	 */
	public boolean[] getSkryteHrany() {return skryteHrany;}
	/** 
	 * @return Vr�ti pole vrcholov objektu pripraven� na vykreslenie.
	 */
	public double[][] getPoleVykreslovania() {return maticaVykreslovania.getPole();}
	
	/**
	 * Kon�truktor.
	 * @param vrcholy	Matica, ktorej jednotliv� riadky predstavuj� vrcholy objektu.
	 * @param polygony	Dvojrozmern� pole, ktor� informuje o posp�jan� vrcholov do polyg�nov.
	 * @param farba		Farba na ktor� sa nastavia v�etky pl��ky objektu.
	 */
	public Objekt(Matica vrcholy, int[][] polygony, int farba) {
		this.vrcholy = vrcholy;
		this.polygony = polygony;
		this.farba = new int[polygony.length];
		for (int i = 0 ; i < polygony.length ; i++) this.farba[i] = farba;
		pripravVykreslenie();
	}
	/**
	 * @param uhol	Uhol o ktor� sa m� objekt oto�i�.
	 * @return		Vr�ti maticu rot�cie o os x o zadan� uhol.
	 */
	public Matica getMaticaRotacieX(double uhol) {
		double sin = Math.sin(Math.toRadians(uhol));
		double cos = Math.cos(Math.toRadians(uhol));
		return new Matica(4,4,new double[][] {{1,0,0,0},{0,cos,-sin,0},{0,sin,cos,0},{0,0,0,1}});
	}
	/**
	 * @param uhol	Uhol o ktor� sa m� objekt oto�i�.
	 * @return		Vr�ti maticu rot�cie o os y o zadan� uhol.
	 */
	public Matica getMaticaRotacieY(double uhol) {
		double sin = Math.sin(Math.toRadians(uhol));
		double cos = Math.cos(Math.toRadians(uhol));
		return new Matica(4,4,new double[][] {{cos,0,sin,0},{0,1,0,0},{-sin,0,cos,0},{0,0,0,1}});
	}
	/**
	 * @param uhol	Uhol o ktor� sa m� objekt oto�i�.
	 * @return		Vr�ti maticu rot�cie o os z o zadan� uhol.
	 */
	public Matica getMaticaRotacieZ(double uhol) {
		double sin = Math.sin(Math.toRadians(uhol));
		double cos = Math.cos(Math.toRadians(uhol));
		return new Matica(4,4,new double[][] {{cos,-sin,0,0},{sin,cos,0,0},{0,0,1,0},{0,0,0,1}});
	}
	/**
	 * @param uholX	Uhol o ktor� sa m� objekt oto�i� okolo osi x.
	 * @param uholY	Uhol o ktor� sa m� objekt oto�i� okolo osi y.
	 * @param uholZ	Uhol o ktor� sa m� objekt oto�i� okolo osi z.
	 * @return		Vr�ti maticu rot�cie o zadan� uhly.
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
	 * @return 		Vr�ti maticu posunu o zadan� s�radnice.
	 */
	public Matica getMaticaTranslacie(double tx, double ty, double tz) {
		return new Matica(4,4,new double[][] {{1,0,0,0},{0,1,0,0},{0,0,1,0},{tx,ty,tz,1}});
	}
	/**
	 * @param sx	�k�lovanie x s�radnice.
	 * @param sy	�k�lovanie y s�radnice.
	 * @param sz	�k�lovanie z s�radnice.
	 * @return 		Vr�ti maticu �k�lovania o zadan� rozmery.
	 */
	public Matica getMaticaSkalovania(double sx, double sy, double sz) {
		return new Matica(4,4,new double[][] {{sx,0,0,0},{0,sy,0,0},{0,0,sz,0},{0,0,0,1}});
	}
	/**
	 * Met�da priprav� objekt na vykreslenie, oto�� objekt o celkov� nato�enie,
	 * zist� vidite�nos� hr�n a potom na�k�luje a posunie objekt o celkov�
	 * posun a zv��enie. 
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
	 * Posunie objekt v smere os� x, y, z.
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
	 * Zrotuje objekt o zadan� uhly.
	 * @param x	Uhol o ktor� sa m� objekt oto�i� okolo osi x.
	 * @param y	Uhol o ktor� sa m� objekt oto�i� okolo osi y.
	 * @param z	Uhol o ktor� sa m� objekt oto�i� okolo osi z.
	 */
	public void rotuj(double x, double y, double z) {
		vrcholy = vrcholy.nasob(getMaticaTranslacie(-posX,-posY,-posZ));
		vrcholy = vrcholy.nasob(getMaticaRotacie(x,y,z));
		vrcholy = vrcholy.nasob(getMaticaTranslacie(posX,posY,posZ));
		pripravVykreslenie();
	}
	/**
	 * Zmen� v�sledn� posunutie objektu.
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
	 * Zmen� v�sledn� zv��enie objektu.
	 * @param sx	�k�lovanie x s�radnice.
	 * @param sy	�k�lovanie y s�radnice.
	 * @param sz	�k�lovanie z s�radnice.
	 */
	public void zmenSkalovanie(double sx, double sy, double sz) {
		this.sx *= sx;
		this.sy *= sy;
		this.sz *= sz;
	}
	/**
	 * Zmen� v�sledn� nato�enie objektu.
	 * @param uholX	Uhol o ktor� sa m� objekt oto�i� okolo osi x.
	 * @param uholY	Uhol o ktor� sa m� objekt oto�i� okolo osi y.
	 * @param uholZ	Uhol o ktor� sa m� objekt oto�i� okolo osi z.
	 */
	public void zmenNatocenie(double uholX, double uholY, double uholZ) {
		this.uholX += uholX;
		this.uholY += uholY;
		this.uholZ += uholZ;
	}
	
}