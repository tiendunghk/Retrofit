package com.example.notificationfcm;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText edtText;
    TextView txtView;
    static  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.137.1:1323")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    static MyService service=retrofit.create(MyService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtText=findViewById(R.id.edtEmail);
        txtView=findViewById(R.id.txtStatus);
    }

    public void btnOnclick(View view) {
        String email=edtText.getText().toString();
        JSONObject reqObj=new JSONObject();
        try{
            reqObj.put("email",email);
        }
        catch (Exception e){

        }
        RequestBody body=RequestBody.create(MediaType.parse("application/json"),reqObj.toString());
        service.registerUser(body).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                txtView.setText(response.body().status);
                registerDevice();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
    void registerDevice(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        String email=edtText.getText().toString();

                        JSONObject reqObj=new JSONObject();
                        try{
                            reqObj.put("email",email);
                            reqObj.put("token",token);
                        }
                        catch (Exception e){

                        }
                        RequestBody body=RequestBody.create(MediaType.parse("application/json"),reqObj.toString());
                        service.registerDevice(body).enqueue(new Callback<Response>() {
                            @Override
                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                Toast.makeText(MainActivity.this,token,Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Response> call, Throwable t) {

                            }
                        });
                    }
                });
    }
}
