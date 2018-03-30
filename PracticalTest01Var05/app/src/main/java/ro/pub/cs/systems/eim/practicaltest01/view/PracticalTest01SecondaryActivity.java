package ro.pub.cs.systems.eim.practicaltest01.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ro.pub.cs.systems.eim.practicaltest01.R;
import ro.pub.cs.systems.eim.practicaltest01.general.Constants;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    private TextView rezFinal;
    private Button returnButton;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.return_but:
                    setResult(RESULT_OK, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        int rez_afisare = 0;

        rezFinal = (TextView)findViewById(R.id.rez_final); //if (requestCode == Constants.SECONDARY_CODE) {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.RESULT_FINAL)) {
            String rez = intent.getStringExtra(Constants.RESULT_FINAL);
            String[] separated = rez.split("\\+");
            for(int i = 0; i < separated.length; i++) {
                rez_afisare += Integer.parseInt(separated[i]);
            }


            rezFinal.setText(String.valueOf(rez_afisare));

            Intent intentS = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
            intentS.putExtra(Constants.REZ_F, rez_afisare);
            setResult(Constants.SECONDARY_CODE, intentS);
            //finish();

        }

        returnButton = (Button)findViewById(R.id.return_but);
        returnButton.setOnClickListener(buttonClickListener);


    }
}
