import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * Trieda zabezpe�uje vykre�ovanie hracej plochy.
 */
public class Plocha extends Canvas {
	
	private Matica vrcholy = new Matica(8,4, new double[][] {
			  { 1.0, -1.0, -0.9, 1},
			  { 1.0, -1.0,  1.1, 1},
			  {-1.0, -1.0,  1.1, 1},
			  {-1.0, -1.0, -0.9, 1},
			  { 1.0,  1.0, -0.9, 1},
			  { 1.0,  1.0,  1.1, 1},
			  {-1.0,  1.0,  1.1, 1},
			  {-1.0,  1.0, -0.9, 1} });
	private Matica vrcholy2 = new Matica(8,4, new double[][] {
			  { 1.0, -1.0, -1.00, 1},
			  { 1.0, -1.0, -0.90, 1},
			  {-1.0, -1.0, -0.90, 1},
			  {-1.0, -1.0, -1.00, 1},
			  { 1.0,  1.0, -1.00, 1},
			  { 1.0,  1.0, -0.90, 1},
			  {-1.0,  1.0, -0.90, 1},
			  {-1.0,  1.0, -1.00, 1} });
	private int[][] polygony = new int[][] {
			{0, 1, 2, 3},
			{4, 7, 6, 5},
			{0, 4, 5, 1},
			{1, 5, 6, 2},
			{2, 6, 7, 3},
			{4, 0, 3, 7} };
	private Objekt kocka;

	private Zamok zamok;
	private double sizeX,sizeY;
	private double VEL = 50;
	private StavHry hra;
	private Color[] farby = new Color[] {Color.WHITE,Color.RED,Color.BLACK};
	/**
	 * Kon�truktor, vytvor� sa v �om nov� n�hodn� plocha.
	 */
	public Plocha() {
		nova();
	}
	/** Met�da priprav� hru.
	 * @param posR	Riadok kde sa nach�dza kocka.
	 * @param posS	St�pec kde sa nach�dza kocka.
	 * @param pole	Dvojrozmern� pole predstavuj�ce hraciu plochu, 0 predstavuje pr�zdne, 1 ofarben� a 2 nedostupn� pol��ko.
	 */
	public void inicializuj(int posR, int posS, int[][] pole) {
		hra = new StavHry(new ArrayList<Objekt>(),new StranyKocky(),pole,posR,posS);

		int R = pole.length;
		int S = pole[0].length;
		
		this.sizeX = (S+2)*VEL;
		this.sizeY = (R+2)*VEL;
		
		this.setWidth(sizeX);
		this.setHeight(sizeY);
		
		for (int i = 0 ; i < R ; i++) {
			for (int j = 0 ; j < S ; j++) {
				Objekt obj = null;
				if (pole[i][j] == 2) {
					obj = new Objekt(vrcholy2,polygony,pole[i][j]);
				}
				else {
					obj = new Objekt(vrcholy2,polygony,0);
					obj.setFarba(3, pole[i][j]);
				}
				obj.posun(-2*(S-j-3), 2*(R-i-3), 0);
				obj.zmenSkalovanie(VEL/2, VEL/2, 0);
				obj.zmenPosunutie(sizeX/2, sizeY/2, 0);
				obj.zmenNatocenie(10, 10, 0);
				obj.pripravVykreslenie();
				hra.objekty.add(obj);
			}
		}
		kocka = new Objekt(vrcholy,polygony,0);
		kocka.posun(-2*(S-posS-3), 2*(R-posR-3), 0);
		kocka.zmenSkalovanie(VEL/2, VEL/2, 0);
		kocka.zmenPosunutie(sizeX/2, sizeY/2, 0);
		kocka.zmenNatocenie(10, 10, 0);
		kocka.pripravVykreslenie();
		hra.objekty.add(kocka);
		zamok = new Zamok();
		kresli();
	}
	/**
	 * Vygeneruje nov� hraciu plochu.
	 * N�hodne zvol� poz�cie ofarben�ch pol��iek,
	 * nedostupn�ch pol��iek a potom s�radnice kocky.
	 */
	public void nova() {
		int[][] nove = new int[5][5];
		Random random = new Random();
		for (int i = 0 ; i < 6 ; i++) {
			int r = random.nextInt(4);
			int s = random.nextInt(4);
			while (nove[r][s] != 0) {
				r = random.nextInt(4);
				s = random.nextInt(4);
			}
			nove[r][s] = 1;
		}
		int prazdne = random.nextInt(3);
		for (int i = 0 ; i < prazdne ; i++) {
			int r = random.nextInt(4);
			int s = random.nextInt(4);
			while (nove[r][s] != 0) {
				r = random.nextInt(4);
				s = random.nextInt(4);
			}
			nove[r][s] = 2;
		}
		int r = random.nextInt(4);
		int s = random.nextInt(4);
		while (nove[r][s] != 0) {
			r = random.nextInt(4);
			s = random.nextInt(4);
		}
		
		inicializuj(r,s,nove);
	}
	/**
	 * Zap�e do s�boru aktu�lny stav hry.
	 * @param subor	S�bor/Absol�tna cesta k s�boru s ulo�enou hrou.
	 */
	public void save(String subor) {
		try {hra.zapis(subor);} catch (Exception e) {}
	}
	/**
	 * Na��ta ulo�en� poz�ciu. V pr�pade zl�ho s�boru sa nestane ni�.
	 * @param subor	S�bor/Absol�tna cesta k s�boru s ulo�enou hrou.
	 */
	public void load(String subor) {
		try {hra = StavHry.precitaj(subor);} catch (Exception e) {return;}
		kocka = hra.objekty.get(hra.objekty.size()-1);
		kresli();
	}
	/**
	 * Met�da posunie kocku o zadan� po�et riadkov a st�pcov.
	 * @param tr Po�et riadkov o ktor� sa posunie kocka.
	 * @param ts Po�et st�pcov o ktor� sa posunie kocka.
	 */
	public void posunKocku(int tr, int ts) {
		
		if (ts == -1 && hra.posR == 4) return;
		if (ts == 1 && hra.posR == 0) return;
		if (tr == -1 && hra.posS == 0) return;
		if (tr == 1 && hra.posS == 4) return;
		int noveR = hra.posR-ts;
		int noveS = hra.posS+tr;
		if (hra.pole[noveR][noveS] == 2) return;
		hra.stranyKocky.otoc(noveR - hra.posR, noveS - hra.posS);

		if (hra.pole[noveR][noveS] == 1 && kocka.getFarba()[hra.stranyKocky.getDolna()] == 0) {
			kocka.setFarba(hra.stranyKocky.getDolna(),1);
			hra.pole[noveR][noveS] = 0;
			hra.objekty.get(noveR*hra.pole.length+noveS).setFarba(3,0);
		}
		else if (hra.pole[noveR][noveS] == 0 && kocka.getFarba()[hra.stranyKocky.getDolna()] == 1) {
			kocka.setFarba(hra.stranyKocky.getDolna(),0);
			hra.pole[noveR][noveS] = 1;
			hra.objekty.get(noveR*hra.pole.length+noveS).setFarba(3,1);
		}
		hra.posS = noveS;
		hra.posR = noveR;
		AnimacnyThread thread = new AnimacnyThread(zamok,this,kocka,ts*2,tr*2);
		thread.start();
	}
	/**
	 * Met�da oto�� objekty a prekresl� hraciu plochu.
	 * @param uholX	Uhol o ktor� sa maj� objekty oto�i� okolo osi x.
	 * @param uholY	Uhol o ktor� sa maj� objekty oto�i� okolo osi y.
	 * @param uholZ	Uhol o ktor� sa maj� objekty oto�i� okolo osi z.
	 */
	public void otoc(double uholX, double uholY, double uholZ) {
		for (Objekt obj : hra.objekty) {
			obj.zmenNatocenie(uholX, uholY, uholZ);
			obj.pripravVykreslenie();
		}
		kresli();
	}
	/**
	 * Met�da vykresl� hraciu plochu a kocku. Kocka sa vykres�uje ako posledn�,
	 * tak�e pri poh�ade zospodu bude vidie� spodn� stranu kocky.
	 */
	public void kresli() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, sizeX, sizeY);
		gc.setStroke(Color.BLACK);
		for (Objekt obj : hra.objekty) {
			boolean[] pole = obj.getSkryteHrany();
			double[][] pole2 = obj.getPoleVykreslovania();
			double[] x = new double[5];
			double[] y = new double[5];
			for (int i = 0 ; i < polygony.length; i++) {
				if (pole[i] == false) continue;
				int[] p = polygony[i];
				x[0] = pole2[p[0]][0];
				y[0] = pole2[p[0]][1];
				x[1] = pole2[p[1]][0];
				y[1] = pole2[p[1]][1];
				x[2] = pole2[p[2]][0];
				y[2] = pole2[p[2]][1];
				x[3] = pole2[p[3]][0];
				y[3] = pole2[p[3]][1];
				for (int j = 0 ; j < 4 ; j++) {
					y[j] = sizeY-y[j];
				}
				x[4] = x[0];
				y[4] = y[0];
				gc.setFill(farby[obj.getFarba()[i]]);
				gc.fillPolygon(x, y, 5);
				gc.strokePolyline(x, y, 5);
			}
		}		
	}
}