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
import android.view.View;

import com.example.simon.bubble_level.R;

/**
 * Created by Guillaume Touzin on 2016-08-10.
 */
public class Drawing_bubble extends View {


    private SensorManager sManager;

    //Couleur des cercles et du texte
    private int circleCol, centerCol,zoneCol, dotCol, labelCol;

    //Angle à afficher
    private String circleText;

    //Objet Paint pour notre custom View
    private Paint circlePaint;
    private Paint centerPaint;
    private Paint zonePaint;
    private Paint dotPaint;

    //Coordonnées de la bulle
    private int x,y;

    private float temps_event=0.1f;         //Variable contenant le moment du dernier affichage

    //Propriétés du cercle
    int viewWidthHalf;      //Centre
    int viewHeightHalf;
    int radius;

    private float delta;                    //Laps de temps entre deux affichage
    private float t;
    private float alpha;               //Paramètre du filtre passe-bas
    private float NS2S;

    //Matrices de rotation et de stockages
    private float[] rMat = new float[16];
    private float[] angle=new float[4];
    private float[] angle_filtre=new float[4];
    private float[] bubble=new float[3];

    private int dot_size;


    public SensorEventListener sel = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = null;
            sensor = event.sensor;

            if (sensor.getType() == Sensor.TYPE_GRAVITY) {

                if ((event.timestamp - temps_event) * NS2S > delta) {
                    temps_event = event.timestamp;

                    moveBubble(event.values[0],event.values[1],event.values[2]);

                }

                setLabelText("Orientation \nX :" + String.format("%.2f",bubble[0]) + "\n Y : " + String.format("%.2f",bubble[1]) + "\n Z : " + String.format("%.2f",bubble[2]));

            }  else {
            }
//Log.d("My log", String.valueOf(event.values[2]));
        };


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public Drawing_bubble(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        //Constante pour le filtrage des données du sensor

        t=0.297f;
        delta=0.05f;
        alpha=t/(t+delta);
        NS2S =1.0f/1000000000.0f;

        //Objets à dessiner
        circlePaint = new Paint();
        centerPaint = new Paint();
        zonePaint = new Paint();

        dotPaint = new Paint();

        bubble[0]=0;
        bubble[1]=0;
        bubble[2]=9.8f;



        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LovelyView, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            circleText = a.getString(R.styleable.LovelyView_circleLabel);
            circleCol = a.getInteger(R.styleable.LovelyView_circleColor, 0);//0 is default
            centerCol = a.getInteger(R.styleable.LovelyView_centerColor, 0);
            zoneCol = a.getInteger(R.styleable.LovelyView_zoneColor, 0);
            dotCol = a.getInteger(R.styleable.LovelyView_dotColor, 0);
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

        //Détermine le rayon du cercle principal en selectionant la valeur la plus petite
        //et en soustrayant 10 pour laisser de l'espace entre le niveau et la bordure
        radius = 0;
        if(viewWidthHalf>viewHeightHalf)
            radius=viewHeightHalf-10;
        else
            radius=viewWidthHalf-10;

        dot_size=radius/8;

        //Cercle définisant le bull eye level----------------------------------------------------------------------------------------
        circlePaint.setStyle(Style.FILL);
        circlePaint.setAntiAlias(true);

        //UTilise les valeurs de activity_bubble pour colorer le cercle
        circlePaint.setColor(circleCol);

        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);

        //Cercle définisant la zone de delimitation du centre ----------------------------------------------------------------------------------------
        zonePaint.setStyle(Style.STROKE);
        zonePaint.setAntiAlias(true);

        //UTilise les valeurs de activity_bubble pour colorer le cercle
        zonePaint.setColor(zoneCol);
        zonePaint.setStrokeWidth(10.0f);

        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius/2, zonePaint);
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius/4, zonePaint);

        //Cercle définisant cercle du centre ----------------------------------------------------------------------------------------
        centerPaint.setStyle(Style.STROKE);
        centerPaint.setAntiAlias(true);
        centerPaint.setColor(centerCol);
        centerPaint.setStrokeWidth(4.5f);

        canvas.drawCircle(viewWidthHalf, viewHeightHalf, dot_size+5, centerPaint);

        //Cercle représentant la bulle -------------------------------------------------------------------------------------------------------
        dotPaint.setStyle(Style.FILL);
        dotPaint.setAntiAlias(true);

        //UTilise les valeurs de activity_bubble pour colorer le cercle
        dotPaint.setColor(dotCol);

        //Initialise les coordonnées de la bulle

        canvas.drawCircle(viewWidthHalf+bubble[0], viewHeightHalf-bubble[1], dot_size, dotPaint);

        //Affichage des coordonnées -------------------------------------------------------------------------------------------------------
        //UTilise les valeurs de activity_bubble afficher le texte
        circlePaint.setColor(labelCol);
        circlePaint.setTextAlign(Paint.Align.CENTER);
        circlePaint.setTextSize(30);

        //Affichage du texte
        canvas.drawText(circleText, viewWidthHalf, this.getMeasuredHeight()-30, circlePaint);

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
    public int getCenterColor(){
        return centerCol;
    }
    public int getZoneColor()   { return zoneCol; }
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

    //Méthode utiliser lorques l'app est en pause pour arrêter la lecture des sensors
    public void releaseSensor(){
        sManager.unregisterListener(sel);
    }

    //Réactive la lecture des sensors
    public void registerListenerSensor(){
        sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void moveBubble(float gravX, float gravY, float gravZ){

        double tempx= Math.pow(gravX,3);
        double tempy= Math.pow(gravY,3);


        //Change les coordonnées en x et y de la bulle
        if(Math.sqrt(Math.pow(tempx,2)+Math.pow(tempy,2))<radius-dot_size)
        {
            //Change les coordonnées en x et y de la bulle
            bubble[0]=alpha*bubble[0]+(1-alpha)*(float)tempx;
            bubble[1]=alpha*bubble[1]+(1-alpha)*(float)tempy;
        }
        else
        {
            //Calcul de la distance entre le centre de la bulle et le coté du cercle
            double hypothenuse=Math.sqrt(Math.pow(bubble[0],2)+Math.pow(bubble[1],2));

            bubble[0]=alpha*bubble[0]+(1-alpha)*(float)((radius-dot_size)*Math.cos(Math.acos(bubble[0]/hypothenuse)));
            bubble[1]=alpha*bubble[1]+(1-alpha)*(float)((radius-dot_size)*Math.sin(Math.asin(bubble[1]/hypothenuse)));

        }

        }




}
