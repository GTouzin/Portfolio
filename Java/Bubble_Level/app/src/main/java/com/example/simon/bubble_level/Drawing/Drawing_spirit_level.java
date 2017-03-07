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
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.simon.bubble_level.R;

/**
 * Created by Guillaume Touzin on 2016-08-10.
 */
public class Drawing_spirit_level extends View {


    private SensorManager sManager;

    //Couleur des rectangles et du texte
    private int rectCol, centerCol,zoneCol, dotCol, labelCol;

    //Angle à afficher
    private String rectText;

    //Détecte si l'écran est en mode portrait ou paysage
    public int portrait;

    //Objet Paint pour notre custom View
    private Paint rectPaint;
    private Paint centerPaint;
    private Paint zonePaint;
    private Paint dotPaint;

    //Coordonnées de la bulle
    private int x,y;

    private float temps_event=0.1f;         //Variable contenant le moment du dernier affichage

    //Propriétés du rectangle
    float viewWidthHalf;                    //Coordonnées du centre
    float viewHeightHalf;                   //Coordonnées du centre
    float longueur;
    float cote_gauche;
    float cote_dessus;
    float cote_droit;
    float cote_dessous;

    private float delta;                    //Laps de temps entre deux affichage
    private float t;
    private float alpha;                    //Paramètre du filtre passe-bas
    private float NS2S;

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
//TODO enlever les angles
                setLabelText("Orientation \nX :" + String.format("%.2f",bubble[0]) + "\n Y : " + String.format("%.2f",bubble[1]) + "\n Z : " + String.format("%.2f",bubble[2]));

            }  else {
            }

        };


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public Drawing_spirit_level(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        //PAramètre pour le filtrage des données du sensor
        t=0.297f;
        delta=0.05f;
        alpha=t/(t+delta);
        NS2S =1.0f/1000000000.0f;

        //Objets à dessiner
        rectPaint = new Paint();
        centerPaint = new Paint();
        zonePaint = new Paint();
        dotPaint = new Paint();

        portrait=0;

        bubble[0]=0;
        bubble[1]=0;
        bubble[2]=9.8f;

        dot_size=18;

        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LovelyView, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            rectText = a.getString(R.styleable.LovelyView_circleLabel);
            rectCol = a.getInteger(R.styleable.LovelyView_circleColor, 0);//0 is default
            centerCol = a.getInteger(R.styleable.LovelyView_centerColor, 0);
            zoneCol = a.getInteger(R.styleable.LovelyView_zoneColor , 0);
            dotCol = a.getInteger(R.styleable.LovelyView_dotColor, 0);
            labelCol = a.getInteger(R.styleable.LovelyView_labelColor, 0);
        } finally {
            a.recycle();
        }

    }



    @Override
    protected void onDraw(Canvas canvas) {
        //Moitier de la longueur et largeur de l'écran (sert pour déterminer la longueur du niveau)
        viewWidthHalf = this.getMeasuredWidth()/2;
        viewHeightHalf = this.getMeasuredHeight()/2;

        //Détermine la longeur du niveau
        longueur = 0;
        if(viewWidthHalf>viewHeightHalf)
            longueur=viewHeightHalf;
        else
            longueur=viewWidthHalf;

        //Définit les cotés du rectangles contenant le niveau
        cote_gauche=viewWidthHalf-(longueur/2);
        cote_dessus=viewHeightHalf-(longueur/8);
        cote_droit=viewWidthHalf+(longueur/2);
        cote_dessous=viewHeightHalf+(longueur/8);

        //Rectangle définisant le niveau----------------------------------------------------------------------------------------
        rectPaint.setStyle(Style.FILL);
        rectPaint.setAntiAlias(true);

        //UTilise les valeurs de activity_spirit_level pour colorer le rectangle
        rectPaint.setColor(rectCol);

        canvas.drawRect(cote_gauche,cote_dessus,cote_droit,cote_dessous, rectPaint);

        //Rectangle définisant la zone de delimitation du centre ----------------------------------------------------------------------------------------
        zonePaint.setStyle(Style.STROKE);
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(zoneCol);
        zonePaint.setStrokeWidth(3.0f);

        //Rectangle du dedans
        canvas.drawRect(viewWidthHalf-(longueur/8),cote_dessus,viewWidthHalf+(longueur/8),cote_dessous, zonePaint);
        //Rectangle du dehors
        canvas.drawRect(viewWidthHalf-(longueur/5),cote_dessus,viewWidthHalf+(longueur/5),cote_dessous, zonePaint);


        //Cercle représentant la bulle -------------------------------------------------------------------------------------------------------
        dotPaint.setStyle(Style.FILL);
        dotPaint.setAntiAlias(true);

        //UTilise les valeurs de activity_bubble pour colorer le cercle
        dotPaint.setColor(dotCol);

        //Initialise les coordonnées de la bulle



        canvas.drawCircle(viewWidthHalf+bubble[0], viewHeightHalf-bubble[1], dot_size, dotPaint);

        //Affichage des coordonnées -------------------------------------------------------------------------------------------------------
        //UTilise les valeurs de activity_bubble afficher le texte
        rectPaint.setColor(labelCol);
        rectPaint.setTextAlign(Paint.Align.CENTER);
        rectPaint.setTextSize(30);

        //Affichage du texte
        canvas.drawText(rectText, viewWidthHalf, this.getMeasuredHeight()-30, rectPaint);

        //Activation de la lecture des sensors
        sManager = (SensorManager) this.getContext().getSystemService(Context.SENSOR_SERVICE);

        //sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        //sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        //sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        //sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    //Méthodes get pour les couleurs des cercles et des textes
    public int getRectColor(){
        return rectCol;
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
        return rectText;
    }

    //Méthodes set pour les couleurs des cercles
    public void setCircleColor(int newColor){
        //update the instance variable
        rectCol=newColor;
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
        rectText=newLabel;
        //redraw the view
        invalidate();
        requestLayout();
    }


    protected void onResume(){
        //TODO Gèrer le retour vers l'app

    }


    protected  void onPause(){

    }

    protected void moveBubble(float gravX, float gravY, float gravZ){

        //TODO Gèrer le changemnt d'orientation de l'écran
        double tempx= Math.pow(gravX,3);
        double tempy= Math.pow(gravY,3);
        float temp;

        //Change les coordonnées en x de la bulle
        temp=alpha*bubble[0]+(1-alpha)*(float)tempx;

        if(viewWidthHalf+temp<cote_droit-dot_size && viewWidthHalf+temp> cote_gauche+dot_size)
        {
            bubble[0]=temp;
        }
        else
        {
            if(tempx>0&&cote_droit-bubble[0]>=dot_size)
            {
                // bubble[0]=alpha*bubble[0]+(1-alpha)*(cote_droit-bubble[0]);
            }
            else
            {
                if(tempx<0&&bubble[0]-cote_gauche>=dot_size)
                {
                    // bubble[0]=alpha*bubble[0]+(1-alpha)*(bubble[0]-cote_dessous);
                }
            }
        }

        //Change les coordonnées en y de la bulle
        temp=alpha*bubble[1]+(1-alpha)*(float)tempy;

        if(viewHeightHalf+temp>=cote_dessus+dot_size && viewHeightHalf+temp<= cote_dessous-dot_size)
        {
            bubble[1]=temp;
        }
        else
        {
            if(tempy>=0&&cote_dessus-bubble[1]>=dot_size)
            {
               // bubble[1]=alpha*bubble[1]+(1-alpha)*(cote_dessus-bubble[1]);
            }
            else
            {
                if(tempy<0&&bubble[1]-cote_dessous>=dot_size)
                {
                   // bubble[1]=alpha*bubble[1]+(1-alpha)*(bubble[1]-cote_dessous);
                }
            }
        }





    }




}
