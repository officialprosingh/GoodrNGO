package online.goodr.ngo.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ConfirmActivity extends AppCompatActivity {

    private TextView maddress , mname , mnumber ;
    private Button call_user ;
    private static final int REQUEST_CALL = 1;
    DatabaseReference databaseRemove;
    List<Artist> artists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Intent intent = getIntent();


        databaseRemove = FirebaseDatabase.getInstance().getReference("Donations");


        maddress = (TextView) findViewById(R.id.address_view);
        mnumber = (TextView) findViewById(R.id.number_view);




        call_user = (Button) findViewById(R.id.call_user);
        call_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        mnumber.setText(intent.getStringExtra(FoodActivity.ARTIST_NUMBER));



        maddress.setText(intent.getStringExtra(FoodActivity.ARTIST_ADDRESS));



    }





    private void makePhoneCall() {
        String number = FoodActivity.ARTIST_NUMBER;
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(ConfirmActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ConfirmActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(ConfirmActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
