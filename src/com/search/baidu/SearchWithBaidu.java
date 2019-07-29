package com.search.baidu;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import java.awt.Desktop;

/**
 * @author Yang Wang (nreg)
 * @create 2019/7/29 19:02
 */
public class SearchWithBaidu extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        //判断是否支持Desktop扩展,如果支持则进行下一步
        if (Desktop.isDesktopSupported()) {
            Editor editor = (Editor) e.getData(PlatformDataKeys.EDITOR);
            SelectionModel model = editor.getSelectionModel();
            String selectedText = model.getSelectedText().trim();

            if (selectedText == null || selectedText.length() == 0) {
                return;
            } else {
                //创建desktop对象
                Desktop desktop = Desktop.getDesktop();
                try {
                    //将搜索的字符串转为URL请求编码格式
                    String seclectStr = URLEncoder.encode(selectedText.trim(), "UTF-8");
                    //使用百度引擎搜索
                    String url = "https://www.baidu.com/s?wd=" + seclectStr;
                    //调用默认浏览器打开网页
                    desktop.browse(new URI(url));
                } catch (IOException e1) {
                    //如果没有默认浏览器时，将引发下列异常
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    //打开浏览器失败
                    e1.printStackTrace();
                }
            }
        }
    }

    public void update(AnActionEvent e) {
        Editor editor = (Editor) e.getData(PlatformDataKeys.EDITOR);
        SelectionModel model = editor.getSelectionModel();
        e.getPresentation().setVisible(model.hasSelection());
    }
}
