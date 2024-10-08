package tuanloc.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tuanloc.junit.exception.InvalidStatusCodeException;
import tuanloc.junit.scrapper.Scrapper;

public class CrawlerServiceTest {
    private static CrawlerService crawlerService;
    private static Scrapper scrapper;

    @BeforeAll
    public static void beforeAll() {
        scrapper = mock(Scrapper.class);
        crawlerService = new CrawlerService(scrapper);
    }

    @Test
    void testShowStatusCode_ScrapperReturn200_VerifyScrapperCallAndNoError() throws InvalidStatusCodeException {
        // Arrange
        String url = "https://tuanloc.com";
        when(scrapper.crawlStatusCode(url)).thenReturn(200);
        // Act
        int statusCode = crawlerService.showStatusCodeOrRaiseException(url);
        // Assert
        assertEquals(200, statusCode);
        verify(scrapper).crawlStatusCode("https://tuanloc.com");
    }

    @Test
    void testShowStatusCode_ScrapperReturn500_VerifyScrapperCallAndException() {
        // Arrange
        String url = "https://tuanloc-500.com";
        when(scrapper.crawlStatusCode(url)).thenReturn(500);
        // Act
        assertThrows(InvalidStatusCodeException.class, () -> crawlerService.showStatusCodeOrRaiseException(url));
        // Assert
        verify(scrapper).crawlStatusCode("https://tuanloc-500.com");
    }
}
