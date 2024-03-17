package works.hop.eztag.server.router;

import org.junit.jupiter.api.Test;
import works.hop.eztag.server.handler.IReqHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PathReqRouterTest {

    PathReqRouter router = new PathReqRouter();

    @Test
    void store_and_fetch_with_simple_path() {
        IReqHandler mockHandler = mock(IReqHandler.class);
        when(mockHandler.path()).thenReturn("/");

        router.store("post", "/", mockHandler);
        //now fetch the handler
        IReqHandler fetched = router.fetch("post", "/");
        assertThat(fetched).isSameAs(mockHandler);
    }

    @Test
    void store_and_fetch_and_identical_simple_path() {
        IReqHandler mockHandler = mock(IReqHandler.class);
        when(mockHandler.path()).thenReturn("/one");

        router.store("post", "/one", mockHandler);
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
            router.store("post", "/one", mockHandler);
        }).withMessage("The current path '/one/' path is already matched").withNoCause();
    }

    @Test
    void store_and_fetch_with_path_having_a_param() {
        IReqHandler mockHandler = mock(IReqHandler.class);
        when(mockHandler.path()).thenReturn("/{id}/one");
        PathParams mockParams = new PathParams();
        when(mockHandler.params()).thenReturn(mockParams);

        router.store("post", "/{id}/one", mockHandler);
        //now fetch the handler
        IReqHandler fetched = router.fetch("post", "/two/one");
        assertThat(fetched).isSameAs(mockHandler);
        assertThat(mockParams).hasSize(1);
        assertThat(mockParams.get("id")).isEqualTo("two");
    }

    @Test
    void store_and_fetch_with_path_having_two_param() {
        IReqHandler mockHandler = mock(IReqHandler.class);
        when(mockHandler.path()).thenReturn("/{id}/one/{name}");
        PathParams mockParams = new PathParams();
        when(mockHandler.params()).thenReturn(mockParams);

        router.store("post", "/{id}/one/{name}", mockHandler);
        //now fetch the handler
        IReqHandler fetched = router.fetch("post", "/two/one/three");
        assertThat(fetched).isSameAs(mockHandler);
        assertThat(mockParams).hasSize(2);
        assertThat(mockParams.get("id")).isEqualTo("two");
        assertThat(mockParams.get("name")).isEqualTo("three");
    }

    @Test
    void store_and_fetch_with_non_existent_path() {
        IReqHandler mockHandler = mock(IReqHandler.class);
        when(mockHandler.path()).thenReturn("/one");

        router.store("post", "/one", mockHandler);
        //now fetch the handler
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> router.fetch("post", "/two"))
                .withMessage("Could not find a handler configured for the '/two/' path")
                .withNoCause();
    }
}