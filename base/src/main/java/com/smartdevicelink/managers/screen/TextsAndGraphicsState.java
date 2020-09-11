package com.smartdevicelink.managers.screen;

import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.rpc.TemplateConfiguration;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;

class TextsAndGraphicsState {
    private String textField1, textField2, textField3, textField4, mediaTrackTextField, title;
    private MetadataType textField1Type, textField2Type, textField3Type, textField4Type;
    private TextAlignment textAlignment;
    private SdlArtwork primaryGraphic, secondaryGraphic;
    private TemplateConfiguration templateConfiguration;

    TextsAndGraphicsState() {

    }

    TextsAndGraphicsState(String textField1, String textField2, String textField3, String textField4, String mediaTrackTextField,
                          String title, SdlArtwork primaryGraphic, SdlArtwork secondaryGraphic, TextAlignment textAlignment,
                          MetadataType textField1Type, MetadataType textField2Type, MetadataType textField3Type, MetadataType textField4Type,
                          TemplateConfiguration templateConfiguration) {
        this.textField1 = textField1;
        this.textField2 = textField2;
        this.textField3 = textField3;
        this.textField4 = textField4;
        this.mediaTrackTextField = mediaTrackTextField;
        this.title = title;
        this.primaryGraphic = primaryGraphic;
        this.secondaryGraphic = secondaryGraphic;
        this.textAlignment = textAlignment;
        this.textField1Type = textField1Type;
        this.textField2Type = textField2Type;
        this.textField3Type = textField3Type;
        this.textField4Type = textField4Type;
        this.templateConfiguration = templateConfiguration;
    }

    String getTextField1() {
        return textField1;
    }

    void setTextField1(String textField1) {
        this.textField1 = textField1;
    }

    String getTextField2() {
        return textField2;
    }

    void setTextField2(String textField2) {
        this.textField2 = textField2;
    }

    String getTextField3() {
        return textField3;
    }

    void setTextField3(String textField3) {
        this.textField3 = textField3;
    }

    String getTextField4() {
        return textField4;
    }

    void setTextField4(String textField4) {
        this.textField4 = textField4;
    }

    String getMediaTrackTextField() {
        return mediaTrackTextField;
    }

    void setMediaTrackTextField(String mediaTrackTextField) {
        this.mediaTrackTextField = mediaTrackTextField;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    MetadataType getTextField1Type() {
        return textField1Type;
    }

    void setTextField1Type(MetadataType textField1Type) {
        this.textField1Type = textField1Type;
    }

    MetadataType getTextField2Type() {
        return textField2Type;
    }

    void setTextField2Type(MetadataType textField2Type) {
        this.textField2Type = textField2Type;
    }

    MetadataType getTextField3Type() {
        return textField3Type;
    }

    void setTextField3Type(MetadataType textField3Type) {
        this.textField3Type = textField3Type;
    }

    MetadataType getTextField4Type() {
        return textField4Type;
    }

    void setTextField4Type(MetadataType textField4Type) {
        this.textField4Type = textField4Type;
    }

    TextAlignment getTextAlignment() {
        return textAlignment;
    }

    void setTextAlignment(TextAlignment textAlignment) {
        this.textAlignment = textAlignment;
    }

    SdlArtwork getPrimaryGraphic() {
        return primaryGraphic;
    }

    void setPrimaryGraphic(SdlArtwork primaryGraphic) {
        this.primaryGraphic = primaryGraphic;
    }

    SdlArtwork getSecondaryGraphic() {
        return secondaryGraphic;
    }

    void setSecondaryGraphic(SdlArtwork secondaryGraphic) {
        this.secondaryGraphic = secondaryGraphic;
    }

    TemplateConfiguration getTemplateConfiguration() {
        return templateConfiguration;
    }

    void setTemplateConfiguration(TemplateConfiguration templateConfiguration) {
        this.templateConfiguration = templateConfiguration;
    }
}
