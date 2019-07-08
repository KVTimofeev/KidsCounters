package kuzia_timofeev.kidscounters2;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.AnnotatedElement;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    final String TAG="LoggerMain";
    int show_numb;
    int answer;
    int count;
    int AllCounters;
    TextView txt;
    char sign;
    Button btn;
    ImageButton options;
    long time_beetween;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt=(TextView)findViewById(R.id.text);
        btn =(Button)findViewById(R.id.button);
        options = (ImageButton)findViewById(R.id.btnOptions);
        options.setOnClickListener(OptionsClick);
        AllCounters=7;
        time_beetween=1000;
        btn.setOnClickListener(this);
        txt.setText("<^__^>");
        btn.setText("НАЧАТЬ");

    }



    @Override
    public void onClick(View v) {
        Go g=new Go();
        btn.setVisibility(View.INVISIBLE);
        options.setVisibility(View.INVISIBLE);
        g.start();
    }

    class Go extends Thread {
        final String TAGThread="LOGGER_GO";

        @Override
        public void run() {
            CounterBack();
            goes();
        }

        void goes() {
            answer = 0;
            int lastNumb=new Random().nextInt(7) + 1;
            int numb;

            for (count = 0; count < AllCounters; count++) {
                Random r = new Random();
                numb = r.nextInt(7) + 1;
                while(numb==lastNumb){
                    numb=r.nextInt(7) + 1;
                    Log.d(TAGThread,"пересчитываю из потока");
                }

                char a;
                if ((answer - numb) < 0) {
                    a = '+';
                    answer += numb;
                } else if ((answer + numb) > 18) {
                    a = '-';
                    answer -= numb;
                } else {
                    a = ChooseSign(r.nextInt(2));
                    if (a == '-') {
                        answer -= numb;
                    }
                    if (a == '+') {
                        answer += numb;
                    }
                }
                sign = a;
                show_numb = numb;
                lastNumb=numb;
                txt.post(new Runnable() {
                    @Override
                    public void run() {
                        if (sign == '+') {
                            txt.setText("+" + show_numb + "");
                        }
                        if (sign == '-') {

                            txt.setText("-" + show_numb + "");
                        }

                    }
                });
                sleeping(time_beetween);
            }

            txt.post(new Runnable() {
                @Override
                public void run() {
                    txt.setText("Какой ответ?");
                }
            });
            sleeping(3200);
            txt.post(new Runnable() {
                @Override
                public void run() {
                    txt.setText("Ответ " + answer);
                }
            });
            sleeping(3500);

            txt.post(new Runnable() {
                @Override
                public void run() {
                    txt.setText("<^__^>");

                }
            });
            btn.post(new Runnable() {
                @Override
                public void run() {
                    btn.setVisibility(View.VISIBLE);
                    btn.setText("Хочу еще!");
                }
            });
            options.post(new Runnable() {
                @Override
                public void run() {
                    options.setVisibility(View.VISIBLE);
                }
            });

        }

        char ChooseSign(int choosen) {
            char a = '+';
            switch (choosen) {
                case 0:
                    a = '+';
                    break;
                case 1:
                    a = '-';
                    break;
            }
            return a;
        }




        void CounterBack() {

            txt.post(new Runnable() {
                @Override
                public void run() {
                    txt.setText("\t\tНасчет три!");
                }
            });
            sleeping(2000);

            for (count = 1; count < 4; count++) {
                txt.post(new Runnable() {
                    @Override
                    public void run() {
                        txt.setText("\t\t" + count);
                    }
                });
                sleeping(1000);
            }
            txt.post(new Runnable() {
                @Override
                public void run() {
                    txt.setText("Гоу!");
                }
            });
            sleeping(1000);
        }

        void sleeping(long time){
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AllCounters=0;
        time_beetween=0;
    }

    View.OnClickListener OptionsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sendOptions = new Intent(getApplicationContext(),ActivityOptions.class);
            sendOptions.putExtra("time",time_beetween);
            sendOptions.putExtra("count",AllCounters);
            startActivityForResult(sendOptions,1);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            try {
                AllCounters=(int)data.getExtras().getSerializable("counts");
            }catch (NullPointerException nullPointer){
                Log.d(TAG,"Null pointer AllConters");
            }
            try{
                time_beetween=(long)data.getExtras().getSerializable("time");


            }catch (NullPointerException nullPointer){
                Log.d(TAG,"Null pointer AllConters");
            }

            //Log.d(TAG," AllCounters= "+AllCounters+" time= "+time_beetween);

            new AlertDialog.Builder(this).setTitle("Изменения").setMessage("Количество было изменено на "+AllCounters+"\n время смены числа было изменено на "+(time_beetween>1000?time_beetween/1000+"секунд и "+time_beetween%1000+" микросекунд":time_beetween+" микросекунд")+"").create().show();

        }else if(resultCode==RESULT_CANCELED){
            new AlertDialog.Builder(this).setTitle("Ничего не изменилось").create().show();

        }

    }
}
