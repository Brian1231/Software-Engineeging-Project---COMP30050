package client.java.tests;


/**
 * © 2015 isp-insoft all rights reserved.
 */

import java.util.concurrent.CountDownLatch;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.sun.javafx.application.PlatformImpl;

/**
 * This runner is used to run JUnit-Tests on the JavaFx-Thread.
 * This class is passed as a parameter with the @RunWith annotation
 **/

@SuppressWarnings("restriction")
public class JfxTestRunner extends BlockJUnit4ClassRunner {
    /**
     * Creates a test runner, that initializes the JavaFx runtime.
     *
     * @param aClass The class under test.
     * @throws InitializationError if the test class is malformed.
     */
    public JfxTestRunner(final Class<?> aClass) throws InitializationError {
        super(aClass);
        try {
            setupJavaFX();
        } catch (final InterruptedException e) {
            throw new InitializationError("Could not initialize the JavaFx platform.");
        }
    }

    private static void setupJavaFX() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        // initializes JavaFX environment
        PlatformImpl.startup(() ->
        {
            /* No need to do anything here */
        });

        latch.countDown();

        latch.await();
    }
}