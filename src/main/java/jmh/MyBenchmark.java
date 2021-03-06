/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
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
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Timeout(time = 5)
@Warmup(time = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MyBenchmark {


    @Param("1")
    private double valx;
    @Param("2")
    private double valy;

    private SimpleRecord recordx, recordy;

    private Multiplicable m0, m1;
    private PlainOldDoubleWrapper w0, w1;

    @Benchmark
    public void plainDoubleMultiplication(Blackhole blackhole) {
        blackhole.consume(valx * valy);
    }

    @Setup
    public void setup() {
        recordx = new SimpleRecord(valx);
        recordy = new SimpleRecord(valy);
        m0 = recordx;
        m1 = recordy;
        w0 = new PlainOldDoubleWrapper(valx);
        w1 = new PlainOldDoubleWrapper(valy);
    }

    @Benchmark
    public void recordMultiplication(Blackhole blackhole) {
        blackhole.consume(recordx.multiplyLocal(recordy));
    }

    @Benchmark
    public void multiplyRecordInterface(Blackhole blackhole) {
        blackhole.consume(m0.multiply(m1));
    }

    @Benchmark
    public void multiplyPOJO(Blackhole blackhole) {
        blackhole.consume(w0.multiply(w1));
    }

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .forks(1)
                .jvm("/home/takeshi/.jdks/azul-15.0.1/bin/java")
                .jvmArgs("--enable-preview")
//                .
//                .addProfiler("async")
                .build();

        new Runner(opt).run();
    }
}
