package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhu on 17/4/1.
 */

public class InputView extends ConstraintLayout {
    public String tag;
    public InputViewDelegate delegate;

    @BindView(R.id.input_editText)
    EditText editText;

    @BindView(R.id.input_sendBtn)
    Button sendButton;

    @OnClick(R.id.input_sendBtn)
    void sendButtonClick() {
        hideKeyBoard();
        if (delegate != null) delegate.getInputContent(editText.getText().toString(), tag);
    }

    public InputView(Context context) {
        super(context, null);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_input, this);
        ButterKnife.bind(this);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendButtonClick();
                    return true;
                }
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.v("info", "before"+String.valueOf(s));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.v("info", "changed"+String.valueOf(s));
                if (String.valueOf(s).equals("")) sendButton.setEnabled(false);
                else sendButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setDelegate(InputViewDelegate delegate) {
        this.delegate = delegate;
    }

    public void setEditText(String content, String hint) {
        editText.setText(content);
        editText.setHint(hint);
    }

    public void getFocus() {
        editText.requestFocus();
        editText.setSelection(editText.getText().length());
    }

    public void loseFocus() {
        editText.clearFocus();
    }

    public void openKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

    }
}
