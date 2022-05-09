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
    private static final String SPN_FROM = "spn_from";
    private static final String SPN_TO = "spn_to";
    private static final String VALUE_FROM = "value_from";
    private static final String VALUE_TO = "value_to";
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "MyDatabaseHelper.onCreate ... ");

        String script = "CREATE TABLE " + TABLE_CURRENCY + "("
                + CURRENCY_ID + " INTEGER PRIMARY KEY, spn_from TEXT, spn_to TEXT, value_from TEXT, value_to TEXT)";
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY);
        onCreate(db);
    }

    public void addCurrency(HistoryCurrency history) {
        Log.e(TAG, "Mydatabase.addCurrentcy" + history.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SPN_FROM, history.getSpn_from());
        contentValues.put(SPN_TO, history.getSpn_to());
        contentValues.put(VALUE_FROM, history.getValue_from());
        contentValues.put(VALUE_TO, history.getValue_to());
        db.insert(TABLE_CURRENCY, null, contentValues);
        db.close();
    }

    public ArrayList<HistoryCurrency> getAllHistory() {
        Log.e(TAG, "MyDatabaseHelper.getAllHistory ... ");
        ArrayList<HistoryCurrency> historyCurrenciesList = new ArrayList<HistoryCurrency>();
        String selectQuery = "SELECT  * FROM " + TABLE_CURRENCY +" ORDER BY " +CURRENCY_ID+ " DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HistoryCurrency his = new HistoryCurrency();
                his.setId(Integer.parseInt(cursor.getString(0)));
                his.setSpn_to(cursor.getString(1));
                his.setSpn_from(cursor.getString(2));
                his.setValue_to(cursor.getString(3));
                his.setValue_from(cursor.getString(4));
                // Adding note to list
                historyCurrenciesList.add(his);
            } while (cursor.moveToNext());
        }
        return historyCurrenciesList;
    }

    public void deleteHistory(HistoryCurrency his) {
        Log.i(TAG, "MyDatabaseHelper.deleteHistory ... " + his.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CURRENCY, CURRENCY_ID + " = ?",
                new String[]{String.valueOf(his.getId())});
        db.close();
    }

}
