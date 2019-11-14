package com.cpunisher.ludowikakiller.util;

import android.content.Context;
import com.cpunisher.ludowikakiller.R;
import com.cpunisher.ludowikakiller.References;

import java.io.*;

public class Utils {

    /**
     * 用于生成由指定数量的相同字符的字符串
     * @param ch 相同的字符
     * @param count 字符的数量
     * @return 生成的字符串
     */
    public static String buildSequence(char ch, int count) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < count; i++) stringBuffer.append(ch);
        return stringBuffer.toString();
    }

    /**
     * 检查数据库文件是否存在，如果不存在就创建并写入
     * @param context 获取资源文件的上下文对象
     */
    public static void checkDatabaseFile(Context context) {
        String databaseFileName = References.DATABASE_PATH + References.DATABASE_NAME;
        File database = new File(databaseFileName);
        if (!database.exists()) {
            File dir = new File(References.DATABASE_PATH);
            if (!dir.exists()) dir.mkdir();

            FileOutputStream fileOutputStream = null;
            InputStream inputStream = null;
            try {
                fileOutputStream = new FileOutputStream(databaseFileName);
                inputStream = context.getResources().openRawResource(R.raw.ludowika);
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, count);
                    fileOutputStream.flush();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
