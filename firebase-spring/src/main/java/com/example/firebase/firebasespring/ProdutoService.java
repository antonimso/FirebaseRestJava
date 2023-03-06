package com.example.firebase.firebasespring;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProdutoService {
    private static final String COL_NAME = "products";

    public Produto getProdutos(String name) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = null;
        try {
            document = future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        Produto patient = null;
        if (document.exists()) {
            patient = document.toObject(Produto.class);
            return patient;
        } else {
            return null;
        }

    }

    public List<Produto> getProcutList() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> listDocuments = dbFirestore.collection(COL_NAME).listDocuments();
        List<Produto> productList = new ArrayList<>();
        Produto product;

        for (DocumentReference ld : listDocuments) {
            ApiFuture<DocumentSnapshot> future = ld.get();

            try {
                product = future.get().toObject(Produto.class);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

            productList.add(product);
        }
        return productList;
    }

    public void criarProduto(Produto produto) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(produto.getName()).set(produto);
        try {
            collectionsApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(Produto person) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(person.getName()).set(person);
        collectionsApiFuture.get();
    }

    public void deleteProduct(String name) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection(COL_NAME).document(name).delete();
    }
}
