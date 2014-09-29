package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.util.DebugTool;

public class OnSdlChoiceChosen extends RPCNotification {
	
	
	public class SdlSubMenu {
		private Integer _menuID = null;
		private Integer _position = null;
		private String _menuName = null;
		
		// Constructor
		SdlSubMenu(Integer menuID, Integer position, String menuName) {
			_menuID = menuID;
			_position = position;
			_menuName = menuName;
		}
		
		// Restrict no-arg constructor
		private SdlSubMenu() {}
		
		// Public Getters
		public Integer getMenuID() {
			return _menuID;
		}
		
		public String getMenuName() {
			return _menuName;
		}
		
		public String toString() {
			return _menuName;
		}
	}
	
	public class SdlCommand {
		private Integer _commandID = null;
		private SdlSubMenu _parentSubMenu = null;
		private Integer _position = null;
		private String _menuName = null;
		private Vector<String> _vrCommands = null;
		
		// Constructor
		SdlCommand(Integer commandID, SdlSubMenu parentSubMenu, Integer position, String menuName, Vector<String> vrCommands) {
			_commandID = commandID;
			_parentSubMenu = parentSubMenu;
			_position = position;
			_menuName = menuName;
			_vrCommands = vrCommands;
		}
		
		// Restrict no-arg constructor
		private SdlCommand() {}
		
		// Public Getters
		public Integer getCommandID() {
			return _commandID;
		}
		
		public SdlSubMenu getParentSubMenu() {
			return _parentSubMenu;
		}
		
		public String getMenuName() {
			return _menuName;
		}
		
		public Vector<String> getVrCommands() {
			return _vrCommands;
		}
		
		public String toString() {
			return _menuName;
		}
	}
	
	public class SdlChoice {
		
		private Choice _choice = null;
		
		// Constructor
		SdlChoice(Choice choice) {
			_choice = choice;
		}
		
		public Choice getChoice() {
			return _choice;
		}
		
		public Integer getChoiceID() {
			return _choice.getChoiceID();
		}
		
		public String getMenuName() {
			return _choice.getMenuName();
		}
		
		public Vector<String> getVrCommands() {
			return _choice.getVrCommands();
		}
		
		public String toString() {
			return _choice.getMenuName();
		}
	}
	
	public class SdlChoiceSet {
		private Integer _choiceSetID = null;
		private Vector<SdlChoice> _choiceSet = null;
		
		// Constructor
		SdlChoiceSet(Integer choiceSetID, Vector<SdlChoice> choiceSet) {
			_choiceSetID = choiceSetID;
			_choiceSet = choiceSet;
		}
		
		public Integer getChoiceSetID() {
			return _choiceSetID;
		}
		
		public Vector<SdlChoice> getChoiceSet() {
			return _choiceSet;
		}
	}
	
	
	

	public OnSdlChoiceChosen() {
		super(Names.OnSdlChoiceChosen);
	}
	public OnSdlChoiceChosen(Hashtable hash){
		super(hash);
	}
    public SdlChoice getSdlChoice() {
    	return (SdlChoice) parameters.get(Names.sdlChoice);
    }
    public void setSdlChoice(SdlChoice sdlChoice) {
    	if (sdlChoice != null) {
    		parameters.put(Names.sdlChoice, sdlChoice);
    	}
    }
    public TriggerSource getTriggerSource() {
        Object obj = parameters.get(Names.triggerSource);
        if (obj instanceof TriggerSource) {
            return (TriggerSource) obj;
        } else if (obj instanceof String) {
            TriggerSource theCode = null;
            try {
                theCode = TriggerSource.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.triggerSource, e);
            }
            return theCode;
        }
        return null;
    }
    public void setTriggerSource( TriggerSource triggerSource ) {
        if (triggerSource != null) {
            parameters.put(Names.triggerSource, triggerSource );
        }
    }
}
