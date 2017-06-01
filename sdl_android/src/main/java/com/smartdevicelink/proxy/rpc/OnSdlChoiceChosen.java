package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;

import java.util.Hashtable;
import java.util.List;

public class OnSdlChoiceChosen extends RPCNotification {
	public static final String KEY_SDL_CHOICE = "sdlChoice";
	public static final String KEY_TRIGGER_SOURCE = "triggerSource";
	
	public class SdlSubMenu {
		private Integer _menuID = null;
		@SuppressWarnings("unused")
        private Integer _position = null;
		private String _menuName = null;
		
		// Constructor
		SdlSubMenu(Integer menuID, Integer position, String menuName) {
			_menuID = menuID;
			_position = position;
			_menuName = menuName;
		}
		
		// Restrict no-arg constructor
		@SuppressWarnings("unused")
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
		@SuppressWarnings("unused")
        private Integer _position = null;
		private String _menuName = null;
		private List<String> _vrCommands = null;
		
		// Constructor
		SdlCommand(Integer commandID, SdlSubMenu parentSubMenu, Integer position, String menuName, List<String> vrCommands) {
			_commandID = commandID;
			_parentSubMenu = parentSubMenu;
			_position = position;
			_menuName = menuName;
			_vrCommands = vrCommands;
		}
		
		// Restrict no-arg constructor
		@SuppressWarnings("unused")
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
		
		public List<String> getVrCommands() {
			return _vrCommands;
		}
		
		public String toString() {
			return _menuName;
		}
	}
	
	public class SdlChoice {
		
		private Choice _choice = null;
		
		// Constructor
		public SdlChoice(Choice choice) {
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
		
		public List<String> getVrCommands() {
			return _choice.getVrCommands();
		}
		
		public String toString() {
			return _choice.getMenuName();
		}
	}
	
	public class SdlChoiceSet {
		private Integer _choiceSetID = null;
		private List<SdlChoice> _choiceSet = null;
		
		// Constructor
		SdlChoiceSet(Integer choiceSetID, List<SdlChoice> choiceSet) {
			_choiceSetID = choiceSetID;
			_choiceSet = choiceSet;
		}
		
		public Integer getChoiceSetID() {
			return _choiceSetID;
		}
		
		public List<SdlChoice> getChoiceSet() {
			return _choiceSet;
		}
	}
	
	
	

	public OnSdlChoiceChosen() {
		super(FunctionID.ON_SDL_CHOICE_CHOSEN.toString());
	}
	public OnSdlChoiceChosen(Hashtable<String, Object> hash){
		super(hash);
	}
    public SdlChoice getSdlChoice() {
    	return (SdlChoice) getParameter(KEY_SDL_CHOICE);
    }
    public void setSdlChoice(SdlChoice sdlChoice) {
		setParameter(KEY_SDL_CHOICE, sdlChoice);
    }
    public TriggerSource getTriggerSource() {
		return (TriggerSource) getObject(TriggerSource.class, KEY_TRIGGER_SOURCE);
    }
    public void setTriggerSource( TriggerSource triggerSource ) {
		setParameter(KEY_TRIGGER_SOURCE, triggerSource);
    }
}
