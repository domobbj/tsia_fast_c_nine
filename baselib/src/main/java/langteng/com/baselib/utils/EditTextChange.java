package langteng.com.baselib.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


/**
 * Created by lang on 16/1/8.
 * 输入框或者背景改变
 */
public class EditTextChange {
    private View view;
    private EditText editText;
    private EditTextChange  editTextChange=null;


    public  EditTextChange(View changeView, EditText editText){
        this.view =changeView;
        this.editText =editText;

    }

    public TextWatcher watcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            editStart = editText.getSelectionStart();
            editEnd = editText.getSelectionEnd();
//            if (temp.length() > 0) {
//                view.setBackgroundResource(R.drawable.question_haveanswer_edittext_bg);
//            } else {
//                view.setBackgroundResource(R.drawable.question_nocontent_edittext_bg);
//            }
        }
    };

}
