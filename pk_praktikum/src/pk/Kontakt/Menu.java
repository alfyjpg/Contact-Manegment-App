package pk.Kontakt;

import java.util.Scanner;

import javax.swing.JOptionPane;
import java.util.InputMismatchException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4602220036655387393L;
	/**
	 * 
	 */
	private Kontaktliste liste;
	private int auswahl;
	Scanner sc;

	public Menu() {
		this.liste = new Kontaktliste();

		// Aus Demostrnationzwecke

		Adresse adPrivate = new Adresse("Westerman alle", 66, 22309, "Braunschweig", "Deutschland");
		Adresse adBeruflich = new Adresse("Nullendorf Straße", 21, 43209, "Berlin", "Deutschland");

		Person perso1 = new Person("Elalfy", "Mohamed", "0157355402699", "megaelalfy22@gmail.com", adPrivate,
				adBeruflich);

		Firma firma1 = new Firma("Siemens", "0123456", "Siemens@deutschland", adPrivate);

		liste.hinzufuegen(perso1);
		liste.hinzufuegen(firma1);
		this.printMenu();

	}

	// Menu hat ab jetzt eine Schleife
	private void printMenu() {
		boolean menu;
		String text = "Kontakt-App\r\n \n" + "1. Person hinzufügen\r\n" + "2. Drucke alle Kontakte\r\n"
				+ "3. Drucke Kontakte mit Name\r\n" + "4. vCard-Export\r\n" + "5. Lade aus datei\n"
				+ "6. Speichere in Datei\n" + "7. Beenden" + "\nBitte Aktion wählen:";

		boolean richtig = true;
		while (richtig)
			try {

				System.out.println(text);
				this.sc = new Scanner(System.in);
				this.auswahl = sc.nextInt();
				menu = auswahl > 7 || auswahl < 1;
				if (menu)
					System.err.println("\nZahlen  zwischen 1 - 5 sind nur erlaubt!");
				else
					richtig = false;

			} catch (InputMismatchException exp) {

				System.err.println("\nKeine gültige Eingabe, nur Nummern eingeben: ");
				sc.nextLine();

			}
		this.internMenu(auswahl);

	}

	private void internMenu(int choice) {

		switch (choice) {

		case 1:
			this.personHinzufuegen();
			this.printMenu();
			break;
		case 2:
			this.liste.druckeAlleKontakte();
			this.printMenu();
			break;
		case 3:
			String name = JOptionPane.showInputDialog(null, "Bitte den Nachname eingeben: ");
			this.druckeMitName(name);
			this.printMenu();
			break;
		case 4:
			String vCardName = null;
			try {
				do {
					vCardName = JOptionPane.showInputDialog("Geben sie dem Dateinamen ein: ");

					if (vCardName.isBlank()) {
						JOptionPane.showMessageDialog(null, "Bitte eine gültige Dateiname eingeben");
					}

				} while (vCardName.isBlank());
			} catch (NullPointerException exp) {
				System.err.println("\nErfoglreich abgebrochen");
				this.printMenu();
			}
			vCard2(vCardName);
			break;
		case 5:
			this.liste.laden();
			this.printMenu();
			break;
		case 6:
			this.liste.speichern();
			this.printMenu();
			break;
		case 7:
			System.out.println("Menü ist beendet, Danke!");
			sc.close();
			break;
		default:
			System.out.println("Etwas ist schief gelaufen!");
			this.printMenu();
		}

	}

	private void personHinzufuegen() {

		// Daten der Person eingeben lassen:
		String nachName = JOptionPane.showInputDialog(null, "Bitte geben Sie einen Nachnamen ein: ");
		String vorName = JOptionPane.showInputDialog(null, "Bitte geben Sie einen Vornamen ein:");

		// Geburtsdatum mit EXP //
		LocalDate geburtstag = null;
		boolean geschafft = true;
		try {
			while (geschafft) {
				try {
					CharSequence birthday = JOptionPane.showInputDialog(null, "Geburtsdatum Bitte in \"dd.MM.yyyy\" ");
					DateTimeFormatter frmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					geburtstag = LocalDate.parse(birthday, frmt);
					geschafft = false;

				} catch (DateTimeParseException exp) {
					JOptionPane.showMessageDialog(null,
							"Eingegebene Geburtsdatum muss den Mutser \"TT.MM.JJJJ\" entsprechen");
				}
			}
		} catch (NullPointerException exp) {
			System.err.println("Erfoglreich abgebrochen ");
			return;
		}

		String telefon = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Telefonnummer ein: ");
		String email = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Mail-Adresse ein: ");

		// Daten der Adresse:
		String straße = JOptionPane.showInputDialog(null, "Straße eingeben: ");

		// Hausnummer mit EXP //
		int hausNummer = 0;
		boolean gemacht = true;

		while (gemacht) {
			try {
				String haus = JOptionPane.showInputDialog(null, "Hausnummer eingeben");
				if (haus == null) {
					System.err.println("Erfoglreich abgebrochen ");
					return;
				}
				hausNummer = Integer.parseInt(haus);

				gemacht = false;
			} catch (NumberFormatException exp) {
				JOptionPane.showMessageDialog(null, "Bitte gültige Zahl eingeben");
			}
		}

		// PLZ mit EXP//
		int plzInt = 0;
		boolean fertig = false;

		while (fertig == false) {
			try {

				String plz = JOptionPane.showInputDialog(null, "Bitte geben Sie eine PLZ ein: ");

				if (plz == null) {
					System.err.println("Erfoglreich abgebrochen ");
					return;
				}
				plzInt = Integer.parseInt(plz);
				fertig = true;
			} catch (NumberFormatException exp) {
				JOptionPane.showMessageDialog(null, "Bitte gültige Zahl eingeben");
			}
		}

		String ort = JOptionPane.showInputDialog(null, "Bitte geben Sie einen Ort ein: ");
		String land = JOptionPane.showInputDialog(null, "Bitte geben Sie ein Land ein: ");

		// Adresse Objekt
		Adresse adPrivate = new Adresse(straße, hausNummer, plzInt, ort, land);

		// Person Objekt
		Person person = new Person(nachName, vorName, geburtstag, telefon, email, adPrivate, null);
		this.liste.hinzufuegen(person);

	}

	// Methode, die alle Kontakte mit dem Name zu drücken
	private void druckeMitName(String name) {

		Kontakt[] similars = this.liste.findeKontakteMitNamen(name);
		this.druckeArrayList(similars);

	}

	// Hilf Methode für die Methode druckeMitName
	private void druckeArrayList(Kontakt[] list) {

		for (Kontakt print : list)
			print.drucke();
	}

	private void vCard(String name) {

		File f = new File(name + ".vcf");
		int frage = -6;
		int creat = 9;

		if (!f.exists())

			creat = JOptionPane.showConfirmDialog(null,
					"Diese Datei existiert bis jetzt noch nicht, Soll ich erstellen?");

		if (f.exists())
			frage = JOptionPane.showConfirmDialog(null, "Solle die Datei überschrieben werden?");

		if (frage == JOptionPane.YES_OPTION || creat == JOptionPane.YES_OPTION) {
			try {
				// Solange das Sie keine Kontakt durch die oder zu Liste (Object in Menu)
				// hinzugeügt haben, würde nix
				// ausgegeben!
				throw new IOException("making the realdeal");

				// liste.exportiereEintraegeAlsVcard(f);

//				if (creat == JOptionPane.YES_OPTION)
//					System.out.println("Datei wurde erstellet und geschrieben!");
//				else
//					System.out.println("Datei wurde erfolgreich überschrieben!");

				// this.printMenu();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Fehlermeldung");
				this.printMenu();
			}

		} else if (frage == JOptionPane.NO_OPTION || creat == JOptionPane.NO_OPTION) {
			this.internMenu(4);

		} else {
			System.err.println("Erfolgreich abgrebrochen!");
			this.printMenu();
		}
	}
//	if (frage == JOptionPane.CANCEL_OPTION || creat == JOptionPane.CANCEL_OPTION)
//		} else {
//			JOptionPane.showMessageDialog(null, "Solche Dateiname existiert nicht. Bitte einen richtigen eingeben!");
//			this.internMenu(4);

	// }
	private void vCard2(String name) {

		File f = new File(name + ".vcf");
		int frage = JOptionPane.showConfirmDialog(null, "Solle die Datei überschrieben werden?", "Meldung",
				JOptionPane.YES_OPTION);

		if (frage == JOptionPane.YES_OPTION) {
			try {
				// Solange das Sie kein Kontakt durch die Menu
				// oder in der Attribute liste hinzugeügt haben
				// würde nix ausgegeben!

				liste.exportiereEintraegeAlsVcard(f);
				System.out.println("Datei wurde erfolgreich überschrieben!");
				this.printMenu();

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Fehlermeldung");
				this.printMenu();
			}

		} else if (frage == JOptionPane.NO_OPTION) {
			this.internMenu(4);

		} else {
			System.err.println("Erfolgreich abgrebrochen!");
			this.printMenu();
		}
//		} else {
//			JOptionPane.showMessageDialog(null, "Solche Dateiname existiert nicht. Bitte einen richtigen eingeben!");
//			this.internMenu(4);

		// }
	}

}
