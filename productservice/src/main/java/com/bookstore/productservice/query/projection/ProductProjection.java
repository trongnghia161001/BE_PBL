package com.bookstore.productservice.query.projection;

import com.bookstore.commonservice.model.*;
import com.bookstore.commonservice.query.*;
import com.bookstore.productservice.command.data.Product;
import com.bookstore.productservice.command.data.ProductRepository;
import com.bookstore.productservice.query.model.ProductResponseModel;
import com.bookstore.productservice.query.queries.*;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductProjection {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<ProductResponseModel> getAllBook(GetAllProductQuery getAll) {
        List<ProductResponseModel> list = new ArrayList<>();
        List<Product> productList = repository.findAll();
        try {
            if (productList.size() > 0) {
                productList.forEach(book -> {
                    ProductResponseModel model = new ProductResponseModel();
                    BeanUtils.copyProperties(book, model);

                    try {
                        GetProductByAuthorQuery getProductByAuthorQuery = new GetProductByAuthorQuery(book.getAuthorId());
                        AuthorResponseCommonModel authorResponseCommonModel = queryGateway.query(getProductByAuthorQuery,
                                ResponseTypes.instanceOf(AuthorResponseCommonModel.class)).join();
                        if (authorResponseCommonModel != null) {
                            model.setAuthorName(authorResponseCommonModel.getName());
                        }
                    } catch (Exception e) {
                        // Xử lý ngoại lệ khi không thể truy vấn thông tin tác giả
                        // Ví dụ: log lỗi, gán giá trị mặc định cho model.setAuthorName(), hoặc ném một ngoại lệ khác
                        // throw new AuthorQueryException("Không thể truy vấn thông tin tác giả", e);
                    }

                    try {
                        GetProductByCategoryQuery getProductByCategoryQuery = new GetProductByCategoryQuery(book.getCategories());
                        CategoriesResponseCommonModel categoriesResponseCommonModel = queryGateway.query(getProductByCategoryQuery,
                                ResponseTypes.instanceOf(CategoriesResponseCommonModel.class)).join();
                        if (categoriesResponseCommonModel != null) {
                            model.setCategoriesName(categoriesResponseCommonModel.getName());
                        }
                    } catch (Exception e) {
                        // Xử lý ngoại lệ khi không thể truy vấn thông tin danh mục
                        // Ví dụ: log lỗi, gán giá trị mặc định cho model.setCategoriesName(), hoặc ném một ngoại lệ khác
                        // throw new CategoryQueryException("Không thể truy vấn thông tin danh mục", e);
                    }

                    // Xử lý các truy vấn khác tương tự cho Publisher và ProductType

                    list.add(model);
                });
            }
        } catch (Exception e) {
            throw new InvalidCredentialsException("Không có dữ liệu");
        }
        return list;
    }


    class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }


    @QueryHandler
    public ProductResponseModel handler(GetProductById getProductById) {
        ProductResponseModel model = new ProductResponseModel();
        Product product = repository.getReferenceById(getProductById.getId());
        BeanUtils.copyProperties(product, model);
        GetProductByAuthorQuery getProductByAuthorQuery = new GetProductByAuthorQuery(product.getAuthorId());
        AuthorResponseCommonModel authorResponseCommonModel = queryGateway.query(getProductByAuthorQuery,
                ResponseTypes.instanceOf(AuthorResponseCommonModel.class)).join();
        if (authorResponseCommonModel != null) {
            model.setAuthorName(authorResponseCommonModel.getName());
        }

        GetProductByCategoryQuery getProductByCategoryQuery = new GetProductByCategoryQuery(product.getCategories());
        CategoriesResponseCommonModel categoriesResponseCommonModel = queryGateway.query(getProductByCategoryQuery,
                ResponseTypes.instanceOf(CategoriesResponseCommonModel.class)).join();
        if (categoriesResponseCommonModel != null) {
            model.setCategoriesName(categoriesResponseCommonModel.getName());
        }

        GetProductByProductTypeQuery getProductByProductTypeQuery = new GetProductByProductTypeQuery(product.getProductTypeId());
        ProductTypeResponseCommonModel productTypeResponseCommonModel = queryGateway.query(getProductByProductTypeQuery,
                ResponseTypes.instanceOf(ProductTypeResponseCommonModel.class)).join();
        if (productTypeResponseCommonModel != null) {
            model.setProductTypeName(productTypeResponseCommonModel.getName());
        }

        GetProductByPublisherQuery getProductByPublisherQuery = new GetProductByPublisherQuery(product.getPublisherId());
        PublisherResponseCommonModel publisherResponseCommonModel = queryGateway.query(getProductByPublisherQuery,
                ResponseTypes.instanceOf(PublisherResponseCommonModel.class)).join();
        if (publisherResponseCommonModel != null) {
            model.setPublisherName(publisherResponseCommonModel.getName());
        }
        return model;
    }

    @QueryHandler
    public ProductResponseCommonModel handle(GetDetailsProductQuery getDetailsProductQuery) {
        ProductResponseCommonModel model = new ProductResponseCommonModel();
        Product product = repository.getReferenceById(getDetailsProductQuery.getId());
        BeanUtils.copyProperties(product, model);
        return model;
    }

    @QueryHandler
    public List<ProductResponseModel> getAllBookByName(GetAllProductByNameQuery getAll) {
        List<ProductResponseModel> list = new ArrayList<>();
        List<Product> List = repository.findByNameContainingIgnoreCase(getAll.getName());
        List.forEach(book -> {
            ProductResponseModel model = new ProductResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<ProductResponseCommonModel> getAllBookByAuthor(GetProductByAuthorQuery getProductByAuthorQuery) {
        List<ProductResponseCommonModel> list = new ArrayList<>();
        List<Product> products = repository.findByAuthorId(getProductByAuthorQuery.getAuthorId());
        if (products.size() > 0) {
            products.forEach(book -> {
                ProductResponseCommonModel productResponseCommonModel = new ProductResponseCommonModel();
                BeanUtils.copyProperties(book, productResponseCommonModel);
                list.add(productResponseCommonModel);
            });
        }
        return list;
    }

    @QueryHandler
    public List<ProductResponseCommonModel> getAllBookByPublisher(GetProductByPublisherQuery getProductByPublisherQuery) {
        List<ProductResponseCommonModel> list = new ArrayList<>();
        List<Product> products = repository.findByPublisherId(getProductByPublisherQuery.getPublisherId());
        if (products.size() > 0) {
            products.forEach(book -> {
                ProductResponseCommonModel productResponseCommonModel = new ProductResponseCommonModel();
                BeanUtils.copyProperties(book, productResponseCommonModel);
                list.add(productResponseCommonModel);
            });
        }
        return list;
    }

    @QueryHandler
    public List<ProductResponseModel> getAllBookByPublishedDate(GetAllProductByPublishedDateQuery publishedDateQuery) {
        List<ProductResponseModel> list = new ArrayList<>();
        if (publishedDateQuery.getStartDate() != null) {
            List<Product> products = repository.findByPublishedDateAfter(publishedDateQuery.getStartDate());
            if (products.size() > 0) {
                products.forEach(book -> {
                    ProductResponseModel productResponseCommonModel = new ProductResponseModel();
                    BeanUtils.copyProperties(book, productResponseCommonModel);
                    list.add(productResponseCommonModel);
                });
            }
        }
        if (publishedDateQuery.getEndDate() != null) {
            List<Product> products = repository.findByPublishedDateBefore(publishedDateQuery.getEndDate());
            if (products.size() > 0) {
                products.forEach(book -> {
                    ProductResponseModel productResponseCommonModel = new ProductResponseModel();
                    BeanUtils.copyProperties(book, productResponseCommonModel);
                    list.add(productResponseCommonModel);
                });
            }
        }

        if (publishedDateQuery.getStartDate() != null && publishedDateQuery.getEndDate() != null) {
            List<Product> products = repository.findByPublishedDateBetween(publishedDateQuery.getStartDate(),
                    publishedDateQuery.getEndDate());
            if (products.size() > 0) {
                products.forEach(book -> {
                    ProductResponseModel productResponseCommonModel = new ProductResponseModel();
                    BeanUtils.copyProperties(book, productResponseCommonModel);
                    list.add(productResponseCommonModel);
                });
            }
        }
        return list;
    }

    @QueryHandler
    public List<ProductResponseModel> getAllBookByPrice(GetAllProductByPriceQuery priceQuery) {
        List<ProductResponseModel> list = new ArrayList<>();
        if (priceQuery.getMaxPrice() != null) {
            List<Product> products = repository.findByPriceBefore(priceQuery.getMaxPrice());
            if (products.size() > 0) {
                products.forEach(book -> {
                    ProductResponseModel productResponseCommonModel = new ProductResponseModel();
                    BeanUtils.copyProperties(book, productResponseCommonModel);
                    list.add(productResponseCommonModel);
                });
            }
        }
        if (priceQuery.getMinPrice() != null) {
            List<Product> products = repository.findByPriceAfter(priceQuery.getMinPrice());
            if (products.size() > 0) {
                products.forEach(book -> {
                    ProductResponseModel productResponseCommonModel = new ProductResponseModel();
                    BeanUtils.copyProperties(book, productResponseCommonModel);
                    list.add(productResponseCommonModel);
                });
            }
        }

        if (priceQuery.getMinPrice() != null && priceQuery.getMaxPrice() != null) {
            List<Product> products = repository.findByPriceBetween(priceQuery.getMinPrice(),
                    priceQuery.getMaxPrice());
            if (products.size() > 0) {
                products.forEach(book -> {
                    ProductResponseModel productResponseCommonModel = new ProductResponseModel();
                    BeanUtils.copyProperties(book, productResponseCommonModel);
                    list.add(productResponseCommonModel);
                });
            }
        }
        return list;

    }

    @QueryHandler
    public List<ProductResponseModel> getAllBookByCondition(GetAllProductByConditionQuery conditionQuery) {
        List<ProductResponseModel> list = new ArrayList<>();
        List<Product> products = repository.findByDataFieldContainingIgnoreCase(conditionQuery.getCondition());
        if (products.size() > 0) {
            products.forEach(book -> {
                ProductResponseModel productResponseCommonModel = new ProductResponseModel();
                BeanUtils.copyProperties(book, productResponseCommonModel);
                list.add(productResponseCommonModel);
            });
        }
        return list;
    }

    @QueryHandler
    public List<ProductResponseModel> getAllBookByBinding(GetAllProductByBindingQuery byBindingQuery) {
        List<ProductResponseModel> list = new ArrayList<>();
        List<Product> products = repository.findByBindingContainingIgnoreCase(byBindingQuery.getBinding());
        if (products.size() > 0) {
            products.forEach(book -> {
                ProductResponseModel productResponseCommonModel = new ProductResponseModel();
                BeanUtils.copyProperties(book, productResponseCommonModel);
                list.add(productResponseCommonModel);
            });
        }
        return list;
    }

    @QueryHandler
    public List<ProductResponseCommonModel> getAllBookByProductType(GetProductByProductTypeQuery getProductByProductTypeQuery) {
        List<ProductResponseCommonModel> list = new ArrayList<>();
        List<Product> products = repository.findByAuthorId(getProductByProductTypeQuery.getProductTypeId());
        if (products.size() > 0) {
            products.forEach(book -> {
                ProductResponseCommonModel productResponseCommonModel = new ProductResponseCommonModel();
                BeanUtils.copyProperties(book, productResponseCommonModel);
                list.add(productResponseCommonModel);
            });
        }
        return list;
    }

    @QueryHandler
    public List<ProductResponseModel> getAllBookByAPC(GetAllProductByAPC getAllProductByAPC) {
        List<ProductResponseModel> products = new ArrayList<>();
        String name = getAllProductByAPC.getName();
        String authorName = getAllProductByAPC.getAuthorName();
        String publisherName = getAllProductByAPC.getPublisherName();
        Double minPrice = getAllProductByAPC.getMinPrice();
        Double maxPrice = getAllProductByAPC.getMaxPrice();

        if (name != null) {
            List<Product> productName =
                    repository.findByNameContainingIgnoreCase(name);
            productName.forEach(book -> {
                ProductResponseModel model = new ProductResponseModel();
                BeanUtils.copyProperties(book, model);
                products.add(model);
            });
        }

        if (authorName != null) {
            GetProductByAuthorNameQuery getAllAuthorByNameQuery =
                    new GetProductByAuthorNameQuery(authorName);
            List<AuthorResponseCommonModel>  author = queryGateway.query(getAllAuthorByNameQuery,
                    ResponseTypes.multipleInstancesOf(AuthorResponseCommonModel.class)).join();
            author.forEach(authorResponseCommonModel -> {
                String authorId = authorResponseCommonModel.getId();
                List<Product> productsByAuthorId =
                        repository.findByAuthorId(authorId);
                productsByAuthorId.forEach(product -> {
                    ProductResponseModel productResponseModel = new ProductResponseModel();
                    BeanUtils.copyProperties(product, productResponseModel);
                    products.add(productResponseModel);
                });

            });

        }

        if (publisherName != null) {
            GetProductByPublishNameQuery getProductByPublishNameQuery = new
                    GetProductByPublishNameQuery(publisherName);
            List<PublisherResponseCommonModel> publisher = queryGateway.query
                    (getProductByPublishNameQuery, ResponseTypes.multipleInstancesOf(PublisherResponseCommonModel.class)).join();
            publisher.forEach(publisherResponseCommonModel -> {
                String publisherId = publisherResponseCommonModel.getId();
                List<Product> productsByPublisherId = repository.findByPublisherId(publisherId);
                productsByPublisherId.forEach(product -> {
                    ProductResponseModel productResponseModel = new ProductResponseModel();
                    BeanUtils.copyProperties(product, productResponseModel);
                    products.add(productResponseModel);
                });
            });
        }

        if (maxPrice != null) {
            List<Product> productsMax =
                    repository.findByPriceBefore(maxPrice);
            if (productsMax.size() > 0) {
                productsMax.forEach(book -> {
                    ProductResponseModel productResponseCommonModel = new ProductResponseModel();
                    BeanUtils.copyProperties(book, productResponseCommonModel);
                    products.add(productResponseCommonModel);
                });
            }
        }
        if (minPrice != null) {
            List<Product> productsMin =
                    repository.findByPriceAfter(minPrice);
            if (productsMin.size() > 0) {
                productsMin.forEach(book -> {
                    ProductResponseModel productResponseCommonModel = new ProductResponseModel();
                    BeanUtils.copyProperties(book, productResponseCommonModel);
                    products.add(productResponseCommonModel);
                });
            }
        }

        if (minPrice != null && maxPrice != null) {
            List<Product> productsMM =
                    repository.findByPriceBetween(minPrice,
                    maxPrice);
            if (productsMM.size() > 0) {
                productsMM.forEach(book -> {
                    ProductResponseModel productResponseCommonModel = new ProductResponseModel();
                    BeanUtils.copyProperties(book, productResponseCommonModel);
                    products.add(productResponseCommonModel);
                });
            }
        }
        return products;
    }

}
