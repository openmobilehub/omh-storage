/*
 * Copyright 2023 Open Mobile Hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package general;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class ErrorsManager {

    private static final String LOG_FILE_NAME = "exceptionLogger.txt";
    private static final String DATE_TIME_PATTERN = "MM-dd-yyyy HH:mm:ss";

    public static void errNExpManager(Exception e) {
        try {
            logExceptionToFile(e);
        } catch (IOException i) {
            System.err.println("Unable to log the exception: " + i);
        }
    }

    private static void logExceptionToFile(Exception e) throws IOException {
        String error = e.toString();
        File txtObj = new File(LOG_FILE_NAME);

        if (txtObj.createNewFile()) {
            System.out.println("File created: " + txtObj.getName());
        } else {
            System.out.println("File already exists.");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        String dateAndTime = LocalDateTime.now().format(dateTimeFormatter);

        String logMessage = String.format("\n\nDate and Time: %s\nERROR MESSAGE LOG\n%s", dateAndTime, error);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(LOG_FILE_NAME), StandardOpenOption.APPEND)) {
            writer.write(logMessage);
        }
    }
}
