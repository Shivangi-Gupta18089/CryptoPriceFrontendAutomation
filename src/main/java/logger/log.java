package logger;

import org.apache.log4j.*;

import java.io.IOException;

public class log {

    private static final Logger LOGGER = Logger.getLogger("logger");
    private static final PatternLayout layout = new PatternLayout("%d{dd MMM yyyy HH:mm:ss} %5p %c{1} - %m%n");
    private static FileAppender appender;
    private static ConsoleAppender consoleAppender;

    static
    {
        try
        {
            consoleAppender = new ConsoleAppender(layout, "System.out");
            appender= new FileAppender(layout,"logs/logFile.txt",true);
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * method to display errors in log.
     * @param className name of class in which error occurred.
     * @param methodName name of method in which error occurred.
     * @param exception stack trace of exception
     */

    public static void logError (String className,String methodName,String exception)
    {
        LOGGER.addAppender(appender);
        LOGGER.setLevel(Level.INFO);
        LOGGER.info("ClassName :"+className);
        LOGGER.info("MethodName :"+methodName );
        LOGGER.info("Exception :" +exception);
        LOGGER.info("-----------------------------------------------------------------------------------");
    }

    /**
     * method to display information in logs
     * @param message message to be displayed
     */
    public static void info(String message){
        consoleAppender.setName("Console");
        LOGGER.addAppender(consoleAppender);
        LOGGER.addAppender(appender);
        LOGGER.setLevel(Level.INFO);
        LOGGER.info(message);
    }

    public static void error(String message, Exception e){
        consoleAppender.setName("Console");
        LOGGER.addAppender(consoleAppender);
        LOGGER.addAppender(appender);
        LOGGER.setLevel(Level.ERROR);
        LOGGER.error(message);
    }

    public static void warn(String message){
        consoleAppender.setName("Console");
        LOGGER.addAppender(consoleAppender);
        LOGGER.addAppender(appender);
        LOGGER.setLevel(Level.WARN);
        LOGGER.warn(message);
    }

}
