package com.smartdevicelink.api.view;

import android.widget.TextView;

import com.smartdevicelink.proxy.rpc.Show;

import java.util.EnumSet;

public class SdlTemplateView implements SdlView {

    public enum TemplateStatus{
        VALID,
        INVALID_TOO_MANY_TEXT_VIEWS,
        INVALID_TOO_MANY_BUTTON_VIEWS,
        INVALID_TEMPLATE_NOT_SUPPORTED,
        INVALID_NO_MAIN_VIEW
    }

    private SdlViewManager.LayoutTemplate mTemplate;

    private SdlView mLeftView;
    private SdlView mRightView;
    private SdlButtonView mSdlButtonView;
    private EnumSet<SdlViewManager.LayoutTemplate> mAvailableTemplateSet;

    public TemplateStatus setViews(SdlView mainView, SdlView secondaryView, SdlButtonView buttonView){
        TemplateStatus status = TemplateStatus.INVALID_TEMPLATE_NOT_SUPPORTED;;
        if(secondaryView == null){
            if(buttonView == null){
                status = setViews(mainView);
            } else {
                status =  setViews(mainView, buttonView);
            }
        } else if(buttonView == null){
            status =  setViews(mainView, secondaryView);
        } else if(mainView == null) {
            status =  TemplateStatus.INVALID_NO_MAIN_VIEW;
        } else {
            SdlViewManager.LayoutTemplate template;
            if(mainView instanceof SdlButtonView || secondaryView instanceof SdlButtonView){
                status =  TemplateStatus.INVALID_TOO_MANY_BUTTON_VIEWS;
            } else if (mainView.getClass() == secondaryView.getClass()) {
                if(mainView instanceof SdlGraphicView){
                    template = SdlViewManager.LayoutTemplate.DOUBLE_GRAPHIC_WITH_SOFTBUTTONS;
                    status =  createTemplate(template, mainView, secondaryView, buttonView);
                }
                if (mainView instanceof SdlTextView) {
                    status =  TemplateStatus.INVALID_TOO_MANY_TEXT_VIEWS;
                }
            } else if(mainView instanceof SdlTextView){
                template = SdlViewManager.LayoutTemplate.TEXT_AND_SOFTBUTTONS_WITH_GRAPHIC;
                status =  createTemplate(template, mainView, secondaryView, buttonView);
            } else {
                template = SdlViewManager.LayoutTemplate.GRAPHIC_WITH_TEXT_AND_SOFTBUTTONS;
                status =  createTemplate(template, mainView, secondaryView, buttonView);
            }
        }
        return status;
    }

    public TemplateStatus setViews(SdlView mainView, SdlView secondaryView){
        TemplateStatus status = TemplateStatus.INVALID_TEMPLATE_NOT_SUPPORTED;
        if(secondaryView == null){
            status = setViews(mainView);
        } else if(mainView == null){
            status = TemplateStatus.INVALID_NO_MAIN_VIEW;
        } else {
            SdlViewManager.LayoutTemplate template;
            if (mainView instanceof SdlButtonView) {
                if(secondaryView instanceof SdlGraphicView){
                    if(((SdlButtonView) mainView).isTiles() &&
                            mAvailableTemplateSet.contains(SdlViewManager.LayoutTemplate.TILES_WITH_GRAPHIC)){
                        template = SdlViewManager.LayoutTemplate.TILES_WITH_GRAPHIC;
                    } else {
                        template = SdlViewManager.LayoutTemplate.TEXTBUTTONS_WITH_GRAPHIC;
                    }
                    status = createTemplate(template, mainView, secondaryView, null);
                } else if(secondaryView instanceof TextView){
                    status = TemplateStatus.INVALID_TEMPLATE_NOT_SUPPORTED;
                }
            } else if (mainView instanceof SdlTextView){
                if(secondaryView instanceof SdlButtonView){
                    status = TemplateStatus.INVALID_TEMPLATE_NOT_SUPPORTED;
                } else if(secondaryView instanceof SdlGraphicView){
                    template = SdlViewManager.LayoutTemplate.TEXT_WITH_GRAPHIC;
                    status = createTemplate(template, mainView, secondaryView, null);
                }
            } else if(mainView instanceof SdlGraphicView){
                if(secondaryView instanceof SdlButtonView){
                    if(((SdlButtonView) secondaryView).isTiles() &&
                            mAvailableTemplateSet.contains(SdlViewManager.LayoutTemplate.GRAPHIC_WITH_TILES)){
                        template = SdlViewManager.LayoutTemplate.GRAPHIC_WITH_TILES;
                    } else {
                        template = SdlViewManager.LayoutTemplate.GRAPHIC_WITH_TEXTBUTTONS;
                    }
                    status = createTemplate(template, mainView, secondaryView, null);
                } else if(secondaryView instanceof TextView){
                    template = SdlViewManager.LayoutTemplate.GRAPHIC_WITH_TEXT;
                    status = createTemplate(template, mainView, secondaryView, null);
                }
            }
        }
        return status;
    }

    public TemplateStatus setViews(SdlView mainView, SdlButtonView buttonView){
        TemplateStatus status = TemplateStatus.INVALID_TEMPLATE_NOT_SUPPORTED;
        if(buttonView == null){
            status = setViews(mainView);
        } else if(mainView == null){
            status = TemplateStatus.INVALID_NO_MAIN_VIEW;
        } else {
            SdlViewManager.LayoutTemplate template;
            if (mainView instanceof SdlButtonView) {
                status = TemplateStatus.INVALID_TOO_MANY_BUTTON_VIEWS;
            } else if (mainView instanceof SdlTextView){
                template = SdlViewManager.LayoutTemplate.DEFAULT;
                status = createTemplate(template, mainView, null, buttonView);
            } else if(mainView instanceof SdlGraphicView){
                template = SdlViewManager.LayoutTemplate.LARGE_GRAPHIC_WITH_SOFTBUTTONS;
                status = createTemplate(template, mainView, null, buttonView);
            }
        }
        return status;
    }

    public TemplateStatus setViews(SdlView mainView){
        SdlViewManager.LayoutTemplate template = null;
        if(mainView instanceof SdlTextView){
            template = SdlViewManager.LayoutTemplate.TEXT_WITH_GRAPHIC;
        } else if(mainView instanceof SdlButtonView){
            if(((SdlButtonView) mainView).isTiles() &&
                    mAvailableTemplateSet.contains(SdlViewManager.LayoutTemplate.TILES_ONLY)){
                template = SdlViewManager.LayoutTemplate.TILES_ONLY;
            } else {
                template = SdlViewManager.LayoutTemplate.TEXTBUTTONS_ONLY;
            }
        } else if(mainView instanceof SdlGraphicView){
            template = SdlViewManager.LayoutTemplate.LARGE_GRAPHIC_ONLY;
        }
        return createTemplate(template, mainView, null, null);
    }

    private TemplateStatus createTemplate(SdlViewManager.LayoutTemplate template, SdlView mainView,
                                          SdlView secondaryView, SdlButtonView buttonView) {
        if(mAvailableTemplateSet.contains(template)) {
            mLeftView = mainView;
            mRightView = secondaryView;
            mSdlButtonView = buttonView;
            mTemplate = template;
        } else {
            return TemplateStatus.INVALID_TEMPLATE_NOT_SUPPORTED;
        }
        return TemplateStatus.VALID;
    }

    @Override
    public boolean decorate(Show show) {
        mLeftView.decorate(show);

        return false;
    }

    @Override
    public void redraw() {

    }

    @Override
    public void clear() {

    }

}
