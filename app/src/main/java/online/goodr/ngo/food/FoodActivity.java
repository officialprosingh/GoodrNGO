package online.goodr.ngo.food;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    public static final String ARTIST_NAME = "pro.artistname";
    public static final String ARTIST_ID = "pro.artistid";

    public static final String ARTIST_NUMBER = "9284458773";
    public static final String ARTIST_ADDRESS = "pro.artistid";

    public static final String ARTIST_WEIGHT = "pro.artistname";
    public static final String ARTIST_FOOD = "pro.artistname";

    private static final int REQUEST_CALL = 1;





    ListView listViewArtists;

    List<Artist> artists;

    DatabaseReference databaseArtists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);



        listViewArtists = (ListView) findViewById(R.id.listViewArtists);
        databaseArtists = FirebaseDatabase.getInstance().getReference("Donations");


        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Artist artist = artists.get(i);


                Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);


                intent.putExtra(ARTIST_ID, artist.getUserId());
                intent.putExtra(ARTIST_NAME, artist.getUserName());
                intent.putExtra(ARTIST_ADDRESS, artist.getUserAddress());
                intent.putExtra(ARTIST_NUMBER , artist.getUserNumber());



                startActivity(intent);
            }
        });


        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = artists.get(i);
                showUpdateDeleteDialog(artist.getUserId(), artist.getUserName() , artist.getUserAddress());
                return true;
            }
        });


        artists = new ArrayList<>();

    }

    private void showUpdateDeleteDialog(final String userId, String userName , String userAddress) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextView textView = (TextView) dialogView.findViewById(R.id.editTextName);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        textView.setText(userAddress);

        dialogBuilder.setTitle(userName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = FoodActivity.ARTIST_NUMBER;
                if (number.trim().length() > 0) {

                    if (ContextCompat.checkSelfPermission(FoodActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(FoodActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    } else {
                        String dial = "tel:" + number;
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    }

                } else {
                    Toast.makeText(FoodActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteArtist(userId);
                b.dismiss();
            }
        });
    }

    private boolean deleteArtist(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Donations").child(id);


        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Thanks For Using GOODr", Toast.LENGTH_LONG).show();

        return true;
    }






    @Override
    protected void onStart() {
        super.onStart();

        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                artists.clear();


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Artist artist = postSnapshot.getValue(Artist.class);

                    artists.add(artist);
                }


                ArtistList artistAdapter = new ArtistList(FoodActivity.this, artists);

                listViewArtists.setAdapter(artistAdapter);


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
