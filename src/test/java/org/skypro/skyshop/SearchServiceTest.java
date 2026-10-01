package org.skypro.skyshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void checkSearchIfStorageIsEmpty() {
        when(storageService.getAllSearchables()).thenReturn(List.of());
        List<SearchResult> result = searchService.search("Футболка").stream().toList();
        assertThat(result).isEmpty();
    }

    @Test
    void checkSearchIfProductsInListIsNoMatch() {
        Searchable product = mock(Searchable.class);
        when(product.getSearchTerm()).thenReturn("Шорты");
        when(storageService.getAllSearchables()).thenReturn(List.of(product));
        List<SearchResult> result = searchService.search("Кепка").stream().toList();
        assertThat(result).isEmpty();
    }

    @Test
    void checkSearchIfProductsIsMatch() {
        Searchable product = mock(Searchable.class);
        when(product.getId()).thenReturn(java.util.UUID.randomUUID());
        when(product.getName()).thenReturn("TestProduct");
        when(product.getSearchTerm()).thenReturn("TestProduct");
        when(product.getContentType()).thenReturn("Продукт");
        when(storageService.getAllSearchables()).thenReturn(List.of(product));
        List<SearchResult> result = searchService.search("Test").stream().toList();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("TestProduct");
    }
}
