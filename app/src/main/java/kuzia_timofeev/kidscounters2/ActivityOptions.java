package kuzia_timofeev.kidscounters2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityOptions extends AppCompatActivity implements View.OnClickListener{
    final String TAG="LOGGER";

    @Override
    public void onClick(View v) {
        int counts=5;
        long timer=1000;
        if(count.getText().length()>3){
            count.setText("");

            Toast.makeText(getApplicationContext(),"Слишком большое количество",Toast.LENGTH_LONG).show();
        }else if(time.getText().length()>6){
            time.setText("");
            Toast.makeText(getApplicationContext(),"Не стоит ставить времени больше минуты",Toast.LENGTH_LONG).show();
        }
        else {
            //переделаем if

            /*if(count.getText().toString()=="" && time.getText().toString()==""){
                counts=Integer.parseInt((String)count.getHint());
                timer=Integer.parseInt((String)time.getHint());
                setResult(RESULT_CANCELED);
                finish();

            }else {
                Intent sending = new Intent();
                if(count.getText().toString().length()>0){
                    counts=Integer.parseInt(count.getText().toString());
                    Log.d(TAG," counts= "+counts);
                    sending.putExtra("counts",counts);

                }
                if(time.getText().toString().length()>0){
                    timer=Integer.parseInt(time.getText().toString());

                    Log.d(TAG," timer= "+timer);
                    sending.putExtra("time",timer);
                }
                setResult(RESULT_OK,sending);
                finish();

            }*/

            //начало переделки
            if(count.getText().toString()=="" && time.getText().toString()==""){
                counts=Integer.parseInt((String)count.getHint());
                timer=Integer.parseInt((String)time.getHint());
                setResult(RESULT_CANCELED);
                finish();

            }else {
                Intent sending = new Intent();
                if(count.getText().toString().length()>0){
                    counts=Integer.parseInt(count.getText().toString());
                    Log.d(TAG," counts= "+counts);
                    sending.putExtra("counts",counts);

                }
                if(time.getText().toString().length()>0){
                    timer=Integer.parseInt(time.getText().toString());

                    Log.d(TAG," timer= "+timer);
                    sending.putExtra("time",timer);
                }
                if(time.getText().toString().length()==0){
                    timer=Integer.parseInt(time.getHint().toString());

                    Log.d(TAG, " timer= " + timer);
                    sending.putExtra("time",timer);
                }
                if(count.getText().toString().length()==0){
                    counts=Integer.parseInt(count.getHint().toString());
                    Log.d(TAG," counts= "+counts);
                    sending.putExtra("counts",counts);

                }
                setResult(RESULT_OK,sending);
                finish();

            }


            //конец

            //NumberFormatException
            }
    }

    Button send;
    EditText count;
    EditText time;
    private long betwtimes;
    private int counts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_options);
        send = (Button)findViewById(R.id.sendSave);
        time=(EditText)findViewById(R.id.time_beetween);
        count=(EditText)findViewById(R.id.countNumbers);
        Intent takeIntent=getIntent();
        betwtimes=takeIntent.getExtras().getLong("time");
        counts=takeIntent.getExtras().getInt("count");
        time.setHint(""+betwtimes);
        count.setHint(""+counts);

        send.setOnClickListener(this);
    }

}
