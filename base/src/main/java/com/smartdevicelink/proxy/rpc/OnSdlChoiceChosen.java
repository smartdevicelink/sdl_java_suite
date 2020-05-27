/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;

import java.util.Hashtable;
import java.util.List;

@Deprecated
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
    	return (SdlChoice) getParameters(KEY_SDL_CHOICE);
    }
    public void setSdlChoice(SdlChoice sdlChoice) {
		setParameters(KEY_SDL_CHOICE, sdlChoice);
    }
    public TriggerSource getTriggerSource() {
		return (TriggerSource) getObject(TriggerSource.class, KEY_TRIGGER_SOURCE);
    }
    public void setTriggerSource( TriggerSource triggerSource ) {
		setParameters(KEY_TRIGGER_SOURCE, triggerSource);
    }
}
