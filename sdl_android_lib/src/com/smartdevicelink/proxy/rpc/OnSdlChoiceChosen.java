package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcNotification;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;

public class OnSdlChoiceChosen extends RpcNotification {
	public static final String KEY_SDL_CHOICE = "sdlChoice";
	public static final String KEY_TRIGGER_SOURCE = "triggerSource";
	
	public class SdlSubMenu {
		private Integer menuId = null;
		@SuppressWarnings("unused")
        private Integer position = null;
		private String menuName = null;
		
		// Constructor
		SdlSubMenu(Integer menuID, Integer position, String menuName) {
			this.menuId = menuID;
			this.position = position;
			this.menuName = menuName;
		}
		
		// Restrict no-arg constructor
		@SuppressWarnings("unused")
        private SdlSubMenu() {}
		
		// Public Getters
		public Integer getMenuId() {
			return menuId;
		}
		
		public String getMenuName() {
			return menuName;
		}
		
		public String toString() {
			return menuName;
		}
	}
	
	public class SdlCommand {
		private Integer commandId = null;
		private SdlSubMenu parentSubMenu = null;
		@SuppressWarnings("unused")
        private Integer position = null;
		private String menuName = null;
		private List<String> vrCommands = null;
		
		// Constructor
		SdlCommand(Integer commandId, SdlSubMenu parentSubMenu, Integer position, String menuName, List<String> vrCommands) {
			this.commandId = commandId;
			this.parentSubMenu = parentSubMenu;
			this.position = position;
			this.menuName = menuName;
			this.vrCommands = vrCommands;
		}
		
		// Restrict no-arg constructor
		@SuppressWarnings("unused")
        private SdlCommand() {}
		
		// Public Getters
		public Integer getCommandId() {
			return commandId;
		}
		
		public SdlSubMenu getParentSubMenu() {
			return parentSubMenu;
		}
		
		public String getMenuName() {
			return menuName;
		}
		
		public List<String> getVrCommands() {
			return vrCommands;
		}
		
		public String toString() {
			return menuName;
		}
	}
	
	public class SdlChoice {
		
		private Choice choice = null;
		
		// Constructor
		public SdlChoice(Choice choice) {
			this.choice = choice;
		}
		
		public Choice getChoice() {
			return choice;
		}
		
		public Integer getChoiceId() {
			return choice.getChoiceId();
		}
		
		public String getMenuName() {
			return choice.getMenuName();
		}
		
		public List<String> getVrCommands() {
			return choice.getVrCommands();
		}
		
		public String toString() {
			return choice.getMenuName();
		}
	}
	
	public class SdlChoiceSet {
		private Integer choiceSetId = null;
		private List<SdlChoice> choiceSet = null;
		
		// Constructor
		SdlChoiceSet(Integer choiceSetId, List<SdlChoice> choiceSet) {
			this.choiceSetId = choiceSetId;
			this.choiceSet = choiceSet;
		}
		
		public Integer getChoiceSetId() {
			return choiceSetId;
		}
		
		public List<SdlChoice> getChoiceSet() {
			return choiceSet;
		}
	}
	
	
	

	public OnSdlChoiceChosen() {
		super(FunctionId.ON_SDL_CHOICE_CHOSEN.toString());
	}
	public OnSdlChoiceChosen(Hashtable<String, Object> hash){
		super(hash);
	}
    public SdlChoice getSdlChoice() {
    	return (SdlChoice) parameters.get(KEY_SDL_CHOICE);
    }
    public void setSdlChoice(SdlChoice sdlChoice) {
    	if (sdlChoice != null) {
    		parameters.put(KEY_SDL_CHOICE, sdlChoice);
    	} else {
            parameters.remove(KEY_SDL_CHOICE);
        }
    }
    public TriggerSource getTriggerSource() {
        Object obj = parameters.get(KEY_TRIGGER_SOURCE);
        if (obj instanceof TriggerSource) {
            return (TriggerSource) obj;
        } else if (obj instanceof String) {
            return TriggerSource.valueForString((String) obj);
        }
        return null;
    }
    public void setTriggerSource( TriggerSource triggerSource ) {
        if (triggerSource != null) {
            parameters.put(KEY_TRIGGER_SOURCE, triggerSource );
        } else {
        	parameters.remove(KEY_TRIGGER_SOURCE);
        }
    }
}
