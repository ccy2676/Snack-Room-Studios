package com.example.snackroomstudios.gestures;

import android.app.Activity;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;

public class MainActivity extends Activity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    private static final String DEBUg_TAG = "Gestures";
    private GestureDetectorCompat mDetector;

    private trackerController Dtracker;
    private trackerController Mtracker;
    private trackerController Stracker;
    private trackerController Ftracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
        Stracker = new trackerController();
        Ftracker = new trackerController();
        Dtracker = new trackerController();
        Mtracker = new trackerController(Ftracker,Stracker);
    }

    public void updateView(){
        TextView textView = (TextView) findViewById(R.id.dCounter);
        String dCounterString = "Donut Number: " + Integer.toString(Dtracker.getTrack());
        textView.setText(dCounterString);

        TextView textViewM = (TextView) findViewById(R.id.mCounter);
        String mCounterString = "$" + Integer.toString(Mtracker.getTrack());
        textViewM.setText(mCounterString);

        TextView textViewF = (TextView) findViewById(R.id.fCounter);
        String fCounterString = "Flour Level: " + Integer.toString(Ftracker.getTrack());
        textViewF.setText(fCounterString);

        TextView textViewS = (TextView) findViewById(R.id.sCounter);
        String sCounterString = "Sugar Level: " + Integer.toString(Stracker.getTrack());
        textViewS.setText(sCounterString);

    }

    public void makeDonut(){
        Dtracker.addTrack();
        this.updateView();
    }

    public void eatDonut(){
        if(Dtracker.getTrack() > 0 ) {
            Dtracker.subTrack();
            Mtracker.altMoney();
            System.out.println(Mtracker.getTrack());
            this.updateView();
        }
    }

    public void levelFlour(View view){
        if( Mtracker.getTrack() >= (Ftracker.getTrack() +1)* 10 ){
            Mtracker.subTrack((Ftracker.getTrack()+1) * 10);
            Ftracker.addTrack();
        }
        this.updateView();
    }

    public void levelSugar(View view){
        if( Mtracker.getTrack() >= (Stracker.getTrack() +1)* 10 ){
            Mtracker.subTrack((Stracker.getTrack() +1)* 10);
            Stracker.addTrack();
        }
        this.updateView();
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

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        this.makeDonut();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //Log.d(DEBUG_TAG, "onDown: " + event.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getRawY()<e2.getRawY() ) {
            this.makeDonut();
        }
        if (e1.getRawY()>e2.getRawY()){
            this.eatDonut();
        }
        this.updateView();
        return true;

    }
}

class trackerController{
    private int tracks;
    private int mult;
    trackerController fTracker;
    trackerController sTracker;

    public trackerController(){
        tracks = 0;
        mult = 1;
    }
    public trackerController(trackerController f,trackerController s){
        tracks = 0;
        mult = 1;
        fTracker = f;
        sTracker = s;
    }
    public synchronized void altMoney(){
        tracks=tracks +fTracker.getTrack() + sTracker.getTrack() + mult;
    }
    public synchronized void addTrack(){
        tracks=tracks + mult;
    }
    public synchronized void subTrack(){
        if( (tracks - mult ) >= 0)
          tracks=tracks-mult;
    }
    public synchronized void subTrack(int x){
        tracks = tracks - x;
    }
    public void addToMult(int x){
        mult++;
    }
    public int getTrack(){
        return tracks;
    }
}