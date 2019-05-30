import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.io.File;

public class FileIconView extends FileView {
    private FileFilter filter;
    public FileIconView(FileFilter filter)
    {
        this.filter = filter;
    }
    // 重写该方法，为文件夹、文件设置图标
    public Icon getIcon(File f)
    {
        if (!f.isDirectory() && filter.accept(f))
        {
            return new ImageIcon("ico/pict.png");
        }
        else if (f.isDirectory())
        {
            // 获取所有根路径
            File[] fList = File.listRoots();
            for (File tmp : fList)
            {
                // 如果该路径是根路径
                if (tmp.equals(f))
                {
                    return  new ImageIcon("ico/dsk.png");
                }
            }
            return new ImageIcon("ico/folder.png");
        }
        // 使用默认图标
        else
        {
            return null;
        }
    }
}
