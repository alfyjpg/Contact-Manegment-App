package pk.Kontakt.ui;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pk.Kontakt.Firma;
import pk.Kontakt.Kontakt;
import pk.Kontakt.Kontaktliste;
import pk.Kontakt.Person;

public class KontaktUI extends Application {

	private Kontaktliste liste = new Kontaktliste();
	private ObservableList<Kontakt> observableList = FXCollections.observableArrayList();
	private ListView<Kontakt> lv = new ListView<>(observableList);

	public void start(Stage primaryStage) throws Exception {

		// MenuBar
		MenuBar bar = new MenuBar();

		// Menu
		Menu datei = new Menu("Datei");
		Menu kontakte = new Menu("Kontakte");

		// Menu Items:

		// 1) Menu Items der Datei Menu:

		var laden = new MenuItem("Laden");
		var speicher = new MenuItem("Speichern");
		var export = new MenuItem("Export");
		var beenden = new MenuItem("Beenden");

		SeparatorMenuItem sp = new SeparatorMenuItem();
		datei.getItems().addAll(laden, speicher, new SeparatorMenuItem(), export, sp, beenden);

		// 2) Menu Items der Kontakt Menu:

		var person = new MenuItem("Person Hinzufügen");
		var firma = new MenuItem("Firma Hinzufügen");
		kontakte.getItems().addAll(person, firma);

		/*
		 * Event Handling:
		 * 
		 */

		// 1) Eventhandeln des Ladens
		laden.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (liste.isEmpty())
					DialogUtil.showErrorDialog("Meldung", "Kontaktliste ist einfach leer");

				KontaktUI.this.lv.getItems().addAll(liste.ladenForGUI());
				liste.obList(observableList);
				DialogUtil.showMessageDialog("Bestätigung", "Erfolgreich geladen");

			}

		});

		// 2) Eventhandeln der Speicherung
		speicher.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if (liste.isEmpty())
					DialogUtil.showErrorDialog("Meldung", "Kontaktliste ist einfach leer");

				liste.speichern();
				DialogUtil.showMessageDialog("Bestätigung", "Erfolgreich gespeichert");

			}

		});

		// 3) EventHandeln der vCard Export

		export.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File f = new File("");

				if (liste.isEmpty())
					DialogUtil.showErrorDialog("Meldung", "Kontaktliste ist einfach leer");
				else {
					try {
//				util.showMessageDialog("Hinweis", "Wählen sie eine Datei mit vcf Formatt aus");
						f = DialogUtil.showFileChooser("Eingabe", primaryStage);

						if (f.getName().isBlank())
							DialogUtil.showMessageDialog("Meldung", "Erfolgreich abgebrochen");
						else {
							boolean choice = DialogUtil.showConfirmDialog("Meldung",
									"Solle die Datei überschrieben werden?");

							if (choice == true) {

								try {
									KontaktUI.this.liste.exportiereEintraegeAlsVcard(f);

									DialogUtil.showMessageDialog("Bestätigung",
											"Datei wurde erfolgreich überschrieben!");
								} catch (IOException e) {
									DialogUtil.showErrorDialog("Error", "Fehlermeldung");
								}
							} else
								DialogUtil.showMessageDialog("Meldung", "Erfolgreich abgebrochen");
						}
					} catch (NullPointerException exp) {
						DialogUtil.showErrorDialog("Meldung", "Export Prozess wurde abgebrochen!");
					}
				}
			}
		});

		// 4) EventHandeln der Beendung

		beenden.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println(KontaktUI.this.liste.gibAnzahlKontakte());

				primaryStage.close();
//				System.exit(0);
			}

		});

		// 5) Eventhandlen des Hinzufügens einer Firma
		firma.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				FirmaView fw = new FirmaView(primaryStage, new Firma(), observableList, liste);
				fw.showView();
			}

		});

		// 6) Eventhandlen des Hinzufügens einer Person
		person.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				PersonView pv = new PersonView(primaryStage, new Person(), observableList, liste);
				pv.showView();
			}

		});

		bar.getMenus().addAll(datei, kontakte);

		var root = this.hauptFensterAufbau();
		root.setTop(bar);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

	}

	/*
	 * Hier steht die Elemente für GridPane und gibt wieder BorderPane zurück
	 */
	private BorderPane hauptFensterAufbau() {

		var text = new Text("Bitte selektieren Sie einen Kontakt");
		var gp = new GridPane();

		lv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Kontakt>() {

			@Override
			public void changed(ObservableValue<? extends Kontakt> observable, Kontakt oldValue, Kontakt newValue) {
				text.setText(newValue.drucke());
			}
		});

		gp.setPadding(new Insets(7));
		gp.setHgap(5);
		gp.setVgap(5);

		gp.add(lv, 0, 0, 1, 3);
		gp.add(text, 2, 0);
		var bp = new BorderPane();
		bp.setCenter(gp);
		return bp;

	}

	public static void main(String[] args) {
		launch(args);

	}

}
