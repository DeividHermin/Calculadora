package com.example.deivi.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainCalculadora extends AppCompatActivity {

    String strOperacion, strSigno;
    boolean hayOperacion, ultimoPulsadoEsSigno, finCalc, strSignoInicializado, memoriaVacia;
    float operacion, ultimoNum, memoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_layout);
        setTextCalculadora("0");
        strOperacion="";
        memoriaVacia=true;
        hayOperacion=ultimoPulsadoEsSigno=finCalc=strSignoInicializado=false; //finCalc se usa cuando se pulsa el boton =
    }

    public void pNumeros (View view){
        //Guarda el numero pulsado en str
        String pulsado = getPulsado(view);
        //Si se pulsa el bt= vacia el tv de arriba, elimina la operacion y pon que el ultimo numero es 0
        //Mira si en el textview hay algo escrito, si es solo un 0 lo sustituye por el numero, si hay mas cosas añade el numero al texto del tv
        if(pulsado.equals(".") && getTextCalculadora().equals("0")){
            pulsado="0.";
        }
        if(getTextCalculadora().equals("0") || finCalc || ultimoPulsadoEsSigno){
            setTextCalculadora(pulsado);
        } else {
            appendCalculadora(pulsado);
        }
        ultimoPulsadoEsSigno=finCalc=false;
    }
    
    public void pOperacion (View view){
        //LA PRIMERA VEZ QUE ENTRA SI NO HAY OPERACION NO AÑADE NADA A STROPERACION
        if(finCalc)
            return;
        //Obtiene el simbolo que se ha pulsado
        String str=getPulsado(view);
        /*if(!strSignoInicializado){
            strSigno = getPulsado(view);
            strSignoInicializado=true;
        }*/
            //str = getPulsado(view);

        //Si ya se habia pulsado + - * / la ultima vez, volver a pulsarlos solo cambia la operacion anterior,
        // else si hay una operacion pendiente hazla, actualizame el tvOperacion y muestra el resultado en tvCalculadora,
        //else pon el texto de la calculadora en 0
        float resultado;
        if(ultimoPulsadoEsSigno)
            strOperacion = strOperacion.subSequence(0, getLengthOperacion()-1) + str;
        else
            if(hayOperacion) {
                strOperacion = strOperacion + getTextCalculadora() + str;
                resultado = calcular(ultimoNum, Float.parseFloat(getTextCalculadora()), strSigno);
                setTextCalculadora("" + resultado);
                ultimoNum=resultado;
            } else {
                ultimoNum=Float.parseFloat(getTextCalculadora());
                strOperacion = ultimoNum + str;
                //setTextCalculadora("0");
            }
        setTextOperacion(strOperacion);
        strSigno=str;
        hayOperacion=ultimoPulsadoEsSigno=true;
    }

    public void pBorrarTexto(View view){
        setTextCalculadora("0");
    }

    public void pBorrarTodo (View view){
        setTextCalculadora("0");
        setTextOperacion("");
        hayOperacion=ultimoPulsadoEsSigno=false;
        strOperacion="";
    }

    //FALTA ENCADENAR LAS OPERACIONES CUANDO
    public void pBorrarUno (View view){
        if(getLengthCalculadora()>1)
            setTextCalculadora(""+getTextCalculadora().subSequence(0, getLengthCalculadora()-1));
        else
            setTextCalculadora("0");
        //ultimoPulsadoEsSigno=false;
    }

    public void igual (View view){
        //Controlar que ultimoNum, strSigno y nuevoNum not null
        float resultado=calcular(ultimoNum, Float.parseFloat(getTextCalculadora()),strSigno);
        setTextCalculadora("" + resultado);
        strOperacion="";
        setTextOperacion(strOperacion);
        ultimoNum=0;
        finCalc=true;
        hayOperacion=false;
    }

    public String getPulsado(View view){
        TextView text=(TextView)findViewById(view.getId());
        return text.getText().toString();
    }
    public int getLengthCalculadora(){
        TextView tv =(TextView)findViewById(R.id.tvCal);
        return tv.length();
    }
    public int getLengthOperacion(){
        TextView tv =(TextView)findViewById(R.id.tvOper);
        return tv.length();
    }
    public String getTextCalculadora(){
        TextView tv =(TextView)findViewById(R.id.tvCal);
        return tv.getText().toString();
    }

    public void setTextCalculadora(String str){
        TextView tv =(TextView)findViewById(R.id.tvCal);
        tv.setText(str);
    }

    public void appendCalculadora(String str){
        TextView tv =(TextView)findViewById(R.id.tvCal);
        tv.append(str);
    }

    public void setTextOperacion(String str){
        TextView tv =(TextView)findViewById(R.id.tvOper);
        tv.setText(str);
    }

    public void pDecimal(View view){
        String str=getTextCalculadora();
        boolean hayDecimal=false;
        for(int i=0; i<str.length() && !hayDecimal; i++)
            if((str.charAt(i)+"").equals(".")){
                hayDecimal=true;
            }

        if(!hayDecimal){
            pNumeros(view);
        }
    }
    
    public float calcular (float num1, float num2, String signo){
        float calculo=0;
        switch (signo){
            case "+": calculo=num1+num2;break;
            case "-": calculo=num1-num2;break;
            case "*": calculo=num1*num2;break;
            case "÷": calculo=num1/num2;break;
        }
        return calculo;
    }

    public void guardarMem(View view){
        memoria=Float.parseFloat(getTextCalculadora());
        memoriaVacia=false;
        Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
    }

    public void cargarMem(View view){
        if(memoriaVacia)
            Toast.makeText(this, "Memoria vacia", Toast.LENGTH_SHORT).show();
        else
            setTextCalculadora(memoria+"");
    }

    public void prueba(View view){
        TextView tv=(TextView)findViewById(view.getId());
        Toast.makeText(this, tv.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
