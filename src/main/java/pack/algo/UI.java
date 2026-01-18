package pack.algo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class UI {
    // Light Gray
    String color1 = "#E2E8F0";
    // Dark Gray
    String color2 = "#2B2B2B";
    // Medium Gray
    String color3 = "#9CA3AF";
    // Cool Gray
    String color4 = "#CCDBDC";
    // Neutral Gray
    String color5 = "#8B8B8B";

    // Textarea to Display the Calculation Result and Execution Time
    private TextArea result = new TextArea();
    // Time It Took To Execute the Calculation
    private Label executionTime = new Label();
    // Textfields Where To Input Source and Destination
    private TextField[] input = new TextField[2];
    // Radio Buttons To Select Which Metric To Calculate on
    /* Options Are:
     * 1-the Shortest Path Needed to Cut the Distance from Src to Dest
     * 2-the Fastest Time it to Reach Dest from Src
     * 3-Both Option 1 and 2*/
    private RadioButton[] rb = new RadioButton[3];
    // The Option the User Chooses to Run Calculations Based on
    private int option = 0;
    // The Buttons that Fires the Actions (Calculate index0, Read index1)
    private Button[] buttons = new Button[2];
    // The Graph that The Program will Work on
    private Graph g = null;

    // The Method That Will be Used to Call the UI in The Driver
    public VBox p() {
        configure();
        // an Array of Labels for Easier Management
        Label[] l = new Label[]{
                new Label("Graph Calculator"), new Label("Find The Optimal Path between Two Graphs"),
                new Label("Source & Destination"), new Label("Calculation Option"), new Label("Results")};

        labelConfig(l[0], 24, color1);
        for (int i = 1; i < l.length; i++)
            labelConfig(l[i], 11, color4);

        // Holds the Header Labels
        VBox header = new VBox(5, l[0], l[1]);
        // Holds the Input Textfields
        VBox inputVbox = new VBox(15, input);
        // Holds the VBOX above with the Label of Source & Destination
        VBox inputSection = new VBox(10, l[2], inputVbox);
        // HBox to hold the Radio Buttons
        HBox rbHbox = new HBox(12, rb);
        // Holds the Label for the Options as well as The Radio Buttons HBox
        VBox rbSection = new VBox(10, l[3], rbHbox);
        // Holds the Results Label, Textarea, and Execution Time Label
        VBox resultSection = new VBox(10, l[4], result, executionTime);
        // Holds the Action Buttons ( Calculate Path, Read File )
        HBox buttonsHBox = new HBox(50, buttons);
        // The Main Pane that Holds all the Elements above
        VBox mainPane = new VBox(25, header, inputSection, rbSection, buttonsHBox, resultSection);
        // Setting Padding for the Elements
        mainPane.setPadding(new Insets(40));
        inputVbox.setPadding(new Insets(20));
        rbHbox.setPadding(new Insets(20));
        // Setting Alignment for some Elements
        mainPane.setAlignment(Pos.TOP_CENTER);
        header.setAlignment(Pos.CENTER);
        // CSS Style String so it be Written once without Redundancy
        String dupeStyle = "-fx-background-color:" + color5 + ";-fx-border-color: " + color4 +
                ";-fx-background-radius: 14;-fx-border-radius: 12;-fx-border-width: 3;";
        // Main Pane has Slightly a Different Style
        mainPane.setStyle("-fx-background-color: " + color2 + ";-fx-border-color: " + color3 + ";-fx-border-width: 2");
        inputVbox.setStyle(dupeStyle);
        rbHbox.setStyle(dupeStyle);
        return mainPane;
    }

    // Configures Basic CSS for the Labels
    private void labelConfig(Label label, double fontSize, String color) {
        label.setStyle("-fx-font-family: Arial;" + "-fx-font-weight: bold;" + "-fx-font-size: " + fontSize + "px;" + "-fx-text-fill: " + color + ";");
    }

    // Configuring CSS and Actions for Textfields, Buttons, Radio Buttons, Textarea
    private void configure() {
        // To Set Prompt Text more Easily
        String[] inputLabels = {"Source Graph", "Destination Graph"};
        for (int i = 0; i < input.length; i++) {
            input[i] = new TextField();
            input[i].setPromptText("Enter " + inputLabels[i]);
            // CSS for the Textfields
            input[i].setStyle("-fx-background-color: " + color1 +
                    ";-fx-text-fill: " + color2 + ";-fx-prompt-text-fill: " + color2 + "; " +
                    "-fx-border-color: " + color2 + ";-fx-border-radius: 10; " +
                    "-fx-background-radius: 10;-fx-padding: 14 14 14 14;-fx-font-size: 14px;");
            input[i].setFont(Font.font("Arial", 15));
        }
        // Radio Button Group to Limit the User to 1 Option at a Time
        ToggleGroup group = new ToggleGroup();
        // To Set Names more Easily
        String[] options = {"Distance", "Time", "Both"};
        for (int i = 0; i < rb.length; i++) {
            rb[i] = new RadioButton(options[i]);
            rb[i].setToggleGroup(group);
            // CSS for the Radio Buttons
            rb[i].setStyle("-fx-text-fill: " + color2 + ";-fx-font-size: 15px;-fx-font-weight: bolder;-fx-padding: 14 16 14 16;");
            rb[i].setFont(Font.font("Arial", FontWeight.MEDIUM, 15));
        }
        // To Set Names more Easily
        String[] buttonsNames = {"Calculate", "Read File"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(buttonsNames[i]);
            // CSS for the Buttons
            buttons[i].setStyle("-fx-background-color: " + color1 +
                    ";-fx-text-fill:  " + color2 + ";-fx-font-weight: bold; " + "-fx-font-size: 16px; " + "-fx-padding: 16; " + "-fx-background-radius: 12; ");
            buttons[i].setPrefWidth(400);
        }
        // Sets the Actions for The Buttons
        buttons[0].setOnAction(e -> calculate());
        buttons[1].setOnAction(e -> readFile());
        // Sets Properties to the Textarea
        result.setPromptText("Results Will Appear Here");
        result.setEditable(false);
        result.setWrapText(true);
        result.setPrefRowCount(100);
        // CSS for the Textarea
        result.setStyle("-fx-control-inner-background: " + color1 + ";-fx-background-color: " + color1 +
                ";-fx-focus-color: transparent;-fx-faint-focus-color: transparent;-fx-highlight-fill: " + color2 +
                ";-fx-highlight-text-fill: " + color2 + ";-fx-text-fill: " + color2 + ";-fx-prompt-text-fill: " + color2 + ";-fx-border-color: " + color4 +
                ";-fx-border-radius: 12;-fx-background-radius: 15;-fx-border-width: 4;-fx-padding: 16;-fx-font-family: 'Courier New';-fx-font-size: 13px;");

        executionTime.setStyle("-fx-text-fill: #94a3b8; " + "-fx-font-size: 12px; " + "-fx-padding: 5 0 0 0;");
    }

    private void readFile() {
        // File Chooser to be able to Select Any File on the Computer
        FileChooser fc = new FileChooser();
        // Setting the Title for the File Chooser
        fc.setTitle("Select Graph File");
        File f = fc.showOpenDialog(null);
        // If Left Empty it Will Just Skip the Graph Creation Process
        if (f == null) return;

        Graph ng = new Graph();

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            /* Reads the First Line in the File
             * to Set the Radio Button and Textfields Later on*/
            String line;
            line = br.readLine();
            if (line == null) {
                showAlert(Alert.AlertType.ERROR, "Invalid File", "File is empty");
                return;
            }
            // Splits Each Line Based on Blank Space
            String[] first = line.trim().split(" ");
            if (first.length < 3) {
                showAlert(Alert.AlertType.ERROR, "Invalid File", "First Line Format is Invalid");
                return;
            }
            // Source
            String src = first[0];
            // Destination
            String dst = first[1];
            // Choice
            int choice = Integer.parseInt(first[2]);
            /*These Values will be Set for the Textfields*/
            // Set Textfields
            input[0].setText(src);
            input[1].setText(dst);

            /* Validates If the Option is Valid or Not
            then Sets the Radio Button Based on the option*/
            if (choice >= 1 && choice <= 3) {
                rb[choice - 1].setSelected(true);
                option = choice - 1;
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid File", "First Line Format is Invalid");
                return;
            }
            // Reads the Rest of the File
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ");
                // If Not 4 Parts it will Move to the Next Line
                if (parts.length != 4) continue;
                // Source
                String from = parts[0];
                // Destination
                String to = parts[1];
                // Distance Weight of the Edge
                double dist = Double.parseDouble(parts[2]);
                // Time Weight of the Edge
                double time = Double.parseDouble(parts[3]);
                // Add Edge to the Graph
                ng.addDirectedEdge(from, to, dist, time);
            }

        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "File Read Error", "Could Not Read The File");
            return;
        }
        /* If Everything Worked out it would Set the New Graph
        to the Class Graph and Display Alert of Success */
        g = ng;
        showAlert(Alert.AlertType.INFORMATION, "File Loaded", "Graph File Loaded Successfully");
    }


    // the Action To Calculate the Selected Option
    private void calculate() {
        // Gets The Selected Radio Button
        for (int i = 0; i < rb.length; i++) {
            if (rb[i].isSelected()) {
                option = i;
                break;
            }
            // To Check if no Option is Selected
            if (i == rb.length - 1) {
                showAlert(Alert.AlertType.WARNING, "Please Select an Option", "To Calculate You Should Press one of the Radio Buttons");
                return;
            }
        }
        // Source
        String src = input[0].getText().trim();
        // Destination
        String dst = input[1].getText().trim();
        // If Source Textfield Left Empty in the UI it will Alert the User to Fill it up
        if (src.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Fill The Source Textfield", "The Source Field is Empty Enter A Valid Source Graph");
            return;
        }
        // If Source Textfield Left Empty in the UI it will Alert the User to Fill it up
        if (dst.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Fill The Destination Textfield", "The Destination Field is Empty Enter A Valid Destination Graph");
            return;
        }
        // To have Data to Calculate on and Alert the User if not
        if (g == null) {
            showAlert(Alert.AlertType.WARNING, "Read The File First", "You Must Press Read File Before Calculating");
            return;
        }
        // To Save Output Results
        StringBuilder out = new StringBuilder();
        // To Run the Calculations on the Graph
        Calculations c = new Calculations();
        // Start Timer
        long start = System.nanoTime();
        switch (option) {
            case 0:
                c.run(g, src, 1);
                out.append(buildOutput(g, c, src, dst, "Distance", 1));
                break;
            case 1:
                c.run(g, src, 2);
                out.append(buildOutput(g, c, src, dst, "Time", 2));
                break;
            case 2:
                c.run(g, src, 1);
                out.append(buildOutput(g, c, src, dst, "Distance", 1));
                out.append("\n");
                c.run(g, src, 2);
                out.append(buildOutput(g, c, src, dst, "Time", 2));
                break;
        }
        // End Timer
        long end = System.nanoTime();
        // Show Output on Textarea
        result.setText(out.toString());
        // Show Execution Time
        executionTime.setText("Execution time: " + formatTime(start, end));
    }

    // Formats Calculation Result For Display
    private String buildOutput(Graph g, Calculations d, String src, String dst, String title, int option) {

        // Get Destination Vertex Index
        int t = g.indexOf(dst);

        // Check Invalid Destination or Unreachable Path
        if (t < 0 || d.dist == null || t >= d.dist.length || d.dist[t] == Calculations.MAX) {
            return title + ":\nNo Path Found\n";
        }

        // Build Path from Source to Destination
        myArrayList<String> path = d.buildNamePath(g, src, dst);

        // Handle Empty or Missing Path
        if (path == null || path.isEmpty()) {
            return title + ":\nNo Path Found\n";
        }

        // Construct Formatted Output
        StringBuilder sb = new StringBuilder();
        sb.append(title).append(":\n");

        // Append Total Cost
        if (option == 1)
            sb.append("Total Distance= ").append(String.format("%.2f", d.dist[t])).append("\n");
        else
            sb.append("Total Time= ").append(String.format("%.2f", d.dist[t])).append("\n");

        // Append Path Sequence
        sb.append("Path = ");
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) sb.append(" -> ");
            sb.append(path.get(i));
        }

        sb.append("\n");
        return sb.toString();
    }

    // Formats the Time Taken Between Start and End Into a Suitable Time Unit
    private String formatTime(long start, long end) {
        // Calculate Execution Time in Nanoseconds
        long nanos = end - start;
        if (nanos < 1000000)
            return nanos + " ns";
        // Convert Nanoseconds to Milliseconds
        long millis = nanos / 1000000;
        if (millis < 1000)
            return millis + " ms";
        // Convert Milliseconds to Seconds
        long seconds = millis / 1000;
        if (seconds < 60)
            return seconds + " s";
        // Convert Seconds to Minutes
        long minutes = seconds / 60;
        if (minutes < 60)
            return minutes + " min";
        // Convert Minutes to Hours
        long hours = minutes / 60;
        return hours + " h";
    }

    // a Simpler Way To Display Alerts and to Tidy the Code
    private void showAlert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
