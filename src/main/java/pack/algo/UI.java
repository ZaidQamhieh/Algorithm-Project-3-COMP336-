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
import java.util.ArrayList;

public class UI {
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

    private Graph g = null;

    // The Method That Will be Used to Call the UI in The Driver
    public VBox p() {
        configure();
        // an Array of Labels for Easier Management
        Label[] l = new Label[]{
                new Label("Graph Calculator"), new Label("Find The Optimal Path between Two Graphs"),
                new Label("Source & Destination"), new Label("Calculation Option"), new Label("Results")};

        labelConfig(l[0], 24, "#6366f1");
        for (int i = 1; i < l.length; i++)
            labelConfig(l[i], 11, "#cbd5e1");

        HBox rbHbox = new HBox(12, rb);
        VBox header = new VBox(5, l[0], l[1]);
        VBox inputVbox = new VBox(15, input);
        VBox inputSection = new VBox(10, l[2], inputVbox);
        VBox rbSection = new VBox(10, l[3], rbHbox);
        VBox resultSection = new VBox(10, l[4], result, executionTime);
        HBox buttonsHBox = new HBox(50, buttons);
        VBox pane = new VBox(25, header, inputSection, rbSection, buttonsHBox, resultSection);

        pane.setPadding(new Insets(40));
        inputVbox.setPadding(new Insets(20));
        rbHbox.setPadding(new Insets(20));

        pane.setAlignment(Pos.TOP_CENTER);
        header.setAlignment(Pos.CENTER);

        String dupeStyle = "-fx-background-color: rgba(15, 15, 30, 0.5);-fx-border-color: rgba(99, 102, 241, 0.15);" +
                "-fx-background-radius: 12;-fx-border-radius: 12;-fx-border-width: 1;";
        pane.setStyle("-fx-background-color: rgba(26, 26, 46, 0.9);-fx-border-color: rgba(99, 102, 241, 0.2);");
        inputVbox.setStyle(dupeStyle);
        rbHbox.setStyle(dupeStyle);
        return pane;
    }

    // Configures Basic CSS for the Labels
    private void labelConfig(Label label, double fontSize, String color) {
        label.setStyle(
                "-fx-font-family: Arial;" + "-fx-font-weight: bold;" +
                        "-fx-font-size: " + fontSize + "px;" + "-fx-text-fill: " + color + ";"
        );
    }

    private void configure() {
        String[] inputLabels = {"Source Graph", "Destination Graph"};
        for (int i = 0; i < input.length; i++) {
            input[i] = new TextField();
            input[i].setPromptText("Enter " + inputLabels[i]);
            input[i].setStyle("-fx-background-color: rgba(15, 15, 30, 0.6); " +
                    "-fx-text-fill: #e2e8f0;-fx-prompt-text-fill: #64748b; " +
                    "-fx-border-color: rgba(99, 102, 241, 0.3);-fx-border-radius: 10; " +
                    "-fx-background-radius: 10;-fx-padding: 14 14 14 14;-fx-font-size: 14px;");
            input[i].setFont(Font.font("Arial", 15));
        }

        ToggleGroup group = new ToggleGroup();
        String[] options = {"Distance", "Time", "Both"};
        for (int i = 0; i < rb.length; i++) {
            rb[i] = new RadioButton(options[i]);
            rb[i].setToggleGroup(group);
            rb[i].setStyle("-fx-text-fill: #cbd5e1;-fx-font-size: 14px;-fx-padding: 14 16 14 16;");
            rb[i].setFont(Font.font("Arial", FontWeight.MEDIUM, 15));
        }

        String[] buttonsNames = {"Calculate", "Read File"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(buttonsNames[i]);
            buttons[i].setStyle("-fx-background-color: linear-gradient(to bottom right, #6366f1, #8b5cf6); " +
                    "-fx-text-fill: white; " + "-fx-font-weight: bold; " + "-fx-font-size: 16px; " + "-fx-padding: 16; " + "-fx-background-radius: 12; ");
            buttons[i].setPrefWidth(400);
        }
        buttons[0].setOnAction(e -> calculate());
        buttons[1].setOnAction(e -> readFile());

        result.setPromptText("Results Will Appear Here");
        result.setEditable(false);
        result.setWrapText(true);
        result.setPrefRowCount(8);
        result.setStyle("-fx-control-inner-background: rgba(135,135,255,0.6); " +
                "-fx-text-fill: #e2e8f0;-fx-prompt-text-fill: #64748b; " +
                "-fx-border-color: rgba(99, 102, 241, 0.3);-fx-border-radius: 12;-fx-background-radius: 12; " +
                "-fx-padding: 16;-fx-font-family: 'Courier New';-fx-font-size: 13px;");

        executionTime.setStyle("-fx-text-fill: #94a3b8; " + "-fx-font-size: 12px; " + "-fx-padding: 5 0 0 0;");
    }

    private void readFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Graph File");
        File f = fc.showOpenDialog(null);
        if (f == null) return;

        Graph ng = new Graph();

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            // Reads The First Line in The File
            line = br.readLine();
            if (line == null) {
                showAlert(Alert.AlertType.ERROR, "Invalid File", "File is empty");
                return;
            }

            String[] first = line.trim().split(" ");
            if (first.length < 3) {
                showAlert(Alert.AlertType.ERROR, "Invalid File", "First line format is invalid");
                return;
            }

            String src = first[0];
            String dst = first[1];
            int choice = Integer.parseInt(first[2]);

            // Set Textfields
            input[0].setText(src);
            input[1].setText(dst);

            // Set Radio Buttons
            if (choice >= 1 && choice <= 3) {
                rb[choice - 1].setSelected(true);
                option = choice - 1;
            }

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length < 4) continue;

                String from = parts[0];
                String to = parts[1];
                double dist = Double.parseDouble(parts[2]);
                double time = Double.parseDouble(parts[3]);

                ng.addDirectedEdge(from, to, dist, time);
            }

        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "File Read Error", "Could Not Read The File");
            return;
        }

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
        String src = input[0].getText().trim();
        String dst = input[1].getText().trim();
        if (src.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Fill The Source Textfield", "The Source Field is Empty Enter A Valid Source Graph");
            return;
        }

        if (dst.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Fill The Destination Textfield", "The Destination Field is Empty Enter A Valid Destination Graph");
            return;
        }

        if (g == null) {
            showAlert(Alert.AlertType.WARNING, "Read The File First", "You Must Press Read File Before Calculating");
            return;
        }


        StringBuilder out = new StringBuilder();

        Dijkstra d = new Dijkstra();
        long start =System.nanoTime();
        switch (option) {
            case 0:
                d.run(g, src, 1);
                out.append(buildOutput(g, d, src, dst, "Distance"));
                break;
            case 1:
                d.run(g, src, 2);
                out.append(buildOutput(g, d, src, dst, "Time"));
                break;
            case 2:
                d.run(g, src, 1);
                out.append(buildOutput(g, d, src, dst, "Distance"));
                out.append("\n");
                d.run(g, src, 2);
                out.append(buildOutput(g, d, src, dst, "Time"));
                break;
        }
        long end = System.nanoTime();

        result.setText(out.toString());

        executionTime.setText("Execution time: " + formatTime(start, end));
    }

    private String buildOutput(Graph g, Dijkstra d, String src, String dst, String title) {
        int t = g.indexOf(dst);
        if (t < 0 || d.dist == null || t >= d.dist.length || d.dist[t] == Dijkstra.MAX) {
            return title + ":\nNo Path Found\n";
        }

        myArrayList<String> path = d.buildNamePath(g, src, dst);
        if (path == null || path.isEmpty()) {
            return title + ":\nNo Path Found\n";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(title).append(":\n");
        sb.append("Total = ").append(d.dist[t]).append("\n");
        sb.append("Path = ");
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) sb.append(" -> ");
            sb.append(path.get(i));
        }
        sb.append("\n");
        return sb.toString();
    }

    // Formats the Time Taken Between start and end Into a Suitable Time Unit
    private String formatTime(long start, long end) {
        long nanos = end - start;
        if (nanos < 1000000)
            return nanos + " ns";
        long millis = nanos / 1000000;
        if (millis < 1000)
            return millis + " ms";

        long seconds = millis / 1000;
        if (seconds < 60)
            return seconds + " s";

        long minutes = seconds / 60;
        if (minutes < 60)
            return minutes + " min";

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
