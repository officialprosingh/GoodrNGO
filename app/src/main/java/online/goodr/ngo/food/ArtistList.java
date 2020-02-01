package online.goodr.ngo.food;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;



public class ArtistList extends ArrayAdapter<Artist> {

    private Activity context;
    List<Artist> artists;

    public ArtistList(Activity context, List<Artist> artists) {
        super(context, R.layout.layout_artist_list, artists);
        this.context = context;
        this.artists = artists;

    }


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_artist_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);
        TextView textViewNumber = (TextView)listViewItem.findViewById(R.id.textViewNumber);
        TextView textViewQauntity = (TextView)listViewItem.findViewById(R.id.textViewQuantity);
        TextView textViewAddress = (TextView)listViewItem.findViewById(R.id.textViewAddress);

        Artist artist = artists.get(position);
        textViewName.setText("Name" + " -  "  + artist.getUserName());
        textViewGenre.setText("Food Type" + " -  "  +artist.getFood());
        textViewNumber.setText("Number" + " -  "  +artist.getUserNumber());
        textViewAddress.setText("Address" + " -  " +artist.getUserAddress());
        textViewQauntity.setText("Weight" + " -  " +artist.getUserQuantity()+ "Kg");

        return listViewItem;
    }
}