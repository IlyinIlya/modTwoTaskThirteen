package org.skypro.skyshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.StorageService;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    @Test
    void checkIfAddNotExistProductThenShouldBeException() {
        UUID wrongId = UUID.randomUUID();
        when(storageService.getProductById(wrongId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> basketService.addProduct(wrongId));
    }

    @Test
    void checkIfAddProductThenCallMethodAddProduct() {
        UUID correctId = UUID.randomUUID();
        Product product = new SimpleProduct(correctId, "Футболка", 1500);
        when(storageService.getProductById(correctId)).thenReturn(Optional.of(product));
        basketService.addProduct(correctId);
        verify(productBasket, times(1)).addProduct(correctId);
    }

    @Test
    void checkIfBasketIsEmptyThenReturnEmptyString() {
        when(productBasket.getProducts()).thenReturn(Map.of());
        UserBasket userBasket = basketService.getUserBasket();
        assertThat(userBasket.getItems()).isEmpty();
        assertThat(userBasket.getTotal()).isZero();
    }

    @Test
    void checkIfBasketIsFillThenReturnMatchBasket() {
        UUID productId = UUID.randomUUID();
        Product product = new SimpleProduct(productId, "Футболка", 1500);
        when(productBasket.getProducts()).thenReturn(Map.of(productId, 3));
        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));
        UserBasket userBasket = basketService.getUserBasket();
        assertThat(userBasket.getItems()).hasSize(1);
        assertThat(userBasket.getTotal()).isEqualTo(4500);
    }
}
