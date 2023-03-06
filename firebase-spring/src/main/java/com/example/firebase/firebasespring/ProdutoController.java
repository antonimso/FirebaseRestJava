package com.example.firebase.firebasespring;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class ProdutoController {
    private final ProdutoService productservice;

    public ProdutoController(ProdutoService productservice) {
        this.productservice = productservice;
    }

    @PostMapping(path = "/salvarproduto")
    public void createProduct(@RequestBody Produto produto) {
        productservice.criarProduto(produto);
    }

    @GetMapping("/getList")
    public List<Produto> getProductList() throws ExecutionException, InterruptedException {
        return productservice.getProcutList();
    }

    @GetMapping("/getDetails/{name}")
    public Produto getProduct(@PathVariable String name ){
        return productservice.getProdutos(name);
    }

    @PutMapping("/updateDetails")
    public void updateProduct(@RequestBody Produto produto) throws ExecutionException, InterruptedException {
        productservice.updateProduct(produto);
    }

    @DeleteMapping(path = "{produtoId}")
    public void deleteProduct(@PathVariable("produtoId") String productName) {
        productservice.deleteProduct(productName);
    }
}
