package com.trianz.newshunthackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

public class NewsContentActivity extends AppCompatActivity {

    ImageView news_image;
    TextView news_title, news_content;
    Button news_url_button;
    ImageView share;
    private ImageView back;
    String share_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);





        String newsImage = getIntent().getStringExtra("newsItemImage");
        String newsTitle = getIntent().getStringExtra("newsItemTitle");
        String newsContent = getIntent().getStringExtra("newsItemContent");
        final String newsUrl = getIntent().getStringExtra("newsItemUrl");
        String newsPublishedAt = getIntent().getStringExtra("newsItemPublishedAt");

        share_data = newsUrl;

        news_image = (ImageView) findViewById(R.id.news_description_image);
        news_title = (TextView) findViewById(R.id.news_description_title);
        news_content = (TextView) findViewById(R.id.news_description_content);
        news_url_button = (Button) findViewById(R.id.news_description_link_button);
        back=(ImageView) findViewById(R.id.back_button) ;

        getSupportActionBar().hide();


        if (!newsImage.equals("null") && !newsImage.equals("")) {
            Picasso.with(this).load(newsImage).fit().placeholder(R.drawable.news_details_foreground).into(news_image);
        }

        news_title.setText(newsTitle);
        news_content.setText(newsContent);
        news_content.setMovementMethod(new ScrollingMovementMethod());

        news_url_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebViewActivity();
            }

        });

        share = (ImageView) findViewById(R.id.share_button);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share_data ); // Simple text and URL to share
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

    }



    private void openWebViewActivity() {
        Intent browserIntent = new Intent(NewsContentActivity.this, WebViewActivity.class);
        browserIntent.putExtra("URL", share_data);
        startActivity(browserIntent);
        this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                return true;
        }
        int id = item.getItemId();



            //noinspection SimplifiableIfStatement
            if (id == R.id.action_share) {

                return true;
            }



            return super.onOptionsItemSelected(item);



    }


        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate menu resource file.
            getMenuInflater().inflate(R.menu.menu_share, menu);





            // Locate MenuItem with ShareActionProvider
            MenuItem shareItem = menu.findItem(R.id.action_share);
            ShareActionProvider myShareActionProvider =
                    (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

            Intent myShareIntent = new Intent(Intent.ACTION_SEND);
            myShareIntent.setType("text/plain");
            myShareIntent.putExtra(Intent.EXTRA_TEXT, share_data);
            myShareActionProvider.setShareIntent(myShareIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            // Return true to display menu
            return true;
        }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


    }

