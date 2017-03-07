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
        import android.renderscript.Sampler;
        import android.util.AttributeSet;
        import android.util.Log;
        import android.view.View;

        import com.example.simon.bubble_level.R;


/**
 * Created by Guillaume Touzin on 2016-08-24.
 */
public class Drawing_Stud extends View{

    private SensorManager sManager;

    //Couleur des cercles et du texte
    private int circleCol,detectCol,labelCol, basCol, milieuCol,hautCol;

    //Texte à afficher
    private String circleText;

    //Objet Paint pour notre custom View
    private Paint circleBasPaint;
    private Paint circleMilieuPaint;
    private Paint circleHautPaint;
    private Paint labelPaint;

    private long temps_event=1;         //Variable contenant le moment du dernier affichage

    //Propriétés de l'écran
    int viewWidthHalf;      //Centre
    int viewHeightHalf;     //Centre
    int radius;

    private float delta;                    //Laps de temps entre deux affichage
   // private float t;
    private float NS2S;

    private int limit1, limit2,limit3;

    public SensorEventListener sel = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = null;
            sensor = event.sensor;

            if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

                double M=Math.sqrt(Math.pow(event.values[0],2)+Math.pow(event.values[1],2)+Math.pow(event.values[2],2));
                setLabelText("Champ Magnetique \nX :" + String.format("%.2f",event.values[0]) + "\n Y : " + String.format("%.2f",event.values[1]) + "\n Z : " + String.format("%.2f",event.values[2])+ "\n M : " + String.format("%.2f",M));
//TODO enlever le texte

                if ((event.timestamp - temps_event) * NS2S > delta) {
                    temps_event = event.timestamp;

                   if(M<=limit1)
                   {
                       setCirBasCol(circleCol);
                       setCirMilCol(circleCol);
                       setCirHautCol(circleCol);
                   }
                    else
                   {
                       if(M>limit1&&M<=limit2)
                       {
                           setCirBasCol(detectCol);
                           setCirMilCol(circleCol);
                           setCirHautCol(circleCol);
                       }
                       else
                       {
                           if(M>limit2&&M<=limit3)
                           {
                               setCirBasCol(detectCol);
                               setCirMilCol(detectCol);
                               setCirHautCol(circleCol);
                           }
                           else
                           {
                               if(M>limit3)
                               {
                                   setCirBasCol(detectCol);
                                   setCirMilCol(detectCol);
                                   setCirHautCol(detectCol);
                               }

                           }
                       }
                   }


                }


            }  else {
            }

        };

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public Drawing_Stud(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        //Constante pour le filtrage des données du sensor
        delta=0.01f;
        NS2S =1.0f/1000000000.0f;

        //Objets à dessiner
        circleMilieuPaint = new Paint();
        circleBasPaint = new Paint();
        circleHautPaint = new Paint();
        labelPaint = new Paint();


        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LovelyView, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            circleText = a.getString(R.styleable.LovelyView_circleLabel);
            circleCol = a.getInteger(R.styleable.LovelyView_circleColor, 0);//0 is default
            detectCol = a.getInteger(R.styleable.LovelyView_detectColor, 0);
            labelCol = a.getInteger(R.styleable.LovelyView_labelColor, 0);
            basCol=circleCol;
            milieuCol=circleCol;
            hautCol=circleCol;
        } finally {
            a.recycle();
        }

        //Définition des valeurs limite de sensibilité du détecteur
        setHalfLimit();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        //Moitier de la longueur et largeur de l'écran (sert pour déterminer le rayon)
        viewWidthHalf = this.getMeasuredWidth()/2;
        viewHeightHalf = this.getMeasuredHeight()/2;

        radius=30;

        //TODO ajouter l'image
        //Cercle imitant des lumières----------------------------------------------------------------------------------------
        circleMilieuPaint.setStyle(Style.FILL);
        circleMilieuPaint.setAntiAlias(true);

        //UTilise les valeurs de activity_stud pour colorer le cercle
        circleMilieuPaint.setColor(milieuCol);

        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circleMilieuPaint);

        //---------------------------------------------------------------------------
        circleBasPaint.setStyle(Style.FILL);
        circleBasPaint.setAntiAlias(true);
        circleBasPaint.setColor(basCol);


        canvas.drawCircle(viewWidthHalf, viewHeightHalf+2*radius, radius, circleBasPaint);

        //---------------------------------------------------------------------------
        circleHautPaint.setStyle(Style.FILL);
        circleHautPaint.setAntiAlias(true);
        circleHautPaint.setColor(hautCol);

        canvas.drawCircle(viewWidthHalf, viewHeightHalf-2*radius, radius, circleHautPaint);


//TODO enlever le texte
        //Affichage des coordonnées -------------------------------------------------------------------------------------------------------
        //UTilise les valeurs de activity_stud pour afficher le texte
        labelPaint.setColor(labelCol);
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setTextSize(30);

        //Affichage du texte
        canvas.drawText(circleText, viewWidthHalf, viewHeightHalf+200, labelPaint);

        //Activation de la lecture des sensors
        sManager = (SensorManager) this.getContext().getSystemService(Context.SENSOR_SERVICE);
        sManager.registerListener(sel, sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);

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
    public void setCirBasCol(int newColor){
        //update the instance variable
        basCol=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }

    public void setCirMilCol(int newColor){
        //update the instance variable
        milieuCol=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }

    public void setCirHautCol(int newColor){
        //update the instance variable
        hautCol=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }

    public void setHalfLimit(){
       limit1=55;
        limit2=80;
        limit3=100;
    }

    public void setThreeQuarterLimit(){
        limit1=55;
        limit2=63;
        limit3=70;
    }

    public void setOneLimit(){
        limit1=55;
        limit2=57;
        limit3=60;
    }

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


    protected void onResume(){
        //TODO Gèrer le retour vers l'app

    }


    protected  void onPause(){

    }




}
