package com.smartdevicelink.managers.screen.menu;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.util.DebugTool;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.menuCellIdMin;
import static com.smartdevicelink.managers.screen.menu.BaseMenuManager.parentIdNotFound;

/**
 * Created by Bilal Alsharifi on 1/20/21.
 */
class MenuReplaceDynamicOperation extends Task {
    private static final String TAG = "MenuReplaceDynamicOperation";

    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<FileManager> fileManager;
    private final WindowCapability defaultMainWindowCapability;
    private List<MenuCell> oldMenuCells;
    private final List<MenuCell> menuCells;
    private final MenuManagerCompletionListener operationCompletionListener;
    private final String displayType;
    private final DynamicMenuUpdatesMode dynamicMenuUpdatesMode;
    private int lastMenuId;
    private MenuConfiguration menuConfiguration;

    MenuReplaceDynamicOperation(ISdl internalInterface, FileManager fileManager, String displayType, DynamicMenuUpdatesMode dynamicMenuUpdatesMode, MenuConfiguration menuConfiguration, WindowCapability defaultMainWindowCapability, List<MenuCell> oldMenuCells, List<MenuCell> menuCells, MenuManagerCompletionListener operationCompletionListener) {
        super(TAG);
        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.displayType = displayType;
        this.dynamicMenuUpdatesMode = dynamicMenuUpdatesMode;
        this.menuConfiguration = menuConfiguration;
        this.defaultMainWindowCapability = defaultMainWindowCapability;
        this.oldMenuCells = oldMenuCells;
        this.menuCells = menuCells;
        this.operationCompletionListener = operationCompletionListener;
    }

    @Override
    public void onExecute() {
        start();
    }

    private void start() {
        if (getState() == Task.CANCELED) {
            return;
        }

        updateMenuCells(new CompletionListener() {
            @Override
            public void onComplete(boolean success) {
                finishOperation(success);
            }
        });
    }

    private void updateMenuCells(final CompletionListener listener) {

    }

    private void finishOperation(boolean success) {
        if (operationCompletionListener != null) {
            operationCompletionListener.onComplete(success, oldMenuCells);
        }
        onFinished();
    }

    public void setMenuConfiguration(MenuConfiguration menuConfiguration) {
    }
}
