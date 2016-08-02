package com.smartdevicelink.api.view;

import android.util.Log;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.Show;

import java.util.EnumSet;

public class SdlTemplateView extends SdlView {

    public enum LayoutTemplate{
        DEFAULT,
        GRAPHIC_WITH_TEXT,
        TEXT_WITH_GRAPHIC,
        TILES_ONLY,
        GRAPHIC_WITH_TILES,
        TILES_WITH_GRAPHIC,
        GRAPHIC_WITH_TEXT_AND_SOFTBUTTONS,
        TEXT_AND_SOFTBUTTONS_WITH_GRAPHIC,
        GRAPHIC_WITH_TEXTBUTTONS,
        DOUBLE_GRAPHIC_WITH_SOFTBUTTONS,
        TEXTBUTTONS_WITH_GRAPHIC,
        TEXTBUTTONS_ONLY,
        LARGE_GRAPHIC_WITH_SOFTBUTTONS,
        LARGE_GRAPHIC_ONLY,
        MEDIA,
        ONSCREEN_PRESETS
    }

    private static final String TAG = SdlTemplateView.class.getSimpleName();

    public enum TemplateStatus{
        VALID,
        INVALID_TOO_MANY_TEXT_VIEWS,
        INVALID_TOO_MANY_BUTTON_VIEWS,
        INVALID_TEMPLATE_NOT_SUPPORTED,
        INVALID_NO_MAIN_VIEW
    }

    private LayoutTemplate mTemplate;

    private SdlView mLeftView;
    private SdlView mRightView;
    private SdlButtonView mSdlButtonView;
    private EnumSet<LayoutTemplate> mAvailableTemplateSet = EnumSet.allOf(LayoutTemplate.class);

    @Override
    public void setDisplayCapabilities(DisplayCapabilities displayCapabilities) {
        if(displayCapabilities.getTemplatesAvailable() != null){
            for(String template: displayCapabilities.getTemplatesAvailable()){
                try {
                    mAvailableTemplateSet.add(LayoutTemplate.valueOf(template));
                } catch (IllegalArgumentException e){
                    Log.w(TAG, "Module reported unsupported Layout: " + template);
                }
            }
        }
        super.setDisplayCapabilities(displayCapabilities);
        if(mLeftView != null){
            mLeftView.setDisplayCapabilities(mDisplayCapabilities);
        }
        if(mRightView != null){
            mRightView.setDisplayCapabilities(mDisplayCapabilities);
        }
        if(mSdlButtonView != null){
            mSdlButtonView.setDisplayCapabilities(mDisplayCapabilities);
        }
    }

    private boolean isLayoutDifferent = true;

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
            LayoutTemplate template;
            if(mainView instanceof SdlButtonView || secondaryView instanceof SdlButtonView){
                status =  TemplateStatus.INVALID_TOO_MANY_BUTTON_VIEWS;
            } else if (mainView.getClass() == secondaryView.getClass()) {
                if(mainView instanceof SdlGraphicView){
                    template = LayoutTemplate.DOUBLE_GRAPHIC_WITH_SOFTBUTTONS;
                    ((SdlGraphicView)secondaryView).setSecondaryGraphic(true);
                    status =  createTemplate(template, mainView, secondaryView, buttonView);
                }
                if (mainView instanceof SdlTextView) {
                    status =  TemplateStatus.INVALID_TOO_MANY_TEXT_VIEWS;
                }
            } else if(mainView instanceof SdlTextView){
                if(buttonView.isTiles()) {
                    template = LayoutTemplate.TEXT_AND_SOFTBUTTONS_WITH_GRAPHIC;
                } else {
                    template = LayoutTemplate.DEFAULT;
                }
                status =  createTemplate(template, mainView, secondaryView, buttonView);
            } else {
                template = LayoutTemplate.GRAPHIC_WITH_TEXT_AND_SOFTBUTTONS;
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
            LayoutTemplate template;
            if (mainView instanceof SdlButtonView) {
                if(secondaryView instanceof SdlGraphicView){
                    if(((SdlButtonView) mainView).isTiles() &&
                            mAvailableTemplateSet.contains(LayoutTemplate.TILES_WITH_GRAPHIC)){
                        template = LayoutTemplate.TILES_WITH_GRAPHIC;
                    } else {
                        template = LayoutTemplate.TEXTBUTTONS_WITH_GRAPHIC;
                    }
                    status = createTemplate(template, mainView, secondaryView, null);
                } else if(secondaryView instanceof SdlTextView){
                    status = TemplateStatus.INVALID_TEMPLATE_NOT_SUPPORTED;
                }
            } else if (mainView instanceof SdlTextView){
                if(secondaryView instanceof SdlButtonView){
                    status = TemplateStatus.INVALID_TEMPLATE_NOT_SUPPORTED;
                } else if(secondaryView instanceof SdlGraphicView){
                    template = LayoutTemplate.TEXT_WITH_GRAPHIC;
                    status = createTemplate(template, mainView, secondaryView, null);
                }
            } else if(mainView instanceof SdlGraphicView){
                if(secondaryView instanceof SdlButtonView){
                    if(((SdlButtonView) secondaryView).isTiles() &&
                            mAvailableTemplateSet.contains(LayoutTemplate.GRAPHIC_WITH_TILES)){
                        template = LayoutTemplate.GRAPHIC_WITH_TILES;
                    } else {
                        template = LayoutTemplate.GRAPHIC_WITH_TEXTBUTTONS;
                    }
                    status = createTemplate(template, mainView, secondaryView, null);
                } else if(secondaryView instanceof SdlTextView){
                    template = LayoutTemplate.GRAPHIC_WITH_TEXT;
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
            LayoutTemplate template;
            if (mainView instanceof SdlButtonView) {
                status = TemplateStatus.INVALID_TOO_MANY_BUTTON_VIEWS;
            } else if (mainView instanceof SdlTextView){
                template = LayoutTemplate.DEFAULT;
                status = createTemplate(template, mainView, new SdlGraphicView(), buttonView);
            } else if(mainView instanceof SdlGraphicView){
                template = LayoutTemplate.LARGE_GRAPHIC_WITH_SOFTBUTTONS;
                status = createTemplate(template, mainView, null, buttonView);
            }
        }
        return status;
    }

    public TemplateStatus setViews(SdlView mainView){
        LayoutTemplate template = null;
        if(mainView instanceof SdlTextView){
            template = LayoutTemplate.TEXT_WITH_GRAPHIC;
        } else if(mainView instanceof SdlButtonView){
            if(((SdlButtonView) mainView).isTiles() &&
                    mAvailableTemplateSet.contains(LayoutTemplate.TILES_ONLY)){
                template = LayoutTemplate.TILES_ONLY;
            } else {
                template = LayoutTemplate.TEXTBUTTONS_ONLY;
            }
        } else if(mainView instanceof SdlGraphicView){
            template = LayoutTemplate.LARGE_GRAPHIC_ONLY;
        }
        return createTemplate(template, mainView, null, null);
    }

    private TemplateStatus createTemplate(LayoutTemplate template, SdlView mainView,
                                          SdlView secondaryView, SdlButtonView buttonView) {
        Log.d(TAG, "Looking for template: " + template.name());
        if(mAvailableTemplateSet.contains(template)) {
            mLeftView = mainView;
            if(mLeftView != null){
                mLeftView.setSdlViewManager(mViewManager);
                mLeftView.setDisplayCapabilities(mDisplayCapabilities);
                if(mSdlContext != null) mLeftView.setSdlContext(mSdlContext);
            }
            mRightView = secondaryView;
            if(mRightView != null){
                mRightView.setSdlViewManager(mViewManager);
                mRightView.setDisplayCapabilities(mDisplayCapabilities);
                if(mSdlContext != null) mRightView.setSdlContext(mSdlContext);
            }
            mSdlButtonView = buttonView;
            if(mSdlButtonView != null){
                mSdlButtonView.setSdlViewManager(mViewManager);
                mSdlButtonView.setDisplayCapabilities(mDisplayCapabilities);
                if(mSdlContext != null) mSdlButtonView.setSdlContext(mSdlContext);
            }
            isLayoutDifferent = template != mTemplate;
            mTemplate = template;
            Log.d(TAG, "My Template is: " + mTemplate.name());
        } else {
            return TemplateStatus.INVALID_TEMPLATE_NOT_SUPPORTED;
        }
        return TemplateStatus.VALID;
    }

    @Override
    public void setSdlViewManager(SdlViewManager sdlViewManager) {
        mViewManager = sdlViewManager;
    }

    @Override
    public void setIsVisible(boolean isVisible) {
        super.setIsVisible(isVisible);
        if(mLeftView != null){
            mLeftView.setIsVisible(isVisible);
        }
        if(mRightView != null){
            mRightView.setIsVisible(isVisible);
        }
        if(mSdlButtonView != null){
            mSdlButtonView.setIsVisible(isVisible);
        }
    }

    @Override
    public void setSdlContext(SdlContext sdlContext) {
        Log.i(TAG, "SdlTemplateView setSdlContext called.");
        if(sdlContext == null) Log.w(TAG, "SdlContext is null!");
        mSdlContext = sdlContext;
        if(mLeftView != null){
            mLeftView.setSdlViewManager(mViewManager);
            mLeftView.setSdlContext(mSdlContext);
        } else {
            Log.w(TAG, "mLeftView is null!");
        }
        if(mRightView != null){
            mRightView.setSdlViewManager(mViewManager);
            mRightView.setSdlContext(mSdlContext);
        } else {
            Log.w(TAG, "mRightView is null!");
        }
        if(mSdlButtonView != null){
            mSdlButtonView.setSdlViewManager(mViewManager);
            mRightView.setSdlContext(mSdlContext);
        } else {
            Log.w(TAG, "mButtonView is null!");
        }
    }

    @Override
    public void decorate(Show show) {
        if(mLeftView != null) {
            mLeftView.decorate(show);
        }
        if(mRightView != null) {
            mRightView.decorate(show);
        }
        if(mSdlButtonView != null) {
            mSdlButtonView.decorate(show);
        }
    }

    @Override
    void uploadRequiredImages() {
        if(mLeftView != null) {
            mLeftView.uploadRequiredImages();
        }
        if(mRightView != null) {
            mRightView.uploadRequiredImages();
        }
        if(mSdlButtonView != null) {
            mSdlButtonView.uploadRequiredImages();
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public String getTemplateName() {
        return isLayoutDifferent ? mTemplate.name(): null;
    }
}
