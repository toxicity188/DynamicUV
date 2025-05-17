import kr.toxicity.library.dynamicuv.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class JsonTest {
    @Test
    public void testJson() {
        System.out.println(new UVElement(
                new ElementVector(8, 4, 4).div(16),
                new ElementVector(0, 2, 0).div(16),
                new UVSpace(8, 4, 4),
                UVElement.ColorType.RGB,
                Map.ofEntries(
                        Map.entry(UVFace.NORTH, new UVPos(0, 0)),
                        Map.entry(UVFace.SOUTH, new UVPos(0, 0)),
                        Map.entry(UVFace.EAST, new UVPos(0, 0)),
                        Map.entry(UVFace.WEST, new UVPos(0, 0)),
                        Map.entry(UVFace.UP, new UVPos(0, 0)),
                        Map.entry(UVFace.DOWN, new UVPos(0, 0))
                )
        ).asJson("one_pixel"));
    }
}
