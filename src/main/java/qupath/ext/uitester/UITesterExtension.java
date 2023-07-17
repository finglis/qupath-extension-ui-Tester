package qupath.ext.uitester;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qupath.ext.uitester.controllers.OverviewMenuController;
import qupath.lib.common.Version;
import qupath.lib.gui.QuPathGUI;
import qupath.lib.gui.dialogs.Dialogs;
import qupath.lib.gui.extensions.QuPathExtension;
import qupath.lib.gui.prefs.PathPrefs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UITesterExtension implements QuPathExtension {
	
	private final static Logger logger = LoggerFactory.getLogger(UITesterExtension.class);

	private final static String EXTENSION_NAME = "QuPath UI Tester";

	private final static String EXTENSION_DESCRIPTION = "Extension to allow quick access to UI elements used in QuPath for quick style,format and design testing purposes";

	private final static Version EXTENSION_QUPATH_VERSION = Version.parse("v0.4.0");

	private boolean isInstalled = false;

	private BooleanProperty enableExtensionProperty = PathPrefs.createPersistentPreference(
			"enableExtension", true);

	@Override
	public void installExtension(QuPathGUI qupath) {
		if (isInstalled) {
			logger.debug("{} is already installed", getName());
			return;
		}
		isInstalled = true;
		addPreference(qupath);
		addMenuItems(qupath);
	}

	private void addPreference(QuPathGUI qupath) {
		qupath.getPreferencePane().addPropertyPreference(
				enableExtensionProperty,
				Boolean.class,
				"Enable my extension",
				EXTENSION_NAME,
				"Enable my extension");
	}

	private void addMenuItems(QuPathGUI qupath) {
		var menu = qupath.getMenu("Extensions>" + EXTENSION_NAME, true);
		MenuItem menuItem = new MenuItem("Simple JavaFX");

		menuItem.setOnAction(e -> {
			Dialogs.showMessageDialog(EXTENSION_NAME,
					"Hello! This is my Java extension.");
		});
		menuItem.disableProperty().bind(enableExtensionProperty.not());
		menu.getItems().add(menuItem);

		//Overview UI test using JavaFXML
		MenuItem menuItem2 = new MenuItem("Overview");
		OverviewMenuController command = new OverviewMenuController(qupath);
		menuItem2.setOnAction(e -> command.run());
		menuItem2.disableProperty().bind(enableExtensionProperty.not());
		menu.getItems().add(menuItem2);
//		System.out.println(menu);

	}

	@Override
	public String getName() {
		return EXTENSION_NAME;
	}

	@Override
	public String getDescription() {
		return EXTENSION_DESCRIPTION;
	}
	
	@Override
	public Version getQuPathVersion() {
		return EXTENSION_QUPATH_VERSION;
	}

}
