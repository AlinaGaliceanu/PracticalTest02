package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import ro.pub.cs.systems.eim.practicaltest02.model.AlarmDetails;
import ro.pub.cs.systems.eim.practicaltest02.utils.Constants;
import ro.pub.cs.systems.eim.practicaltest02.utils.Utilities;

public class CommunicationThread extends Thread {
    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket == null) {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
            return;
        }
        try {
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Buffered Reader / Print Writer are null!");
                return;
            }

            Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (city / information type!");
            String city = bufferedReader.readLine();
            String informationType = bufferedReader.readLine();
            if (city == null || city.isEmpty() || informationType == null || informationType.isEmpty()) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Error receiving parameters from client (city / information type!");
                return;
            }

            HashMap<String, AlarmDetails> data = serverThread.getData();
            AlarmDetails alarmDetails = null;
//            if (data.containsKey("abc")) {
//                Log.i(Constants.TAG,
//                        "[COMMUNICATION THREAD] Getting the information from the cache...");
//                weatherForecastInformation = data.get(city);
//            } else {
//                Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the webservice...");
//                HttpClient httpClient = new DefaultHttpClient();
//                String url = Constants.BASE_URL + ":" + Constants.UTC_PORT;
//
//                HttpGet httpGet = new HttpGet(url);
//                ResponseHandler<String> responseHandler = new BasicResponseHandler();
//                String content = httpClient.execute(httpGet, responseHandler);
//                JSONObject result = new JSONObject(content);
//
//                JSONObject mainObservation = result.getJSONObject(Constants.MAIN);
//                String temperature = mainObservation.getString(Constants.FIELD_TEMPERATURE);
//                String pressure = mainObservation.getString(Constants.FIELD_PRESSURE);
//                String humidity = mainObservation.getString(Constants.FIELD_HUMIDITY);
//                String feelsLike = mainObservation.getString(Constants.FIELD_FEELS_LIKE);
//
//                JSONObject windObservation = result.getJSONObject(Constants.WIND);
//                String windSpeed = windObservation.getString(Constants.FIELD_WIND_SPEED);
//
//                weatherForecastInformation = new WeatherInfo(
//                        temperature, pressure, humidity, feelsLike, windSpeed
//                );
//
//                serverThread.addData(city, weatherForecastInformation);
//            }
//            if (weatherForecastInformation == null) {
//                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Weather Forecast Information is null!");
//                return;
//            }
//            String res = null;
//            switch (informationType) {
//                case Constants.ALL:
//                    res = weatherForecastInformation.toString();
//                    break;
//                case Constants.TEMPERATURE:
//                    res = weatherForecastInformation.getMainTemp();
//                    break;
//                case Constants.WIND_SPEED:
//                    res = weatherForecastInformation.getWindSpeed();
//                    break;
//                case Constants.CONDITION:
//                    res = weatherForecastInformation.getMainFeelsLike();
//                    break;
//                case Constants.HUMIDITY:
//                    res = weatherForecastInformation.getMainHumidity();
//                    break;
//                case Constants.PRESSURE:
//                    res = weatherForecastInformation.getMainPressure();
//                    break;
//                default:
//                    res = "[COMMUNICATION THREAD] Wrong information type (all / temperature / wind speed / feels like / humidity / pressure)!";
//            }
//            printWriter.println(res);
////            printWriter.flush();
        } catch (IOException exception) {
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }
  }
}
