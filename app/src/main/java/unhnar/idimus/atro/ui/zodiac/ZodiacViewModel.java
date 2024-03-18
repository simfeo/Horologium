package unhnar.idimus.atro.ui.zodiac;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ZodiacViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ZodiacViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is zodiac fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
