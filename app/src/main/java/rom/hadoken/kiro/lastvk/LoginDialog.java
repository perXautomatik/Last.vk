package rom.hadoken.kiro.lastvk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import rom.hadoken.kiro.lastvk.DAOK.UserDAO;
import rom.hadoken.kiro.lastvk.Logic.User;
import rom.hadoken.kiro.lastvk.Logic._Factory;
import rom.hadoken.kiro.util.SupportSuff;

/**
 * Created by Kiro on 11.05.2015.
 */
public class LoginDialog extends DialogFragment {


    View view;
    AlertDialog.Builder adb;
    EditText etName;
    EditText etPass;
    ProgressBar progressBar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.login_dialog, null);
        etName = (EditText) view.findViewById(R.id.etName);
        etPass = (EditText) view.findViewById(R.id.etPass);
        progressBar = (ProgressBar) view.findViewById(R.id.progressDialog);
        builder.setTitle("Enter your credentials here");
        builder.setView(view);
        builder.setPositiveButton("Login",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }
        );
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog dialog = (AlertDialog)getDialog();
        if(dialog != null)
        {
            Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    String name = etName.getText().toString();
                    String pass = etPass.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    etName.setVisibility(View.INVISIBLE);
                    etPass.setVisibility(View.INVISIBLE);
                    try {
                        registerUser(name, pass);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    private void registerUser(String name, String pass) throws ExecutionException, InterruptedException {
        class SendRegisterTask extends AsyncTask<String, Void, Boolean> {

            @Override
            protected Boolean doInBackground(String... params) {
                String name = params[0];
                String pass = params[1];
                _Factory.SIG = md5("api_key" + _Factory.KEY + "methodauth.getMobileSessionpassword" + pass + "username" + name + _Factory.SECRET);
                try {
                    URL url = new URL("https://ws.audioscrobbler.com/2.0/");
                    String urlParams = "method=auth.getMobileSession&username="+ name+"&password=" + pass + "&api_key=" + _Factory.KEY + "&api_sig=" + _Factory.SIG + "&format=json";
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(urlParams);
                    wr.flush();
                    wr.close();
                    int responseCode = con.getResponseCode();
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    JSONObject jsObj = new JSONObject(response.toString()).getJSONObject("session");
                    String userName = jsObj.getString("name");
                    String userKey = jsObj.getString("key");
                    int userSubs = jsObj.getInt("subscriber");
                    User user = new User(userName,userKey,userSubs);
                    UserDAO.setUserToken(user);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }

            private String md5(String s) {
                try {
                    MessageDigest m = MessageDigest.getInstance("MD5");
                    m.update(s.getBytes(), 0, s.length());
                    BigInteger i = new BigInteger(1,m.digest());
                    return String.format("%1$032x", i);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                progressBar.setVisibility(View.INVISIBLE);
                etName.setVisibility(View.VISIBLE);
                etPass.setVisibility(View.VISIBLE);
                if(aBoolean) {
                    dismiss();
                    Toast.makeText(getActivity(), "Yeah!", Toast.LENGTH_SHORT).show();
                    SupportSuff stuff = (SupportSuff)getActivity();
                    stuff.setLayoutAfterLogin();
                }else {
                    Toast.makeText(getActivity(), "Damn,something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }

        SendRegisterTask task = new SendRegisterTask();
        task.execute(name,pass);
    }
}



  /**/


                  /*  HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://ws.audioscrobbler.com/2.0/");
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("method", "auth.getMobileSession"));
                    nameValuePairs.add(new BasicNameValuePair("password", pass));
                    nameValuePairs.add(new BasicNameValuePair("username", name));
                    nameValuePairs.add(new BasicNameValuePair("api_key", _Factory.KEY));
                    nameValuePairs.add(new BasicNameValuePair("api_sig", _Factory.SIG));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httpPost);
                    BufferedReader rd = new BufferedReader(
                            new InputStreamReader(response.getEntity().getContent()));

                    StringBuffer result = new StringBuffer();
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    Log.d("hadoken", response.toString());*/