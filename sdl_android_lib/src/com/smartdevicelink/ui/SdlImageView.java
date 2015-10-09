package com.smartdevicelink.ui;

import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.ImageType;

//We inherit most of the methods we need
public class SdlImageView extends ImageField{
	int id = -1;
	Image image;

	public SdlImageView(String imageId, ImageFieldName imageFieldName){
		this.setName(imageFieldName);
		image = new Image();
		image.setValue(imageId);
		image.setImageType(ImageType.DYNAMIC); //Assuming this view will only be dynamic for now.
		this.id = SdlViewHelper.generateViewId();
	}
	
	public void setImage(Image image){
		this.image = image;
	}
	
	/**
	 * Set the file name id that this image view should reference
	 * @param id
	 */
	public void setImageId(String id){
		if(image == null){
			this.image = new Image();
			image.setImageType(ImageType.DYNAMIC); //Assuming this view will only be dynamic for now.
		}
		this.image.setValue(id);
	}
	
}
