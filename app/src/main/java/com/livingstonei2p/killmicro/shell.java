package com.livingstonei2p.killmicro;

import java.io.DataOutputStream;
import java.io.IOException;

public class shell {
    public static Process sudo(String...strings) {
        try{
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            for (String s : strings) {
                outputStream.writeBytes(s+"\n");
                outputStream.flush();
            }

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            try {
                su.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            outputStream.close();
            return su;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
