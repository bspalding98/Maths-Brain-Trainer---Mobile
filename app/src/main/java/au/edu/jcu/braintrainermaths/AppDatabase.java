package au.edu.jcu.braintrainermaths;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class AppDatabase extends SQLiteOpenHelper {

    private static AppDatabase appDatabase;
    private static final String DATABASE_NAME = "gameDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "highscore";
    private static final String COUNTER = "counter";

    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "Name";
    private static final String SCORE_FIELD = "Score";


    public AppDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static AppDatabase instanceOfDatabase(Context context) {
        if (appDatabase == null)
            appDatabase = new AppDatabase(context);

        return appDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(SCORE_FIELD)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void addScoreToDatabase(Person person) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, person.getId());
        contentValues.put(NAME_FIELD, person.getName());
        contentValues.put(SCORE_FIELD, person.getScore());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        populateNoteListArray();
    }

    public void populateNoteListArray() {
        ArrayList<String> scores = new ArrayList<>();
        ArrayList<String> namesArr = new ArrayList<>();

        String[] arr;
        String[] names;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    scores.add(result.getString(3));
                    namesArr.add(result.getString(2));
                }
                arr = new String[scores.size()];
                names = new String[scores.size()];

                for (int i = 0; i < scores.size(); i++) {
                    arr[i] = scores.get(i);
                    names[i] = namesArr.get(i);
                }

                for (int i = 0; i < arr.length; i++) {      //Loop over java Array  outer Loop use
                    for (int j = i + 1; j < arr.length; j++) {  // Loop over java array
                        int tmp = 0;                            //tempraory variable in order to compare.
                        if (Integer.parseInt(arr[i]) < Integer.parseInt(arr[j])) {          //compare outer loop object with inner loop
                            tmp = Integer.parseInt(arr[i]);               // if greater than swapping.
                            arr[i] = arr[j];            // Swaping code here.
                            arr[j] = Integer.toString(tmp);

                            String temp = "";
                            temp = names[i];
                            names[i] = names[j];
                            names[j] = temp;
                        }
                    }
                }

                Person.personArrayList.add(new Person("Name", "Score"));
                for (int i = 0; i < 10; i++) {
                    Person person = new Person(names[i], arr[i]);
                    Person.personArrayList.add(person);
                }
            }
        }
    }
}
