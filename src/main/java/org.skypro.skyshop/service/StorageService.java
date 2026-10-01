package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {

    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        products = new HashMap<>();
        articles = new HashMap<>();

        initializeStorage();
    }

    private void initializeStorage() {

        Product product1 = new SimpleProduct(
                UUID.randomUUID(),
                "Футболка",
                1500
        );

        Product product2 = new SimpleProduct(
                UUID.randomUUID(),
                "Шорты",
                2100
        );

        Product product3 = new DiscountedProduct(
                UUID.randomUUID(),
                "Рубашка",
                1900,
                20
        );

        Product product4 = new FixPriceProduct(
                UUID.randomUUID(),
                "Кепка"
        );

        Product product5 = new DiscountedProduct(
                UUID.randomUUID(),
                "Брюки и Футболка",
                2500,
                15
        );

        Product product6 = new SimpleProduct(
                UUID.randomUUID(),
                "Ремень",
                900
        );

        products.put(product1.getId(), product1);
        products.put(product2.getId(), product2);
        products.put(product3.getId(), product3);
        products.put(product4.getId(), product4);
        products.put(product5.getId(), product5);
        products.put(product6.getId(), product6);


        Article article1 = new Article(
                UUID.randomUUID(),
                "Модели футболок. футболка",
                "Футболки классифицируют по ..."
        );

        Article article2 = new Article(
                UUID.randomUUID(),
                "Обозначение размеров шорт на этикетках и ярлыках",
                "Существует несколько систем маркировки размеров шорт.."
        );

        Article article3 = new Article(
                UUID.randomUUID(),
                "Акссессуары для рубашки",
                "Футболка - нет, рубашка выглядела безупречно.."
        );

        Article article4 = new Article(
                UUID.randomUUID(),
                "Кепка - только ли для лета?. Футболка",
                "Сегодня поговорим о стиле, моде и удобстве нашей головы..."
        );

        articles.put(article1.getId(), article1);
        articles.put(article2.getId(), article2);
        articles.put(article3.getId(), article3);
        articles.put(article4.getId(), article4);
    }


    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }

    public Collection<Searchable> getAllSearchables() {

        List<Searchable> searchables = new ArrayList<>();

        searchables.addAll(products.values());
        searchables.addAll(articles.values());

        return searchables;
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }
}