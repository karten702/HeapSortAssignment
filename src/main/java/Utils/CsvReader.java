package main.java.Utils;

import main.java.Kickstarter;
import main.java.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private String sourceName;

    public CsvReader(String source){
        sourceName = source;
    }

    public List<Kickstarter> getContent() throws IOException {
        InputStream in = Main.class.getResourceAsStream(sourceName);
        BufferedReader reader=new BufferedReader(new InputStreamReader(in));

        List<String> crashes = new ArrayList<>();
        List<Kickstarter> projectList = new ArrayList<>();

        String line;
        String splitter = ",";
        int counter = 0;
        while( (line=reader.readLine()) !=null) {
            try {
                line = line.replace("\"", "");
                String[] projectString = line.split(splitter);

                if(counter != 0)
                    projectList.add(new Kickstarter(Integer.parseInt(projectString[1]),
                            Double.parseDouble(projectString[9]),
                            Double.parseDouble(projectString[7]),
                            projectString[3],
                            projectString[2]));
            }
            catch (NumberFormatException e){
                crashes.add("Crashed :( - Counter nr: " + counter);
            }
            finally {
                counter++;
            }
        }
        in.close();
        System.out.println("Crashes size: " + crashes.size());
        crashes.forEach(System.out::println);

        return projectList;
    }

}
