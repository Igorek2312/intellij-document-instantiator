package com.company.document.instantiator.util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Created by igorek2312 on 20.09.16.
 */
public class ClipboardUtil {
    private static ClipboardUtil ourInstance = new ClipboardUtil();

    public static ClipboardUtil getInstance() {
        return ourInstance;
    }

    private ClipboardUtil() {
    }

    public void copyToClipBoard(String s) {
        StringSelection stringSelection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

}
