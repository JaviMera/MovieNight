package movienight.javi.com.movienight.dialogs.VoteDialog;

/**
 * Created by Javi on 11/2/2016.
 */

public class VoteDialogFragmentPresenter {

    private VoteDialogFragmentView mView;
    public VoteDialogFragmentPresenter(VoteDialogFragmentView view) {

        mView = view;
    }

    public void setVoteEditText(String text) {

        mView.setVoteEditText(text);
    }
}
