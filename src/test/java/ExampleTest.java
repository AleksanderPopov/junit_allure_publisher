import com.alex.jap.AllureAttachment;
import com.alex.jap.AllurePublisher;
import org.junit.Rule;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.*;

import static com.alex.jap.AllurePublisher.*;
import static org.junit.Assert.fail;

/**
 * Created by apop on 3/28/2017.
 */
public class ExampleTest {

    @Rule
    public AllurePublisher alwaysPublisher = always().publish(new AllureAttachment() {
        @Override
        @Attachment(value = "always publish this attach", type = "text/plain")
        public byte[] publish() {
            createTestAttach();
            byte[] result = getBytesTestAttach();
            removeTestAttach();
            return result;
        }
    });

    @Rule
    public AllurePublisher successPublisher = onSuccess().publish(new AllureAttachment() {
        @Override
        @Attachment(value = "if success publish this attach", type = "text/plain")
        public byte[] publish() {
            createTestAttach();
            byte[] result = getBytesTestAttach();
            removeTestAttach();
            return result;
        }
    });

    @Rule
    public AllurePublisher failPublisher = onFail().publish(new AllureAttachment() {
        @Override
        @Attachment(value = "if fail publish this attach", type = "text/plain")
        public byte[] publish() {
            createTestAttach();
            byte[] result = getBytesTestAttach();
            removeTestAttach();
            return result;
        }
    });

    @Test
    public void successTest() {
    }

    @Test
    public void failedTest() {
        fail();
    }

    private void removeTestAttach() {
        new File(".//test.txt").delete();
    }

    private void createTestAttach() {
        File file;
        try (Writer writer = new FileWriter(file = new File(".//test.txt"))) {
            file.createNewFile();
            writer.write("test attachment");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getBytesTestAttach() {
        try (
                Reader reader = new FileReader(new File(".//test.txt"));
                ByteArrayOutputStream writer = new ByteArrayOutputStream()
        ) {
            int i;
            while ((i = reader.read()) != -1) writer.write(i);
            return writer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
