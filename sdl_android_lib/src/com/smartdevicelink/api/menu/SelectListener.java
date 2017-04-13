package com.smartdevicelink.api.menu;

import com.smartdevicelink.proxy.rpc.enums.TriggerSource;

public interface SelectListener {

    void onSelect(TriggerSource triggerSource);

}
