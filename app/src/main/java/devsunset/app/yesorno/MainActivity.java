package devsunset.app.yesorno;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import devsunset.app.yesorno.modules.httpservice.DataVo;
import devsunset.app.yesorno.modules.httpservice.HttpConnectClient;
import devsunset.app.yesorno.modules.httpservice.HttpConnectService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private HttpConnectService httpConnectService = null;

    @BindView(R.id.question_text)
    EditText question_text;

    @BindView(R.id.tvQuestion)
    TextView tvQuestion;

    @BindView(R.id.tvAnswer)
    TextView tvAnswer;

    private static boolean EXECUTE_ACTION = false;

    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        httpConnectService = HttpConnectClient.getClient().create(HttpConnectService.class);
    }

    @OnClick(R.id.btnSend)
    void onBtnSendClicked() {

        if (EXECUTE_ACTION) {
            return;
        }

        String message = question_text.getText().toString();

        if (question_text.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Please input question ?", Toast.LENGTH_SHORT).show();
            return;
        }

        tvQuestion.setText("Q : " + question_text.getText());
        tvAnswer.setText("A : ");
        question_text.setText("");

        EXECUTE_ACTION = true;

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setBackgroundColor(getResources().getColor(R.color.progress))
                .setAnimationSpeed(2)
                .show();

        httpConnectService.api().enqueue(new Callback<DataVo>() {
            @Override
            public void onResponse(@NonNull Call<DataVo> call, @NonNull Response<DataVo> response) {
                if (response.isSuccessful()) {
                    DataVo data = response.body();
                    if (data != null) {
                        ImageView view_image = (ImageView) findViewById(R.id.view_image);
                        Glide.with(getApplicationContext())
                                .load(data.getImage())
                                //.placeholder(R.drawable.place_logo)
                                .error(R.drawable.app_logo)
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        hud.dismiss();
                                        tvAnswer.setText("A : " + data.getAnswer().toUpperCase());
                                        EXECUTE_ACTION = false;
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        hud.dismiss();
                                        tvAnswer.setText("A : " + data.getAnswer().toUpperCase());
                                        EXECUTE_ACTION = false;
                                        return false;
                                    }
                                })
                                .into(view_image);
                    }
                } else {
                    hud.dismiss();
                    EXECUTE_ACTION = false;
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataVo> call, @NonNull Throwable t) {
                hud.dismiss();
                EXECUTE_ACTION = false;
                question_text.setText("");
                Log.e("MainActivity Error : ", t.getMessage(), t);
            }
        });
    }
}
