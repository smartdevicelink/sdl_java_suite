package com.smartdevicelink.api.view;

import android.support.annotation.NonNull;

import com.smartdevicelink.api.permission.SdlPermission;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.ScrollableMessage;
import com.smartdevicelink.proxy.rpc.SoftButton;

import java.util.List;

/**
 * Created by mschwerz on 5/3/16.
 */
public class SdlInformationDialog extends SdlInteractionBase {

    protected SdlInformationDialog(Builder builder) {
        super(builder);
    }

    @Override
    protected @NonNull SdlPermission getRequiredPermission() {
        return SdlPermission.ScrollableMessage;
    }

    @Override
    protected @NonNull IRPCRequestWithButtons getSettableButtonRPCMessage() {
        final ScrollableMessage newScrollableMessage= new ScrollableMessage();
        newScrollableMessage.setScrollableMessageBody(mTextFields[0]);
        newScrollableMessage.setTimeout(mDuration);
        return new IRPCRequestWithButtons() {
            @Override
            public void setButtons(List<SoftButton> buttons) {
                newScrollableMessage.setSoftButtons(buttons);
            }

            @Override
            public RPCRequest createRequest() {
                return newScrollableMessage;
            }
        };
    }

    public static class Builder extends SdlInteractionBase.Builder<Builder>{

        @Override
        protected int getMaxDuration() {
            return 65535;
        }

        @Override
        protected int getMinDuration() {
            return 0;
        }

        @Override
        protected int getDefaultDuration() {
            return 30000;
        }

        @Override
        public Builder setText(String text) {
            mTextFields[0]= text;
            return grabBuilder();
        }

        @Override
        protected Builder grabBuilder() {
                return this;
            }


        @Override
            public SdlInformationDialog build() throws IllegalCreation {
                return new SdlInformationDialog(this);
            }
        }

}
