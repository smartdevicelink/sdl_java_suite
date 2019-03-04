package android.os;

public abstract class AsyncTask<Params, Progress, Result> {


    abstract protected Result doInBackground(Params...params);

}
