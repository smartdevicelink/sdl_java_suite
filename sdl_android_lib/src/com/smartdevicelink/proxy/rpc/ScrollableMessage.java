package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.util.DebugTool;

/**
 * Creates a full screen overlay containing a large block of formatted text that
 * can be scrolled with up to 8 SoftButtons defined
 * <p>
 * Function Group: ScrollableMessage
 * <p>
 * <b>HMILevel needs to be FULL</b>
 * <p>
 */
public class ScrollableMessage extends RPCRequest {

	/**
	 * Constructs a new ScrollableMessage object
	 */
    public ScrollableMessage() {
        super("ScrollableMessage");
    }

	/**
	 * Constructs a new ScrollableMessage object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ScrollableMessage(Hashtable hash) {
        super(hash);
    }

	/**
	 * Sets a Body of text that can include newlines and tabs
	 * 
	 * @param scrollableMessageBody
	 *            a String value representing the Body of text that can include
	 *            newlines and tabs
	 *            <p>
	 *            <b>Notes: </b>Maxlength=500
	 */
    public void setScrollableMessageBody(String scrollableMessageBody) {
        if (scrollableMessageBody != null) {
            parameters.put(Names.scrollableMessageBody, scrollableMessageBody);
        } else {
        	parameters.remove(Names.scrollableMessageBody);
        }
    }

	/**
	 * Gets a Body of text that can include newlines and tabs
	 * 
	 * @return String -a String value
	 */
    public String getScrollableMessageBody() {
        return (String) parameters.get(Names.scrollableMessageBody);
    }

	/**
	 * Sets an App defined timeout. Indicates how long of a timeout from the
	 * last action
	 * 
	 * @param timeout
	 *            an Integer value representing an App defined timeout
	 *            <p>
	 *            <b>Notes</b>:Minval=0; Maxval=65535;Default=30000
	 */
    public void setTimeout(Integer timeout) {
        if (timeout != null) {
            parameters.put(Names.timeout, timeout);
        } else {
        	parameters.remove(Names.timeout);
        }
    }

	/**
	 * Gets an App defined timeout
	 * 
	 * @return Integer -an Integer value representing an App defined timeout
	 */
    public Integer getTimeout() {
        return (Integer) parameters.get(Names.timeout);
    }

	/**
	 * Sets App defined SoftButtons.If omitted on supported displays, only the
	 * system defined "Close" SoftButton will be displayed
	 * 
	 * @param softButtons
	 *            a Vector<SoftButton> value representing App defined
	 *            SoftButtons
	 *            <p>
	 *            <b>Notes: </b>Minsize=0, Maxsize=8
	 */
    public void setSoftButtons(Vector<SoftButton> softButtons) {
        if (softButtons != null) {
            parameters.put(Names.softButtons, softButtons);
        } else {
        	parameters.remove(Names.softButtons);
        }
    }

	/**
	 * Gets App defined soft button
	 * @return Vector -Vector<SoftButton> value
	 */
    public Vector<SoftButton> getSoftButtons() {
        if (parameters.get(Names.softButtons) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)parameters.get(Names.softButtons);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof SoftButton) {
	                return (Vector<SoftButton>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<SoftButton> newList = new Vector<SoftButton>();
	                for (Object hashObj : list) {
	                    newList.add(new SoftButton((Hashtable) hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
}
