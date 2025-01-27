package helper;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class WarningDialog {

    public WarningDialog(String title, String message) {
        Dialog<TextFlow> dialog = new Dialog<>();
        dialog.setTitle(title);
        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
        dialog.getDialogPane().setContent(textFlow);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }
}
