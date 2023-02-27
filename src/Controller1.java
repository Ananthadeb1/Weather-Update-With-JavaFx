import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller1 implements Initializable {

    @FXML
    private ChoiceBox<String> TempType;
    String tempTyp;

    private String[] temp = { "  Celsius ", "  Fahrenheit  " };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TempType.getItems().addAll(temp);
    }

    /**
     * @param e
     */
    // public void getTemp (ActionEvent e){
    // tempTyp = TempType.getValue();
    // }

    @FXML
    private Label WeatherShow;

    @FXML
    private TextField tf;

    @FXML
    void btnClicked(ActionEvent event) throws ParseException {
        String apiKey = "c302b6697e7349a2b66104308231302";
        String location = tf.getText(); // "India";
        String urlString = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + location;
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int responseCode = con.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                String inline = "";
                Scanner sc = new Scanner(url.openStream());

                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                sc.close();

                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline);

                // Get the required object from the above created object
                JSONObject obj = (JSONObject) data_obj.get("current");

                // Get the required data using its key
                if (TempType.getValue().equals("  Celsius "))
                    WeatherShow.setText("  Temp in " + location + " is : " + String.valueOf(obj.get("temp_c"))
                            + " degree " + "celsius");
                // System.out.println(obj.get("temp_c")+" C");
                else if (TempType.getValue().equals("  Fahrenheit  "))
                    WeatherShow.setText("  Temp in " + location + " is : " + String.valueOf(obj.get("temp_f"))
                            + " degree " + "fahrenheit");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
