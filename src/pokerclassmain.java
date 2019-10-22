import java.util.Arrays;
import java.util.Random;

public class pokerclassmain {
	static final int KARTENANZAHL=52;
	static final int FARBANZAHL=4;

	static  int kartendeck[]=new int [KARTENANZAHL];
	static  int farbe[]=new int [FARBANZAHL+1];
	static  int symbole[]=new int [KARTENANZAHL/FARBANZAHL];
	static  int hand[]=new int[5];
	static int  situation=0;
	static  int paarwert[]=new int[10]; 
	static int highcardzaehler=0;
	static int onepairzaehler=0;
	static int twopairzaehler=0;
	static int drillingzaehler=0;
	static int viererzaehler=0;
	static int straightzaehler=0;
	static int flushzaehler=0;
	static int fullhousezaehler=0;
	static int straightflushzaehler=0;
	static int royalflushzaehler=0;
	static float Versuch=1000000;
	public static void main(String[] args) {
		kartenfuellen();
		for (int x=0; x<=Versuch;x++){	
			ziehen();
			for (int i=0;i<hand.length;i++){
				farbe[i]=farberkennen(i);
				symbole[i]=kartenerkennung(i);
			}
			Arrays.sort(symbole);
			if (onepair()) {
				onepairzaehler++;
			}
			if (twopair()) {
				twopairzaehler++;
			}
			if (threeOAK()) {
				drillingzaehler++;
			}
			if (fourOAK()) {
				viererzaehler++;
			}
			if (straight()) {
				straightzaehler++;
			}
			if (flush()) {
				flushzaehler++;
			}
			if(flush() && straight()) {
				straightflushzaehler++;
			}
			if(royalflush()) {
				royalflushzaehler++;
			}
			if (fullhouse()) {
				fullhousezaehler++;
			}
		}
		int temp=onepairzaehler+twopairzaehler+drillingzaehler+viererzaehler+straightzaehler+flushzaehler+fullhousezaehler+straightflushzaehler+royalflushzaehler;
		System.out.println("Wahrscheinlichtkeit Royalflush: "+(royalflushzaehler/Versuch)*100.0000000000);
		System.out.println("Wahrscheinlichtkeit Straightflush:"+(straightflushzaehler/Versuch)*100.00000000);
		System.out.println("Wahrscheinlichtkeit Vierer:"+(viererzaehler/Versuch)*100.00000000);
		System.out.println("Wahrscheinlichtkeit Fullhouse:"+(fullhousezaehler/Versuch)*100.00000000);
		System.out.println("Wahrscheinlichtkeit Flush:"+(flushzaehler/Versuch)*100.00000000);
		System.out.println("Wahrscheinlichtkeit Straight:"+(straightzaehler/Versuch)*100.00000000);
		System.out.println("Wahrscheinlichtkeit Drilling:"+(drillingzaehler/Versuch)*100.00000000);
		System.out.println("Wahrscheinlichtkeit Twopair:"+(twopairzaehler/Versuch)*100.00000000);
		System.out.println("Wahrscheinlichtkeit Onepair:"+(onepairzaehler/Versuch)*100.00000000);
		System.out.println("Wahrscheinlichtkeit Highcard:"+((Versuch-temp)/Versuch)*100.00000000);
	}
	static void kartenfuellen (){
		for (int i=1;i<=KARTENANZAHL;i++){
			kartendeck[i-1]=i;
		}
	}
	static int rnd() {
		Random zufall=new Random();
		int zufallszahl=zufall.nextInt(kartendeck.length-1);
		return zufallszahl;
	}
	static void ziehen() {
		for (int i = 0; i<hand.length;i++) {
			hand[i]=rnd();
		}
		for (int i = 0; i<hand.length;i++) {
			for (int k = i+1; k<hand.length -1 ; k++ ){
			 if( hand[i]==hand[k]) { 
				 hand[k]=rnd();
			 }
			}	
		}
	}
	static int farberkennen( int i){
		int y=hand[i]%FARBANZAHL;
		return y;
	}
	static int kartenerkennung(int i){
		int kartenwert=hand[i%5]%(KARTENANZAHL/FARBANZAHL);
		return kartenwert;
	}
	static boolean checkzweier(int situation) {
		int temp=situation;
		paarwert[temp]=1;
		int zaehler=0;
		int mehrerepairs=0;
		for (int i = 0; i<hand.length-1; i++) {
			for(int k=i+1; k<hand.length;k++) {
				if (symbole[i]==symbole[k]) {
					paarwert[i]=symbole[i];
					zaehler++;
					if (situation==1) { //onepair
						if (zaehler == 1) {
							return true;
						}
					}
					if (situation==2) { //twopair
						if (symbole[i]!=symbole[k+1]) {
							mehrerepairs++;
						}
						if (mehrerepairs==2) {
							return true;
						}
					}
					if (situation==3) { //drilling
						if (zaehler == 2) {
							return true;
						}
					}
					if (situation==4) { //vierling
						if (zaehler == 3) {
							return true;
						}
					}
				}
			}
			zaehler=0;
		}
		return false;
	}
	static boolean onepair() {
		situation=1;
		if (checkzweier(situation)) {
				return true;
			}
		return false;
		}
	static boolean twopair() {
		situation=2;
		if (checkzweier(situation)) {
				if(paarwert[0] == paarwert[1])
					return true;
			}
		return false;
		}
	static boolean threeOAK() {
		situation=3;
		if (checkzweier(situation)) {
			if(paarwert[0]==paarwert[1] && paarwert[1]==paarwert[2]){
				return true;
			}
		}
		return false;
	}
	static boolean fourOAK() {
		situation=4;
		if (checkzweier(situation)) {
			if(paarwert[0]==paarwert[1] && paarwert[1]==paarwert[2] && paarwert[2]==paarwert[3]) {
				return true;
			}
		}
		return false;
	}
	static boolean fullhouse() {
		if (threeOAK() && onepair()) {
			return true;
		}
		return false;
	}
	static boolean straight() {
		int temp[]=new int [hand.length];
		for(int i=0;i<hand.length;i++) {
			temp[i]=symbole[i];
		}
		Arrays.sort(temp);
		if((temp[0]+1)==temp[1] && (temp[0]+2)==temp[2] && (temp[0]+3)==temp[3] && (temp[0]+4)==temp[4]) {
			return true;
		}
		return false;
	}
	static boolean flush() {
		if(farbe[0]==farbe[1] && farbe[0]==farbe[2] && farbe[0]==farbe[3] && farbe[0]==farbe[4]) {
			return true;
		}
		return false;
	}
	static boolean royalflush() {
		int temp[]=new int [hand.length];
		for(int i=0;i<hand.length;i++) {
			temp[i]=symbole[i];
		}
		Arrays.sort(temp);
		if(flush() && straight() && temp[4]==12) {
			return true;
		}
		return false;
	}
}