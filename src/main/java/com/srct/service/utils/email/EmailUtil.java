/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: SpringBootCommon
 * @Package: com.srct.service.utils.email
 * @author: ruopeng.sha
 * @date: 2018-08-22 13:31
 */
package com.srct.service.utils.email;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.ExcelUtils;
import com.srct.service.utils.JSONUtil;
import com.srct.service.utils.log.Log;

/**
 * @ClassName: EmailUtil
 * @Description: TODO
 */
public class EmailUtil {

    private static String buildCmd(List<String> optionList, String optionType) {
        String cmd = "";
        if (optionList != null && !optionList.isEmpty()) {
            for (String option : optionList) {
                cmd += " " + optionType + " " + JSONUtil.toJSONString(option) + " ";
            }
        }
        return cmd;
    }

    @SuppressWarnings("unchecked")
    private static String buildAttachmentObjCmd(List<EmailAttachment> attachmentList, List<File> fileList) {
        List<String> filePathList = new ArrayList<>();
        for (EmailAttachment atta : attachmentList) {
            List<Object> attaArray = new ArrayList<>();
            if (!(atta.getData() instanceof ArrayList<?>)) {
                attaArray.add(atta.getData());
            } else {
                attaArray.addAll((ArrayList<Object>)atta.getData());
            }
            File f = createFile(atta.getName());
            if (f == null)
                continue;
            fileList.add(f);
            String filePath = f.getAbsolutePath();
            ExcelUtils.generateResultExcelResponse(attaArray, filePath);
            filePathList.add(filePath);
        }
        return buildCmd(filePathList, "-a");
    }

    public static void sendEmail(@Valid EmailRepository repo) {
        List<File> fileList = new ArrayList<>();
        List<String> defaultBbc = Arrays.asList(BeanUtil.getProperty("my.mail.address").split(","));// 默认收件人
        String recipientsCmd = buildCmd(repo.getRecipients(), "");
        String attachPathCmd = buildCmd(repo.getAttachmentPath(), "-a");
        String defaultBccCmd = buildCmd(defaultBbc, "-b");
        String bccCmd = buildCmd(repo.getBcc(), "-b");
        String ccCmd = buildCmd(repo.getCc(), "-c");
        String titleCmd = buildCmd(Arrays.asList(repo.getTopic()), "-s");
        String attachmentCmd = buildAttachmentObjCmd(repo.getAttachment(), fileList);
        String bodyCmd = "echo " + JSONUtil.toJSONString(repo.getBody());
        String cmd = bodyCmd + "　| mail " + titleCmd + ccCmd + defaultBccCmd + bccCmd + attachPathCmd + attachmentCmd
            + recipientsCmd;
        Log.i(cmd);
        try {
            Process p = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", cmd});
            InputStream is = p.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                Log.d(line);
            }
            p.waitFor();
            is.close();
            reader.close();
            p.destroy();
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            Log.e(e.getClass().getSimpleName());
        } finally {
            for (File f : fileList) {
                f.delete();
            }
        }
    }

    private static File createFile(String newName) {
        File file = null;
        try {
            file = new File(File.separator + "tmp" + File.separator + newName + ".xls");
            int index = 1;
            newName += "(" + index + ")";
            while (!file.createNewFile()) {
                file = new File(file.getParent() + File.separator + newName + ".xls");
                newName = newName.replace("(" + index + ")", "(" + (index + 1) + ")");
                index++;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e(e.getClass().getSimpleName());
        }
        return file;
    }
}
