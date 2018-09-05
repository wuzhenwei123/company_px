package com.base.upload.controller;

import com.baidu.ueditor.ActionEnter;
import com.base.utils.ConfigConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author keeny
 * @time 2015年02月04日 21:27:20
 */
public class MyActionEnter {
    ActionEnter actionEnter = null;

    public MyActionEnter(HttpServletRequest request, String rootPath) {
        String saveRootPath = ConfigConstants.UPLOAD_FILE_ROOT + ConfigConstants.SYS_PIC;
        actionEnter = new ActionEnter(request, saveRootPath, rootPath);
    }

    public String exec() {
        String s = actionEnter.exec();
        return s;
    }
}
