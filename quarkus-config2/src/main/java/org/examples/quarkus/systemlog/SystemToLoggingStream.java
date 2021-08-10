package org.examples.quarkus.systemlog;

import java.io.OutputStream;
import java.io.PrintStream;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

public class SystemToLoggingStream extends OutputStream {

	private final Logger logger;
    private final Level level;
    private final StringBuffer buffer = new StringBuffer();

    public SystemToLoggingStream (Logger logger, Level level) {
        this.logger = logger;
        this.level = level;
    }

    public void write (int b) {
        byte[] bytes = new byte[1];
        bytes[0] = (byte) (b & 0xff);
        String str = new String(bytes);

        if (str.equals("\n")) {
            flush ();
        }
        else {
            buffer.append(str);
        }
    }

    public void flush () {
        logger.log (level, buffer);
        buffer.setLength(0);
    }

    public static PrintStream createPrintStream(Logger logger, Level level) {
        return new PrintStream(new SystemToLoggingStream(logger, level));
    }

}
