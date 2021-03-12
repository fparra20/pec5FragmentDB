package hfad.com.pec4mascotas;
import android.os.Bundle;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

// Nota: esta clase está prácticamente copiada del vídeo https://www.youtube.com/watch?v=i-7tUdtFbIg
public class ContactActivity extends AppCompatActivity {

    Session session = null;
    Context context = null;
    EditText msg, emailUsuario, nombreUsuario;
    String rec, subject, textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //Añadimos la toolbar a la activity
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Accedemos a la app bar y permitimos el boton Up
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        context = this;

        // Inicializamos el boton
        Button login = (Button) findViewById(R.id.botonsiguiente);

        // Inicializamos el campo de email
        emailUsuario = (EditText) findViewById(R.id.email);

        // Inicializamos el campo de nombre completo
        nombreUsuario = (EditText) findViewById(R.id.nombre_completo);

        // Inicializamos el mensaje
        msg = (EditText) findViewById(R.id.mensaje);

        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // El email donde irán a parar los correos
                rec = "odoo18.virrey2020@gmail.com";

                // El texto que saldrá en el asunto del email
                subject = "Nueva solicitud de contacto de " + emailUsuario.getText().toString();

                // El texto del interior del email
                // Lo formateamos en HTML porque así es como se va a mandar el mensaje
                textMessage = "<b>Nombre: </b>"+nombreUsuario.getText().toString() + "<br>"+
                        "<b>Email: </b>"+emailUsuario.getText().toString() + "<br>"+
                        "<b>Mensaje: </b>"+msg.getText().toString();

                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                session = Session.getDefaultInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        // Aquí tenemos que poner el correo desde el que mandamos los correos
                        // y su contraseña
                        return new PasswordAuthentication("odoo18.virrey2020@gmail.com", "proyectillos");
                    }
                });

                RetreiveFeedTask task = new RetreiveFeedTask();
                task.execute();
            }
        });
    }
    
   

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);

                // Indicamos de qué direccón viene el correo
                message.setFrom(new InternetAddress("odoo18.virrey2020@gmail.com"));

                // Indicamos los receptores (va a ser siempre el mismo, aunque lo guardamos en rec
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));

                // Indicamos el asunto
                message.setSubject(subject);

                // Indicamos el contenido del texto y marcamos que va a ser en HTML
                message.setContent(textMessage, "text/html; charset=utf-8");

                // Mandamos el mensaje
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            // Borramos todo lo que hayamos escrito en las variables
            emailUsuario.setText("");
            msg.setText("");
            nombreUsuario.setText("");

            // Mostramos un toast de que el mensaje ha sido enviado.
            Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
        }
    }
}