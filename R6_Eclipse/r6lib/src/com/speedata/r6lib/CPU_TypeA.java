package com.speedata.r6lib;

import android.util.Log;

import com.android.hflibs.CpuA_native;

import java.util.Arrays;

/**
 * Created by brxu on 2016/12/27.
 */

public class CPU_TypeA implements IR6Manager  {
    private CpuA_native devA = new CpuA_native();
    private static String TAG = "CPUA";
    public String result = "未改动";
    public String AIDString;
    public String Aai;



    @Override
    public int InitDevice() {
        if (devA.InitDev() != 0) {
            Log.i(TAG,1 + "");
            return 1;
        }
        Log.i(TAG, 0 + "");
        return 0;
    }

    @Override
    public void ReleaseDevice() {
        devA.ReleaseDev();
        Log.i(TAG, "下电");

    }

    @Override
    public byte[] SearchCard() {
        byte[] IDall = null;

        byte[] ID = devA.SearchCard();
        if (ID == null) {
            result = "搜不到卡";
            Log.i(TAG, "搜不到卡");
            return null;
        }
        IDall = ID;
        StringBuilder IDStringA = new StringBuilder();
        for (byte a : ID) {
            IDStringA.append(String.format("%02X", a));
        }
        AIDString = IDStringA.toString();
        Log.i(TAG, AIDString);
        return IDall;
    }

    @Override
    public int Deselect() {
        int desall = 0;

        desall = devA.deselect();
        Log.i(TAG, desall + "");
        return desall;
    }

    @Override
    public byte[] ReadCard() {
        byte[] atsall = null;

        byte[] ats = devA.exec_rats();
        if (ats == null) {
            result = "读不到卡内数据";

            Log.i(TAG, result);
            return null;
        } else {
            atsall = ats;
            StringBuilder ai = new StringBuilder();

            for (byte i : ats) {
                ai.append(String.format("%02X", i));
            }
            Aai = ai.toString();
        }
        Log.i(TAG, Arrays.toString(atsall));

        return atsall;
    }

    @Override
    public byte[] ExecCmdInput(byte[] input) {

        byte[] execall = null;
        execall = devA.exec_cmd(input);
        Log.i(TAG, Arrays.toString(execall));
        return execall;
    }
}
