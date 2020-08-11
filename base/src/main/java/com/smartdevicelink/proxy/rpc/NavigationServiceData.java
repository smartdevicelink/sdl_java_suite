/*
 * Copyright (c) 2019 Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
import java.util.List;

/**
 * This data is related to what a navigation service would provide.
 */
public class NavigationServiceData extends RPCStruct {

	public static final String KEY_TIMESTAMP = "timeStamp";
	public static final String KEY_ORIGIN= "origin";
	public static final String KEY_DESTINATION = "destination";
	public static final String KEY_DESTINATION_ETA = "destinationETA";
	public static final String KEY_INSTRUCTIONS = "instructions";
	public static final String KEY_NEXT_INSTRUCTION_ETA = "nextInstructionETA";
	public static final String KEY_NEXT_INSTRUCTION_DISTANCE = "nextInstructionDistance";
	public static final String KEY_NEXT_INSTRUCTION_DISTANCE_SCALE= "nextInstructionDistanceScale";
	public static final String KEY_PROMPT = "prompt";

	// Constructors

	public NavigationServiceData() { }

	public NavigationServiceData(Hashtable<String, Object> hash) {
		super(hash);
	}

	public NavigationServiceData(@NonNull DateTime timeStamp){
		this();
		setTimeStamp(timeStamp);
	}

	// Setters and Getters

	/**
	 * This is the timeStamp of when the data was generated. This is to ensure any time or distance
	 * given in the data can accurately be adjusted if necessary.
	 * @param timeStamp -
	 */
	public void setTimeStamp(@NonNull DateTime timeStamp){
		setValue(KEY_TIMESTAMP, timeStamp);
	}

	/**
	 * This is the timeStamp of when the data was generated. This is to ensure any time or distance
	 * given in the data can accurately be adjusted if necessary.
	 * @return timeStamp
	 */
	public DateTime getTimeStamp(){
		return (DateTime) getObject(DateTime.class, KEY_TIMESTAMP);
	}

	/**
	 * @param origin -
	 */
	public void setOrigin(LocationDetails origin){
		setValue(KEY_ORIGIN, origin);
	}

	/**
	 * @return origin
	 */
	public LocationDetails getOrigin(){
		return (LocationDetails) getObject(LocationDetails.class, KEY_ORIGIN);
	}

	/**
	 * @param destination -
	 */
	public void setDestination(LocationDetails destination){
		setValue(KEY_DESTINATION, destination);
	}

	/**
	 * @return destination
	 */
	public LocationDetails getDestination(){
		return (LocationDetails) getObject(LocationDetails.class, KEY_DESTINATION);
	}

	/**
	 * @param destinationETA -
	 */
	public void setDestinationETA(DateTime destinationETA){
		setValue(KEY_DESTINATION_ETA, destinationETA);
	}

	/**
	 * @return destinationETA
	 */
	public DateTime getDestinationETA(){
		return (DateTime) getObject(DateTime.class, KEY_DESTINATION_ETA);
	}

	/**
	 * This array should be ordered with all remaining instructions. The start of this array should
	 * always contain the next instruction.
	 * @param instructions -
	 */
	public void setInstructions(List<NavigationInstruction> instructions){
		setValue(KEY_INSTRUCTIONS, instructions);
	}

	/**
	 * This array should be ordered with all remaining instructions. The start of this array should
	 * always contain the next instruction.
	 * @return instructions
	 */
	@SuppressWarnings("unchecked")
	public List<NavigationInstruction> getInstructions(){
		return (List<NavigationInstruction>) getObject(NavigationInstruction.class,KEY_INSTRUCTIONS);
	}

	/**
	 * @param nextInstructionETA -
	 */
	public void setNextInstructionETA(DateTime nextInstructionETA){
		setValue(KEY_NEXT_INSTRUCTION_ETA, nextInstructionETA);
	}

	/**
	 * @return nextInstructionETA
	 */
	public DateTime getNextInstructionETA(){
		return (DateTime) getObject(DateTime.class, KEY_NEXT_INSTRUCTION_ETA);
	}

	/**
	 * The distance to this instruction from current location. This should only be updated every
	 * .1 unit of distance. For more accuracy the consumer can use the GPS location of itself and
	 * the next instruction.
	 * @param nextInstructionDistance -
	 */
	public void setNextInstructionDistance(Float nextInstructionDistance){
		setValue(KEY_NEXT_INSTRUCTION_DISTANCE, nextInstructionDistance);
	}

	/**
	 * The distance to this instruction from current location. This should only be updated every
	 * .1 unit of distance. For more accuracy the consumer can use the GPS location of itself and
	 * the next instruction.
	 * @return nextInstructionDistance
	 */
	public Float getNextInstructionDistance(){
		return getFloat(KEY_NEXT_INSTRUCTION_DISTANCE);
	}

	/**
	 * Distance till next maneuver (starting from) from previous maneuver.
	 * @param nextInstructionDistanceScale -
	 */
	public void setNextInstructionDistanceScale(Float nextInstructionDistanceScale){
		setValue(KEY_NEXT_INSTRUCTION_DISTANCE_SCALE, nextInstructionDistanceScale);
	}

	/**
	 * Distance till next maneuver (starting from) from previous maneuver.
	 * @return nextInstructionDistanceScale
	 */
	public Float getNextInstructionDistanceScale(){
		return getFloat(KEY_NEXT_INSTRUCTION_DISTANCE_SCALE);
	}

	/**
	 * This is a prompt message that should be conveyed to the user through either display or voice
	 * (TTS). This param will change often as it should represent the following: approaching
	 * instruction, post instruction, alerts that affect the current navigation session, etc.
	 * @param prompt -
	 */
	public void setPrompt(String prompt){
		setValue(KEY_PROMPT, prompt);
	}

	/**
	 * This is a prompt message that should be conveyed to the user through either display or voice
	 * (TTS). This param will change often as it should represent the following: approaching
	 * instruction, post instruction, alerts that affect the current navigation session, etc.
	 * @return prompt
	 */
	public String getPrompt(){
		return getString(KEY_PROMPT);
	}

}
