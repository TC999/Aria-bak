/*
 * Copyright (C) 2016 AriaLyy(https://github.com/AriaLyy/Aria)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arialyy.compiler;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Created by lyy on 2017/6/6.
 */
class PrintLog {

  private volatile static PrintLog INSTANCE = null;
  private Messager mMessager;

  public static PrintLog init(Messager msg) {
    if (INSTANCE == null) {
      synchronized (PrintLog.class) {
        INSTANCE = new PrintLog(msg);
      }
    }
    return INSTANCE;
  }

  public static PrintLog getInstance() {
    return INSTANCE;
  }

  private PrintLog() {
  }

  private PrintLog(Messager msg) {
    mMessager = msg;
  }

  public void error(Element e, String msg, Object... args) {
    mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
  }

  public void error(String msg, Object... args) {
    mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
  }

  public void warning(String msg) {
    mMessager.printMessage(Diagnostic.Kind.WARNING, msg);
  }

  public void error(String msg) {
    mMessager.printMessage(Diagnostic.Kind.ERROR, msg);
  }

  public void info(String str) {
    mMessager.printMessage(Diagnostic.Kind.NOTE, str);
  }
}
