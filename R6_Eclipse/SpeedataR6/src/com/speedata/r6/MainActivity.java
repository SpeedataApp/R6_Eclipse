package com.speedata.r6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.speedata.r6lib.IR6Manager;
import com.speedata.r6lib.R6Manager;

import static com.speedata.r6.DataConversionUtils.HexString2Bytes;
import static com.speedata.r6lib.R6Manager.CardType.CPUA;
import static com.speedata.r6lib.R6Manager.CardType.CPUB;


public class MainActivity extends Activity implements View.OnClickListener {

    private EditText etShow;
    private EditText etCmdInput;
    private TextView tvTitle;
    private IR6Manager mIR6Manager;
    private Button btnCmdIn;
    private static String TAG = "CPUA_B";
    private Button btnInitdev;
    private Button btnReleasedev;
    private Button btnSearchcard;
    private Button btnDeselect;
    private Button btnExecrats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.message_title);
        Button btnCpuA = (Button) findViewById(R.id.cpu_a);
        Button btnCpuB = (Button) findViewById(R.id.cpu_b);
        Button btnISO15693 = (Button) findViewById(R.id.iso_15693);
        Button btnMifare = (Button) findViewById(R.id.mifare);
        Button btnMifareU = (Button) findViewById(R.id.mifare_u);
        btnCmdIn = (Button) findViewById(R.id.btn_cmd_in);

        etCmdInput = (EditText) findViewById(R.id.et_cmd_input);

        btnInitdev = (Button) findViewById(R.id.btn_init_dev); //设备初始化上电
        btnReleasedev = (Button) findViewById(R.id.btn_release_dev); //下电
        btnSearchcard = (Button) findViewById(R.id.btn_search_card); //寻卡
        btnDeselect = (Button) findViewById(R.id.btn_deselect); //移除卡片
        btnExecrats = (Button) findViewById(R.id.btn_exec_rats); //读卡

        etShow = (EditText) findViewById(R.id.et_show); //显示结果
        btnCpuA.setOnClickListener(this);
        btnCpuB.setOnClickListener(this);
        btnISO15693.setOnClickListener(this);
        btnMifare.setOnClickListener(this);
        btnMifareU.setOnClickListener(this);
        btnCmdIn.setOnClickListener(this);

        btnInitdev.setOnClickListener(this);
        btnReleasedev.setOnClickListener(this);
        btnSearchcard.setOnClickListener(this);
        btnDeselect.setOnClickListener(this);
        btnExecrats.setOnClickListener(this);

        setFalse();
        setCmdFalse();
        String startsuccess = getString(R.string.start_success) + "\n";
        etShow.setText(startsuccess);
        getLast();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {

        }
    }

    //将光标移动到最后显示最下面的信息.
    private void getLast() {
        Editable text = etShow.getText();
        Selection.setSelection(text, text.length());
    }

    //按钮配置
    private void setFalse() {
        btnInitdev.setEnabled(false);
        btnReleasedev.setEnabled(false);
        btnSearchcard.setEnabled(false);
        btnDeselect.setEnabled(false);
        btnExecrats.setEnabled(false);

    }

    private void setTrue() {
        btnInitdev.setEnabled(true);
        btnReleasedev.setEnabled(true);
        btnSearchcard.setEnabled(true);
        btnDeselect.setEnabled(true);
        btnExecrats.setEnabled(true);

    }

    private void setCmdFalse() {
        etCmdInput.setEnabled(false);
        btnCmdIn.setEnabled(false);
    }

    private void setCmdTrue() {
        etCmdInput.setEnabled(true);
        btnCmdIn.setEnabled(true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cpu_a:
                mIR6Manager = R6Manager.getInstance(CPUA);
                tvTitle.setText("");
                tvTitle.append(getString(R.string.title_title) + getString(R.string.title_cpu_a));
                etShow.append("CPU_A\n");
                getLast();
                setTrue();
                break;
            case R.id.cpu_b:
                mIR6Manager = R6Manager.getInstance(CPUB);
                tvTitle.setText("");
                tvTitle.append(getString(R.string.title_title) + getString(R.string.title_cpu_b));
                etShow.append("CPU_B\n");
                getLast();
                setTrue();
                break;
            case R.id.iso_15693:

                Intent intent1 = new Intent();
                intent1.setClass(this, ISO15693Activity.class);
                startActivity(intent1);

                break;
            case R.id.mifare:
                Intent intent2 = new Intent();
                intent2.setClass(this, MifareActivity.class);
                startActivity(intent2);
                break;
            case R.id.mifare_u:
                Intent intent3 = new Intent();
                intent3.setClass(this, UltralightActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_init_dev:
                int init = mIR6Manager.InitDevice();
                String initdev = getString(R.string.power_on) + init + "\n";
                etShow.append(initdev);
                getLast();
                break;
            case R.id.btn_release_dev:
                mIR6Manager.ReleaseDevice();
                String release = getString(R.string.power_off) + "\n";
                etShow.append(release);
                getLast();
                break;
            case R.id.btn_search_card:
                byte[] sCard = mIR6Manager.SearchCard();
                String sCards = "";
                if (sCard == null) {
                    String notfound = getString(R.string.not_found) + "\n";
                    etShow.append(notfound);
                    getLast();
                    return;
                }
                if (sCard != null) {
                    for (byte i : sCard) {
                        sCards += String.format("%02X", i);
                    }
                }
                etShow.append(sCards + "\n");
                getLast();
                break;


            case R.id.btn_deselect://将卡片移除有效范围
                int outCard = mIR6Manager.Deselect();
                String remove = getString(R.string.remove) + outCard + "\n";
                etShow.append(remove);
                getLast();
                break;

            case R.id.btn_exec_rats:
                byte[] eCard = mIR6Manager.ReadCard();
                String eCards = "";
                if (eCard == null) {
                    String failread = getString(R.string.fail_read) + "\n";
                    etShow.append(failread);
                    getLast();
                    return;
                }
                if (eCard != null) {
                    for (byte i : eCard) {
                        eCards += String.format("%02X", i);
                    }
                }
                etShow.append(eCards + "\n");
                getLast();
                setCmdTrue();

                break;
            //命令输入
            case R.id.btn_cmd_in:
                String tk = etCmdInput.getText().toString();
                int len = tk.length();
                if (len % 2 != 0) {
                    String makesure = getString(R.string.make_sure) + "\n";
                    etShow.append(makesure);
                    return;
                }
                byte[] cm = HexString2Bytes(tk);
                byte[] xs = mIR6Manager.ExecCmdInput(cm); //lib命令调用
                if (xs == null) {
                    tvTitle.setText("exchange l4 failed");
                    setCmdFalse();
                    mIR6Manager.ReleaseDevice();
                } else {
                    Log.i(TAG, "cmd retured value begin");
                    String res = "";
                    for (byte i : xs) {
                        res += String.format("%02x ", i);
                    }
                    etShow.append(res + "\n");
                    getLast();
                    tvTitle.setText("execute cmd ok");
                }

                break;

        }
    }
}


