package com.example.mysql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import com.example.mysql.models.Person;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private final String TAG = "MySQLiteHelper class";

    public static final String TABLE_NAME = "Users";
    public static final String COL_NAME = "name";
    public static final String COL_GENDER = "gender";
    public static final String COL_STREET = "street";
    public static final String COL_STATE = "state";
    public static final String COL_POSTCODE = "postcode";


    public MySQLiteHelper(Context context) {
        super(context, "test.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" + " " + COL_NAME + " TEXT, "
                + COL_GENDER + " TEXT, " + COL_STREET + " TEXT, " + COL_STATE + " TEXT, " +  COL_POSTCODE + " TEXT" + ")" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    boolean insertData(Person p) {
        SQLiteDatabase db = this.getWritableDatabase();
//        int user_postcode = 1;
//        String user_name = "Menahem";
        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        */
        ContentValues contentValues = new ContentValues();
//
        contentValues.put(COL_NAME, p.getUser_name());
        contentValues.put(COL_GENDER, p.getUser_gender());
        contentValues.put(COL_STREET, p.getUser_street());
        contentValues.put(COL_STATE, p.getUser_state());
        contentValues.put(COL_POSTCODE, p.getUser_postcode());

        if (db.insert(TABLE_NAME, null, contentValues) != -1) {
            return true;
        }
        return false;
    }

    public void DeleteUser(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "NAME = ?", new String[] {name});
        /*db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE "+COL_GENDER+"='"+value+"'");
        db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE "+COL_STREET+"='"+value+"'");
        db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE "+COL_STATE+"='"+value+"'");
        db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE "+COL_POSTCODE+"='"+value+"'");*/

        // איך מוחקים את כל הפרטים של user מבלי להוריד את ה ? TABLE

        db.close();
        Log.i(TAG, "DeleteUser: wwwwwwwwwwwwwwooooooooooooooorrrrrrrrrrrrrrkkkkkkkkkkkkkkkkkeeeeeeeeeeedddddddddd");

    }
    public void clearAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
        Log.d(TAG,"all users has been deleted from you SQL!!! (I think....) ============================");
        //Toast.makeText(context, "all users has been deleted from you SQL!!! (I think....)", Toast.LENGTH_SHORT).show();
    }


    public void updateUser(Person p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        /*
        *
        *
        *                עוד לא מצאתי דרך ליצור עידכון בצורה נכונה, אשמח לאיזשהו כיוון ליפתרון :)
        *
        * */
        contentValues.put(COL_NAME,p.getUser_name());
        contentValues.put(COL_GENDER,p.getUser_gender());
        contentValues.put(COL_STREET,p.getUser_street());
        contentValues.put(COL_STATE,p.getUser_state());
        contentValues.put(COL_POSTCODE,p.getUser_postcode());

        db.update(TABLE_NAME,contentValues,"NAME = ?",null);
        Log.d(TAG,"user has been UPDATED !!! (I think....) ============================");
        db.close();
    }
}