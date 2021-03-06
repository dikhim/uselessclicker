package org.dikhim.jclicker.actions.utils.decoders;

import org.dikhim.jclicker.actions.actions.Action;
import org.dikhim.jclicker.util.Gzip;
import org.dikhim.jclicker.actions.utils.encoding.UnicodeDecoder;

import java.io.IOException;
import java.util.List;

public class UnicodeZipActionDecoder extends UnicodeActionDecoder {
    @Override
    public List<Action> decode(String code) {
        String decompressed = "";
        try {
            decompressed = Gzip.decompressString(UnicodeDecoder.decode(code));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.decode(decompressed);
    }
}
