package com.example.williamrudwall.mailappen;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainMail extends Activity {

    private ImageView imageview;
    private EditText mailAdressTextField, subjectTextField, messageTextField;
    private Button sendButton, attachmentButton;
    private Uri imageURI;
    private static final int PICK_FROM_GALLERY = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mail);
        mailAdressTextField = (EditText) findViewById(R.id.emailAdressTextEdit);
        subjectTextField = (EditText) findViewById(R.id.emailSubjectTextEdit);
        messageTextField = (EditText) findViewById(R.id.messageTextEdit);
        attachmentButton = (Button) findViewById(R.id.buttonAttachment);
        sendButton = (Button) findViewById(R.id.buttonSend);
        imageview = (ImageView) findViewById(R.id.theImageView);

        //knapp för att skicka ivä
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] {  mailAdressTextField.getText().toString()});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subjectTextField.getText().toString());
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, messageTextField.getText().toString());
                emailIntent.putExtra(Intent.EXTRA_STREAM, imageURI);
                startActivity(Intent.createChooser(emailIntent,"Sending email"));
            }
        });

        //Knapp för att välja en bild till mailet
        attachmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_FROM_GALLERY);
                }

        });
    }

// metod som tilldelar en bild till URI variabeln
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK
                && null != data) {
            imageURI = data.getData();
            imageview.setImageURI(imageURI);
        }
    }

}

