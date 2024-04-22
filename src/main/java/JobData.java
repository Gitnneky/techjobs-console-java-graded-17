import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by LaunchCode
 */
public class JobData {
private final static Logger LOGGER = Logger.getLogger(JobData.class.getName());

    private static final String DATA_FILE = "src/main/resources/job_data.csv";
    private static boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all  the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        return allJobs;
    }

    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {
        // load data, if not already loaded
        loadData();

value = value.toLowerCase();
        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);


            if (aValue != null && aValue.equalsIgnoreCase(value)){
                jobs.add(row);
            }
        }

        return jobs;
    }

    /**
     * Search all columns for the given term
     *
     * @param value The search term to look for
     * @return      List of all jobs with at least one field containing the value
     */
    public static ArrayList<HashMap<String, String>> findByValue(String value) {

        // load data, if not already loaded

        loadData();{

        // TODO - implement this method
       // return null;
    }

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();
    value = value.trim().toLowerCase();

    for (HashMap<String, String> row : allJobs){
        boolean jobAlreadyAdded = false;
        for(Map.Entry<String, String> entry: row.entrySet()){
                    String entryValue = entry.getValue();
                    if(entryValue != null && entryValue.toLowerCase().contains(value) && !jobAlreadyAdded){
                        jobs.add(row);
                        jobAlreadyAdded = true;
                    }
                }
            }


        return jobs;
    }


    public static void loadData()
    {


        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            int numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load job data", e);
        }

        }
    }