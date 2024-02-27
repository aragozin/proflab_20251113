/*
 * Copyright (c) 2008, 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the Classpath exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */


import static org.openjdk.btrace.core.BTraceUtils.println;

import org.openjdk.btrace.core.BTraceUtils.Sys;
import org.openjdk.btrace.core.BTraceUtils.Time;
import org.openjdk.btrace.core.annotations.BTrace;
import org.openjdk.btrace.core.annotations.OnMethod;
import org.openjdk.btrace.core.annotations.OnTimer;
import org.openjdk.btrace.core.annotations.ProbeClassName;
import org.openjdk.btrace.core.annotations.ProbeMethodName;

/**
 * Demonstrates multiple timer probes with different periods to fire.
 */
@BTrace
public class Timers {

    // when starting print the target VM version and start time
    static {
        println("vm version " + Sys.VM.vmVersion());
        println("vm starttime " + Sys.VM.vmStartTime());
    }

//    @OnMethod(
//            clazz="info.ragozin.perflab.hazelagg.kryo.KryoSerializer",
//            method="write"
//            )
//        public static void entryJobS(@ProbeMethodName String probeMethod) {
//            println(probeMethod);
//        }
    @OnMethod(
            clazz="java.io.FileInputStream",
            method="read"
            )
        public static void aaa(@ProbeClassName String className, @ProbeMethodName String probeMethod) {
            println(className + "::" + probeMethod);
        }

    @OnMethod(
            clazz="java.util.HashMap",
            method="put"
            )
        public static void aa1(@ProbeClassName String className, @ProbeMethodName String probeMethod) {
            println(className + "::" + probeMethod);
        }

    @OnTimer(1000)
    public static void f() {
        println("1000 msec: " + Sys.VM.vmUptime());
    }

    @OnTimer(3000)
    public static void f1() {
        println("3000 msec: " + Time.millis());
    }

}
