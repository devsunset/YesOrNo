package devsunset.app.yesorno;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Launcher extends Activity {

    @BindView(R.id.progress)
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        load();
    }

    private void load() {
        class LoadingTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    for(int i = 0; i  < 5; i++){
                        Thread.sleep(200);
                        progress.setProgress(i);
                    }
                }catch (Exception e){
                    //skip
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                initActivity();
            }
        }
        LoadingTask st = new LoadingTask();
        st.execute();
    }

    private void initActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
