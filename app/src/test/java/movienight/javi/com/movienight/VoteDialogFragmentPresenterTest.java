package movienight.javi.com.movienight;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import movienight.javi.com.movienight.dialogs.VoteDialog.VoteDialogFragmentPresenter;
import movienight.javi.com.movienight.dialogs.VoteDialog.VoteDialogFragmentView;

/**
 * Created by Javi on 11/2/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class VoteDialogFragmentPresenterTest {

    @Mock
    public VoteDialogFragmentView mView;

    private VoteDialogFragmentPresenter mPresenter;

    @Before
    public void setUp() throws Exception {

        mPresenter = new VoteDialogFragmentPresenter(mView);
    }

    @Test
    public void setVoteEditText() throws Exception {

        // Arrange
        String text = "";

        // Act
        mPresenter.setVoteEditText(text);

        // Assert
        Mockito.verify(mView).setVoteEditText(text);
    }
}