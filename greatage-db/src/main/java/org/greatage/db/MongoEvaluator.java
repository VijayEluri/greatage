/*
 * Copyright (c) 2008-2014 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.db;

import com.mongodb.MongoClientURI;
import org.greatage.common.ProcessExecutor;
import org.greatage.common.SimpleProcessExecutor;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class MongoEvaluator implements Evaluator {
    private final ProcessExecutor executor;
    private final MongoClientURI uri;

    public MongoEvaluator(final ProcessExecutor executor, final String uri) {
        this.executor = executor;
        this.uri = new MongoClientURI(uri);
    }

    @Override
    public void evaluate(final String script) {
        final int exitCode = executor.create()
                .command("mongo", uri.getDatabase(), "--eval", script)
                .handler(new ProcessExecutor.ContentHandler() {
                    @Override
                    public void start(final List<String> command) {
                    }

                    @Override
                    public void end(final int returnCode) {
                    }

                    @Override
                    public void line(final String line) {
                        System.out.println(line);
                    }

                    @Override
                    public void error(final Throwable e) {
                    }
                }).execute();
    }

    public static void main(final String[] args) {
        final Evaluator evaluator = new MongoEvaluator(new SimpleProcessExecutor(), "mongodb://localhost/test");
        evaluator.evaluate("db.test_users.insert({ _id: 'Test' });");
    }
}
