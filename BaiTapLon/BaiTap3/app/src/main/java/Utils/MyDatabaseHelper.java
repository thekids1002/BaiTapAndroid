package Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import Model.HistoryCurrency;

public class MyDatabaseHelper extends SQLiteOpenHelper {


    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Currency_Manager";
    private static final String TABLE_CURRENCY = "Currency";
    private static final String CURRENCY_ID = "Currency_Id";
    private static final String COLUMN_CURRENCY_TITLE = "Currency_Title";
    private static final String COLUMN_CURRENCY_CONTENT = "Currency_Content";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "MyDatabaseHelper.onCreate ... ");

        String script = "CREATE TABLE " + TABLE_CURRENCY + "("
                + CURRENCY_ID + " INTEGER PRIMARY KEY," + COLUMN_CURRENCY_TITLE + " TEXT,"
                + COLUMN_CURRENCY_CONTENT + " TEXT" + ")";
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY);
        onCreate(db);
    }

    public void addCurrency(HistoryCurrency history) {
        Log.e(TAG, "Mydatabase.addCurrentcy" + history.getValues());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CURRENCY_TITLE, history.getTitle());
        contentValues.put(COLUMN_CURRENCY_CONTENT, history.getValues());
        db.insert(TABLE_CURRENCY, null, contentValues);
        db.close();
    }

    public ArrayList<HistoryCurrency> getAllHistory() {
        Log.e(TAG, "MyDatabaseHelper.getAllHistory ... ");
        ArrayList<HistoryCurrency> historyCurrenciesList = new ArrayList<HistoryCurrency>();
        String selectQuery = "SELECT  * FROM " + TABLE_CURRENCY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HistoryCurrency his = new HistoryCurrency();
                his.setId(Integer.parseInt(cursor.getString(0)));
                his.setTitle(cursor.getString(1));
                his.setValues(cursor.getString(2));
                // Adding note to list
                historyCurrenciesList.add(his);
            } while (cursor.moveToNext());
        }
        return historyCurrenciesList;
    }

    public void deleteHistory(HistoryCurrency his) {
        Log.i(TAG, "MyDatabaseHelper.deleteHistory ... " + his.getTitle());
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CURRENCY, CURRENCY_ID + " = ?",
                new String[]{String.valueOf(his.getId())});
        db.close();
    }

}
