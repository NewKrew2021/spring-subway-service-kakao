package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PathGraphEdgeTest {

    @DisplayName("생성자 테스트")
    @Test
    public void PathGraphEdgeTest() {
        PathGraphEdge pathGraphEdge = new PathGraphEdge(5, 1000);
        assertThat(pathGraphEdge.getExtraFare()).isEqualTo(1000);
        assertThat(pathGraphEdge.getWeight()).isEqualTo(5);
    }

}
