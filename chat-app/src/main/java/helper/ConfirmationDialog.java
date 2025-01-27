package helper;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ConfirmationDialog {
    private final String title;
    private final String message;

    public ConfirmationDialog(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public boolean showDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
        dialog.getDialogPane().setContent(textFlow);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
        return dialog.showAndWait()
                .filter(result -> result == ButtonType.OK)
                .isPresent();
    }
}
