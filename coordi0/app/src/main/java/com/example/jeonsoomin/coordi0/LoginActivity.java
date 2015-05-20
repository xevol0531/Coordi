package com.example.jeonsoomin.coordi0;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;



public class LoginActivity extends ActionBarActivity {

    RelativeLayout loginLayout;
    RelativeLayout enrollLayout;

    EditText id;
    EditText pw;
    EditText newEmail;
    EditText newPW;
    EditText rePW;
    EditText newName;
    EditText newBirth;

    Button loginBt;
    Button enrollBt;
    Button compBt;

    private String return_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Context context = this;

        id = (EditText) findViewById(R.id.Email);
        pw = (EditText) findViewById(R.id.PW);
        newEmail = (EditText) findViewById(R.id.new_Email);
        newPW = (EditText) findViewById(R.id.new_PW);
        rePW = (EditText) findViewById(R.id.re_PW);
        newName = (EditText) findViewById(R.id.new_Name);
        newBirth = (EditText) findViewById(R.id.new_Birth);
        loginBt = (Button) findViewById(R.id.LoginBt);
        enrollBt = (Button) findViewById(R.id.EnrollBt);
        compBt=(Button) findViewById(R.id.Comp_Bt);

        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String Email = id.getText().toString();
                    String Pw = pw.getText().toString();
                    String message = Email + "/" + Pw;

                    TCPclient tcpThread = new TCPclient(message);

                    Thread thread = new Thread(tcpThread);

                    thread.start();
                } catch (Exception e) {

                }
            }
        });

        loginBt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {


                // 디비 연동 후 아이디 비번 체크

                String Email = id.getText().toString();
                String Pw = pw.getText().toString();
                if (Email.equals("admin@naver.com")) {
                    if (Pw.equals("1q2w3e")) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다. ", Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "이메일이 틀렸습니다. ", Toast.LENGTH_LONG).show();
                }

            }
        });

        enrollBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_enroll);
            }
        });
    }

    public void LoginBtClicked() {

    }


    public void EnrollBtClicked(){
//        RelativeLayout enrollLayout = (RelativeLayout) findViewById(R.id.enrollLayout);
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(R.layout.activity_enroll, enrollLayout, true);
        loginLayout.setVisibility(View.INVISIBLE);
        enrollLayout.setVisibility(View.VISIBLE);
    }
    public void CompBtClicked(){



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

    private class TCPclient implements Runnable {

        private static final String serverIP = "127.0.0.1"; // 서버 아이피

        private static final int serverPort = 4444; // ex: 5555 // 접속 포트

        private Socket inetSocket = null;

        private String msg;
        // private String return_msg;

        public TCPclient(String _msg) {

            this.msg = _msg;

        }
        public void run() {

            // TODO Auto-generated method stub

            try {

                Log.d("TCP", "C: Connecting...");
                inetSocket = new Socket(serverIP ,serverPort);

                //inetSocket.connect(socketAddr);
                try {
                    Log.d("TCP", "C: Sending: '" + msg + "'");

                    PrintWriter out = new PrintWriter(

                            new BufferedWriter(new OutputStreamWriter(

                                    inetSocket.getOutputStream())), true);
                    out.println(msg);

                    Log.d("TCP", "C: Sent.");

                    Log.d("TCP", "C: Done.");
                    BufferedReader in = new BufferedReader(

                            new InputStreamReader(inetSocket.getInputStream()));

                    return_msg = in.readLine();
                    Log.d("TCP", "C: Server send to me this message -->"

                            + return_msg);

                } catch (Exception e) {

                    Log.e("TCP", "C: Error1", e);

                } finally {

                    inetSocket.close();

                }

            } catch (Exception e) {

                Log.e("TCP", "C: Error2", e);

            }

        }// run

    }// TCPclient

}
