package com.project.MoodMApp.assets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by PsichO on 03.04.14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 18;
    private static final String DATABASE_NAME = "moodsHolder.db";

    private static final String TABLE_MOODS = "moods";

    private static final String KEY_ID = "id";
    private static final String KEY_MOOD = "mood";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_PICTURE = "picture";
    private static final String KEY_DATE = "date";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_COORD = "coord";

    Context context;

    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_MOODS + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_MOOD + " STRING,"
            + KEY_COMMENT + " STRING,"
            + KEY_PICTURE + " STRING,"
            + KEY_LAT + " DOUBLE,"
            + KEY_LNG + " DOUBLE,"
            + KEY_COORD + " STRING,"
            + KEY_DATE + " STRING)";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOODS);
        onCreate(db);
    }

    public void addNote (Note note){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MOOD, note.getMood());
        values.put(KEY_COMMENT, note.getComment());
        values.put(KEY_PICTURE, note.getImage());
        values.put(KEY_LAT, note.getLat());
        values.put(KEY_LNG, note.getLng());
        values.put(KEY_COORD, note.getCoords());
        values.put(KEY_DATE, note.getDate());

        sqLiteDatabase.insert(TABLE_MOODS, null, values);
        sqLiteDatabase.close();
    }

    public Note getNote(String title) throws Exception {
        ArrayList<Note> notes = getAllNotes();
        for (Note note : notes)
            if (note.getMood().equals(title)) {
                return note;
            }
        throw new Exception("Event  with title=" + title + " not found");
    }

    /*public Note getNoteByID(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_MOODS, new String[] { KEY_ID,
                        KEY_MOOD, KEY_COMMENT, KEY_PICTURE, KEY_LAT, KEY_LNG, KEY_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return contact
        return note;
    }*/

    public ArrayList<Note> getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Note> notes = new ArrayList<Note>();
        Cursor notesCursor = db.rawQuery("SELECT * FROM " + TABLE_MOODS, null);
        if (notesCursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(Integer.parseInt(notesCursor.getString(0)));
                note.setMood(notesCursor.getString(1));
                note.setComment(notesCursor.getString(2));
                note.setImage(notesCursor.getString(3));
                note.setLat(notesCursor.getDouble(4));
                note.setLng(notesCursor.getDouble(5));
                note.setCoords(notesCursor.getString(6));
                note.setDate(notesCursor.getString(7));
                notes.add(note);
            } while (notesCursor.moveToNext());
        }
        notesCursor.close();
        db.close();
        return notes;
    }

    public int getNotesCount(){
        String countQuery = "SELECT * FROM " + TABLE_MOODS;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery,null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateNote(Note note){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MOOD, note.getMood());
        values.put(KEY_COMMENT, note.getComment());
        values.put(KEY_PICTURE, note.getImage());
        values.put(KEY_LAT, note.getLat());
        values.put(KEY_LNG, note.getLng());
        values.put(KEY_COORD, note.getCoords());
        values.put(KEY_DATE, note.getDate());

        return sqLiteDatabase.update(TABLE_MOODS,values,KEY_ID + " =?", new String[]{String.valueOf(note.getID())});
    }

    public void deleteNote(Note note){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_MOODS,KEY_ID + " =?", new String[] {String.valueOf(note.getID())});
        sqLiteDatabase.close();
    }

    public void formatDB(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_MOODS, null, null);
        sqLiteDatabase.close();
    }
}