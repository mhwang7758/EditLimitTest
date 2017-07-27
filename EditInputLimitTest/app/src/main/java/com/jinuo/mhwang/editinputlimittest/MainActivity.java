package com.jinuo.mhwang.editinputlimittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    /**
     *  "."号相对于字符串长度偏移量
     */
    public static final int POINT_OFFSET = 3;

    /**
     *  取得点的最少长度
     */
    public static final int GET_POINT_LOWEST_LENGTH = 4;

    /**
     *  可以输入的最大数
     */
    public static final double MAX_INPUT = 100.0;

    /**
     *  输入限制的正则表达式
     */
    public static final String REGEX_INPUT_LIMIT = "[0-9.]";

    EditText et_beLimited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_beLimited = (EditText) findViewById(R.id.et_beLimited);

//        et_beLimited.setFilters(new InputFilter[]{getLimitFilter()});

        et_beLimited.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private InputFilter getLimitFilter(){
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Log.d("------>", "source is "+source.toString()+   // 本次输入字符
                        "\nstart is "+start+    // start和end一般为0和1，即sorce长度
                        "\nend is "+end+
                        "\ndest is "+dest+      // 上一次输入内容
                        "\ndstart is "+dstart+  // 光标开始位置
                        "\ndend is "+dend);     // 光标结束位置

                // 限制1：只能输入数字和"."
                Pattern p = Pattern.compile(REGEX_INPUT_LIMIT);
                Matcher matcher = p.matcher(source.toString());
                if (!matcher.find()){
                    return "";
                }

                // 限制2：首位不能输入"."
                if (dend == 0 && source.toString().equals(".")){
                    return "";                  // 这里如果返回null无效
                }

                // 限制3：如果已经有“.”，则不能再输入“.”
                if (dest.toString().contains(".") && source.equals(".")){
                    return "";
                }

                // 限制4：如果上一个字符为0，则下一位只能输入点
                if (dest.toString().equals("0") && !source.toString().equals(".")){
                    return "";
                }

                // 限制5：如果大于100.0，则不能输入
                double inputNum = Double.parseDouble(dest.toString()+source);
                if (inputNum > MAX_INPUT){
                    return "";
                }

                // 限制6：如果上次上两位数前是".",则不能再输入(0.01长度为4)
                if (dest.length() >= GET_POINT_LOWEST_LENGTH
                        && dest.charAt(dest.length()-POINT_OFFSET) == '.'){
                    return "";
                }

                return null;
            }
        };

        return filter;
    }
}
