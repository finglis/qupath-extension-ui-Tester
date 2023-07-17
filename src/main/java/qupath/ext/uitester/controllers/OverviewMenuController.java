package qupath.ext.uitester.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qupath.lib.gui.QuPathGUI;
import qupath.lib.gui.dialogs.Dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
Command to open UI Tester Overview interface from extension menu
**/

public class OverviewMenuController implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(OverviewController.class);
    private final QuPathGUI qupath;
    //TODO: sort ui resources path?
    private final ResourceBundle resources = ResourceBundle.getBundle("qupath.ext.uitester.ui.strings");
    private Stage stage;
    public OverviewMenuController(QuPathGUI qupath) {
        this.qupath = qupath;
    }

    @Override
    public void run() {
        if (stage == null) {
            try {
                stage = createStage();
            } catch (IOException e) {
                Dialogs.showErrorMessage(resources.getString("title"),
                        resources.getString("error.initializing"));
                logger.error(e.getMessage(), e);
                return;
            }
        }
        stage.show();
    }

    private Stage createStage() throws IOException {
        System.out.println("The issue is here");
        URL url = getClass().getResource("overview.fxml");
        if (url == null) {
            throw new IOException("Cannot find URL for UI Tester Overview FXML");
        }
        Parent root = FXMLLoader.load(url, resources);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initOwner(qupath.getStage());
        stage.setScene(scene);
        stage.setTitle(resources.getString("title"));
        return stage;
    }

}
