package br.primaria.math;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class GameActivity extends ActionBarActivity {
	
	String strResultadoTotal = " ";
	String strResultadoDigitado = "";
	
	int intRand1, intRand2, intOp;
	double dblPontos = 0;
	int intErrada = 0;
	
	Random gerador = new Random();
	
	Timer tmrTempo;
	int intTempo = 1;
	
	boolean boolStart = false;

	TextView txtPass;
	TextView txtAConta;
	TextView txtPontos;
	TextView txtTempo;
	TextView txtRecord;
	TextView txtMRec;
	TextView txtVAgo;

	TextView txtResponda;
	Button btnReset;
	Button btnComecar;
	
	ImageView imgChance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        	requestWindowFeature(Window.FEATURE_NO_TITLE);
        	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            
            super.onCreate(savedInstanceState);
            
            Typeface font = Typeface.createFromAsset(getAssets(), "Franchise.ttf");
            
            setContentView(R.layout.activity_game);
            
            txtTempo = (TextView) findViewById(R.id.tempo);
            txtTempo.setTypeface(font);
            
    		txtRecord = (TextView) findViewById(R.id.record);
    		txtRecord.setTypeface(font);
    		
            record();

            txtResponda = (TextView) findViewById(R.id.responda);
            txtResponda.setTypeface(font);

        	txtAConta = (TextView) findViewById(R.id.txtConta);
        	txtAConta.setTypeface(font);
        	
        	btnReset = (Button) findViewById(R.id.btnResett);
        	btnReset.setTypeface(font);
        	
        	btnComecar = (Button) findViewById(R.id.btnComecar);
        	
        	txtPontos = (TextView) findViewById(R.id.ponte);
        	txtPontos.setTypeface(font);
        	
        	imgChance = (ImageView) findViewById(R.id.imgChance);
        	
        	txtVAgo = (TextView) findViewById(R.id.vago);
        	txtVAgo.setTypeface(font);
        	txtMRec = (TextView) findViewById(R.id.mrec);
        	txtMRec.setTypeface(font);
        	txtPass  = (TextView) findViewById(R.id.txtResposta);
        	txtPass.setTypeface(font);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public void addNum(View view){
    	try {
    		txtPass  = (TextView) findViewById(R.id.txtResposta);
        	if(strResultadoDigitado.length() <= strResultadoTotal.length() && boolStart){
        		if(strResultadoDigitado.equals("0")) strResultadoDigitado = "";
        		switch (view.getId()) {
    			case R.id.btn0:
    				strResultadoDigitado += "0";
    				break;
    			case R.id.btn1:
    				strResultadoDigitado += "1";
    				break;
    			case R.id.btn2:
    				strResultadoDigitado += "2";
    				break;
    			case R.id.btn3:
    				strResultadoDigitado += "3";
    				break;
    			case R.id.btn4:
    				strResultadoDigitado += "4";
    				break;
    			case R.id.btn5:
    				strResultadoDigitado += "5";
    				break;
    			case R.id.btn6:
    				strResultadoDigitado += "6";
    				break;
    			case R.id.btn7:
    				strResultadoDigitado += "7";
    				break;
    			case R.id.btn8:
    				strResultadoDigitado += "8";
    				break;
    			case R.id.btn9:
    				strResultadoDigitado += "9";
    				break;
        		}
        		txtPass.setText(strResultadoDigitado);
        		
        		if(strResultadoDigitado.length() >= strResultadoTotal.length()){
        			if(strResultadoDigitado.equals(strResultadoTotal)){
        				//sucess
        				dblPontos += 1000/intTempo;
        				gerar();
        				intTempo = 1;
        				strResultadoDigitado = "";
        				txtPass.setText("");
        			}else{
        				//error
        				intErrada++;
        				switch (intErrada) {
        				case 1:
    			            imgChance.setImageResource(R.drawable.chance2);
    						break;
        				case 2:
    			            imgChance.setImageResource(R.drawable.chance1);
    						break;
        				case 3:
    			            //imgChance.setVisibility(view.GONE);
        					imgChance.setImageResource(R.drawable.chance0);
    						break;
    					}
        				if(intErrada >= 3){
        					txtPontos.setText(""+(int) dblPontos);
        					record();
        					record();
        					tmrTempo.cancel();
        					intTempo = 1;
        					dblPontos = 0;
        					intErrada = 0;
        					txtTempo.setText(""+intTempo);
            				strResultadoDigitado = "";
            				txtPass.setText("");
            				btnComecar.setBackgroundResource(R.drawable.btncomecar);
            				//txtAConta.setVisibility(View.GONE);
            	            txtTempo.setVisibility(View.INVISIBLE);
            				boolStart = false;
        				}else txtPass.setText("");
        				strResultadoDigitado = "";
        			}
        		}
        	}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    final Handler myHandler = new Handler();
    public void start(View view){
    	try {
    		if(!boolStart){
        		gerar();
            	
            	tmrTempo = new Timer();
                tmrTempo.schedule(new TimerTask() {
        			
        			@Override
        			public void run() {
        				myHandler.post(myRunnable);
        			}
        		}, 0, 1000);
            	
                btnComecar.setBackgroundResource(R.drawable.btnstarted);
                //txtAConta.setVisibility(View.VISIBLE);
                imgChance.setImageResource(R.drawable.chance3);
                //imgChance.setVisibility(View.VISIBLE);
                txtTempo.setVisibility(View.VISIBLE);
            	boolStart = true;
        	}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    final Runnable myRunnable = new Runnable() {
        public void run() {
			if(boolStart){
	        	intTempo++;
				txtTempo.setText(""+intTempo);
				if(intTempo >= 50){
		    		dblPontos -= 1000;
		    		gerar();
		    		intTempo = 1;
				}
			}
        }
    };
    
    public void gerar(){
    	try {
    		txtPontos.setText((int) dblPontos + "");
        	
        	if(dblPontos <= 1000){
        		intRand1 = gerador.nextInt(11);
            	intRand2 = gerador.nextInt(11);
            	intOp = gerador.nextInt(1);
        	}else if(dblPontos <= 2000){
        		intRand1 = gerador.nextInt(11);
            	intRand2 = gerador.nextInt(11);
            	intOp = gerador.nextInt(2);
        	}else if(dblPontos <= 3000){
        		intRand1 = gerador.nextInt(101);
            	intRand2 = gerador.nextInt(11);
            	intOp = gerador.nextInt(2);
        	}else if(dblPontos <= 4000){
        		intRand1 = gerador.nextInt(101);
            	intRand2 = gerador.nextInt(101);
            	intOp = gerador.nextInt(2);
        	}else if(dblPontos <= 7000){
        		intRand1 = gerador.nextInt(101);
            	intRand2 = gerador.nextInt(101);
            	intOp = gerador.nextInt(3);
            	if(intOp >= 2){
            		intRand1 = gerador.nextInt(11);
            		intRand2 = gerador.nextInt(11);
            	}
        	}else if(dblPontos <= 15000){
        		intRand1 = gerador.nextInt(101);
            	intRand2 = gerador.nextInt(101);
            	intOp = gerador.nextInt(4);
            	if(intOp >= 2){
            		intRand1 = gerador.nextInt(11);
            		intRand2 = gerador.nextInt(11);
            	}
        	}else if(dblPontos <= 30000){
        		intRand1 = gerador.nextInt(101);
            	intRand2 = gerador.nextInt(101);
            	intOp = gerador.nextInt(4);
            	if(intOp >= 2){
            		intRand2 = gerador.nextInt(11);
            	}
        	}else if(dblPontos <= 50000){
        		intRand1 = gerador.nextInt(101);
            	intRand2 = gerador.nextInt(101);
            	intOp = gerador.nextInt(4);
            	if(intOp == 2){
            		intRand2 = gerador.nextInt(11);
            	}
        	}
        	else{
        		intRand1 = gerador.nextInt(101);
            	intRand2 = gerador.nextInt(101);
            	intOp = gerador.nextInt(4);
        	}
        	
        	switch (intOp) {
        	case 0:
    			// +
    			txtAConta.setText(""+intRand1+" + "+intRand2);
    			
    			strResultadoTotal = ""+(intRand1+intRand2);
    			
    			break;
        	case 1:
    			// -
        		
        		if(intRand1 < intRand2){
        			int r = intRand1;
        			intRand1 = intRand2;
        			intRand2 = r;
        		}
        		
    			txtAConta.setText(""+intRand1+" - "+intRand2);
    			
    			strResultadoTotal = ""+(intRand1-intRand2);
    			
    			break;
        	case 2:
    			// *
    			txtAConta.setText(""+intRand1+" * "+intRand2);
    			
    			strResultadoTotal = ""+(intRand1*intRand2);
    			
    			break;
        	case 3:
    			// /
        		
        		if(intRand1 < intRand2){
        			int r = intRand1;
        			intRand1 = intRand2;
        			intRand2 = r;
        		}
        		int r = (int) (intRand1/intRand2);
    			txtAConta.setText((intRand1 * r) + " / "+ intRand1);
    			
    			strResultadoTotal = ""+r;
        		
    			break;
        	}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public void reset(View view){
    	try {
    		strResultadoDigitado = "";
    		txtPass.setText(null);
		} catch (Exception e) {
		}
    }
    
    public void record(){
    	try {
    		String FILENAME = "record";
    		String string = ""+(int) dblPontos;
    		
    		FileInputStream open = openFileInput(FILENAME);
    		
    		StringBuffer fileContent = new StringBuffer("");
    		byte[] buffer = new byte[1024];
    		int n;
			while ((n = open.read(buffer)) != -1) 
    		{ 
    		  fileContent.append(new String(buffer, 0, n)); 
    		}
    		
    		if(Integer.parseInt(fileContent.toString()) < (int) dblPontos){
    			FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        		fos.write(string.getBytes());
        		fos.close();
    		}
			txtRecord.setText(fileContent.toString());
    		
    		open.close();

    		
		} catch (Exception e) {
			try {
				String FILENAME = "record";
	    		String string = "0";
				FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
	    		fos.write(string.getBytes());
	    		fos.close();
	    		record();
			} catch (Exception e2) {
			}
    		
		}
    }
    
    public void passe(View view){
    	try {
    		if(boolStart){
        		dblPontos -= 500;
        		gerar();
        		intTempo = 1;
        	}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
}
