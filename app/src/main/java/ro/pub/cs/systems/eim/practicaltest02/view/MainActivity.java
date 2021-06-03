package ro.pub.cs.systems.eim.practicaltest02.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import ro.pub.cs.systems.eim.practicaltest02.R;
import ro.pub.cs.systems.eim.practicaltest02.model.AlarmDetails;
import ro.pub.cs.systems.eim.practicaltest02.network.ServerThread;
import ro.pub.cs.systems.eim.practicaltest02.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private TextView serverPortEditText;
    private TextView clientPortEditText;
    private TextView clientAddressEditText;
    private TextView alarmDetailsTextView;

    private Button serverConnectButton;
    private Button setButton;
    private Button resetButton;
    private Button pollButton;

    private TextView hourEditText;
    private TextView minutesEditText;
    private ServerThread serverThread = null;

    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();
        }

    }

    private SetButtonClickListener setButtonListener = new SetButtonClickListener();
    private class SetButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddress = clientAddressEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();
            if (clientAddress == null || clientAddress.isEmpty()
                    || clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }
            String hour = hourEditText.getText().toString();
            String minutes = minutesEditText.getText().toString();
            if (hour == null || hour.isEmpty()
                    || minutes == null || minutes.isEmpty()) {
                alarmDetailsTextView.setText(Constants.EMPTY_STRING);
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (city / information type) should be filled", Toast.LENGTH_SHORT).show();
                return;
            }
            alarmDetailsTextView.setText("S-a setat o alarma la ora " + hour + " : "+ minutes);
            serverThread.addData(clientAddress, new AlarmDetails(hour, minutes));
        }
    }
        private ResetButtonClickListener resetButtonListener = new ResetButtonClickListener();
        private class ResetButtonClickListener implements Button.OnClickListener {

            @Override
            public void onClick(View view) {
                String clientAddress = clientAddressEditText.getText().toString();
                String clientPort = clientPortEditText.getText().toString();
                if (clientAddress == null || clientAddress.isEmpty()
                        || clientPort == null || clientPort.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (serverThread == null || !serverThread.isAlive()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (serverThread.getData().containsKey(clientAddress)) {

                    HashMap<String, AlarmDetails> data = serverThread.getData();
                    data.remove(clientAddress);
                    serverThread.updateData(data);
                }
            }

    }
    private PollButtonClickListener pollButtonListener = new PollButtonClickListener();
    private class PollButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddress = clientAddressEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();
            if (clientAddress == null || clientAddress.isEmpty()
                    || clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (serverThread.getData().containsKey(clientAddress)) {
                Integer hourToCompare = Integer.parseInt(serverThread.getData().get(clientAddress).getHours());
                Integer minutesToCompare = Integer.parseInt(serverThread.getData().get(clientAddress).getMinutes());
                int res = 0;
                // aici conexiunea TCP
//                int res = -1(inactiv), 0 (none), 1 (activ))
                if (res < 0) {
                    Toast.makeText(getApplicationContext(), "Inactiv", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (res == 0) {
                    Toast.makeText(getApplicationContext(), "None", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (res > 0) {
                    Toast.makeText(getApplicationContext(), "Activ", Toast.LENGTH_SHORT).show();
                    return;
                }

                return;
            }
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);;
        clientPortEditText= (EditText)findViewById(R.id.client_port_edit_text);;
        clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);;

        serverConnectButton = (Button)findViewById(R.id.connect_button);
        serverConnectButton.setOnClickListener(connectButtonClickListener);

        hourEditText = (EditText)findViewById(R.id.hour_edit_text);
        minutesEditText = (EditText)findViewById(R.id.minutes_edit_text);
        alarmDetailsTextView = (TextView) findViewById(R.id.weather_forecast_text_view);

        setButton = (Button)findViewById((R.id.set_button));
        setButton.setOnClickListener(setButtonListener);
        resetButton = (Button)findViewById(R.id.reset_button);
        resetButton.setOnClickListener(resetButtonListener);


        pollButton =  (Button)findViewById(R.id.poll_button);
        pollButton.setOnClickListener(pollButtonListener);


    }
}