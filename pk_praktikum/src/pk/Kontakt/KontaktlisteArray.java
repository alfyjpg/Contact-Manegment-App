package pk.Kontakt;

import java.util.Arrays;
import java.util.Objects;

public class KontaktlisteArray {

	private Kontakt[] kontaktArray = new Kontakt[2];
	private int size;

	// constructor
	public KontaktlisteArray() {

		kontaktArray = new Kontakt[2];
		this.size = 0;

	}

	public void hinzufuegen(Kontakt kontakt) {
		if (kontakt == null) {
			return;
		} else {
			if (size < kontaktArray.length) {

				kontaktArray[size] = kontakt;
				size++;
			}

			else {
				kontaktArray = Arrays.copyOf(kontaktArray, kontaktArray.length + 2);
				kontaktArray[size] = kontakt;
				size++;
			}
		}
	}

	public void druckeAlleKontakte() {

		// Nulität Absichern
		if (kontaktArray == null) {
			return;
		} else {

			for (int i = 0; i < kontaktArray.length; i++) {
//				 Verwendung toString
				System.out.println(kontaktArray[i] + "\n");
				
				// Verwendung von drucke mit OutputStream 
				//kontaktArray[i].drucke(System.out);
			}
		}

	}

	public int gibAnzahlKontakte() {
		return size;
	}

	public Kontakt[] findeKontakteMitNamen(String nachname) {
		int biggy = 0;
		Kontakt[] kontakten = new Kontakt[1];

		// Nullität absichern
		if (kontaktArray == null || nachname == null) {
			return null;
		}

		else {

			for (int i = 0; i < kontaktArray.length; i++) {
				if (biggy < kontakten.length) {
					String name = kontaktArray[i].getSuchkriterium();
					if (Objects.equals(nachname, name)) {
						kontakten[biggy] = kontaktArray[i];
						biggy++;
						kontakten = Arrays.copyOf(kontakten, kontakten.length + 1);
					}

				}

			}
		}
		if (biggy != 0) {
			return kontakten;
		} else {
			return null;
		}

	}

}
