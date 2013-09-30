package com.arkami.myidkey.view;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-9-30
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class TabTag {
    /**
     * layout of the tab when selected
     */
    private int selectedTabLayoutId;
    /**
     * layout of the tab when not selected
     */
    private int unselectedTabLayoutId;

    /**
     *
     * @param selectedTabLayoutId
     * @param unselectedTabLayoutId
     */
    public TabTag(int selectedTabLayoutId, int unselectedTabLayoutId) {
        this.selectedTabLayoutId = selectedTabLayoutId;
        this.unselectedTabLayoutId = unselectedTabLayoutId;
    }

    /**
     *
     * @return
     */
    public int getSelectedTabLayoutId() {
        return selectedTabLayoutId;
    }

    /**
     *
     * @param selectedTabLayoutId
     */
    public void setSelectedTabLayoutId(int selectedTabLayoutId) {
        this.selectedTabLayoutId = selectedTabLayoutId;
    }

    /**
     *
     * @return
     */
    public int getUnselectedTabLayoutId() {
        return unselectedTabLayoutId;
    }

    /**
     *
     * @param unselectedTabLayoutId
     */
    public void setUnselectedTabLayoutId(int unselectedTabLayoutId) {
        this.unselectedTabLayoutId = unselectedTabLayoutId;
    }





}
