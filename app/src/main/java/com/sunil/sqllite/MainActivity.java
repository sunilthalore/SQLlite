package com.sunil.sqllite;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    TextView tv;
    EditText et1,et2;
    ShareActionProvider mShareActionProvider;

    private RadioGroup rg1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView1);
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        db = openOrCreateDatabase("Mydb", MODE_PRIVATE, null);
        db.execSQL("create table if not exists mytable(name varchar, sur_name varchar)");
        rg1 = (RadioGroup) findViewById(R.id.radioGroup);
    }
    public void insert(View v){
        String name=et1.getText().toString();
        String sur_name = et2.getText().toString();
        et1.setText("");
        et2.setText("");

        db.execSQL("insert into mytable values('"+name+"','"+sur_name+"')");
        Toast.makeText(this,"values inserted successfully.", Toast.LENGTH_LONG).show();

    }

    public void display(View v){
        Cursor c = db.rawQuery("select *from mytable",null);
        tv.setText("");
        c.moveToFirst();
        do {
            String name = c.getString(c.getColumnIndex("name"));
            String surname = c.getString(1);
            tv.append("Name: "+ name+" nad SurName: "+surname+"\n");
        }while (c.moveToNext());
    }

    public void imageClick(View v){
        Toast.makeText(this,"Enter Values in the box", Toast.LENGTH_LONG).show();
    }

    public void delete(View v){
        tv.setText("");
        Toast.makeText(this, " Table Cleared from screen!", Toast.LENGTH_LONG).show();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.Rbutton1:
                if (checked)
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                //  Toast.makeText(this,"working!",Toast.LENGTH_SHORT).show();


                break;

            case R.id.Rbutton2:
                if (checked)
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                // Toast.makeText(this,"working!",Toast.LENGTH_SHORT).show();
                break;

        }
    }
     public void next(View v){
        Intent detailIntent = new Intent(this, DetailActivity.class);
        startActivity(detailIntent);

    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
            MenuItem shareItem = menu.findItem(R.id.share_item);
            if(shareItem != null){
                mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

            }
            setShareIntent();
        return true;
    }
    private void setShareIntent(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Amazing App!");
        mShareActionProvider.setShareIntent(shareIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share_item) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
