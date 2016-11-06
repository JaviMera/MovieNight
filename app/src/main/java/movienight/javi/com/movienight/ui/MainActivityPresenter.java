package movienight.javi.com.movienight.ui;

/**
 * Created by Javi on 11/6/2016.
 */

public class MainActivityPresenter {

    private MainActivityView mView;

    public MainActivityPresenter(MainActivityView view) {

        mView = view;
    }


    public void setToolBarTitle(String title) {

        mView.setToolBarTitle(title);
    }
}
