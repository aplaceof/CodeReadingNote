package jp.kitabatakep.intellij.plugins.codereadingnote.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.Pair;
import com.intellij.ui.EditorTextField;
import jp.kitabatakep.intellij.plugins.codereadingnote.Topic;
import jp.kitabatakep.intellij.plugins.codereadingnote.TopicLine;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Function;

public class TopicLineAddDescAction extends AnAction
{
    Function<Void, Pair<Topic, TopicLine>> fetcher;

    Project project;

    public TopicLineAddDescAction(Project project, Function<Void, Pair<Topic, TopicLine>> fetcher)
    {
        super("add description", "add  description", AllIcons.General.Inline_edit);
        this.project = project;
        this.fetcher = fetcher;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabled(e.getProject() != null);
    }

    /**
     * modify the description of the file, what you see what you get
     * @param e
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e)
    {
        Pair<Topic, TopicLine> payload = fetcher.apply(null);

        if (payload.getSecond() == null) { return; }
        Topic topic = payload.getFirst();
        TopicLine  tl = payload.getSecond();


         JFrame f=new JFrame("note description");
         f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         JTextField desc=new JTextField(30);
         desc.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if((char)e.getKeyChar()==KeyEvent.VK_ENTER) {

                    tl.setDescription(desc.getText());
                    f.dispose();
                }
            }
        });
         f.setLayout(new GridLayout(2,2));// set layout
         f.add(desc);
         f.setSize(300,100);
         Toolkit kit = Toolkit.getDefaultToolkit();
         int x = (kit.getScreenSize().width - f.getWidth()) / 2;
         int y = (kit.getScreenSize().height - f.getHeight()) / 2;
         f.setLocation(x,y);
         f.setVisible(true);
    }
}
