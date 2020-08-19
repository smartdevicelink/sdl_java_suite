package com.smartdevicelink.managers.screen;



import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;

public class TextsAndGraphicsState {
    private String textField1, textField2, textField3, textField4, mediaTrackTextField, title;
    private MetadataType textField1Type, textField2Type, textField3Type, textField4Type;
    private TextAlignment textAlignment;
    private SdlArtwork primaryGraphic, secondaryGraphic;

    public TextsAndGraphicsState(){}

    public TextsAndGraphicsState(String textField1, String textField2, String textField3, String textField4, String mediaTrackTextField,
                                 String title, SdlArtwork primaryGraphic, SdlArtwork secondaryGraphic, TextAlignment textAlignment,
                                 MetadataType textField1Type, MetadataType textField2Type, MetadataType textField3Type, MetadataType textField4Type) {
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
    }

    public String getTextField1() {
        return textField1;
    }

    public void setTextField1(String textField1) {
        this.textField1 = textField1;
    }

    public String getTextField2() {
        return textField2;
    }

    public void setTextField2(String textField2) {
        this.textField2 = textField2;
    }

    public String getTextField3() {
        return textField3;
    }

    public void setTextField3(String textField3) {
        this.textField3 = textField3;
    }

    public String getTextField4() {
        return textField4;
    }

    public void setTextField4(String textField4) {
        this.textField4 = textField4;
    }

    public String getMediaTrackTextField() {
        return mediaTrackTextField;
    }

    public void setMediaTrackTextField(String mediaTrackTextField) {
        this.mediaTrackTextField = mediaTrackTextField;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MetadataType getTextField1Type() {
        return textField1Type;
    }

    public void setTextField1Type(MetadataType textField1Type) {
        this.textField1Type = textField1Type;
    }

    public MetadataType getTextField2Type() {
        return textField2Type;
    }

    public void setTextField2Type(MetadataType textField2Type) {
        this.textField2Type = textField2Type;
    }

    public MetadataType getTextField3Type() {
        return textField3Type;
    }

    public void setTextField3Type(MetadataType textField3Type) {
        this.textField3Type = textField3Type;
    }

    public MetadataType getTextField4Type() {
        return textField4Type;
    }

    public void setTextField4Type(MetadataType textField4Type) {
        this.textField4Type = textField4Type;
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        this.textAlignment = textAlignment;
    }

    public SdlArtwork getPrimaryGraphic() {
        return primaryGraphic;
    }

    public void setPrimaryGraphic(SdlArtwork primaryGraphic) {
        this.primaryGraphic = primaryGraphic;
    }

    public SdlArtwork getSecondaryGraphic() {
        return secondaryGraphic;
    }

    public void setSecondaryGraphic(SdlArtwork secondaryGraphic) {
        this.secondaryGraphic = secondaryGraphic;
    }
}
