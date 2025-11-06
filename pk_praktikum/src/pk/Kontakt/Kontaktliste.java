package pk.Kontakt;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import java.util.Objects;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pk.Kontakt.ui.DialogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Kontaktliste {

	HashSet<Kontakt> contacts;

	public Kontaktliste() {

		contacts = new HashSet<>();
	}

	public void hinzufuegen(Kontakt kontakt) {

		if (kontakt == null) {
			return;
		}
		try {
			if (this.hasAContact(kontakt)) {
				throw new DoppelterKontaktException("Dublette Einträge sind nicht erlaubt ");
			} else {

				contacts.add(kontakt);
				// System.out.println(kontakt.getSuchkriterium() + " wurde erfolgreich
				// hinzugefügt\n");
			}
		} catch (DoppelterKontaktException exp) {
			JOptionPane.showMessageDialog(null, exp.getMessage());

		}

	}

	// Using Iterator
	// Rückgabe Typ ist korrigiert!
	public Kontakt[] findeKontakteMitNamen(String name) {
		if (name == null || contacts == null) {
			return null;
		}

		int pos = 0;
		Kontakt[] liste = new Kontakt[1];
		Iterator<Kontakt> it = contacts.iterator();

		while (it.hasNext()) {
			Kontakt saver = it.next();
			if (Objects.equals(name, saver.getSuchkriterium())) {
				liste[pos] = saver;
				liste = Arrays.copyOf(liste, 1);
				pos++;
			}

		}
		return liste;
	}

	// Using erweiterte For Schleife
	public void druckeAlleKontakte() {

		if (contacts == null || contacts.isEmpty()) {
			return;
		} else {

			// ArrayList, weil HashSet behält kein Reihenfolge der Elementen
			ArrayList<Kontakt> set = new ArrayList<>(contacts);
			Collections.sort(set);
			for (Kontakt print : set) {
//				 usting toString
				System.out.println(print);

				// using drucke Method mit OutputStream
				print.drucke();

			}
		}
	}

	// private sorting method
	public int gibAnzahlKontakte() {
		return contacts.size();
	}

	public void exportiereEintraegeAlsVcard(File datei) throws IOException {

		try (FileOutputStream fos = new FileOutputStream(datei);
				BufferedOutputStream boss = new BufferedOutputStream(fos)) {

			for (Kontakt print : contacts) {
				boss.write(print.exportiereAlsVcard().getBytes());

				boss.flush();
			}
		}

	}

	public void exportiereEintraegeAlsVcardNio(File datei) throws IOException {
		// 1)StringBuilder str = new StringBuilder();
		Path path = datei.toPath();
		Iterator<Kontakt> it = contacts.iterator();
		// 2) String str = "";
		while (it.hasNext()) {
			Kontakt print = it.next();

			/*
			 * Schreiben der Daten am Ende kann durch OpenOption Interface wie
			 * StandardOpenOption oder einfach durch StringBuilder bzw String
			 *
			 * 2) // str = str + print.exportiereAlsVcard();
			 * 
			 * 1) //str.append(print.exportiereAlsVcard());
			 * 
			 * = //Files.writeString(path, str);
			 */
			Files.writeString(path, print.exportiereAlsVcard(), StandardOpenOption.APPEND);
		}

	}

	// Speichern oder beladen
	public void speichern() {
		// Leider es wurde nicht gesagt, ob die Methode Parameter bekommt oder nicht
		// Auch wurde nicht gesagt, ob die Dateipfad ist bekannt!
		// Deswegen lasse ich das System das File/Datei im Workingspace erstellen
		File f = new File("contacts.ser");
		try (FileOutputStream fos = new FileOutputStream(f); ObjectOutputStream oos = new ObjectOutputStream(fos)) {

			oos.writeObject(this.contacts);
//			DialogUtil.showMessageDialog("Bestätigung", "Erfolgreich serialisiert");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// laden oder entladen // ohne Rückgabe
	public void laden() {
		// Leider es wurde nicht gesagt, ob die Methode Parameter bekommt oder nicht
		// Auch wurde nicht gesagt, ob die Dateipfad ist bekannt!
		File f = new File("contacts.ser");
		try (FileInputStream fis = new FileInputStream(f); ObjectInputStream ois = new ObjectInputStream(fis)) {

			this.contacts = (HashSet<Kontakt>) ois.readObject();
//			DialogUtil.showMessageDialog("Bestätigung", "Erfolgreich deserialisiert");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ObservableList<Kontakt> ladenForGUI() {
		// Leider es wurde nicht gesagt, ob die Methode Parameter bekommt oder nicht
		// Auch wurde nicht gesagt, ob die Dateipfad ist bekannt!
		File f = new File("contacts.ser");
		HashSet<Kontakt> serializedContacts = new HashSet<>();
		try (FileInputStream fis = new FileInputStream(f); ObjectInputStream ois = new ObjectInputStream(fis)) {

			serializedContacts = (HashSet<Kontakt>) ois.readObject();
//			DialogUtil.showMessageDialog("Bestätigung", "Erfolgreich deserialisiert");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ObservableList<Kontakt> obContact = FXCollections.observableArrayList(serializedContacts);
		return obContact;
	}

	public HashSet<Kontakt> getContacts() {
		return contacts;
	}

	public boolean hasAContact(Kontakt contact) {

		return contacts.contains(contact);
	}

	public void obList(ObservableList<Kontakt> oblist) {

		contacts.addAll(oblist);
	}

	public boolean isEmpty() {

		return contacts.isEmpty();
	}

}