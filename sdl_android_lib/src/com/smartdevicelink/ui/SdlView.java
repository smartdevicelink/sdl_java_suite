package com.smartdevicelink.ui;

import java.util.HashMap;

import android.util.Log;
import android.util.SparseArray;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.ui.SdlButton.SdlButtonListner;
import com.smartdevicelink.util.CorrelationIdGenerator;

public class SdlView {
	
	SparseArray<SdlButton> buttons;
	SdlMenu menu;
	HashMap<ButtonName,SdlButton> subscribedButtons;
	TextAlignment defaultTextAlignment;
	HashMap<TextFieldName, SdlTextView> textViews;
	HashMap<ImageFieldName, SdlImageView> imageViews;
	int id = -1;
	IViewManager iViewManager = null;
	
	
	public SdlView(){
		id = SdlViewHelper.generateViewId();
		init();
	}
	
	/**
	 * 
	 * @param id This id MUST be generated from the SldViewHelper.generateId() method.
	 */
	public SdlView(int id){
		this.id =id;
		init();
	}
	
	private void init(){
		defaultTextAlignment = TextAlignment.CENTERED;
		//TODO actually build this out according to the type of view we have
		textViews = new HashMap<TextFieldName, SdlTextView>();
		textViews.put(TextFieldName.mainField1, new SdlTextView(TextFieldName.mainField1,null));
		textViews.put(TextFieldName.mainField2, new SdlTextView(TextFieldName.mainField2,null));
		textViews.put(TextFieldName.mainField3, new SdlTextView(TextFieldName.mainField3,null));
		textViews.put(TextFieldName.mainField4, new SdlTextView(TextFieldName.mainField4,null));
		textViews.put(TextFieldName.mediaTrack, new SdlTextView(TextFieldName.mediaTrack,null));

		
		imageViews = new HashMap<ImageFieldName, SdlImageView>();
		imageViews.put(ImageFieldName.graphic, new SdlImageView(null, ImageFieldName.graphic));
		//imageViews.put(ImageFieldName.secondarygraphic, new SdlImageView(null, ImageFieldName.secondarygraphic)); //secondarygraphic is missing from the ImageFieldName enum
	}
	
	public int getId(){
		return this.id;
	}
	
	/**
	 * This will return the text field associated with the text field name on the screen.
	 * <p> This method may change in the future to mimic findViewById instead.
	 * @param textFieldName
	 * @return
	 */
	public SdlTextView getTextView(TextFieldName textFieldName){
		return textViews.get(textFieldName);
	}
	/**
	 * This will return the image field associated with the image field name on the screen.
	 * <p> This method may change in the future to mimic findViewById instead.
	 * @param imageFieldName
	 * @return
	 */
	public SdlImageView getImaveView(ImageFieldName imageFieldName){
		return imageViews.get(imageFieldName);
	}
	
	public void setDefaultTextAlignment(TextAlignment alignment){
		this.defaultTextAlignment = alignment;
	}
	
	protected void setIViewManager(IViewManager iFace){
		iViewManager = iFace;
	}
	
	public void addButton(SdlButton button){//For now we are assuming these are custom buttons
		//TODO Check for ButtonName
		buttons.put(button.id, button);
	}
	
	public SparseArray<SdlButton> getButtons(){
		return this.buttons;
	}
	
	public SdlButton getButtonForId(int id){
		return this.buttons.get(id);
	}
	
	public SdlButton getSubscribedButton(ButtonName name){
		return this.subscribedButtons.get(name);
	}
	
	public HashMap<ButtonName,SdlButton> getSubscribedButtons(){
		return this.subscribedButtons;
	}
	
	public void subscribeButton(ButtonName name, SdlButtonListner listener){
		
		if(subscribedButtons == null){
			subscribedButtons = new HashMap<ButtonName,SdlButton>();
		}
		
		SdlButton button = new SdlButton();
		button.setSdlButtonListerner(listener);
		subscribedButtons.put(name, button);
	}
	
	/**
	 * This sets a custom menu for this view. When this view is set at the current view, the SDL view manager will recognize
	 * there is a custom menu attached to this view object and take the necessary steps to populate the hardware with this custom menu.
	 * The menu object must be fully formed before getting to this point.
	 * @param menu The custom menu for this view. The menu object must be fully formed before getting to this point.
	 */
	public void setMenu(SdlMenu menu ){
		this.menu = menu;
	}
	
	/**
	 * Triggers a redraw of the screen on the head unit.
	 * <p>** <b>NOTE</b> ** Will not update softbuttons at this time. Softbuttons will only be updated during a "set current view" from the ViewManager
	 */
	public void invalidate(){
		if(iViewManager!=null){
			//First delete what was there
			Show show = new Show();
			show.setCorrelationID(CorrelationIdGenerator.generateId());
			show.setAlignment(defaultTextAlignment); //Can this be per text field?
			
			//TextViews
			show.setMainField1(textViews.get(TextFieldName.mainField1).text);
			show.setMainField2(textViews.get(TextFieldName.mainField2).text);
			show.setMainField3(textViews.get(TextFieldName.mainField3).text);
			show.setMainField4(textViews.get(TextFieldName.mainField4).text);
			show.setMediaTrack(textViews.get(TextFieldName.mediaTrack).text);
			show.setMediaClock(null);
			show.setCustomPresets(null);
			show.setSoftButtons(null);
			show.setStatusBar(null);
			
			//Image Views
			SdlImageView imageView = imageViews.get(ImageFieldName.graphic);
			if(imageView!=null && imageView.image!=null && imageView.image.getValue()!=null){
				show.setGraphic(imageViews.get(ImageFieldName.graphic).image);
			}
			//show.setSecondaryGraphic(imageViews.get(ImageFieldName.secondarygraphic).image);
		
			iViewManager.sendRpc(show);
		}
	}
	
	
}
