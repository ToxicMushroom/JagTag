/*
 * Copyright 2016 John Grosh (jagrosh).
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
import com.jagrosh.jagtag.Method;
import com.jagrosh.jagtag.Parser;
import com.jagrosh.jagtag.ParserBuilder;

/**
 * This example shows some simple method creation without using any of the
 * included libraries. Specifically, this example creates a random decimal
 * number between 0 and 100 using two methods, randint and randdecimal.
 *
 * @author John Grosh (jagrosh)
 */
public class Example2 {

    public static void main(String[] args) {
        Parser parser = new ParserBuilder()
                .addMethod(new Method("randint", (env) -> Integer.toString((int) (Math.random() * 100))))
                .addMethod(new Method("randdecimal", (env) -> Double.toString(Math.random()).substring(1)))
                .build();
        System.out.println(parser.parse("{randint}{randdecimal}"));
    }
}
