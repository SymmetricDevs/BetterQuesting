package betterquesting.client.toolbox.tools;

import betterquesting.api.client.toolbox.IToolboxTool;
import betterquesting.api.utils.NBTConverter;
import betterquesting.api2.client.gui.controls.PanelButtonQuest;
import betterquesting.api2.client.gui.panels.lists.CanvasQuestLine;
import betterquesting.client.gui2.editors.designer.PanelToolController;
import betterquesting.network.handlers.NetQuestEdit;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ToolboxToolComplete implements IToolboxTool {
    private CanvasQuestLine gui = null;

    @Override
    public void initTool(CanvasQuestLine gui) {
        this.gui = gui;
    }

    @Override
    public void disableTool() {
    }

    @Override
    public void refresh(CanvasQuestLine gui) {
    }

    @Override
    public void drawCanvas(int mx, int my, float partialTick) {
    }

    @Override
    public void drawOverlay(int mx, int my, float partialTick) {
    }

    @Override
    public List<String> getTooltip(int mx, int my) {
        return null;
    }

    @Override
    public boolean onMouseClick(int mx, int my, int click) {
        if (click != 0 || !gui.getTransform().contains(mx, my)) return false;

        PanelButtonQuest btn = gui.getButtonAt(mx, my);

        if (btn == null) return false;
        if (PanelToolController.selected.size() > 0 && !PanelToolController.selected.contains(btn)) return false;

        List<PanelButtonQuest> btnList = PanelToolController.selected.size() > 0 ? PanelToolController.selected : Collections.singletonList(btn);
        List<UUID> questIDs = new ArrayList<>();

        for (PanelButtonQuest btn1 : btnList) {
            questIDs.add(btn1.getStoredValue().getKey());
        }

        NBTTagCompound payload = new NBTTagCompound();
        payload.setTag("questIDs", NBTConverter.UuidValueType.QUEST.writeIds(questIDs));
        payload.setBoolean("state", true);
        payload.setInteger("action", 2);
        NetQuestEdit.sendEdit(payload);

        return true;
    }

    @Override
    public boolean onMouseRelease(int mx, int my, int click) {
        return false;
    }

    @Override
    public boolean onMouseScroll(int mx, int my, int scroll) {
        return false;
    }

    @Override
    public boolean onKeyPressed(char c, int key) {
        if (PanelToolController.selected.isEmpty() || key != Keyboard.KEY_RETURN) return false;

        List<PanelButtonQuest> btnList = PanelToolController.selected;
        List<UUID> questIDs = new ArrayList<>();

        for (PanelButtonQuest btn : btnList) {
            questIDs.add(btn.getStoredValue().getKey());
        }

        NBTTagCompound payload = new NBTTagCompound();
        payload.setTag("questIDs", NBTConverter.UuidValueType.QUEST.writeIds(questIDs));
        payload.setBoolean("state", true);
        payload.setInteger("action", 2);
        NetQuestEdit.sendEdit(payload);

        return true;
    }

    @Override
    public boolean clampScrolling() {
        return true;
    }

    @Override
    public void onSelection(NonNullList<PanelButtonQuest> buttons) {
    }

    @Override
    public boolean useSelection() {
        return true;
    }
}
