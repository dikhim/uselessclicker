package org.dikhim.jclicker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.dikhim.jclicker.ui.controllers.ChooseLanguageDialogController;
import org.dikhim.jclicker.ui.controllers.ShortcutRecordingDialogController;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.prefs.Preferences;

public class WindowManager {
    private static WindowManager windowManager;
    private static ResourceBundle resourceBundle;
    private Preferences preferences = Preferences.userRoot().node(getClass().getName());

    private Map<String, Stage> stageMap = new HashMap<>();
    private Map<String, Scene> sceneMap = new HashMap<>();
    private Locale locale;


    public static WindowManager getInstance() {
        if (windowManager == null) {
            windowManager = new WindowManager();
        }
        return windowManager;
    }


    private WindowManager() {
        this.locale = Dependency.getConfig().localization().getLocale();
        resourceBundle = ResourceBundle.getBundle("i18n/WindowManager", locale);
    }

    public void showStage(String stageName) {
        Stage stage = getStage(stageName);
        stage.show();
        stage.toFront();
    }


    private Scene getScene(String sceneName) {
        Scene scene = sceneMap.get(sceneName);
        if (scene != null) return scene;

        try {
            if ("main".equals(sceneName)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/main/MainScene.fxml"));
                loader.setResources(ResourceBundle.getBundle("i18n/MainScene", locale));
                Parent root = loader.load();

                scene = new Scene(root);
            } else if ("help".equals(sceneName)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/main/HelpScene.fxml"));
                loader.setResources(ResourceBundle.getBundle("i18n/HelpScene", locale));
                Parent root = loader.load();

                scene = new Scene(root);
            } else if ("about".equals(sceneName)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/main/AboutScene.fxml"));
                loader.setResources(ResourceBundle.getBundle("i18n/AboutScene", locale));
                Parent root = loader.load();

                scene = new Scene(root);
            } else if ("settings".equals(sceneName)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/config/ConfigScene.fxml"));
                loader.setResources(ResourceBundle.getBundle("i18n/SettingsScene", locale));
                Parent root = loader.load();

                scene = new Scene(root);
            } else if ("server".equals(sceneName)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/server/ServerScene.fxml"));
                loader.setResources(ResourceBundle.getBundle("i18n/ServerScene", locale));
                Parent root = loader.load();

                scene = new Scene(root);
            } else {
                throw new IllegalArgumentException("wrong scene name :" + sceneName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }


    public Stage getStage(String stageName) {
        Stage stage = stageMap.get(stageName);
        if (stage != null) return stage;
        if ("main".equals(stageName)) {
            stage = new Stage();
            stage.setScene(getScene("main"));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/cursor.png")));
            stage.setOnCloseRequest(event -> stageMap.forEach((k, v) -> v.hide()));
            stageMap.put("main", stage);
        } else if ("help".equals(stageName)) {
            stage = new Stage();
            stage.setScene(getScene("help"));
            stage.setTitle(resourceBundle.getString("help"));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/help.png")));
            stageMap.put("help", stage);
        } else if ("settings".equals(stageName)) {
            stage = new Stage();
            stage.setScene(getScene("settings"));
            stage.setTitle(resourceBundle.getString("settings"));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/config.png")));
            stageMap.put("settings", stage);
        } else if ("about".equals(stageName)) {
            stage = new Stage();
            stage.setScene(getScene("about"));
            stage.setTitle(resourceBundle.getString("about"));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/info.png")));
            stageMap.put("about", stage);

        } else if ("server".equals(stageName)) {
            stage = new Stage();
            stage.setScene(getScene("server"));
            stage.setTitle(resourceBundle.getString("server"));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/server.png")));
            stageMap.put("server", stage);
        } else {
            throw new IllegalArgumentException("wrong stage name :" + stageName);
        }
        return stage;
    }


    public File openScriptFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resourceBundle.getString("open"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(resourceBundle.getString("allTypes"), "*.*"),
                new FileChooser.ExtensionFilter("*.js", "*.js"));

        String pathFolder = preferences.get("last-opened-script-folder", "");
        if (!pathFolder.isEmpty()) {
            fileChooser.setInitialDirectory(new File(pathFolder));
        }
        File file = fileChooser.showOpenDialog(getStage("main"));
        if (file != null) {
            preferences.put("last-opened-script-folder", file.getParentFile().getAbsolutePath());
        }
        return file;
    }

    public File saveScriptFileAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resourceBundle.getString("saveAs"));
        fileChooser.setInitialFileName("newFile.js");

        String pathFolder = preferences.get("last-opened-script-folder", "");
        if (!pathFolder.isEmpty())
            fileChooser.setInitialDirectory(new File(pathFolder));

        File file = fileChooser.showSaveDialog(getStage("main"));
        if (file != null) {
            preferences.put("last-opened-script-folder", file.getParentFile().getAbsolutePath());
        }
        return file;
    }


    public File openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resourceBundle.getString("open"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(resourceBundle.getString("allTypes"), "*.*"));

        String pathFolder = preferences.get("last-opened-folder", "");
        if (!pathFolder.isEmpty()) {
            fileChooser.setInitialDirectory(new File(pathFolder));
        }
        File file = fileChooser.showOpenDialog(getStage("main"));
        if (file != null) {
            preferences.put("last-opened-folder", file.getParentFile().getAbsolutePath());
        }
        return file;
    }


    public File openImageFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resourceBundle.getString("open"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(resourceBundle.getString("allTypes"), "*.*"),
                new FileChooser.ExtensionFilter("*.png", "*.png"));

        String pathFolder = preferences.get("last-opened-image-folder", "");
        if (!pathFolder.isEmpty()) {
            fileChooser.setInitialDirectory(new File(pathFolder));
        }
        File file = fileChooser.showOpenDialog(getStage("main"));
        if (file != null) {
            preferences.put("last-opened-image-folder", file.getParentFile().getAbsolutePath());
        }
        return file;
    }


    public File saveImageFileAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resourceBundle.getString("saveAs"));
        fileChooser.setInitialFileName("image.png");

        String pathFolder = preferences.get("last-opened-image-folder", "");
        if (!pathFolder.isEmpty())
            fileChooser.setInitialDirectory(new File(pathFolder));

        File file = fileChooser.showSaveDialog(getStage("main"));
        if (file != null) {
            preferences.put("last-opened-image-folder", file.getParentFile().getAbsolutePath());
        }
        return file;
    }

    public String showImageInputDialog() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(resourceBundle.getString("imageInputDialog.title"));
        dialog.setHeaderText(resourceBundle.getString("imageInputDialog.header"));
        dialog.setContentText(resourceBundle.getString("imageInputDialog.content"));
        dialog.setGraphic(null);

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/images/24/download.png").toString()));
        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    public static String showChooseLanguageDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WindowManager.class.getResource("/fxml/ChooseLanguageDialogScene.fxml"));
            GridPane page = (GridPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(" ");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(getStage("main"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image(WindowManager.class.getClass().getResourceAsStream("/images/24/globe.png")));

            // Set the person into the controller.
            ChooseLanguageDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.getSelectedLanguage();
        } catch (IOException e) {
            e.printStackTrace();
            return "en";
        }
    }

    public static String showShortcutRecordingDialog(String stageName) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WindowManager.class.getResource("/fxml/ShortcutRecordingDialogScene.fxml"));
            loader.setResources(resourceBundle);
            GridPane page = (GridPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(" ");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getInstance().getStage(stageName));
            //dialogStage.initOwner(getStage("main"));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image(WindowManager.class.getClass().getResourceAsStream("/images/keyboard.png")));

            // Set the person into the controller.
            ShortcutRecordingDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.getShortcut();
        } catch (IOException e) {
            e.printStackTrace();
            return "en";
        }
    }


}
