package com.eatdafood.myapplication;

        import android.content.Intent;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.eatdafood.MyApplication.MESSAGE";
    public final static String EXTRA_INT = "0";

    private donut tracker;
    private int upgraded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tracker = new donut();
        upgraded = 1;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * send message method
     */
    /*
    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/

    public void makeDonut(View view){
        tracker.makeDon();
        this.updateView();
    }

    public void eatDonut(View view){
        if(tracker.getDon()-upgraded>=0) {
            tracker.eatDon();
            this.updateView();
        }
    }

    public void doubleDonuts(View view){
        if(tracker.getDon() >= (10*upgraded)){
            tracker.eatDonNum(10*upgraded);
            upgraded++;
            tracker.addToMult(1);
            this.updateView();
        }
    }
/*
    public void makeDonutAuto(View view){
        if(tracker.getDon() >= 10){
            tracker.eatDonNum(10);
            TextView textView = (TextView) findViewById(R.id.dcounter);
            autoMaker yups = new autoMaker(tracker,this);
            String dCounterString = Integer.toString(tracker.getDon());
            textView.setText(dCounterString);
            yups.start();
        }
    }
*/

    public void updateView(){
        TextView textView = (TextView) findViewById(R.id.dcounter);
        String dCounterString = Integer.toString(tracker.getDon());
        textView.setText(dCounterString);
    }
}

/*
class autoMaker extends Thread {
    donut tracker;
    MainActivity view;
    autoMaker(donut count,MainActivity v) {
        tracker = count;
        view = v;
    }

    @Override
    public void run(){
        try {
            while(true) {
                sleep(1000);
                tracker.makeDon();
                String dCounterString = Integer.toString(tracker.getDon());
                //view.setText(dCounterString);
                //view.updateView();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}*/

class donut{
    private int donuts;
    private int multiplier;
    public donut(){
        donuts = 0;
        multiplier = 1;
    }
    public synchronized void makeDon(){
        donuts=donuts+multiplier;
    }
    public synchronized void eatDon(){
        donuts=donuts-multiplier;
    }
    public synchronized void eatDonNum(int x){
        donuts = donuts - x;
    }
    public void addToMult(int x){
        multiplier++;
    }
    public int getDon(){
        return donuts;
    }
}