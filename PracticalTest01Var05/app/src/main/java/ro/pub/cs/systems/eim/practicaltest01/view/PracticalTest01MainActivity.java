package ro.pub.cs.systems.eim.practicaltest01.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest01.R;
import ro.pub.cs.systems.eim.practicaltest01.general.Constants;
import ro.pub.cs.systems.eim.practicaltest01.service.PracticalTest01Service;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private EditText addText;
    private EditText resultEdit;
    private Button add_button, compute_button;
    private Button navigateToSecondaryActivityButton;

    private int serviceStatus = Constants.SERVICE_STOPPED;

    private IntentFilter intentFilter = new IntentFilter();

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int add_num = Integer.valueOf(addText.getText().toString());
            String result_num = resultEdit.getText().toString();

            switch(view.getId()) {
                case R.id.add:
                    result_num = result_num.concat("+");
                    result_num = result_num.concat(addText.getText().toString());
                    resultEdit.setText(String.valueOf(result_num));
                    break;
                case R.id.compute:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    String result_final = resultEdit.getText().toString();
                    intent.putExtra(Constants.RESULT_FINAL, result_final);
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }
//            if (leftNumberOfClicks + rightNumberOfClicks > Constants.NUMBER_OF_CLICKS_THRESHOLD
//                    && serviceStatus == Constants.SERVICE_STOPPED) {
//                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
//                intent.putExtra(Constants.FIRST_NUMBER, leftNumberOfClicks);
//                intent.putExtra(Constants.SECOND_NUMBER, rightNumberOfClicks);
//                getApplicationContext().startService(intent);
//                serviceStatus = Constants.SERVICE_STARTED;
//            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);

        addText = (EditText)findViewById(R.id.add_text);
        resultEdit = (EditText)findViewById(R.id.result);
        add_button = (Button)findViewById(R.id.add);
        add_button.setOnClickListener(buttonClickListener);
        compute_button = (Button)findViewById(R.id.compute);
        compute_button.setOnClickListener(buttonClickListener);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.ADD_TEXT)) {
                addText.setText(savedInstanceState.getString(Constants.ADD_TEXT));
            } else {
                addText.setText(String.valueOf(0));
            }
            if (savedInstanceState.containsKey(Constants.RESULT_TEXT)) {
                resultEdit.setText(savedInstanceState.getString(Constants.RESULT_TEXT));
            } else {
                resultEdit.setText(String.valueOf(0));
            }
        } else {
            addText.setText(String.valueOf(0));
            resultEdit.setText(String.valueOf(0));
        }

        navigateToSecondaryActivityButton = (Button)findViewById(R.id.compute);
        navigateToSecondaryActivityButton.setOnClickListener(buttonClickListener);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(Constants.ADD_TEXT, addText.getText().toString());
        savedInstanceState.putString(Constants.RESULT_TEXT, resultEdit.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.ADD_TEXT)) {
            addText.setText(savedInstanceState.getString(Constants.ADD_TEXT));
        } else {
            addText.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey(Constants.RESULT_TEXT)) {
            resultEdit.setText(savedInstanceState.getString(Constants.RESULT_TEXT));
        } else {
            resultEdit.setText(String.valueOf(0));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_CODE) {
            Toast.makeText(this, "The activity returned with result " + intent.getStringExtra(Constants.REZ_F), Toast.LENGTH_LONG).show();
        }
    }
}
