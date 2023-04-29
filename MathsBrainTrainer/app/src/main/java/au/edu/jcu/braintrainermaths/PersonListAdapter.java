package au.edu.jcu.braintrainermaths;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PersonListAdapter extends ArrayAdapter<Person> {

    private static final String TAG = "PersonListAdapter";

    public PersonListAdapter(Context context, @NonNull List<Person> personList) {
        super(context, 0, personList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Person person = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_view_layout, parent, false);
        }

        TextView name = convertView.findViewById(R.id.nameText);
        TextView score = convertView.findViewById(R.id.scoreText);

        name.setText(person.getName());
        score.setText(person.getScore());

        return convertView;
    }
}
