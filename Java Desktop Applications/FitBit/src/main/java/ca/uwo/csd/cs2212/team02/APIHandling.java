package ca.uwo.csd.cs2212.team02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.apis.service.FitbitOAuth20ServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.Hashtable;

/**
 * Class for sending API requests to retrieve user data
 */
public class APIHandling {
    private static String CALL_BACK_URI = "http://localhost:8080";
    private static int CALL_BACK_PORT = 8080;
    private static final String REQUEST_URL_PREFIX = "https://api.fitbit.com/1/user/3WGW2P/";
    private static final String[] DATA_TYPE = {"steps", "distance", "floors", "caloriesOut", "minutesSedentary", "minutesLightlyActive", "minutesFairlyActive", "minutesVeryActive"};

    private static final String[] DATA_TYPE1 = {"steps", "distance", "floors", "calories", "minutesSedentary", "minutesLightlyActive", "minutesFairlyActive", "minutesVeryActive"};
    private static final String[] HR_ZONE = {"Out of Range", "Fat Burn", "Cardio", "Peak"};
    private static final boolean testMode = App.isTestMode();
    private static Hashtable<String, JSONObject> backup;

    private static Hashtable<String, JSONObject> hashtable = new Hashtable<String, JSONObject>();

    /**
     * Gets Daily data
     *
     * @param type ID of the metric for which data should be returned
     * @param date Date that the user has selected
     * @return value for the metric for the day requested
     */
    public static double getDailyData(int type, Date date) throws APIException, IOException {

        String requestUrl, response = null;

        if (type != 5) {
            if (!testMode) {
                requestUrl = REQUEST_URL_PREFIX + "activities/" + DATA_TYPE1[type] + "/date/" + date.format() + "/1d.json";

                if (hashtable.get(requestUrl) == null) {
                    response = sendAPIRequest(requestUrl);
                    hashtable.put(requestUrl, new JSONObject(response));
                }

            } else {
                requestUrl = "src/main/resources/activities_" + DATA_TYPE1[type] + "_date_2016-01-08_1d-json.txt";
                if (hashtable.get(requestUrl) == null) {
                    response = getTestBody(requestUrl);
                    hashtable.put(requestUrl, new JSONObject(response));
                }

            }
            JSONObject obj = hashtable.get(requestUrl);
            JSONArray jarr = (JSONArray) obj.get("activities-" + DATA_TYPE1[type]);
            JSONObject index = (JSONObject) jarr.get(0);
            Double i = Double.parseDouble((String) index.get("value"));

            return i;
        } else {
            Double sum = 0.0;
            for (; type < 8; type++) {
                if (!testMode) {
                    requestUrl = REQUEST_URL_PREFIX + "activities/" + DATA_TYPE1[type] + "/date/" + date.format() + "/1d.json";
                    if (hashtable.get(requestUrl) == null) {
                        response = sendAPIRequest(requestUrl);
                        hashtable.put(requestUrl, new JSONObject(response));
                    }
                } else {
                    requestUrl = "src/main/resources/activities_" + DATA_TYPE1[type] + "_date_2016-01-08_1d-json.txt";
                    if (hashtable.get(requestUrl) == null) {
                        response = getTestBody(requestUrl);
                        hashtable.put(requestUrl, new JSONObject(response));
                    }
                }
                JSONObject obj = hashtable.get(requestUrl);
                JSONArray jarr = (JSONArray) obj.get("activities-" + DATA_TYPE1[type]);
                JSONObject index = (JSONObject) jarr.get(0);
                sum += Double.parseDouble((String) index.get("value"));
            }
            return sum;
        }
    }

    /**
     * Gets daily goal data
     *
     * @param type ID of the metric for which data should be returned
     * @return value of the goal for the metric
     */
    public static double getGoalData(int type) throws APIException, IOException {
        String requestUrl, response;
        if (!testMode) {
            requestUrl = REQUEST_URL_PREFIX + "activities/goals/daily.json";
            if (hashtable.get(requestUrl) == null) {
                response = sendAPIRequest(requestUrl);
                hashtable.put(requestUrl, new JSONObject(response));
            }
        } else {
            requestUrl = "src/main/resources/activities_date_2016-01-08-json.txt";
            if (hashtable.get(requestUrl) == null) {
                response = getTestBody(requestUrl);
                hashtable.put(requestUrl, new JSONObject(response));
            }
        }
        JSONObject obj = hashtable.get(requestUrl);
        JSONObject jr1 = (JSONObject) obj.get("goals");
        Double val;
        //Depending on the data type, Fitbit provides an int or a double
        //use try/catch to ensure the proper type is stored
        try {
            val = (Double) jr1.get(DATA_TYPE[type]);
        } catch (java.lang.ClassCastException e) {
            val = (Integer) jr1.get(DATA_TYPE[type]) * 1.0;
        }
        return val;

    }

    /**
     * Gets the number of minutes spent in each heart rate zone as well as the resting heart rate
     *
     * @param date Date for which the data has been requested
     * @return HeartRate object storing zone details and resting heart rate
     */
    public static HeartRate getHeartRate(Date date) throws APIException, IOException {
        try {
            String requestUrl, response;
            if (!testMode) {
                requestUrl = REQUEST_URL_PREFIX + "activities/heart/date/" + date.format() + "/1d.json";
                if (hashtable.get(requestUrl) == null) {
                    response = sendAPIRequest(requestUrl);
                    hashtable.put(requestUrl, new JSONObject(response));
                }

            } else {
                requestUrl = "src/main/resources/activities_heart_date_2016-01-07_1d-json.txt";
                if (hashtable.get(requestUrl) == null) {
                    response = getTestBody(requestUrl);
                    hashtable.put(requestUrl, new JSONObject(response));
                }
            }

            JSONObject obj = hashtable.get(requestUrl);
            JSONArray jarr = obj.getJSONArray("activities-heart");
            JSONObject jr = (JSONObject) jarr.get(0);
            JSONObject jr1 = (JSONObject) jr.get("value");
            JSONArray jarr2 = jr1.getJSONArray("heartRateZones");
            JSONObject temp = new JSONObject();

            int[] results = new int[4];
            for (int i = 0; i < 4; i++) {
                temp = jarr2.getJSONObject(i);
                results[i] = temp.getInt("minutes");
            }
            HeartRate result = new HeartRate(results, jr1.getInt("restingHeartRate"));

            return result;
        } catch (JSONException e) {
            HeartRate result = new HeartRate(new int[4], -9);
            return result;
        }


    }

    /**
     * Gets date when the user achieved their best day for that data type
     *
     * @param type ID of the metric for which data should be returned
     * @return BestDay object storing the date and value
     */
    public static BestDay getBestDayData(int type) throws APIException, IOException {
        String requestUrl, response;
        if (!testMode) {
            requestUrl = REQUEST_URL_PREFIX + "activities.json";

            if (hashtable.get(requestUrl) == null) {
                response = sendAPIRequest(requestUrl);
                hashtable.put(requestUrl, new JSONObject(response));
            }
        } else {
            requestUrl = "src/main/resources/activities-json.txt";
            if (hashtable.get(requestUrl) == null) {
                response = getTestBody(requestUrl);
                hashtable.put(requestUrl, new JSONObject(response));
            }

        }

        JSONObject obj = hashtable.get(requestUrl);
        JSONObject jr1 = (JSONObject) obj.get("best");
        JSONObject jr2 = (JSONObject) jr1.get("total");
        JSONObject jr3 = (JSONObject) jr2.get(DATA_TYPE[type]);


        String day = (String) jr3.get("date");
        Double val;
        //Depending on the data type, Fitbit provides an int or a double
        //use try/catch to ensure the proper type is stored
        try {
            val = (Double) jr3.get("value");
        } catch (java.lang.ClassCastException e) {
            val = (Integer) jr3.get("value") * 1.0;
        }

        BestDay result = new BestDay(day, val);
        return result;

    }

    /**
     * Get life time total data
     *
     * @param type ID of the metric for which data should be returned
     * @return Lifetime total for that data type
     */
    public static double getLifeTimeData(int type) throws APIException, IOException {

        String requestUrl, response;
        if (!testMode) {
            requestUrl = REQUEST_URL_PREFIX + "activities.json";
            if (hashtable.get(requestUrl) == null) {
                response = sendAPIRequest(requestUrl);
                hashtable.put(requestUrl, new JSONObject(response));
            }

        } else {
            requestUrl = "src/main/resources/activities-json.txt";
            if (hashtable.get(requestUrl) == null) {
                response = getTestBody(requestUrl);
                hashtable.put(requestUrl, new JSONObject(response));
            }
        }
        JSONObject obj = hashtable.get(requestUrl);
        JSONObject jrr = (JSONObject) obj.get("lifetime");
        JSONObject jr2 = (JSONObject) jrr.get("total");
        double val;
        //Depending on the data type, Fitbit provides an int or a double
        //use try/catch to ensure the proper type is stored
        try {
            val = (Double) jr2.get(DATA_TYPE[type]);
        } catch (java.lang.ClassCastException e) {
            val = (Integer) jr2.get(DATA_TYPE[type]) * 1.0;
        }


        return val;
    }

    /**
     * Sending API request to fitbit
     *
     * @param requestUrl URL to send the API request
     * @return body of the API response
     */
    private static String sendAPIRequest(String requestUrl) throws APIException, IOException {
        //read credentials from a file
        BufferedReader bufferedReader = null;
        // This will reference one line at a time
        String line = null;

        //Need to save service credentials for Fitbit
        String apiKey = null;
        String apiSecret = null;
        String clientID = null;

        //holder for all the elements we will need to make an access token ( information about an authenticated session )
        String accessTokenItself = null;
        String tokenType = null;
        String refreshToken = null;
        Long expiresIn = null;
        String rawResponse = null;

        //This is the only scope you have access to currently
        String scope = "activity%20heartrate";


        FileReader fileReader =
                new FileReader("src/main/resources/Team2Credentials.txt");
        bufferedReader = new BufferedReader(fileReader);
        clientID = bufferedReader.readLine();
        apiKey = bufferedReader.readLine();
        apiSecret = bufferedReader.readLine();
        bufferedReader.close();
        fileReader = new FileReader("src/main/resources/Team2Tokens.txt");
        bufferedReader = new BufferedReader(fileReader);

        accessTokenItself = bufferedReader.readLine();
        tokenType = bufferedReader.readLine();
        refreshToken = bufferedReader.readLine();
        expiresIn = Long.parseLong(bufferedReader.readLine());
        rawResponse = bufferedReader.readLine();

        FitbitOAuth20ServiceImpl service = (FitbitOAuth20ServiceImpl) new ServiceBuilder()
                .apiKey(clientID)       //fitbit uses the clientID here
                .apiSecret(apiSecret)
                .callback("http://localhost:8080")
                .scope(scope)
                .grantType("authorization_code")
                .build(FitbitApi20.instance());


        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                accessTokenItself,
                tokenType,
                refreshToken,
                expiresIn,
                rawResponse);


        OAuthRequest request = new OAuthRequest(Verb.GET, requestUrl, service);
        service.signRequest(accessToken, request);

        Response response = request.send();

        int statusCode = response.getCode();

        switch (statusCode) {
            case 200:
                break;
            case 400:
                throw new APIException(0);
            case 401:
                accessToken = service.refreshOAuth2AccessToken(accessToken);
                request = new OAuthRequest(Verb.GET, requestUrl, service);
                service.signRequest(accessToken, request);
                response = request.send();

                if (response.getCode() != 200) {
                    throw new APIException(0);
                }

                break;
            case 429:
                throw new APIException(1);
            default:
                throw new APIException(0);
        }

        BufferedWriter bufferedWriter = null;


        FileWriter fileWriter;
        fileWriter =
                new FileWriter("src/main/resources/Team2Tokens.txt");
        bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(accessToken.getToken());
        bufferedWriter.newLine();
        bufferedWriter.write(accessToken.getTokenType());
        bufferedWriter.newLine();
        bufferedWriter.write(accessToken.getRefreshToken());
        bufferedWriter.newLine();
        bufferedWriter.write(accessToken.getExpiresIn().toString());
        bufferedWriter.newLine();
        bufferedWriter.write(accessToken.getRawResponse());
        bufferedWriter.newLine();
        bufferedWriter.close();
        return response.getBody();
    }//end main

    /**
     * Retrieve the response string from test text files
     *
     * @param requestUrl The file name of the test data
     * @return Return the response string to be used to parse for data values
     * @throws IOException Throws exception when the txt file is not found
     */
    public static String getTestBody(String requestUrl) throws IOException {
        String response = "";

        FileReader fileReader =
                new FileReader(requestUrl);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        //burn the first three lines, as they are the header
        bufferedReader.readLine();
        bufferedReader.readLine();
        bufferedReader.readLine();
        response = bufferedReader.readLine();

        bufferedReader.close();

        return response;
    }

    /**
     *
     * @return hashtable all URL requests previously sent w/ JSON objects
     */
    public static Hashtable<String, JSONObject> getHashtable() {
        return hashtable;
    }

    /**
     *
     * @param hashtable storing prev. URL requests w/ JSON objects
     */
    public static void setHashtable(Hashtable<String, JSONObject> hashtable) {
        APIHandling.hashtable = hashtable;
    }

    /**
     * Empty the hashtable with URLs and JSONObjects
     */
    public static void reinitializeHashTable(){
        backup = hashtable;
        hashtable = new Hashtable<String, JSONObject>();
    }
}//end class