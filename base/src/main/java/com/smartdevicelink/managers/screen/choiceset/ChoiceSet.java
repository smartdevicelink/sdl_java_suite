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

package com.smartdevicelink.managers.screen.choiceset;

import com.smartdevicelink.proxy.rpc.TTSChunk;

import java.util.List;

public class ChoiceSet {
    private String title;
    private List<TTSChunk> initialPrompt, timeoutPrompt, helpPrompt;
    private ChoiceSetLayout layout;
    private int timeout;
    private List<ChoiceCell> choices;
    private ChoiceSetSelectionListener choiceSetSelectionListener;

    public ChoiceSet(String title, ChoiceSetSelectionListener listener, ChoiceSetLayout layout, List<TTSChunk> initialPrompt, List<ChoiceCell> choices){
        setTitle(title);
        setChoiceSetSelectionListener(listener);
        setLayout(layout);
        setInitialPrompt(initialPrompt);
        setChoices(choices);
    }

    public ChoiceSet(String title, ChoiceSetSelectionListener listener, ChoiceSetLayout layout, List<TTSChunk> initialPrompt, int timeout, List<ChoiceCell> choices){
        setTitle(title);
        setChoiceSetSelectionListener(listener);
        setLayout(layout);
        setInitialPrompt(initialPrompt);
        setTimeout(timeout);
        setChoices(choices);
    }

    public ChoiceSet(String title, ChoiceSetSelectionListener listener, List<TTSChunk> initialPrompt, List<TTSChunk> timeoutPrompt, List<TTSChunk> helpPrompt, List<ChoiceCell> choices){
        setTitle(title);
        setChoiceSetSelectionListener(listener);
        setInitialPrompt(initialPrompt);
        setTimeoutPrompt(timeoutPrompt);
        setHelpPrompt(helpPrompt);
        setChoices(choices);
    }

    public ChoiceSet(String title, ChoiceSetSelectionListener listener, ChoiceSetLayout layout, List<TTSChunk> initialPrompt, List<TTSChunk> timeoutPrompt, List<TTSChunk> helpPrompt, List<ChoiceCell> choices){
        setTitle(title);
        setChoiceSetSelectionListener(listener);
        setLayout(layout);
        setInitialPrompt(initialPrompt);
        setTimeoutPrompt(timeoutPrompt);
        setHelpPrompt(helpPrompt);
        setChoices(choices);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TTSChunk> getInitialPrompt() {
        return initialPrompt;
    }

    public void setInitialPrompt(List<TTSChunk> initialPrompt) {
        this.initialPrompt = initialPrompt;
    }

    public List<TTSChunk> getTimeoutPrompt() {
        return timeoutPrompt;
    }

    public void setTimeoutPrompt(List<TTSChunk> timeoutPrompt) {
        this.timeoutPrompt = timeoutPrompt;
    }

    public List<TTSChunk> getHelpPrompt() {
        return helpPrompt;
    }

    public void setHelpPrompt(List<TTSChunk> helpPrompt) {
        this.helpPrompt = helpPrompt;
    }

    public ChoiceSetLayout getLayout() {
        return layout;
    }

    public void setLayout(ChoiceSetLayout layout) {
        this.layout = layout;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public List<ChoiceCell> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceCell> choices) {
        this.choices = choices;
    }

    public ChoiceSetSelectionListener getChoiceSetSelectionListener() {
        return choiceSetSelectionListener;
    }

    public void setChoiceSetSelectionListener(ChoiceSetSelectionListener choiceSetSelectionListener) {
        this.choiceSetSelectionListener = choiceSetSelectionListener;
    }
}
