package ttt;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.function.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import minestra.text.*;

public final class App extends Application implements Initializable {

    private static final ResourceSheaf res = ResourceSheaf.create().withMessages().withDefaultLocale();

    @FXML
    private MenuBar menuBar;

    @FXML
    private TextArea textArea;

    @FXML
    private CheckBox cbInputFromClipboard;

    @FXML
    private CheckBox cbOutputFromClipboard;

    public App() {
        //
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuBar.setUseSystemMenuBar(true);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(res.string("product.name"));
        stage.setScene(new Scene(new BorderPane()));
        stage.setWidth(800d);
        stage.setHeight(560d);
        stage.setOnCloseRequest(x -> {
            Platform.exit();
        });
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"), res.toResourceBundle());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    String readText() {
        return textArea.getText();
    }

    void writeText(String s) {
        textArea.setText(s);
    }

    void doEdit(UnaryOperator<String> f) {
        if (cbInputFromClipboard.isSelected()) {
            Clipboard cb = Clipboard.getSystemClipboard();
            Object x = cb.getContent(DataFormat.PLAIN_TEXT);
            if (x != null) {
                textArea.setText(x.toString());
            }
        }
        writeText(f.apply(readText()));
        if (cbOutputFromClipboard.isSelected()) {
            Clipboard cb = Clipboard.getSystemClipboard();
            Map<DataFormat, Object> m = new HashMap<>();
            m.put(DataFormat.PLAIN_TEXT, textArea.getText());
            cb.setContent(m);
        }
        Platform.runLater(() -> textArea.requestFocus());
    }

    @FXML
    void onButtonSortClicked() {
        doEdit(Trans::sort);
    }

    @FXML
    void onButtonReverseClicked() {
        doEdit(Trans::reverse);
    }

    @FXML
    void onButtonExpandClicked() {
        doEdit(Trans::expand);
    }

    @FXML
    void onButtonFlipClicked() {
        doEdit(Trans::flip);
    }

    @FXML
    void onButtonZipClicked() {
        doEdit(Trans::zip);
    }

    @FXML
    void onMenuOpenSelected() {
        FileChooser fc = new FileChooser();
        final File f = fc.showOpenDialog(null);
        // TODO impl
        // TODO check contentType
    }

    @FXML
    void onMenuCloseSelected() {
        Platform.exit();
    }

    @FXML
    void onMenuAboutSelected() {
        // TODO impl
    }

    String getVersion() {
        StringBuilder sb = new StringBuilder();
        // TODO impl
//        sb.append(message(".productName")).append(" version ");
//        try (InputStream is = App.class.getResourceAsStream("version")) {
//            if (is == null) {
//                sb.append("???");
//            }
//            else {
//                @SuppressWarnings("resource")
//                Scanner sc = new Scanner(is);
//                sb.append(sc.nextLine());
//            }
//        } catch (IOException | NoSuchElementException e) {
//            log.warn(() -> "App.version", e);
//            sb.append("?");
//        }
        return sb.toString();
    }

    public static void main(String[] args) {
        launch(App.class);
    }

}
