package com.kartuzov.testappgit.gittest.MainListView;


        import java.io.IOException;
        import java.lang.ref.WeakReference;
        import java.net.MalformedURLException;
        import java.net.URL;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.AsyncTask;
        import android.widget.ImageView;

public class DownloadTask extends AsyncTask<String, Void, Boolean> {
    private final WeakReference<ImageView> imageViewReference;
    String url;
    Bitmap bm;

    public DownloadTask(ImageView v) {
        imageViewReference = new WeakReference<ImageView>(v);
    }


    @Override
    protected Boolean doInBackground(String... params) {
        url = params[0];
        bm = loadBitmap(url);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                if (bm != null) {
                    imageView.setImageBitmap(bm);
                }
            }
        }

    }

    public static Bitmap loadBitmap(String url) {
        try {
            URL newurl = new URL(url);
            Bitmap b = BitmapFactory.decodeStream(newurl.openConnection()
                    .getInputStream());
            return b;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}