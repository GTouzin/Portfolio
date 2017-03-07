package com.example.simon.bubble_level.Drawing;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.simon.bubble_level.R;

/**
 * Created by Guillaume Touzin on 2016-08-25.
 */
public class Drawing_inclinaison extends View {


    private SensorManager sManager;

    //Couleur des cercles et du texte
    private int circleCol, rectCol, labelCol;

    //Angle à afficher
    private String circleText;

    //Objet Paint pour notre custom View
    private Paint circlePaint;
    private Paint rectPaint;

    private float temps_event=0.1f;         //Variable contenant le moment du dernier affichage

    //Propriétés de l'écran
    int viewWidthHalf;      //Centre
    int viewHeightHalf;
    int radius;
    int angle;

    private float delta;                    //Laps de temps entre deux affichage
    private float t;
    private float alpha;               //Paramètre du filtre passe-bas
    private float NS2S;



    public SensorEventListener sel = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = null;
            sensor = event.sensor;

            if (sensor.getType() == Sensor.TYPE_GRAVITY) {

                if ((event.timestamp - temps_event) * NS2S > delta) {
                    temps_event = event.timestamp;
                }

                if(event.values[1]>0)
                {
                    setInclinaisonText((int) (Math.acos(-1*event.values[0]/9.81)*57.29577)+270);
                    setLabelText(String.format("%.2f",(Math.acos(event.values[0]/9.81)*57.29577)-90)+" degrées");
                }
                else
                {
                    setInclinaisonText((int) (Math.acos(event.values[0]/9.81)*57.29577)+90);
                    setLabelText( String.format("%.2f",Math.acos(event.values[0]/9.81)*57.29577-90)+" degrées");
                }


            }  else {
            }

        };


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public Drawing_inclinaison(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        //Constante pour le filtrage des données du sensor

        t=0.297f;
        delta=0.005f;
        alpha=t/(t+delta);
        NS2S =1.0f/1000000000.0f;

        //Objets à dessiner
        circlePaint = new Paint();
        rectPaint = new Paint();


        angle=0;

        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LovelyView, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            circleText = a.getString(R.styleable.LovelyView_circleLabel);
            circleCol = a.getInteger(R.styleable.LovelyView_circleColor, 0);//0 is default
            rectCol =a.getInteger(R.styleable.LovelyView_circleColor,0);
            labelCol = a.getInteger(R.styleable.LovelyView_labelColor, 0);
        } finally {
            a.recycle();
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        //Moitier de la longueur et largeur de l'écran (sert pour déterminer le rayon)
        viewWidthHalf = this.getMeasuredWidth()/2;
        viewHeightHalf = this.getMeasuredHeight()/2;

        if(viewWidthHalf>viewHeightHalf)
            radius=viewHeightHalf/6;
        else
            radius=viewWidthHalf/6;

        //Cadre de l'angle----------------------------------------------------------------------------------------
        circlePaint.setStyle(Style.FILL);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleCol);

        canvas.save();
        canvas.rotate(angle,viewWidthHalf, viewHeightHalf);
        canvas.drawRect(viewWidthHalf-3.0f*radius, viewHeightHalf-1.2f*radius, viewWidthHalf+3.0f*radius, viewHeightHalf+0.7f*radius, circlePaint);
        canvas.restore();


        //Affichage des coordonnées -------------------------------------------------------------------------------------------------------
        //UTilise les valeurs de activity_bubble afficher le texte
        circlePaint.setColor(labelCol);
        circlePaint.setTextAlign(Paint.Align.CENTER);
        circlePaint.setTextSize(60);

        canvas.save();
        canvas.rotate(angle,viewWidthHalf, viewHeightHalf);
        //Affichage du texte
        canvas.drawText(circleText, viewWidthHalf, viewHeightHalf, circlePaint);
        canvas.restore();

        //Activation de la lecture des sensors
        sManager = (SensorManager) this.getContext().getSystemService(Context.SENSOR_SERVICE);

        //sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        //sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        //sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        //sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    //Méthodes get pour les couleurs des cercles et des textes
    public int getCircleColor(){
        return circleCol;
    }

    public int getLabelColor(){
        return labelCol;
    }

    //Méthodes get la valeur des textes
    public String getLabelText(){
        return circleText;
    }

    //Méthodes set pour les couleurs des cercles
    public void setCircleColor(int newColor){
        //update the instance variable
        circleCol=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }
    public void setLabelColor(int newColor){
        //update the instance variable
        labelCol=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }

    public void setLabelText(String newLabel){
        //update the instance variable
        circleText=newLabel;
        //redraw the view
        invalidate();
        requestLayout();
    }

    public void setInclinaisonText(int angle){
        //update the instance variable
        this.angle=angle;
        //redraw the view
        invalidate();
        requestLayout();
    }

    public int getInclinaisonText(){
        return angle;
    }


    protected void onResume(){
        //TODO Gèrer le retour vers l'app

    }


    protected  void onPause(){

    }






}
